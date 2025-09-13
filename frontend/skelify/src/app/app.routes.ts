import { Routes } from '@angular/router';
import { autoLoginPartialRoutesGuard } from 'angular-auth-oidc-client';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'about',
    loadComponent: () => import('./pages/about-page/about-page').then(m => m.AboutPage)
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/landing-page/landing-page').then(m => m.LandingPage)
  },
  {
    path: 'todo',
    loadComponent: () => import('./pages/todo-list-page/todo-list-page').then(m => m.TodoListPage),
    canActivate: [autoLoginPartialRoutesGuard]
  },
  {
    path: 'product',
    loadComponent: () => import('./pages/product-list-page/product-list-page.component').then(m => m.ProductListPage),
    canActivate: [autoLoginPartialRoutesGuard]
  },
  {
    path: 'wizard',
    loadComponent: () => import('./pages/wizard-page/wizard-page').then(m => m.WizardPage),
    canActivate: [autoLoginPartialRoutesGuard]
  },
  {
    path: 'profile',
    loadComponent: () => import('./pages/profile-page/profile-page').then(m => m.ProfilePage),
    canActivate: [autoLoginPartialRoutesGuard]
  },
  {
    path: 'unauthorized',
    loadComponent: () => import('./pages/unauthorized-page/unauthorized-page').then(m => m.UnauthorizedPage)
  }
];
