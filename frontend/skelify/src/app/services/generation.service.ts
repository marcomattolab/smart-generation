import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { WizardStateModel } from '../models/page/wizard-state.model';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GenerationService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl =  environment.apiUrl + '/generate';

  generateProject(state: WizardStateModel): Observable<WizardStateModel> {
    console.log('Generating project with the following state:', state);
    return this.http.post<WizardStateModel>(`${this.baseUrl}`, state).pipe(
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: any): Observable<never> {
    console.error('Some error occurred', error);
    return throwError(() => new Error(error.message || error));
  }
}
