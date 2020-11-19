import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { DecisionPec, Motifs, EtatMotifs } from '../../constants/app.constants';
import { SinisterPec } from './sinister-pec.model';
import { SinisterService, Sinister } from '../sinister';
import { RefMotifService } from '../ref-motif/ref-motif.service';
import { RefMotif } from '../ref-motif/ref-motif.model';
import { RefModeGestionService } from '../ref-mode-gestion/ref-mode-gestion.service';
import { RefModeGestion } from '../ref-mode-gestion/ref-mode-gestion.model';
import { RaisonPec, RaisonPecService } from '../raison-pec';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogService } from '../../shared';
import { StepService, Step } from '../step';
import { StatusPec, StatusPecService } from '../status-pec';
import { Principal, ResponseWrapper } from '../../shared';
import { StatusSinister, TypeService } from '../../constants/app.constants';
import { TiersService, Tiers } from '../tiers';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { SinisterPecService } from './sinister-pec.service';
import { Attachment } from '../attachments';
import * as Stomp from 'stompjs';
import { UserExtraService, UserExtra } from '../user-extra';
import { UserPartnerMode } from '../user-partner-mode';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { Observation, ObservationService } from '../observation';

@Component({
    selector: 'jhi-decision-sinister-pec',
    templateUrl: './decision-sinister-pec.component.html'
})
export class DecisionSinisterPecComponent implements OnInit, OnDestroy {

    @Input() sinister: Sinister;
    @Input() contratAssurance: ContratAssurance;
    @Input() constatAttachment: Attachment;
    @Input() carteGriseAttachment: Attachment;
    @Input() acteCessionAttachment: Attachment;
    @Input() constatFiles: File;
    @Input() carteGriseFiles: File;
    @Input() acteCessionFiles: File;
    @Input() modeId: any;
    @Input() listModeByCnvByUser: any[];
    @Input() vehicule: VehiculeAssure;
    @Input() updateConstat: boolean;
    @Input() updateCarteGrise: boolean;
    @Input() updateActeDeCession: boolean;
    @Input() piecesAttachments: Attachment[];
    @Input() updatePieceJointe1: boolean;

