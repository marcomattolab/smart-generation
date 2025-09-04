import { platformBrowser } from '@angular/platform-browser';
import { App } from './app/app';

platformBrowser().bootstrapModule(App, {
  ngZoneEventCoalescing: true,
})
  .catch(err => console.error(err));
