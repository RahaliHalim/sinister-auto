import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ConventionAmendment } from './convention-amendment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConventionAmendmentService {

    private resourceUrl = 'api/conventionAmendment';
    private resourceUrlFerieByDate = 'api/ferier/conventionAmendment';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(file: File, conventionAmendment: ConventionAmendment): Observable<ConventionAmendment> {
        console.log('_____________________________________________');
        const copy = this.convert(conventionAmendment);
        let formdata: FormData = new FormData();
        console.log(JSON.stringify(copy));
        formdata.append('signedConventionAmendment', file);
        formdata.append('conventionAmendment', new Blob([JSON.stringify(copy)], { type: 'application/json' }));

        return this.http.post(this.resourceUrl, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(conventionAmendment: ConventionAmendment): Observable<ConventionAmendment> {
        const copy = this.convert(conventionAmendment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ConventionAmendment> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByDate(date): Observable<ConventionAmendment> {
        return this.http.get(`${this.resourceUrlFerieByDate}/${date}`).map((res: Response) => {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils.convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils.convertLocalDateFromServer(entity.endDate);
    }

    private convert(conventionAmendment: ConventionAmendment): ConventionAmendment {
        const copy: ConventionAmendment = Object.assign({}, conventionAmendment);
        copy.startDate = this.dateUtils.convertLocalDateToServer(conventionAmendment.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(conventionAmendment.endDate);
        return copy;
    }
}
