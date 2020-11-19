import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { RefNotifAlert } from './ref-notif-alert.model';

@Injectable()
export class RefNotifAlertService {

    private resourceUrl = 'api/ref-notif-alert';


    constructor(private http: Http , private dateUtils: JhiDateUtils) { }

    create(notification: RefNotifAlert): Observable<RefNotifAlert> {
        console.log("save notifff");
        return this.http.post(`${this.resourceUrl}`, notification).map((res: Response) => {
            return res.json();
        });
    }
}