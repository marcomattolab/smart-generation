import { Routes } from '@angular/router';
import { WizardPage } from './pages/wizard/wizard-page';
import { UnauthorizedPage } from './pages/unauthorized-page/unauthorized-page';
import { LandingPage } from './pages/landing-page/landing-page';
import { TodoListPage } from './pages/todo-list-page/todo-list-page';
//=>import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: LandingPage },
  { path: 'todo', component: TodoListPage },
  //=>{ path: 'wizard', component: WizardPage, canActivate: [authGuard] },
  { path: 'wizard', component: WizardPage },
  { path: 'unauthorized', component: UnauthorizedPage }
];
