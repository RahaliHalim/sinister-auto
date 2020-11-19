import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from "rxjs/Observable";
import { Rules } from "./rules.model";

@Injectable()
export class RulesService {

    private resourceUrl = 'api/rules';

    constructor(private http: Http) { }

    findByCode(code: string): Observable<Rules[]> {
        return this.http.get(`${this.resourceUrl}/${code}`).map((res: Response) => {
            return res.json();
        });
    }
}