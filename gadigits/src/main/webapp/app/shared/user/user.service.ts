import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { User } from './user.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';
import { MenuItems } from '../../entities/MenuItems/menu.model';

@Injectable()
export class UserService {

    private resourceUrl = 'api/users';
    private resourceUrl1 = 'api/usersDto';
    private resourceUrlGroup = 'api/users/group';
    private resourceUrlWithoutPagination = 'api/users-without-pagination';
    private actUrl = 'api/act-users';
    private resourceUrlAffectCompAgent = 'api/affect-compagnie-agent';
    private findUserUrl = 'api/connecteduser';
    private menuByUserUrl = 'api/users/menus';
    agent: any;
    user: any;

    constructor(private http: Http) { }

    create(user: User): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl, user)
            .map((res: Response) => this.convertResponse(res));
    }

    createDto(user: User): Observable<ResponseWrapper> {
        return this.http.post(this.resourceUrl1, user)
            .map((res: Response) => this.convertResponse(res));
    }

    update(user: User): Observable<ResponseWrapper> {
        console.log("service update");
        console.log(user);
        return this.http.put(this.resourceUrl, user)
            .map((res: Response) => this.convertResponse(res));
    }
    updateDto(user: User): Observable<ResponseWrapper> {

        return this.http.put(this.resourceUrl1, user)
            .map((res: Response) => this.convertResponse(res));
    }
    updateGroup(idGroup: number, idUser: number): Observable<ResponseWrapper> {
        this.findById(idUser).subscribe((res) => this.user = res)
        return this.http.put(`${this.resourceUrl}/${idGroup}/${idUser}`, this.user)
            .map((res: Response) => this.convertResponse(res));
    }

    activateUser(userId: number): Observable<Response> {
        return this.http.put(`${this.resourceUrl}/activate/${userId}`, {});
    }

    disableUser(userId: number): Observable<Response> {
        return this.http.put(`${this.resourceUrl}/disable/${userId}`, {});
    }

    find(login: string): Observable<User> {
        return this.http.get(`${this.resourceUrl}/${login}`).map((res: Response) => res.json());
    }
    findById(id: number): Observable<User> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => res.json());
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findAllWithoupPagination(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlWithoutPagination, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(login: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${login}`);
    }

    deleteById(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
    }

    authorities(): Observable<string[]> {
        return this.http.get('api/users/authorities').map((res: Response) => {
            const json = res.json();
            return <string[]>json;
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    lanceActiviti(): Observable<User> {
        return this.http.get(this.actUrl).map((res: Response) => res.json());
    }

    getUserConnected(): Observable<User> {
        return this.http.get(`${this.findUserUrl}`).map((res :Response) => res.json());
    }
   
    getMenus(): Observable<MenuItems[]>{
        return this.http.get(`${this.menuByUserUrl}`).map((res: Response) => res.json());
    }

    exportUserInExcel(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrl}/export/excel/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            console.log('________________________________________________________________________');
            let mediatype = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';

            const data  = res.blob();;
            return data;
        });;
    }

}
