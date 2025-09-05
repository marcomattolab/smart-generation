import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ${projectNameCamelCase}SharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
@NgModule({
    imports: [${projectNameCamelCase}SharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ${projectNameCamelCase}HomeModule {}
