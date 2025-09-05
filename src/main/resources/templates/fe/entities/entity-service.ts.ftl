import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ${iName} } from 'app/shared/model/${classNameLowerCase}.model';

type EntityResponseType = HttpResponse<${iName}>;
type EntityArrayResponseType = HttpResponse<${iName}[]>;

@Injectable({ providedIn: 'root' })
export class ${entityName}Service {

    public resourceUrl = SERVER_API_URL + 'api/${classNameLowerCase}s';
    public resourceExportUrl = SERVER_API_URL + 'api/${classNameLowerCase}sExport';
    constructor(private http: HttpClient) {}

    create(${classNameLowerCase}: ${iName}): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(${classNameLowerCase});
        return this.http
            .post<${iName}>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(${classNameLowerCase}: ${iName}): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(${classNameLowerCase});
        return this.http
            .put<${iName}>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<${iName}>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<${iName}[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(${classNameLowerCase}: ${iName}): ${iName} {
        const copy: ${iName} = Object.assign({}, ${classNameLowerCase}, {
<#list dateFields as field>
            ${field.fieldName}: ${classNameLowerCase}.${field.fieldName} != null && ${classNameLowerCase}.${field.fieldName}.isValid() ? ${classNameLowerCase}.${field.fieldName}<#if field.localDate>.format(DATE_FORMAT)<#else>.toJSON()</#if> : null,
</#list>
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
<#list dateFields as field>
            res.body.${field.fieldName} = res.body.${field.fieldName} != null ? moment(res.body.${field.fieldName}) : null;
</#list>
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((${classNameLowerCase}: ${iName}) => {
<#list dateFields as field>
                ${classNameLowerCase}.${field.fieldName} = ${classNameLowerCase}.${field.fieldName} != null ? moment(${classNameLowerCase}.${field.fieldName}) : null;
</#list>
            });
        }
        return res;
    }
}
