import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ${entityName} } from 'app/shared/model/${classNameLowerCase}.model';
import { ${entityName}Service } from './${classNameLowerCase}.service';
import { ${entityName}Component } from './${classNameLowerCase}.component';
import { ${entityName}DetailComponent } from './${classNameLowerCase}-detail.component';
import { ${entityName}UpdateComponent } from './${classNameLowerCase}-update.component';
import { ${entityName}DeletePopupComponent } from './${classNameLowerCase}-delete-dialog.component';
import { ${iName} } from 'app/shared/model/${classNameLowerCase}.model';

@Injectable({ providedIn: 'root' })
export class ${entityName}Resolve implements Resolve<${iName}> {
    constructor(private service: ${entityName}Service) {}
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<${entityName}> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<${entityName}>) => response.ok),
                map((${classNameLowerCase}: HttpResponse<${entityName}>) => ${classNameLowerCase}.body)
            );
        }
        return of(new ${entityName}());
    }
}

export const ${classNameLowerCase}Route: Routes = [
    {
        path: '${classNameLowerCase}',
        component: ${entityName}Component,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: [${authorities}],
            defaultSort: 'id,asc',
            pageTitle: '${projectName}App.${classNameLowerCase}.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: '${classNameLowerCase}/:id/view',
        component: ${entityName}DetailComponent,
        resolve: {
            ${classNameLowerCase}: ${entityName}Resolve
        },
        data: {
            authorities: [${authorities}],
            pageTitle: '${projectName}App.${classNameLowerCase}.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: '${classNameLowerCase}/new',
        component: ${entityName}UpdateComponent,
        resolve: {
            ${classNameLowerCase}: ${entityName}Resolve
        },
        data: {
            authorities: [${authorities}],
            pageTitle: '${projectName}App.${classNameLowerCase}.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: '${classNameLowerCase}/:id/edit',
        component: ${entityName}UpdateComponent,
        resolve: {
            ${classNameLowerCase}: ${entityName}Resolve
        },
        data: {
            authorities: [${authorities}],
            pageTitle: '${projectName}App.${classNameLowerCase}.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ${classNameLowerCase}PopupRoute: Routes = [
    {
        path: '${classNameLowerCase}/:id/delete',
        component: ${entityName}DeletePopupComponent,
        resolve: {
            ${classNameLowerCase}: ${entityName}Resolve
        },
        data: {
            authorities: [${authorities}],
            pageTitle: '${projectName}App.${classNameLowerCase}.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }

];
