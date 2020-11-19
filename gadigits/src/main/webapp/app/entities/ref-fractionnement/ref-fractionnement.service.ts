import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefFractionnement } from './ref-fractionnement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefFractionnementService {

    private resourceUrl = 'api/ref-fractionnements';
    private resourceSearchUrl = 'api/_search/ref-fractionnements';

    constructor(private http: Http) { }

    create(refFractionnement: RefFractionnement): Observable<RefFractionnement> {
        const copy = this.convert(refFractionnement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refFractionnement: RefFractionnement): Observable<RefFractionnement> {
        const copy = this.convert(refFractionnement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefFractionnement> {
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

    private convert(refFractionnement: RefFractionnement): RefFractionnement {
        const copy: RefFractionnement = Object.assign({}, refFractionnement);
        return copy;
    }
}
