import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper } from '../model/response-wrapper.model';
import { createRequestOption } from '../model/request-util';
import { GroupDetails } from './group-details.model';


@Injectable()
export class GroupDetailsService {

    private resourceUrl = 'api/group-details';
    private resourceUrlByGroupId = 'api/group-details/group';
    ResponseWrapper
    constructor(private http: Http) { }

    create(groupDetails: GroupDetails): Observable<GroupDetails> {
               
            return this.http.post(this.resourceUrl, groupDetails).map((res: Response) => {
                return res.json();
            });
    }


    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    find(id: number): Observable<GroupDetails> {
        
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findByGroupId(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlByGroupId}/${id}`)
        .map((res: Response) => this.convertResponse(res));
       
    }

    /*findById(id: number): Observable<GroupDetails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => res.json());
    }*/

    deleteById(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

}