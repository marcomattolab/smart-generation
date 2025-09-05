import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { ${projectNameCamelCase}SharedLibsModule, ${projectNameCamelCase}SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
@NgModule({
    imports: [${projectNameCamelCase}SharedLibsModule, ${projectNameCamelCase}SharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [${projectNameCamelCase}SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ${projectNameCamelCase}SharedModule {
    static forRoot() {
        return {
            ngModule: ${projectNameCamelCase}SharedModule
        };
    }
}
