
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
    maven: any;
    xray: {
      vulnerabilityScan: boolean;
      vulnerabilityThreshold: number;
      licenseScan: boolean;
    };
    sonarQube: any;
    deployment: {
      type: 'vm' | 'okd';
    };
  };
}
