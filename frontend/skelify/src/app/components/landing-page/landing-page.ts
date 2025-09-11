import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.html',
  styleUrls: ['./landing-page.css'],
  standalone: true,
  imports: [CommonModule]
})
export class LandingPageComponent {
  constructor(private router: Router) {}

  navigateToWizard() {
    this.router.navigate(['/wizard']);
  }
}
