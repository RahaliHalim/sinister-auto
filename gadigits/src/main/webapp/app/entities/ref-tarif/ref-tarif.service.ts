 import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { RefTarif } from './ref-tarif.model';
import { TugPricing } from './tug-pricing.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { JhiDateUtils } from 'ng-jhipster';
@Injectable()
export class RefTarifService {
    private resourceUrl = 'api/refTarifs';
    private resourceUrl1 = 'api/tarifLines';
    private resourceUrl2 = 'api/tugPricings';
    private resourceUrl3 = 'api/refTarifs/tarifLine';
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }
    create(refTarif: RefTarif): Observable<RefTarif> {
        const copy = this.convert1(refTarif);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }
    update(refTarif: RefTarif): Observable<RefTarif> {
        const copy = this.convert1(refTarif);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }
    find(id: number): Observable<RefTarif> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findByTarifLine(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl3}/${id}`).map((res: Response) => this.convertResponse1(res));
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse1(res));
    }
    queryline(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl1, options)
            .map((res: Response) => this.convertResponse1(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse1(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert1(refTarif: RefTarif): RefTarif {
        const copy: RefTarif = Object.assign({}, refTarif);
        return copy;
    }

    createPricing(tugPricing: TugPricing): Observable<TugPricing> {
        const copy = this.convert(tugPricing);
        return this.http.post(this.resourceUrl2, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    updatePricing(tugPricing: TugPricing): Observable<TugPricing> {
        const copy = this.convert(tugPricing);
        return this.http.put(this.resourceUrl2, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findTugPricing(id: number): Observable<TugPricing> {
        return this.http.get(`${this.resourceUrl2}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    private convertItemFromServer(entity: any) {    
        entity.dateEffectiveAgreement = this.dateUtils
            .convertLocalDateFromServer(entity.dateEffectiveAgreement);
        entity.dateEndAgreement = this.dateUtils
            .convertLocalDateFromServer(entity.dateEndAgreement);
    }
    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
    private convert(tugPricing: TugPricing): TugPricing {
        const copy: TugPricing = Object.assign({}, tugPricing);
        copy.dateEffectiveAgreement = this.dateUtils
            .convertLocalDateToServer(tugPricing.dateEffectiveAgreement);
        copy.dateEndAgreement = this.dateUtils
            .convertLocalDateToServer(tugPricing.dateEndAgreement);
        return copy;
    }
   
   
}
