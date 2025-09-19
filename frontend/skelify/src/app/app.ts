import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LayoutHorizontal } from './core/layout/layout-horizontal/layout-horizontal.component';
import { TranslateService } from '@ngx-translate/core';
import { AppConstants } from './models/constant/app-constant';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [RouterOutlet, CommonModule, LayoutHorizontal],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  private readonly translate = inject(TranslateService);
  private readonly oidc = inject(OidcSecurityService);

  userData: any;
  isAuthenticated = signal(false);

  constructor() {
    const appLanguage = AppConstants.COMMON.DEFAULT_LANGUAGE
    this.translate.setDefaultLang(appLanguage);
    this.translate.use(appLanguage);

    this.oidc.checkAuth().subscribe((auth) => {
      this.isAuthenticated.set(auth.isAuthenticated);
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
}
