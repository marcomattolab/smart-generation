import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AppConfigService {
  private http = inject(HttpClient);

  private appConfig: any;

  loadAppConfig() {
    return this.http.get('/assets/conf/appConfig.json')
      .toPromise()
      .then(data => {
        this.appConfig = data;
      });
  }

  getConfig() {
    return this.appConfig;
  }
}
