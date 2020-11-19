import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from "rxjs/Rx";

import { NaturePanne } from "./nature-panne.model";
import { ResponseWrapper, createRequestOption } from "../../shared";

@Injectable()
export class NaturePanneService {
    private resourceUrl = "api/ref-nature-pannes";
    private resourceUrlActive = "api/ref-nature-pannes/active";

    private resourceUrlByLabel = "api/ref-nature-pannes/label";

    constructor(private http: Http) {}

    create(NaturePanne: NaturePanne): Observable<NaturePanne> {
        const copy = this.convert(NaturePanne);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(NaturePanne: NaturePanne): Observable<NaturePanne> {
        const copy = this.convert(NaturePanne);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<NaturePanne> {
        return this.http
            .get(`${this.resourceUrl}/${id}`)
            .map((res: Response) => {
                return res.json();
            });
    }

    findByLabel(label: string): Observable<NaturePanne> {
        return this.http
            .get(`${this.resourceUrlByLabel}/${label}`)
            .map((res: Response) => {
                return res.json();
            });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findAllActive(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(this.resourceUrlActive, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(NaturePanne: NaturePanne): NaturePanne {
        const copy: NaturePanne = Object.assign({}, NaturePanne);
        return copy;
    }
}
