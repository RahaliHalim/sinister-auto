import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { ParamsValueDrools } from './drools-params.model';

@Injectable()
export class DroolsParamsService {


    private resourceUrl = 'api/paramValueDrools';
    private resourceUrlCompagnieClients = 'api/compagnieclients';

    constructor(private http: Http) { }

    create(paramsValueDrools: ParamsValueDrools): Observable<ParamsValueDrools> {
        const copy = this.convert(paramsValueDrools);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }
    
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlCompagnieClients, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(paramsValueDrools: ParamsValueDrools): ParamsValueDrools {
        const copy: ParamsValueDrools = Object.assign({}, paramsValueDrools);
        return copy;
    }
}