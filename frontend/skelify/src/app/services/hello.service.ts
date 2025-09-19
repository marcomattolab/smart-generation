import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { catchError } from 'rxjs/operators';
import { IHello } from '../models/page/hello.model';

@Injectable({
  providedIn: 'root'
})
export class HelloService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl =  environment.apiUrl + '/hello';

  getHello(): Observable<IHello> {
    console.log(`API baseUrl: ${this.baseUrl}`);
    return this.http.get<IHello>(this.baseUrl).pipe(
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('Some error occurred', error);
    return throwError(() => new Error(error.message || error));
  }
}
