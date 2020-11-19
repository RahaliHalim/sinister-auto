import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { UserCellule } from './user-cellule.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserCelluleService {

    private resourceUrl = 'api/user-cellules';
    private resourceUserCelluleByUserUrl = 'api/user-cellules/user';
    private ressourceUserCelluleByUserAndCelluleUrl = 'api/user-cellules'

    constructor(private http: Http) { }

    create(userCellule: UserCellule): Observable<UserCellule> {
        const copy = this.convert(userCellule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(userCellule: UserCellule): Observable<UserCellule> {
        const copy = this.convert(userCellule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UserCellule> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByUserAndCellule(idUser: number, idCellule: number): Observable<UserCellule> {
        return this.http.get(`${this.ressourceUserCelluleByUserAndCelluleUrl}/${idUser}/${idCellule}`).map((res: Response) => {
            return res.json();
        });
    }

    findByUser(id: number): Observable<UserCellule[]> {
        return this.http.get(`${this.resourceUserCelluleByUserUrl}/${id}`).map((res: Response) => {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(userCellule: UserCellule): UserCellule {
        const copy: UserCellule = Object.assign({}, userCellule);
        return copy;
    }
}
