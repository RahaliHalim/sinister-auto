import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { ObservationApec } from './observation-apec.model';

@Injectable()
export class ObservationApecService {

    private resourceUrl = 'api/observation-apecs';
    private resourceUrlByApec = 'api/observation-apecs-by-apec';

    constructor(private http: Http) { }

    create(observationApec: ObservationApec): Observable<ObservationApec> {
        return this.http.post(this.resourceUrl, observationApec).map((res: Response) => {
            return res.json();
        });
    }

    update(observationApec: ObservationApec): Observable<ObservationApec> {
        return this.http.put(this.resourceUrl, observationApec).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ObservationApec> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByApec(id: number): Observable<ObservationApec> {
        return this.http.get(`${this.resourceUrlByApec}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(observationApec: ObservationApec): ObservationApec {
        const copy: ObservationApec = Object.assign({}, observationApec);
        return copy;
    }
}
