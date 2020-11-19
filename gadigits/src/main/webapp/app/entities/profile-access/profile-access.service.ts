import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ProfileAccess, UserAccessWork } from './profile-access.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProfileAccessService {

    private resourceUrl = 'api/profile-accesses';
    private resourceSearchUrl = 'api/_search/profile-accesses';

    constructor(private http: Http) { }

    create(profileAccess: ProfileAccess): Observable<ProfileAccess> {
        const copy = this.convert(profileAccess);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(profileAccess: ProfileAccess): Observable<ProfileAccess> {
        const copy = this.convert(profileAccess);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProfileAccess> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByProfile(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/profile/${id}`).map((res: Response) => this.convertResponse(res));
    }

    findEntityAccessByProfile(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/entity/profile/${id}`).map((res: Response) => this.convertResponse(res));
    }

    treatEntityAccessByProfile(userAccessWork: UserAccessWork): Observable<UserAccessWork> {
        return this.http.post(`${this.resourceUrl}/entity/profile`, userAccessWork).map((res: Response) => {
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

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(profileAccess: ProfileAccess): ProfileAccess {
        const copy: ProfileAccess = Object.assign({}, profileAccess);
        return copy;
    }
}
