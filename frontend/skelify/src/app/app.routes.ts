import { Routes } from '@angular/router';
import { WizardPage } from './pages/wizard-page/wizard-page';
import { UnauthorizedPage } from './pages/unauthorized-page/unauthorized-page';
import { LandingPage } from './pages/landing-page/landing-page';
import { TodoListPage } from './pages/todo-list-page/todo-list-page';
import { AboutPage } from './pages/about-page/about-page';
import { authGuard } from './shared/guards/auth.guard';
import { ProductListPage } from './pages/product-list-page/product-list-page.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'about', component: AboutPage },
  { path: 'home', component: LandingPage },
  { path: 'todo', component: TodoListPage, canActivate: [authGuard] },
  { path: 'product', component: ProductListPage, canActivate: [authGuard] },
  { path: 'wizard', component: WizardPage, canActivate: [authGuard] },
  { path: 'unauthorized', component: UnauthorizedPage }
];
