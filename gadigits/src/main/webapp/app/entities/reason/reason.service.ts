import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Reason } from './reason.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReasonService {

    private resourceUrl = 'api/reasons';
    private resourceSearchUrl = 'api/_search/reasons';
    private resourceUrlByStep = 'api/reasons/motifs';
    private resourceUrlMotifsReopened = 'api/reasons/motifs-reopened';
    private resourceUrlMotifsRefused = 'api/reasons/motifs-refused';
    private resourceUrlMotifsCanceled = 'api/reasons/motifs-canceled';


    constructor(private http: Http) { }

    create(reason: Reason): Observable<Reason> {
        const copy = this.convert(reason);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(reason: Reason): Observable<Reason> {
        const copy = this.convert(reason);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }


    find(id: number): Observable<Reason> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findAllMotifReopened(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlMotifsReopened, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllMotifCanceled(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlMotifsCanceled, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllMotifRefused(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlMotifsRefused, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findMotifsByStep(id: number): Observable<Reason[]> {
        return this.http.get(`${this.resourceUrlByStep}/${id}`)
            .map((res: Response) => res.json());
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

    private convert(reason: Reason): Reason {
        const copy: Reason = Object.assign({}, reason);
        return copy;
    }
}
