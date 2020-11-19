import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { ResponseWrapper } from '../../shared';
import { Observable } from "rxjs";

@Injectable()
export class UserReslationService{

    private resourceChargeUrl = 'api/user-relations/charge';
    private resourceResponsableUrl = 'api/user-relations/responsable';
    private resourceDirecteurUrl = 'api/user-relations/directeur';

    constructor(private http: Http){}

    queryCharge(): Observable<ResponseWrapper> {
        console.log("charge service");
        return this.http.get(this.resourceChargeUrl)
            .map((res: Response) => this.convertResponse(res));
    }
    queryResponsable(): Observable<ResponseWrapper> {
        console.log("responsable service");
        return this.http.get(this.resourceResponsableUrl)
            .map((res: Response) => this.convertResponse(res));
    }
    queryDirecteur(): Observable<ResponseWrapper> {
        console.log("directeur service");
        return this.http.get(this.resourceDirecteurUrl)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}