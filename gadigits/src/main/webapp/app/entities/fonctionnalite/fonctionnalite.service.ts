import { Http, Response } from "@angular/http";
import { Fonctionnalite } from "./fonctionnalite.model";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable()
export class FonctionnaliteService {

    private resourceUrl = 'api/fonctionnalites';

    constructor(private http:Http){}

    find(id: number): Observable<Fonctionnalite> {
        
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }


}