import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Router } from '@angular/router';
import { Principal, ResponseWrapper } from '../../shared';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { QuoteStatus } from '../../constants/app.constants';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { SinisterPecService } from '../sinister-pec/sinister-pec.service';
import { Sinister } from '../sinister';
import { EtatAccord } from '../../constants/app.constants';
import { ConfirmationDialogService } from '../../shared';
import { UserExtraService, PermissionAccess, UserExtra } from '../user-extra';
import { ApecService, Apec } from '../apec';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import * as Stomp from 'stompjs';
import { QuotationService } from '../quotation';
import { ViewApecService } from '../view-apec/view-apec.service';
import { ViewApec } from '../view-apec/view-apec.model';
@Component({
    selector: 'jhi-instance-reparation',
    templateUrl: './instance-reparation.component.html'
})
export class InstanceReparationComponent implements OnInit, OnDestroy {
    currentAccount: any;
    prestationPECS: SinisterPec[] = [];
    eventSubscriber: Subscription;
    listePrestation: SinisterPec[] = [];
    listeViewPrestation: ViewApec[] = [];
    sinisterPec: SinisterPec = new SinisterPec();
    prestationpec: any;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    apec: Apec = new Apec();
    oneClickForButton: Boolean = true;
    ws: any;
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    notification: RefNotifAlert = new RefNotifAlert();
    userExNotif: UserExtra[] = [];


    constructor(

        private sinisterPecService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService,
        private apecService: ApecService,
        private notificationAlerteService: NotifAlertUserService,
        private quotationService: QuotationService,
        private viewApecService: ViewApecService

    ) {

    }
    loadAll() {
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.viewApecService.findByEtatAccord(this.currentAccount.id, EtatAccord.ACC_INSTANCE_REPARATION).subscribe((res) => {
                this.listeViewPrestation = res.json;
                var cache = {};
                this.listeViewPrestation = this.listeViewPrestation.filter(function (elem) {
                    return cache[elem.sinisterPecId] ? 0 : cache[elem.sinisterPecId] = 1;
                });
                this.dtTrigger.next();
            });
            this.userExtraService.findFunctionnalityEntityByUser(102, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });

        });

    }


    ngOnInit() {

        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            retrieve: true,
            // Declare the use of the extension in the dom parameter
            dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6 text-align: center;"p>>',

            language: {
                processing: 'Traitement en cours...',
                search: 'Rechercher&nbsp;:',
                lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
                info: '_START_ - _END_ / _TOTAL_',
                infoEmpty: 'La liste est vide',
                infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
                infoPostFix: '',
                loadingRecords: 'Chargement en cours...',
                zeroRecords: 'Aucun &eacute;l&eacute;ment &agrave; afficher',
                emptyTable: 'Aucune donnée disponible dans le tableau',
                paginate: {
                    first: '<button type="button" class="btn btn-sm btn-default btn-rounded"><i class="fa fa-angle-double-left" style="font-size:16px"></i></button>&nbsp;',
                    previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>&nbsp;',
                    next: '&nbsp;<i class="fa fa-angle-right" style="font-size:16px"></i>',
                    last: '&nbsp;<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
                },
                aria: {
                    sortAscending: ': activer pour trier la colonne par ordre croissant',
                    sortDescending: ': activer pour trier la colonne par ordre décroissant'
                }
            },
            // Declare the use of the extension in the dom parameter
            //dom: 'Bfrtip',
            // Configure the buttons
            buttons: [
                {
                    extend: 'print',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
                },
                {
                    extend: 'csvHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                    fieldSeparator: ';'
                },
                {
                    extend: 'excelHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
                },
                {
                    extend: 'pdfHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                    orientation: 'landscape'
                }
            ]
        };


        this.loadAll();

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestationPECS();

    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: SinisterPec) {
        return item.id;
    }
    registerChangeInPrestationPECS() {
        this.eventSubscriber = this.eventManager.subscribe('prestationPECListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    /**
    * added from home
    */

    addComplementaryQuotation(idPec: number, idApec: number) {

        this.router.navigate(['/complementary-devis-new/' + idPec + '/' + idApec]);
    }

    facturer(id) {
        this.router.navigate(['/facturation/' + id]);
    }

    modificationPrix(sinisterPec: ViewApec) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.sinisterPecService.updatePecModifPrix(sinisterPec.sinisterPecId).subscribe((resP) => {
                    this.sendNotifModifPrix(sinisterPec, 'ModifPrixEffectuer');
                    this.listeViewPrestation.forEach((item, index) => {
                        if (item.sinisterPecId === sinisterPec.sinisterPecId) this.listeViewPrestation.splice(index, 1);
                    });

                });



            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    /*modificationPrix(id) {
        this.router.navigate(['/modificationP/' + id]);

    }*/



    sendNotifModifPrix(sinisterPec: ViewApec, typeNotif: string) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtraService.finPersonneByUser(sinisterPec.assignedToId).subscribe((usrAss: UserExtra) => {
                        if (usrAss.userBossId !== null && usrAss.userBossId !== undefined) {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 41;
                            this.notifUser.source = usr.id;
                            this.notifUser.destination = usrAss.userBossId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': sinisterPec.reparateurId, 'refSinister': sinisterPec.gaReference, 'sinisterId': sinisterPec.sinisterId, 'sinisterPecId': sinisterPec.id, 'expertId': sinisterPec.expertId, 'stepPecId': sinisterPec.stepPecId });
                            this.listNotifUser.push(this.notifUser);
                        }
                        this.userExtraService.findByProfil(21).subscribe((userExNotifPartner: UserExtra[]) => {
                            this.userExNotif = userExNotifPartner;
                            this.userExNotif.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 41;
                                this.notifUser.source = usr.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': sinisterPec.reparateurId, 'refSinister': sinisterPec.gaReference, 'sinisterId': sinisterPec.sinisterId, 'sinisterPecId': sinisterPec.id, 'expertId': sinisterPec.expertId, 'stepPecId': sinisterPec.stepPecId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': sinisterPec.reparateurId, 'refSinister': sinisterPec.gaReference, 'sinisterId': sinisterPec.sinisterId, 'sinisterPecId': sinisterPec.id, 'expertId': sinisterPec.expertId, 'stepPecId': sinisterPec.stepPecId }));
                            });
                        });
                    });
                });
            });

        }
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







}
