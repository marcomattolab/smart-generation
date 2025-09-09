import { Component, OnInit, OnDestroy, NgModule, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { FormGroup, ReactiveFormsModule, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { toTimestampInizio, toTimestampFine } from 'app/shared/util/date-util';
import { ${iNometabella} } from 'app/shared/model/${nometabella}.model';
import { ${Nometabella}Service } from './${nometabella}.service';
import { checkAndCompileSearchFilterContains, checkAndCompileSearchFilterEquals, checkAndCompileSearchBetween, ITEMS_PER_PAGE } from 'app/shared';
<#list relations.imports as anImport>
${anImport}
</#list>

@Component({
    selector: 'jhi-${nometabella}',
    templateUrl: './${nometabella}.component.html'
})
export class ${Nometabella}Component implements OnInit, OnDestroy {
    currentAccount: any;
    ${nometabella}s: ${iNometabella}[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    myGroup: FormGroup;
<#list relations.inits as init>
    ${init}
</#list>

    constructor(
        private ${nometabella}Service: ${Nometabella}Service,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private accountService: AccountService,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private router: Router,
<#list relations.constructors as constructor>
        ${constructor}
</#list>
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });

        this.initFormRicerca();
    }

    initFormRicerca() {
        this.myGroup = new FormGroup({
<#list relations.searches as search>
            ${search}
</#list>
<#list columns as column>
    <#if column.isDate>
            ${column.name}Da: new FormControl(''),
            ${column.name}A: new FormControl('')<#if column_has_next>,</#if>
    <#elseif column.isNumeric>
            ${column.name}Da: new FormControl(''),
            ${column.name}A: new FormControl('')<#if column_has_next>,</#if>
    <#elseif column.isString>
            ${column.name}: new FormControl('')<#if column_has_next>,</#if>
    </#if>
</#list>
        });
        this.myGroup.updateValueAndValidity();
    }

    loadAll() {
        this.${nometabella}Service
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<${iNometabella}[]>) => this.paginate${Nometabella}s(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/${nometabella}'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/${nometabella}',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();

        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeIn${Nometabella}s();
<#list relations.ngOnInits as onInit>
        ${onInit}
</#list>
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ${iNometabella}) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeIn${Nometabella}s() {
        this.eventSubscriber = this.eventManager.subscribe('${nometabella}ListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginate${Nometabella}s(data: ${iNometabella}[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.${nometabella}s = data;
    }

    exportFile(fileType) {
        console.log('Export file with type: ' + fileType);

        const myGroupControls = this.myGroup.controls;
        let searchFilter = {
            'fileType': fileType
        };

<#list searchFilters as searchFilter>
        ${searchFilter}
</#list>
<#list columns as column>
    <#if column.isDate>
        searchFilter = checkAndCompileSearchBetween(myGroupControls, searchFilter, '${column.name}Da', '${column.name}A', '${column.name}');
    <#elseif column.isNumeric>
        searchFilter = checkAndCompileSearchBetween(myGroupControls, searchFilter, '${column.name}Da', '${column.name}A', '${column.name}');
    <#elseif column.isString && column.isEnum>
        searchFilter = checkAndCompileSearchFilterEquals(myGroupControls, searchFilter, '${column.name}');
    <#elseif column.isString && !column.isEnum>
        searchFilter = checkAndCompileSearchFilterContains(myGroupControls, searchFilter, '${column.name}');
    </#if>
</#list>

        return window.location.href = this.${nometabella}Service.resourceExportUrl + this.buildFilterParams(searchFilter);
    }

    buildFilterParams(searchFilter) {
       let res = '';
       let isFirst = true;
       for (const filter in searchFilter) {
          if (searchFilter.hasOwnProperty(filter)) {
             res += (isFirst ? '?' : '&') + filter + '=' + searchFilter[filter];
             isFirst = false;
          }
       }
       return res;
    }

    resetFiltri() {
        this.initFormRicerca();
    }

    cerca() {
        console.log('Cerca ${Nometabella} ');
        const myGroupControls = this.myGroup.controls;
        let searchFilter = {
            'page': this.page - 1,
            'size': this.itemsPerPage,
            'sort': this.sort()
        };

<#list searchFilters as searchFilter>
        ${searchFilter}
</#list>
<#list columns as column>
    <#if column.isDate>
        searchFilter = checkAndCompileSearchBetween(myGroupControls, searchFilter, '${column.name}Da', '${column.name}A', '${column.name}');
    <#elseif column.isNumeric>
        searchFilter = checkAndCompileSearchBetween(myGroupControls, searchFilter, '${column.name}Da', '${column.name}A', '${column.name}');
    <#elseif column.isString && column.isEnum>
        searchFilter = checkAndCompileSearchFilterEquals(myGroupControls, searchFilter, '${column.name}');
    <#elseif column.isString && !column.isEnum>
        searchFilter = checkAndCompileSearchFilterContains(myGroupControls, searchFilter, '${column.name}');
    </#if>
</#list>

        this.${nometabella}Service.query(searchFilter).subscribe(
             (res: HttpResponse<${iNometabella}[]>) => this.paginate${Nometabella}s(res.body, res.headers),
             (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

}
