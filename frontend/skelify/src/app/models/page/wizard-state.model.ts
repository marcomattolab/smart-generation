
export interface Person {
  name: string;
  username: string;
  defaultFE: boolean;
  defaultBE: boolean;
}

export interface DeploymentEnv {
  enabled: boolean;
  namespace: string;
}

export interface WizardStateModel {
  currentStep: number;
  currentSubstep: number;
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
    security: string;
  };
  infrastructure: {
    bitbucket: {
      flow: string;
      repoFE?: string;
      repoBE?: string;
      minReviewers: number;
      reviewers: Person[];
    };
    xray: {
      vulnerabilityScan: boolean;
      vulnerabilityThreshold: string;
      licenseScan: boolean;
      checkOpensource: boolean;
      checkSBOM: boolean;
      reportOnSonar: boolean;
    };
    sonarQube: {
      enabled: boolean,
      qualityGateFE: string,
      qualityGateBE: string,
      sonarNameFE: string,
      sonarNameBE: string
    };
    deployment: {
      type: string;
      dev: DeploymentEnv;
      test: DeploymentEnv;
      acceptance: DeploymentEnv;
    };
  };
}

export interface PipelineStep {
  id: string;
  name: string;
}
