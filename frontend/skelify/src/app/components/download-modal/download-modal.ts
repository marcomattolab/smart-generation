import { ChangeDetectionStrategy, Component, inject, input, output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppConstants } from '../../models/constant/app-constant';
import { WizardStateService } from '../../services/wizard-state';

@Component({
  selector: 'app-download-modal',
  templateUrl: './download-modal.html',
  styleUrls: ['./download-modal.scss'],
  imports: [CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DownloadModal {
  private readonly wizardState = inject(WizardStateService);
  private readonly includeProjectName = true;

  isOpen = input<boolean>(false);
  closeModal = output<void>();

  onClose() {
    this.closeModal.emit();
  }

  onDownload() {
    const projectName = this.wizardState.projectInfo().projectName;
    const fileUrl = AppConstants.WIZARD.SKELETON_GENERATED;

    const baseName = this.getFileBaseName(fileUrl);
    const fileName = this.includeProjectName ? `${baseName}-${projectName}.zip` : `${baseName}.zip`;

    this.downloadFile(fileUrl, fileName);

    this.onClose();
  }

  private getFileBaseName(path: string): string {
    const fileName = path.split('/').pop() || '';
    return fileName.replace(/\.zip$/i, '');
  }

  private downloadFile(url: string, fileName: string) {
    const link = document.createElement('a');
    link.href = url;
    link.download = fileName;
    link.click();
  }
}
