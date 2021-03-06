import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { Estimation } from './estimation.model';
import { PrestationPEC } from './reparation.model';
import { User } from '../../shared/user/user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PrestationPECService {

    public prestationId: any;
    public id: any;
    private resourceUrl = 'api/prestation-pecs';
    private resourceUrlReparateur = 'api/prestation-pecs/reparateur';
    private resourcePECByPrestationUrl = 'api/prestation-pecs/prestation';
    private resourceuserForAuthorityGestionnaireUrl = 'api/authorities/users';
    private resourceUrlExportAffectRep = 'api/affectRepPDF';
    private resourceUrlExportAffectExpert = 'api/affectExpertPDF';
    private resourceUrlExportlettreIDA = 'api/generatelettreIDA';
    private resourceUrlExportlettreReouverture = 'api/generatelettreReouverture';
    private resourceUrlprestationPECWhenTiersNotNull = 'api/prestationPECWhenTiersNotNull';
    private resourceUrlprestationPECAcceptedOrQuaotationInProgress = 'api/prestationPECAcceptedOrQuaotationInProgress';
    private resourceUrlprestationPECAcceptedOrQuaotationRefused = 'api/prestationPECAcceptedOrQuaotationRefused';
    private resourceUrlprestationPECAcceptedOrQuaotationAccord = 'api/prestationPECAcceptedOrQuaotationAccord';
    private resourceUrlprestationPECAcceptedOrQuaotationGeneratedAccord = 'api/prestationPECAcceptedOrQuaotationGeneratedAccord';
    private resourceUrlprestationPECAcceptedOrQuaotationSignAccord = 'api/prestationPECAcceptedOrQuaotationSignAccord';
    private resourceUrlprestationPECAcceptedOrQuaotationValidBill = 'api/prestationPECAcceptedOrQuaotationValidBill';
    private resourceSearchUrl = 'api/_search/prestation-pecs';
    private resourceUrlPrestationToCheck = 'api/prestation-pecs/verification';
    private toDoListUrl = 'api/service-pec-todo';
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }


    create(prestationPEC: PrestationPEC): Observable<PrestationPEC> {
        const copy = this.convert(prestationPEC);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(prestationPEC: PrestationPEC): Observable<PrestationPEC> {
        const copy = this.convert(prestationPEC);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    generatePrestationPEC(prestationPEC: PrestationPEC  ): Observable<Response> {
        const copy = this.convertPrestationPEC(prestationPEC);
        return this.http.post(this.resourceUrlExportAffectExpert, copy);
    }
    generatelettreIDA(prestationPEC: PrestationPEC  ): Observable<Response> {
    console.log( prestationPEC )
        const copy = this.convertPrestationPEC(prestationPEC);
        return this.http.post(this.resourceUrlExportlettreIDA, copy);
    }
    generatelettreReouverture(prestationPEC: PrestationPEC  ): Observable<Response> {
    console.log( prestationPEC )
        const copy = this.convertPrestationPEC(prestationPEC);
        return this.http.post(this.resourceUrlExportlettreReouverture, copy);
    }

    decider(prestationPEC: PrestationPEC, decision: string, motifs: number[]): Observable<PrestationPEC> {
        const copy = this.convert(prestationPEC);
        return this.http.put(`${this.resourceUrl}/${decision}/${motifs}`, copy).map((res: Response) => {
            return res.json();
        });
    }

    findUserForAuthorityGestionnaire(): Observable<User[]> {
        return this.http.get(this.resourceuserForAuthorityGestionnaireUrl).map((res: Response) => {
            return res.json();
        });
    }



    find(id: number): Observable<PrestationPEC> {
        this.id = id;
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findPECByPrestation(id: number): Observable<PrestationPEC> {
        return this.http.get(`${this.resourcePECByPrestationUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryReparateur(operation: number ,userId:number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlReparateur}/${operation}/${userId}`, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryPrestationsToCheck(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlPrestationToCheck, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryForEditQuotation(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllPrestationPECWhenTiersIsNotNull(id: number , req?: any ): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get( `${this.resourceUrlprestationPECWhenTiersNotNull}/${id}` , options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllPrestationPECAcceptedOrQuaotationInProgress(req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get( this.resourceUrlprestationPECAcceptedOrQuaotationInProgress , options)
            .map((res: Response) => this.convertResponse(res));

    }
    findAllPrestationPECAcceptedOrQuaotationRefused(req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get( this.resourceUrlprestationPECAcceptedOrQuaotationRefused , options)
            .map((res: Response) => this.convertResponse(res));

    }
    findAllPrestationPECAcceptedOrQuaotationAccord(req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get( this.resourceUrlprestationPECAcceptedOrQuaotationAccord , options)
            .map((res: Response) => this.convertResponse(res));

    }
    findAllPrestationPECAcceptedOrQuaotationGeneratedAccord(req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get( this.resourceUrlprestationPECAcceptedOrQuaotationGeneratedAccord , options)
            .map((res: Response) => this.convertResponse(res));

    }
    findAllPrestationPECAcceptedOrQuaotationSignAccord(req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get( this.resourceUrlprestationPECAcceptedOrQuaotationSignAccord , options)
            .map((res: Response) => this.convertResponse(res));

    }
    findAllPrestationPECAcceptedOrQuaotationValidBill(req?: any): Observable<ResponseWrapper>{
        const options = createRequestOption(req);
        return this.http.get( this.resourceUrlprestationPECAcceptedOrQuaotationValidBill , options)
            .map((res: Response) => this.convertResponse(res));

    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateReceptionVehicule = this.dateUtils
            .convertDateTimeFromServer(entity.dateReceptionVehicule);
    }

    private convert(prestationPEC: PrestationPEC): PrestationPEC {
        const copy: PrestationPEC = Object.assign({}, prestationPEC);
         copy.dateReceptionVehicule = this.dateUtils.toDate(prestationPEC.dateReceptionVehicule);
        return copy;
    }

    private convertPrestationPEC(prestationPEC:  PrestationPEC):  PrestationPEC { // convertAffectExpert
        const copy: PrestationPEC = Object.assign({}, prestationPEC);
        return copy;
    }
}
