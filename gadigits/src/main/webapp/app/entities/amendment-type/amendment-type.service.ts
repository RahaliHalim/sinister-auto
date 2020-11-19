import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AmendmentType } from './amendment-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AmendmentTypeService {

    private resourceUrl = 'api/amendment-types';
    private resourceSearchUrl = 'api/_search/amendment-types';

    constructor(private http: Http) { }

    create(amendmentType: AmendmentType): Observable<AmendmentType> {
        const copy = this.convert(amendmentType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(amendmentType: AmendmentType): Observable<AmendmentType> {
        const copy = this.convert(amendmentType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AmendmentType> {
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

    private convert(amendmentType: AmendmentType): AmendmentType {
        const copy: AmendmentType = Object.assign({}, amendmentType);
        return copy;
    }
}
