import { createRequestOption, ResponseWrapper } from "../../shared";
import { Observable } from "rxjs/Observable";
import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { PrimaryQuotation } from "./primary-quotation.model";
import { PieceJointe } from "../piece-jointe";
import { JhiDateUtils } from 'ng-jhipster';

@Injectable()
export class PrimaryQuotationService {
    private resourceUrl = 'api/primaryQuotation';
    private resourceUrlCreate = 'api/primaryQuotation/create';
    private resourceUrlUpdate = 'api/primaryQuotation/update';
    private resourceUrlByPrestationId = 'api/primaryQuotation/prestation';
    private resourceUrlQuotation = 'api/quotation';
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(formdata: FormData, primaryQuotation: PrimaryQuotation): Observable<PrimaryQuotation> {
        const copy = this.convert(primaryQuotation);
        formdata.append('primaryQuotation', new Blob([JSON.stringify(copy)], {type: 'application/json'}));
        return this.http.post(`${this.resourceUrlCreate}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    update(formdata: FormData, primaryQuotation: PrimaryQuotation): Observable<PrimaryQuotation> {
        const copy = this.convert(primaryQuotation);
        formdata.append('primaryQuotation', new Blob([JSON.stringify(copy)], {type: 'application/json'}));
        return this.http.post(`${this.resourceUrlUpdate}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    updateQuotation(primaryQuotation: PrimaryQuotation): Observable<PrimaryQuotation> {
        const copy = this.convert(primaryQuotation);
        return this.http.put(this.resourceUrlQuotation, copy).map((res: Response) => {
            return res.json();
        });
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findByPrestationId(id: number): Observable<PrimaryQuotation> {
        return this.http.get(`${this.resourceUrlByPrestationId}/${id}`).map((res: Response) => {

            return res.json();
        });
    }
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        console.log("list responseservice" + res);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
    private convert(primaryQuotation: PrimaryQuotation): PrimaryQuotation {
        const copy: PrimaryQuotation = Object.assign({}, primaryQuotation);
        //copy.creationDate = this.dateUtils.convertLocalDateToServer(primaryQuotation.creationDate);
        //   copy.expertiseDate = this.dateUtils.convertLocalDateToServer(primaryQuotation.expertiseDate);
        return copy;
    }
    private convertItemFromServer(entity: any) {
        // entity.creationDate = this.dateUtils.convertLocalDateFromServer(entity.creationDate);
        // entity.expertiseDate = this.dateUtils.convertLocalDateFromServer(entity.expertiseDate);
    }

}
