import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Carousel } from '../../components/carousel/carousel';
import { ProductResponse } from '../../domain/models/product';
import { ProductService } from '../../domain/services/product.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [Carousel, RouterModule, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  products: ProductResponse[] = [];
  isLoading = false;

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  private loadProducts(): void {
    this.isLoading = true;

    this.productService.getProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar produtos:', error);
        this.products = [];
        this.isLoading = false;
      },
    });
  }

  goToProduct(id: number): void {
    this.router.navigate(['/product', id]);
  }
}

