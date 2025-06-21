import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../domain/services/user.service';
import { User } from '../../domain/models/user';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  email = 'taih.marques@uesc.br';
  password = '123';

  loading = false;

  @Output()
  close = new EventEmitter();

  constructor(private userService: UserService) { }

  login() {
    this.loading = true;
    this.userService.login(this.email, this.password).subscribe({
      next: () => {
        this.loading = false;
        this.close.emit();
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
