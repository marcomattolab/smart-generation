import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { ProgressBarComponent } from '../progress-bar/progress-bar';
import { ProjectInfoStepComponent } from '../project-info-step/project-info-step';
import { TechStackStepComponent } from '../tech-stack-step/tech-stack-step';
import { InfrastructureStepComponent } from '../infrastructure-step/infrastructure-step';
import { ReviewStepComponent } from '../review-step/review-step';

@Component({
  selector: 'app-wizard',
  templateUrl: './wizard.html',
  styleUrls: ['./wizard.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    CommonModule,
    ProgressBarComponent,
    ProjectInfoStepComponent,
    TechStackStepComponent,
    InfrastructureStepComponent,
    ReviewStepComponent
  ]
})
export class WizardComponent {
  wizardState = inject(WizardStateService);

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
