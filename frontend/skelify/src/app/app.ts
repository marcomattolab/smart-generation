import { ChangeDetectionStrategy, Component, ElementRef, HostListener, inject, signal, ViewChild } from '@angular/core';
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

  @ViewChild('menu') menu!: ElementRef;

  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (!this.isMenuOpen()) {
      return;
    }
    if (!this.menu.nativeElement.contains(event.target)) {
      this.isMenuOpen.set(false);
    }
  }

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
