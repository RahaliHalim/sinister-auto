import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Tiers } from './tiers.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TiersService {

    private resourceUrl = 'api/tiers';
    private resourceSearchUrl = 'api/_search/tiers';
    private resourceUrl1 = 'api/tiersImmat';
    private resourceUrlByImmatriculation = 'api/tiersByImmat';
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(tiers: Tiers): Observable<Tiers> {
        const copy = this.convert(tiers);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    update(tiers: Tiers): Observable<Tiers> {
        const copy = this.convert(tiers);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByImmatriculation(immatriculation: String): Observable<Tiers>{
        return this.http.get(`${this.resourceUrl1}/${immatriculation}`).map((res: Response) =>{
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
        });
    }
    findTiersByImmatriculation(immatriculation: String, clientId: number, req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlByImmatriculation}/${immatriculation}/${clientId}`,options)
        .map((res: Response) => this.convertResponse(res));
    }
    find(id: number): Observable<Tiers> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findAllByPEC(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => this.convertResponse(res));
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
        entity.debutValidite = this.dateUtils
            .convertLocalDateFromServer(entity.debutValidite);
        entity.finValidite = this.dateUtils
            .convertLocalDateFromServer(entity.finValidite);
    }

    private convert(tiers: Tiers): Tiers {
        const copy: Tiers = Object.assign({}, tiers);
        copy.debutValidite = this.dateUtils
            .convertLocalDateToServer(tiers.debutValidite);
        copy.finValidite = this.dateUtils
            .convertLocalDateToServer(tiers.finValidite);
        return copy;
    }
}
