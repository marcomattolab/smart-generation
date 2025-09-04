import { Component, signal } from '@angular/core';
import { HeaderComponent } from './components/header/header';
import { WizardComponent } from './components/wizard/wizard';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [HeaderComponent, WizardComponent],
  standalone: true,
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('smart-scaffolding-wizard');
}
