import { createRequestOption, ResponseWrapper } from "../../shared";
import { Observable } from "rxjs/Observable";
import { Http, Response } from "@angular/http";
import { Injectable } from "@angular/core";
import { Quotation } from "./quotation.model";
import { PrimaryQuotation } from "../PrimaryQuotation/primary-quotation.model";
import { QuotationMP } from "../quotation-m-p";


@Injectable()
export class QuotationService {
    quotation: any;
    public id: any;
    private resourceUrl = 'api/quotation';
    private resourceUrlCompl = 'api/sinister-pecs/quote/complementary';
    public resourceUrlAttachments = 'api/quotation/attachement';
    private motifQuotationUrl = 'api/quotation/non-confirme-motif';
    private resourceUrlJounal = 'api/journal/quotation';
    private resourceUrlCreate = 'api/quotation-mp';
    private resourceUrlHistoryDevis = 'api/quotation/Byhistory';
    private resourceUrlForMPDelete = 'api/quotation/additionel-quote';
    private resourceUrlDeleteQuote = 'api/quotation/delete-quote';
    private resourceUrlSendFirstConnectionGtEstimate = 'api/web-sockets/gt-estimate-first-connection';
    constructor(private http: Http) { }

    update(quotation: Quotation): Observable<Quotation> {

        return this.http.put(this.resourceUrl, quotation).map((res: Response) => {
            return res.json();
        });
    }

    createQuotationMP(quotation: QuotationMP): Observable<QuotationMP> {

        const copy = this.convertMP(quotation);
        return this.http.post(this.resourceUrlCreate, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Quotation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findHistoryDevis(id: number, entityId: number, entityName: string): Observable<Quotation> {
        console.log("find service--****");
        return this.http.get(`${this.resourceUrlHistoryDevis}/${id}/${entityId}/${entityName}`).map((res: Response) => {

            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    deleteAdditionnelQuote(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlForMPDelete}/${id}`);
    }

    findQuotCompl(id: number): Observable<Quotation> {
        console.log("find service--****");
        return this.http.get(`${this.resourceUrlCompl}/${id}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlDeleteQuote}/${id}`);
    }

    sendFirstConnectionEventForGtEstimate(sinisterPecId: number, quoteId: number): Observable<Response> {
        return this.http.get(`${this.resourceUrlSendFirstConnectionGtEstimate}/${sinisterPecId}/${quoteId}`);
    }


    /* la liste de attachements  */
    getAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlAttachments}/${id}`, options).map((res: Response) =>
            this.convertResponse(res));
    }
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    nonConfirmeMotif(id: number, motifs: number[]): Observable<PrimaryQuotation> {
        this.find(id).subscribe((res) => this.quotation = res)
        const copy = this.convert(this.quotation);
        return this.http.put(`${this.motifQuotationUrl}/${id}/${motifs}`, this.quotation).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findjournal(id: number): Observable<PrimaryQuotation> {
        this.id = id;
        console.log("get id Primary Quotation " + id);
        return this.http.get(`${this.resourceUrlJounal}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    private convert(quotation: PrimaryQuotation): PrimaryQuotation {
        const copy: PrimaryQuotation = Object.assign({}, quotation);
        return copy;
    }

    private convertMP(quotation: QuotationMP): QuotationMP {
        const copy: QuotationMP = Object.assign({}, quotation);
        return copy;
    }
}
