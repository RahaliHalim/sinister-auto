import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Camion } from './camion.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()

export class CamionService {

    camions: Camion[];
    public id: any;
    private resourceUrl = 'api/camions';
    private resourceSearchUrl = 'api/_search/camions';
    private resourceUrlCamionByRmq = 'api/camions/refRemorqueur';
    private resourceUrlFindByServiceType = 'api/camions/serviceType';
    private resourceUrlFindMainCamion = 'api/camion/main/refRemorqueur';
    private resourceUrlFindByServiceTypeAndByGovernorate = 'api/camions/serviceType-governorate';

    constructor(private http: Http) { }

    create(camion: Camion): Observable<Camion> {
        const copy = this.convert(camion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(camion: Camion): Observable<Camion> {
        const copy = this.convert(camion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    findByRefRemorqueur(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlCamionByRmq}/${id}`).map((res: Response) => this.convertResponse(res));
    }

    findByServiceType(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlFindByServiceType}/${id}`).map((res: Response) => this.convertResponse(res));
    }

    findByServiceTypeAndByGovernorate(id: number, governorateId: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlFindByServiceTypeAndByGovernorate}/${id}/${governorateId}`).map((res: Response) => this.convertResponse(res));
    }

    find(id: number): Observable<Camion> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByImmatriculation(rn: string): Observable<Camion> {
        return this.http.get(`${this.resourceUrl}/rn/${rn}`).map((res: Response) => {
            return res.json();
        });
    }

    findMainContactOfRefRemorqueur(idRefRemorqueur: number): Observable<Camion> {
        return this.http.get(`${this.resourceUrlFindMainCamion}/${idRefRemorqueur}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(camion: Camion): Camion {
        const copy: Camion = Object.assign({}, camion);
        return copy;
    }
}
