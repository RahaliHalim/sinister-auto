import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefNatureContrat } from './ref-nature-contrat.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefNatureContratService {

    private resourceUrl = 'api/ref-nature-contrats';
    private resourceSearchUrl = 'api/_search/ref-nature-contrats';

    constructor(private http: Http) { }

    create(refNatureContrat: RefNatureContrat): Observable<RefNatureContrat> {
        const copy = this.convert(refNatureContrat);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refNatureContrat: RefNatureContrat): Observable<RefNatureContrat> {
        const copy = this.convert(refNatureContrat);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefNatureContrat> {
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

    private convert(refNatureContrat: RefNatureContrat): RefNatureContrat {
        const copy: RefNatureContrat = Object.assign({}, refNatureContrat);
        return copy;
    }
}
