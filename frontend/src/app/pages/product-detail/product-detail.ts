import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../domain/services/product.service';
import { Product } from '../../domain/models/product';
import { ProductDetailedResponse } from '../../domain/dto/product-detailed-response';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// Imports do PrimeNG
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { RatingModule } from 'primeng/rating';
import { ChipModule } from 'primeng/chip';
import { ButtonModule } from 'primeng/button';
import { ChartModule } from 'primeng/chart';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-product-detail',
  imports: [
    CommonModule,
    FormsModule,
    BreadcrumbModule,
    RatingModule,
    ChipModule,
    ButtonModule,
    ChartModule,
    ProgressSpinnerModule,
  ],
  templateUrl: './product-detail.html',
  styleUrl: './product-detail.scss',
})
export class ProductDetail implements OnInit {
  id = 0;
  product!: ProductDetailedResponse;

  // Propriedades para o breadcrumb
  breadcrumbItems: MenuItem[] = [];
  homeItem: MenuItem = { icon: 'pi pi-home', routerLink: '/' };

  // Propriedades calculadas
  menorPreco = 0;
  marcaOficial = '';
  imagemAtiva = 0; // Índice da imagem ativa na galeria
  menorLoja: string = '';

  // Dados do gráfico
  chartData: any;
  chartOptions: any;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
  ) {
    this.initializeChart();
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      if (!params.has('id')) {
        throw new Error('Product ID not provided');
      }
      this.id = Number(params.get('id'));
      this.productService.getProductDetailed(this.id).subscribe((product) => {
        this.product = product;
        this.imagemAtiva = 0; // Reset da imagem ativa para o novo produto
        this.setupBreadcrumb();
        this.calculateMenorPreco();
        this.extractMarcaOficial();
        this.updateChartData();
        this.product.offers.sort((a, b) => a.price - b.price);
        this.menorPreco = this.product.offers[0]?.price;
        this.menorLoja = this.product.offers[0]?.sellerName;
      });
    });
  }

  private setupBreadcrumb(): void {
    this.breadcrumbItems = [{ label: 'Home', routerLink: '/' }];

    // Adiciona a categoria do produto se existir
    if (this.product.category && this.product.category.name) {
      this.breadcrumbItems.push({
        label: this.product.category.name,
        routerLink: `/categoria/${
          this.product.category.id || this.product.category.name.toLowerCase()
        }`,
      });
    }

    // Adiciona o título do produto como último item (sem link)
    this.breadcrumbItems.push({
      label: this.product.title,
    });
  }

  private calculateMenorPreco(): void {
    if (this.product.offers && this.product.offers.length > 0) {
      this.menorPreco = Math.min(
        ...this.product.offers.map((offer) => offer.price)
      );
    }
  }

  private extractMarcaOficial(): void {
    // Usa a entidade brand do backend em vez de extrair do título
    if (this.product.brand && this.product.brand.name) {
      this.marcaOficial = this.product.brand.name;
    } else {
      this.marcaOficial = 'Marca não identificada';
    }
  }

  private initializeChart(): void {
    this.chartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false,
        },
      },
      scales: {
        x: {
          display: true,
          grid: {
            display: false,
          },
        },
        y: {
          display: true,
          grid: {
            color: '#e0e0e0',
          },
          beginAtZero: false, // Permite que a escala se ajuste aos dados
          ticks: {
            callback: function (value: any) {
              return (
                'R$ ' +
                value.toLocaleString('pt-BR', {
                  minimumFractionDigits: 2,
                  maximumFractionDigits: 2,
                })
              );
            },
          },
        },
      },
      elements: {
        line: {
          tension: 0.4,
          borderColor: '#3FBF52',
          backgroundColor: 'rgba(63, 191, 82, 0.1)',
          fill: true,
        },
        point: {
          radius: 4,
          backgroundColor: '#3FBF52',
          borderColor: '#ffffff',
          borderWidth: 2,
        },
      },
    };
  }

  private updateChartData(): void {
    // Limpa dados anteriores completamente
    this.chartData = null;

    // Remove o setTimeout que pode estar causando problemas
    if (
      this.product &&
      this.product.history &&
      this.product.history.length > 0
    ) {
      console.log('Dados do histórico recebidos:', this.product.history);

      // Ordena o histórico por data para garantir ordem cronológica
      const sortedHistory = [...this.product.history].sort(
        (a, b) => new Date(a.date).getTime() - new Date(b.date).getTime()
      );

      const labels = sortedHistory.map((h) => {
        const date = new Date(h.date);
        return date.toLocaleDateString('pt-BR', {
          day: '2-digit',
          month: '2-digit',
        });
      });

      const prices = sortedHistory.map((h) => Number(h.price));
      console.log('Preços processados:', prices);

      // Calcula min e max para ajustar a escala Y
      const minPrice = Math.min(...prices);
      const maxPrice = Math.max(...prices);
      const range = maxPrice - minPrice;
      const padding = range > 0 ? range * 0.1 : maxPrice * 0.1; // 10% de padding

      console.log(
        `Min: ${minPrice}, Max: ${maxPrice}, Range: ${range}, Padding: ${padding}`
      );

      // Cria nova instância das opções do gráfico com escala específica
      this.chartOptions = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false,
          },
        },
        scales: {
          x: {
            display: true,
            grid: {
              display: false,
            },
          },
          y: {
            display: true,
            grid: {
              color: '#e0e0e0',
            },
            min: Math.max(0, minPrice - padding),
            max: maxPrice + padding,
            beginAtZero: false,
            ticks: {
              callback: function (value: any) {
                return (
                  'R$ ' +
                  Number(value).toLocaleString('pt-BR', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2,
                  })
                );
              },
            },
          },
        },
        elements: {
          line: {
            tension: 0.4,
            borderColor: '#3FBF52',
            backgroundColor: 'rgba(63, 191, 82, 0.1)',
            fill: true,
          },
          point: {
            radius: 4,
            backgroundColor: '#3FBF52',
            borderColor: '#ffffff',
            borderWidth: 2,
          },
        },
      };

      this.chartData = {
        labels: labels,
        datasets: [
          {
            data: prices,
            borderColor: '#3FBF52',
            backgroundColor: 'rgba(63, 191, 82, 0.1)',
            fill: true,
            tension: 0.4,
          },
        ],
      };

      console.log(
        'Gráfico configurado com escala Y:',
        this.chartOptions.scales.y.min,
        'a',
        this.chartOptions.scales.y.max
      );
    } else {
      console.log('Sem dados de histórico disponíveis');
      // Estado para produtos sem histórico
      this.chartData = {
        labels: ['Sem dados'],
        datasets: [
          {
            data: [0],
            borderColor: '#3FBF52',
            backgroundColor: 'rgba(63, 191, 82, 0.1)',
            fill: true,
            tension: 0.4,
          },
        ],
      };
    }
  }

  // Método para trocar a imagem ativa na galeria
  trocarImagem(index: number): void {
    this.imagemAtiva = index;
  }
}
