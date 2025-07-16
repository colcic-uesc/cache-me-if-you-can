import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { CategoryResponse } from '../models/category';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<CategoryResponse[]>(environment.apiUrl + '/categories');
  }
  findById(categoryId: number) {
    return this.http.get<CategoryResponse>(environment.apiUrl + '/categories/' + categoryId);
  }

}
