import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { StatusPec } from './status-pec.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StatusPecService {

    private resourceUrl = 'api/status-pecs';
    private resourceSearchUrl = 'api/_search/status-pecs';

    constructor(private http: Http) { }

    create(statusPec: StatusPec): Observable<StatusPec> {
        const copy = this.convert(statusPec);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(statusPec: StatusPec): Observable<StatusPec> {
        const copy = this.convert(statusPec);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<StatusPec> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findWitchHasReason(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/reason`)
            .map((res: Response) => this.convertResponse(res));
    }

    findByCode(code: string, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/code/${code}`, options)
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

    private convert(statusPec: StatusPec): StatusPec {
        const copy: StatusPec = Object.assign({}, statusPec);
        return copy;
    }
}
