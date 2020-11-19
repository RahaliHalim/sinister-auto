import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Upload } from './upload.model';
import { JhiDateUtils } from 'ng-jhipster';
import { ResponseContentType } from '@angular/http';
@Injectable()
export class UploadService {

    resourceUrlRef= 'api/referentiels';
    private resourceUrl = 'api/upload';
    private resourceUrlByLabel = 'api/vehicle-brands/label';
    private resourceSearchUrl = 'api/_search/vehicle-brands';
    private resourceUrlUploadXls = 'api/upload/xls';

    constructor(private http: Http,private dateUtils: JhiDateUtils) { }

    createRef(file: File,upload: Upload): Observable<Upload> {
        console.log('___________________create service front__________________________');
        const copy = this.convert(upload);
        let formdata: FormData = new FormData();
        formdata.append('croquis', file);
        formdata.append('refupload', new Blob([JSON.stringify(copy)], {type: 'application/json'}));


        return this.http.post(this.resourceUrl, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    create(upload: Upload): Observable<Upload> {
        const copy = this.convert(upload);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(upload: Upload): Observable<Upload> {
        const copy = this.convert(upload);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Upload> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    getUploadXls(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrlUploadXls}/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';

            const data  = res.blob();;
            return data;
        });
    }

    findByLabel(label: string): Observable<Upload> {
        return this.http.get(`${this.resourceUrlByLabel}/${label}`).map((res: Response) => {
            return res.json();
        });
    }
    //get all ref 
    queryReferentiel(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlRef, options)
            .map((res: Response) => this.convertResponse(res));
    }
    //get all upload
    queryUpload(req?: any): Observable<ResponseWrapper> {
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

    private convert(vehicleBrand: Upload): Upload {
        const copy: Upload = Object.assign({}, vehicleBrand);
        return copy;
    }
    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils.convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils.convertLocalDateFromServer(entity.endDate);
    }
    
}
