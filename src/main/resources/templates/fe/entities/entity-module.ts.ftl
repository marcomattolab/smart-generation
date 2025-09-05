import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ${projectNameCamelCase}SharedModule } from 'app/shared';
import {
    ${entityName}Component,
    ${entityName}DetailComponent,
    ${entityName}UpdateComponent,
    ${entityName}DeletePopupComponent,
    ${entityName}DeleteDialogComponent,
    ${classNameLowerCase}Route,
    ${classNameLowerCase}PopupRoute
} from './';

const ENTITY_STATES = [...${classNameLowerCase}Route, ...${classNameLowerCase}PopupRoute];
@NgModule({
    imports: [${projectNameCamelCase}SharedModule, FormsModule, ReactiveFormsModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ${entityName}Component,
        ${entityName}DetailComponent,
        ${entityName}UpdateComponent,
        ${entityName}DeleteDialogComponent,
        ${entityName}DeletePopupComponent
    ],
    entryComponents: [${entityName}Component, ${entityName}UpdateComponent, ${entityName}DeleteDialogComponent, ${entityName}DeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class ${projectNameCamelCase}${entityName}Module {}
