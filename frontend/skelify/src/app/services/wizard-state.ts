import { Injectable, signal, computed } from '@angular/core';
import { WizardStateModel } from "../models/page/wizard-state.model";
import { AppConstants } from "../models/constant/app-constant";

@Injectable({
  providedIn: 'root'
})
export class WizardStateService {
  private readonly state = signal(this.initialState);
  readonly steps = signal([
    { label: 'Project Info' },
    { label: 'Tech Stack' },
    { label: 'Infrastructure' },
    { label: 'Review' }
  ]);
  readonly isLoading = signal(false);
  readonly isGenerated = signal(false);

  // Selectors
  readonly currentStep = computed(() => this.state().currentStep);
  readonly projectInfo = computed(() => this.state().projectInfo);
  readonly techStack = computed(() => this.state().techStack);
  readonly infrastructure = computed(() => this.state().infrastructure);
  readonly isLastStep = computed(() => this.currentStep() === this.steps().length);

  get initialState(): WizardStateModel {
    return AppConstants.WIZARD.INITIAL_STATE;
  }

  // Actions
  nextStep() {
    if (!this.isLastStep()) {
      this.state.update(state => ({ ...state, currentStep: state.currentStep + 1 }));
    }
  }

  previousStep() {
    this.state.update(state => ({ ...state, currentStep: state.currentStep - 1 }));
  }

  goToStep(step: number) {
    // Allow navigation only to previously visited steps
    if (step < this.currentStep()) {
      this.state.update(state => ({ ...state, currentStep: step }));
    }
  }

  updateProjectInfo(projectInfo: Partial<WizardStateModel['projectInfo']>) {
    this.state.update(state => ({ ...state, projectInfo: { ...state.projectInfo, ...projectInfo } }));
  }

  updateTechStack(techStack: Partial<WizardStateModel['techStack']>) {
    this.state.update(state => ({ ...state, techStack: { ...state.techStack, ...techStack } }));
  }

  updateScm(scm: Partial<WizardStateModel['infrastructure']['scm']>) {
    this.state.update(state => ({
      ...state,
      infrastructure: { ...state.infrastructure, scm: { ...state.infrastructure.scm, ...scm } }
    }));
  }

  updateXray(xray: Partial<WizardStateModel['infrastructure']['xray']>) {
    this.state.update(state => ({
      ...state,
      infrastructure: { ...state.infrastructure, xray: { ...state.infrastructure.xray, ...xray } }
    }));
  }

  updateDeployment(deployment: Partial<WizardStateModel['infrastructure']['deployment']>) {
    this.state.update(state => ({
      ...state,
      infrastructure: { ...state.infrastructure, deployment: { ...state.infrastructure.deployment, ...deployment } }
    }));
  }

  simulate() {
    this.isLoading.set(true);
    console.log('Simulating project generation with the following state:', this.state());

    // Simulate a delay
    setTimeout(() => {
      this.isLoading.set(false);
      this.isGenerated.set(true);
    }, AppConstants.WIZARD.SIMULATED_TIME);
  }

}
