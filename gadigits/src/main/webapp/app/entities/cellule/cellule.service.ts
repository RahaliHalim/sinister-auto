import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Cellule } from './cellule.model';
import { Authority } from '../authority/authority.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CelluleService {

    private resourceUrl = 'api/cellules';
    private resourceUrlByName = 'api/cellules/nom';

    private resourceAuthorityUrlByName = 'api/authorities';
    private resourceAuthorityInterne = 'api/authorities/interne';
    private resourceAuthorityExterne = 'api/authorities/externe';
    private resourceAuthorityActive = 'api/authorities/active';
    private resourceAuthorityCelluleCombinaison = 'api/authorities/authorities_cellules_combinaison';

    constructor(private http: Http) { }

    create(cellule: Cellule): Observable<Cellule> {
        const copy = this.convert(cellule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cellule: Cellule): Observable<Cellule> {
        const copy = this.convert(cellule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Cellule> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByName(name: String): Observable<Cellule> {
        return this.http.get(`${this.resourceUrlByName}/${name}`).map((res: Response) => {
            return res.json();
        });
    }

     findAuthorityByName(name: String): Observable<Authority> {
        return this.http.get(`${this.resourceAuthorityUrlByName}/${name}`).map((res: Response) => {
            return res.json();
        });
    }

    findAuthorityInterne(): Observable<Authority[]> {
        return this.http.get(this.resourceAuthorityInterne).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    AuthoritiesCellulesCombinaison(): Observable<String[]> {
        return this.http.get(this.resourceAuthorityCelluleCombinaison).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findAuthorityExterne(): Observable<Authority[]> {
        return this.http.get(this.resourceAuthorityExterne).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findAuthorityActive(): Observable<String[]> {
        return this.http.get(this.resourceAuthorityActive).map((res: Response) => {
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

    private convert(cellule: Cellule): Cellule {
        const copy: Cellule = Object.assign({}, cellule);
        return copy;
    }
}
