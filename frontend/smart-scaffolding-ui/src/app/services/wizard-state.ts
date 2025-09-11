import { Injectable, signal, computed } from '@angular/core';
import { AppState } from '../models/app-state.model';

const initialState: AppState = {
  currentStep: 1,
  projectInfo: {
    projectName: '',
    projectDescription: '',
    organization: 'ELCA',
    packageName: ''
  },
  techStack: {
    backend: '',
    frontend: '',
    mobile: '',
    database: ''
  },
  infrastructure: {
    ci_cd: ['gitlab-ci'],
    containerization: ['docker', 'docker-compose', 'nginx'],
    quality: ['sonarqube', 'eslint', 'unit-tests', 'integration-tests'],
    security: ['oauth2', 'jwt', 'cors', 'ssl']
  }
};

@Injectable({
  providedIn: 'root'
})
export class WizardStateService {
  private readonly _state = signal(initialState);
  readonly steps = signal([
    { label: 'Project Info' },
    { label: 'Tech Stack' },
    { label: 'Infrastructure' },
    { label: 'Review' }
  ]);

  // Selectors
  readonly currentStep = computed(() => this._state().currentStep);
  readonly projectInfo = computed(() => this._state().projectInfo);
  readonly techStack = computed(() => this._state().techStack);
  readonly infrastructure = computed(() => this._state().infrastructure);
  readonly isLastStep = computed(() => this.currentStep() === this.steps().length);

  // Actions
  nextStep() {
    if (!this.isLastStep()) {
      this._state.update(state => ({ ...state, currentStep: state.currentStep + 1 }));
    }
  }

  previousStep() {
    this._state.update(state => ({ ...state, currentStep: state.currentStep - 1 }));
  }

  updateProjectInfo(projectInfo: Partial<AppState['projectInfo']>) {
    this._state.update(state => ({ ...state, projectInfo: { ...state.projectInfo, ...projectInfo } }));
  }

  updateTechStack(techStack: Partial<AppState['techStack']>) {
    this._state.update(state => ({ ...state, techStack: { ...state.techStack, ...techStack } }));
  }

  updateInfrastructure(key: keyof AppState['infrastructure'], value: string) {
    this._state.update(state => {
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
    // Here you can replace the console.log with a call to your backend service
    console.log('Simulating project generation with the following state:', this._state());
  }
}
