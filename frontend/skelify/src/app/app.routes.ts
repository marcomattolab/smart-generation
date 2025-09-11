import { Routes } from '@angular/router';
import { WizardPage } from './pages/wizard-page/wizard-page';
import { UnauthorizedPage } from './pages/unauthorized-page/unauthorized-page';
import { LandingPage } from './pages/landing-page/landing-page';
import { TodoListPage } from './pages/todo-list-page/todo-list-page';
import { AboutPage } from './pages/about-page/about-page';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: LandingPage },
  { path: 'about', component: AboutPage },
  { path: 'wizard', component: WizardPage },
  { path: 'todo', component: TodoListPage, canActivate: [authGuard]},
  { path: 'unauthorized', component: UnauthorizedPage }
];
