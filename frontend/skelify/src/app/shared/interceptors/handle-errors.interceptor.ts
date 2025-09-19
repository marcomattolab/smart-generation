import { HttpErrorResponse, HttpEvent, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export const handleErrorsInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  // If method is not DELETE and header 'X-Exclude-Interceptor' exists
  if (req.method !== 'DELETE' && req.headers.has('X-Exclude-Interceptor')) {
    return next(req).pipe(
      catchError((err: HttpErrorResponse) => {
        router.navigate(['**']); // Navigate to not-found/error page
        const error = new Error(err?.message);
        return throwError(() => error);
      })
    ) as Observable<HttpEvent<unknown>>;
  } else {
    return next(req);
  }
};
