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

  updateInfrastructure(key: keyof WizardStateModel['infrastructure'], value: string) {
    this.state.update(state => {
      const currentValues = state.infrastructure[key];
      const newValues = currentValues.includes(value)
        ? currentValues.filter(v => v !== value)
        : [...currentValues, value];
      return {
        ...state,
        infrastructure: {
          ...state.infrastructure,
          [key]: newValues
        }
      };
    });
  }

  simulate() {
    this.isLoading.set(true);
    console.log('Simulating project generation with the following state:', this.state());
    setTimeout(() => {
      this.isLoading.set(false);
      this.isGenerated.set(true);
    }, AppConstants.COMMON.SIMULATED_TIMEOUT);
  }

  get initialState(): WizardStateModel {
    return AppConstants.WIZARD.INITIAL_STATE
  }
}
