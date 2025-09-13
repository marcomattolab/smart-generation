import {inject, Injectable} from '@angular/core';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {ADMIN, USER, UserRoles} from '../models/core/auth.model';
import {Observable, of} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly oidcSecurityService = inject(OidcSecurityService);

  readonly isAuthenticated$ = this.oidcSecurityService.isAuthenticated$;
  readonly userData$ = this.oidcSecurityService.userData$;

  login() {
    this.oidcSecurityService.authorize();
  }

  logout() {
    this.oidcSecurityService.logoff();
  }

  get roles$(): Observable<UserRoles[]> {
    // TODO => FIXME DEVELOP THIS
    // => return this._currentUser$.pipe(map(auth => auth?.roles));
    const roles: UserRoles[] = [USER,ADMIN]
    return of(roles);
  }
}
