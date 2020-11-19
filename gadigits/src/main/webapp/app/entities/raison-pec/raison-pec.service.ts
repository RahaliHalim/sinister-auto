import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RaisonPec } from './raison-pec.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RaisonPecService {

    private resourceUrl = 'api/raison-pecs';
    private resourceSearchUrl = 'api/_search/raison-pecs';
    private resourceUrlByStep = 'api/raison-pecs/motifs';
    private resourceUrlByOperation  = 'api/raison-pecs/operations';
    private resourceUrlMotifsReopened = 'api/raison-pecs/motifs-reopened';
    private resourceUrlMotifsRefused = 'api/raison-pecs/motifs-refused';
    private resourceUrlMotifsCanceled = 'api/raison-pecs/motifs-canceled';
    private resourceUrlByStatusPecStatusChangeMatrix = 'api/raison-pecs/motifs/changeMatrixId';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(raisonPec: RaisonPec): Observable<RaisonPec> {
        const copy = this.convert(raisonPec);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(raisonPec: RaisonPec): Observable<RaisonPec> {
        const copy = this.convert(raisonPec);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RaisonPec> {
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

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    findAllMotifReopened(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlMotifsReopened, options)
            .map((res: Response) => this.convertPecResponse(res));
    }

    findAllMotifCanceled(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlMotifsCanceled, options)
            .map((res: Response) => this.convertPecResponse(res));
    }

    findAllMotifRefused(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlMotifsRefused, options)
            .map((res: Response) => this.convertPecResponse(res));
    }

    findMotifsByStep(id: number): Observable<RaisonPec[]> {
        return this.http.get(`${this.resourceUrlByStep}/${id}`)
            .map((res: Response) => res.json());
    }
    findMotifsByOperation(id: number): Observable<RaisonPec[]> {
        return this.http.get(`${this.resourceUrlByOperation}/${id}`)
            .map((res: Response) => res.json());
    }

    findMotifsByStatusPecStatusChangeMatrix(id: number): Observable<RaisonPec[]> {
        return this.http.get(`${this.resourceUrlByStatusPecStatusChangeMatrix}/${id}`)
            .map((res: Response) => res.json());
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

    private convertPecResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(entity.creationDate);
    }

    private convert(raisonPec: RaisonPec): RaisonPec {
        const copy: RaisonPec = Object.assign({}, raisonPec);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(raisonPec.creationDate);
        return copy;
    }
}
