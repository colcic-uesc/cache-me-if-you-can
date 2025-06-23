import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Alerts } from './pages/alerts/alerts';
import { Search } from './pages/search/search';
import { ProductDetail } from './pages/product-detail/product-detail';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: Home,
  },
  {
    path: 'alerts',
    component: Alerts,
  },
  {
    path: 'search',
    component: Search,
  },
  {
    path: 'product/:id',
    component: ProductDetail,
  },
  {
    path: '**',
    redirectTo: 'home',
    pathMatch: 'full',
  },
];
