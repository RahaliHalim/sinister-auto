import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefPack } from './ref-pack.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { TypeaheadOptions } from 'ngx-bootstrap';

@Injectable()
export class RefPackService {
 private   id ;
    private resourceUrl = 'api/ref-packs';
    private apiUrl = 'api/packs';
    private resourceSearchUrl = 'api/_search/ref-packs';
    private resourceUrlUnbloquedPck = 'api/ref-packs/nonbloque';
    private resourceBloquePckUrl = 'api/ref-packs/bloque';
    private  resourceUrl1  = 'api//ref-mode-Exclusions';
    constructor(private http: Http) { }

    create(refPack: RefPack): Observable<RefPack> {
        const copy = this.convert(refPack);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refPack: RefPack): Observable<RefPack> {
        const copy = this.convert(refPack);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefPack> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByUserMode(id: number, idUser: number): Observable<RefPack> {
        return this.http.get(`${this.resourceUrl}/${id}/${idUser}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryExclusion(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl1, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findByServiceType(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/ts/${id}`)
            .map((res: Response) => this.convertResponse(res));
    }

    findByPartner(partnerId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.apiUrl}/company/${partnerId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    queryNonBloque(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlUnbloquedPck, options)
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
    bloque(id: number): Observable<Response> {
        return this.http.patch(`${this.resourceBloquePckUrl}/${id}`,TypeaheadOptions);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(refPack: RefPack): RefPack {
        const copy: RefPack = Object.assign({}, refPack);
        return copy;
    }
}
