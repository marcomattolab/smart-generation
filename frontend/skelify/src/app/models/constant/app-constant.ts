import { WizardStateModel } from '../page/wizard-state.model';

export const AppConstants: {
  COMMON: {
    COMPANY_NAME: string;
    DEFAULT_LANGUAGE: string;
    SUPPORTED_LANGUAGES: string[];
    DEFAULT_DATE_LOCAL: string;
    DEFAULT_DATE_FORMAT: string;
  };
  WIZARD: {
    SIMULATED_TIME: number;
    INITIAL_STATE: WizardStateModel;
  }
} = {
  COMMON: {
    COMPANY_NAME: 'ELCA',
    DEFAULT_LANGUAGE: 'en',
    SUPPORTED_LANGUAGES: ['en', 'it'],
    DEFAULT_DATE_LOCAL: 'en-US',
    DEFAULT_DATE_FORMAT: 'dd.MM.yyyy'
  },
  WIZARD: {
    SIMULATED_TIME: 5000,
    INITIAL_STATE: {
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
        mobile: 'none',
        database: 'postgresql'
      },
      infrastructure: {
        scm: {
          flow: 'gitflow',
          teamRoles: '',
          defaultReviewers: ''
        },
        maven: {},
        xray: {
          vulnerabilityScan: true,
          vulnerabilityThreshold: 70,
          licenseScan: false
        },
        sonarQube: {
          qualityGateFE: 80,
          qualityGateBE: 80
        },
        deployment: {
          type: 'vm'
        }
      }
    }
  }
};