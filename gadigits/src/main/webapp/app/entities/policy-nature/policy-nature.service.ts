import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PolicyNature } from './policy-nature.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PolicyNatureService {

    private resourceUrl = 'api/policy-natures';
    private resourceSearchUrl = 'api/_search/policy-natures';

    constructor(private http: Http) { }

    create(policyNature: PolicyNature): Observable<PolicyNature> {
        const copy = this.convert(policyNature);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(policyNature: PolicyNature): Observable<PolicyNature> {
        const copy = this.convert(policyNature);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PolicyNature> {
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

    private convert(policyNature: PolicyNature): PolicyNature {
        const copy: PolicyNature = Object.assign({}, policyNature);
        return copy;
    }
}
