import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { map } from 'rxjs/operators';

export const authGuard: CanActivateFn = () => {
  const oidcSecurityService = inject(OidcSecurityService);
  //=> const router = inject(Router);

  return oidcSecurityService.isAuthenticated$.pipe(
    map(({ isAuthenticated }) => {
      if (isAuthenticated) {
        return true;
      }

      // TODO => Call Login in the service
      oidcSecurityService.authorize();
      return true;
      //=> return router.parseUrl('/unauthorized');
    })
  );
};
