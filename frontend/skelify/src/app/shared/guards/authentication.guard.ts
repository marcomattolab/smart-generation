import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

export const authenticationGuard: CanActivateFn = () => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  if (authService.isAuthenticated$) {
    // User is logged in, allow access to the route
    return true;
  } else {
    // User is NOT logged in, navigate to the login page
    return router.navigateByUrl('/login');
  }
};
