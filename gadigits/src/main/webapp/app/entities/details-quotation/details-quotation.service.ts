import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { DetailsQuotation } from './details-quotation.model';

@Injectable()
export class DetailsQuotationService {

    private resourceUrl = 'api/details-quotation';
    private resourceUrlBySinisterPec = 'api/details-quotation-by-sinister-pec';

    constructor(private http: Http) { }

    create(detailsQuotation: DetailsQuotation): Observable<DetailsQuotation> {
        return this.http.post(this.resourceUrl, detailsQuotation).map((res: Response) => {
            return res.json();
        });
    }

    update(detailsQuotation: DetailsQuotation): Observable<DetailsQuotation> {
        return this.http.put(this.resourceUrl, detailsQuotation).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DetailsQuotation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findBySinisterPec(id: number): Observable<DetailsQuotation> {
        return this.http.get(`${this.resourceUrlBySinisterPec}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(detailsQuotation: DetailsQuotation): DetailsQuotation {
        const copy: DetailsQuotation = Object.assign({}, detailsQuotation);
        return copy;
    }
}