import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { StampDuty } from './stamp-duty.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StampDutyService {

    private resourceUrl = 'api/stamp-duties';
    private resourceSearchUrl = 'api/_search/stamp-duties';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(stampDuty: StampDuty): Observable<StampDuty> {
        const copy = this.convert(stampDuty);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(stampDuty: StampDuty): Observable<StampDuty> {
        const copy = this.convert(stampDuty);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<StampDuty> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findActiveStamp(): Observable<StampDuty> {
        return this.http.get(`${this.resourceUrl}/active`).map((res: Response) => {
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
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
    }

    private convert(stampDuty: StampDuty): StampDuty {
        const copy: StampDuty = Object.assign({}, stampDuty);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(stampDuty.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(stampDuty.endDate);
        return copy;
    }
}
