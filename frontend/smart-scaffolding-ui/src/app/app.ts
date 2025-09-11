import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [RouterOutlet, CommonModule]
})
export class AppComponent {
  authService = inject(AuthService);
  isAuthenticated$ = this.authService.isAuthenticated$;

  login() {
    this.authService.login();
  }

  logout() {
    this.authService.logout();
  }
}
