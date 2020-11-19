import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefNatureExpertise } from './ref-nature-expertise.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefNatureExpertiseService {

    private resourceUrl = 'api/ref-nature-expertises';
    private resourceSearchUrl = 'api/_search/ref-nature-expertises';

    constructor(private http: Http) { }

    create(refNatureExpertise: RefNatureExpertise): Observable<RefNatureExpertise> {
        const copy = this.convert(refNatureExpertise);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refNatureExpertise: RefNatureExpertise): Observable<RefNatureExpertise> {
        const copy = this.convert(refNatureExpertise);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefNatureExpertise> {
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

    private convert(refNatureExpertise: RefNatureExpertise): RefNatureExpertise {
        const copy: RefNatureExpertise = Object.assign({}, refNatureExpertise);
        return copy;
    }
}
