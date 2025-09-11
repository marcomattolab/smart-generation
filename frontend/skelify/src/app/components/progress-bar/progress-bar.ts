import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.html',
  styleUrls: ['./progress-bar.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule]
})
export class ProgressBarComponent {
  wizardState = inject(WizardStateService);
  steps = this.wizardState.steps;
  currentStep = this.wizardState.currentStep;

  progressWidth = computed(() => {
    return ((this.currentStep() - 1) / (this.steps().length - 1)) * 100 + '%';
  });
}
