import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Reinsurer } from './reinsurer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReinsurerService {

    private resourceUrl = 'api/reinsurers';
    private resourceSearchUrl = 'api/_search/reinsurers';

    constructor(private http: Http) { }

    create(reinsurer: Reinsurer): Observable<Reinsurer> {
        const copy = this.convert(reinsurer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(reinsurer: Reinsurer): Observable<Reinsurer> {
        const copy = this.convert(reinsurer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Reinsurer> {
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

    private convert(reinsurer: Reinsurer): Reinsurer {
        const copy: Reinsurer = Object.assign({}, reinsurer);
        return copy;
    }
}
