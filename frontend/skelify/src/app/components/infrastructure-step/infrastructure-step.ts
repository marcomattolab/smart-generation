import { ChangeDetectionStrategy, Component, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { FormsModule } from '@angular/forms';
import { AppConstants } from '../../models/constant/app-constant';
import { Person } from '../../models/page/wizard-state.model';

@Component({
  selector: 'app-infrastructure-step',
  templateUrl: './infrastructure-step.html',
  styleUrls: ['./infrastructure-step.scss'],
  imports: [CommonModule, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfrastructureStep implements OnInit{
  wizardState = inject(WizardStateService);

  activeSubstep = computed(() => this.wizardState.pipelineSteps()[this.wizardState.currentSubstep()-1]?.id);
  pipelineSteps = computed(() => this.wizardState.pipelineSteps());

  // People management state
  people = computed(() => this.wizardState.infrastructure().bitbucket.reviewers);
 
  newPerson: Person = { name: '', username: '', defaultFE: false, defaultBE: false };

  // Coverage options
  qualityOptions: string[] = [
    'No Coverage',
    'Coverage > 10%',
    'Coverage > 20%',
    'Coverage > 30%',
    'Coverage > 40%',
    'Coverage > 50%',
    'Coverage > 60%',
    'Coverage > 70%',
    'Coverage > 80%',
    'Coverage > 90%',
    'Full Coverage'
  ];

  projectInfo = this.wizardState.projectInfo();

  sonarNameFE = computed(() => `${AppConstants.WIZARD.PREFIX_SONAR}${this.projectInfo.packageName}.fe`);
  sonarNameBE = computed(() => `${AppConstants.WIZARD.PREFIX_SONAR}${this.projectInfo.packageName}.be`);
  deploymentDevNamespace = computed(() =>`${AppConstants.WIZARD.PREFIX_DEPLOYMENT}${this.wizardState.projectInfo().projectName}-dev`);
  deploymentTestNamespace = computed(() =>`${AppConstants.WIZARD.PREFIX_DEPLOYMENT}${this.wizardState.projectInfo().projectName}-test`);
  deploymentAcceptanceNamespace = computed(() =>`${AppConstants.WIZARD.PREFIX_DEPLOYMENT}${this.wizardState.projectInfo().projectName}-acceptance`);
  
  ngOnInit(): void {
    this.wizardState.updateSonarQube({
      sonarNameFE: this.sonarNameFE(),
      sonarNameBE: this.sonarNameBE(),
    });
    this.wizardState.updateDeployment({
      dev: { ...this.wizardState.infrastructure().deployment.dev, namespace: this.deploymentDevNamespace() },
      test: { ...this.wizardState.infrastructure().deployment.test, namespace: this.deploymentTestNamespace() },
      acceptance: { ...this.wizardState.infrastructure().deployment.acceptance, namespace: this.deploymentAcceptanceNamespace() },
    });
  }

  setActiveSubstep(substepId: string) {
    this.wizardState.updateSubstep(substepId);
  }

  onBitbucketFlowChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateBitbucket({ flow: target.value as 'gitflow' | 'trunk' });
  }

  onRepoFEChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateBitbucket({ repoFE: target.value });
  }

  onRepoBEChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateBitbucket({ repoBE: target.value });
  }

  addPerson() {
    debugger;
    if (!this.newPerson.name.trim() || !this.newPerson.username.trim()) {
      return;
    }
    const exists = this.people().some(p => p.username === this.newPerson.username);
    if (exists) {
      alert('Person Already Added');
      return;
    }

    this.wizardState.addPerson({ ...this.newPerson });
    this.newPerson = { name: '', username: '', defaultFE: false, defaultBE: false };
  }

  onMinReviewersChange(event: any) {
    this.wizardState.updateBitbucket({ minReviewers: Number(event.target.value) });
  }

  removePerson(index: number) {
    this.wizardState.removePerson(index);
  }

  onDeploymentTypeChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateDeployment({ type: target.value as 'okd' | 'vm'});
  }

  onQualityGateFEChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateSonarQube({ qualityGateFE: target.value });
  }

  onQualityGateBEChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateSonarQube({ qualityGateBE: target.value });
  }

  onVulnerabilityScanChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ vulnerabilityScan: target.checked });
  }

  onCheckOpensourceChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ checkOpensource: target.value === 'yes' });
  }

  onCheckSBOMChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ checkSBOM: target.checked });
  }

  onReportOnSonarChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateXray({ reportOnSonar: target.checked });
  }

  onVulnerabilityThresholdChange(event: Event) {
    const target = event.target as HTMLSelectElement;
    this.wizardState.updateXray({ vulnerabilityThreshold: target.value as 'medium' | 'high' | 'critical' });
  }

  onSonarNameFEChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateSonarQube({ sonarNameFE: target.value });
  }

  onSonarNameBEChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateSonarQube({ sonarNameBE: target.value });
  }

  onEnableSonarChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateSonarQube({ enabled: target.checked });
  }

  onNamespaceChange(env: 'dev' | 'test' | 'acceptance', event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateDeployment({
      ...this.wizardState.infrastructure().deployment,
      [env]: {
        ...this.wizardState.infrastructure().deployment[env],
        namespace: target.value
      }
    });
  }

  onDeploymentEnabledChange(env: 'dev' | 'test' | 'acceptance', event: Event) {
    const target = event.target as HTMLInputElement;
    this.wizardState.updateDeployment({
      ...this.wizardState.infrastructure().deployment,
      [env]: {
        ...this.wizardState.infrastructure().deployment[env],
        enabled: target.checked
      }
    });
  }

}
