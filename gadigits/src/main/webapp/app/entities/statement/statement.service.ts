import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Statement } from './statement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StatementService {

    private resourceUrl = 'api/statements';
    private resourceSearchUrl = 'api/_search/statements';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(statement: Statement): Observable<Statement> {
        const copy = this.convert(statement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(statement: Statement): Observable<Statement> {
        const copy = this.convert(statement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Statement> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    validate(id: number): Observable<Statement> {
        return this.http.get(`${this.resourceUrl}/validate/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    invalidate(id: number): Observable<Statement> {
        return this.http.get(`${this.resourceUrl}/invalidate/${id}`).map((res: Response) => {
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

    getStatementPdf(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrl}/get/pdf/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';

            const data  = res.blob();;
            return data;
        });
    }

    generateStatementPdf(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrl}/generate/pdf/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';

            const data  = res.blob();;
            return data;
        });
    }

    exportStatementInExcel(id: number, month: string): Observable<Blob> {
        return this.http.get(`${this.resourceUrl}/${month}/export/excel/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';

            const data  = res.blob();;
            return data;
        });;
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(entity.creationDate);
    }

    private convert(statement: Statement): Statement {
        const copy: Statement = Object.assign({}, statement);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(statement.creationDate);
        return copy;
    }
}
