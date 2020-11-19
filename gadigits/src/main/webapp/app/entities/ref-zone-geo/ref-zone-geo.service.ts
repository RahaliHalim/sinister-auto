import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefZoneGeo } from './ref-zone-geo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefZoneGeoService {

    private resourceUrl = 'api/ref-zone-geos';
    private resourceSearchUrl = 'api/_search/ref-zone-geos';

    constructor(private http: Http) { }

    create(refZoneGeo: RefZoneGeo): Observable<RefZoneGeo> {
        const copy = this.convert(refZoneGeo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refZoneGeo: RefZoneGeo): Observable<RefZoneGeo> {
        const copy = this.convert(refZoneGeo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefZoneGeo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(refZoneGeo: RefZoneGeo): RefZoneGeo {
        const copy: RefZoneGeo = Object.assign({}, refZoneGeo);
        return copy;
    }
}
