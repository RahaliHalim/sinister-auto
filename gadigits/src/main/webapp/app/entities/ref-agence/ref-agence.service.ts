import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefAgence } from './ref-agence.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefAgenceService {

    private resourceUrl = 'api/ref-agences';
    private resourceSearchUrl = 'api/_search/ref-agences';
    private resourceUrlAgence = 'api/ref-agences/compagnie';
    private resourceUrlAgenceCompagnie = 'api/ref-agences/authorities/compagnie';
    private resourceUrlAgenceByAgent = 'api/ref-agences/agent-general';

    constructor(private http: Http) { }

    create(refAgence: RefAgence): Observable<RefAgence> {
        const copy = this.convert(refAgence);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refAgence: RefAgence): Observable<RefAgence> {
        const copy = this.convert(refAgence);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefAgence> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findAgenceByCompagnie(id: number): Observable<RefAgence[]> {
        return this.http.get(`${this.resourceUrlAgence}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findAgenceByAuthorityCompagnie(): Observable<RefAgence[]> {
        return this.http.get(this.resourceUrlAgenceCompagnie).map((res: Response) => {
            return res.json();
        });
    }
    findAgenceByAgent(): Observable<RefAgence> {
        return this.http.get(this.resourceUrlAgenceByAgent).map((res: Response) => {
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

    private convert(refAgence: RefAgence): RefAgence {
        const copy: RefAgence = Object.assign({}, refAgence);
        return copy;
    }
}
