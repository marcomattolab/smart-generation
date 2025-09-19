import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { map } from 'rxjs/operators';

/**
 * This Guard works on the authorization side.
 * Receive from routing this "data.requiredRoles" parameter.
 *   ex) data: {roles: [ ADMIN, USER ] }
 *   ex) data: {roles: [ ADMIN ] }
 *
 * @param route  ActivatedRouteSnapshot
 * @returns CanActivateFn
 */
export const RoleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const oidc = inject(OidcSecurityService);
  const router = inject(Router);
  const allowedRoles = route.data['roles'] as string[];

  return oidc.getUserData().pipe(
    map((user: any) => {
      console.log("Json => " + JSON.stringify(user));

      // Keycloak roles
      const realmRoles = user?.realm_access?.roles || [];
      const clientId ='skelify';
      const clientRoles = user?.resource_access?.[clientId]?.roles || [];
      const roles = [...realmRoles, ...clientRoles];

      const allowed = roles.some((r: string) => allowedRoles.includes(r));
      console.log('allowed =>', allowed, ' roles:', roles, ' allowedRoles:', allowedRoles);

      if (!allowed) router.navigate(['/']);
      return allowed;
    })
  );

};
