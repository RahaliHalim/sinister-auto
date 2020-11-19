import { Injectable } from '@angular/core';
import { Devis } from './devis.model';
import { DetailsMo } from '../details-mo';

@Injectable()
export class QuotationCalculateService {

    constructor() { }

    /**
     * calculate totalHt and Ttc on change of hours number
     * @param detailsMoLine 
     */
    changeHourCount(detailsMoLine: DetailsMo, devis: Devis) {
        detailsMoLine.totalHt = detailsMoLine.th*detailsMoLine.nombreHeures;
        detailsMoLine.totalTtc = detailsMoLine.totalHt + (detailsMoLine.totalHt*detailsMoLine.vat/100);
        detailsMoLine.totalTtc = detailsMoLine.totalTtc - (detailsMoLine.totalTtc*detailsMoLine.discount/100);
        
    }

}