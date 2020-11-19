import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Expert } from './expert.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ExpertService {

    private resourceUrl = 'api/experts';
    private resourceSearchUrl = 'api/_search/experts';
    private resourceUrlNonBloque = 'api/experts/nonBloque';
    private resourceUrlByVille = 'api/experts/ville';
    private resourceUrlExpertByGouvernorat = 'api/experts/gouvernorat';
    private resourceUrlAffectExpert = 'api/affectExpert';
    private resourceExpertsByGovernorate = 'api/experts/by-governorate';
    
    
    private resourceViewUrl = 'api/view/experts';

    expert: any;

    constructor(private http: Http, private dateUtils: JhiDateUtils) {}
    create(expert: Expert): Observable<Expert> {
        const copy = this.convert(expert);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(expert: Expert): Observable<Expert> {
        const copy = this.convert(expert);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    updateForActive(expert: Expert): Observable<Expert> {
        const copy: Expert = Object.assign({}, expert);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    updateAffectExpert(idexp: number, iduser: number): Observable<Expert>{
     
        this.find(idexp).subscribe((res) => this.expert = res)
        return this.http.put(`${this.resourceUrlAffectExpert}/${idexp}/${iduser}`, this.expert).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<Expert> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findByGouvernorat(gouvernoratId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlExpertByGouvernorat}/${gouvernoratId}`)
        .map((res: Response) => this.convertResponse(res));
    }

    findListesByGouvernorat(idGovernorate: number,partnerId: number,modeId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceExpertsByGovernorate}/${idGovernorate}/${partnerId}/${modeId}`,options)
            .map((res: Response) => this.convertResponse(res));
    }
 
    findByExpNameFTUSA(numeroFTUSA?: String,pname?: String): Observable<Expert> {
        return this.http.get(`${this.resourceUrl}/byNameFTUSA/${numeroFTUSA}/${pname}`).map((res: Response) => {
             if (res['_body']) {
                const jsonResponse = res.json();
                return jsonResponse;
            }
            return null;
     });
    }

 

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllExperts(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceViewUrl, options)
            .map((res: Response) => this.convertViewResponse(res));
    }

    queryByVille(villeId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlByVille}/${villeId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    queryNonBloque(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlNonBloque, options)
            .map((res: Response) => this.convertResponse(res));
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertViewResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateEffetConvention = this.dateUtils
            .convertLocalDateFromServer(entity.dateEffetConvention);
        entity.dateFinConvention = this.dateUtils
            .convertLocalDateFromServer(entity.dateFinConvention);
        entity.debutBlocage = this.dateUtils
            .convertLocalDateFromServer(entity.debutBlocage);
        entity.finBlocage = this.dateUtils
            .convertLocalDateFromServer(entity.finBlocage);
    }

    private convert(expert: Expert): Expert {
        const copy: Expert = Object.assign({}, expert);
        copy.dateEffetConvention = this.dateUtils
            .convertLocalDateToServer(expert.dateEffetConvention);
        copy.dateFinConvention = this.dateUtils
            .convertLocalDateToServer(expert.dateFinConvention);
        copy.debutBlocage = this.dateUtils
            .convertLocalDateToServer(expert.debutBlocage);
        copy.finBlocage = this.dateUtils
            .convertLocalDateToServer(expert.finBlocage);
        return copy;
    }
}
