import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Governorate } from './governorate.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GovernorateService {

    private resourceUrl = 'api/governorates';
    private resourceUrlGaGeo = 'api/governorates-gageo';
    private resourceSearchUrl = 'api/_search/governorates';

    constructor(private http: Http) { }

    create(governorate: Governorate): Observable<Governorate> {
        const copy = this.convert(governorate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(governorate: Governorate): Observable<Governorate> {
        const copy = this.convert(governorate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Governorate> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByDelegation(id: number): Observable<Governorate> {
        return this.http.get(`${this.resourceUrl}/delegation/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByRegion(id: number): Observable<Governorate[]> {
        return this.http.get(`${this.resourceUrl}/region/${id}`)
            .map((res: Response) => res.json());
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options).map((res: Response) => this.convertResponse(res));
    }

    queryGaGeo(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlGaGeo, options).map((res: Response) => this.convertResponse(res));
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(governorate: Governorate): Governorate {
        const copy: Governorate = Object.assign({}, governorate);
        return copy;
    }
}
