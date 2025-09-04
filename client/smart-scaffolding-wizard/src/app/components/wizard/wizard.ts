import { Component, OnInit } from '@angular/core';
import { WizardStateService } from '../../services/wizard-state';
import { CommonModule } from '@angular/common';
import { ProgressBarComponent } from '../progress-bar/progress-bar';
import { ProjectInfoStepComponent } from '../project-info-step/project-info-step';
import { PluginConfigStepComponent } from '../plugin-config-step/plugin-config-step';
import { ReviewStepComponent } from '../review-step/review-step';

@Component({
  selector: 'app-wizard',
  templateUrl: './wizard.html',
  styleUrls: ['./wizard.css'],
  standalone: true,
  imports: [CommonModule, ProgressBarComponent, ProjectInfoStepComponent, PluginConfigStepComponent, ReviewStepComponent]
})
export class WizardComponent implements OnInit {
  currentStep = 1;
  totalSteps = 1;

  constructor(private wizardState: WizardStateService) {}

  ngOnInit() {
    this.wizardState.currentStep$.subscribe(step => this.currentStep = step);
    this.wizardState.wizardSteps$.subscribe(steps => this.totalSteps = steps.length);
  }

  nextStep() {
    this.wizardState.nextStep();
  }

  previousStep() {
    this.wizardState.previousStep();
  }
}
