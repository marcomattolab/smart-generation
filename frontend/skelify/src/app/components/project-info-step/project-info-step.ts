import { ChangeDetectionStrategy, Component, effect, inject, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { WizardStateService } from '../../services/wizard-state';
import { toSignal } from '@angular/core/rxjs-interop';
import { debounceTime } from 'rxjs/operators';
import { AppConstants } from '../../models/constant/app-constant';

@Component({
  selector: 'app-project-info-step',
  templateUrl: './project-info-step.html',
  styleUrls: ['./project-info-step.scss'],
  imports: [CommonModule, ReactiveFormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProjectInfoStep {
  private readonly fb = inject(FormBuilder);
  private readonly wizardState = inject(WizardStateService);
  isFormValid = output<boolean>();

  form = this.fb.group({
    projectName: ['', Validators.required],
    projectDescription: [''],
    organization: [AppConstants.COMMON.COMPANY_NAME],
    packageName: ['']
  });

  private formValue = toSignal(this.form.valueChanges.pipe(debounceTime(300)));
  private formStatus = toSignal(this.form.statusChanges);

  constructor() {
    this.form.patchValue(this.wizardState.projectInfo());

    effect(() => {
      const value = this.formValue();
      if (value) {
        this.wizardState.updateProjectInfo({
          projectName: value.projectName ?? '',
          projectDescription: value.projectDescription ?? '',
          organization: value.organization ?? '',
          packageName: value.packageName ?? ''
        });

        const organization = value.organization?.toLowerCase() || AppConstants.COMMON.COMPANY_NAME;
        const packageName = `ch.${organization}.${value.projectName?.toLowerCase().replace(/[^a-zA-Z0-9]/g, '')}`;
        this.form.get('packageName')?.setValue(packageName, { emitEvent: false });
      }
    });

    effect(() => {
      const status = this.formStatus();
      this.isFormValid.emit(status === 'VALID');
    });
  }
}
