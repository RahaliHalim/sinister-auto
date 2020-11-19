import { Search } from './search.model';
import { Injectable } from '@angular/core';
import { Http, Response , ResponseContentType} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ReportTugPerformance } from './report-tug-performance.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReportAssistanceService {

    private resourceUrl = 'api/report/assistance';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    findReportTugPerformance(search: Search): Observable<ReportTugPerformance[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourceUrl}/report-tug-performance`, copy).map((res: Response) => {
            return res.json();
        });
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/report-tug-performance`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findReportTugPerformancePageS(search: Search): Observable<ReportTugPerformance[]> {
        const copy = this.convertSearch(search);
        return this.http.post(`${this.resourceUrl}/report-tug-performance/page`, copy).map((res: Response) => {
            return res.json();
        });
    }

    private convertSearch(search: Search): Search {
        const copy: Search = Object.assign({}, search);
        copy.startDate = this.dateUtils.convertLocalDateToServer(search.startDate);
        copy.endDate = this.dateUtils.convertLocalDateToServer(search.endDate);
        return copy;
    }
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
    exportTugPerfToExcel(search: Search): Observable<Blob> {
        
        console.log('___________________________________________');
        return this.http.post(`${this.resourceUrl}/report-tug-performance/export/excel`,search ,{ responseType: ResponseContentType.Blob }).map((res: Response) => {
            return res.blob();
        });;
    }
}
