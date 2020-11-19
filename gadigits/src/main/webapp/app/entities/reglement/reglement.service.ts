import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Reglement } from './reglement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReglementService {

    private resourceUrl = 'api/reglements';
    private resourceSearchUrl = 'api/_search/reglements';

    constructor(private http: Http) { }

    create(reglement: Reglement): Observable<Reglement> {
        const copy = this.convert(reglement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(reglement: Reglement): Observable<Reglement> {
        const copy = this.convert(reglement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Reglement> {
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

    private convert(reglement: Reglement): Reglement {
        const copy: Reglement = Object.assign({}, reglement);
        return copy;
    }
}
