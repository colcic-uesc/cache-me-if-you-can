import { Component, OnInit } from '@angular/core';
import { Carousel } from '../../components/carousel/carousel';
import { Product } from '../../domain/models/product';
import { ProductService } from '../../domain/services/product.service';

@Component({
  selector: 'app-home',
  imports: [Carousel],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService) {

  }
  ngOnInit(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
    })
  }


}

