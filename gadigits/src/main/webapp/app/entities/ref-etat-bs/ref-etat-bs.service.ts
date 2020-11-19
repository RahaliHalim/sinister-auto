import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefEtatBs } from './ref-etat-bs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefEtatBsService {

    private resourceUrl = 'api/ref-etat-bs';
    private resourceSearchUrl = 'api/_search/ref-etat-bs';

    constructor(private http: Http) { }

    create(refEtatBs: RefEtatBs): Observable<RefEtatBs> {
        const copy = this.convert(refEtatBs);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refEtatBs: RefEtatBs): Observable<RefEtatBs> {
        const copy = this.convert(refEtatBs);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefEtatBs> {
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

    private convert(refEtatBs: RefEtatBs): RefEtatBs {
        const copy: RefEtatBs = Object.assign({}, refEtatBs);
        return copy;
    }
}
