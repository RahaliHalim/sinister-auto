import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { VehicleUsage } from './vehicle-usage.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehicleUsageService {

    private resourceUrl = 'api/vehicle-usages';
    private resourceSearchUrl = 'api/_search/vehicle-usages';

    constructor(private http: Http) { }

    create(vehicleUsage: VehicleUsage): Observable<VehicleUsage> {
        const copy = this.convert(vehicleUsage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(vehicleUsage: VehicleUsage): Observable<VehicleUsage> {
        const copy = this.convert(vehicleUsage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<VehicleUsage> {
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

    private convert(vehicleUsage: VehicleUsage): VehicleUsage {
        const copy: VehicleUsage = Object.assign({}, vehicleUsage);
        return copy;
    }
}
