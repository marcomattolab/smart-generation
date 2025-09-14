import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-download-modal',
  templateUrl: './download-modal.html',
  styleUrls: ['./download-modal.scss'],
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DownloadModal {
  isOpen = input<boolean>(false);
  closeModal = output<void>();

  onClose() {
    this.closeModal.emit();
  }
}
