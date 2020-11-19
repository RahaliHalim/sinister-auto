import { createRequestOption, ResponseWrapper } from "../../shared";
import { Observable } from "rxjs/Observable";
import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { QuotationMP } from "./quotation-mp.model";
import { PrimaryQuotation } from "../PrimaryQuotation/primary-quotation.model";


@Injectable()
export class QuotationMPService {
    
    private resourceUrl = 'api/quotation-mp';
    constructor(private http: Http) { }

    
    create(quotation: QuotationMP): Observable<QuotationMP> {
        const copy = this.convert(quotation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    put(quotation: QuotationMP): Observable<QuotationMP> {
        const copy = this.convert(quotation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<QuotationMP> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    private convert(quotation: QuotationMP): QuotationMP {
        const copy: QuotationMP = Object.assign({}, quotation);
        return copy;
    }
}
