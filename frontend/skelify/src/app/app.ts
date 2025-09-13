import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LayoutHorizontal } from './core/layout/layout-horizontal/layout-horizontal.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  imports: [RouterOutlet, CommonModule, LayoutHorizontal],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
}
