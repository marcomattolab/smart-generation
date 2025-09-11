import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppConfigService } from './app-config.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Todo } from '../models/todo';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
  private readonly http = inject(HttpClient);
  private readonly appConfigService = inject(AppConfigService);

  private readonly baseUrl = this.appConfigService.getConfig().baseUrl + '/api/todos';

  getTodos(): Observable<Todo[]> {
    //=> console.log("baseUrl: "+this.baseUrl);
    return this.http.get<Todo[]>(this.baseUrl).pipe(
      catchError(error => this.handleError(error))
    );
  }

  createTodo(todoData: Todo): Observable<Todo> {
    return this.http.post<Todo>(this.baseUrl, todoData).pipe(
      catchError(error => this.handleError(error))
    );
  }

  updateTodo(todoData: Todo): Observable<Todo> {
    return this.http.put<Todo>(`${this.baseUrl}/${todoData.id}`, todoData).pipe(
      catchError(error => this.handleError(error))
    );
  }

  deleteTodo(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('Some error occurred', error);
    return throwError(() => new Error(error.message || error));
  }
}
