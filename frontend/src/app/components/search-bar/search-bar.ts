import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../domain/services/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  imports: [FormsModule],
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
