import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefModeGestion } from './ref-mode-gestion.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefModeGestionService {

    private resourceUrl = 'api/ref-mode-gestions';
    private resourceModesByClientUrl = 'api/convention/packs/modeGestion';
    private resourceSearchUrl = 'api/_search/ref-mode-gestions';

    constructor(private http: Http) { }

    create(refModeGestion: RefModeGestion): Observable<RefModeGestion> {
        const copy = this.convert(refModeGestion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refModeGestion: RefModeGestion): Observable<RefModeGestion> {
        const copy = this.convert(refModeGestion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find( id: number ): Observable<RefModeGestion> {
        if ( id ) {
            return this.http.get( `${this.resourceUrl}/${id}` ).map(( res: Response ) => {
                //return res.json();
                if ( res ) {
                    return res.json();
                } else {
                    throw new Error( 'Pas de mode de gestion' );
                }
            } );
        } else {
            console.log( 'find() RefModeGestionid not found!' );
        }

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

    findModesGestionByClient(partnerId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceModesByClientUrl}/${partnerId}`).map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(refModeGestion: RefModeGestion): RefModeGestion {
        const copy: RefModeGestion = Object.assign({}, refModeGestion);
        return copy;
    }
}
