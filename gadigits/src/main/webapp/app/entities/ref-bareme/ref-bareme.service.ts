import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RefBareme } from './ref-bareme.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { JhiDateUtils } from 'ng-jhipster';
import { Attachment } from '../attachments';

@Injectable()
export class RefBaremeService {

    private resourceUrl = 'api/ref-baremes';
    private resourceSearchUrl = 'api/_search/ref-baremes';
    private ressourceUrlWithoutPagination = 'api//ref-baremes-without-pagination'
    private resourceUrlCroquis = 'api/ref-baremes/attachments';
    private refbareme = new RefBareme();

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    createRef(file: File,refBareme: RefBareme): Observable<RefBareme> {
        console.log('___________________create service front__________________________');
        const copy = this.convert(refBareme);
        let formdata: FormData = new FormData();
        console.log(JSON.stringify(copy));
        formdata.append('croquis', file);
        formdata.append('refbareme', new Blob([JSON.stringify(copy)], {type: 'application/json'}));


        return this.http.post(this.resourceUrl, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    create(refBareme: RefBareme): Observable<RefBareme> {
        const copy = this.convert(refBareme);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }
    //Le champ translation-not-found[auxiliumApp.refBaremeDTO.chemin] ne peut pas être vide !;

    saveAttachments(id: number, croquisFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('croquis', croquisFile);
        return this.http.post(`${this.resourceUrlCroquis}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    updateAttachments(id: number, croquisFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('croquis', croquisFile);
        return this.http.put(`${this.resourceUrlCroquis}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils.convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils.convertLocalDateFromServer(entity.endDate);
    }

    update(refBareme: RefBareme): Observable<RefBareme> {
        const copy = this.convert(refBareme);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }


    find(id: number): Observable<RefBareme> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    getBaremesWithoutPagination(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.ressourceUrlWithoutPagination, options)
            .map((res: Response) => this.convertResponse(res));
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

    private convert(refBareme: RefBareme): RefBareme {
        const copy: RefBareme = Object.assign({}, refBareme);
        return copy;
    }
}
