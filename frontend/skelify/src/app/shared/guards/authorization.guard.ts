import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { UserRoles } from '../../models/core/auth.model';

/**
 * This Guard works on the authorization side.
 * Receive from routing this "data.requiredRoles" parameter.
 *   ex) data: {requiredRoles: [ ADMIN, USER ] }
 *   ex) data: {requiredRoles: [ ADMIN ] }
 *
 * @param route  ActivatedRouteSnapshot
 * @returns CanActivateFn
 */
export const authorizationGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  // let requiredRoles: UserRoles[] = [ ADMIN, USER ];
  // => console.log(" roles$   ==> " + authService.roles$);
  // => console.log(" getRoles ==> " + authService.getRoles());
  const requiredRoles: UserRoles[] = route.data['requiredRoles'];
  // => console.log('requiredRoles (Guard) ==> ' + requiredRoles);

  return authService.roles$.pipe(
    map((roles: UserRoles[]) => {
      const hasRequiredRole = requiredRoles.some(role => roles?.includes(role));
      if (hasRequiredRole) {
        return true;
      } else {
        // Redirect to login or unauthorized page
        router.navigate(['/login']);
        return false;
      }
    })
  );
};
