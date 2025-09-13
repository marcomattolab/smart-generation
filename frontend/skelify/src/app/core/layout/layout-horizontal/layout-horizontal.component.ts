import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Footer } from '../footer/footer';
import { Header } from '../header/header';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-layout-horizontal',
  imports: [CommonModule, Header, Footer, TranslateModule],
  templateUrl: './layout-horizontal.component.html',
  styleUrl: './layout-horizontal.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LayoutHorizontal {}
