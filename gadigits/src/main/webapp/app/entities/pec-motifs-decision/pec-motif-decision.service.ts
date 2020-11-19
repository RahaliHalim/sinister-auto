import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from "rxjs/Observable";
import { PecMotifDecision } from "./pec-motif-decision.model";


@Injectable()
export class PecMotifDecisionService {

    private resourceUrl = 'api/motifs-pec';

    constructor(private http: Http) { }

    create(pecModifDecision: PecMotifDecision): Observable<PecMotifDecision> {
        console.log("in service fromt save motifs------");
        return this.http.post(this.resourceUrl, pecModifDecision).map((res: Response) => {
            return res.json();
        });
    }
}