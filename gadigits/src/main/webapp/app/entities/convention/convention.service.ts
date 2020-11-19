import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Convention } from './convention.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { PartnerModeMapping } from '../user-partner-mode/partner-mode-mapping.model';

@Injectable()
export class ConventionService {

    private resourceUrl = 'api/convention';
    private resourceUrlFerieByDate = 'api/ferier/convention';
    private resourceUrlConventionPacks = 'api/convention/packs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(file: File, convention: Convention): Observable<Convention> {
        console.log('_____________________________________________');
        const copy = this.convert(convention);
        let formdata: FormData = new FormData();
        console.log(JSON.stringify(copy));
        formdata.append('signedConvention', file);
        formdata.append('convention', new Blob([JSON.stringify(copy)], {type: 'application/json'}));

        return this.http.post(this.resourceUrl, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(convention: Convention): Observable<Convention> {
        const copy = this.convert(convention);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Convention> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findPackByPartner(partnersId: number[]): Observable<PartnerModeMapping[]>{
        console.log("selected compagnies services-*-*-*-"+partnersId[0]);
        return this.http.get(`${this.resourceUrlConventionPacks}/${partnersId}`).map((res: Response) => {
            return res.json();
        });
    }

    findByDate(date): Observable<Convention> {
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

    private convert(convention: Convention): Convention {
        const copy: Convention = Object.assign({}, convention);
        copy.startDate = this.dateUtils.convertLocalDateToServer(convention.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(convention.endDate);
        return copy;
    }
}
