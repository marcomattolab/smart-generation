import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { WizardStateModel } from '../models/page/wizard-state.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenerationService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'https://jsonplaceholder.typicode.com/posts';

  generateProject(state: WizardStateModel): Observable<any> {
    console.log('Generating project with the following state:', state);
    return this.http.post(this.apiUrl, state);
  }
}
