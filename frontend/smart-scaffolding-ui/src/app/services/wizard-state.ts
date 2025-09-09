import { Injectable, signal, computed } from '@angular/core';

export interface AppState {
  currentStep: number;
  projectInfo: {
    projectName: string;
    projectDescription: string;
    organization: string;
    packageName: string;
  };
  techStack: {
    backend: string;
    frontend: string;
    mobile: string;
    database: string;
  };
  infrastructure: {
    ci_cd: string[];
    containerization: string[];
    quality: string[];
    security: string[];
  };
}

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

  // Selectors
  readonly currentStep = computed(() => this._state().currentStep);
  readonly projectInfo = computed(() => this._state().projectInfo);
  readonly techStack = computed(() => this._state().techStack);
  readonly infrastructure = computed(() => this._state().infrastructure);

  // Actions
  nextStep() {
    this._state.update(state => ({ ...state, currentStep: state.currentStep + 1 }));
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
}
