import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RaisonAssistance } from './raison-assistance.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RaisonAssistanceService {

    private resourceUrl = 'api/raison-assistances';
    private resourceSearchUrl = 'api/_search/raison-assistances';
    private resourceUrlByOperation= 'api/reasons/motifs/operation';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(raisonAssistance: RaisonAssistance): Observable<RaisonAssistance> {
        const copy = this.convert(raisonAssistance);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(raisonAssistance: RaisonAssistance): Observable<RaisonAssistance> {
        const copy = this.convert(raisonAssistance);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RaisonAssistance> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findByStatus(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/status/${id}`)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    findMotifsByOperation(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlByOperation}/${id}`)
            .map((res: Response) => this.convertResponse(res));
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
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(entity.creationDate);
    }

    private convert(raisonAssistance: RaisonAssistance): RaisonAssistance {
        const copy: RaisonAssistance = Object.assign({}, raisonAssistance);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(raisonAssistance.creationDate);
        return copy;
    }
}
