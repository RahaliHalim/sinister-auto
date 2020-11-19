import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Apec } from './apec.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';

@Injectable()
export class ApecService {

    private resourceUrl = 'api/apecs';
    private resourceUrlFixe = 'api/apecs/etat-fixe';
    private resourceSearchUrl = 'api/_search/apecs';
    private resourceUrlSignatureAccord  = 'api/signature-accord';
    private resourceUrlExportAccordPriseEnCharge = 'api/accordPriseChargePdf';
    private resourceUrlByQuotation = 'api/apecs/quotation';
    private resourceUrlBySinisterPec = 'api/apecs-by-pec';
    private resourceUrlForMP = 'api/apecs/sin-pec-devis-com';
    private resourceUrlForFacture = 'api/apecs-for-facture';
    private resourceUrlApecByDevis = 'api/apecs/sin-pec-by-quotation';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(apec: Apec): Observable<Apec> {
        const copy = this.convert(apec);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(apec: Apec): Observable<Apec> {
        //const copy = this.convert(apec);
        return this.http.put(this.resourceUrl, apec).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Apec> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByQuotation(id: number): Observable<Apec> {
        return this.http.get(`${this.resourceUrlByQuotation}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByDecision(etat: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/decision/${etat}`)
            .map((res: Response) => this.convertResponse(res));
    }
    queryByStatus(etat: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/status/${etat}`)
        .map((res: Response) => this.convertResponse(res));
    }

    queryListBySinPec(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/apecs-by-quotation/${id}`)
        .map((res: Response) => this.convertResponse(res));
    }

    queryBySinisterPec(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlBySinisterPec}/${id}`)
        .map((res: Response) => this.convertResponse(res));
    }

    findByEtatAccord(userId: number,etat: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlSignatureAccord}/${userId}/${etat}`, options)

            .map((res: Response) => this.convertResponse(res));
    }

    findAccordBySinPecAndEtat(id: number, etat: number): Observable<Apec>{
        return this.http.get(`${this.resourceUrl}/${id}/${etat}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findAccordBySinPecAndEtatFixe(id: number): Observable<Apec>{
        return this.http.get(`${this.resourceUrlFixe}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    deleteAPecDevisCompl(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlForMP}/${id}`);
    }

    deleteApecByDevis(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlApecByDevis}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }
    generateAccordPrisCharge(sinisterPec: SinisterPec, id: number,confirmation: boolean, etat: number, typeAccord: String): Observable<Response> {
        //const copy = this.convertPec(sinisterPec);
        return this.http.post(`${this.resourceUrlExportAccordPriseEnCharge}/${id}/${confirmation}/${etat}/${typeAccord}`, sinisterPec);
    }

    updateForFacture(sinPecId: number): Observable<Apec> {
        //const copy = this.convert(apec);
        return this.http.put(`${this.resourceUrlForFacture}/${sinPecId}`, sinPecId).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateGeneration = this.dateUtils
            .convertLocalDateFromServer(entity.dateGeneration);
    }

    private convert(apec: Apec): Apec {
        const copy: Apec = Object.assign({}, apec);
        copy.dateGeneration = this.dateUtils
            .convertLocalDateToServer(apec.dateGeneration);
        return copy;
    }

    private convertPec(sinisterPec: SinisterPec): SinisterPec {
        const copy: SinisterPec = Object.assign({}, sinisterPec);
        //copy.declarationDate = this.dateUtils.toDate(sinisterPec.declarationDate);
        copy.dateRDVReparation = this.dateUtils.toDate(sinisterPec.dateRDVReparation);
        copy.dateDerniereMaj = this.dateUtils.toDate(sinisterPec.dateDerniereMaj);
        //copy.dateReceptionVehicule = this.dateUtils.toDate(sinisterPec.dateReceptionVehicule);
        
        return copy;
    }
}
