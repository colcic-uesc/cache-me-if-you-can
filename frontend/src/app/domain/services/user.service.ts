import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { User } from '../models/user';
import { BehaviorSubject, mergeMap, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user = new BehaviorSubject<User | undefined>(undefined);

  constructor(private http: HttpClient) { }

  login(email: string, password: string) {
    return this.http.post(environment.apiUrl + '/auth/login',
      { email, password }, { responseType: 'text' })
      .pipe(
        mergeMap((jwt) => {
          let headers = new HttpHeaders();
          headers = headers.set('Authorization', 'Bearer ' + jwt)
          return this.http.get<User>(environment.apiUrl + '/auth/user', { headers, responseType: 'json' });
        }),
        tap(user => {
          this.user.next(user);
        })
      );
  }

  getUser() {
    return this.user.asObservable();
  }
}
