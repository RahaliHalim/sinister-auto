import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefMotif } from './ref-motif.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefMotifService {

    private resourceUrl = 'api/ref-motifs';
    private resourceSearchUrl = 'api/_search/ref-motifs';
    private resourceMotifByTypeUrl = 'api/ref-motifs';

    constructor(private http: Http) { }

    create(refMotif: RefMotif): Observable<RefMotif> {
        const copy = this.convert(refMotif);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refMotif: RefMotif): Observable<RefMotif> {
        const copy = this.convert(refMotif);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefMotif> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByTypeAndEtat(type?: any, etat?: any): Observable<RefMotif[]> {
        return this.http.get(`${this.resourceMotifByTypeUrl}/${type}/${etat}`)
        .map((res: Response) => {
            return res.json();
        });
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

    private convert(refMotif: RefMotif): RefMotif {
        const copy: RefMotif = Object.assign({}, refMotif);
        return copy;
    }
}
