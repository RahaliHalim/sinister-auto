import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ViewDetailQuotation } from './view-detail-quotation.model';

@Injectable()
export class ViewDetailQuotationService {

    private resourceUrlView = 'api/view/detail-quotation';

    constructor(private http: Http) { }
 
    getDetailDevisByPec(id: number): Observable<ViewDetailQuotation> {
            return this.http.get(`${this.resourceUrlView}/${id}`).map((res: Response) => {
                console.log("json detail quotation*--*-"+res.json());
                return res.json();
            });
    }
}
