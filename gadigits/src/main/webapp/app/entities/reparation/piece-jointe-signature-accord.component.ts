import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { Attachment } from '../attachments';
import { ConfirmationDialogService } from '../../shared';
import * as Stomp from 'stompjs';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { Reparateur, ReparateurService } from '../reparateur';
import { Authorities, PrestationPecStep } from '../../constants/app.constants';
import { Apec, ApecService } from '../apec';
import { saveAs } from 'file-saver/FileSaver';
import { Principal, ResponseWrapper } from '../../shared';
import { UserExtraService, UserExtra } from '../user-extra';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
@Component({
    selector: 'jhi-piece-jointe-dialog',
    templateUrl: './piece-jointe-signature-accord.component.html'
})
export class PieceJointeSignatureAccordComponent implements OnInit, OnDestroy {

    pieceJointe: Attachment = new Attachment();
    isSaving: boolean;
    sinisterPec: SinisterPec = new SinisterPec();
    reparateur: Reparateur = new Reparateur();
    //new implementation
    selectedFiles: FileList;
    currentFileUpload: File;
    signatureAccordPreview: any = false;
    progress: { percentage: number } = { percentage: 0 }
    currentAccount: any;
    nowNgbDate: any;
    userExNotifPartner: UserExtra[] = [];
    oneClickForButton: Boolean = true;
    ws: any;
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    notification: RefNotifAlert = new RefNotifAlert();
    extensionImage: string;
    nomImage: string;

    @Input() apec: Apec;
    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private prestationService: SinisterPecService,
        private apecService: ApecService,
        private reparateurService: ReparateurService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private notificationAlerteService: NotifAlertUserService,
        private userExtraService: UserExtraService
    ) {
    }

    ngOnInit() {
        const now = new Date();
        this.nowNgbDate = {
            year: now.getFullYear(),
            month: now.getMonth() + 1,
            day: now.getDate()
        };
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.isSaving = false;
        this.pieceJointe.entityId = this.apec.id;
        this.pieceJointe.entityName = "Accord Apec";
        this.pieceJointe.label = "Attachement Signature accord";
        this.pieceJointe.original = true;
        this.pieceJointe.note = "Signature de l’APEC";
        this.prestationService.findPrestationPec(this.apec.sinisterPecId).subscribe((res) => {
            this.sinisterPec = res;
        });


    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregister ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.isSaving = true;

                    if (this.sinisterPec.id !== undefined && this.sinisterPec.id !== null) {
                        this.prestationService.saveAttachmentsEntity(this.sinisterPec.id, this.currentFileUpload, this.pieceJointe.label, this.pieceJointe.note, "APEC Accord").subscribe((res: Attachment) => {
                            this.pieceJointe = res;
                            this.apec.testAttAccord = true;
                            if (this.sinisterPec.reparateurId != null) {
                                this.sendNotif(this.sinisterPec.reparateurId, "l’accord de prise en charge signé par l’assuré a été rattaché");
                            }
                            this.apec.etat = 10;
                            this.apec.signatureDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                            this.apecService.update(this.apec).subscribe((res) => {


                                this.apec = res;
                                this.apec.sinisterPec = this.sinisterPec;
                                this.activeModal.close(this.apec);

                            });

                            //this.eventManager.broadcast({ name: 'pieceJointeListeModification', content: 'OK' });
                        });
                    } else {
                        if (this.sinisterPec.reparateurId != null) {
                            this.sendNotif(this.sinisterPec.reparateurId, "l’accord de prise en charge signé par l’assuré a été rattaché");
                        }
                        this.apec.etat = 10;
                        this.apec.signatureDate = new Date();
                        this.apecService.update(this.apec).subscribe((res) => {


                            this.apec = res;
                            this.apec.sinisterPec = this.sinisterPec;
                            this.activeModal.close(this.apec);

                        });
                    }



                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
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

    //connect to send Notif
    sendNotif(id, typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            if (id != null && id != undefined) {
                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                    this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                        if (usr.profileId == 28) {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 42;
                            this.notifUser.source = usr.id;
                            this.notifUser.destination = this.sinisterPec.assignedToId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': id, 'refSinister': this.sinisterPec.reference, 'sinisterId': this.sinisterPec.sinisterId, 'sinisterPecId': this.sinisterPec.id, 'expertId': this.sinisterPec.expertId });
                            this.listNotifUser.push(this.notifUser);
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': id, 'refSinister': this.sinisterPec.reference, 'sinisterId': this.sinisterPec.sinisterId, 'sinisterPecId': this.sinisterPec.id }));
                            });
                        }
                        if (usr.profileId == 6) {
                            this.userExtraService.finUsersByPersonProfil(id, 28).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 42;
                                    this.notifUser.source = usr.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': id, 'refSinister': this.sinisterPec.reference, 'sinisterId': this.sinisterPec.sinisterId, 'sinisterPecId': this.sinisterPec.id });
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': id, 'refSinister': this.sinisterPec.reference, 'sinisterId': this.sinisterPec.sinisterId, 'sinisterPecId': this.sinisterPec.id }));
                                });
                            });
                        }
                    });
                });
            }
        }
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }



    onFileChange(fileInput: any) {
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.pieceJointe.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.pieceJointe.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.currentFileUpload = fileInput.target.files[0];
        this.signatureAccordPreview = true;
        console.log(this.currentFileUpload);
    }

    downloadSignatureAccordFile() {
        if (this.currentFileUpload) {
            saveAs(this.currentFileUpload);
        }
    }

    private subscribeToSaveResponse(result: Observable<Attachment>) {
        result.subscribe((res: Attachment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Attachment) {
        this.eventManager.broadcast({ name: 'pieceJointeListeModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }



    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    ngOnDestroy() {
    }
}


