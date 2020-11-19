import { SinisterPrestation } from './sinister-prestation.model';
import { Search } from './search.model';
import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { Sinister } from './sinister.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Result } from '../sinister-pec/result.model';
import { ReportFollowUpAssistance } from './report-follow-up-assistance.model';
import { Assitances } from './assitances.model';
import { Recherche } from './recherche.model';
import { Dossiers } from './dossiers.model';
import { PriseEnCharges } from './priseencharge.model';
import { ViewSinisterPrestation } from './view-sinister-prestation.model';
import { ViewSinisterPec } from '../view-sinister-pec';
import { StatusSinister } from '../../constants/app.constants';
@Injectable()
export class SinisterService {
    public sinisterId: any;
    public id: any;

    private resourceUrl = 'api/sinister';
    private resourceUrlPrestation = 'api/view/view-prestation';
    private resourceUrlPrestationVr = 'api/view/view-prestation-vr';

    private resourceUrlExcel = 'api/view-assitances';
    private resourceUrlSinisterExcel = 'api/view/sinister';
    private resourceUrlReport1ServicesExcel = 'api/sinister/prestation';
    private resourceUrlExportOrdrePrestationVr = 'api/generateOrdrePrestationVr';
    private resourcePriseEnChargesUrlExcel = 'api/view-prise-en-charge';


