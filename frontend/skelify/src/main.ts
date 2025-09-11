import { bootstrapApplication } from '@angular/platform-browser';
import { enableProdMode } from '@angular/core';
import { environment } from './environments/environment';
import { appConfig } from './app/app.config';
import { App } from './app/app';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
