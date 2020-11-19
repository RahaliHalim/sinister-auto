import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { GaDatatable } from '../../constants/app.constants';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { UserCelluleService } from '../user-cellule/user-cellule.service';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { Sinister, SinisterService } from '../sinister';
import { DataTableDirective } from 'angular-datatables';
import { DateUtils } from '../../utils/date-utils';
import { RefTypePieces, RefTypePiecesService } from '../ref-type-pieces';
import { RefTypePj, RefTypePjService } from '../ref-type-pj';
import { Attachment } from '../attachments';
import { saveAs } from 'file-saver/FileSaver';
import { ConfirmationDialogService } from '../../shared';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
import { UserExtraService, UserExtra } from '../user-extra';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import * as Stomp from 'stompjs';



@Component({
    selector: 'jhi-imprime',
    templateUrl: './imprime.component.html'
})
export class ImprimeComponent implements OnInit, OnDestroy {

    sinisterPecs: ViewSinisterPec[];
    sinisters: Sinister[];
    sinister: Sinister = new Sinister();
    currentAccount: any;
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    piecesFiles: File[] = [];
    pieceFiles1: File;
    AjouterPiece = false;
    DateArrv: Date;
    nowNgbDate: any;
    labelPiece: String = "piece";
    labelPiece1: string;
    piecesAttachments: Attachment[] = [];
    pieceAttachment: Attachment = new Attachment();
    pieceAttachment1: Attachment = new Attachment();
    SinisterPecView: ViewSinisterPec = new ViewSinisterPec();
    refTypePiecesList: RefTypePj[];
    canAddPiece = true;
    attachment: Attachment = new Attachment;
    attachmentList: Attachment[];
    SinesterPecSelected: SinisterPec = new SinisterPec();
    AjoutNouvelPiece = false;
    file: File;
    selectedRow: Number;
    sinPecid: number;
    selectedItem: boolean = true;
    activeBtSaveImp: boolean = false;
    activeBtSave = false;
    piecePreview1 = false;
    extensionImage: string;
    nomImage: string;
    oneClickForButton: boolean = true;
    notifUser: NotifAlertUser;
    notification: RefNotifAlert = new RefNotifAlert();
    listNotifUser: NotifAlertUser[] = [];
    ws: any;


    constructor(
        private sinisterPecService: SinisterPecService,
        private principal: Principal,
        private alertService: JhiAlertService,
        private route: ActivatedRoute,
        private sinisterService: SinisterService,
        private refTypePjService: RefTypePjService,
        private confirmationDialogService: ConfirmationDialogService,
        private viewSinisterPecService: ViewSinisterPecService,
        private router: Router,
        private userExtraService: UserExtraService,
        private notificationAlerteService: NotifAlertUserService,

    ) {

    }

    ngOnInit() {

        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.refTypePjService.query().subscribe((res) => {
            this.refTypePiecesList = res.json;
        });
        this.viewSinisterPecService.findAllSinPecWithDecision().subscribe((res) => {
            this.sinisterPecs = res.json;
            this.dtTrigger.next();
        });
        this.pieceAttachment1.original = true;


        this.pieceAttachment.creationDateP = this.dateAsYYYYMMDDHHNNSS(new Date());
    }


