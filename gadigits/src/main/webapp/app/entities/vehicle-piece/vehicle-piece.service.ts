import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { VehiclePiece } from './vehicle-piece.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehiclePieceService {

    private resourceUrl = 'api/vehicle-pieces';
    private resourceSearchUrl = 'api/_search/vehicle-pieces';
    private resourceUrlView = 'api/view/vehicle-pieces';

    constructor(private http: Http) { }

    create(vehiclePiece: VehiclePiece): Observable<VehiclePiece> {
        const copy = this.convert(vehiclePiece);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(vehiclePiece: VehiclePiece): Observable<VehiclePiece> {
        const copy = this.convert(vehiclePiece);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<VehiclePiece> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    getPieceByReference(reference: string, type: number): Observable<VehiclePiece> {
        let formdata: FormData = new FormData();
        formdata.append('reference', reference);
        return this.http.put(`${this.resourceUrlView}/reference/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }
    getPieceByReferenceRef(reference: string, type: number): Observable<VehiclePiece> {
        let formdata: FormData = new FormData();
        formdata.append('reference', reference);
        return this.http.put(`${this.resourceUrlView}/reference/ref/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }
    getPieceByCode(code: string, type: number): Observable<VehiclePiece> {
        return this.http.get(`${this.resourceUrlView}/code/${code}/${type}`).map((res: Response) => {
            return res.json();
        });
    }
    getByTypeAndTapedReference(reference: string, type: number): Observable<VehiclePiece[]> {
        let formdata: FormData = new FormData();
        formdata.append('reference', reference);
        return this.http.put(`${this.resourceUrlView}/typed/reference/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }
    getByTypeAndTapedDesignation(designation: string, type: number): Observable<VehiclePiece[]> {
        let formdata: FormData = new FormData();
        formdata.append('designation', designation);
        return this.http.put(`${this.resourceUrlView}/typed/designation/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }
    getPieceByDesignation(designation: string, type: number): Observable<VehiclePiece> {
        let formdata: FormData = new FormData();
        formdata.append('designation', designation);
        return this.http.put(`${this.resourceUrlView}/designation/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }

    getPieceByDesignationIsPresent(designation: string, type: number): Observable<Boolean> {
        let formdata: FormData = new FormData();
        formdata.append('designation', designation);
        return this.http.put(`${this.resourceUrlView}/designation/design/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }

    getPieceByDesignationAutoComplete(designation: string, type: number): Observable<VehiclePiece> {
        let formdata: FormData = new FormData();
        formdata.append('designation', designation);
        return this.http.put(`${this.resourceUrlView}/designation/complete/${type}`, formdata).map((res: Response) => {
            return res.json();
        });
    }

    getPiecesByType(id: number): Observable<VehiclePiece[]> {
        return this.http.get(`${this.resourceUrlView}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(vehiclePiece: VehiclePiece): VehiclePiece {
        const copy: VehiclePiece = Object.assign({}, vehiclePiece);
        return copy;
    }
}
