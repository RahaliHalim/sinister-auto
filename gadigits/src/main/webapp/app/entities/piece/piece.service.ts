import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Piece } from './piece.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PieceService {

    private resourceUrl = 'api/pieces';
    private resourceSearchUrl = 'api/_search/pieces';
    private resourceUrlByType = 'api/pieces/type'

    constructor(private http: Http) { }

    create(piece: Piece): Observable<Piece> {
        const copy = this.convert(piece);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(piece: Piece): Observable<Piece> {
        const copy = this.convert(piece);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    getPiecesByType(id: number): Observable<Piece[]> {
        return this.http.get(`${this.resourceUrlByType}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<Piece> {
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

    private convert(piece: Piece): Piece {
        const copy: Piece = Object.assign({}, piece);
        return copy;
    }
}
