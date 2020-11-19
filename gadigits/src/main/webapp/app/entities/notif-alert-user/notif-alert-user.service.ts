import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { NotifAlertUser } from './notif-alert-user.model';
import { createRequestOption, ResponseWrapper } from '../../shared';
import { ViewNotification } from './view-notification.model';

@Injectable()
export class NotifAlertUserService {

    private resourceUrl = 'api/notif-alert-user';
    private resourceViewUrl = 'api/view-notification';

    private resourceUrlReadNotification = 'api/notif-alert-user-to-read';
    private resourceUrlReadNotificationForUser = 'api/notif-alert-user-to-read-for-user';
    private resourceUrlReadNotificationForUserReparateur = 'api/notif-alert-user-to-read-for-user-reparateur';


    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(notification: NotifAlertUser[]): Observable<NotifAlertUser> {
        console.log("save notifff or alert user");
        return this.http.post(`${this.resourceUrl}`, notification).map((res: Response) => {
            return res.json();
        });
    }

    update(notification: NotifAlertUser): Observable<NotifAlertUser> {
        const copy = this.convert(notification);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<NotifAlertUser[]> {
        console.log("list notifff or alert user");
        return this.http.get(this.resourceUrl).map((res: Response) => {
            return res.json();
        });
    }
    viewQuery(type: string,req?: any,): Observable<ViewNotification[]> {
        return this.http.get(`${this.resourceViewUrl}/${type}`).map((res: Response) => {
            return res.json();
        });
    }

    queryReadNotification(idPec: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlReadNotification}/${idPec}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryReadNotificationForUser(idPec: number, stepId: number, idUser: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlReadNotificationForUser}/${idPec}/${stepId}/${idUser}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryReadNotificationForUserReparateur(idPec: number, stepId: number, idUser: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlReadNotificationForUserReparateur}/${idPec}/${stepId}/${idUser}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    private convert(notification: NotifAlertUser): NotifAlertUser {
        const copy: NotifAlertUser = Object.assign({}, notification);
        return copy;
    }

    private convertResponsePec(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

}