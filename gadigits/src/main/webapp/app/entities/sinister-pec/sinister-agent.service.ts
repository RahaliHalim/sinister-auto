import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { Sinister } from '../sinister/sinister.model';

@Injectable()
export class SinisterAgentService {
    public sinisterId: any;
    public id: any;

    private resourceUrl = 'api/dossiers';
    private counterUrl = 'api/dossier-rmqs/counter';
    private resourceSearchUrl = 'api/_search/dossiers';
    private resourcePecUrl = 'api/dossiers/prestation-pec';
    private resourceRmqUrl = 'api/dossiers/service-rmq';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(sinister: Sinister): Observable<Sinister> {
        const copy = this.convert(sinister);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(sinister: Sinister): Observable<Sinister> {
         this.sinisterId = sinister.id;
        const copy = this.convert(sinister);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<Sinister> {
        this.id = id;
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

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

    findRef(immat?: String): Observable<Response> {
        return this.http.get(`${this.counterUrl}/${immat}`).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
            }
            return null;
        });
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

    private convert(sinister: Sinister): Sinister {
        const copy: Sinister = Object.assign({}, sinister);
        copy.incidentDate = this.dateUtils
            .convertLocalDateToServer(sinister.incidentDate);
            return copy;
        }







}
