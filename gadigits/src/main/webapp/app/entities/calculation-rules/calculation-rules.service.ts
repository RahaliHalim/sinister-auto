import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { CalculationRules } from "./calculation-rules.model";
import { Observable } from "rxjs/Observable";
import { ResponseWrapper } from "../../shared/model/response-wrapper.model";
import { createRequestOption } from "../../shared/model/request-util";
import { JhiDateUtils } from "ng-jhipster/src/service/date-util.service";

@Injectable()
export class CalculationRulesService {

    private resourceUrl = 'api/partner-rules';
    private resourceUrlHistory = 'api/partner-rules/history';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(rules: CalculationRules): Observable<CalculationRules> {
        const copy = this.convertToServer(rules);
        console.log("save calculation rules----");
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }
    createHistory(rules: CalculationRules): Observable<CalculationRules> {
        
        const copy = this.convert(rules);
        console.log("date convention history----"+copy.startDateConvention);
        return this.http.post(this.resourceUrlHistory, copy).map((res: Response) => {
            return res.json();
        });
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    find(id: number): Observable<CalculationRules> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findByPartnerAndMode(partnerId: number, modeId: number): Observable<CalculationRules> {
        return this.http.get(`${this.resourceUrl}/${partnerId}/${modeId}`).map((res: Response) => {
            return res.json();
        });
    }
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertToServer(rules: CalculationRules): CalculationRules {
        const copy: CalculationRules = Object.assign({}, rules);     
        copy.startDateConvention = this.dateUtils.convertLocalDateToServer(rules.startDateConvention);
        copy.effectiveDate = this.dateUtils.convertLocalDateToServer(rules.effectiveDate);
        return copy;
    }
    private convert(rules: CalculationRules): CalculationRules {
        const copy: CalculationRules = Object.assign({}, rules);
        copy.startDateConvention = this.dateUtils.toDate(rules.startDateConvention);
        copy.effectiveDate = this.dateUtils.toDate(rules.effectiveDate);
        return copy;
    }
}