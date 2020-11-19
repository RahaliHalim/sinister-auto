import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { Result } from '../sinister-pec/result.model';
import { PieceJointe } from './piece-jointe.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PieceJointeService {

    res: any;
    private resourceUrl = 'api/piece-jointes';
    private resourceSearchUrl = 'api/_search/piece-jointes';
    private resourceUrlPrestation = 'api/piece-jointes/prestation';
    private resourceUrlDevis = 'api/piece-jointes/devis';
    private resourceUrlAffectChemin = 'api/piece-jointes/affect';
    private resourceUrlUpload = 'api/piece-jointes/upload';
    private resourceUrlGenratePieceJointe = 'api/pieceJointePdf';
    private resourceUrlSignature = 'api/piece-jointes/Signature';
   

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pieceJointe: PieceJointe): Observable<PieceJointe> {
        const copy = this.convert(pieceJointe);
        
        //formData.append('X-XSRF-TOKEN', this._cookieService.get("XSRF-TOKEN"));
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    


    

    createSignature(pieceJointe: PieceJointe): Observable<PieceJointe> {
        const copy = this.convert(pieceJointe);
        
        //formData.append('X-XSRF-TOKEN', this._cookieService.get("XSRF-TOKEN"));
        return this.http.post(this.resourceUrlSignature, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(pieceJointe: PieceJointe): Observable<PieceJointe> {
        const copy = this.convert(pieceJointe);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findByPrestation(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlPrestation}/${id}`).map((res: Response) => this.convertResponse(res));
    }

    findByDevis(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlDevis}/${id}`).map((res: Response) => this.convertResponse(res));
    }

      affectChemin(): Observable<Result> {
        return this.http.get(this.resourceUrlAffectChemin).map((res: Response) => {
            if (res['_body']) {
                const jsonResponse = res.json();
                this.convertItemFromServer(jsonResponse);
                return jsonResponse;
            }
            return null;
        });
    }

    find(id: number): Observable<PieceJointe> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateImport = this.dateUtils
            .convertLocalDateFromServer(entity.dateImport);
    }

    private convert(pieceJointe: PieceJointe): PieceJointe {
        const copy: PieceJointe = Object.assign({}, pieceJointe);
        copy.dateImport = this.dateUtils
            .convertLocalDateToServer(pieceJointe.dateImport);
        return copy;
    }

    updateFile = (pieceJointe: PieceJointe, file) => {
        let headers = new Headers();
        let formData: FormData = new FormData();
        formData.append('chemin', file);

        // headers.append('enctype', 'multipart/form-data'); don't need since Angular 4
        headers.append('Accept', 'application/json');
        let options = new RequestOptions({headers: headers});
        if (pieceJointe.id !== undefined) {
            return this.http.post(`${this.resourceUrlUpload}/${pieceJointe.id}`, formData, options).map(res => res.json())
                .catch(error => Observable.throw(error))
                .subscribe(
                    data => console.log('success'),
                    error => console.log(error)
                )
        } 
    }

    createFile = (file) => {
        let headers = new Headers();
        let formData: FormData = new FormData();
        formData.append('chemin', file);

        // headers.append('enctype', 'multipart/form-data'); don't need since Angular 4
        headers.append('Accept', 'application/json');
        let options = new RequestOptions({headers: headers});
        
            return this.http.post(`${this.resourceUrlUpload}`, formData, options).map(res => res.json())
            .catch(error => Observable.throw(error))
            .subscribe(
                data => console.log('success'),
                error => console.log(error)
            )

        
    }

    generatePJ(pieceJointe: PieceJointe): Observable<Response> {
        const copy = this.convertPieceJointe(pieceJointe);
        return this.http.post(this.resourceUrlGenratePieceJointe, copy);
    }
    
    private convertPieceJointe(pieceJointe: PieceJointe): PieceJointe {
        const copy: PieceJointe = Object.assign({}, pieceJointe);
        return copy;
    }
}
