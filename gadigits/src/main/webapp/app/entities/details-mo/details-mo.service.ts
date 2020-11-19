import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DetailsMo } from './details-mo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DetailsMoService {

    private resourceUrl = 'api/details-mos';
    private resourceSearchUrl = 'api/_search/details-mos';
    private resourceUrlByDevis = 'api/details-mos/devis';

    constructor(private http: Http) { }

    create(detailsMo: DetailsMo): Observable<DetailsMo> {
        const copy = this.convert(detailsMo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(detailsMo: DetailsMo): Observable<DetailsMo> {
        const copy = this.convert(detailsMo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DetailsMo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByDevis(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlByDevis}/${id}`).map((res: Response) => this.convertResponse(res));
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

    private convert(detailsMo: DetailsMo): DetailsMo {
        const copy: DetailsMo = Object.assign({}, detailsMo);
        return copy;
    }

}
