import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Product } from '../models/product';
import { ProductDetailedResponse } from '../dto/product-detailed-response';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProducts(query?: string) {
    let params = new HttpParams();
    if (query) {
      params = params.append('query', query);
    }
    return this.http.get<Product[]>(environment.apiUrl + '/products', { params });
  }

  getProductDetailed(id: number) {
    return this.http.get<ProductDetailedResponse>(environment.apiUrl + '/products/' + id);
  }
}
