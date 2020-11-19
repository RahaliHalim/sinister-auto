import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefTypePieces } from './ref-type-pieces.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefTypePiecesService {

    private resourceUrl = 'api/ref-type-pieces';
    private resourceSearchUrl = 'api/_search/ref-type-pieces';

    constructor(private http: Http) { }

    create(refTypePieces: RefTypePieces): Observable<RefTypePieces> {
        const copy = this.convert(refTypePieces);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refTypePieces: RefTypePieces): Observable<RefTypePieces> {
        const copy = this.convert(refTypePieces);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefTypePieces> {
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

    private convert(refTypePieces: RefTypePieces): RefTypePieces {
        const copy: RefTypePieces = Object.assign({}, refTypePieces);
        return copy;
    }
}
