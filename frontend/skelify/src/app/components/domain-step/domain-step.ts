import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { WizardStateService } from '../../services/wizard-state';
import { Domain, Entity } from '../../models/page/wizard-state.model';

@Component({
  selector: 'app-domain-step',
  templateUrl: './domain-step.html',
  styleUrls: ['./domain-step.scss'],
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
})
export class DomainStepComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly wizardState = inject(WizardStateService);

  domainForm: FormGroup = this.fb.group({
    entities: this.fb.array([]),
  });

  ngOnInit() {
    const currentDomain = this.wizardState.domain();
    if (currentDomain && currentDomain.entities) {
      currentDomain.entities.forEach(entity => {
        this.addEntity(entity);
      });
    }

    this.domainForm.valueChanges.subscribe((value: Domain) => {
      this.wizardState.updateDomain(value);
    });
  }

  get entities(): FormArray {
    return this.domainForm.get('entities') as FormArray;
  }

  addEntity(entity?: Entity) {
    const entityGroup = this.fb.group({
      name: [entity ? entity.name : '', Validators.required],
      columns: this.fb.array([]),
    });

    if (entity && entity.columns) {
      entity.columns.forEach(column => {
        (entityGroup.get('columns') as FormArray).push(this.fb.group({
          name: [column.name, Validators.required],
          type: [column.type, Validators.required],
          required: [column.required],
        }));
      });
    }

    this.entities.push(entityGroup);
  }

  removeEntity(index: number) {
    this.entities.removeAt(index);
  }

  getColumns(entity: any): FormArray {
    return entity.get('columns') as FormArray;
  }

  addColumn(entityIndex: number) {
    const columns = this.getColumns(this.entities.at(entityIndex));
    columns.push(this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      required: [false],
    }));
  }

  removeColumn(entityIndex: number, columnIndex: number) {
    const columns = this.getColumns(this.entities.at(entityIndex));
    columns.removeAt(columnIndex);
  }
}