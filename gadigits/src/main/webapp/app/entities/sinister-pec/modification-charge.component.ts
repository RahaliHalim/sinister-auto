import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { GaDatatable } from '../../constants/app.constants';


import { ITEMS_PER_PAGE, Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { UserCelluleService } from '../user-cellule/user-cellule.service';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { DataTableDirective } from 'angular-datatables';
import { UserExtraService, UserExtra } from '../user-extra';
import { DateUtils } from '../../utils/date-utils';
import * as Stomp from 'stompjs';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { ViewSinisterPecService, ViewSinisterPec } from '../view-sinister-pec';




@Component({
    selector: 'jhi-modification-charge',
    templateUrl: './modification-charge.component.html'
})
export class ModificationChargeComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    sinisterPecs: ViewSinisterPec[] = [];
    sinisterPec: SinisterPec = new SinisterPec();
    currentAccount: any;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    user: UserExtra = new UserExtra();
    users: UserExtra[];
    userExtra: UserExtra = new UserExtra();
    userExtras: UserExtra[];
    masterSelected: boolean;
    selectedAll: any;
    ws: any;
    oneAlert = true;
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    oneClickForButton: Boolean = true;
    sinisterPecsSelected: ViewSinisterPec[] = [];


    constructor(
        private sinisterPecService: SinisterPecService,
        private principal: Principal,
        private alertService: JhiAlertService,
        private userExtraService: UserExtraService,
        private owerDateUtils: DateUtils,
        private notificationAlerteService: NotifAlertUserService,
        private viewSinisterPecService: ViewSinisterPecService,
        private confirmationDialogService: ConfirmationDialogService,
    ) {

    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.userExtraService.findByProfils().subscribe((res: UserExtra[]) => {
            this.users = res;
        });


    }

    loadAll() {
        //this.sinisterPecService.findPrestationEmpty().subscribe((res) => {
        this.sinisterPecs = []
        this.dtTrigger.next();
        // });
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', ' Êtes-vous sûrs de vouloir Soumettre ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.oneAlert = true;
                    let m = 0;
                    this.sinisterPecsSelected = [];
                    this.sinisterPecs.forEach(sinPec => {

                        if (sinPec.isSelected == true) {
                            this.sinisterPecsSelected.push(sinPec);
                        }
                    });
                    console.log("lengthavant-----------------" + this.sinisterPecsSelected.length);
                    this.sinisterPecsSelected.forEach(sinPec => {
                        m++;
                        console.log("test1-----------------" + m);
                        this.sinisterPecService.findPrestationPec(sinPec.id).subscribe((pec: SinisterPec) => {
                            this.sinisterPec = pec;

                            this.sinisterPec.assignedToId = this.userExtra.userId;

                            this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                this.sinisterPec = res;
                                if (m == this.sinisterPecsSelected.length) {
                                    this.sendNotifPec('modificationCharge');
                                    this.rerender();
                                    this.selectedAll = false;
                                    this.user = new UserExtra();
                                    this.userExtra = new UserExtra();
                                    this.loadAll();
                                    if (this.oneAlert) {
                                        this.oneAlert = false;
                                        this.alertService.success('auxiliumApp.sinisterPec.updatedCharge', null, null);
                                    }
                                }
                            });
                        });

                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

        //this.listePrestationByUserId(this.user.userId); 
    }


    selectAll() {
        for (var i = 0; i < this.sinisterPecs.length; i++) {
            this.sinisterPecs[i].isSelected = this.selectedAll;
        }
    }

    checkIfAllSelected() {
        this.selectedAll = this.sinisterPecs.every(function (item: any) {
            return item.isSelected == true;
        })
    }

    sendNotifPec(typeNotif) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {

                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 19;
                    this.notifUser.source = usr.userId;
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.notification = this.notification;
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'refSinister': this.sinisterPec.reference, 'sinisterId': this.sinisterPec.sinisterId, 'sinisterPecId': this.sinisterPec.id });
                    this.listNotifUser.push(this.notifUser);
                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                        this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'refSinister': this.sinisterPec.reference, 'sinisterId': this.sinisterPec.sinisterId, 'sinisterPecId': this.sinisterPec.id }));
                    });
                });
            });
        }
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }



    listePrestationByUserId(chargeId) {

        this.viewSinisterPecService.getAllSinisterPecByAssignedId(chargeId).subscribe((res) => {
            this.sinisterPecs = res.json;
            this.rerender();
        });
        this.userExtras = [];
        this.users.forEach(usrEx => {
            if (usrEx.id !== chargeId) {
                this.userExtras.push(usrEx);
            }

        });
        if (this.userExtras && this.userExtras.length) {
            this.userExtra.userId = this.userExtras[0].userId;
        }

    }

    dateAsYYYYMMDDHHNNSS(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + ' ' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    dateAsYYYYMMDDHHNNSSLDT(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + 'T' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
            + String(val)).slice(String(val).length);
    }

    ngOnDestroy() {
    }

} 
