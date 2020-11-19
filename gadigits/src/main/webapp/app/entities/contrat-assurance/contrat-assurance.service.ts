import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { ContratAssurance } from './contrat-assurance.model';
import { VehiculeContrat } from './vehicule-contrat.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Recherche, Search } from '../sinister';
import { Contrat } from './contrat.model';
import { SouscritpionGa } from './souscription-ga/souscriptionGa.model';
import { SuiviDossiers } from '../report';

@Injectable()
export class ContratAssuranceService {

    private resourceUrl1 = 'api/contrat';
    private resourceUrl = 'api/contrat-assurances';
    private resourceSearchUrl = 'api/_search/contrat-assurances';
    private resourceUrlVehicule = 'api/contrat-assurances/vehicule';
    private resourceVehiculeContratUrl = 'api/vehicule-assures/vehiculecontratAssurance';
    private resourceUrlContrat = 'api/view/contrat';
    private resourceUrlSouscription = 'api/view/souscriptionGa';
    private resourceUrlListCharge = 'api/view/listCharge';
    private resourceUrlTiers = 'api/contrat/tiers';
    private resourceUrlSuiviDossiers = 'api/view/suiviDossiers';
    private resourceUrlByImmatAndVehicule = 'api/contrat-client-immatriculation';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(contratAssurance: ContratAssurance): Observable<ContratAssurance> {
        const copy = this.convert(contratAssurance);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByVehicule(id: number): Observable<ContratAssurance> {
        return this.http.get(`${this.resourceUrlVehicule}/${id}`).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
            }
            return null;
        });
    }

    findByImmatriculation(immatriculationVehicule?: String): Observable<ContratAssurance> {
        return this.http.get(`${this.resourceUrl1}/${immatriculationVehicule}`).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
            }
            return null;
        });
    }

    findVehiculeByImmatriculationAndClient(immatriculationVehicule?: String, clientId?: number): Observable<ContratAssurance> {
        return this.http.get(`${this.resourceUrlByImmatAndVehicule}/${immatriculationVehicule}/${clientId}`).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
            }
            return null;
        });
    }
    findByPolicyNumber(policy?: ContratAssurance): Observable<ContratAssurance> {
        return this.http.post(`${this.resourceUrl}/bypnumber`, policy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }



    findAllListCharge(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlListCharge, options)
            .map((res: Response) => this.convertResponse(res));
    }



    update(contratAssurance: ContratAssurance): Observable<ContratAssurance> {
        const copy = this.convert(contratAssurance);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ContratAssurance> {
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


    querySuiviDossiers(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlSuiviDossiers, options)
            .map((res: Response) => this.convertResponse(res));
    }

    querySuiviDossiersSearch(search: Search): Observable<SuiviDossiers[]> {
        const copy = this.convertSearch(search);
        return this.http.post(this.resourceUrlSuiviDossiers, copy).map((res: Response) => {
            return res.json();
        });

    }


    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    findVehiculeContrat(req?: any): Observable<VehiculeContrat[]> {
        return this.http.get(this.resourceVehiculeContratUrl).map((res: Response) => {
            return res.json();
        });
    }


    queryContrat(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlContrat, options)
            .map((res: Response) => this.convertResponse(res));
    }



    queryContratSearch(search: Search): Observable<Contrat[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourceUrlContrat}`, copy).map((res: Response) => {
            return res.json();
        });
    }

    querySouscriptionSearch(search: Search): Observable<SouscritpionGa[]> {
        const copy = this.convertSearch(search);
        return this.http.post(this.resourceUrlSouscription, copy).map((res: Response) => {
            return res.json();
        });
    }

    findContractByImmatriculationForTiers(immatriculationVehicule?: String, clientId?: number): Observable<boolean> {
        return this.http.get(`${this.resourceUrlTiers}/${immatriculationVehicule}/${clientId}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    private convertSearch(search: Search): Search {
        const copy: Search = Object.assign({}, search);
        copy.startDate = this.dateUtils.convertLocalDateToServer(search.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(search.endDate);
        return copy;
    }



    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.debutValidite = this.dateUtils.convertLocalDateFromServer(entity.debutValidite);
        entity.finValidite = this.dateUtils.convertLocalDateFromServer(entity.finValidite);
        entity.deadlineDate = this.dateUtils.convertLocalDateFromServer(entity.deadlineDate);
        entity.amendmentEffectiveDate = this.dateUtils.convertLocalDateFromServer(entity.amendmentEffectiveDate);
        entity.receiptValidityDate = this.dateUtils.convertLocalDateFromServer(entity.receiptValidityDate);

    }

    private convert(contratAssurance: ContratAssurance): ContratAssurance {
        const copy: ContratAssurance = Object.assign({}, contratAssurance);
        copy.debutValidite = this.dateUtils.convertLocalDateToServer(contratAssurance.debutValidite);
        copy.finValidite = this.dateUtils.convertLocalDateToServer(contratAssurance.finValidite);
        copy.deadlineDate = this.dateUtils.convertLocalDateToServer(contratAssurance.deadlineDate);
        copy.amendmentEffectiveDate = this.dateUtils.convertLocalDateToServer(contratAssurance.amendmentEffectiveDate);
        copy.receiptValidityDate = this.dateUtils.convertLocalDateToServer(contratAssurance.receiptValidityDate);
        return copy;
    }


    querySoucription(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlSouscription, options)
            .map((res: Response) => this.convertResponse(res));
    }

    exportSouscriptionToExcel(search: string): Observable<Blob> {
        if (search === undefined || search === null || search === "") {
            search = '-1';
        }
        return this.http.get(`${this.resourceUrlContrat}/export/excel/${search}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });
    }

    exportCaPoliciesToExcel(search: string): Observable<Blob> {
        if (search === undefined || search === null || search === "") {
            search = '-1';
        }
        return this.http.get(`${this.resourceUrlSouscription}/export/excel/${search}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });
    }

}
