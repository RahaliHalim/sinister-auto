import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RefGrounds } from './ref-grounds.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefGroundsService {
    public refGroundsId: any;
    public id: any;

    private resourceUrl = 'api/ref-grounds';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(refGrounds: RefGrounds): Observable<RefGrounds> {
        const copy = this.convert(refGrounds);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refGrounds: RefGrounds): Observable<RefGrounds> {
        this.refGroundsId = refGrounds.id;
        const copy = this.convert(refGrounds);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<RefGrounds> {
        this.id = id;
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findByStatus(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/status/${id}`)
            .map((res: Response) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(refGrounds: RefGrounds): RefGrounds {
        const copy: RefGrounds = Object.assign({}, refGrounds);
        return copy;
    }
}
