import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PolicyType } from './policy-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PolicyTypeService {

    private resourceUrl = 'api/policy-types';
    private resourceSearchUrl = 'api/_search/policy-types';

    constructor(private http: Http) { }

    create(policyType: PolicyType): Observable<PolicyType> {
        const copy = this.convert(policyType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(policyType: PolicyType): Observable<PolicyType> {
        const copy = this.convert(policyType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PolicyType> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(policyType: PolicyType): PolicyType {
        const copy: PolicyType = Object.assign({}, policyType);
        return copy;
    }
}
