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
    PREFIX_SONAR: '',
    PREFIX_DEPLOYMENT: 'prj-elca-',
    SKELETON_GENERATED: "assets/skeleton/generated-skeleton.zip",
    INITIAL_STATE: {
      currentStep: 1,
      currentSubstep: 1,
      domain: {
        entities: [
          { 
            name: 'User', 
            columns: [
              { name: 'id', type: 'number', required: true }, 
              { name: 'username', type: 'string', required: true }, 
              { name: 'email', type: 'string', required: true }
            ] 
          }
        ]
      },
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
            { name: "Martin MacFly", username: "martin.macfly@elca.ch", defaultBE: true, defaultFE: true }
          ] as Person[]
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
