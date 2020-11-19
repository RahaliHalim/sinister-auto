import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ViewSinisterPec } from './view-sinister-pec.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ViewSinisterPecService {

    private resourceUrl = 'api/view-sinister-pecs';
    private resourceSearchUrl = 'api/_search/view-sinister-pecs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(viewSinisterPec: ViewSinisterPec): Observable<ViewSinisterPec> {
        const copy = this.convert(viewSinisterPec);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(viewSinisterPec: ViewSinisterPec): Observable<ViewSinisterPec> {
        const copy = this.convert(viewSinisterPec);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ViewSinisterPec> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
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

    getAllAcceptedAndNoReparator(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/accepted-no-reparator/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllSinPecWithDecision(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/with-decision`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllSinPecWithDecisionForAutresPiecesJointes(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/with-decision-for-autres-pieces-jointes/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllSinPecForModificationPrestation(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/modification-prestation`, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryPrestationsInNotCancelExpertMission(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/in-cancel-mission-expert/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryPrestationsInMissionExpert(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/in-mission-expert`, options)
            .map((res: Response) => this.convertResponse(res));
    }


    queryPrestationsVerification(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/verification`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllAcceptedAndHasReparator(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/accepted-has-reparator`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllForReparator(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/for-reparator/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllPecsForUpdateDevis(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/update-devis/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllSinisterPecByAssignedId(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/assigned/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryToApprove(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/to-approve/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryForDerogation(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/for-derogation/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryToConfirmRefusedPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/refused-pec-confirm/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllSinPecForSignatureBonSortie(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/signature-bon-sortie/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllPecsInRevueValidationDevis(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/revue/validation/devis/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllForExpertOpinion(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/expert-opinion/${id}`, options)
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
    }

    private convert(viewSinisterPec: ViewSinisterPec): ViewSinisterPec {
        const copy: ViewSinisterPec = Object.assign({}, viewSinisterPec);
        copy.declarationDate = this.dateUtils
            .convertLocalDateToServer(viewSinisterPec.declarationDate);
        copy.incidentDate = this.dateUtils
            .convertLocalDateToServer(viewSinisterPec.incidentDate);
        return copy;
    }
}
