import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Alerts } from './pages/alerts/alerts';
import { Search } from './pages/search/search';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: Home
    },
    {
        path: 'alerts',
        component: Alerts
    },
    {
        path: 'search',
        component: Search
    },
    {
        path: '**',
        redirectTo: 'home',
        pathMatch: 'full'
    }
];
