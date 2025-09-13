import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class HandleErrorsInterceptor implements HttpInterceptor {
  constructor(private readonly route: Router) {}

  public intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (req.method !== 'DELETE' && req.headers.has('X-Exclude-Interceptor')) {
      return next.handle(req).pipe(
        catchError((err: HttpErrorResponse) => {
          this.route.navigate(['**']);
          const error = new Error(err?.message);
          return throwError(() => error);
        })
      ) as Observable<HttpEvent<unknown>>;
    } else {
      return next.handle(req);
    }
  }
}
