import { Component, OnInit } from '@angular/core';
import { UserService } from '../../domain/services/user.service';
import { Login } from '../login/login';
import { SearchBar } from '../search-bar/search-bar';
import { User } from '../../domain/models/user';
import { RouterModule } from '@angular/router';
import { CategoryResponse } from '../../domain/models/category';
import { CategoryService } from '../../domain/services/category.service';

@Component({
  selector: 'app-navbar',
  imports: [SearchBar, Login, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar implements OnInit {
  showLogin = false;
  user?: User;
  userInitial: string = '';
  categories: CategoryResponse[] = [];
  openMenu = false;

  constructor(
    private userService: UserService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.userService.getUser().subscribe((user) => {
      this.user = user;
      if (user?.name) {
        this.userInitial = user.name.charAt(0).toUpperCase();
      }
    });
    this.categoryService.getAll().subscribe((categories) => {
      this.categories = categories.filter((category) => !category.parentId);
    });
  }

  logout() {
    this.userService.logout();
  }
}
