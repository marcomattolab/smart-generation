import { ChangeDetectionStrategy, Component, HostListener, inject, signal } from '@angular/core';
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
  styleUrls: ['./wizard-page.scss'],
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


  @HostListener('document:keydown.enter', ['$event'])
  onEnterKey(event: Event) {
    event.preventDefault();
    const isButtonDisabled = (this.wizardState.currentStep() === 1 && !this.isProjectInfoFormValid()) || this.wizardState.isLoading();
    if (!isButtonDisabled) {
      this.handleNextStep();
    }
  }

  @HostListener('document:keydown.escape', ['$event'])
  onEscapeKey(event: Event) {
    const keyboardEvent = event as KeyboardEvent;
    keyboardEvent.preventDefault();
    const isFirstStep = this.wizardState.currentStep() === 1;
    if (!isFirstStep && !this.wizardState.isLoading()) {
      this.previousStep();
    }
  }

  handleNextStep() {
    if (this.wizardState.isLastStep()) {
      this.wizardState.generateProject();
    } else {
      this.wizardState.nextStep();
    }
  }

  previousStep() {
    this.wizardState.previousStep();
  }

}
