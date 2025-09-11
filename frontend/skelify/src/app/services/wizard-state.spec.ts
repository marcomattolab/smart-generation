import { TestBed } from '@angular/core/testing';
import { WizardStateService } from './wizard-state';

describe('WizardStateService', () => {
  let service: WizardStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WizardStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should have a default currentStep of 1', () => {
    expect(service.currentStep()).toBe(1);
  });

  it('should increment the currentStep when nextStep is called', () => {
    service.nextStep();
    expect(service.currentStep()).toBe(2);
  });

  it('should not increment the currentStep beyond the last step', () => {
    service.nextStep();
    service.nextStep();
    service.nextStep();
    service.nextStep();
    expect(service.currentStep()).toBe(4);
  });

  it('should decrement the currentStep when previousStep is called', () => {
    service.nextStep();
    service.previousStep();
    expect(service.currentStep()).toBe(1);
  });

  it('should update the project info', () => {
    const projectInfo = {
      projectName: 'test',
      projectDescription: 'test',
      organization: 'test',
      packageName: 'test'
    };
    service.updateProjectInfo(projectInfo);
    expect(service.projectInfo()).toEqual(projectInfo);
  });

  it('should update the tech stack', () => {
    const techStack = {
      backend: 'test',
      frontend: 'test',
      mobile: 'test',
      database: 'test'
    };
    service.updateTechStack(techStack);
    expect(service.techStack()).toEqual(techStack);
  });

  it('should update the infrastructure', () => {
    service.updateInfrastructure('ci_cd', 'jenkins');
    expect(service.infrastructure().ci_cd).toContain('jenkins');
  });
});
