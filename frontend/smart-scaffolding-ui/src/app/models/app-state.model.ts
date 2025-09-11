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
