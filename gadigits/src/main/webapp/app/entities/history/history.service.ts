import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { History } from './history.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { PrimaryQuotation } from '../PrimaryQuotation';

@Injectable()

export class HistoryService {

    histories: History[];
    private resourceUrl = 'api/historys';
    private resourceQuotationUrl = 'api/historys/quotation';
    private resourceUrlApecHistory = 'api/historys/apec';
    private resourceQuotationUrlHistory = 'api/historys/quotationHistory';
    private resourceApecUrlHistory = 'api/historys/apecHistory';

    private resourceUrlPec = 'api/historysPec';
    private resourceQuotationPecUrl = 'api/historysPec/quotation';
    private resourceUrlApecHistoryPec = 'api/historysPec/apec';
    private resourceQuotationUrlHistoryPec = 'api/historysPec/quotationHistory';
    private resourceApecUrlHistoryPec = 'api/historysPec/apecHistory';

    constructor(private http: Http) { }
  
    findHistoriesByEntity(entityId: number, entityName: string ): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${entityId}/${entityName}`).map((res: Response) => this.convertResponse(res));
    }
    
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    findQuotationByHistory(actionId: number, entityId: number, entityName: string): Observable<PrimaryQuotation> {
        console.log("find service--****");
        return this.http.get(`${this.resourceQuotationUrl}/${actionId}/${entityId}/${entityName}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findListDevisByHistory(sinisterPecId: number): Observable<ResponseWrapper> {
     
        return this.http.get(`${this.resourceQuotationUrl}/${sinisterPecId}`).map((res: Response) => this.convertResponse(res));

    }
    findQuotationHistoryById(id: number): Observable<PrimaryQuotation> {
        return this.http.get(`${this.resourceQuotationUrlHistory}/${id}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findApecHistoryById(id: number): Observable<PrimaryQuotation> {
        return this.http.get(`${this.resourceApecUrlHistory}/${id}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }


    findListAccordByHistory(sinisterPecId: number): Observable<ResponseWrapper> {
     
        return this.http.get(`${this.resourceUrlApecHistory}/${sinisterPecId}`).map((res: Response) => this.convertResponse(res));

        
    }



    /////////////////////////////////////

    findHistoriesPecByEntity(entityId: number, entityName: string ): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlPec}/${entityId}/${entityName}`).map((res: Response) => this.convertResponse(res));
    }
    
 

    findQuotationByHistoryPec(actionId: number, entityId: number, entityName: string): Observable<PrimaryQuotation> {
        console.log("find service--****");
        return this.http.get(`${this.resourceQuotationPecUrl}/${actionId}/${entityId}/${entityName}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findListDevisByHistoryPec(sinisterPecId: number): Observable<ResponseWrapper> {
     
        return this.http.get(`${this.resourceQuotationPecUrl}/${sinisterPecId}`).map((res: Response) => this.convertResponse(res));

    }
    findQuotationHistoryPecById(id: number): Observable<PrimaryQuotation> {
        return this.http.get(`${this.resourceQuotationUrlHistoryPec}/${id}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findApecHistoryPecById(id: number): Observable<PrimaryQuotation> {
        return this.http.get(`${this.resourceApecUrlHistoryPec}/${id}`).map((res: Response) => {
            console.log("find service after find--****");
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }


    findListAccordByHistoryPec(sinisterPecId: number): Observable<ResponseWrapper> {
     
        return this.http.get(`${this.resourceUrlApecHistoryPec}/${sinisterPecId}`).map((res: Response) => this.convertResponse(res));

    }
}
