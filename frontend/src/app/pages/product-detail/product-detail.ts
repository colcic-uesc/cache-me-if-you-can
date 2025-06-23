import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../domain/services/product.service';
import { Product } from '../../domain/models/product';
import { ProductDetailedResponse } from '../../domain/dto/product-detailed-response';

@Component({
  selector: 'app-product-detail',
  imports: [],
  templateUrl: './product-detail.html',
  styleUrl: './product-detail.scss',
})
export class ProductDetail implements OnInit {
  id = 0;
  product!: ProductDetailedResponse;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      if (!params.has('id')) {
        throw new Error('Product ID not provided');
      }
      this.id = Number(params.get('id'));
      this.productService.getProductDetailed(this.id).subscribe((product) => {
        this.product = product;
      });
    });
  }
}
