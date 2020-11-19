import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { UserProfile } from './user-profile.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserProfileService {

    private resourceUrl = 'api/user-profiles';
    private resourceSearchUrl = 'api/_search/user-profiles';

    constructor(private http: Http) { }

    create(userProfile: UserProfile): Observable<UserProfile> {
        const copy = this.convert(userProfile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(userProfile: UserProfile): Observable<UserProfile> {
        const copy = this.convert(userProfile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UserProfile> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
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

    private convert(userProfile: UserProfile): UserProfile {
        const copy: UserProfile = Object.assign({}, userProfile);
        return copy;
    }
}
