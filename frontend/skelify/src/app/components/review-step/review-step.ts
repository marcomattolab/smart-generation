import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-review-step',
  templateUrl: './review-step.html',
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReviewStep {
  wizardState = inject(WizardStateService);

  downloadProject() {
    const projectName = this.wizardState.projectInfo().projectName;
    alert(`🎉 Project "${projectName}" would be downloaded as a ZIP file with:\n\n` +
      `• Complete source code skeleton\n` +
      `• Docker configuration files\n` +
      `• CI/CD pipeline configurations\n` +
      `• Database migration scripts\n` +
      `• Documentation and README files\n` +
      `• Security and authentication setup\n\n` +
      `This is a POC demo - in production, this would trigger the actual project generation and download.`);
  }
}
