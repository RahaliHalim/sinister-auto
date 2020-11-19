import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DetailsPieces } from './details-pieces.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DetailsPiecesService {

    private resourceUrl = 'api/details-pieces';
    private resourceUrlOther = 'api/details-piecesOther';
    private resourceSearchUrl = 'api/_search/details-pieces';
    private resourceUrlQuotationMP = 'api/details-pieces/quotation-mp';
    private  resourceUrlModified= 'api/details-pieces/line-modified';
    private resourceUrlForMp = 'api/details-pieces-for-quotation-mp';
    private resourceUrlForMPDelete = 'api/details-pieces-by-mp';
    private resourceUrlForMPCompDelete = 'api/details-pieces-by-mp-comp';
    private resourceUrlDeleteByQuotation = 'api/apecs/delete-by-quotation';
    private resourceUrlQuotationAnnulee = 'api/details-pieces-quotation-annulee';
    constructor(private http: Http) { }

    create(detailsPieces: DetailsPieces): Observable<DetailsPieces> {
        const copy = this.convert(detailsPieces);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(detailsPieces: DetailsPieces): Observable<DetailsPieces> {
        //const copy = this.convert(detailsPieces);

        return this.http.put(this.resourceUrl, detailsPieces).map((res: Response) => {

            return res.json();
        });
    }

    find(id: number): Observable<DetailsPieces> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    getDetailsPieceBylineModified(quotationId: number,lineModified: number): Observable<DetailsPieces> {
        return this.http.get(`${this.resourceUrlModified}/${quotationId}/${lineModified}`).map((res: Response) => {
            return res.json();
        });
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByDevisAndType(idDevis: number, idType: number, isMo: boolean): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${idDevis}/${idType}/${isMo}`)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByDevisAndTypeOther(idDevis: number, idType: number, isMo: boolean): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlOther}/${idDevis}/${idType}/${isMo}`)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByDevis(idDevis: number): Observable<boolean> {
        return this.http.get(`${this.resourceUrlForMp}/${idDevis}`)
            .map((res: Response) => {
                const jsonResponse = res.json();
                return jsonResponse;});
    }

    queryByQuotationMPAndType(idDevis: number, idType: number, isMo: boolean): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlQuotationMP}/${idDevis}/${idType}/${isMo}`)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    deleteByQuotationId(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlDeleteByQuotation}/${id}`);
    }

    deleteByQuery(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlForMPDelete}/${id}`);
    }

    deleteDetailsPiecesQuotationAnnule(sinisterPecId: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlQuotationAnnulee}/${sinisterPecId}`);
    }

    deleteByQueryMP(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlForMPCompDelete}/${id}`);
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

    private convert(detailsPieces: DetailsPieces): DetailsPieces {
        const copy: DetailsPieces = Object.assign({}, detailsPieces);
        return copy;
    }
}
