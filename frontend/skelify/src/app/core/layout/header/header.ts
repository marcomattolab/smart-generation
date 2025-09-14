import { ChangeDetectionStrategy, Component, signal, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { TranslateModule } from '@ngx-translate/core';
import { LanguageService } from '../../../services/language.service';
import { AppConstants } from '../../../models/constant/app-constant';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  templateUrl: './header.html',
  styleUrls: ['./header.scss'],
  imports: [CommonModule, RouterModule, TranslateModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Header implements OnInit {
  isMenuOpen = signal(false);
  isAuthenticated = signal(false);

  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly languageService = inject(LanguageService);
  
  constructor() {
    this.languageService.setDefaultLanguage(AppConstants.COMMON.DEFAULT_LANGUAGE);
  }

  ngOnInit() {
    this.oidcSecurityService.isAuthenticated$.subscribe(({ isAuthenticated }) => {
      this.isAuthenticated.set(isAuthenticated);
    });
  }

  toggleMenu() {
    this.isMenuOpen.update(value => !value);
  }
  
  closeMenu() {
    this.isMenuOpen.set(false);
  }

  login() {
    this.oidcSecurityService.authorize();
  }

  logout() {
    this.oidcSecurityService.logoff();
  }

}
