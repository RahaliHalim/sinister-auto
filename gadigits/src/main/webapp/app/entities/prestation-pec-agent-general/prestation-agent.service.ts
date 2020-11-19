import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PrestationAgentService {
    public dossierId: any;
    public id: any;

    private resourceUrl = 'api/dossiers';
    private counterUrl = 'api/dossier-rmqs/counter';
    private resourceSearchUrl = 'api/_search/dossiers';
    private resourcePecUrl = 'api/dossiers/prestation-pec';
    private resourceRmqUrl = 'api/dossiers/service-rmq';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }


    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryAssistance(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceRmqUrl}/${id}`)
            .map((res: Response) => this.convertResponse(res));
    }

    queryPec(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourcePecUrl}/${id}`)
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

    private convertItemFromServer(entity: any) {
        entity.dateCreation = this.dateUtils
            .convertDateTimeFromServer(entity.dateCreation);
        entity.dateCloture = this.dateUtils
            .convertDateTimeFromServer(entity.dateCloture);
        entity.dateDerniereMaj = this.dateUtils
            .convertDateTimeFromServer(entity.dateDerniereMaj);
        entity.dateAccident = this.dateUtils
            .convertLocalDateFromServer(entity.dateAccident);
    }








}
