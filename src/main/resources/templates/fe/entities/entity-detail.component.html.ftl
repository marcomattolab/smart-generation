<div class="row justify-content-center">
    <div class="col-8">
        <div *ngIf="${nometabella}">
            <h2><span jhiTranslate="${projectName}App.${nometabella}.detail.title">${Nometabella}</span> {{${nometabella}.id}}</h2>
            <hr>
            <jhi-alert-error></jhi-alert-error>
            <dl class="row-md jh-entity-details">
<#list columns as column>
    <#if !column.isId>
                <dt><span jhiTranslate="${projectName}App.${nometabella}.${column.name}">${column.splitted}</span></dt>
                <dd>
        <#if column.isBlob>
					<div *ngIf="${nometabella}.${column.name}">
                        <a (click)="openFile(${nometabella}.${column.name}ContentType, ${nometabella}.${column.name})" jhiTranslate="entity.action.open">open</a>
                        {{${nometabella}.${column.name}ContentType}}, {{byteSize(${nometabella}.${column.name})}}
                    </div>
        <#else>
                    <span>{{${nometabella}.${column.name}}}</span>
        </#if>
                </dd>
    </#if>
</#list>
<#list relations as relation>
    ${relation}
</#list>
            </dl>
            <button type="submit"
                    (click)="previousState()"
                    class="btn btn-info">
                <fa-icon [icon]="'arrow-left'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.back"> Back</span>
            </button>
            <button type="button"
                    [routerLink]="['/${nometabella}', ${nometabella}.id, 'edit']"
                    class="btn btn-primary">
                <fa-icon [icon]="'pencil-alt'"></fa-icon>&nbsp;<span jhiTranslate="entity.action.edit"> Edit</span>
            </button>
        </div>
    </div>
</div>
