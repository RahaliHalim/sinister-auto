import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefTypePj } from './ref-type-pj.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefTypePjService {

    private resourceUrl = 'api/ref-type-pjs';
    private resourceSearchUrl = 'api/_search/ref-type-pjs';

    constructor(private http: Http) { }

    create(refTypePj: RefTypePj): Observable<RefTypePj> {
        const copy = this.convert(refTypePj);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refTypePj: RefTypePj): Observable<RefTypePj> {
        const copy = this.convert(refTypePj);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefTypePj> {
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

    private convert(refTypePj: RefTypePj): RefTypePj {
        const copy: RefTypePj = Object.assign({}, refTypePj);
        return copy;
    }
}
