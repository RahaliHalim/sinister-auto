import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Reparateur, RefAgeVehicule } from './reparateur.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReparateurService {
    private resourceUrlRefAgeVehicule = 'api/refAgeVehicules';
    private resourceUrl = 'api/reparateurs';
    private resourceSearchUrl = 'api/_search/reparateurs';
    private resourceUrlReparateurByGouvernorat = 'api/reparateurs/gouvernorat';
    private resourceUrlAffectReparateur = 'api/affectReparateur'
    private resourceUrlFindRepIdByUser = 'api/findRepIdByIdUser'
    private motifrmqUrl = 'api/reparateurs/bloquemotif';
    private resourceUrlJounal = 'api/journal/reparateur';

    private resourceViewUrl = 'api/view/reparators';

    reparateur: any;
    public id: any;
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(reparateur: Reparateur): Observable<Reparateur> {
        const copy = this.convert(reparateur);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(reparateur: Reparateur): Observable<Reparateur> {
        const copy = this.convert(reparateur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();;
        });
    }
    updateReparateur(reparateur: Reparateur): Observable<Reparateur> {
        const copy: Reparateur = Object.assign({}, reparateur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();;
        });
    }
    updateAffectReparateur(idrep: number, iduser: number): Observable<Reparateur> {
        this.find(idrep).subscribe((res) => this.reparateur = res)
        return this.http.put(`${this.resourceUrlAffectReparateur}/${idrep}/${iduser}`, this.reparateur).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    updatedeux(reparateur: Reparateur): Observable<Reparateur> {
        const copy = this.convert(reparateur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Reparateur> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();

            return jsonResponse;
        });
    }

    bloquerMotif(id: number, motifs: number[]): Observable<Reparateur> {
        this.find(id).subscribe((res) => { this.reparateur = res; })
        return this.http.put(`${this.motifrmqUrl}/${id}/${motifs}`, this.reparateur).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findjournal(id: number): Observable<Reparateur> {
        this.id = id;
        console.log("get id reparateur" + id);
        return this.http.get(`${this.resourceUrlJounal}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findByExpNameFTUSA(reparateur:Reparateur): Observable<Reparateur> {
        const copy = this.convert(reparateur);
        return this.http.post(`${this.resourceUrl}/byNameRegistreCommerce`, copy).map((res: Response) => {
             if (res['_body']) {
                const jsonResponse = res.json();
                return jsonResponse;
            }
            return null;
     });
    }
    findByGouvernorat(gouvernoratId: number): Observable<Reparateur[]> {
        return this.http.get(`${this.resourceUrlReparateurByGouvernorat}/${gouvernoratId}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllReparators(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceViewUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryrefAgeVehicule(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlRefAgeVehicule, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findRefAgeVehicule(id: number): Observable<RefAgeVehicule> {
        return this.http.get(`${this.resourceUrlRefAgeVehicule}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();

            return jsonResponse;
        });
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

    private convertItemFromServer(entity: any) {
        entity.dateEffetConvention = this.dateUtils
            .convertLocalDateFromServer(entity.dateEffetConvention);
        entity.dateFinConvention = this.dateUtils
            .convertLocalDateFromServer(entity.dateFinConvention);
        entity.dateDebutBlocage = this.dateUtils
            .convertLocalDateFromServer(entity.dateDebutBlocage);
        entity.dateFinBlocage = this.dateUtils
            .convertLocalDateFromServer(entity.dateFinBlocage);
    }


    private convert(reparateur: Reparateur): Reparateur {
        const copy: Reparateur = Object.assign({}, reparateur);
        copy.dateEffetConvention = this.dateUtils
            .convertLocalDateToServer(reparateur.dateEffetConvention);
        copy.dateFinConvention = this.dateUtils
            .convertLocalDateToServer(reparateur.dateFinConvention);
        copy.dateDebutBlocage = this.dateUtils
            .convertLocalDateToServer(reparateur.dateDebutBlocage);
        copy.dateFinBlocage = this.dateUtils
            .convertLocalDateToServer(reparateur.dateFinBlocage);
        return copy;
    }
}
