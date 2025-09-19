import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { environment } from '../../../environments/environment';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(OidcSecurityService);

  return from(authService.getAccessToken()).pipe(
    switchMap((token) => {
      console.log('Outgoing request', req.url, 'with token:', token);

      const isApiRequest = req.url.startsWith(environment.apiUrl);
      const isJsonRequest =
        req.responseType === 'json' ||
        req.headers.get('Accept')?.includes('application/json');

      if (token && isApiRequest) {
        req = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` },
        });
      }

      return next(req);
    })
  );
};
