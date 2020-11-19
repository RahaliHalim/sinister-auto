import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Agency } from './agency.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AgencyService {

    private resourceUrl = 'api/agencies';
    private resourceUrlZone = 'api/agencieszone';
    private resourceSearchUrl = 'api/_search/agencies';

    constructor(private http: Http) { }

    create(agency: Agency): Observable<Agency> {
        const copy = this.convert(agency);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(agency: Agency): Observable<Agency> {
        const copy = this.convert(agency);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    findAllAgentAssurance(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/agents`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllAgenceConcessionnares(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/concess`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findByAgencyByNameCode(partnerId?: number, code?: String): Observable<Agency> {
        return this.http.get(`${this.resourceUrl}/byNameCode/${partnerId}/${code}`).map((res: Response) => {
             if (res['_body']) {
                const jsonResponse = res.json();
                return jsonResponse;
            }
            return null;
     });
    }

    findByAgenceConcessionnaireNameConcessName(pname?: String, id?: number): Observable<Agency> {
        return this.http.get(`${this.resourceUrl}/concessionnairebyNameCompanyName/${pname}/${id}`).map((res: Response) => {
             if (res['_body']) {
                const jsonResponse = res.json();
                return jsonResponse;
            }
            return null;
     });
    }

    find(id: number): Observable<Agency> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllByPartner(partnerId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/partner/${partnerId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllByPartnerAndZone(partnerId: number, zoneId : number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${partnerId}/${zoneId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllByZone(zoneId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlZone}/${zoneId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllByPartnerWithoutFiltrage(partnerId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/partner-without-user/${partnerId}`)
            .map((res: Response) => this.convertResponse(res));
    }
    
    findAgencyByPartnerAndType(partnerId: number, type: string): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/partner/${partnerId}/${type}`)
            .map((res: Response) => this.convertResponse(res));
    }
    findCourtierByPartnerAndType(partnersId: number[], type: string): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/partner/courtier/${partnersId}/${type}`)
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

    private convert(agency: Agency): Agency {
        const copy: Agency = Object.assign({}, agency);
        return copy;
    }
}
