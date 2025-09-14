import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { WizardStateModel } from '../../models/page/wizard-state.model';

@Component({
  selector: 'app-infrastructure-step',
  templateUrl: './infrastructure-step.html',
  styleUrls: ['./infrastructure-step.scss'],
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfrastructureStep {
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

  isInfrastructureSelected(key: keyof WizardStateModel['infrastructure'], value: string): boolean {
    return this.wizardState.infrastructure()[key].includes(value);
  }

  updateInfrastructure(key: keyof WizardStateModel['infrastructure'], value: string) {
    this.wizardState.updateInfrastructure(key, value);
  }
}
