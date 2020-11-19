import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Delegation } from './delegation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DelegationService {

    private resourceUrl = 'api/delegations';
    private resourceUrlGaGeo = 'api/delegations-gageo';
    private resourceSearchUrl = 'api/_search/delegations';

    constructor(private http: Http) { }

    create(delegation: Delegation): Observable<Delegation> {
        const copy = this.convert(delegation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(delegation: Delegation): Observable<Delegation> {
        const copy = this.convert(delegation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Delegation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryGaGeo(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlGaGeo, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findByGovernorate(id: number): Observable<Delegation[]> {
        return this.http.get(`${this.resourceUrl}/governorate/${id}`)
            .map((res: Response) => res.json());
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

    private convert(delegation: Delegation): Delegation {
        const copy: Delegation = Object.assign({}, delegation);
        return copy;
    }
}
