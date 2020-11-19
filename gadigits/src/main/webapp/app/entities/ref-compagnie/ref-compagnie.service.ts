import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RefCompagnie } from './ref-compagnie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefCompagnieService {

    private resourceUrl = 'api/ref-compagnies';
    private resourceAgreementUrl = 'api/ref-compagnies-agreement';
    private resourceUnblockedUrl = 'api/ref-unb-compagnies';
    private resourceCompagnieBloqueUrl = 'api/ref-compagnies/bloque';
    private resourceSearchUrl = 'api/_search/ref-compagnies';
    private resourceUrlCompagnieAuth = 'api/ref-compagnies/authority';
    private resourcePartnertUrl = 'api/partners/companies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
    }

    create(refCompagnie: RefCompagnie): Observable<RefCompagnie> {
        const copy = this.convert(refCompagnie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(refCompagnie: RefCompagnie): Observable<RefCompagnie> {
        const copy = this.convert(refCompagnie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RefCompagnie> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findCompagnyAuthCompagnie(): Observable<RefCompagnie> {
        return this.http.get(this.resourceUrlCompagnieAuth).map((res: Response) => {
            const jsonResponse = res.json();
            console.log("reffffffcompagnie"+jsonResponse);
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    getUnblockedCompany(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUnblockedUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAgreementCompany(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourcePartnertUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    bloque(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceCompagnieBloqueUrl}/${id}`);
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
        entity.dateCreation = this.dateUtils
            .convertLocalDateFromServer(entity.dateCreation);
    }

    private convert(refCompagnie: RefCompagnie): RefCompagnie {
        const copy: RefCompagnie = Object.assign({}, refCompagnie);
        copy.dateCreation = this.dateUtils
            .convertLocalDateToServer(refCompagnie.dateCreation);
        return copy;
    }
}
