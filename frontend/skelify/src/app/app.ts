import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  HostListener,
  inject,
  OnInit,
  signal,
  ViewChild
} from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
// => import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';
import { LoginResponse, OidcSecurityService } from 'angular-auth-oidc-client';
import { LayoutHorizontal } from './core/layout/layout-horizontal/layout-horizontal.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [RouterOutlet, CommonModule, LayoutHorizontal],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  // authService = inject(AuthService); //FIXME => DEVELOP THIS SERVICE
  // isAuthenticated$ = this.authService.isAuthenticated$;


  ngOnInit() {
    this.oidcSecurityService
      .checkAuth()
      .subscribe((loginResponse: LoginResponse) => {
        const { isAuthenticated, userData, accessToken, idToken, configId } =
          loginResponse;
          console.log("### isAuthenticated "+isAuthenticated + " accessToken: "+accessToken);
      });
  }

  login() {
    this.oidcSecurityService.authorize();
  }

  logout() {
    this.oidcSecurityService
      .logoff()
      .subscribe((result) => console.log(result));
  }

  /**
   *   login() {
   *     this.authService.login();
   *   }
   *
   *   logout() {
   *     this.authService.logout();
   *   }
   */

}
