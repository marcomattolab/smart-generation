import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.html',
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProgressBar {
  wizardState = inject(WizardStateService);
  steps = this.wizardState.steps;
  currentStep = this.wizardState.currentStep;

  progressWidth = computed(() => {
    return ((this.currentStep() - 1) / (this.steps().length - 1)) * 100 + '%';
  });

  navigateToStep(step: number) {
    this.wizardState.goToStep(step);
  }
}
