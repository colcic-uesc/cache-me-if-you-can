import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProducts() {
    return this.http.get<Product[]>(environment.apiUrl + '/products');
  }
  getProductById(id: number) {
    return this.http.get<Product>(environment.apiUrl + '/products/' + id);
  }
}