    private resourceUrlByVehicleRegistration = 'api/sinister/vehicleregistration';
    private resourceSearchUrl = 'api/_search/sinister';
    private counterUrl = 'api/sinister/counter';
    private resourcePrestationUrl = 'api/sinister/prestation';
    private resourcePecUrl = 'api/sinister-pecs';
    private resourceViewUrl = 'api/view/sinister';
    private resourceViewPrestationUrl = 'api/view/prestation';
    public resourceUrlAttachments = 'api/sinister/attachement';
    public resourceUrlDossiers = 'api/getdossiers';
    private resourceUrlAssitances = 'api/getassitances';
    private resourceUrlPriseEncharge = 'api/getpriseencharges';
    private resourceUrlSinisterPrestation = 'api/sinister/prestation';
    private resourceUrlCanSavePrestation = 'api/sinister/prestation/authorized';
    private resourceUrlViewPrestationsByVehicleRegistration = 'api/sinister/viewvehicleregistration';
    private resourceUrlViewPecByVehicleRegistration = 'api/sinisterPec/viewvehicleregistration'
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(sinister: Sinister): Observable<Sinister> {
        const copy = this.convert(sinister);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    canCreate(sinister: Sinister): Observable<Sinister> {
        const copy = this.convert(sinister);
        return this.http.post(`${this.resourceUrl}/authorized`, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(sinister: Sinister): Observable<Sinister> {
        this.sinisterId = sinister.id;
        const copy = this.convert(sinister);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<Sinister> {
        this.id = id;
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    canSavePrestation(id: number): Observable<boolean> {
        this.id = id;
        return this.http.get(`${this.resourceUrlCanSavePrestation}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findSinisterPrestation(id: number): Observable<SinisterPrestation> {
        this.id = id;
        console.log("id sinister pec**-*-*-*" + id);
        return this.http.get(`${this.resourceUrlSinisterPrestation}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findByPrestation(id: number): Observable<Sinister> {
        this.id = id;
        return this.http.get(`${this.resourcePrestationUrl}/${id}/parent`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findBySinisterPec(id: number): Observable<Sinister> {
        return this.http.get(`${this.resourcePecUrl}/${id}/parent`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findByVehicleRegistration(vehicleRegistration: string): Observable<Sinister[]> {
        return this.http.get(`${this.resourceUrlByVehicleRegistration}/${vehicleRegistration}`).map((res: Response) => {
            return res.json();
        });
    }

    findViewSinistersPrestationsByVehicleRegistration(vehiculeId: number): Observable<ViewSinisterPrestation[]> {
        return this.http.get(`${this.resourceUrlViewPrestationsByVehicleRegistration}/${vehiculeId}`).map((res: Response) => {
            return res.json();
        });
    }
    findViewSinistersPecByVehicleRegistration(vehiculeId: number): Observable<ViewSinisterPec[]> {
        return this.http.get(`${this.resourceUrlViewPecByVehicleRegistration}/${vehiculeId}`).map((res: Response) => {
            return res.json();
        });
    }

    getAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        console.log("service get attachments");
        return this.http.get(`${this.resourceUrlAttachments}/${id}`).map((res: Response) =>
            this.convertResponse(res));
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryDossiers(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlDossiers, options)
            .map((res: Response) => this.convertResponse(res));
    }


    queryAssitances(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlAssitances, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryPriseEncharge(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlPriseEncharge, options)
            .map((res: Response) => this.convertResponse(res));
    }
    /*queryAssistanceSearch(type?:any, immatriculation?:String,startDate?:any,endDate?:any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlPriseEncharge, options)
            .map((res: Response) => this.convertResponse(res));
    }*/

    queryAssistanceSearch(search: Search): Observable<Assitances[]> {
        const copy = this.convertSearch(search);
        return this.http.post(this.resourceUrlAssitances, copy).map((res: Response) => {
            return res.json();
        });
    }
    queryDossiersSearch(search: Search): Observable<Dossiers[]> {
        //const copy = this.convertSearch(search);
        return this.http.post(this.resourceUrlDossiers, search).map((res: Response) => {
            return res.json();
        });
    }
    /*  queryPriseEnChargeSearch(recherche: Recherche): Observable<PriseEnCharges[]> {
          const copy = this.convertSearch(recherche);
          return this.http.post(`${this.resourceUrlPriseEncharge}`, copy).map((res: Response) => {
              return res.json();
          });
      } */

    queryPriseEnChargeSearch(search: Search): Observable<PriseEnCharges[]> {
        //const copy = this.convertSearch(search);
        return this.http.post(this.resourceUrlPriseEncharge, search).map((res: Response) => {
            return res.json()
        });
    }


    findAll(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceViewUrl, options)
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    findRef(id?: number): Observable<Result> {
        return this.http.get(`${this.counterUrl}/${id}`).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
            }
            return null;
        });
    }

    findInProgressPrestations(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceViewPrestationUrl}/inprogress`)
            .map((res: Response) => this.convertResponse(res));
    }

    findInProgressPrestationsNotView(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourcePrestationUrl}/inprogress`)
            .map((res: Response) => this.convertResponse(res));
    }

    findCanceledPrestations(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceViewPrestationUrl}/canceled`)
            .map((res: Response) => this.convertResponse(res));
    }

    findNotEligiblePrestations(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceViewPrestationUrl}/not-eligible`)
            .map((res: Response) => this.convertResponse(res));
    }

    findClosedPrestations(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceViewPrestationUrl}/closed`)
            .map((res: Response) => this.convertResponse(res));
    }

    findReport1Prestations(search: Search): Observable<ReportFollowUpAssistance[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourcePrestationUrl}/report1`, copy).map((res: Response) => {
            return res.json();
        });
    }

    findReport1PrestationsFile(search: Search): Observable<Blob> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourcePrestationUrl}/report1/download`, copy).map((res: Response) => {
            console.log('________________________________________________________________________');
            let mediatype = 'application/vnd.ms-excel;charset=UTF-8';

            const data = new Blob(["\ufeff", res.arrayBuffer()], { type: mediatype });
            return data;
        });
    }

    findReport2Prestations(search: Search): Observable<Sinister[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourceUrl}/report2`, copy).map((res: Response) => {
            return res.json();
        });
    }
    queryReport2Prestations(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/report2`, options)
            .map((res: Response) => this.convertResponse(res));
    }


    isSinisterDuplicated(sinister: Sinister): Observable<Sinister> {
        const copy = this.convert(sinister);
        return this.http.post(`${this.resourceUrl}/duplicated`, copy).map((res: Response) => {
            return res.json();
        });
    }

    private convertItemFromServer(entity: any) {
        entity.creationDate = this.dateUtils.convertDateTimeFromServer(entity.creationDate);
        entity.closureDate = this.dateUtils.convertDateTimeFromServer(entity.closureDate);
        entity.updateDate = this.dateUtils.convertDateTimeFromServer(entity.updateDate);
        entity.incidentDate = this.dateUtils.convertLocalDateFromServer(entity.incidentDate);
    }

    private convert(sinister: Sinister): Sinister {
        const copy: Sinister = Object.assign({}, sinister);
        //copy.creationDate = this.dateUtils.toDate(sinister.creationDate);
        copy.closureDate = this.dateUtils.toDate(sinister.closureDate);
        copy.updateDate = this.dateUtils.toDate(sinister.updateDate);
        console.log(sinister.incidentDate);
        copy.incidentDate = this.dateUtils.convertLocalDateToServer(sinister.incidentDate);
        return copy;
    }

    private convertSearch(search: Search): Search {
        const copy: Search = Object.assign({}, search);
        copy.startDate = this.dateUtils.convertLocalDateToServer(search.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(search.endDate);
        return copy;
    }

    exportAssitancesToExcel(search: Search): Observable<Blob> {
        
        console.log('___________________________________________');
        return this.http.post(this.resourceUrlExcel + '/export/excel/', search, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }

    exportprestationToExcel(search: string, status: number): Observable<Blob> {
        if (search === undefined || search === null || search === "") {
            search = '-1';
        }
        let formdata: FormData = new FormData();
        formdata.append('search', search);
        console.log('___________________________________________');
        return this.http.post(`${this.resourceUrlPrestation}/export/excel/${status}`, formdata, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }
    exportSinisterToExcel(search: string): Observable<Blob> {
        if (search === undefined || search === null || search === "") {
            search = '-1';
        }
        let formdata: FormData = new FormData();
        formdata.append('search', search);
        console.log('___________________________________________');
        return this.http.post(this.resourceUrlSinisterExcel + '/export/excel', formdata, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }

    exportReport1ServicesToExcel(search: Search): Observable<Blob> {

        console.log('___________________________________________');
        return this.http.post(`${this.resourceUrlReport1ServicesExcel}/export/excel`, search, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }

    exportVehiculePieceToExcel(): Observable<Blob> {
        
        console.log('___________________________________________');
        return this.http.get('api/view/view-vehicule-pieces/export/excel', { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }
    exportprestationVrToExcel(search: string, status: number): Observable<Blob> {
        if (search === undefined || search === null || search === "") {
            search = '-1';
        }
        // let statusVr = 0;
        // if (status  == "in-progress") {
        //     statusVr = StatusSinister.INPROGRESS;
        // } else if (status == "closed") {
        //     statusVr = StatusSinister.CLOSED;
        // } else if (status  == "refused") {
        //     statusVr = StatusSinister.REFUSED;
        // } else if (status  == "canceled") {
        //     statusVr = StatusSinister.CANCELED;
        // }
        let formdata: FormData = new FormData();
        formdata.append('search', search);
        console.log('___________________________________________');
        return this.http.post(`${this.resourceUrlPrestationVr}/export/excel/${status}`, formdata, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }

    generateOrdrePrestationVr(id: number  ): Observable<Response> {
            return this.http.post(`${this.resourceUrlExportOrdrePrestationVr}/${id}`,id);
    }
    exportPriseEnChargesToExcel(search: Search): Observable<Blob> {
        
        console.log('___________________________________________');
        return this.http.post(this.resourcePriseEnChargesUrlExcel + '/export/excel/', search, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }
}
