import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ${projectName}BarchartModule } from './barchart/barchart.module';
import { ${projectName}DoughnutchartModule } from './doughnutchart/doughnutchart.module';
import { ${projectName}LinechartModule } from './linechart/linechart.module';
import { ${projectName}PiechartModule } from './piechart/piechart.module';
import { ${projectName}PolarareachartModule } from './polarareachart/polarareachart.module';
import { ${projectName}RadarchartModule } from './radarchart/radarchart.module';

@NgModule({
    imports: [
        ${projectName}BarchartModule,
        ${projectName}DoughnutchartModule,
        ${projectName}LinechartModule,
        ${projectName}PiechartModule,
        ${projectName}PolarareachartModule,
        ${projectName}RadarchartModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ${projectName}DashboardModule {}
