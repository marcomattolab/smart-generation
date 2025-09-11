import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [RouterOutlet, CommonModule, RouterLink],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  authService = inject(AuthService);
  isAuthenticated$ = this.authService.isAuthenticated$;
  isMenuOpen = signal(false);

  login() {
    this.authService.login();
  }

  logout() {
    this.authService.logout();
  }

  toggleMenu() {
    this.isMenuOpen.update(value => !value);
  }
}
