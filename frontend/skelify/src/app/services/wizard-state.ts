import { Injectable, signal, computed, inject } from '@angular/core';
import { Person, PipelineStep, WizardStateModel } from "../models/page/wizard-state.model";
import { AppConstants } from "../models/constant/app-constant";
import { GenerationService } from './generation.service';
import { finalize } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WizardStateService {
  private readonly generationService = inject(GenerationService);
  private readonly state = signal(this.initialState);
  readonly steps = signal([
    { label: 'Project Info' },
    { label: 'Tech Stack' },
    { label: 'Infrastructure' },
    { label: 'Review' }
  ]);

  readonly pipelineSteps = signal([
    { id: 'bitbucket', name: 'Bitbucket' },
    { id: 'xray', name: 'Xray' },
    { id: 'sonarqube', name: 'SonarQube' },
    { id: 'deployment', name: 'Deployment' },
  ]);

  readonly isLoading = signal(false);
  readonly isGenerated = signal(false);

  // Selectors
  readonly currentStep = computed(() => this.state().currentStep);
  readonly currentSubstep = computed(() => this.state().currentSubstep);
  readonly projectInfo = computed(() => this.state().projectInfo);
  readonly techStack = computed(() => this.state().techStack);
  readonly infrastructure = computed(() => this.state().infrastructure);
  readonly isLastStep = computed(() => this.currentStep() === this.steps().length);

  readonly isPipelineStep = computed(() => this.currentStep() === 3);
  readonly isLastPipelineStep = computed(() => this.currentSubstep() === this.pipelineSteps().length);

  get initialState(): WizardStateModel {
    return AppConstants.WIZARD.INITIAL_STATE
  }

  // Actions
  nextStep() {
    this.state.update(state => {
      if (this.isPipelineStep()) {
        return this.isLastPipelineStep()
          ? { ...state, currentStep: state.currentStep + 1 }
          : { ...state, currentSubstep: state.currentSubstep + 1 };
      }
      return this.isLastStep()
        ? state
        : { ...state, currentStep: state.currentStep + 1 };
    });
  }

  previousStep() {
    this.state.update(state => {
      if (this.isPipelineStep()) {
        if (state.currentSubstep > 1) {
          return { ...state, currentSubstep: state.currentSubstep - 1 };
        }
        if (state.currentStep > 0) {
          return { ...state, currentStep: state.currentStep - 1 };
        }
        return state;
      }
      if (state.currentStep > 0) {
        return { ...state, currentStep: state.currentStep - 1 };
      }
      return state;
    });
  }


  updateSubstep(substepId: string) {
    const substepIndex = this.pipelineSteps().findIndex((i: PipelineStep) => i.id === substepId) + 1;
    // => console.log("substepIndex: "+substepIndex);
    this.state.update(state => ({...state, currentSubstep: substepIndex }));
  }

  goToStep(step: number) {
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

  updateBitbucket(bitbucket: Partial<WizardStateModel['infrastructure']['bitbucket']>) {
    this.state.update(state => ({
      ...state,
      infrastructure: { ...state.infrastructure, bitbucket: { ...state.infrastructure.bitbucket, ...bitbucket } }
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

  updateSonarQube(sonarQube: Partial<WizardStateModel['infrastructure']['sonarQube']>) {
    this.state.update(state => ({
      ...state,
      infrastructure: { ...state.infrastructure, sonarQube: { ...state.infrastructure.sonarQube, ...sonarQube } }
    }));
  }

  addPerson(person: Person) {
    this.state.update(state => ({
      ...state,
      infrastructure: {
        ...state.infrastructure,
        bitbucket: {
          ...state.infrastructure.bitbucket,
          reviewers: [...state.infrastructure.bitbucket.reviewers, person]
        }
      }
    }));
  }

  removePerson(index: number) {
    this.state.update(state => {
      const reviewers = [...state.infrastructure.bitbucket.reviewers];
      reviewers.splice(index, 1);
      return {
        ...state,
        infrastructure: {
          ...state.infrastructure,
          bitbucket: {
            ...state.infrastructure.bitbucket,
            reviewers
          }
        }
      };
    });
  }

  generateProject() {
    this.isLoading.set(true);
    this.generationService.generateProject(this.state())
      .pipe(
        finalize(() => {
          this.isLoading.set(false);
          this.isGenerated.set(true);
        })
      )
      .subscribe({
        next: (response) => console.log('Project generation successful:', response),
        error: (error) => console.error('Project generation failed:', error)
      });
  }

}
