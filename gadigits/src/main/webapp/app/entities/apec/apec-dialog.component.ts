import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Apec } from './apec.model';
import { ApecPopupService } from './apec-popup.service';
import { ApecService } from './apec.service';
import { ResponseWrapper, Principal } from '../../shared';
import { Sinister } from '../sinister/sinister.model';
import { VehiculeAssure } from '../vehicule-assure/vehicule-assure.model';
import { VehicleUsage } from '../vehicle-usage/vehicle-usage.model';
import { Reparateur } from '../reparateur/reparateur.model';
import { ContratAssurance } from '../contrat-assurance/contrat-assurance.model';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { Governorate } from '../governorate/governorate.model';
import { Delegation } from '../delegation/delegation.model';
import { RefBareme } from '../ref-bareme/ref-bareme.model';
import { SinisterPecService } from '../sinister-pec/sinister-pec.service';
import { SinisterService } from '../sinister/sinister.service';
import { VehiculeAssureService } from '../vehicule-assure/vehicule-assure.service';
import { ContratAssuranceService } from '../contrat-assurance/contrat-assurance.service';
import { RefPack } from '../ref-pack/ref-pack.model';
import { RefPackService } from '../ref-pack/ref-pack.service';
import { TiersService } from '../tiers/tiers.service';
import { Tiers } from '../tiers/tiers.model';
import { RefModeGestionService } from '../ref-mode-gestion/ref-mode-gestion.service';
import { RefModeGestion } from '../ref-mode-gestion/ref-mode-gestion.model';
import { ObservationService } from '../observation/observation.service';
import { Observation, TypeObservation } from '../observation/observation.model';
import { DemandSinisterPecService } from '../sinister-pec/demand-sinister-pec.service';
import { RefBaremeService } from '../ref-bareme/ref-bareme.service';
import { DelegationService } from '../delegation/delegation.service';
import { GovernorateService } from '../governorate/governorate.service';
import { DecisionAccord, EtatAccord, DecisionAssure, DecisionPec, PrestationPecStep, NotificationAccord, TypePiecesDevis, TypeInterventionQuotation } from '../../constants/app.constants';
import { ConfirmationDialogService } from '../../shared';
import { RefPositionGaService } from '../ref-position-ga/ref-position-ga.service';
import { PermissionAccess } from '../user-extra/permission-access.model';
import { UserExtraService } from '../user-extra/user-extra.service';
import * as Stomp from 'stompjs';
import { WebSocketService } from '../web-socket/web-socket.service';
import { RaisonPec } from '../raison-pec/raison-pec.model';
import { RaisonPecService } from '../raison-pec/raison-pec.service';
import { NotificationService } from '../notification/notification.service';
import { JhiNotification } from '../notification/notification.model';
import { NotificationUserProfile } from '../notification/notification-user-profile.model';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { UserExtra } from '../user-extra';
import { VehicleUsageService } from '../vehicle-usage/vehicle-usage.service';
import { AssureService } from '../assure/assure.service';
import { Assure } from '../assure/assure.model';
import { ViewDetailQuotationService } from '../devis/view-detail-quotation.service';
import { ViewDetailQuotation } from '../devis/view-detail-quotation.model';
import { VehiclePieceService } from '../vehicle-piece/vehicle-piece.service';
import { VehiclePiece } from '../vehicle-piece/vehicle-piece.model';
import { DetailsPiecesService } from '../details-pieces/details-pieces.service';
import { DetailsPieces } from '../details-pieces/details-pieces.model';
import { DetailsMo } from '../details-mo/details-mo.model';
import { QuotationService } from '../quotation/quotation.service';
import { Quotation } from '../quotation/quotation.model';
import { RefTypeIntervention } from '../ref-type-intervention/ref-type-intervention.model';
import { RefTypeInterventionService } from '../ref-type-intervention/ref-type-intervention.service';
import { VatRate } from '../vat-rate/vat-rate.model';
import { VatRateService } from '../vat-rate/vat-rate.service';
import { SinisterPecPopupService } from '../sinister-pec/sinister-pec-popup.service';
import { HistoryPopupDetailsPec } from '../sinister-pec/historyPopupDetailsPec.component';
import { PieceJointe } from '../piece-jointe/piece-jointe.model';
import { Attachment } from '../attachments/attachment.model';
import { saveAs } from 'file-saver/FileSaver';
import { ObservationApec } from '../observationApec/observation-apec.model';
import { ObservationApecService } from '../observationApec/observation-apec.service';

@Component({
    selector: 'jhi-apec-dialog',
    templateUrl: './apec-dialog.component.html'
})
export class ApecDialogComponent implements OnInit {

    apec: Apec = new Apec();
    apecInBase: Apec = new Apec();
    isSaving: boolean;
    piecesAttachments: Attachment[] = [];
    dateGenerationDp: any;
    dossier: Sinister = new Sinister();
    vehicule: VehiculeAssure = new VehiculeAssure();
    refusagecontrats: VehicleUsage[] = [];
    reparateur: Reparateur = new Reparateur();
    contratAssurance: ContratAssurance = new ContratAssurance();
    sinisterPec: SinisterPec = new SinisterPec();
    myDate: any;
    sinister: Sinister = new Sinister();
    searchActive: boolean = false;
    listModeGestionByPack: any[] = [];
    isContractLoaded = false;
    garantieDommageVehicule = false;
    garantieDommageCollision = false;
    garantieVolIncendiePartiel = false;
    garantieBrisGlace = false;
    garantieTierceCollision = false;
    garantieTierce = false;
    governorates: Governorate[];
    sysvillesRep: Delegation[];
    CasBareme = new RefBareme();
    refBarShow: boolean = false;
    isDevisComplementaire = false;
    currentAccount: any;
    pack: RefPack = new RefPack();
    creation: any;
    cloture: any;
    miseAjour: any;
    gov: boolean = true;
    govRep: boolean = true;
    casDeBareme = false;
    tiers: Tiers[] = [];
    debut2: any;
    fin2: any;
    modeChoisi: RefModeGestion = new RefModeGestion;
    observations: Observation[] = [];
    observationss: Observation[] = [];
    observationAp = new Observation();
    minVinNumber;
    VinNumber;
    vehNmbrIda: boolean = false;
    showFrmTiers: boolean = false;
    tiersLength: boolean = true;
    refBar: boolean = false;
    showAlertTiers: boolean = false;
    sysVille: Delegation;
    sysGouvernoratRep: Governorate;
    sysvilles: Delegation[];
    clear: any;
    trimRegistrationNumber: any;
    search: any;
    AvisExpert: any;
    expert: any;
    consultation: any;
    ajoutContrat: any;
    decisionSiege: any;
    decisionAssure: any;
    observation: boolean = false;
    autreObservation: boolean = false;
    instanceParticipation: boolean = false;
    modeEdit: boolean = false;
    modeValid: boolean = false;
    modeApprouv: boolean = false;
    modeValidPart: boolean = false;
    autreObservationAssure: boolean = false;
    modeImprimer: boolean = false;
    //testRefernceSinister
    //changeMode
    oneClickForButton: boolean = true;
    oneClickForButton1: boolean = true;
    oneClickForButton2: boolean = true;
    oneClickForButton3: boolean = true;
    oneClickForButton4: boolean = true;
    oneClickForButton5: boolean = true;
    oneClickForButton6: boolean = true;
    oneClickForButton7: boolean = true;

