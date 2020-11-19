import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { VisAVis } from './vis-a-vis.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()

export class VisAVisService {

    visAViss: VisAVis[];
    private resourceUrl = 'api/vis-a-viss';
    private resourceSearchUrl = 'api/_search/vis-a-viss';
    private resourceUrlContactByEntity = 'api/contacts/entity';


    constructor(private http: Http) { }

    create(visAVis: VisAVis): Observable<VisAVis> {
        const copy = this.convert(visAVis);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(visAVis: VisAVis): Observable<VisAVis> {
        const copy = this.convert(visAVis);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    findByEntity(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlContactByEntity}/${id}`).map((res: Response) => this.convertResponse(res));
    }
    find(id: number): Observable<VisAVis> {
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

    private convert(visAVis: VisAVis): VisAVis {
        const copy: VisAVis = Object.assign({}, visAVis);
        return copy;
    }
}
