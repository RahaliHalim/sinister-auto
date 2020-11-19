import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { ParamsDrools } from './drools.model';
import { JhiDateUtils } from 'ng-jhipster';

@Injectable()
export class DroolsService {


    private resourceUrl = 'api/paramDrools';
    

    constructor(private http: Http, private dateUtils: JhiDateUtils) {}

   
    update(paramsDrools: ParamsDrools): Observable<ParamsDrools> {
        const copy = this.convert(paramsDrools);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(paramsDrools: ParamsDrools): ParamsDrools {
        const copy: ParamsDrools = Object.assign({}, paramsDrools);
        return copy;
    }

    private convertItemFromServer(entity: any) {
        entity.dateGeneration = this.dateUtils
            .convertDateTimeFromServer(entity.dateGeneration);
    }

}