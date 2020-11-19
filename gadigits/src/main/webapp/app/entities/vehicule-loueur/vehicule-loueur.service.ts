import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { VehiculeLoueur } from './vehicule-loueur.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehiculeLoueurService {

    private resourceUrl = 'api/vehicule-loueurs';
    private resourceSearchUrl = 'api/_search/vehicule-loueurs';

    constructor(private http: Http) { }

    create(vehiculeLoueur: VehiculeLoueur): Observable<VehiculeLoueur> {
        const copy = this.convert(vehiculeLoueur);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(vehiculeLoueur: VehiculeLoueur): Observable<VehiculeLoueur> {
        const copy = this.convert(vehiculeLoueur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<VehiculeLoueur> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByImmatriculation(imm: string): Observable<VehiculeLoueur> {
        return this.http.get(`${this.resourceUrl}/imm/${imm}`).map((res: Response) => {
            return res.json();
        });
    }
    findByLoueur(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/loueur/${id}`).map((res: Response) => this.convertResponse(res));
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

    private convert(vehiculeLoueur: VehiculeLoueur): VehiculeLoueur {
        const copy: VehiculeLoueur = Object.assign({}, vehiculeLoueur);
        return copy;
    }
}
