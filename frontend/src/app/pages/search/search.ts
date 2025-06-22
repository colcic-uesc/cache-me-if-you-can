import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../domain/services/product.service';
import { Product } from '../../domain/models/product';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { __param } from 'tslib';

@Component({
  selector: 'app-search',
  imports: [],
  templateUrl: './search.html',
  styleUrl: './search.scss'
})
export class Search implements OnInit {

  products: Product[] = [];
  query = '';

  constructor(private productService: ProductService, private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.query = params['query']
      this.productService.getProducts(this.query).subscribe(products => {
        this.products = products;
      })
    })
  }
}