import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { ModalComponent } from '../modal/modal';

@Component({
  selector: 'app-review-step',
  templateUrl: './review-step.html',
  imports: [CommonModule, ModalComponent],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReviewStep {
  wizardState = inject(WizardStateService);
  isModalOpen = signal(false);

  downloadProject() {
    this.isModalOpen.set(true);
  }

  closeModal() {
    this.isModalOpen.set(false);
  }
}
