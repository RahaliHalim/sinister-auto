import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Vehicle } from './vehicle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehicleService {

    private resourceUrl = 'api/vehicles';
    private resourceSearchUrl = 'api/_search/vehicles';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(vehicle: Vehicle): Observable<Vehicle> {
        const copy = this.convert(vehicle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(vehicle: Vehicle): Observable<Vehicle> {
        const copy = this.convert(vehicle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Vehicle> {
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
        entity.firstEntryIntoService = this.dateUtils
            .convertLocalDateFromServer(entity.firstEntryIntoService);
    }

    private convert(vehicle: Vehicle): Vehicle {
        const copy: Vehicle = Object.assign({}, vehicle);
        copy.firstEntryIntoService = this.dateUtils
            .convertLocalDateToServer(vehicle.firstEntryIntoService);
        return copy;
    }
}