    decision: any;
    decisions: any[];
    statusPecs: StatusPec[];
    statusPecsDecision: StatusPec[] = [];
    statusPec: StatusPec;
    eventSubscriber: Subscription;
    reasons: RaisonPec[] = [];
    rs: RaisonPec;
    listModeGestion: RefModeGestion[] = [];
    modeGestion: any;
    sinisterPec: SinisterPec;
    motifIdDecision: any;
    isAccepted: boolean = false;
    showMotif: boolean = false;
    showMode: boolean = false;
    oneClickForButton: boolean = true;
    currentAccount: any;
    tiers: Tiers[] = [];
    tier: Tiers = new Tiers();
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    prestAApprouv: boolean = false;
    ws: any;
    UserExtra: UserExtra;
    usersPartnerModes: UserPartnerMode[];
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    observationss: Observation[] = [];
    listNotifUser: NotifAlertUser[] = [];
    userExNotifAgency: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];

    constructor(
        private eventManager: JhiEventManager,
        public activeModal: NgbActiveModal,
        public refMotifService: RefMotifService,
        public refModeGestionService: RefModeGestionService,
        private raisonPecService: RaisonPecService,
        private confirmationDialogService: ConfirmationDialogService,
        private sinisterService: SinisterService,
        private stepService: StepService,
        private statusPecService: StatusPecService,
        private router: Router,
        public principal: Principal,
        private tiersService: TiersService,
        private contratAssuranceService: ContratAssuranceService,
        private prestationPECService: SinisterPecService,
        private userExtraService: UserExtraService,
        private alertService: JhiAlertService,
        private notificationAlerteService: NotifAlertUserService,
        private vehiculeAssureService: VehiculeAssureService,
        private observationService: ObservationService
    ) {
    }


    ngOnInit() {

        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.listModeGestion = this.listModeByCnvByUser;

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((res) => {
                this.UserExtra = res;
                this.usersPartnerModes = this.UserExtra.userPartnerModes;
            });
        });

        this.sinisterPec = this.sinister.sinisterPec;

        this.tiers = this.sinisterPec.tiers;
        this.sinisterPec.tiers = [];

        this.observationss = this.sinisterPec.observations;
        this.sinisterPec.observations = [];

        this.raisonPecService.query().subscribe((subRes: ResponseWrapper) => {
            this.reasons = subRes.json;

        });

        if (this.sinisterPec.isDecidedFromCreateSinister) {
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.sinister.sinisterPec.assignedToId = this.currentAccount.id;
                this.sinister.assignedToId = this.currentAccount.id;
                this.sinister.creationUserId = this.currentAccount.id;

            });
        }


        //this.refModeGestionService.query().subscribe((response) => { this.listModeGestion = response.json });

        if (!this.sinisterPec.rfCmp) {
            this.statusPecService.find(2).subscribe((resStat: StatusPec) => {
                this.statusPec = resStat;
                this.statusPecsDecision.push(this.statusPec);

            });
        } else {
            this.statusPecService.findByCode('PEC').subscribe((resAll) => {
                this.statusPecsDecision = resAll.json;
            });
        }


        if (!this.sinisterPec.isDecidedFromInProgress && !this.sinisterPec.isDecidedFromCreateSinister) {
            this.statusPecService.findByCode('PEC').subscribe((resPecStatuts) => {
                this.statusPecsDecision = resPecStatuts.json;
                if (this.sinisterPec.oldStep == 20) {
                    let listesStatusPec = this.statusPecsDecision;
                    this.statusPecsDecision = [];
                    listesStatusPec.forEach(element => {
                        if (element.id == 3 || element.id == 4) { this.statusPecsDecision.push(element); }
                    });
                }
                switch (this.sinisterPec.decision) {
                    case 'REFUSED':
                        this.sinisterPec.stepDecisionId = 5;
                        this.showMode = false;
                        this.isAccepted = false;
                        this.showMotif = true;
                        break;
                    case 'CANCELED':
                        this.sinisterPec.stepDecisionId = 4;

                        this.showMode = false;
                        this.isAccepted = false;
                        this.showMotif = true;
                        break;
                    case 'ACCEPTED':
                        this.sinisterPec.stepDecisionId = 1;
                        this.isAccepted = true;
                        this.showMode = false;
                        break;
                    case 'ACC_WITH_RESRV':
                        this.sinisterPec.stepDecisionId = 2;
                        this.showMode = false;
                        this.isAccepted = false;
                        this.showMotif = true;
                        break;
                    case 'ACC_WITH_CHANGE_STATUS':
                        this.sinisterPec.stepDecisionId = 3;
                        this.modeGestion = this.sinisterPec.modeModifId;
                        this.showMode = true;
                        this.isAccepted = false;
                        this.showMotif = true;
                        break;
                }
                console.log("testdecison " + this.sinisterPec.motifsDecisionId);
                this.motifIdDecision = this.sinisterPec.motifsDecisionId;
            });
        }


        /*const date = new Date(this.sinister.incidentDate);
                    if (this.sinister.incidentDate) {
                        this.sinister.incidentDate = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    }*/



    }

    savePhotoPlusPec(sinisterPecId: number) {
        this.piecesAttachments.forEach(pieceAttFile => {
            pieceAttFile.creationDate = null;
            if (pieceAttFile.pieceFile !== null && this.updatePieceJointe1 == true && (pieceAttFile.id == null || pieceAttFile.id == undefined)) {
                this.prestationPECService.saveAttachmentsPiecePhotoPlus(sinisterPecId, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.name).subscribe((res: Attachment) => {
                });
            }
        });

    }

    selectDecision(id) {
        this.motifIdDecision = undefined;
        this.modeGestion = undefined;
        // add by Helmi
        if (this.sinisterPec.oldStep == 20) {
            this.reasons = [];
            this.showMotif = true;
            switch (id) {
                case 4:
                    this.showMode = false;
                    this.isAccepted = false;
                    this.raisonPecService.find(95).subscribe((subRes: RaisonPec) => {
                        this.rs = subRes;
                        this.reasons.push(this.rs);
                    });
                    break;
                case 3:
                    this.showMode = true;
                    this.isAccepted = false;
                    break;
            }
        }
        else if (!this.sinisterPec.rfCmp) {
            this.reasons = [];
            this.showMotif = true;
            this.statusPecService.find(id).subscribe((res: StatusPec) => {
                this.statusPec = res;

                switch (this.statusPec.id) {
                    case 2:
                        this.showMode = false;
                        this.isAccepted = false;
                        break;
                }
                this.raisonPecService.find(92).subscribe((subRes: RaisonPec) => {
                    this.rs = subRes;
                    this.reasons.push(this.rs);

                });


            });
        } else {
            this.reasons = [];
            this.showMotif = true;
            this.statusPecService.find(id).subscribe((res: StatusPec) => {
                this.statusPec = res;

                switch (this.statusPec.id) {
                    case 1:
                        this.isAccepted = true;
                        this.showMode = false;
                        this.raisonPecService.findMotifsByStep(id).subscribe((subRes: RaisonPec[]) => {
                            this.reasons = subRes;

                        });
                        break;
                    case 3:
                        this.showMode = true;
                        this.isAccepted = false;
                        break;
                    default:
                        this.showMode = false;
                        this.isAccepted = false;
                        this.raisonPecService.findMotifsByStep(id).subscribe((subRes: RaisonPec[]) => {
                            this.reasons = subRes;

                        });
                }

            });
        }
    }
    selectMode() {
        this.modeId = this.modeGestion;
        this.motifIdDecision = undefined;
        this.reasons = [];

        if (this.sinisterPec.modeId == 1 && (this.modeId == 5 || this.modeId == 6 || this.modeId == 11 || this.modeId == 10)) {
            this.raisonPecService.findMotifsByStatusPecStatusChangeMatrix(1).subscribe((subRes: RaisonPec[]) => {
                this.showMotif = true;
                this.reasons = subRes;
                //this.motifIdDecision = this.reasons[0].id;
            });
        }
        if (this.sinisterPec.modeId == 1 && (this.modeId == 3 || this.modeId == 4)) {
            this.raisonPecService.findMotifsByStatusPecStatusChangeMatrix(2).subscribe((subRes: RaisonPec[]) => {
                this.showMotif = true;
                this.reasons = subRes;
                //this.motifIdDecision = this.reasons[0].id;
            });
        }
        if ((this.sinisterPec.modeId == 5 || this.sinisterPec.modeId == 6 || this.sinisterPec.modeId == 10 || this.sinisterPec.modeId == 11) && this.modeId == 1) {
            this.raisonPecService.findMotifsByStatusPecStatusChangeMatrix(3).subscribe((subRes: RaisonPec[]) => {
                this.showMotif = true;
                this.reasons = subRes;
                //this.motifIdDecision = this.reasons[0].id;
            });
        }

        if (this.modeId == 2 || this.modeId == 7 || this.modeId == 8 || this.modeId == 9) {
            this.showMotif = false;
        }
    }

    updateVehicule() {

        if (this.vehicule.id !== undefined && this.vehicule.id !== null) {
            if (this.vehicule.datePCirculation !== null && this.vehicule.datePCirculation !== undefined) {
                const datePCirculation = new Date(this.vehicule.datePCirculation);
                this.vehicule.datePCirculation = {
                    year: datePCirculation.getFullYear(),
                    month: datePCirculation.getMonth() + 1,
                    day: datePCirculation.getDate()
                };
            }
            this.vehiculeAssureService.update(this.vehicule).subscribe((subRes: VehiculeAssure) => {
            });
        }
    }





    valider() {

        if (this.sinisterPec.isDecidedFromInProgress) {
            if (this.sinisterPec.stepDecisionId == 1) {
                if (this.sinisterPec.regleModeGestion) {
                    console.log("regles des modes de gestion")
                    const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                    this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                    this.sinister.sinisterPec = this.sinisterPec;
                    this.sinister.statusId = StatusSinister.INPROGRESS;
                    this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                    this.sinister.sinisterPec.stepId = 30;
                    this.sinisterPec.prestAApprouv = true;
                    this.activeModal.dismiss('cancel');
                } else {
                    this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
                        console.log('User confirmed:', confirmed);
                        if (confirmed) {
                            const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                            this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                            this.sinister.sinisterPec = this.sinisterPec;
                            this.sinister.sinisterPec.motifsDecisionId = this.motifIdDecision;
                            this.sinister.statusId = StatusSinister.INPROGRESS;
                            this.sinister.sinisterPec.stepId = 30;
                            this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                            if (this.sinister.id !== null && this.sinister.id !== undefined) {
                                this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                    this.sinister = resSinister;
                                    this.updateVehicule();
                                    this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                    this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                    this.sendNotif('notifPrestEnCours', 'Accepté');
                                    this.saveAttachments(this.sinister.sinisterPec.id);
                                    this.savePhotoPlusPec(this.sinister.sinisterPec.id);
                                    this.sinisterPec = this.sinister.sinisterPec;
                                    this.router.navigate(['/pec-being-processed']);
                                    this.alertService.success('auxiliumApp.sinisterPec.decisionValid', null, null);
                                });
                            }

                            this.activeModal.dismiss('cancel');
                        }
                    })
                        .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
                }
            } else {
                this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                        this.sinister.sinisterPec = this.sinisterPec;
                        this.sinister.sinisterPec.stepId = 30;
                        this.sinister.sinisterPec.motifsDecisionId = this.motifIdDecision;
                        this.sinister.statusId = StatusSinister.INPROGRESS;
                        switch (this.sinisterPec.stepDecisionId) {
                            case 4:
                                this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                                break;
                            case 2:
                                this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                                break;
                            case 3:
                                this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                                break;
                            case 5:
                                this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                                break;
                            case 1:
                                this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                                break;
                        }
                        if (this.modeId) {
                            if (this.sinisterPec.isDecidedFromInProgress) {
                                this.sinister.sinisterPec.modeModifId = this.modeId;
                            } else {
                                this.sinister.sinisterPec.modeModifId = this.modeId;
                            }

                        }
                        if (this.sinister.id !== null && this.sinister.id !== undefined) {
                            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                this.updateVehicule();
                                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                let etatApp = '';
                                if (this.sinister.sinisterPec.decision == DecisionPec.CANCELED) { etatApp = 'Annulé' }
                                if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_RESRV) { etatApp = 'Accepté avec réserves' }
                                if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_CHANGE_STATUS) { etatApp = 'Accepté avec changement de statut' }
                                if (this.sinister.sinisterPec.decision == DecisionPec.REFUSED) { etatApp = 'Refusé' }
                                if (this.sinister.sinisterPec.decision == DecisionPec.ACCEPTED) { etatApp = 'Accepté' }
                                this.sendNotif('notifPrestEnCours', etatApp);
                                this.saveAttachments(this.sinister.sinisterPec.id);
                                this.savePhotoPlusPec(this.sinister.sinisterPec.id);
                                this.sinisterPec = this.sinister.sinisterPec;
                                this.router.navigate(['/pec-being-processed']);
                                this.alertService.success('auxiliumApp.sinisterPec.decisionValid', null, null);
                            });
                        }
                        this.activeModal.dismiss('cancel');
                    }
                })
                    .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

            }
        } else if (this.sinisterPec.isDecidedFromCreateSinister) {
            if (this.sinisterPec.stepDecisionId == 1) {
                if (this.sinisterPec.regleModeGestion) {
                    const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                    this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                    this.sinisterPec.generatedLetter = false;
                    this.sinister.sinisterPec = this.sinisterPec;
                    this.sinister.statusId = StatusSinister.INPROGRESS;
                    this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                    this.sinister.sinisterPec.stepId = 30;
                    this.sinisterPec.prestAApprouv = true;
                    this.activeModal.dismiss('cancel');
                } else {

                    this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
                        console.log('User confirmed:', confirmed);
                        if (confirmed) {

                            const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                            this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                            this.sinisterPec.generatedLetter = false;
                            this.sinister.sinisterPec = this.sinisterPec;
                            this.sinister.statusId = StatusSinister.INPROGRESS;
                            this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                            this.sinister.sinisterPec.stepId = 30;
                            if (this.sinister.id !== null && this.sinister.id !== undefined) {
                                this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                    this.sinister = resSinister;
                                    this.sinisterPec = this.sinister.sinisterPec;
                                    this.updateVehicule();
                                    this.sendNotif('notifPrestEnCours', 'Accepté');
                                    this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                    this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                    this.saveAttachments(this.sinister.sinisterPec.id);
                                    this.savePhotoPlusPec(this.sinister.sinisterPec.id);
                                    this.router.navigate(['/sinister']);
                                });
                            } else {
                                this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                                    this.sinister = resSinister;
                                    this.updateVehicule();
                                    this.sendNotif('notifPrestEnCours', 'Accepté');
                                    this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                    this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                    this.saveAttachments(this.sinister.sinisterPec.id);
                                    this.savePhotoPlusPec(this.sinister.sinisterPec.id);
                                    this.sinisterPec = this.sinister.sinisterPec;
                                    this.router.navigate(['/sinister']);
                                });

                            }
                            this.activeModal.dismiss('cancel');

                        }
                    })
                        .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

                }
            } else {
                this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                        this.sinisterPec.generatedLetter = false;
                        this.sinister.sinisterPec = this.sinisterPec;
                        this.sinister.sinisterPec.motifsDecisionId = this.motifIdDecision;
                        this.sinister.statusId = StatusSinister.INPROGRESS;
                        this.statusPecService.find(this.sinisterPec.stepDecisionId).subscribe((res: StatusPec) => {
                            this.statusPec = res;
                            console.log("testStepCode" + this.statusPec.code);

                            switch (this.statusPec.id) {
                                case 4:
                                    this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                                    break;
                                case 2:
                                    this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                                    break;
                                case 3:
                                    this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                                    break;
                                case 5:
                                    this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                                    break;
                                case 1:
                                    this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                                    break;
                            }
                            this.sinister.sinisterPec.stepId = 30;

                            if (this.modeId) {
                                this.sinister.sinisterPec.modeModifId = this.modeId;
                            }

                            let etatApp = '';
                            if (this.sinister.sinisterPec.decision == DecisionPec.CANCELED) { etatApp = 'Annulé' }
                            if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_RESRV) { etatApp = 'Accepté avec réserves' }
                            if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_CHANGE_STATUS) { etatApp = 'Accepté avec changement de statut' }
                            if (this.sinister.sinisterPec.decision == DecisionPec.REFUSED) { etatApp = 'Refusé' }
                            if (this.sinister.sinisterPec.decision == DecisionPec.ACCEPTED) { etatApp = 'Accepté' }
                            if (this.sinister.id !== null && this.sinister.id !== undefined) {
                                this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                    this.sinister = resSinister;
                                    this.sinisterPec = this.sinister.sinisterPec;
                                    this.updateVehicule();
                                    this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                    this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                    this.saveAttachments(this.sinister.sinisterPec.id);
                                    this.savePhotoPlusPec(this.sinister.sinisterPec.id);
                                    this.sendNotif('notifPrestEnCours', etatApp);
                                    this.router.navigate(['/sinister']);
                                });
                            } else {
                                this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                                    this.sinister = resSinister;
                                    this.updateVehicule();
                                    this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                    this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                    this.saveAttachments(this.sinister.sinisterPec.id);
                                    this.savePhotoPlusPec(this.sinister.sinisterPec.id);
                                    this.sendNotif('notifPrestEnCours', etatApp);
                                    this.sinisterPec = this.sinister.sinisterPec;
                                    this.router.navigate(['/sinister']);
                                });

                            }



                            this.activeModal.dismiss('cancel');

                        });


                    }
                })
                    .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
            }

        } else {


            const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
            this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
            this.sinisterPec.generatedLetter = false;
            this.sinister.sinisterPec = this.sinisterPec;
            this.sinister.sinisterPec.motifsDecisionId = this.motifIdDecision;
            this.sinister.statusId = StatusSinister.INPROGRESS;
            this.statusPecService.find(this.sinisterPec.stepDecisionId).subscribe((res: StatusPec) => {
                this.statusPec = res;
                console.log("testStepCode" + this.statusPec.code);

                switch (this.statusPec.id) {
                    case 4:
                        this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                        break;
                    case 2:
                        this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                        break;
                    case 3:
                        this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                        break;
                    case 5:
                        this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                        break;
                    case 1:
                        this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                        break;
                }
                this.sinister.sinisterPec.stepId = 30;

                if (this.modeId) {
                    this.sinisterPec.modeModifId = this.modeId;
                    this.sinister.sinisterPec.modeModifId = this.modeId;
                }


                this.activeModal.close(this.sinisterPec);

            });




        }

    }

    saveTiers(prestationPecId: number, tiers: Tiers[]) {
        // Add tiers
        if (tiers && tiers.length > 0) {
            tiers.forEach(tier => {
                if (tier.deleted && tier.id !== null && tier.id !== undefined) {
                    this.tiersService.delete(tier.id).subscribe(((resultTier) => {
                    }));
                } else if (!tier.deleted && (tier.id === null || tier.id === undefined)) {
                    tier.sinisterPecId = prestationPecId;
                    this.tiersService.create(tier).subscribe(((resultTier) => { }));
                } else if (!tier.deleted && tier.id !== null && tier.id !== undefined) {
                    this.tiersService.update(tier).subscribe(((resultTier) => { }));
                }
            });
        }
    }

    saveAttachments(prestationPecId: number) {
        if (this.constatFiles !== null && this.updateConstat == true) {
            this.prestationPECService.saveAttachmentsNw(prestationPecId, this.constatFiles, this.labelConstat, this.constatAttachment.name).subscribe((res: Attachment) => {
                this.constatAttachment = res;
            });
        }

        if (this.carteGriseFiles !== null && this.updateCarteGrise == true) {
            this.prestationPECService.saveAttachmentsNw(prestationPecId, this.carteGriseFiles, this.labelCarteGrise, this.carteGriseAttachment.name).subscribe((res: Attachment) => {
                this.carteGriseAttachment = res;
            });
        }

        if (this.acteCessionFiles !== null && this.updateActeDeCession == true) {
            this.prestationPECService.saveAttachmentsNw(prestationPecId, this.acteCessionFiles, this.labelActeCession, this.acteCessionAttachment.name).subscribe((res: Attachment) => {
                this.acteCessionAttachment = res;

            });
        }

    }

    saveObservations(prestationId: number, observations: Observation[]) {
        if (observations && observations.length > 0) {
            observations.forEach(observation => {
                if (observation.id === null || observation.id === undefined) {
                    observation.sinisterPecId = prestationId;
                    observation.date = this.formatEnDate(new Date());
                    this.observationService.create(observation).subscribe(((resultObs: Observation) => {
                    }));
                }
            });
        }
    }

    formatEnDate(date) {
        var d = date,
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }

    sendNotif(typeNotif, etatPrestation) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    if (usr.userBossId) {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 13;
                        this.notifUser.destination = usr.userBossId;
                        this.notifUser.source = usr.id;
                        this.notifUser.notification = this.notification;
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                    }
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 13;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json;
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 13;
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 3, usr.id).subscribe((prestToRead) => {
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));

                                });
                            });
                        });
                    });


                });
            });
        }
    }

    clear() {
        if (this.sinisterPec.isDecidedFromInProgress || this.sinisterPec.isDecidedFromCreateSinister) {
            this.sinisterPec.stepId = null;
            this.sinisterPec.stepDecisionId = null;
            this.modeGestion = null;
            this.motifIdDecision = null;
        }
        this.activeModal.dismiss('cancel');
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
        if (this.eventSubscriber !== null && this.eventSubscriber !== undefined) {
            this.eventManager.destroy(this.eventSubscriber);
        }
    }

}
