import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { WizardStateService } from '../../services/wizard-state';
import { Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-project-info-step',
  templateUrl: './project-info-step.html',
  styleUrls: ['./project-info-step.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class ProjectInfoStepComponent implements OnInit, OnDestroy {
  private readonly fb = inject(FormBuilder);
  private readonly wizardState = inject(WizardStateService);
  private subscription: Subscription | undefined;

  form = this.fb.group({
    projectName: ['', Validators.required],
    projectDescription: [''],
    organization: ['ELCA'],
    packageName: ['']
  });

  ngOnInit() {
    this.form.patchValue(this.wizardState.projectInfo());
    this.subscription = this.form.valueChanges
      .pipe(debounceTime(300))
      .subscribe(value => {
         const sanitizedValue = {
            projectName: value.projectName ?? undefined,
            projectDescription: value.projectDescription ?? undefined,
            organization: value.organization ?? undefined,
            packageName: value.packageName ?? undefined
          };
          this.wizardState.updateProjectInfo(sanitizedValue);
      });

    this.form.get('projectName')?.valueChanges.subscribe(projectName => {
        const organization = this.form.get('organization')?.value?.toLowerCase() || 'elca';
        const packageName = `com.${organization}.${projectName?.toLowerCase().replace(/[^a-zA-Z0-9]/g, '')}`;
        this.form.get('packageName')?.setValue(packageName);
    });
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }
}
