import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Sinister } from '../sinister/sinister.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SinisterPecAgentService {
    public dossierId: any;
    public id: any;

    private resourceUrl = 'api/sinister';
    private counterUrl = 'api/sinister-rmqs/counter';
    private resourceSearchUrl = 'api/_search/sinister';
    private resourcePecUrl = 'api/sinister/sinister-pec';
    private resourceRmqUrl = 'api/sinister/service-rmq';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(dossier: Sinister): Observable<Sinister> {
        console.log("service create 1");
        const copy = this.convert(dossier);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            console.log("service create 2");
            return res.json();
        });
    }

    update(dossier: Sinister): Observable<Sinister> {
         this.dossierId = dossier.id;
        const copy = this.convert(dossier);
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

    private convert(dossier: Sinister): Sinister {
        const copy: Sinister = Object.assign({}, dossier);
        copy.incidentDate = this.dateUtils
            .convertLocalDateToServer(dossier.insuredFullName);
            return copy;
        }







}
