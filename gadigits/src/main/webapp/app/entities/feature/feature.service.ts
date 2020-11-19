import { createRequestOption, ResponseWrapper } from "../../shared";
import { Observable } from "rxjs/Observable";
import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { Features } from "./feature.model";

@Injectable()
export class FeatureService {


    private resourceUrl = 'api/feature';
    private resourceUrlWithoutPagination = 'api/feature-with-pagination';
    private resourceUrlByEntiti = 'api/feature/entiti';
    private resourceUrlWithourParent = 'api/feature-without-parent';

    constructor(private http: Http) { }

    create(feature: Features): Observable<Features> {
       
        return this.http.post(this.resourceUrl, feature).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findFeatureWithPagination(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlWithoutPagination, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findFeatureWithoutParent(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlWithourParent, options)
            .map((res: Response) => this.convertResponse(res));
    }

    find(id: number): Observable<Features> {
        
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findByEntitiId(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlByEntiti}/${id}`)
        .map((res: Response) => this.convertResponse(res));
       
    }
    deleteById(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
    }
   
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

}