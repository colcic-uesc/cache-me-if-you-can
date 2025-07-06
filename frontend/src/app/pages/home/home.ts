import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Carousel } from '../../components/carousel/carousel';
import { Product } from '../../domain/models/product';
import { ProductService } from '../../domain/services/product.service';

@Component({
  selector: 'app-home',
  imports: [Carousel, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService, private router: Router) {}
  ngOnInit(): void {
    this.productService.getProducts().subscribe((products) => {
      this.products = products;
    });
  }
  goToProduct(id: number) {
    this.router.navigate(['/product', id]);
  }
}
