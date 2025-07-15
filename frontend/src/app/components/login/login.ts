import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../domain/services/user.service';
import { User } from '../../domain/models/user';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { DividerModule } from 'primeng/divider';

@Component({
  selector: 'app-login',
  imports: [FormsModule, ButtonModule,
    InputTextModule,
    InputGroupModule,
    InputGroupAddonModule,
    DividerModule],
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
  register() {
  console.log('Redirecionar para o cadastro...');
}

}
