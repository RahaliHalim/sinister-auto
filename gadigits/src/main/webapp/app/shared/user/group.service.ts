import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Group } from './group.model';
import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';


@Injectable()
export class GroupService {

    private resourceUrl = 'api/group';
    private resourceUrlProductGroup = 'api/group/product';
    ResponseWrapper
    constructor(private http: Http) { }

    create(group: Group): Observable<Group> {
       
            return this.http.post(this.resourceUrl, group).map((res: Response) => {
                return res.json();
            });
    }


    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    find(id: number): Observable<Group> {
        
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findProductGroup(id: number): Observable<Group> {
        
        return this.http.get(`${this.resourceUrlProductGroup}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    deleteById(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

}