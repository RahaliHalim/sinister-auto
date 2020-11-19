import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ViewApecService {

    private resourceUrlSignatureAccordApec = 'api/view/signature-accord';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    findByEtatAccord(userId: number, etat: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlSignatureAccordApec}/${userId}/${etat}`, options)

            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
    
}