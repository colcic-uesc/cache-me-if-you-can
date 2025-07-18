import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { AlertResponse } from '../dto/alert-response';
import { AlertRequest } from '../dto/alert-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  constructor(private http: HttpClient) {}

  getAlerts(): Observable<AlertResponse[]> {
    return this.http.get<AlertResponse[]>(environment.apiUrl + '/alerts');
  }
  createAlert(body: AlertRequest): Observable<AlertResponse> {
    return this.http.post<AlertResponse>(environment.apiUrl + '/alerts', body);
  }

  updateAlert(body: AlertRequest): Observable<AlertResponse> {
    return this.http.put<AlertResponse>(environment.apiUrl + '/alerts', body);
  }

  deleteAlert(productId: number): Observable<void> {
    return this.http.delete<void>(environment.apiUrl + `/alerts/${productId}`);
  }
  calculateDiscountPrice(originalPrice: number): number {
    return Math.round((originalPrice * 0.9) * 100) / 100;
  }
}

