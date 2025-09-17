import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-infrastructure-step',
  templateUrl: './infrastructure-step.html',
  styleUrls: ['./infrastructure-step.scss'],
  imports: [CommonModule, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfrastructureStep {
  wizardState = inject(WizardStateService);

  pipelineSteps = [
    { id: 'scm', name: 'Checkout SCM' },
    { id: 'maven', name: 'Maven' },
    { id: 'xray', name: 'Xray' },
    { id: 'sonarqube', name: 'SonarQube' },
    { id: 'deployment', name: 'Deployment' },
  ];

  activeStep = signal('scm');

  setActiveStep(stepId: string) {
    this.activeStep.set(stepId);
  }

  onScmFlowChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateScm({ flow: target.value as 'gitflow' | 'trunk' });
  }

  onTeamRolesChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateScm({ teamRoles: target.value });
  }

  onDefaultReviewersChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateScm({ defaultReviewers: target.value });
  }

  onVulnerabilityScanChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ vulnerabilityScan: target.checked });
  }

  onVulnerabilityThresholdChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ vulnerabilityThreshold: +target.value });
  }

  onLicenseScanChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ licenseScan: target.checked });
  }

  onDeploymentTypeChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateDeployment({ type: target.value as 'vm' | 'okd' });
  }

  onQualityGateFEChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateSonarQube({ qualityGateFE: +target.value });
  }

  onQualityGateBEChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateSonarQube({ qualityGateBE: +target.value });
  }
}
