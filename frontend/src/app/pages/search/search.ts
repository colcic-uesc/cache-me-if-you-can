import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryResponse } from '../../domain/models/category';
import { ProductResponse } from '../../domain/models/product';
import { CategoryService } from '../../domain/services/category.service';
import { ProductService } from '../../domain/services/product.service';

interface SortOption {
  label: string;
  value: string;
}

@Component({
  selector: 'app-search',
  imports: [CommonModule, FormsModule],
  templateUrl: './search.html',
  styleUrl: './search.scss',
})
export class Search implements OnInit {
  // Propriedades principais
  products: ProductResponse[] = [];
  query = '';
  isLoading = false;

  sortOptions: SortOption[] = [
    { label: 'Menor preço', value: 'price_asc' },
    { label: 'Maior preço', value: 'price_desc' },
    { label: 'Mais relevante', value: 'relevance' },
    { label: 'Mais recente', value: 'newest' },
  ];
  selectedSort: string = 'price_asc';

  favoriteProducts: Set<number> = new Set();

  // Propriedades do carrossel
  @ViewChild('carouselWrapper') carouselWrapper!: ElementRef;
  canScrollLeft = false;
  canScrollRight = true;
  category?: CategoryResponse;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.query = params['query'] || '';
      this.selectedSort = params['sort'] || 'price_asc';
      const categoryId = Number(params['categoryId']);
      this.loadCategory(categoryId);
      this.loadProducts(categoryId);
    });
  }

  private loadCategory(categoryId: number) {
    this.categoryService.findById(categoryId).subscribe((category) => {
      this.category = category;
    });
  }

  private loadProducts(categoryId: number): void {
    this.isLoading = true;

    this.productService.getProducts(this.query, categoryId).subscribe({
      next: (products) => {
        this.products = products;
        this.sortProducts();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar produtos:', error);
        this.products = [];
        this.isLoading = false;
      },
    });
  }

  // ===== MÉTODOS DE ORDENAÇÃO =====

  onSortChange(value: string): void {
    this.selectedSort = value;
    this.sortProducts();
    this.updateUrlParams();
  }

  private sortProducts(): void {
    switch (this.selectedSort) {
      case 'price_asc':
        this.products.sort((a, b) => a.bestPrice - b.bestPrice);
        break;
      case 'price_desc':
        this.products.sort((a, b) => b.bestPrice - a.bestPrice);
        break;
      case 'relevance':
        this.products.sort((a, b) => a.title.localeCompare(b.title));
        break;
    }
  }

  private updateUrlParams(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        query: this.query,
        sort: this.selectedSort,
      },
      queryParamsHandling: 'merge',
    });
  }

  // ===== MÉTODOS DE NAVEGAÇÃO =====

  navigateToProduct(productId: number): void {
    this.router.navigate(['/product', productId]);
  }
}
