import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefPricing } from './ref-pricing.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefPricingService {

    private resourceUrl = 'api/ref-pricing';

    constructor(private http: Http) { }

    create(pricing: RefPricing): Observable<RefPricing> {
        const copy = this.convert(pricing);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pricing: RefPricing): Observable<RefPricing> {
        const copy = this.convert(pricing);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefPricing> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(pricing: RefPricing): RefPricing {
        const copy: RefPricing = Object.assign({}, pricing);
        return copy;
    }
}
