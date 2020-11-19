import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RefHolidays } from './ref-holidays.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefHolidaysService {

    private resourceUrl = 'api/ref-holidays';
    private resourceUrlFerieByDate = 'api/ferier/ref-holidays';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(refHolidays: RefHolidays): Observable<RefHolidays> {
        const copy = this.convert(refHolidays);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(refHolidays: RefHolidays): Observable<RefHolidays> {
        const copy = this.convert(refHolidays);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RefHolidays> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByDate(date): Observable<RefHolidays> {
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

    findByLabelOrDate(refHolidays: RefHolidays): Observable<ResponseWrapper> {
        const copy = this.convert(refHolidays);
        return this.http.post(`${this.resourceUrl}/search`, copy)
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
            .convertLocalDateFromServer(entity.date);
    }

    private convert(refHolidays: RefHolidays): RefHolidays {
        const copy: RefHolidays = Object.assign({}, refHolidays);
        copy.date = this.dateUtils
            .convertLocalDateToServer(refHolidays.date);
        return copy;
    }
}
