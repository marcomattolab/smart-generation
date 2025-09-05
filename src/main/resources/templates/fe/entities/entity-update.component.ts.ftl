import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ${iName} } from 'app/shared/model/${classNameLowerCase}.model';
import { ${entityName}Service } from './${classNameLowerCase}.service';
<#list imports as anImport>
${anImport}
</#list>

@Component({
    selector: 'jhi-${classNameLowerCase}-update',
    templateUrl: './${classNameLowerCase}-update.component.html'
})
export class ${entityName}UpdateComponent implements OnInit {
    ${classNameLowerCase}: ${iName};
    isSaving: boolean;

<#list properties as prop>
    ${prop}
</#list>

<#list dateFields as field>
    ${field.fieldName}: <#if field.isLocalDate>any<#else>string</#if>;
</#list>

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
<#list constructorParams as param>
        ${param},
</#list>
        private ${classNameLowerCase}Service: ${entityName}Service,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ${classNameLowerCase} }) => {
            this.${classNameLowerCase} = ${classNameLowerCase};
<#list dateFields as field>
    <#if !field.isLocalDate>
            this.${field.fieldName} = this.${classNameLowerCase}.${field.fieldName} != null ? this.${classNameLowerCase}.${field.fieldName}.format(DATE_TIME_FORMAT) : null;
    </#if>
</#list>
        });
<#list ngOnInitCode as line>
        ${line}
</#list>
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
<#list dateFields as field>
    <#if !field.isLocalDate>
        this.${classNameLowerCase}.${field.fieldName} = this.${field.fieldName} != null ? moment(this.${field.fieldName}, DATE_TIME_FORMAT) : null;
    </#if>
</#list>
        if (this.${classNameLowerCase}.id !== undefined) {
            this.subscribeToSaveResponse(this.${classNameLowerCase}Service.update(this.${classNameLowerCase}));
        } else {
            this.subscribeToSaveResponse(this.${classNameLowerCase}Service.create(this.${classNameLowerCase}));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<${iName}>>) {
        result.subscribe((res: HttpResponse<${iName}>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

<#list trackByMethods as method>
    ${method}
</#list>

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
