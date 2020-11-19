import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Http, Response ,ResponseContentType} from '@angular/http';
import { JhiDateUtils } from 'ng-jhipster';
import { Search } from '../sinister';
import { Bonification } from './bonification-depenses/bonification.model';
import { SuiviAnnulation } from './suivi-annulation/suiviAnnulation.model';
import { PerformanceRemorqueur } from './performance-remorqueur/performance-remorqueur.model';
import { DelaiMoyenImmobilisation } from './delai-moyen-immobilisation/delai-moyen.model';
import { PaiementReparation } from './paiement-reparation/paiement-reparation.model';
import { ReportFrequency } from '../sinister/reportFrequency.model';

@Injectable()
export class ReportService {


  private  resourceUrlBonification = 'api/view/bonification';
  private  resourceUrlSuiviAnnulation = 'api/view/suiviAnnulation';
  private resourceUrlPerformance = 'api/view/viewPerformance';
  private resourceUrlDelaiMoyImmobilisation = 'api/view/viewDelaiMoy';
  private resourceUrlDPaiementReparation = 'api/view/viewPaiment';
  private resourceUrlTauxDeFreq = 'api/view/reportFrequencyyy';
  private resourceUrl = 'api/sinister';


  constructor(private http: Http, private dateUtils: JhiDateUtils) { }

  queryPaiementReparation(req?: any): Observable<ResponseWrapper> {
    const options = createRequestOption(req);
    return this.http.get(this.resourceUrlDPaiementReparation, options)
        .map((res: Response) => this.convertResponse(res));
}



  queryBonification(req?: any): Observable<ResponseWrapper> {
    const options = createRequestOption(req);
    return this.http.get(this.resourceUrlBonification, options)
        .map((res: Response) => this.convertResponse(res));
}

queryPerformance(req?: any): Observable<ResponseWrapper> {
  const options = createRequestOption(req);
  return this.http.get(this.resourceUrlPerformance, options)
      .map((res: Response) => this.convertResponse(res));
}
queryDelaiMoyImmobilisation(req?: any): Observable<ResponseWrapper> {
  const options = createRequestOption(req);
  return this.http.get(this.resourceUrlDelaiMoyImmobilisation, options)
      .map((res: Response) => this.convertResponse(res));
}



queryTauxDeFreq(search: Search): Observable<ReportFrequency[]> {
  const copy = this.convertSearch(search);
  return this.http.post(this.resourceUrlTauxDeFreq, copy).map((res: Response) => {
      return res.json();
  });
}

  findBonification(search: Search): Observable<Bonification[]> {
    const copy = this.convertSearch(search);
    return this.http.post(this.resourceUrlBonification, copy).map((res: Response) => {
        return res.json()
    });
}

findPaiementReparation(search: Search): Observable<PaiementReparation[]> {
  const copy = this.convertSearch(search);
  return this.http.post(this.resourceUrlDPaiementReparation, copy).map((res: Response) => {
      return res.json()
  });
}

querySuiviAnnulation(req?: any): Observable<ResponseWrapper> {
  const options = createRequestOption(req);
  return this.http.get(this.resourceUrlSuiviAnnulation, options)
      .map((res: Response) => this.convertResponse(res));
}



findPerformance(search: Search): Observable<PerformanceRemorqueur[]> {
  const copy = this.convertSearch(search);
  return this.http.post(this.resourceUrlPerformance, copy).map((res: Response) => {
      return res.json()
  });
}
findDelaiMoyImmobilisation(search: Search): Observable<DelaiMoyenImmobilisation[]> {
  const copy = this.convertSearch(search);
  return this.http.post(this.resourceUrlDelaiMoyImmobilisation, copy).map((res: Response) => {
      return res.json()
  });
}
findSuiviAnnulation(search: Search): Observable<SuiviAnnulation[]> {
  const copy = this.convertSearch(search);
  return this.http.post(this.resourceUrlSuiviAnnulation, copy).map((res: Response) => {
      return res.json()
  });
}


private convertResponse(res: Response): ResponseWrapper {
  const jsonResponse = res.json();
  for (let i = 0; i < jsonResponse.length; i++) {
      this.convertItemFromServer(jsonResponse[i]);
  }
  return new ResponseWrapper(res.headers, jsonResponse, res.status);
}

private convertItemFromServer(entity: any) {
  entity.debutValidite = this.dateUtils.convertLocalDateFromServer(entity.debutValidite);
  entity.finValidite = this.dateUtils.convertLocalDateFromServer(entity.finValidite);
  entity.deadlineDate = this.dateUtils.convertLocalDateFromServer(entity.deadlineDate);
  entity.amendmentEffectiveDate = this.dateUtils.convertLocalDateFromServer(entity.amendmentEffectiveDate);
  entity.receiptValidityDate = this.dateUtils.convertLocalDateFromServer(entity.receiptValidityDate);

}

private convertSearch(search: Search): Search {
  const copy: Search = Object.assign({}, search);
  copy.startDate = this.dateUtils.convertLocalDateToServer(search.startDate);
  copy.endDate = this.dateUtils.convertLocalDateToServer(search.endDate);
  return copy;
}

exportReport2ServiceToExcel(search: Search): Observable<Blob> {
        
  console.log('___________________________________________');
  return this.http.post(`${this.resourceUrl}/report2/export/excel`,search ,{ responseType: ResponseContentType.Blob }).map((res: Response) => {
      return res.blob();
  });;
}

}
