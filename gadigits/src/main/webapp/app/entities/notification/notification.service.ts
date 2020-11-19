import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { JhiNotification } from './notification.model';

@Injectable()
export class NotificationService {

    private resourceUrl = 'api/notifications';


    constructor(private http: Http , private dateUtils: JhiDateUtils) { }

    /*create(commentaire: string, isClosed: boolean, notification: Notification): Observable<Notification> {
        //const copy = this.convert(partner);
        return this.http.post(`${this.resourceUrl}/${commentaire}/${isClosed}`, notification).map((res: Response) => {
            return res.json();
        });
    }*/

    create(notification: JhiNotification): Observable<JhiNotification> {
        //const copy = this.convert(partner);
        console.log("save notifff");
        return this.http.post(`${this.resourceUrl}`, notification).map((res: Response) => {
            return res.json();
        });
    }
}