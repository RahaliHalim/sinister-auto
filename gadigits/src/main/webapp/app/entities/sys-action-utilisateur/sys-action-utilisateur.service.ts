import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SysActionUtilisateur } from './sys-action-utilisateur.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SysActionUtilisateurService {

    private resourceUrl = 'api/sys-action-utilisateurs';
    private resourceSearchUrl = 'api/_search/sys-action-utilisateurs';
    private resourceUrlActionByName = 'api/sys-action-utilisateurs/action';

    constructor(private http: Http) { }

    create(sysActionUtilisateur: SysActionUtilisateur): Observable<SysActionUtilisateur> {
        const copy = this.convert(sysActionUtilisateur);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(sysActionUtilisateur: SysActionUtilisateur): Observable<SysActionUtilisateur> {
        const copy = this.convert(sysActionUtilisateur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SysActionUtilisateur> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByName(name: String): Observable<SysActionUtilisateur> {
        return this.http.get(`${this.resourceUrlActionByName}/${name}`).map((res: Response) => {
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

    private convert(sysActionUtilisateur: SysActionUtilisateur): SysActionUtilisateur {
        const copy: SysActionUtilisateur = Object.assign({}, sysActionUtilisateur);
        return copy;
    }
}
