import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService, AppState } from '../../services/wizard-state';

@Component({
  selector: 'app-infrastructure-step',
  templateUrl: './infrastructure-step.html',
  styleUrls: ['./infrastructure-step.css'],
  standalone: true,
  imports: [CommonModule]
})
export class InfrastructureStepComponent {
  wizardState = inject(WizardStateService);

  ciCdOptions = [
    { id: 'jenkins', label: 'Jenkins Pipeline' },
    { id: 'gitlab-ci', label: 'GitLab CI/CD' },
    { id: 'github-actions', label: 'GitHub Actions' },
    { id: 'azure-devops', label: 'Azure DevOps' }
  ];

  containerizationOptions = [
    { id: 'docker', label: 'Docker Containers' },
    { id: 'docker-compose', label: 'Docker Compose' },
    { id: 'kubernetes', label: 'Kubernetes Deployment' },
    { id: 'nginx', label: 'Nginx Reverse Proxy' }
  ];

  qualityOptions = [
    { id: 'sonarqube', label: 'SonarQube Code Quality' },
    { id: 'eslint', label: 'ESLint/Prettier' },
    { id: 'unit-tests', label: 'Unit Test Framework' },
    { id: 'integration-tests', label: 'Integration Tests' }
  ];

  securityOptions = [
    { id: 'oauth2', label: 'OAuth2 Authentication' },
    { id: 'jwt', label: 'JWT Token Management' },
    { id: 'cors', label: 'CORS Configuration' },
    { id: 'ssl', label: 'SSL/TLS Configuration' }
  ];

  isInfrastructureSelected(key: keyof AppState['infrastructure'], value: string): boolean {
    return this.wizardState.infrastructure()[key].includes(value);
  }

  updateInfrastructure(key: keyof AppState['infrastructure'], value: string) {
    this.wizardState.updateInfrastructure(key, value);
  }
}
