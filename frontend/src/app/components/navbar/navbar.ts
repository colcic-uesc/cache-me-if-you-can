import { Component, OnInit } from '@angular/core';
import { UserService } from '../../domain/services/user.service';
import { Login } from '../login/login';
import { SearchBar } from '../search-bar/search-bar';
import { User } from '../../domain/models/user';

@Component({
  selector: 'app-navbar',
  imports: [SearchBar, Login],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})

export class Navbar implements OnInit {
  showLogin = false;
  user?: User;
  userInitial: string = '';
  constructor(private userService: UserService) {}
  categorias = [
    'Automoveis',
    'Eletrodomésticos',
    'Eletrônicos',
    'Medicamentos',
    'Perfumaria',
    'Vestuarios',
  ];
  ngOnInit(): void {
    this.userService.getUser().subscribe((user) => {
      this.user = user;
      if (user?.name) {
        this.userInitial = user.name.charAt(0).toUpperCase();
      }
    });
  }
}
