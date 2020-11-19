import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PolicyReceiptStatus } from './policy-receipt-status.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PolicyReceiptStatusService {

    private resourceUrl = 'api/policy-receipt-statuses';
    private resourceSearchUrl = 'api/_search/policy-receipt-statuses';

    constructor(private http: Http) { }

    create(policyReceiptStatus: PolicyReceiptStatus): Observable<PolicyReceiptStatus> {
        const copy = this.convert(policyReceiptStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(policyReceiptStatus: PolicyReceiptStatus): Observable<PolicyReceiptStatus> {
        const copy = this.convert(policyReceiptStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PolicyReceiptStatus> {
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

    private convert(policyReceiptStatus: PolicyReceiptStatus): PolicyReceiptStatus {
        const copy: PolicyReceiptStatus = Object.assign({}, policyReceiptStatus);
        return copy;
    }
}
