import { NgModule } from '@angular/core';
import { ${projectNameCamelCase}SharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';
@NgModule({
    imports: [${projectNameCamelCase}SharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [${projectNameCamelCase}SharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ${projectNameCamelCase}SharedCommonModule {}
