import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { PolicyHolder } from './policy-holder.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PolicyHolderService {

    private resourceUrl = 'api/policy-holders';
    private resourceSearchUrl = 'api/_search/policy-holders';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(policyHolder: PolicyHolder): Observable<PolicyHolder> {
        const copy = this.convert(policyHolder);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(policyHolder: PolicyHolder): Observable<PolicyHolder> {
        const copy = this.convert(policyHolder);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PolicyHolder> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(entity.creationDate);
        entity.updateDate = this.dateUtils
            .convertLocalDateFromServer(entity.updateDate);
    }

    private convert(policyHolder: PolicyHolder): PolicyHolder {
        const copy: PolicyHolder = Object.assign({}, policyHolder);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(policyHolder.creationDate);
        copy.updateDate = this.dateUtils
            .convertLocalDateToServer(policyHolder.updateDate);
        return copy;
    }
}
