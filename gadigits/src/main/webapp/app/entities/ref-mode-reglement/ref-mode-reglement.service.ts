import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefModeReglement } from './ref-mode-reglement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefModeReglementService {

    private resourceUrl = 'api/ref-mode-reglements';
    private resourceSearchUrl = 'api/_search/ref-mode-reglements';

    constructor(private http: Http) { }

    create(refModeReglement: RefModeReglement): Observable<RefModeReglement> {
        const copy = this.convert(refModeReglement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refModeReglement: RefModeReglement): Observable<RefModeReglement> {
        const copy = this.convert(refModeReglement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefModeReglement> {
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

    private convert(refModeReglement: RefModeReglement): RefModeReglement {
        const copy: RefModeReglement = Object.assign({}, refModeReglement);
        return copy;
    }
}
