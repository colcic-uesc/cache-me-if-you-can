import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, mergeMap, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user = new BehaviorSubject<User | undefined>(undefined);

  constructor(private http: HttpClient) {
    const jwt = localStorage.getItem('jwt');
    if (jwt) {
      this.getUserDetails().subscribe(user => {
        this.user.next(user);
      })
    }
  }

  login(email: string, password: string) {
    return this.http.post(environment.apiUrl + '/auth/login',
      { email, password }, { responseType: 'text' })
      .pipe(
        mergeMap((jwt) => {
          localStorage.setItem('jwt', jwt);
          return this.getUserDetails();
        }),
        tap(user => {
          this.user.next(user);
        })
      );
    }

    private getUserDetails() {
      return this.http.get<User>(environment.apiUrl + '/auth/user', { responseType: 'json' });
    }

    getUser() {
      return this.user.asObservable();
    }

    logout() {
      this.user.next(undefined);
      localStorage.removeItem('jwt');
  }
}
