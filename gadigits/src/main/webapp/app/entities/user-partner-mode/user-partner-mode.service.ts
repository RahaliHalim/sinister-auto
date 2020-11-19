import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { UserPartnerMode } from './user-partner-mode.model'
import { Agency } from '../agency/agency.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserPartnerModeService {

    private resourceUrl = 'api/user-partner-mode';
    private resourceUrlUserPartnerAgencyProfile = 'api/user-partner-mode/partner-agency-mode';
    private resourceUrlUserPartnerProfile = 'api/user-partner-mode/partner';
    private resourceUrlUserPartnerMode = 'api/user-partner-mode/partner-mode';

    constructor(private http: Http) { }

    create(userPartnerMode: UserPartnerMode): Observable<UserPartnerMode> {
        return this.http.post(this.resourceUrl, userPartnerMode).map((res: Response) => {
            return res.json();
        });
    }

    findAgencyByUser(id: number): Observable<Agency[]> {
        return this.http.get(`${this.resourceUrl}/agency/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findUserPartnerModeByPartnerAndAgencyAndModeAndProfile(idPartner: number, idMode: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrlUserPartnerAgencyProfile}/${idPartner}/${idMode}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findUsersByPartnerAndMode(idPartner: number, idMode: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrlUserPartnerMode}/${idPartner}/${idMode}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findUserPartnerModeByPartner(idPartner: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrlUserPartnerProfile}/${idPartner}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    private convertResponsePec(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}