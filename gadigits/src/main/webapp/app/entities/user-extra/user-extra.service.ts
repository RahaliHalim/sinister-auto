import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { PermissionAccess } from './permission-access.model';
import { UserExtra } from './user-extra.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { UserAccessWork } from '../profile-access';
import { Partner } from '../partner/partner.model';
import { PartnerModeMapping } from '../user-partner-mode/partner-mode-mapping.model';

@Injectable()
export class UserExtraService {

    private resourceUrl = 'api/user-extras';
    private resourceSearchUrl = 'api/_search/user-extras';

    private resourceViewUrl = 'api/view/users';

    constructor(private http: Http) { }

    create(userExtra: UserExtra): Observable<UserExtra> {
        const copy = this.convert(userExtra);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(userExtra: UserExtra): Observable<UserExtra> {
        const copy = this.convert(userExtra);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UserExtra> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findFunctionnalityEntityByUser(idEntity: number,idUser: number): Observable<PermissionAccess> {
        return this.http.get(`${this.resourceUrl}/byEntity/${idEntity}/${idUser}`).map((res: Response) => {
          return res.json();
        });
    }

    isUserHasFunctionality(entityId: number, funcId: number): Observable<boolean> {
        return this.http.get(`${this.resourceUrl}/route/${entityId}/${funcId}`).map((res: Response) => {
          return res.json();
        });
    }

    findByUser(id: number): Observable<UserAccessWork> {
        return this.http.get(`${this.resourceUrl}/user/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    finPersonneByUser(id: number): Observable<UserExtra> {
        return this.http.get(`${this.resourceUrl}/user/personne/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    finUserByPersonProfil(personId: number, profilId: number): Observable<UserExtra> {
        return this.http.get(`${this.resourceUrl}/person/profil/${personId}/${profilId}`).map((res: Response) => {
            return res.json();
        });
    }

    finUsersByPersonProfil(personId: number, profilId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/pers/profil/${personId}/${profilId}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findByProfil(id: number): Observable<UserExtra[]> {
        return this.http.get(`${this.resourceUrl}/profil/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findByProfils(): Observable<UserExtra[]> {
        return this.http.get(`${this.resourceUrl}/profil`).map((res: Response) => {
            return res.json();
        });
    }


   findUserByCharge(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/bycharge`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findCompagnieByUser(id: number): Observable<Partner[]> {
        return this.http.get(`${this.resourceUrl}/partner/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findModeByUserAndPartner(iduser: number, partnersId: number[]): Observable<PartnerModeMapping[]> {
        return this.http.get(`${this.resourceUrl}/mode/${iduser}/${partnersId}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllUsers(req?: any): Observable<ResponseWrapper> {
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

    private convert(userExtra: UserExtra): UserExtra {
        const copy: UserExtra = Object.assign({}, userExtra);
        return copy;
    }


   
}
