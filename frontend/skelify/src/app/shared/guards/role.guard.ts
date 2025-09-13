import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { map } from 'rxjs/operators';

export const RoleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const oidc = inject(OidcSecurityService);
  const router = inject(Router);
  const allowedRoles = route.data['roles'] as string[];

  return oidc.getUserData().pipe(
    map((user) => {
      const roles = user?.role || [];
      const allowed = roles.some((r: string) => allowedRoles.includes(r));
      if (!allowed) router.navigate(['/']);
      return allowed;
    })
  );
};