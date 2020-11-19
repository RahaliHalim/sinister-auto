import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Sinister } from '../sinister/sinister.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DemandSinisterPecService {
    public sinisterId: any;
    public id: any;

    private resourceDemandUrl = "api/demands/pec";
    private resourceDemandOutstandingUrl = "api/demands/outstanding";
    private resourceDemandSentUrl = "api/demands/sent";
    private resourceDemandAccordUrl = "api/demands/accord";

    constructor(private http: Http, private dateUtils: JhiDateUtils) {}

    findAllNewExternanlDemands(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceDemandUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findOutstandingDemands(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceDemandOutstandingUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findSentDemands(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceDemandSentUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAccordDemands(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceDemandAccordUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        console.log("___________________________________________________00");
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateCreation = this.dateUtils.convertDateTimeFromServer(
            entity.dateCreation
        );
        entity.dateCloture = this.dateUtils.convertDateTimeFromServer(
            entity.dateCloture
        );
        entity.dateDerniereMaj = this.dateUtils.convertDateTimeFromServer(
            entity.dateDerniereMaj
        );
        entity.dateAccident = this.dateUtils.convertLocalDateFromServer(
            entity.dateAccident
        );
    }

    formatDate(date) {
        var d = date,
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [day, month, year].join('/');
    }

    formatEnDate(date) {
        var d = date,
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }

    formatDateForDirectiveDate(date) {
        if(date !== null || date !== undefined) {
            var d = new Date(date);
            return {
                year: d.getFullYear(),
                month: d.getMonth() + 1,
                day: d.getDate()
            }
        }
        return date;
    }

}
