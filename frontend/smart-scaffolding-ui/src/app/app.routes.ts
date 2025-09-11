import { Routes } from '@angular/router';
import { WizardComponent } from './components/wizard/wizard';
import { LandingPageComponent } from './components/landing-page/landing-page';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'wizard', component: WizardComponent, canActivate: [authGuard] },
  { path: 'unauthorized', component: UnauthorizedComponent }
];
