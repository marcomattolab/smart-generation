import { ChangeDetectionStrategy, Component, inject, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { WizardStateService } from '../../services/wizard-state';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { debounceTime, startWith } from 'rxjs/operators';
import { AppConstants } from '../../models/constant/app-constant';

@Component({
  selector: 'app-project-info-step',
  templateUrl: './project-info-step.html',
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

  constructor() {
    this.form.patchValue(this.wizardState.projectInfo());

    this.form.statusChanges.pipe(
      startWith(this.form.status),
      takeUntilDestroyed()
    ).subscribe(status => {
      this.isFormValid.emit(status === 'VALID');
    });

    this.form.valueChanges
      .pipe(debounceTime(300), takeUntilDestroyed())
      .subscribe(value => {
        this.wizardState.updateProjectInfo({
          projectName: value.projectName ?? '',
          projectDescription: value.projectDescription ?? '',
          organization: value.organization ?? '',
          packageName: value.packageName ?? ''
        });
      });

    this.form.get('projectName')?.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe(projectName => {
        const organization = this.form.get('organization')?.value?.toLowerCase() || AppConstants.COMMON.COMPANY_NAME;
        const packageName = `ch.${organization}.${projectName?.toLowerCase().replace(/[^a-zA-Z0-9]/g, '')}`;
        this.form.get('packageName')?.setValue(packageName);
      });
  }
}
