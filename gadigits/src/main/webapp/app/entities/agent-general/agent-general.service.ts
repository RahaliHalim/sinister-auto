import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AgentGeneral } from './agent-general.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AgentGeneralService {

    private resourceUrl = 'api/agent-generals';
    private resourceSearchUrl = 'api/_search/agent-generals';
    private resourceUrlAffectAgent = 'api/affectAgent';
    agent: any;

    constructor(private http: Http) { }

    create(agentGeneral: AgentGeneral): Observable<AgentGeneral> {
        const copy = this.convert(agentGeneral);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(agentGeneral: AgentGeneral): Observable<AgentGeneral> {
        const copy = this.convert(agentGeneral);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AgentGeneral> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    updateAffectAgent(idagent: number, iduser: number): Observable<AgentGeneral>{
     
        this.find(idagent).subscribe((res) => this.agent = res)
        return this.http.put(`${this.resourceUrlAffectAgent}/${idagent}/${iduser}`, this.agent).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
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

    private convert(agentGeneral: AgentGeneral): AgentGeneral {
        const copy: AgentGeneral = Object.assign({}, agentGeneral);
        return copy;
    }
}
