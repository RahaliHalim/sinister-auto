import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Loueur } from './loueur.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LoueurService {

    private resourceUrl = 'api/loueurs';
    private resourceSearchUrl = 'api/_search/loueurs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(loueur: Loueur): Observable<Loueur> {
        const copy = this.convert(loueur);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(loueur: Loueur): Observable<Loueur> {
        const copy = this.convert(loueur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Loueur> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findByGovernorate(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/gov/${id}`)
            .map((res: Response) =>{
               return new ResponseWrapper(res.headers, res.json(), res.status)
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateEffetConvention = this.dateUtils
            .convertLocalDateFromServer(entity.dateEffetConvention);
        entity.dateFinConvention = this.dateUtils
            .convertLocalDateFromServer(entity.dateFinConvention);
    }

    private convert(loueur: Loueur): Loueur {
        const copy: Loueur = Object.assign({}, loueur);
        copy.dateEffetConvention = this.dateUtils
            .convertLocalDateToServer(loueur.dateEffetConvention);
        copy.dateFinConvention = this.dateUtils
            .convertLocalDateToServer(loueur.dateFinConvention);
        return copy;
    }
}
