
export interface WizardStateModel {
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
    scm: {
      flow: 'gitflow' | 'trunk';
      teamRoles: string;
      defaultReviewers: string;
    };
    maven: {};
    xray: {
      vulnerabilityScan: boolean;
      vulnerabilityThreshold: number;
      licenseScan: boolean;
    };
    sonarQube: {
      qualityGateFE: number;
      qualityGateBE: number;
    };
    deployment: {
      type: 'vm' | 'okd';
    };
  };
}
