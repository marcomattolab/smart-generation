import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { catchError } from 'rxjs/operators';
import { ITodo } from '../models/page/todo.model';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl =  environment.apiUrl + '/todo';

  getTodos(): Observable<ITodo[]> {
    console.log(`API baseUrl: ${this.baseUrl}`);
    return this.http.get<ITodo[]>(this.baseUrl).pipe(
      catchError(error => this.handleError(error))
    );
  }

  createTodo(todoData: ITodo): Observable<ITodo> {
    return this.http.post<ITodo>(this.baseUrl, todoData).pipe(
      catchError(error => this.handleError(error))
    );
  }

  updateTodo(todoData: ITodo): Observable<ITodo> {
    return this.http.put<ITodo>(`${this.baseUrl}/${todoData.id}`, todoData).pipe(
      catchError(error => this.handleError(error))
    );
  }

  deleteTodo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('Some error occurred', error);
    return throwError(() => new Error(error.message || error));
  }
}
