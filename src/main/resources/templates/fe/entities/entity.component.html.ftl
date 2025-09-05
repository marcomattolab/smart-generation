<div>
    <h2 id="page-heading">
        <span jhiTranslate="${projectName}App.${nometabella}.home.title">${Nometabella}s</span>
        <button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-${nometabella}" [routerLink]="['/${nometabella}/new']">
            <fa-icon [icon]="'plus'"></fa-icon>
            <span  jhiTranslate="${projectName}App.${nometabella}.home.createLabel">
            Create new ${Nometabella}
            </span>
        </button>
    </h2>
    <jhi-alert></jhi-alert>
    <br/>
    <ngb-accordion #acc="ngbAccordion" activeIds="filtriPanel">
        <ngb-panel id="filtriPanel">
            <ng-template ngbPanelTitle>
                <span jhiTranslate="global.search.filtersLabel">Filtri</span>
            </ng-template>
            <ng-template ngbPanelContent>
                <form (ngSubmit)="cerca()">
                    <!-- [filtri di ricerca] -->
                    <div class="row" [formGroup]="myGroup">
<#list columns as column>
    <#if column.isString && !column.isEnum>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label jhiTranslate="${projectName}App.${nometabella}.${column.name}">${column.splitted}</label>
                                <input formControlName="${column.name}" type="text" class="form-control" />
                            </div>
                        </div>
    <#elseif column.isString && column.isEnum>
                        <div class="col-md-4">
                              <div class="form-group">
                                   <label jhiTranslate="${projectName}App.${nometabella}.${column.name}">${column.ColumnName}</label>
                                   <select class="form-control" formControlName="${column.name}" >
                                        <option></option>
    <#list column.enumValues as vEnum>
                                        <option value="${vEnum.value}">${'${'}${projectName}App.${vEnum.name}.${vEnum.value}${'}'} | translate}}</option>
    </#list>
                                    </select>
                               </div>
			               </div>
    <#elseif column.isDate>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label jhiTranslate="${projectName}App.${nometabella}.${column.name}">${column.splitted}(Da / A)</label>
                                <div class="row">
                                    <div class="col-md-6 col-12">
                                        <div class="input-group form-group">
                                            <input formControlName="${column.name}Da" type="date" class="form-control" />
                                        </div>
                                    </div>
                                    <div class="col-md-6 col-12">
                                        <div class="input-group form-group">
                                            <input formControlName="${column.name}A" type="date" class="form-control" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
    <#elseif column.isNumeric>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label jhiTranslate="${projectName}App.${nometabella}.${column.name}">${column.splitted} (Da / A)</label>
                                <div class="row">
                                    <div class="col-md-6 col-12">
                                        <div class="input-group form-group">
                                            <input formControlName="${column.name}Da" type="number" class="form-control" />
                                        </div>
                                    </div>
                                    <div class="col-md-6 col-12">
                                        <div class="input-group form-group">
                                            <input formControlName="${column.name}A" type="number" class="form-control" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
    </#if>
</#list>
<#list relations as relation>
    ${relation}
</#list>
                    </div>
				   <!-- [/filtri di ricerca] -->

                    <div class="form-group float-left">
						<button style="font-size:16px;" type="button" (click)="exportFile('CSV')">CSV <i class="fa fa-file-o"></i></button>
						<button style="font-size:16px;" type="button" (click)="exportFile('PDF')">PDF <i class="fa fa-file-pdf-o"></i></button>
						<button style="font-size:16px;" type="button" (click)="exportFile('XLS')">XLS <i class="fa fa-file-excel-o"></i></button>
						<button style="font-size:16px;" type="button" (click)="exportFile('DOC')">DOC <i class="fa fa-file-word-o"></i></button>
                    </div>

                    <div class="form-group float-right">
                        <button class="btn btn-link" type="button" (click)="resetFiltri()">
                            <span jhiTranslate="global.search.clearfiltersLabel">Pulisci Filtri</span>
                        </button>
                        <button class="btn btn-primary" type="submit">
                            <fa-icon [icon]="'search'"></fa-icon>
                            <span jhiTranslate="global.search.buttonLabel">Cerca</span>
                        </button>
                    </div>
                </form>
            </ng-template>
        </ngb-panel>
    </ngb-accordion>
    <div class="table-responsive" *ngIf="${nometabella}s">
        <table class="table table-striped">
            <thead>
            <tr jhiSort [(predicate)]="predicate" [(ascending)]="reverse" [callback]="transition.bind(this)">
            <th jhiSortBy="id"><span jhiTranslate="global.field.id">ID</span> <fa-icon [icon]="'sort'"></fa-icon></th>
