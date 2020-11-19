import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { Partner } from './partner.model';
import { Attachment } from '../attachments/attachment.model'
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PartnerService {

    private resourceUrl = 'api/partners';
    private resourceSearchUrl = 'api/_search/partners';
    private resourceUrlLogoConcessionnaire = 'api/concessionnaire/attachments';
    private resourceUrlLogoCompany = 'api/partners/attachments';
    private resourceUrlPartners ='api/allpartners';
    private resourceUrlStatement ='api/statements';
    private resourceUrlPiecesJointesFile = 'api/partners/pieces-jointes-file';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(partner: Partner): Observable<Partner> {
        const copy = this.convert(partner);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(partner: Partner): Observable<Partner> {
        const copy = this.convert(partner);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Partner> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    saveAttachmentsConcessionnaire(id: number, logoFile: File, label: String, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('concessionnaire', logoFile);
        return this.http.post(`${this.resourceUrlLogoConcessionnaire}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    updateAttachmentsConcessionnaire(id: number, logoFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('concessionnaire', logoFile);
        return this.http.put(`${this.resourceUrlLogoConcessionnaire}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    saveAttachmentsCompany(id: number, logoFile: File, label: String, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('company', logoFile);
        return this.http.post(`${this.resourceUrlLogoCompany}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    updateAttachmentsCompany(id: number, logoFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('company', logoFile);
        return this.http.put(`${this.resourceUrlLogoCompany}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    allPartners(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlPartners, options)
            .map((res: Response) => this.convertResponse(res));
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllCompanies(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/companies`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllCompaniesWithoutUser(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/companies-without-user`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllCompaniesForStatement(month: string, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlStatement}/${month}/companies`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllDealers(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/dealers`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findByCompanyNameRegistre(pname?: Partner): Observable<Partner> {
        return this.http.post(`${this.resourceUrl}/CompbyNameReg`, pname).map((res: Response) => {
            return res.json();
        });
    }

    findByDealerNameReg(pname?: Partner): Observable<Partner> {
        return this.http.post(`${this.resourceUrl}/DealerbyNameReg`,pname).map((res: Response) => {
            return res.json();
            
        });
    }

    findByDealerRegistre(pname?: String): Observable<Partner> {
        return this.http.get(`${this.resourceUrl}/DealerbyRegistre/${pname}`).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                return jsonResponse;
            }
            return null;
        });
    }

    getPieceBySinPecAndLabel(entityName: string, id: number, label: string): Observable<Blob> {
        return this.http.get(`${this.resourceUrlPiecesJointesFile}/${id}/${entityName}/${label}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            const data  = res.blob();;
            return data;
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(partner: Partner): Partner {
        const copy: Partner = Object.assign({}, partner);
        return copy;
    }

    private convertItemFromServer(entity: any) {
        entity.dateReceptionVehicule = this.dateUtils
            .convertDateTimeFromServer(entity.dateReceptionVehicule);
        entity.dateDerniereMaj = this.dateUtils
            .convertDateTimeFromServer(entity.dateDerniereMaj);
        entity.declarationDate = this.dateUtils
            .convertLocalDateFromServer(entity.declarationDate);
    }
}
