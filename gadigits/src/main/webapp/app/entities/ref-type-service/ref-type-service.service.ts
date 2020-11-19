import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { RefTypeService } from './ref-type-service.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RefTypeServiceService {

    private resourceUrl = 'api/ref-type-services';
    private resourceSearchUrl = 'api/_search/ref-type-services';
    private resourceUrlPack ='api/ref-type-service-packs';
    private resourceUrlPackk ='api/ref-type-service-packks';
    private resourceUrlPackTypeService = ' api/ref-type-service-packs-type-service';
    private resourceUrlStatutsSinister = 'api/refStatusSinister';
    constructor(private http: Http) { }

    create(refTypeService: RefTypeService): Observable<RefTypeService> {
        const copy = this.convert(refTypeService);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refTypeService: RefTypeService): Observable<RefTypeService> {
        const copy = this.convert(refTypeService);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }
    find(id: number): Observable<RefTypeService> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    getAllActiveServiceType(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPackTypeService}/active`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryPacks(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlPack, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getRefTypeServicePacksbyTypeService(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlPackTypeService}/${id}`)
        .map((res: Response) => this.convertResponse(res));
    }

    queryRefStatusSinister(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlStatutsSinister, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    deletePack(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlPackk}/${id}`);
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

    private convert(refTypeService: RefTypeService): RefTypeService {
        const copy: RefTypeService = Object.assign({}, refTypeService);
        return copy;
    }
}