<#list columns as column>
    <#if !column.isId>
			<th jhiSortBy="${column.name}"><span jhiTranslate="${projectName}App.${nometabella}.${column.name}">${column.splitted}</span><fa-icon [icon]="'sort'"></fa-icon></th>
    </#if>
</#list>
<#list relationsTh as relation>
    ${relation}
</#list>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr *ngFor="let ${nometabella} of ${nometabella}s ;trackBy: trackId">
                <td><a [routerLink]="['/${nometabella}', ${nometabella}.id, 'view' ]">{{${nometabella}.id}}</a></td>
<#list columns as column>
    <#if !column.isId>
        <#if column.isDate>
			<td>{{${nometabella}.${column.name} | ${column.filterDate}}}</td>
        <#elseif column.isBlob>
			<td>
			    <a *ngIf="${nometabella}.${column.name}" (click)="openFile(${nometabella}.${column.name}ContentType, ${nometabella}.${column.name})" jhiTranslate="entity.action.open">open</a>
			        <img [src]="'data:' + ${nometabella}.${column.name}ContentType + ';base64,' + ${nometabella}.${column.name}" style="max-height: 30px;" alt="${column.name} image"/>
			    </a>
			    <span *ngIf="${nometabella}.${column.name}">{{${nometabella}.${column.name}ContentType}}, {{byteSize(${nometabella}.${column.name})}}</span>
			</td>
        <#else>
			<td>{{${nometabella}.${column.name}}}</td>
        </#if>
    </#if>
</#list>
<#list relationsTd as relation>
    ${relation}
</#list>
                <td class="text-right">
                    <div class="btn-group flex-btn-group-container">
                        <button type="submit"
                                [routerLink]="['/${nometabella}', ${nometabella}.id, 'view' ]"
                                class="btn btn-info btn-sm">
                            <fa-icon [icon]="'eye'"></fa-icon>
                            <span class="d-none d-md-inline" jhiTranslate="entity.action.view">View</span>
                        </button>
                        <button type="submit"
                                [routerLink]="['/${nometabella}', ${nometabella}.id, 'edit']"
                                class="btn btn-primary btn-sm">
                            <fa-icon [icon]="'pencil-alt'"></fa-icon>
                            <span class="d-none d-md-inline" jhiTranslate="entity.action.edit">Edit</span>
                        </button>
                        <button type="submit"
                                [routerLink]="['/', { outlets: { popup: '${nometabella}/'+ ${nometabella}.id + '/delete'} }]"
                                replaceUrl="true"
                                queryParamsHandling="merge"
                                class="btn btn-danger btn-sm">
                            <fa-icon [icon]="'times'"></fa-icon>
                            <span class="d-none d-md-inline" jhiTranslate="entity.action.delete">Delete</span>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div *ngIf="${nometabella}s && ${nometabella}s.length">
        <div class="row justify-content-center">
            <jhi-item-count [page]="page" [total]="queryCount" [maxSize]="5" [itemsPerPage]="itemsPerPage"></jhi-item-count>
        </div>
        <div class="row justify-content-center">
            <ngb-pagination [collectionSize]="totalItems" [(page)]="page" [pageSize]="itemsPerPage" [maxSize]="5" [rotate]="true" [boundaryLinks]="true" (pageChange)="loadPage(page)"></ngb-pagination>
        </div>
    </div>
</div>
