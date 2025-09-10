import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-review-step',
  templateUrl: './review-step.html',
  styleUrls: ['./review-step.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ReviewStepComponent {
  wizardState = inject(WizardStateService);
  generationCompleted = signal(false);
  isLoading = signal(false);

  generateProject() {
    this.generationCompleted.set(true);
    this.isLoading.set(true);
    setTimeout(() => {
      this.isLoading.set(false);
    }, 3000);
  }

  downloadProject() {
    const projectName = this.wizardState.projectInfo().projectName;
    alert(`🎉 Project "${projectName}" would be downloaded as a ZIP file with:\n\n` +
          `• Complete source code scaffolding\n` +
          `• Docker configuration files\n` +
          `• CI/CD pipeline configurations\n` +
          `• Database migration scripts\n` +
          `• Documentation and README files\n` +
          `• Security and authentication setup\n\n` +
          `This is a POC demo - in production, this would trigger the actual project generation and download.`);
  }
}
