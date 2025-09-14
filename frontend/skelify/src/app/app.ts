import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LayoutHorizontal } from './core/layout/layout-horizontal/layout-horizontal.component';
import { TranslateService } from '@ngx-translate/core';
import { AppConstants } from './models/constant/app-constant';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  imports: [RouterOutlet, CommonModule, LayoutHorizontal],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  private readonly translate = inject(TranslateService);

  constructor() {
    const appLanguage = AppConstants.COMMON.DEFAULT_LANGUAGE
    this.translate.setDefaultLang(appLanguage);
    this.translate.use(appLanguage);
  }

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
