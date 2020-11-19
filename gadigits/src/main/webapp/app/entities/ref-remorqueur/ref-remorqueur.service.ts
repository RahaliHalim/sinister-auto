import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { RefRemorqueur } from './ref-remorqueur.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { PersonneMorale } from '../personne-morale';
import { SinisterPec } from '../sinister-pec';


@Injectable()
export class RefRemorqueurService {
    refRemorqueur:any;
    public id: any;
    private resourceUrlJounal  = 'api/journal/remorqueur';
    private resourceUrl = 'api/ref-remorqueurs';
    private resourceUrlRemorqueursWithOrder = 'api/remorqueurs/ref-remorqueurs';
    private resourceUrlUnbloquedRmq = 'api/ref-remorqueurs/nonbloque';
    private resourceBloqueRmqUrl = 'api/ref-remorqueurs/bloque';
    private resourceSearchUrl = 'api/_search/ref-remorqueurs';
    private motifrmqUrl = 'api/ref-remorqueurs/bloquemotif';
    private resourceUrlCloture = 'api/ref-remorqueurs/cloture';
    private resourceUrlRemorqueurCloture = 'api/generateRemorqueurBordereau';
    constructor(private http: Http) { }

    create(refRemorqueur: RefRemorqueur): Observable<RefRemorqueur> {
        const copy = this.convert(refRemorqueur);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(refRemorqueur: RefRemorqueur): Observable<RefRemorqueur> {
        const copy = this.convert(refRemorqueur);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RefRemorqueur> {
        this.id = id;
        console.log("getidremorquer"+id);
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findjournal(id: number): Observable<RefRemorqueur> {
        this.id = id;
        console.log("getidremorquer"+id);
        return this.http.get(`${this.resourceUrlJounal}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryCloture(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlCloture, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryNonBloque(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlUnbloquedRmq, options)
            .map((res: Response) => this.convertResponse(res));
    }

    bloquerMotif(id: number, motifs: number[]): Observable<RefRemorqueur> {
        this.find(id).subscribe((res) => { this.refRemorqueur = res; })
        const copy = this.refRemorqueur;
        return this.http.put(`${this.motifrmqUrl}/${id}/${motifs}`,copy).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
     });
    }
   
    generateRemorqueurBordereau(prestationPEC: SinisterPec  ): Observable<Response> {
        const copy = this.convertPrestationPEC(prestationPEC);
        return this.http.post(this.resourceUrlRemorqueurCloture, copy);
    }

    queryRemorqueursWithOrder(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlRemorqueursWithOrder}/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    bloque(refRemorqueur: RefRemorqueur): Observable<Response> {

        const copy = this.convert(refRemorqueur);
        return this.http.put(this.resourceBloqueRmqUrl, copy).map((res: Response) => {
            return res.json();
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

    findByTugName(refRemorqueur: PersonneMorale): Observable<RefRemorqueur> {
        const copy = this.convertPersonne(refRemorqueur);
        return this.http.post(`${this.resourceUrl}/byName`,copy).map((res: Response) => {
             if (res['_body']) {
                const jsonResponse = res.json();
                return jsonResponse;
            }  
            return null;
     });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(refRemorqueur: RefRemorqueur): RefRemorqueur {
        const copy: RefRemorqueur = Object.assign({}, refRemorqueur);
        return copy;
    }

    private convertPrestationPEC(prestationPEC:  SinisterPec):  SinisterPec { // convertAffectExpert
        const copy: SinisterPec = Object.assign({}, prestationPEC);
        return copy;
    }

    

    private convertPersonne(personneMorale: PersonneMorale): PersonneMorale {
        const copy: PersonneMorale = Object.assign({}, personneMorale);
        return copy;
    }
}
