import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ViewPolicy } from './view-policy.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ViewPolicyService {

    private resourceUrl = 'api/view-policies';
    private resourceSearchUrl = 'api/_search/view-policies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(viewPolicy: ViewPolicy): Observable<ViewPolicy> {
        const copy = this.convert(viewPolicy);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(viewPolicy: ViewPolicy): Observable<ViewPolicy> {
        const copy = this.convert(viewPolicy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ViewPolicy> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    exportpoliciesToExcel(search: string): Observable<Blob> {
        if(search === undefined || search === null || search === "" ){
            search = '-1';
        } 
        console.log('___________________________________________');
        return this.http.get(`${this.resourceUrl}/export/excel/${search}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
    }

    private convert(viewPolicy: ViewPolicy): ViewPolicy {
        const copy: ViewPolicy = Object.assign({}, viewPolicy);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(viewPolicy.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(viewPolicy.endDate);
        return copy;
    }
}
