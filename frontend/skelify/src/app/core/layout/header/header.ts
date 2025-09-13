import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, ElementRef, HostListener, inject, signal, ViewChild } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppConstants } from '../../../models/constant/app-constant';
import { TranslateModule } from '@ngx-translate/core';
import { LanguageService } from '../../../services/language.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.html',
  styleUrl: './header.css',
  imports: [CommonModule, RouterModule, TranslateModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Header {
  isMenuOpen = signal(false);
  @ViewChild('menu') menu!: ElementRef;

  private readonly languageService = inject(LanguageService);

  constructor() {
    this.languageService.setDefaultLanguage(AppConstants.COMMON.DEFAULT_LANGUAGE);
  }

  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (!this.isMenuOpen()) {
      return;
    }
    if (!this.menu.nativeElement.contains(event.target)) {
      this.isMenuOpen.set(false);
    }
  }

  toggleMenu() {
    this.isMenuOpen.update(value => !value);
  }
}