    oneClickForButtonEtat: boolean = true;
    oneClickForButtonDecAssure: boolean = true;
    isApprouved: boolean = false;
    showModeToModif: boolean = false;
    isAcceptedWithChangeStatus: boolean = false;
    listPositionGa: any[] = [];
    permissionToAccess: PermissionAccess = new PermissionAccess();
    participationGA: any;
    ws: any;
    userExNotif: UserExtra[] = [];
    userNotifAgency: UserExtra[] = [];
    motifsApec: RaisonPec[];
    motifsDefavApec: RaisonPec[];
    motifsFavoAvecReservevApec: RaisonPec[];
    notification: RefNotifAlert;
    notifUser: NotifAlertUser;
    notifUserUpdate: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    userExtra: UserExtra;
    assignTo: UserExtra;
    userReparateurPerson: UserExtra[] = [];
    userExpertPerson: UserExtra[] = [];
    assure: any;
    receiveVehicule = false;
    detailQuotation: ViewDetailQuotation = new ViewDetailQuotation();
    moPieces: VehiclePiece[] = [];
    ingredientPieces: VehiclePiece[] = [];
    fourniturePieces: VehiclePiece[] = [];
    detailsPiecesMO: DetailsPieces[] = [];
    detailsPiecesIngredient: DetailsPieces[] = [];
    detailsPieces: DetailsPieces[] = [];
    detailsPiecesFourniture: DetailsPieces[] = [];
    lastDetailsPieces: DetailsPieces[] = [];
    listPieces: DetailsPieces[] = [];
    detailsMos: DetailsMo[] = [];
    quotation: Quotation = new Quotation();
    refOperationTypesMo: RefTypeIntervention[];
    refOperationTypesPeinture: RefTypeIntervention[];
    refOperationTypesFourniture: RefTypeIntervention[];
    listVat: VatRate[];
    totalTtc = 0;
    moTotalTtc = 0;
    apecStatut: number;
    ingTotalTtc = 0;
    fournTotalTtc = 0;
    piecesTotalTtc = 0;
    private ngbModalRef: NgbModalRef;
    faceAvantDroitPreview = false;
    faceAvantGauchePreview = false;
    faceArriereDroitPreview = false;
    faceArriereGauchePreview = false;
    extensionImage: string;
    finitionPreview = false;
    immatriculationPreview = false;
    compteurPreview = false;
    nSeriePreview = false;
    isCommentError = false;
    carteGrisePreview = false;
    showObservationAssure = false;
    isNotValid: boolean = true;
    isNotApprouv: boolean = true;
    isNotValidPart: boolean = true;
    userExNotifAgency: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];
    userExNotifRep: UserExtra[] = [];
    buttonImprime: boolean = true;
    gtShow = false;
    labelAccordNormal: String = "AccordNormal";
    buttonApprouvApec: boolean = false;
    buttonParticipAss: boolean = true;
    buttonValid: boolean = false;
    buttonModifApec: boolean = true;
    observationApec: ObservationApec = new ObservationApec();
    apecId: any;

    decisions = [
        { name: 'Favorable', value: '1', checked: false },
        { name: 'Défavorable', value: '2', checked: false },
        { name: 'Favorable avec modification', value: '4', checked: false },
        { name: 'Favorable avec réserve', value: '5', checked: false }
    ];
    decisionsAssure = [
        { name: 'Accepter', value: '1', checked: false },
        { name: 'Accepter avec pièce générique', value: '2', checked: false },
        { name: 'Refuser', value: '3', checked: false },
        { name: 'En instance de confirmation participation', value: '4', checked: false }
    ];

    observationSiege = [
        { label: 'CHANGEMENT_RP' },
        { label: 'CHANGEMENT_TVA' },
        { label: 'AUTRE' }
    ];

    constructor(
        //public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private apecService: ApecService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private sinisterPecService: SinisterPecService,
        private dossierService: SinisterService,
        private vehiculeAssureService: VehiculeAssureService,
        private refPackService: RefPackService,
        private refBaremeService: RefBaremeService,
        private sysVilleService: DelegationService,
        private sysGouvernoratService: GovernorateService,
        private delegationService: DelegationService,
        private confirmationDialogService: ConfirmationDialogService,
        private refPositionGaService: RefPositionGaService,
        private userExtraService: UserExtraService,
        private notificationAlerteService: NotifAlertUserService,
        private raisonPecService: RaisonPecService,
        private vehicleUsageService: VehicleUsageService,
        private assureService: AssureService,
        private viewDetailQuotationService: ViewDetailQuotationService,
        private vehiclePieceService: VehiclePieceService,
        private detailsPiecesService: DetailsPiecesService,
        private quotationService: QuotationService,
        private refTypeInterventionService: RefTypeInterventionService,
        private vatRateService: VatRateService,
        private sinisterPecPopupService: SinisterPecPopupService,
        private observationApecService: ObservationApecService,
        private router: Router,
        public principal: Principal,
        private observationService: ObservationService
    ) {
    }

    ngOnInit() {

        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.observation = false;
        this.autreObservation = false;
        this.modeEdit = false;
        this.modeValid = false;
        this.modeApprouv = false;
        this.modeValidPart = false;
        this.modeImprimer = false;
        this.instanceParticipation = false;
        this.autreObservationAssure = false;

        this.notification = new RefNotifAlert();
        this.isSaving = false;

        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.MAIN_OEUVRE).subscribe((res) => {
            this.moPieces = res;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.INGREDIENT_FOURNITURE).subscribe((res) => { this.ingredientPieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.PIECES_FOURNITURE).subscribe((res) => { this.fourniturePieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_MO).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesMo = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_PEINTURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesPeinture = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_FOURNITURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesFourniture = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.vatRateService.query().subscribe((res) => {
            this.listVat = res.json;
        });
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(55, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });

            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((res) => {
                this.userExtra = res;
            });
        });

        this.refPositionGaService.query().subscribe((res: ResponseWrapper) => {
            this.listPositionGa = res.json;
        });
        this.raisonPecService.findMotifsByStep(8).subscribe((res: RaisonPec[]) => {
            this.motifsApec = res;
        });
        this.raisonPecService.findMotifsByStep(9).subscribe((res: RaisonPec[]) => {
            this.motifsDefavApec = res;
        });
        this.raisonPecService.findMotifsByStep(10).subscribe((res: RaisonPec[]) => {
            this.motifsFavoAvecReservevApec = res;
        });
        this.vehicleUsageService.query().subscribe((res: ResponseWrapper) => {
            this.refusagecontrats = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));

        this.sysGouvernoratService.query()
            .subscribe((res: ResponseWrapper) => {
                this.governorates = res.json;
                this.sysGouvernoratRep = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));

        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => {
                this.sysvilles = res.json;
                this.sysvillesRep = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.sinisterPec.gouvernorat == null) {
            this.sinisterPec.gouvernorat = new Governorate()
        }
        this.route.params.subscribe((params) => {
            if (params['id']) {
                this.apecId = params['id'];

                this.apecService.find(this.apecId).subscribe((res) => {
                    this.apec = res;
                    this.apecStatut = this.apec.etat;
                    this.apec.participationGa = + (this.apec.participationGa).toFixed(3);
                    this.apec.droitDeTimbre = + (this.apec.droitDeTimbre).toFixed(3);
                    this.apec.participationAssure = + (this.apec.participationAssure).toFixed(3);
                    this.apec.participationVetuste = + (this.apec.participationVetuste).toFixed(3);
                    this.apec.participationTva = + (this.apec.participationTva).toFixed(3);
                    this.apec.participationRpc = + (this.apec.participationRpc).toFixed(3);
                    this.apec.fraisDossier = + (this.apec.fraisDossier).toFixed(3);
                    this.apec.avanceFacture = + (this.apec.avanceFacture).toFixed(3);
                    this.apec.depacementPlafond = + (this.apec.depacementPlafond).toFixed(3);
                    this.apec.estimationFranchise = + (this.apec.estimationFranchise).toFixed(3);
                    this.apec.regleProportionnel = + (this.apec.regleProportionnel).toFixed(3);
                    console.log(this.apec);
                    this.observationApecService.findByApec(this.apec.id).subscribe((resObservationApec) => {
                        this.observationApec = resObservationApec;
                        if (this.observationApec.observationAssure !== null) {
                            this.showObservationAssure = true;
                        } else {
                            this.showObservationAssure = false;
                        }
                        if (this.observationApec.decriptionObservationAssure !== null) {
                            this.autreObservationAssure = true;
                        }
                        if (this.observationApec.observationAssure == "AUTRE") {
                            this.autreObservation = true;
                        }
                    });


                    this.apecInBase = Object.assign({}, this.apec);
                    console.log(this.apec.sinisterPec.stepId + "  " + this.apec.etat);

                    if (this.apec.sinisterPec.stepId !== 103 && this.apec.etat !== 3) { this.buttonImprime = false; }
                    if (this.apec.sinisterPec.stepId === 104 && this.apec.etat === 4) { this.buttonModifApec = true; }
                    if (this.apec.sinisterPec.stepId === 106 && this.apec.etat === 6) { this.buttonValid = true; }
                    if (this.apec.sinisterPec.stepId === 100 && this.apec.etat === 0) { this.buttonApprouvApec = true; }
                    if (this.apec.sinisterPec.stepId !== 107 && this.apec.etat !== 7) { this.buttonParticipAss = false; }
                    if (this.apec.decision == DecisionAccord.ACC_FAVORABLE_AVEC_MODIF) {
                        //this.observation = true;
                    }

                    this.viewDetailQuotationService.getDetailDevisByPec(this.apec.sinisterPecId).subscribe((res) => {
                        this.detailQuotation = res;
                        this.assure = this.detailQuotation.fullName;
                        this.vehicule.immatriculationVehicule = this.detailQuotation.immatriculationVehicule;
                        this.vehicule.usageId = this.detailQuotation.usageId;
                        this.vehicule.numeroChassis = this.detailQuotation.numeroChassis;
                        this.vehicule.puissance = this.detailQuotation.puissance;
                        this.vehicule.marqueLibelle = this.detailQuotation.marqueLibelle;
                        this.vehicule.modeleLibelle = this.detailQuotation.modeleLibelle;
                        this.reparateur.raisonSociale = this.detailQuotation.raisonSociale;
                        this.contratAssurance.marketValue = this.detailQuotation.marketValue;
                        this.contratAssurance.newValueVehicle = this.detailQuotation.newValueVehicle;
                        this.sinisterPec.governorateId = this.detailQuotation.governorateId;
                        this.sinisterPec.delegationId = this.detailQuotation.delegationId;
                        this.sinisterPec.receptionVehicule = this.detailQuotation.receptionVehicule;
                        this.sinisterPec.vehicleReceiptDate = this.detailQuotation.vehicleReceiptDate;
                        this.sinisterPec.lightShock = this.detailQuotation.lightShock;
                        this.sinisterPec.disassemblyRequest = this.detailQuotation.disassemblyRequest;
                        //this.sinisterPec.companyReference = this.detailQuotation.companyReference;
                        this.sinisterPec.modeId = this.detailQuotation.modeId;
                        this.sinisterPec.vehicleNumber = this.detailQuotation.vehicleNumber;
                        this.sinisterPec.posGaId = this.detailQuotation.posGaId;
                        if (this.detailQuotation.vehicleReceiptDate && !this.detailQuotation.receptionVehicule) {
                            const date = new Date(this.detailQuotation.vehicleReceiptDate);
                            this.detailQuotation.vehicleReceiptDate = {
                                year: date.getFullYear(),
                                month: date.getMonth() + 1,
                                day: date.getDate()
                            };
                        }
                        else { this.myDate = this.detailQuotation.vehicleReceiptDate; }

                        if (this.detailQuotation.packid) {
                            this.refPackService.find(this.detailQuotation.packid).subscribe((res: RefPack) => {
                                this.pack = res;
                                this.listModeGestionByPack = this.pack.modeGestions;
                            }
                            );
                        }

                    });
                    this.sinisterPecService.findPrestationPec(this.apec.sinisterPecId).subscribe((res) => {
                        this.sinisterPec = res;
                        if(this.sinisterPec.id !== null && this.sinisterPec.id !== undefined){
                            if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                                this.isDevisComplementaire = false;
                            } else {
                                this.isDevisComplementaire = true;
                            }
                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            this.changeModeGestion(this.sinisterPec.modeId);
                            this.verifTiers();
                            this.userExtraService.finPersonneByUser(this.sinisterPec.assignedToId).subscribe((usr: UserExtra) => {
                                this.assignTo = usr;
                            });
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.reparateurId, 28).subscribe((usr: ResponseWrapper) => {
                                this.userReparateurPerson = usr.json;
                            });
                            if (this.sinisterPec.expertId !== null && this.sinisterPec.expertId != undefined) {
                                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.expertId, 27).subscribe((usr: ResponseWrapper) => {
                                    this.userExpertPerson = usr.json;
                                });
                            }
    
                            this.dossierService.find(this.sinisterPec.sinisterId).subscribe((res) => {
                                this.dossier = res;
                                this.sinister = res;
                                const date = new Date(this.sinister.incidentDate);
                                if (this.sinister.incidentDate) {
                                    this.sinister.incidentDate = {
                                        year: date.getFullYear(),
                                        month: date.getMonth() + 1,
                                        day: date.getDate()
                                    };
                                }
                                this.creation = this.principal.parseDate(this.sinister.creationDate);
                                this.cloture = this.principal.parseDate(this.sinister.closureDate);
                                this.miseAjour = this.principal.parseDate(this.sinister.updateDate);
                                this.vehiculeAssureService.find(this.dossier.vehicleId).subscribe((res) => {
                                    this.vehicule = res;
                                    this.assureService.find(this.vehicule.insuredId).subscribe((assureRes: Assure) => {
                                        this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                    });
    
                                    if (this.sinisterPec.vehicleReceiptDate) { this.myDate = this.sinisterPec.vehicleReceiptDate; }
    
                                });
                            });
    
                            this.getDevis(this.apec.quotationId);
    
                            this.observationService.findByPrestation(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                                this.observationss = subRes.json;
                                if (this.observationss.length == 0) {
                                    this.observationss = [];
                                }
                            });
                        }else {
                            this.router.navigate(["/accessdenied"]);
                        }
                        

                    });
                });

                if (params['modif'] == "modif") {
                    this.sinisterPec.oldStepNw = 104;
                    this.modeEdit = true;
                    this.isNotApprouv = false;
                    this.isNotValid = false;
                } else {
                    this.modeEdit = false;
                }
                if (params['modif'] == "valid") {
                    this.modeValid = true;
                } else {
                    this.modeValid = false;
                }
                if (params['modif'] == "approuv") {
                    this.modeApprouv = true;
                } else {
                    this.modeApprouv = false;
                }
                if (params['modif'] == "valid-part") {
                    this.modeValidPart = true;
                } else {
                    this.modeValidPart = false;
                }
                if (params['modif'] == "imprimer") {
                    this.modeImprimer = true;
                } else {
                    this.modeImprimer = false;
                }
                console.log("test1234567 " + this.apec.observationAssure);
            }
        });
    }

    //getDevis
    getDevis(quotationId) {
        this.quotationService.find(quotationId).subscribe((res) => {  // Find devid By ID
            this.quotation = res;
            this.loadAllDetailsMo();
            this.loadAllIngredient();
            this.loadAllRechange();
            this.loadAllFourniture();
        })
    }
    /** get attachments pec */


    getPhotoReparation() {

        this.sinisterPecService.getPhotoReparationAttachments(this.sinisterPec.id).subscribe((resImprime) => {
            this.piecesAttachments = resImprime.json;
        });


    }

    getPieceForAttachment(entityName: string, attachmentId: number, originalName: string) {
        this.sinisterPecService.getPieceByAttachmentIdAndEntityName(entityName, attachmentId).subscribe((blob: Blob) => {
            let fileName = originalName;
            console.log(fileName);

            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, fileName);
            } else {
                var a = document.createElement('a');
                a.href = URL.createObjectURL(blob);
                a.download = fileName;
                a.target = '_blank';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
        },
            err => {
                alert("Error while downloading. File Not Found on the Server");
            });
    }

    loadAllDetailsMo() {
        console.log("quotationid-*-*" + this.quotation.id);
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, true).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesMO = subRes.json;


            this.calculateGlobalMoTtc();
            this.getSum4();
        })
    }
    loadAllIngredient() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.INGREDIENT_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesIngredient = subRes.json;
            this.calculateGlobalIngTtc();
        })
    }
    loadAllRechange() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, false).subscribe((subRes: ResponseWrapper) => {
            this.detailsPieces = subRes.json;
            this.calculateGlobalPiecesTtc();
        })
    }
    loadAllFourniture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.PIECES_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesFourniture = subRes.json;
            this.calculateGlobalFournTtc();
        });
    }
    /**
      * Calculate the total Ttc in quotation where fourn modified
      */
    calculateGlobalFournTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty ? this.quotation.stampDuty : 0;
        this.fournTotalTtc = 0;
        if (this.detailsPiecesFourniture && this.detailsPiecesFourniture.length > 0) {
            for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
                if (this.detailsPiecesFourniture[i].isModified != true) {
                    this.fournTotalTtc += this.detailsPiecesFourniture[i].totalTtc;
                }
            }
        }
        this.fournTotalTtc = +(this.fournTotalTtc).toFixed(3);
        this.quotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
        this.quotation.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
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

    addObservation() {
        console.log('Add Observation');
        if (this.observationAp.commentaire === null || this.observationAp.commentaire === undefined) {
            this.isCommentError = true;
        } else {
            this.observationAp.type = TypeObservation.Observation;
            this.observationAp.userId = this.principal.getAccountId();
            this.observationAp.firstName = this.principal.getCurrentAccount().firstName;
            this.observationAp.lastName = this.principal.getCurrentAccount().lastName;
            this.observationAp.date = new Date();
            this.observationAp.sinisterPecId = this.sinisterPec.id;
            this.observationAp.prive = false;
            this.observationss.push(this.observationAp);
            this.observationAp.date = this.formatEnDate(new Date());
            this.observationService.create(this.observationAp).subscribe((res) => {
            });
            this.observationAp = new Observation();
        }
    }

    /**
     * Calculate the total Ttc in quotation where pieces modified
     */
    calculateGlobalPiecesTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty ? this.quotation.stampDuty : 0;
        this.piecesTotalTtc = 0;
        if (this.detailsPieces && this.detailsPieces.length > 0) {
            for (let i = 0; i < this.detailsPieces.length; i++) {
                //if (this.detailsPieces[i].modified) {
                this.piecesTotalTtc += this.detailsPieces[i].totalTtc;
                //}
            }
        }
        this.piecesTotalTtc = +(this.piecesTotalTtc).toFixed(3);
        this.quotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
        this.quotation.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }
    getSum4(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMos.length; i++) {
            if (this.detailsMos[i].typeInterventionId && this.detailsMos[i].nombreHeures) {
                sum += this.detailsMos[i].totalHt;
            }
        }
        return sum;
    }
    /**
     * Calculate the total Ttc in quotation where mo modified
     */
    calculateGlobalMoTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty ? this.quotation.stampDuty : 0;
        this.moTotalTtc = 0;
        if (this.detailsPiecesMO && this.detailsPiecesMO.length > 0) {
            for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                this.moTotalTtc += this.detailsPiecesMO[i].totalTtc;
            }
        }
        this.moTotalTtc = +(this.moTotalTtc).toFixed(3);
        this.quotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
        this.quotation.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }
    /**
       * Calculate the total Ttc in quotation where ing modified
       */
    calculateGlobalIngTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty ? this.quotation.stampDuty : 0;
        this.ingTotalTtc = 0;
        if (this.detailsPiecesIngredient && this.detailsPiecesIngredient.length > 0) {
            for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
                //if (this.detailsPiecesIngredient[i].modified != true) {
                this.ingTotalTtc += this.detailsPiecesIngredient[i].totalTtc;
                //}
            }
        }
        this.ingTotalTtc = +(this.ingTotalTtc).toFixed(3);
        this.quotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
        this.quotation.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }


    // Traitement Siége
    onClickedDecisionSiege(decision3, event) {
        console.log(event.target.value);
        //console.log(decision.name);
        this.decisionSiege = event.target.value;
        this.observationApec.decisionApprobationCompagnie = decision3;
        if (this.decisionSiege == DecisionAccord.ACC_FAVORABLE_AVEC_MODIF) {
            this.apec.etat = EtatAccord.ACC_FAVORABLE_AVEC_MODIF;
            this.observation = true;
        } else {

            this.observation = false;
        }
        if (this.decisionSiege == DecisionAccord.ACC_FAVORABLE) {
            this.apec.etat = EtatAccord.ACC_A_VALIDER;
        }
        if (this.decisionSiege == DecisionAccord.ACC_FAVORABLE_AVEC_RESERV) {
            this.apec.etat = EtatAccord.ACC_A_APPROUV;
        }
        if (this.decisionSiege == DecisionAccord.ACC_DEFAVORABLE) {
            this.apec.etat = EtatAccord.ACC_DEFAVORABLE;
        }
        this.isNotApprouv = false;
        this.isNotValid = false;
    }
    selectObservation(observationSiege: any) {
        if (observationSiege == "AUTRE") {
            this.autreObservation = true;
        } else {
            this.autreObservation = false;
        }
    }
    // Traitement Assure
    onClickedDecisionAssure(decision1, event) {
        this.decisionAssure = event.target.value;
        this.observationApec.decisionValidationPartAssure = decision1;
        if (this.decisionAssure == DecisionAssure.ACC_INSTANCE_CONFIRMATION) {
            this.instanceParticipation = true;
        } else {

            this.instanceParticipation = false;
        }
        this.isNotValidPart = false;

    }
    onValidAccord(decision2, event) {
        this.observationApec.decisionValidationApec = decision2;
        if (event.target.value == 8) {
            this.isNotValid = false;
            this.isNotApprouv = false;
        }
        if (event.target.value == 7) {
            this.isNotValid = false;
            this.isNotApprouv = false;
        }
    }
    selectObservationAssure(observation: any) {

        if (observation == "AUTRE") {
            this.autreObservationAssure = true;
        } else {
            this.autreObservationAssure = false;
        }

        if (observation == "AUTRE") {
            this.autreObservation = true;
        } else {
            this.autreObservation = false;
        }
    }


    changeModeGestion(value) {
        console.log(value);

        //this.sinisterPec.vehicleNumber = null;
        this.verifTiers();
        this.garantieDommageVehicule = value === 6 ? true : false;
        this.garantieDommageCollision = value === 5 ? true : false;
        this.garantieVolIncendiePartiel = (value === 8 || value === 9) ? true : false;
        this.garantieBrisGlace = value === 7 ? true : false;
        this.garantieTierceCollision = value === 11 ? true : false;
        this.garantieTierce = value === 10 ? true : false;
        if (value === 1 || value === 2) {
            this.sinisterPec.vehicleNumber = 2;
            this.minVinNumber = 2;
            this.vehNmbrIda = true;
            this.verifTiers();
        }
        else if (value === 3 || value === 4) {
            this.vehNmbrIda = false;
            this.minVinNumber = 2;
            this.verifTiers();
        } else {
            this.vehNmbrIda = false;
            this.minVinNumber = 1;
            this.verifTiers();
        }

    }

    verifTiers() {
        this.showFrmTiers = false;
        if (this.sinisterPec.vehicleNumber !== null && this.sinisterPec.vehicleNumber !== undefined) {
            if (this.sinisterPec.vehicleNumber > 1) {
                this.tiersLength = ((this.tiers.length < this.sinisterPec.vehicleNumber) && this.tiers.length > 0) ? true : false;
                if (!this.CasBareme.id) {
                    this.refBar = false;
                }
                if ((this.tiers.length < this.sinisterPec.vehicleNumber)) {
                    this.showAlertTiers = false;
                }
            }
            else if (this.sinisterPec.vehicleNumber === 1) {
                this.tiersLength = true;
                this.refBar = true;
            }
        } else {
            this.tiersLength = true;
            this.showFrmTiers = false;
        }

    }
    LoadBareme(id: number) {
        if (id) {
            this.refBaremeService.find(id).subscribe((baremeRes) => {
                if (baremeRes) {
                    this.CasBareme = baremeRes;
                    this.refBar = true;
                    this.refBarShow = true;
                }
            });
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
    leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
            + String(val)).slice(String(val).length);
    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: Delegation) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.governorateId).subscribe((subRes: Governorate) => {
                this.sinister.gouvernorat = subRes
            })
        }
        )
    }


    /*clear() {
        this.activeModal.dismiss('cancel');
    }*/

    activeRecherche() {
        if (this.sinister.vehicleRegistration !== null && this.sinister.vehicleRegistration !== undefined && this.sinister.vehicleRegistration !== '') {
            this.searchActive = true;
        } else {
            this.searchActive = false;
        }
    }


    save() {
        this.isSaving = false;
        if (this.apec.id !== undefined) {
            this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer l\'accord ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        if (this.oneClickForButton1) {
                            this.oneClickForButton1 = false;

                            console.log("apec for edit------" + this.apec.id);
                            this.route.params.subscribe((params) => {
                                if (params['modif'] == "modif") {
                                    this.apec.testStep = true;
                                    this.observationApec.decriptionObservationAssure = null;
                                    this.observationApec.observationAssure = null;
                                    console.log("apec for favorable avec modification------" + this.apec.id);
                                    if (this.apec.droitDeTimbre > this.apecInBase.droitDeTimbre) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.droitDeTimbre - this.apecInBase.droitDeTimbre);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.droitDeTimbre - this.apecInBase.droitDeTimbre);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.droitDeTimbre - this.apecInBase.droitDeTimbre);
                                    }
                                    if (this.apec.droitDeTimbre < this.apecInBase.droitDeTimbre) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.droitDeTimbre + this.apecInBase.droitDeTimbre);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.droitDeTimbre + this.apecInBase.droitDeTimbre);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.droitDeTimbre + this.apecInBase.droitDeTimbre);
                                    }

                                    /*if(this.apec.participationAssure > this.apecInBase.participationAssure){
                                       this.apec.participationGa = this.apec.participationGa - (this.apec.participationAssure - this.apecInBase.participationAssure);
                                    }
                                    if(this.apec.participationAssure < this.apecInBase.participationAssure){
                                       this.apec.participationGa = this.apec.participationGa + (- this.apec.participationAssure + this.apecInBase.participationAssure);
                                    }*/

                                    if (this.apec.participationVetuste > this.apecInBase.participationVetuste) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.participationVetuste - this.apecInBase.participationVetuste);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.participationVetuste - this.apecInBase.participationVetuste);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.participationVetuste - this.apecInBase.participationVetuste);
                                    }
                                    if (this.apec.participationVetuste < this.apecInBase.participationVetuste) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.participationVetuste + this.apecInBase.participationVetuste);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.participationVetuste + this.apecInBase.participationVetuste);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.participationVetuste + this.apecInBase.participationVetuste);
                                    }

                                    if (this.apec.participationTva > this.apecInBase.participationTva) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.participationTva - this.apecInBase.participationTva);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.participationTva - this.apecInBase.participationTva);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.participationTva - this.apecInBase.participationTva);
                                    }
                                    if (this.apec.participationTva < this.apecInBase.participationTva) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.participationTva + this.apecInBase.participationTva);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.participationTva + this.apecInBase.participationTva);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.participationTva + this.apecInBase.participationTva);
                                    }

                                    if (this.apec.participationRpc > this.apecInBase.participationRpc) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.participationRpc - this.apecInBase.participationRpc);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.participationRpc - this.apecInBase.participationRpc);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.participationRpc - this.apecInBase.participationRpc);
                                    }
                                    if (this.apec.participationRpc < this.apecInBase.participationRpc) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.participationRpc + this.apecInBase.participationRpc);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.participationRpc + this.apecInBase.participationRpc);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.participationRpc + this.apecInBase.participationRpc);
                                    }

                                    if (this.apec.fraisDossier > this.apecInBase.fraisDossier) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.fraisDossier - this.apecInBase.fraisDossier);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.fraisDossier - this.apecInBase.fraisDossier);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.fraisDossier - this.apecInBase.fraisDossier);
                                    }
                                    if (this.apec.fraisDossier < this.apecInBase.fraisDossier) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.fraisDossier + this.apecInBase.fraisDossier);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.fraisDossier + this.apecInBase.fraisDossier);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.fraisDossier + this.apecInBase.fraisDossier);
                                    }

                                    if (this.apec.avanceFacture > this.apecInBase.avanceFacture) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.avanceFacture - this.apecInBase.avanceFacture);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.avanceFacture - this.apecInBase.avanceFacture);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.avanceFacture - this.apecInBase.avanceFacture);
                                    }
                                    if (this.apec.avanceFacture < this.apecInBase.avanceFacture) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.avanceFacture + this.apecInBase.avanceFacture);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.avanceFacture + this.apecInBase.avanceFacture);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.avanceFacture + this.apecInBase.avanceFacture);
                                    }

                                    if (this.apec.depacementPlafond > this.apecInBase.depacementPlafond) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.depacementPlafond - this.apecInBase.depacementPlafond);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.depacementPlafond - this.apecInBase.depacementPlafond);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.depacementPlafond - this.apecInBase.depacementPlafond);
                                    }
                                    if (this.apec.depacementPlafond < this.apecInBase.depacementPlafond) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.depacementPlafond + this.apecInBase.depacementPlafond);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.depacementPlafond + this.apecInBase.depacementPlafond);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.depacementPlafond + this.apecInBase.depacementPlafond);
                                    }

                                    if (this.apec.estimationFranchise > this.apecInBase.estimationFranchise) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.estimationFranchise - this.apecInBase.estimationFranchise);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.estimationFranchise - this.apecInBase.estimationFranchise);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.estimationFranchise - this.apecInBase.estimationFranchise);
                                    }
                                    if (this.apec.estimationFranchise < this.apecInBase.estimationFranchise) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.estimationFranchise + this.apecInBase.estimationFranchise);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.estimationFranchise + this.apecInBase.estimationFranchise);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.estimationFranchise + this.apecInBase.estimationFranchise);
                                    }

                                    if (this.apec.regleProportionnel > this.apecInBase.regleProportionnel) {
                                        this.apec.participationGa = this.apec.participationGa - (this.apec.regleProportionnel - this.apecInBase.regleProportionnel);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur - (this.apec.regleProportionnel - this.apecInBase.estimationFranchise);
                                        this.apec.participationAssure = this.apec.participationAssure + (this.apec.regleProportionnel - this.apecInBase.regleProportionnel);
                                    }
                                    if (this.apec.regleProportionnel < this.apecInBase.regleProportionnel) {
                                        this.apec.participationGa = this.apec.participationGa + (- this.apec.regleProportionnel + this.apecInBase.regleProportionnel);
                                        this.apec.soldeReparateur = this.apec.soldeReparateur + (- this.apec.regleProportionnel + this.apecInBase.regleProportionnel);
                                        this.apec.participationAssure = this.apec.participationAssure - (- this.apec.regleProportionnel + this.apecInBase.regleProportionnel);
                                    }
                                    if (this.apec.participationGa != this.apecInBase.participationGa) {
                                        this.apec.apecEdit = true;
                                        this.apec.modifDate = new Date();
                                    }
                                    this.apec.etat = EtatAccord.ACC_A_VALIDER;
                                }





                                if (params['modif'] == "valid" && this.apec.etat != 8) {
                                    this.apec.etat = EtatAccord.ACC_A_VALIDER_PART_ASSURE;
                                }
                            });
                            this.route.params.subscribe((params) => {
                                if (params['modif'] == "approuv") {
                                    this.apec.approuvDate = new Date();
                                }
                                if (params['modif'] == "valid") {
                                    this.apec.accordRaisonId = null;
                                    this.apec.validationDate = new Date();
                                }
                            });
                            if (this.modeApprouv == true && this.apec.etat == EtatAccord.ACC_DEFAVORABLE && this.isDevisComplementaire == true) {

                                let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                                this.quotation.listPieces = listPieces;
                                this.quotation.isConfirme = false;
                                this.quotation.fromSignature = true;
                                this.sinisterPec.quotation = this.quotation;
                                this.sinisterPec.stepId = 110;
                                // this.sinisterPec.observations = this.observations;
                                this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.apec.etat = EtatAccord.ACC_INSTANCE_REPARATION;
                                    this.apec.isConfirme = false;
                                    this.apec.testDevis = false;
                                    this.apecService.update(this.apec).subscribe((apecQuote: Apec) => {
                                        this.router.navigate(['../apec-approuv']);
                                    });
                                });
                            } else {
                                this.subscribeToSaveResponse(
                                    this.apecService.update(this.apec));

                                this.route.params.subscribe((params) => {
                                    if (params['modif'] == "modif") {
                                        this.router.navigate(['../apec-modif']);
                                        this.sinisterPec.oldStepNw = 104;

                                    }
                                    if (params['modif'] == "imprimer") {
                                        this.router.navigate(['../apec-imprim']);
                                    }
                                    if (params['modif'] == "valid") {
                                        this.router.navigate(['../apec-valid']);
                                    }
                                    if (params['modif'] == "approuv") {
                                        this.router.navigate(['../apec-approuv']);
                                    }

                                });
                            }

                        }
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

        }
    }
    nonValide() {
        this.isSaving = false;

        if (this.apec.id !== undefined) {
            this.apec.assureValidationDate = new Date();
            this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir sauvegarder l\'accord ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        if (this.oneClickForButton2) {
                            this.oneClickForButton2 = false;
                            this.apec.etat = EtatAccord.ACC_NON_VALIDER;

                            this.subscribeToSaveResponse(
                                this.apecService.update(this.apec));
                        }
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

        }
    }

    validationAssure() {
        this.isSaving = false;
        if (this.apec.id !== undefined) {
            this.apec.assureValidationDate = new Date();
            if (this.apec.decisionAssure == DecisionAssure.ACC_ACCEPTED) {
                this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer l\'accord ?', 'Oui', 'Non', 'lg')
                    .then((confirmed) => {
                        console.log('User confirmed:', confirmed);
                        if (confirmed) {
                            if (this.oneClickForButton3) {
                                this.oneClickForButton3 = false;
                                this.apec.etat = EtatAccord.ACC_VALIDE_PART_ASSURE;
                                this.route.params.subscribe((params) => {

                                });
                                this.subscribeToSaveResponse(
                                    this.apecService.update(this.apec));

                                this.route.params.subscribe((params) => {

                                    if (params['modif'] == "valid-part") {
                                        this.router.navigate(['../apec-valid-assur']);
                                    }
                                });

                            }
                        }
                    })
                    .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
            }
            if (this.apec.decisionAssure == DecisionAssure.ACC_REFUSED) {
                this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer l\'accord ?', 'Oui', 'Non', 'lg')
                    .then((confirmed) => {

                        console.log('User confirmed:', confirmed);
                        if (confirmed) {
                            if (this.oneClickForButton4) {
                                this.oneClickForButton4 = false;
                                this.apec.etat = EtatAccord.ACC_REFUSED;

                                this.subscribeToSaveResponse(
                                    this.apecService.update(this.apec));
                                this.route.params.subscribe((params) => {

                                    if (params['modif'] == "valid-part") {
                                        this.router.navigate(['../apec-valid-assur']);
                                    }
                                });

                            }
                        }
                    })
                    .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
            }
            if (this.apec.decisionAssure == DecisionAssure.ACC_INSTANCE_CONFIRMATION) {
                this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer l\'accord ?', 'Oui', 'Non', 'lg')
                    .then((confirmed) => {
                        console.log('User confirmed:', confirmed);
                        if (confirmed) {
                            if (this.oneClickForButton5) {
                                this.oneClickForButton5 = false;
                                this.apec.etat = EtatAccord.ACC_A_APPROUV;
                                this.route.params.subscribe((params) => {

                                });
                                this.subscribeToSaveResponse(
                                    this.apecService.update(this.apec));
                                this.route.params.subscribe((params) => {

                                    if (params['modif'] == "valid-part") {
                                        this.router.navigate(['../apec-valid-assur']);
                                    }
                                });

                            }
                        }
                    })
                    .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
            }
            if (this.apec.decisionAssure == DecisionAssure.ACC_ACCEPTED_WITH_GENERIQ_PIECE) { // voir suppression accord ??
                this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer l\'accord ?', 'Oui', 'Non', 'lg')
                    .then((confirmed) => {
                        console.log('User confirmed:', confirmed);
                        if (confirmed) {
                            if (this.oneClickForButton6) {
                                this.oneClickForButton6 = false;
                                this.apec.etat = EtatAccord.ACC_WITH_GENERIC_PIECE;
                                let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                                listPieces.forEach(element => {
                                    element.observationExpert = null;
                                });

                                this.quotation.listPieces = listPieces;
                                this.sinisterPec.quotation = this.quotation;
                                // this.sinisterPec.observations = this.observations;
                                this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.subscribeToSaveResponse(
                                        this.apecService.update(this.apec));

                                    this.route.params.subscribe((params) => {

                                        if (params['modif'] == "valid-part") {
                                            this.router.navigate(['../apec-valid-assur']);
                                        }
                                    });
                                });
                            }
                        }
                    })
                    .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
            }

        }
    }
    imprimer() {
        this.isSaving = false;
        if (this.apec.id !== undefined) {
            this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir imprimer l\'accord ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        if (this.oneClickForButton7) {
                            this.oneClickForButton7 = false;
                            this.apec.etat = EtatAccord.ACC_IMPRIMER;
                            this.apec.testImprim = false;
                            this.sinisterPec.oldStepNw = 103;
                            this.sinisterPec.stepId = 109;
                            this.apecService.generateAccordPrisCharge(this.sinisterPec, this.apec.quotationId, false, 9, this.labelAccordNormal).subscribe((resPdf) => {
                                this.sendNotifImprim('imprimAccord');
                                this.subscribeToSaveResponse(
                                    this.apecService.update(this.apec));
                                window.open(resPdf.headers.get('pdfname'), '_blank');
                                this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                                this.isSaving = false;
                            })

                            this.router.navigate(['../apec-imprim']);
                        }
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

        }

    }

    getPieceNew(entityName: string, sinisterPecId: number, label: string) {
        this.sinisterPecService.getPieceBySinPecAndLabel(entityName, sinisterPecId, label).subscribe((blob: Blob) => {
            const indexOfFirst = (blob.type).indexOf('/');
            this.extensionImage = ((blob.type).substring((indexOfFirst + 1), ((blob.type).length)));
            this.extensionImage = this.extensionImage.toLowerCase();
            let fileName = label + "." + this.extensionImage;
            console.log(fileName);

            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, fileName);
            } else {
                var a = document.createElement('a');
                a.href = URL.createObjectURL(blob);
                a.download = fileName;
                a.target = '_blank';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
        },
            err => {
                alert("Error while downloading. File Not Found on the Server");
            });
    }



    sendNotifImprim(typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.userExNotifRep = [];
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idReparateur': this.sinisterPec.reparateurId, 'stepPecId': this.sinisterPec.stepId };
                    this.sendNotifAccordDecision(55, this.currentAccount.id, this.currentAccount.id, settingJson);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.reparateurId, 28).subscribe((userExNotifRep) => {
                        this.userExNotifRep = userExNotifRep.json
                        this.userExNotifRep.forEach(element => {
                            let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idReparateur': this.sinisterPec.reparateurId, 'stepPecId': this.sinisterPec.stepId };
                            this.sendNotifAccordDecision(55, element.userId, usr.userId, settingJson);
                        });
                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idReparateur': this.sinisterPec.reparateurId, 'stepPecId': this.sinisterPec.stepId }));
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

    private subscribeToSaveResponse(result: Observable<Apec>) {
        result.subscribe((res: Apec) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    sendNotifAccordDecision(notificationId, destination, sourse, settings) {
        this.notifUser = new NotifAlertUser();
        this.notification.id = notificationId;
        this.notifUser.destination = destination;
        console.log("accord favorable-*-*-*-" + destination);
        this.notifUser.source = sourse;
        this.notifUser.entityId = this.sinisterPec.id;
        this.notifUser.entityName = "SinisterPec";
        this.notifUser.notification = this.notification;
        this.notifUser.settings = JSON.stringify(settings);
        this.listNotifUser.push(this.notifUser);
    }

    private onSaveSuccess(result: Apec) {

        this.observationApec.apecId = this.apecId;
        if (this.modeEdit == true) {
            this.observationApec.modifDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
            this.observationApec.decriptionObservationAssure = null;
            this.observationApec.observationAssure = null;
        }
        if (this.modeApprouv == true) {
            this.observationApec.approuvDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        }
        if (this.modeValid == true) {
            this.observationApec.validationDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        }
        if (this.modeValidPart == true) {
            this.observationApec.assureValidationDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        }
        if (this.modeImprimer == true) {
            this.observationApec.imprimDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        }
        if (this.observationApec.id !== null && this.observationApec !== undefined) {
            this.observationApecService.update(this.observationApec).subscribe((resSave) => {
                this.observationApec = resSave;
            });
        } else {
            this.observationApecService.create(this.observationApec).subscribe((resSave) => {
                this.observationApec = resSave;
            });
        }




        /*this.notificationAlerteService.query().subscribe((res) => {
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((resuser) => {
                this.userExtra = resuser;
                for (let i = 0; i < res.length; i++) {
                    var obj = JSON.parse(res[i].settings);
                    if (res[i].notification.type == "N" && res[i].destination == this.userExtra.id
                    && (obj.typenotification == NotificationAccord.NOTIF_ACCORD_FAVORABLE || obj.typenotification == NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_MODIF
                        || obj.typenotification == NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV || obj.typenotification == NotificationAccord.NOTIF_ACCORD_DEFAVORABLE
                        || obj.typenotification == NotificationAccord.NOTIF_ACCORD_VALIDE || obj.typenotification == NotificationAccord.NOTIF_NON_ACCORD_VALIDE
                        || obj.typenotification == NotificationAccord.NOTIF_ASSURE_ACCEPTED || obj.typenotification == NotificationAccord.NOTIF_ASSURE_REFUSED
                        || obj.typenotification == NotificationAccord.NOTIF_ASSURE_ACCEPTED_WITH_P_GEN || obj.typenotification == NotificationAccord.NOTIF_ASSURE_INSTANCE_CONFIRM)) {
                        res[i].read = true;
                        this.notificationAlerteService.update(res[i]).subscribe((res) => {
                            this.notifUserUpdate = res;
                        });
                    }
                }
            });
    })*/

        /******************************** START NOTIFS ****************************************/
        if (this.modeApprouv) {
            if (this.apec.decision == 1) { // Notif Accord favorable


                if (this.oneClickForButton) {
                    this.oneClickForButton = false;
                    this.listNotifUser = [];
                    if (this.sinisterPec.modeId == 7) {
                        let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                        this.sendNotifAccordDecision(1, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json;
                            console.log("4-------------------------------------------------------------------");
                            this.userExNotifAgency.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 1;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 1;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                });
                            });
                        });
                    } else if (this.sinisterPec.modeId == 1 || this.sinisterPec.modeId == 2 || this.sinisterPec.modeId == 3 || this.sinisterPec.modeId == 4 || this.sinisterPec.modeId == 5 || this.sinisterPec.modeId == 6 || this.sinisterPec.modeId == 8 || this.sinisterPec.modeId == 9) {
                        if (this.apec.ttc <= 3000) {
                            let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                            this.sendNotifAccordDecision(1, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                console.log("4-------------------------------------------------------------------");
                                this.userExNotifAgency.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 1;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                    this.userExNotifPartner = userExNotifPartner.json
                                    this.userExNotifPartner.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 1;
                                        this.notifUser.source = this.userExtra.id;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.settings = JSON.stringify(settingJson);
                                        this.listNotifUser.push(this.notifUser);
                                    });
                                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                        this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                    });
                                });
                            });
                        } else if (this.apec.ttc >= 3000 && this.apec.ttc <= 15000) {
                            if (this.assignTo.userBossId) {
                                let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                                this.sendNotifAccordDecision(1, this.assignTo.userBossId, this.userExtra.id, settingJson);
                                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                                    this.userExNotifAgency = userExNotifAgency.json;
                                    console.log("4-------------------------------------------------------------------");
                                    this.userExNotifAgency.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 1;
                                        this.notifUser.source = this.userExtra.id;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.settings = JSON.stringify(settingJson);
                                        this.listNotifUser.push(this.notifUser);
                                    });
                                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                        this.userExNotifPartner = userExNotifPartner.json
                                        this.userExNotifPartner.forEach(element => {
                                            this.notifUser = new NotifAlertUser();
                                            this.notification.id = 1;
                                            this.notifUser.source = this.userExtra.id;
                                            this.notifUser.destination = element.userId;
                                            this.notifUser.notification = this.notification;
                                            this.notifUser.entityId = this.sinisterPec.id;
                                            this.notifUser.entityName = "SinisterPec";
                                            this.notifUser.settings = JSON.stringify(settingJson);
                                            this.listNotifUser.push(this.notifUser);
                                        });
                                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                            this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                        });
                                    });
                                });
                            }
                        } else if (this.apec.ttc > 15000) {
                            if (this.assignTo.userBossId) {
                                this.userExtraService.find(this.assignTo.userBossId).subscribe((usr: UserExtra) => {
                                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                                    if (usr.userBossId) {
                                        this.sendNotifAccordDecision(1, usr.userBossId, usr.id, settingJson);
                                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency = userExNotifAgency.json;
                                            console.log("4-------------------------------------------------------------------");
                                            this.userExNotifAgency.forEach(element => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 1;
                                                this.notifUser.source = this.userExtra.id;
                                                this.notifUser.destination = element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName = "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(settingJson);
                                                this.listNotifUser.push(this.notifUser);
                                            });
                                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner = userExNotifPartner.json
                                                this.userExNotifPartner.forEach(element => {
                                                    this.notifUser = new NotifAlertUser();
                                                    this.notification.id = 1;
                                                    this.notifUser.source = this.userExtra.id;
                                                    this.notifUser.destination = element.userId;
                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                    this.notifUser.entityName = "SinisterPec";
                                                    this.notifUser.notification = this.notification;
                                                    this.notifUser.settings = JSON.stringify(settingJson);
                                                    this.listNotifUser.push(this.notifUser);
                                                });
                                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                    this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                                });
                                            });
                                        });
                                    }
                                });
                            }
                        }
                    }
                }
            }
            if (this.apec.decision == 4) { // Notif Accord favorable avec modification
                if (this.oneClickForButton) {
                    this.oneClickForButton = false;
                    this.listNotifUser = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_MODIF, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    this.sendNotifAccordDecision(2, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 2;
                            this.notifUser.source = this.userExtra.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify(settingJson);
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 2;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                            });
                        });
                    });
                }
            }
            if (this.apec.decision == 5) { // Notif Accord favorable avec réserve
                if (this.oneClickForButton) {
                    this.oneClickForButton = false;
                    this.listNotifUser = [];
                    if (this.sinisterPec.modeId == 7) {
                        let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                        this.sendNotifAccordDecision(3, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json;
                            console.log("4-------------------------------------------------------------------");
                            this.userExNotifAgency.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 3;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 3;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                });
                            });
                        });
                    } else if (this.sinisterPec.modeId == 1 || this.sinisterPec.modeId == 2 || this.sinisterPec.modeId == 3 || this.sinisterPec.modeId == 4 || this.sinisterPec.modeId == 5 || this.sinisterPec.modeId == 6 || this.sinisterPec.modeId == 8 || this.sinisterPec.modeId == 9) {
                        if (this.apec.ttc <= 3000) {
                            console.log("notif accord favorable avec reserve-*-*-*");
                            let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                            this.sendNotifAccordDecision(3, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                console.log("4-------------------------------------------------------------------");
                                this.userExNotifAgency.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 3;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                    this.userExNotifPartner = userExNotifPartner.json
                                    this.userExNotifPartner.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 3;
                                        this.notifUser.source = this.userExtra.id;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.settings = JSON.stringify(settingJson);
                                        this.listNotifUser.push(this.notifUser);
                                    });
                                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                        this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                    });
                                });
                            });
                        } else if (this.apec.ttc >= 3000 && this.apec.ttc <= 15000) {
                            if (this.assignTo.userBossId) {
                                let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                                this.sendNotifAccordDecision(3, this.assignTo.userBossId, this.userExtra.id, settingJson);
                                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                                    this.userExNotifAgency = userExNotifAgency.json;
                                    console.log("4-------------------------------------------------------------------");
                                    this.userExNotifAgency.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 3;
                                        this.notifUser.source = this.userExtra.id;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.settings = JSON.stringify(settingJson);
                                        this.listNotifUser.push(this.notifUser);
                                    });
                                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                        this.userExNotifPartner = userExNotifPartner.json
                                        this.userExNotifPartner.forEach(element => {
                                            this.notifUser = new NotifAlertUser();
                                            this.notification.id = 3;
                                            this.notifUser.source = this.userExtra.id;
                                            this.notifUser.destination = element.userId;
                                            this.notifUser.entityId = this.sinisterPec.id;
                                            this.notifUser.entityName = "SinisterPec";
                                            this.notifUser.notification = this.notification;
                                            this.notifUser.settings = JSON.stringify(settingJson);
                                            this.listNotifUser.push(this.notifUser);
                                        });
                                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                            this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                        });
                                    });
                                });
                            }
                        } else if (this.apec.ttc > 15000) {
                            if (this.assignTo.userBossId) {
                                this.userExtraService.find(this.assignTo.userBossId).subscribe((usr: UserExtra) => {
                                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                                    if (usr.userBossId) {
                                        this.sendNotifAccordDecision(3, usr.userBossId, usr.id, settingJson);
                                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                            this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                        });
                                    }
                                });
                            }
                            let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                console.log("4-------------------------------------------------------------------");
                                this.userExNotifAgency.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 3;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                    this.userExNotifPartner = userExNotifPartner.json
                                    this.userExNotifPartner.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 3;
                                        this.notifUser.source = this.userExtra.id;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.settings = JSON.stringify(settingJson);
                                        this.listNotifUser.push(this.notifUser);
                                    });
                                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                        this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                    });
                                });
                            });
                        }
                    }
                }
            }
            if (this.apec.decision == 2) { // Notif Accord défavorable
                if (this.oneClickForButton) {
                    this.oneClickForButton = false;
                    this.listNotifUser = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_DEFAVORABLE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    this.sendNotifAccordDecision(4, this.assignTo.userBossId, this.userExtra.id, settingJson);
                    this.sendNotifAccordDecision(4, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 4;
                            this.notifUser.source = this.userExtra.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify(settingJson);
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 4;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                            });
                        });
                    });
                }
            }
        }













        if (this.modeValid) {
            if (this.apec.etat == EtatAccord.ACC_A_VALIDER_PART_ASSURE) { // Notif Accord valide
                if (this.oneClickForButtonEtat) {
                    this.oneClickForButtonEtat = false;
                    this.listNotifUser = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ACCORD_VALIDE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    this.sendNotifAccordDecision(5, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 5;
                            this.notifUser.source = this.userExtra.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify(settingJson);
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 5;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                            });
                        });
                    });
                }
            }
            if (this.apec.etat == EtatAccord.ACC_NON_VALIDER) { // Notif Accord non valide
                if (this.oneClickForButtonEtat) {
                    this.oneClickForButtonEtat = false;
                    this.listNotifUser = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_NON_ACCORD_VALIDE, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    this.sendNotifAccordDecision(6, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 6;
                            this.notifUser.source = this.userExtra.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify(settingJson);
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 6;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                            });
                        });
                    });
                }
            }
        }

        if (this.modeValidPart) {
            if (this.apec.decisionAssure == DecisionAssure.ACC_ACCEPTED) { // Notif Assuree accepte
                if (this.oneClickForButtonDecAssure) {
                    this.oneClickForButtonDecAssure = false;
                    this.listNotifUser = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ASSURE_ACCEPTED, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    this.sendNotifAccordDecision(7, this.sinisterPec.assignedToId, this.userExtra.id, settingJson);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.reparateurId, 28).subscribe((userExNotif) => {
                        this.userExNotif = userExNotif.json;
                        this.userExNotif.forEach(element => {
                            this.sendNotifAccordDecision(7, element.userId, this.userExtra.id, settingJson);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json;
                            console.log("4-------------------------------------------------------------------");
                            this.userExNotifAgency.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 7;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 7;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                });
                            });
                        });

                    });
                }
            }

            if (this.apec.decisionAssure == DecisionAssure.ACC_REFUSED) { // Notif Assuree REFUSE
                if (this.oneClickForButtonDecAssure) {
                    this.oneClickForButtonDecAssure = false;
                    this.listNotifUser = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ASSURE_REFUSED, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    if (this.assignTo.userBossId) {
                        this.sendNotifAccordDecision(8, this.assignTo.userBossId, this.userExtra.id, settingJson);
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json;
                            console.log("4-------------------------------------------------------------------");
                            this.userExNotifAgency.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 8;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 8;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                });
                            });
                        });
                    }
                }
            }
            if (this.apec.decisionAssure == DecisionAssure.ACC_ACCEPTED_WITH_GENERIQ_PIECE) { // Notif Assuree Accepte avec pieces generique
                if (this.oneClickForButtonDecAssure) {
                    this.oneClickForButtonDecAssure = false;
                    this.listNotifUser = [];
                    this.userExNotif = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ASSURE_ACCEPTED_WITH_P_GEN, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.reparateurId, 28).subscribe((userExNotif) => {
                        this.userExNotif = userExNotif.json;
                        this.userExNotif.forEach(element => {
                            this.sendNotifAccordDecision(9, element.userId, this.userExtra.id, settingJson);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json;
                            console.log("4-------------------------------------------------------------------");
                            this.userExNotifAgency.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 9;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 9;
                                    this.notifUser.source = this.userExtra.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(settingJson);
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                                });
                            });
                        });
                    });
                }
            }
            if (this.apec.decisionAssure == DecisionAssure.ACC_INSTANCE_CONFIRMATION) { // Notif Assuree en instance de confirmation
                if (this.oneClickForButtonDecAssure) {
                    this.oneClickForButtonDecAssure = false;
                    this.listNotifUser = [];
                    this.userExNotif = [];
                    let settingJson = { 'typenotification': NotificationAccord.NOTIF_ASSURE_INSTANCE_CONFIRM, 'idApec': this.apec.id, 'sinisterPecId': this.apec.sinisterPecId, 'quotationId': this.apec.quotationId, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId };

                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 10;
                            this.notifUser.source = this.userExtra.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify(settingJson);
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 10;
                                this.notifUser.source = this.userExtra.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify(settingJson);
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify(settingJson));
                            });
                        });
                    });

                }
            }
        }



        /******************************** END NOTIFS ****************************************/

        if (this.apec.etat == EtatAccord.ACC_NON_VALIDER) { // Accord non valide
            console.log("if cnon valid-**-*-" + this.apec.etat);
            this.sinisterPec.repriseStep = this.sinisterPec.stepId;
            this.sinisterPec.repriseEtat = this.apecStatut;
            this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
            //this.sinisterPec.approvPec

            this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                console.log("update pec  non valid");
                this.sinisterPec = res;
                this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                this.isSaving = false;
            });
        } else {
            if (this.apec.etat == EtatAccord.ACC_WITH_GENERIC_PIECE) {   // DECISION ASSURE WITH GENERIQ PIECE

                console.log("accepted with generic piece*-*-" + this.apec.etat);
                console.log("step sinister pec*-*-" + this.sinisterPec.stepId);
                this.sinisterPec.oldStep = this.sinisterPec.stepId;
                this.sinisterPec.stepId = PrestationPecStep.UPDATE_QUOTE;
                this.sinisterPec.pieceGenerique = true;


                this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                    this.sinisterPec = res;
                    this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                    this.isSaving = false;
                });
            } else {
                if (this.apec.etat == EtatAccord.ACC_REFUSED) { // Accord refus par assuree
                    console.log("if refused-**-*-" + this.apec.etat);
                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                    this.sinisterPec.repriseEtat = this.apecStatut;
                    this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
                    this.sinisterPec.reasonCanceledId = 91;
                    //this.sinisterPec.approvPec

                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                        console.log("update pec  non valid");
                        this.sinisterPec = res;
                        this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                        this.isSaving = false;
                    });
                } else {
                    if (this.apec.etat == EtatAccord.ACC_DEFAVORABLE) { // Accord defavorable
                        console.log("if refused-**-*-" + this.apec.etat);
                        this.sinisterPec.oldStep = this.sinisterPec.stepId;
                        this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                        this.sinisterPec.repriseEtat = this.apecStatut;
                        this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
                        //this.sinisterPec.approvPec

                        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                            this.sinisterPec = res;
                            this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                            this.isSaving = false;
                        });
                    } else {
                        if (this.apec.etat == EtatAccord.ACC_REFUSED) { // Accord refus par assuree
                            console.log("if refused-**-*-" + this.apec.etat);
                            this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                            this.sinisterPec.repriseEtat = this.apecStatut;
                            this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
                            this.sinisterPec.reasonCanceledId = 91;
                            //this.sinisterPec.approvPec

                            this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                console.log("update pec  non valid");
                                this.sinisterPec = res;
                                this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                                this.isSaving = false;
                            });
                        } else {
                            if (this.apec.etat == EtatAccord.ACC_DEFAVORABLE) { // Accord defavorable
                                console.log("if refused-**-*-" + this.apec.etat);
                                this.sinisterPec.oldStep = this.sinisterPec.stepId;
                                this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                                this.sinisterPec.repriseEtat = this.apecStatut;
                                this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
                                //this.sinisterPec.approvPec

                                this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                                    this.isSaving = false;
                                });
                            } else {
                                if (this.apec.etat == EtatAccord.ACC_REFUSED) { // Accord refus par assuree
                                    console.log("if refused-**-*-" + this.apec.etat);
                                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                                    this.sinisterPec.repriseEtat = this.apecStatut;
                                    this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
                                    this.sinisterPec.reasonCanceledId = 91;
                                    //this.sinisterPec.approvPec

                                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                        console.log("update pec  non valid");
                                        this.sinisterPec = res;
                                        this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                                        this.isSaving = false;
                                    });
                                } else {
                                    if (this.apec.etat == EtatAccord.ACC_DEFAVORABLE) { // Accord defavorable
                                        console.log("if refused-**-*-" + this.apec.etat);
                                        this.sinisterPec.oldStep = this.sinisterPec.stepId;
                                        this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                                        this.sinisterPec.repriseEtat = this.apecStatut;
                                        this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_CANCELLATION;
                                        //this.sinisterPec.approvPec

                                        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                            console.log("update pec  non valid");
                                            this.sinisterPec = res;
                                            this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                                            this.isSaving = false;
                                        });

                                    } else {
                                        console.log("valid-**-*-" + this.apec.etat);
                                        this.eventManager.broadcast({ name: 'apecListModification', content: 'OK' });
                                        this.isSaving = false;
                                    }
                                }
                            }
                        }

                        //this.activeModal.dismiss(result);
                    }
                }
            }
        }
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


    historyApec() {
        this.ngbModalRef = this.sinisterPecPopupService.openHistoryDetailsSinisterPec(HistoryPopupDetailsPec as Component, this.sinisterPec.id);
        this.ngbModalRef.result.then((result: any) => {
            if (result !== null && result !== undefined) {
                console.log("result select popup------" + result);


            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });
    }
}

@Component({
    selector: 'jhi-apec-popup',
    template: ''
})
export class ApecPopupComponent implements OnInit {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apecPopupService: ApecPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.apecPopupService
                    .open(ApecDialogComponent as Component, params['id']);
            } else {
                this.apecPopupService
                    .open(ApecDialogComponent as Component);
            }
        });
    }

    /*ngOnDestroy() {
        this.routeSub.unsubscribe();
    }*/
}
