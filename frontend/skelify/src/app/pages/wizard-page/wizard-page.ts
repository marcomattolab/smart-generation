import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { ProgressBar } from '../../components/progress-bar/progress-bar';
import { ProjectInfoStep } from '../../components/project-info-step/project-info-step';
import { TechStackStep } from '../../components/tech-stack-step/tech-stack-step';
import { InfrastructureStep } from '../../components/infrastructure-step/infrastructure-step';
import { ReviewStep } from '../../components/review-step/review-step';

@Component({
  selector: 'app-wizard-page',
  templateUrl: './wizard-page.html',
  styleUrls: ['./wizard-page.css'],
  imports: [
    CommonModule,
    ProgressBar,
    ProjectInfoStep,
    TechStackStep,
    InfrastructureStep,
    ReviewStep
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WizardPage {
  wizardState = inject(WizardStateService);
  isProjectInfoFormValid = signal(false);

  handleNextStep() {
    if (this.wizardState.isLastStep()) {
      this.wizardState.simulate();
    } else {
      this.wizardState.nextStep();
    }
  }

  previousStep() {
    this.wizardState.previousStep();
  }

}
