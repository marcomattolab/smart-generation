import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-review-step',
  templateUrl: './review-step.html',
  styleUrls: ['./review-step.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule]
})
export class ReviewStepComponent {
  wizardState = inject(WizardStateService);
}
