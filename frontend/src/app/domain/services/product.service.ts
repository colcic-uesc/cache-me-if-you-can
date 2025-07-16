import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { ProductResponse } from '../models/product';
import { ProductDetailedResponse } from '../dto/product-detailed-response';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProducts(query?: string, categoryId?: number) {
    let params = new HttpParams();
    if (query) {
      params = params.append('query', query);
    }
    if(categoryId) {
      params = params.append('categoryId', categoryId);
    }
    return this.http.get<ProductResponse[]>(environment.apiUrl + '/products', { params });
  }

  getProductDetailed(id: number) {
    return this.http.get<ProductDetailedResponse>(environment.apiUrl + '/products/' + id);
  }
}
