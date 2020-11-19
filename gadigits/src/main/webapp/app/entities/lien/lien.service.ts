import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Lien } from './lien.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LienService {

    private resourceUrl = 'api/liens';

    private resourceUrlLienUser = 'api/liens/user';
    private resourceUrlLienWithoutLien = 'api/liensWithoutParent';

    constructor(private http: Http) { }

    create(lien: Lien): Observable<Lien> {
        const copy = this.convert(lien);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(lien: Lien): Observable<Lien> {
        const copy = this.convert(lien);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Lien> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    getLiensWithoutParent(): Observable<Lien[]> {
        return this.http.get(this.resourceUrlLienWithoutLien).map((res: Response) => {
            return res.json();
        });
    }

     findLiens(id: number): Observable<Lien[]> {
        return this.http.get(`${this.resourceUrlLienUser}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(lien: Lien): Lien {
        const copy: Lien = Object.assign({}, lien);
        return copy;
    }
}
