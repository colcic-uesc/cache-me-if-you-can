import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-search-bar',
  imports: [FormsModule, ButtonModule],
  templateUrl: './search-bar.html',
  styleUrl: './search-bar.scss'
})
export class SearchBar {
  query = '';

  constructor(private router: Router) {

  }

  search() {
    this.router.navigate(['search'], { queryParams: { query: this.query } })
  }

}
