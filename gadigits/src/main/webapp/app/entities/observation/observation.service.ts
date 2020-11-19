import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Observation } from './observation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ObservationService {

    private resourceUrl = 'api/observations';
    private resourceUrlPrestation = 'api/observations/prestation';
    private resourceUrlDevis = 'api/observations/devis';
    

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(observation: Observation): Observable<Observation> {
        const copy = this.convert(observation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(observation: Observation): Observable<Observation> {
        const copy = this.convertObservationDate(observation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Observation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByPrestation(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlPrestation}/${id}`).map((res: Response) => this.convertResponse(res));
    }

   

    findByDevis(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlDevis}/${id}`).map((res: Response) => this.convertResponse(res));
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.date = this.dateUtils
            .convertDateTimeFromServer(entity.date);
    }

    private convert(observation: Observation): Observation {
        const copy: Observation = Object.assign({}, observation);
        if(observation.date && observation.date.day) {
            observation.date = new Date(observation.date.year, observation.date.month, observation.date.day);
        }
        copy.date = this.dateUtils.toDate(observation.date);
        return copy;
    }
    private convertObservationDate(observation: Observation): Observation {
        const copy: Observation = Object.assign({}, observation);
        /*if(observation.date && observation.date.day) {
            observation.date = new Date(observation.date.year, observation.date.month, observation.date.day);
        }*/
		
        copy.date = new Date();
        return copy;
    }
}
