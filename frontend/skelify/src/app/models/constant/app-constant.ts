import {Person} from '../page/wizard-state.model';

export const AppConstants = {
  COMMON: {
    COMPANY_NAME: 'ELCA',
    DEFAULT_LANGUAGE: 'en',
    SUPPORTED_LANGUAGES: ['en', 'it'],
    DEFAULT_DATE_LOCAL: 'en-US',
    DEFAULT_DATE_FORMAT: 'dd.MM.yyyy'
  },
  WIZARD: {
    SIMULATED_TIME: 3500,
    SKELETON_GENERATED: "assets/skeleton/generated-skeleton.zip",
    INITIAL_STATE: {
      currentStep: 1,
      currentSubstep: 1,
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
        database: 'postgresql',
        security: 'oauth2',
      },
      infrastructure: {
        bitbucket: {
          flow: 'gitflow',
          repoFE: '',
          repoBE: '',
          minReviewers: 1,
          reviewers: [
            { name:"Valentino Rossi", username:"valentino.rossi@elca.ch", defaultBE:true, defaultFE:false },
            { name:"Mario Bianchi", username:"mario.bianchi@elca.ch", defaultBE:false, defaultFE:true }
          ] as Person[],
          sourceBranch: {},
          targetBranch: {},
        },
        xray: {
          vulnerabilityScan: true,
          vulnerabilityThreshold: 'medium',
          licenseScan: true,
          checkOpensource: true,
          checkSBOM: false,
          reportOnSonar: true,
        },
        sonarQube: {
          enabled: true,
          qualityGateFE: 'Coverage > 90%',
          qualityGateBE: 'Coverage > 90%',
          sonarNameFE: '',
          sonarNameBE: ''
        },
        deployment: {
          type: 'okd',
          dev: {
            enabled: true,
            namespace: ''
          },
          test: {
            enabled: true,
            namespace: ''
          },
          acceptance: {
            enabled: false,
            namespace: ''
          }
        }
      }
    }
  }
};
