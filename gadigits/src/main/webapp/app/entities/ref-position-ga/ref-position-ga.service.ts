import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefPositionGa } from './ref-position-ga.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefPositionGaService {

    private resourceUrl = 'api/ref-position-gas';
    private resourceSearchUrl = 'api/_search/ref-position-gas';

    constructor(private http: Http) { }

    create(refPositionGa: RefPositionGa): Observable<RefPositionGa> {
        const copy = this.convert(refPositionGa);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refPositionGa: RefPositionGa): Observable<RefPositionGa> {
        const copy = this.convert(refPositionGa);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefPositionGa> {
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

    private convert(refPositionGa: RefPositionGa): RefPositionGa {
        const copy: RefPositionGa = Object.assign({}, refPositionGa);
        return copy;
    }
}
