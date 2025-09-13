import { inject, Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private readonly translateService= inject(TranslateService);

  setDefaultLanguage(lang: string): void {
    this.translateService.setDefaultLang(lang);
    this.translateService.use(lang);
  }

  changeLanguage(lang: string): void {
    this.translateService.use(lang);
  }
}
