import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefEtatDossier } from './ref-etat-dossier.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefEtatDossierService {

    private resourceUrl = 'api/ref-etat-dossiers';
    private resourceSearchUrl = 'api/_search/ref-etat-dossiers';

    constructor(private http: Http) { }

    create(refEtatDossier: RefEtatDossier): Observable<RefEtatDossier> {
        const copy = this.convert(refEtatDossier);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refEtatDossier: RefEtatDossier): Observable<RefEtatDossier> {
        const copy = this.convert(refEtatDossier);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefEtatDossier> {
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

    private convert(refEtatDossier: RefEtatDossier): RefEtatDossier {
        const copy: RefEtatDossier = Object.assign({}, refEtatDossier);
        return copy;
    }
}
