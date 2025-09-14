import { ChangeDetectionStrategy, Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-unauthorized-page',
  templateUrl: './unauthorized-page.html',
  styleUrls: ['./unauthorized-page.scss'],
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UnauthorizedPage {}
