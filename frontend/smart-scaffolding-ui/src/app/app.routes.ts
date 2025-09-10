import { Routes } from '@angular/router';
import { WizardComponent } from './components/wizard/wizard';

export const routes: Routes = [
  { path: '', redirectTo: 'wizard', pathMatch: 'full' },
  { path: 'wizard', component: WizardComponent }
];
