import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header';
import { WizardComponent } from './components/wizard/wizard';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, WizardComponent, CommonModule],
})
export class AppComponent {
  title = 'smart-scaffolding-wizard';
}
