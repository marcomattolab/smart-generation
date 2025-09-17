export const AppConstants = {
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
        ci_cd: ['gitlab-ci'],
        containerization: ['docker', 'docker-compose', 'nginx'],
        quality: ['sonarqube', 'eslint', 'unit-tests', 'integration-tests'],
        security: ['oauth2', 'jwt']
      }
    }
  }
};