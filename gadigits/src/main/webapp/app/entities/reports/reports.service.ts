import { Search } from '../sinister/search.model';
import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { AssistanceMonitoringCompany } from './assistance-monitoring-company.model';
import { PecMonitoringPrestation } from './pec-monitoring-prestation.model';
import { PolicyIndicator } from './policy-indicator.model';

@Injectable()
export class ReportsService {

    private resourcePrestationUrl = 'api/sinister/prestation';
    private resourcePecUrl = 'api/report/pec';
    private resourcePolicyUrl = 'api/report/policy';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    
    findReport1Prestations(search: Search): Observable<AssistanceMonitoringCompany[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourcePolicyUrl}/report1`, copy).map((res: Response) => {
            return res.json();
        });
    }

    findPolicyIndicatorReport(search: Search): Observable<PolicyIndicator[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourcePolicyUrl}/indicator`, copy).map((res: Response) => {
            return res.json();
        });
    }

    findPecMonitoringPrestation(search: Search): Observable<PecMonitoringPrestation[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourcePecUrl}/report-pec-monitoring`, copy).map((res: Response) => {
            return res.json();
        });
    }

    findReport1PrestationsFile(search: Search): Observable<Blob> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourcePrestationUrl}/report1/download`, copy).map((res: Response) => {
            console.log('________________________________________________________________________');
            let mediatype = 'application/vnd.ms-excel;charset=UTF-8';

            const data  = new Blob(["\ufeff",res.arrayBuffer()], {type: mediatype});
            return data;
        });
    }


    queryPecMonitoringPrestation(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourcePecUrl}/report-pec-monitoring`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.creationDate = this.dateUtils.convertDateTimeFromServer(entity.creationDate);
        entity.closureDate = this.dateUtils.convertDateTimeFromServer(entity.closureDate);
        entity.updateDate = this.dateUtils.convertDateTimeFromServer(entity.updateDate);
        entity.incidentDate = this.dateUtils.convertLocalDateFromServer(entity.incidentDate);
    }

    private convertSearch(search: Search): Search {
        const copy: Search = Object.assign({}, search);
        copy.startDate = this.dateUtils.convertLocalDateToServer(search.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(search.endDate);
        return copy;
    }

}
