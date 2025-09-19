import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { catchError } from 'rxjs/operators';
import { IProduct } from '../models/page/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl =  environment.apiUrl + '/product';

  getProducts(): Observable<IProduct[]> {
    console.log(`API baseUrl: ${this.baseUrl}`);
    return this.http.get<IProduct[]>(this.baseUrl).pipe(
      catchError(error => this.handleError(error))
    );
  }

  createProduct(productData: IProduct): Observable<IProduct> {
    return this.http.post<IProduct>(this.baseUrl, productData).pipe(
      catchError(error => this.handleError(error))
    );
  }

  updateProduct(productData: IProduct): Observable<IProduct> {
    return this.http.put<IProduct>(`${this.baseUrl}/${productData.id}`, productData).pipe(
      catchError(error => this.handleError(error))
    );
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('Some error occurred', error);
    return throwError(() => new Error(error.message || error));
  }
}
