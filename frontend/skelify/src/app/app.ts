import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LayoutHorizontal } from './core/layout/layout-horizontal/layout-horizontal.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [RouterOutlet, CommonModule, LayoutHorizontal],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  /*private oidc = inject(OidcSecurityService);
  isAuthenticated = false;
  userData: any;

  constructor() {
    this.oidc.checkAuth().subscribe((auth) => {
      this.isAuthenticated = auth.isAuthenticated;
      if (auth.isAuthenticated) {
        this.oidc.getUserData().subscribe((user) => (this.userData = user));
      }
    });
  }

  login() {
    this.oidc.authorize();
  }

  logout() {
    this.oidc.logoff();
  }
  */
}
