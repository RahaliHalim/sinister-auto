import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { VehiculeAssure } from './vehicule-assure.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { VehicleBrandModel } from '../vehicle-brand-model/vehicle-brand-model.model';

@Injectable()
export class VehiculeAssureService {
    private resourceUrlVehiculeByImmatriculation = 'api/vehicule-assurees';
    private resourceUrlVehiculeByContrat = 'api/vehicule-assures/contratAssurance';
    private resourceUrl = 'api/vehicule-assures';
    private resourceUrlDeleteVehiculeByContrat = 'api/vehicule-assures/contrat';
    private resourceUrlModel = 'api/ref-models/marque';
    private resourceSearchUrl = 'api/_search/vehicule-assures';
    private resourceUrlListesVehicules = 'api/vehicule-assurees-list';
    private resourceUrlCompagnyNameImmatriculation = 'api/vehicule-assures/company-immatriculation';
    private resourceUrlClientIdImmatriculation = 'api/vehicule-assures/client-immatriculation';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(vehiculeAssure: VehiculeAssure): Observable<VehiculeAssure> {

        var copy;
        if (!vehiculeAssure.id) {
            copy = this.convert(vehiculeAssure);
        } else {
            vehiculeAssure.datePCirculation = new Date(vehiculeAssure.datePCirculation);
            copy = vehiculeAssure;
        }
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(vehiculeAssure: VehiculeAssure): Observable<VehiculeAssure> {
        const copy = this.convert(vehiculeAssure);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findVehiculeByCompagnyIdAndImmatriculation(clientId?: number, immatriculationVehicule?: String): Observable<VehiculeAssure> {
        return this.http.get(`${this.resourceUrlClientIdImmatriculation}/${clientId}/${immatriculationVehicule}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<VehiculeAssure> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findVehicule(id: number): Observable<VehiculeAssure> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findByImmatriculation(immatriculationVehicule?: String): Observable<VehiculeAssure> {
        return this.http.get(`${this.resourceUrlVehiculeByImmatriculation}/${immatriculationVehicule}`).map((res: Response) => {
            return res.json();
        });
    }
    findByContrat(contratId?: number): Observable<VehiculeAssure[]> {
        return this.http.get(`${this.resourceUrlVehiculeByContrat}/${contratId}`).map((res: Response) => {
            return res.json();
        });
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }
    deleteByContrat(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlDeleteVehiculeByContrat}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }
    findModelByMarque(id: number): Observable<VehicleBrandModel[]> {
        return this.http.get(`${this.resourceUrlModel}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertResponseVehicule(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    findListesVehiculesByImmatriculation(immatriculationVehicule?: String, req?: any): Observable<ResponseWrapper> {
        immatriculationVehicule = immatriculationVehicule.toUpperCase();
        return this.http.get(`${this.resourceUrlListesVehicules}/${immatriculationVehicule}`, req)
            .map((res: Response) => this.convertResponseVehicule(res));
    }

    findVehiculeByCompagnyNameAndImmatriculation(compagnyName?: String, immatriculationVehicule?: String): Observable<VehiculeAssure> {
        return this.http.get(`${this.resourceUrlCompagnyNameImmatriculation}/${compagnyName}/${immatriculationVehicule}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }


    private convertItemFromServer(entity: any) {
        entity.datePCirculation = this.dateUtils
            .convertLocalDateFromServer(entity.datePCirculation);

    }

    private convert(vehiculeAssure: VehiculeAssure): VehiculeAssure {
        const copy: VehiculeAssure = Object.assign({}, vehiculeAssure);
        //copy.datePCirculation = this.dateUtils.toDate(vehiculeAssure.datePCirculation);
        copy.datePCirculation = this.dateUtils
            .convertLocalDateToServer(vehiculeAssure.datePCirculation);
        return copy;
    }
}