    setClickedRow(index: number, refbid: number) {


        this.selectedRow = index;
        this.sinPecid = refbid
        this.selectedItem = false;
        this.activeBtSaveImp = true;
        this.activeBtSave = true;

    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous s�rs de vouloir enregistrer?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinPecid) {
                    const sinPec = this.sinPecid;
                    console.log(this.pieceFiles1);
                    this.piecesAttachments.forEach(pieceAttFile => {
                        if (pieceAttFile.pieceFile && !pieceAttFile.id) {
                            if (pieceAttFile.note === null || pieceAttFile.note == undefined) { pieceAttFile.note = 'vide'; }
                            this.sinisterPecService.saveAttachmentsPieceNw(sinPec, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.note, pieceAttFile.original, pieceAttFile.name).subscribe((res: Attachment) => {
                                if (this.oneClickForButton) {
                                    this.oneClickForButton = false;
                                    this.viewSinisterPecService.find(sinPec).subscribe((sinPecView) => {
                                        this.SinisterPecView = sinPecView;
                                        this.principal.identity().then((account) => {
                                            this.currentAccount = account;
                                            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 60;
                                                this.notifUser.source = usr.userId;
                                                this.notifUser.destination = this.SinisterPecView.assignedToId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.settings = JSON.stringify({ 'typenotification': 'imprime', 'assignedTo': this.SinisterPecView.assignedToId, 'sinisterPecId': this.SinisterPecView.id, 'refSinister': this.SinisterPecView.gaReference, 'stepPecId': this.SinisterPecView.step });
                                                this.listNotifUser.push(this.notifUser);
                                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': 'imprime', 'assignedTo': this.SinisterPecView.assignedToId, 'sinisterPecId': this.SinisterPecView.id, 'refSinister': this.SinisterPecView.gaReference, 'stepPecId': this.SinisterPecView.step }));
                                                });
                                            });
                                        });

                                    });
                                }
                                this.alertService.success('auxiliumApp.sinisterPec.createdImprime', null, null);
                            });
                        }
                    });
                    this.selectedItem = true;
                    this.selectedRow = undefined;
                    this.sinPecid = undefined;
                    this.piecesAttachments = [];
                }
                this.router.navigate(['/imprimeReload']);

            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    downloadPieceFile(pieceFileAttachment: File) {
        if (pieceFileAttachment) {
            saveAs(pieceFileAttachment);
        }
    }

    downloadPieceFile1() {
        if (this.pieceFiles1) {
            saveAs(this.pieceFiles1);
        }
    }

    onPieceFileChange1(fileInput: any) {
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.pieceAttachment1.name  = "noExtension" ;

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.pieceAttachment1.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.pieceFiles1 = fileInput.target.files[0];
        this.piecePreview1 = true;
    }



    ajoutPiece1() {
        this.pieceAttachment1.label = this.labelPiece1;
        if (this.pieceAttachment1.original) {
            this.pieceAttachment1.originalFr = 'Oui';
        } else {
            this.pieceAttachment1.originalFr = 'Non';
        }
        if (this.pieceAttachment1.note === null || this.pieceAttachment1.note === undefined) {
            this.pieceAttachment1.note = ' ';
        }
        this.pieceAttachment1.creationDateP = this.dateAsYYYYMMDDHHNNSS(new Date());
        this.pieceAttachment1.pieceFile = this.pieceFiles1;
        this.piecesFiles.push(this.pieceFiles1);
        console.log(this.piecesFiles[0]);
        this.piecesAttachments.push(this.pieceAttachment1);
        this.pieceAttachment1 = new Attachment();
        this.pieceAttachment1.original = true;
        this.labelPiece1 = undefined;
        this.selectedItem = true;
        this.piecePreview1 = false;

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
    /*for (let i = 0; i < this.piecesAttachments.length; i++) {
        if (this.piecesAttachments[i].pieceFile && !this.piecesAttachments[i].id) {
            if (this.piecesAttachments[i].note === null || this.piecesAttachments[i].note == undefined) { this.piecesAttachments[i].note = 'vide'; }
            this.sinisterPecService.saveAttachmentsPieceNw(this.sinPecid, this.piecesAttachments[i].pieceFile, this.piecesAttachments[i].label, this.piecesAttachments[i].note, this.piecesAttachments[i].original, this.pieceAttachment1.name).subscribe((res: Attachment) => {
                if (this.oneClickForButton) {
                    this.oneClickForButton = false;
                    this.viewSinisterPecService.find(this.sinPecid).subscribe((sinPecView) => {
                        this.SinisterPecView = sinPecView;
                        this.principal.identity().then((account) => {
                            this.currentAccount = account;
                            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 60;
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = this.SinisterPecView.assignedToId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': 'imprime', 'assignedTo': this.SinisterPecView.assignedToId, 'sinisterPecId': this.SinisterPecView.id, 'refSinister': this.SinisterPecView.gaReference, 'stepPecId': this.SinisterPecView.step });
                                this.listNotifUser.push(this.notifUser);
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': 'imprime', 'assignedTo': this.SinisterPecView.assignedToId, 'sinisterPecId': this.SinisterPecView.id, 'refSinister': this.SinisterPecView.gaReference, 'stepPecId': this.SinisterPecView.step }));
                                    if (this.piecesAttachments.length - 1 == i) {
                                        this.selectedItem = true;
                                        this.selectedRow = undefined;
                                        this.sinPecid = undefined;
                                        this.piecesAttachments = [];
                                    }
                                });
                            });
                        });

                    });
                } else {
                    if (this.piecesAttachments.length - 1 == i) {
                        this.selectedItem = true;
                        this.selectedRow = undefined;
                        this.sinPecid = undefined;
                        this.piecesAttachments = [];
                    }
                }
                this.alertService.success('auxiliumApp.sinisterPec.createdImprime', null, null);
            });
        }
    }*/

} 
