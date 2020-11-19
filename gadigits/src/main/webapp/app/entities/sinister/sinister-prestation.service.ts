import { SinisterPrestation } from './sinister-prestation.model';
import { Search } from './search.model';
import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SinisterPrestationService {
    
    private resourceUrl = 'api/sinister/prestation';
    private resourceUrlSi = 'api/sinister/prestation/sinister';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    update(sinisterPrestation: SinisterPrestation): Observable<SinisterPrestation> {
         
        //const copy = this.convert(sinisterPrestation);
        return this.http.put(this.resourceUrl, sinisterPrestation).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    private convert(sinister: Sinister): Sinister {
        const copy: Sinister = Object.assign({}, sinister);
        copy.creationDate = this.dateUtils.toDate(sinister.creationDate);
        copy.closureDate = this.dateUtils.toDate(sinister.closureDate);
        copy.updateDate = this.dateUtils.toDate(sinister.updateDate);
        console.log(sinister.incidentDate);
        copy.incidentDate = this.dateUtils.convertLocalDateToServer(sinister.incidentDate);
        return copy;
    }

    find(id : number): Observable<SinisterPrestation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findBySinister(id: number): Observable<SinisterPrestation> {
        return this.http.get(`${this.resourceUrlSi}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findSinisterPrestationBySinisterId(id : number){


    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options);
    }
   


}
