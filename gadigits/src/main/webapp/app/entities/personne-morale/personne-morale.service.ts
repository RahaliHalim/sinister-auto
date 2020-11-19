import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

import { PersonneMorale } from './personne-morale.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonneMoraleService {
    public personneMoraleId: any;
    public id: any;
    private resourceUrl = 'api/personne-morales';
    private resourceSearchUrl = 'api/_search/personne-morales';
    constructor(private http: Http, private route: ActivatedRoute) { }

    create(personneMorale: PersonneMorale): Observable<PersonneMorale> {
        const copy = this.convert(personneMorale);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(personneMorale: PersonneMorale): Observable<PersonneMorale> {
        this.personneMoraleId = personneMorale.id;
        const copy = this.convert(personneMorale);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PersonneMorale> {
        this.id = id;
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

    private convert(personneMorale: PersonneMorale): PersonneMorale {
        const copy: PersonneMorale = Object.assign({}, personneMorale);
        return copy;
    }
}
