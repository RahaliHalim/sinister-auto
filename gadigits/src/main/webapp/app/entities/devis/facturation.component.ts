import { Component, OnInit, OnDestroy, ɵConsole } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { DevisPopupService } from './devis-popup.service';
import { SinisterPec, SinisterPecService, SinisterPecPopupService, } from '../sinister-pec';
import { DetailsMo } from '../details-mo';
import { PrestationPecStep, TypeInterventionQuotation } from '../../constants/app.constants';
import { DetailsPieces, DetailsPiecesService } from '../details-pieces';
import { Principal, ResponseWrapper } from '../../shared';
import { PieceJointe } from '../piece-jointe/piece-jointe.model';
import { SafeResourceUrl, DomSanitizer, EventManager } from '@angular/platform-browser';
import { GAEstimateService } from "./gaestimate.service";
import { QuotationService } from '../quotation/quotation.service';
import { Quotation } from '../quotation';
import { ReparateurService } from '../reparateur/reparateur.service';
import { Reparateur } from '../reparateur/reparateur.model';
import { Grille } from '../grille/grille.model';
import { Piece } from '../piece/piece.model';
import { Assure, AssureService } from '../assure';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { VehiculeAssure } from '../vehicule-assure/vehicule-assure.model';
import { Sinister } from '../sinister/sinister.model';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { RefTypeIntervention } from '../ref-type-intervention/ref-type-intervention.model';
import { Devis } from './devis.model';
import { PieceService } from '../piece/piece.service';
import { RefTypeInterventionService } from '../ref-type-intervention/ref-type-intervention.service';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap/datepicker/ngb-date-struct';
import { NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap/datepicker/ngb-date-parser-formatter';
import { TypePiecesDevis, QuoteStatus } from '../../constants/app.constants';
import { Estimation } from '../reparation/estimation.model';
import { RefMotif, RefMotifService } from '../ref-motif';
import { ViewChild, ElementRef } from '@angular/core';
import { SinisterService } from '../sinister/sinister.service';
import { VehiculeAssureService } from '../vehicule-assure/vehicule-assure.service';
import { Observation, TypeObservation, ObservationService } from '../observation';
import { ConfirmationDialogService } from '../../shared';
import { Attachment } from '../attachments';
import { Journal } from '../journal';
import { HistoryPopupDetailsPec } from '../sinister-pec/historyPopupDetailsPec.component';
import { VehicleUsage, VehicleUsageService } from '../vehicle-usage';
import { Expert, ExpertService } from '../expert';
import { Governorate, GovernorateService } from '../governorate';
import { RaisonPecService, RaisonPec } from '../raison-pec';
import { RefModeGestion } from '../ref-mode-gestion';
import { VatRate, VatRateService } from '../vat-rate';
import { StampDutyService } from '../stamp-duty/stamp-duty.service';
import { StampDuty } from '../stamp-duty/stamp-duty.model';
import { AccordPriseCharge } from './accord-prise-charge.model';
import { DevisService } from './devis.service';
import { ApecService, Apec } from '../apec';
import { saveAs } from 'file-saver/FileSaver';

import * as Stomp from 'stompjs';
import { UserExtra, UserExtraService } from '../user-extra';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { VehiclePieceService } from '../vehicle-piece/vehicle-piece.service';
import { VehiclePiece } from '../vehicle-piece/vehicle-piece.model';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ViewDetailQuotationService } from './view-detail-quotation.service';
import { ViewDetailQuotation } from './view-detail-quotation.model';

@Component({

    selector: "jhi-facturation",
    templateUrl: "./facturation.component.html",
})
export class FacturationComponent implements OnInit {
    @ViewChild('myFrame') myFrame: ElementRef;

    primaryQuotation: Quotation = new Quotation();
    devis: Devis = new Devis();
    quotation: Quotation = new Quotation();
    isSaving: boolean;
    isModeSaisie: boolean = false;
    observation = new Observation();
    attachments: Attachment[] = [];
    sinisterPec: SinisterPec = new SinisterPec();
    AvisExpert: boolean = false;

    expertObservations = [];
    validationDevis = false;
    dossier: Sinister = new Sinister();
    contratAssurance: ContratAssurance = new ContratAssurance();
    vehicule: VehiculeAssure = new VehiculeAssure();
    assure: Assure = new Assure();
    // refUsage: RefUsageContrat = new RefUsageContrat();
    refUsage: VehicleUsage = new VehicleUsage();
    refModeGestion: RefModeGestion = new RefModeGestion();
    vehiculeIsActive = false;
    assureIsActive = false;
    test = false;
    etat: any = 'Refuse_Apres_Facture';
    // Ref list
    moPieces: VehiclePiece[] = [];
    ingredientPieces: VehiclePiece[] = [];
    fourniturePieces: VehiclePiece[] = [];
    isCommentError = false;
    refOperationTypes: RefTypeIntervention[];
    // list of pieces
    detailsMos: DetailsMo[] = [];
    insured: Assure = new Assure();
    isCollapsed = false;
    detailsPiece: DetailsPieces = new DetailsPieces();
    detailsPiecesIngredient: DetailsPieces[] = [];
    detailsPiecesFourniture: DetailsPieces[] = [];
    detailsPiecesMO: DetailsPieces[] = [];
    detailsPieces: DetailsPieces[] = [];
    iMoRow: number;
    observationss: Observation[] = [];
    isObsModeEdit = false;
    isPcsModeEdit = false;
    modeEdit = false;
    detailsMo: DetailsMo = new DetailsMo();
    detailsPieceMo: DetailsPieces = new DetailsPieces();
    iPaintRow: number;
    grille: Grille = new Grille();
    lastDevis: any;
    reparateur: Reparateur = new Reparateur();
    estimation: Estimation = new Estimation();
    expert: any;
    private ngbModalRef: NgbModalRef;
    pec: any;
    observations: any;
    piecesJointes: PieceJointe[] = []
    result: boolean;
    devisAccepte: any;
    devisPreview: any;
    devisValide: any;
    devisSaved: any;
    nbrReports: number;
    expertMissionary: boolean = false;
    obserExpert: boolean = false;
    nbmissionExpert: number;
    experts: Expert[] = [];
    expertsAffectation: Expert[] = [];
    sysgouvernorats: Governorate[] = [];
    piece: any;
    verifExport: any;
    status: any;
    motif: any;
    totalTtc = 0;
    moTotalTtc = 0;
    labelPiece1: string;
    ingTotalTtc = 0;
    fournTotalTtc = 0;
    piecesTotalTtc = 0;
    public devisTotalTtc: any;
    public grilleDetailsMo: any;
    ttc: any[] = [];
    ttaffect: any;
    assureNom: any;
    assureAdresse: any;
    assureVille: any;
    assureTel: any;
    contastatuscts: any;
    persostatusnnePhysique: any;
    devisstatusToAccept: any;
    devisFacture: any;
    editDateReceptionVehicule: boolean;
    cancelExpertMissionary: boolean = false;
    totalTTCtest: any;
    validAccord: any;
    dateReceptionVehicule: any;
    admin: any;
    isResponsable: any;
    isGestionnaire: any;
    isChefCellule: any;
    label: string;
    pieceJointe: PieceJointe;
    pj: number;
    deleteDetailsMo: boolean = false;
    deletedpIngredient: boolean = true;
    deletedpRechange: boolean = true;
    resTotalHt = 0;
    resTotalTtc = 0;
    reasons: RaisonPec[] = [];
    refusagecontrats: VehicleUsage[] = [];
    ajoutNouvelpiece = false;
    pieceJ: PieceJointe = new PieceJointe();
    PieceJointes: PieceJointe[] = [];
    deletePiece = false;
    dateExpertiseDp: any;
    detailsM: any;
    pecAnnule = false;
    gaestimateUrl: SafeResourceUrl;
    gaestimateLink: string;
    useGtEstimate = false;
    demontage: any;
    modeReparationLeger: any;
    model: NgbDateStruct;
    dateString: string;
    myDate: any;
    selectedFiles: FileList;
    selectedFileList: File[] = [];
    ajoutPiece = false;
    signedAgreementPreview = false;
    signedAgreementUrl: any;
    currentFileUpload: File;
    selectedDescriptionList: String[] = [];
    refMotif = new RefMotif();
    motifs: RaisonPec[] = [];
    dropdownSettings = {};
    idMotif: number;
    ajoutNouvelpieceform = false;
    actifModeAvisExpert = false;
    modeConfirmation = false;
    observationDetailPiece = "ORIGINE";
    confirme: any = null;
    userReparateur: any;
    SysAction: any;
    fileStorageUrl: any;
    url: any;
    formdata: FormData = new FormData();
    sinister: Sinister = new Sinister();
    listVat: VatRate[];
    totalDiscount: number;
    totalAfterDiscount: number = 0;
    refOperationTypesMo: RefTypeIntervention[];
    refOperationTypesPeinture: RefTypeIntervention[];
    refOperationTypesFourniture: RefTypeIntervention[];
    discountMarque: number;
    activeStamp: StampDuty;
    currentAccount: any;
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    userExNotifAgency: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];
    oneClickForButton: boolean = true;

    // add by Helmi

    receiveVehicule = false;
    quoteValidation = false;
    quoteRectify = false;
    selectedGouvernorat: number;
    ws: any;
    piecesFiles: File[] = [];
    reasonCancelExpertId: number;
    notif: string;
    accord: AccordPriseCharge = new AccordPriseCharge();
    pieceFiles1: File;
    pieceFiles2: File;
    apecFacturation: Apec = new Apec();
    labelFacture: String = "Facture";
    labelPhotoReparation: String = "Photo de Reparation";
    factureFiles: File;
    photoReparationFiles: File;
    factureAttachment: Attachment = new Attachment();
    photoReparationAttachment: Attachment = new Attachment();
    facturePreview = false;
    photoReparationPreview = false;
    showFactureAttachment: boolean = true;
    showPhotoReparationAttachment: boolean = true;
    detailQuotation: ViewDetailQuotation = new ViewDetailQuotation();
    extensionImage: string;
    extensionImageOriginal: string;
    nomImage: string;
    piecesAttachments: Attachment[] = [];
    pieceAttachment1: Attachment = new Attachment();
    updatePieceJointe1 = false;
    piecePreview1 = false;
    selectedItem: boolean = true;
    showPieceAttachment = false;

    constructor(
        private apecService: ApecService,
        private alertService: JhiAlertService,
        private pieceService: PieceService,
        private detailsPiecesService: DetailsPiecesService,
        private reparateurService: ReparateurService,
        private primaryQuotationService: QuotationService,
        private dossierService: SinisterService,
        private refMotifService: RefMotifService,
        private vehiculeAssureService: VehiculeAssureService,
        private quotationService: QuotationService,
        private refTypeInterventionService: RefTypeInterventionService,
        private contratAssuranceService: ContratAssuranceService,
        private route: ActivatedRoute,
        private sinisterPecPopupService: SinisterPecPopupService,
        private principal: Principal,
        private expertService: ExpertService,
        private governorateService: GovernorateService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private router: Router,
        private vehicleUsageService: VehicleUsageService,
        private confirmationDialogService: ConfirmationDialogService,
        private sanitizer: DomSanitizer,
        private evtManager: EventManager,
        private ngbDateParserFormatter: NgbDateParserFormatter,
        private reasonPecService: RaisonPecService,
        private sinisterService: SinisterService,
        private sinisterPecService: SinisterPecService,
        private vatRateService: VatRateService,
        private stampDutyService: StampDutyService,
        private devisService: DevisService,
        private insuredService: AssureService,
        private userExtraService: UserExtraService,
        private vehiclePieceService: VehiclePieceService,
        private notificationAlerteService: NotifAlertUserService,
        private viewDetailQuotationService: ViewDetailQuotationService,
        private observationService: ObservationService

    ) {

    }

    /**
     * Init component
     */
    ngOnInit() {
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.sinisterPec = new SinisterPec();
        this.reasonPecService.query().subscribe((subRes: ResponseWrapper) => {
            this.reasons = subRes.json;
        });
        this.expertService.query().subscribe((listExpert: ResponseWrapper) => {
            this.expertsAffectation = listExpert.json;
        });
        this.governorateService.query().subscribe((res: ResponseWrapper) => {
            this.sysgouvernorats = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleUsageService.query().subscribe((res: ResponseWrapper) => {
            this.refusagecontrats = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));

        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.MAIN_OEUVRE).subscribe((res) => { this.moPieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.INGREDIENT_FOURNITURE).subscribe((res) => { this.ingredientPieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.PIECES_FOURNITURE).subscribe((res) => { this.fourniturePieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        // Get all intervention type from the database
        this.refTypeInterventionService.query().subscribe((res: ResponseWrapper) => { this.refOperationTypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_MO).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesMo = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_PEINTURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesPeinture = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_FOURNITURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesFourniture = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.init();
        this.model = this.setDefaultDate();
        this.onSelectDate(this.model);
        this.vatRateService.query().subscribe((res) => {
            this.listVat = res.json;
        });
        this.stampDutyService.findActiveStamp().subscribe((res) => {
            this.activeStamp = res;
            this.quotation.stampDuty = this.activeStamp.amount;
        });
    }

    init() {
        this.route.params.subscribe((params) => {
            // missionnement Expert
            if (params['idPecFacturation']) {
                this.getDetailReceptionVehicule(params['idPecFacturation']);
                this.sinisterPecService.findPrestationPec(params['idPecFacturation']).subscribe((res) => {
                    this.sinisterPec = res;
                    if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                        this.observationService.findByPrestation(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                            this.observationss = subRes.json;
                        });
                        if (this.sinisterPec.primaryQuotationId) {
                            this.quotationService.find(this.sinisterPec.primaryQuotationId).subscribe((res) => {  // Find devid By ID
                                this.quotation = res;
                                this.quotation.stampDuty = this.activeStamp.amount;
                                console.log("rep icii devis priincipale" + this.quotation.id);
                                this.quotation.confirmationDecisionQuote = false;
                                this.loadAllDetailsMo();
                                this.loadAllIngredient();
                                this.loadAllRechange();
                                this.loadAllFourniture();
                                if (this.sinisterPec.listComplementaryQuotation.length > 0) {
                                    console.log("rep icii devis  comp entrer");

                                    this.sinisterPec.listComplementaryQuotation.forEach(element => {
                                        console.log("rep icii devis compli list element" + element.id);
                                        if (element.isConfirme) {
                                            this.quotationService.find(element.id).subscribe((res) => {  // Find devid By ID
                                                console.log("rep icii devis  quotation  complimentaire sub");
                                                this.quotation = res;
                                                this.quotation.stampDuty = this.activeStamp.amount;
                                                this.loadAllDetailsMo();
                                                this.loadAllIngredient();
                                                this.loadAllRechange();
                                                this.loadAllFourniture();
                                            });
                                        }
                                    });
                                }

                                const date = new Date(this.quotation.expertiseDate);
                                if (this.quotation.expertiseDate) {
                                    this.quotation.expertiseDate = {
                                        year: date.getFullYear(),
                                        month: date.getMonth() + 1,
                                        day: date.getDate()
                                    };
                                }
                                this.status = this.quotation.statusId; // Get etat de devis selectionné 
                            })
                        }
                    } else {
                        this.router.navigate(["/accessdenied"]);
                    }
                });
            }
        });
    }

    savePhotoReparation() {
        this.piecesAttachments.forEach(pieceAttFile => {
            pieceAttFile.creationDate = null;
            if (pieceAttFile.pieceFile !== null) {
                this.sinisterPecService.saveAttachmentsPiecePhotoReparation(this.sinisterPec.id, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.name, 'PHOTOREPARATION').subscribe((res: Attachment) => {
                    //this.alertService.success('auxiliumApp.sinisterPec.createdImprime',null,null);
                });
            }
        });

    }

    ajoutPiece1() {
        this.pieceAttachment1.type = this.labelPiece1;
        this.pieceAttachment1.originalFr = 'Oui';
        this.pieceAttachment1.note = ' ';
        this.pieceAttachment1.creationDateP = this.dateAsYYYYMMDDHHNNSS(new Date());
        this.pieceAttachment1.creationDate = new Date();
        this.pieceAttachment1.pieceFile = this.pieceFiles1;
        this.piecesFiles.push(this.pieceFiles1);
        console.log(this.piecesFiles[0]);
        this.piecesAttachments.push(this.pieceAttachment1);
        this.pieceAttachment1 = new Attachment();
        this.pieceAttachment1.original = true;
        this.labelPiece1 = undefined;
        this.selectedItem = true;
        this.piecePreview1 = false;
        this.ajoutNouvelpieceform = false;
    }

    downloadPieceFile(pieceFileAttachment: File) {
        if (pieceFileAttachment) {
            saveAs(pieceFileAttachment);
        }
    }

    getPhotoReparation() {

        this.sinisterPecService.getPhotoReparationAttachments(this.sinisterPec.id).subscribe((resImprime) => {
            this.piecesAttachments = resImprime.json;
            if (this.piecesAttachments.length !== 0) {
                this.showPieceAttachment = true;
            }

        });


    }

    onPieceFileChange1(fileInput: any) {
        this.updatePieceJointe1 = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.pieceAttachment1.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.pieceAttachment1.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.pieceFiles1 = fileInput.target.files[0];
        this.piecePreview1 = true;
    }

    downloadPieceFile1() {
        if (this.pieceFiles1) {
            saveAs(this.pieceFiles1);
        }
    }

    //edit piece jointe
    prepareEditPieceJ(piece) {
        this.pieceAttachment1 = new Attachment();
        this.pieceJ = piece;
        this.isPcsModeEdit = true;
        this.ajoutPiece = true;
        this.ajoutNouvelpieceform = true;

    }

    getDetailReceptionVehicule(prestationId) {
        this.viewDetailQuotationService.getDetailDevisByPec(prestationId).subscribe((res) => {
            this.detailQuotation = res;
            this.sinisterPec.id = prestationId;
            this.insured.fullName = this.detailQuotation.fullName;
            this.vehicule.immatriculationVehicule = this.detailQuotation.immatriculationVehicule;
            this.vehicule.usageId = this.detailQuotation.usageId;
            this.vehicule.numeroChassis = this.detailQuotation.numeroChassis;
            this.vehicule.puissance = this.detailQuotation.puissance;
            this.vehicule.marqueLibelle = this.detailQuotation.marqueLibelle;
            this.vehicule.modeleLibelle = this.detailQuotation.modeleLibelle;
            this.vehicule.marqueId = this.detailQuotation.marqueId;
            this.reparateur.raisonSociale = this.detailQuotation.raisonSociale;
            this.reparateur.isGaEstimate = this.detailQuotation.isGaEstimate;
            this.contratAssurance.marketValue = this.detailQuotation.marketValue;
            this.contratAssurance.newValueVehicle = this.detailQuotation.newValueVehicle;
            this.sinisterPec.receptionVehicule = this.detailQuotation.receptionVehicule;
            this.sinisterPec.vehicleReceiptDate = this.detailQuotation.vehicleReceiptDate;
            this.sinisterPec.lightShock = this.detailQuotation.lightShock;
            this.sinisterPec.disassemblyRequest = this.detailQuotation.disassemblyRequest;
            this.sinisterPec.primaryQuotationId = this.detailQuotation.primaryQuotationId;
            this.reparateur.isGaEstimate = true;
            if (this.detailQuotation.vehicleReceiptDate && !this.detailQuotation.receptionVehicule) {
                const date = new Date(this.detailQuotation.vehicleReceiptDate);

                this.detailQuotation.vehicleReceiptDate = {
                    year: date.getFullYear(),
                    month: date.getMonth() + 1,
                    day: date.getDate()
                };
            }
            else { this.myDate = this.detailQuotation.vehicleReceiptDate; }

        });
    }
    // open the form to add a piece
    ajoutNouvelpieceJointe() {
        this.ajoutNouvelpieceform = true;
        this.ajoutNouvelpiece = false;
        if (this.piecesJointes.length == 6 || this.piecesJointes.length > 6) {
            this.ajoutNouvelpieceform = false;
        }
    }

    findInsured() {// find insured
        this.insuredService.find(this.vehicule.insuredId).subscribe((insured: Assure) => {
            this.insured = insured;
            this.sinister.insuredId = insured.id;
            if (this.insured.company) {
                this.insured.fullName = this.insured.raisonSociale;
            } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
            } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                this.insured.fullName = this.insured.prenom;
            } else {
                this.insured.fullName = this.insured.nom;
            }

        });
    }
    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregister ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.isSaving = true;
                    this.apecService.updateForFacture(this.sinisterPec.id).subscribe((res) => {
                        this.saveAttachments(this.sinisterPec.id);
                        this.savePhotoReparation();
                        this.sendNotif('facturation');
                        this.router.navigate(['../facturation-devis-complementaire']);
                    });

                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }


    //connect to send Notif
    sendNotif(typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 46;
                    this.notifUser.source = usr.userId;
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.notification = this.notification;
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinisterPec.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'primaryQuotationId': this.sinisterPec.primaryQuotationId, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.agencyId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 46;
                            this.notifUser.source = usr.id;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinisterPec.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'primaryQuotationId': this.sinisterPec.primaryQuotationId, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.partnerId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 46;
                                this.notifUser.source = usr.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinisterPec.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'primaryQuotationId': this.sinisterPec.primaryQuotationId, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinisterPec.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'primaryQuotationId': this.sinisterPec.primaryQuotationId, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId }));
                            });
                        });
                    });
                });
            });
        }
    }


    // open the form to add a piece
    downloadFile() {
        if (this.selectedFiles && this.selectedFiles.length > 0) {
            this.currentFileUpload = this.selectedFiles.item(0);
        }
        saveAs(this.currentFileUpload);
    }

    ModeAvisExpert(id) {
        this.actifModeAvisExpert = true;
    }

    selectFile(event, libelle) {
        this.ajoutPiece = true;
        this.selectedFiles = event.target.files;
        this.pieceFiles1 = event.target.files[0]; // read file as data url
        this.piecesFiles.push(this.pieceFiles1);
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

    addPieceJ() {
        if (this.pieceJ.typeLibelle === null || this.pieceJ.typeLibelle === undefined) {
            this.isCommentError = true;
        } else {
            this.pieceJ.libelle = this.selectedFiles.item(0).name;
            this.pieceJ.userId = this.principal.getAccountId();
            this.pieceJ.file = this.selectedFiles.item(0);
            this.piecesJointes.push(this.pieceJ);
            this.pieceJ = new PieceJointe();
            this.ajoutPiece = false;
        }
        this.ajoutNouvelpieceform = false;


    }
    //delete piece jointe
    deletePieceJ(pieceJ) {
        this.piecesAttachments.forEach((item, index) => {
            if (item === pieceJ) this.piecesAttachments.splice(index, 1);
        });
    }
    //edit piece jointe
    // prepareEditPieceJ(piece) {
    //     this.pieceJ = new PieceJointe();
    //     this.pieceJ = piece;
    //     this.isPcsModeEdit = true;
    //     this.ajoutPiece = true;
    //     this.ajoutNouvelpieceform = true;

    // }
    // edit Piece Jointe
    editPieceJ() {
        this.pieceJ = new PieceJointe();
        this.isPcsModeEdit = false;
        this.ajoutNouvelpieceform = false;
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
    /*   addObservation() {
           console.log('Add Observation');
           console.log(this.principal.getCurrentAccount());
           if (this.observation.commentaire === null || this.observation.commentaire === undefined) {
               this.isCommentError = true;
           } else {
               this.observation.type = TypeObservation.Observation;
               this.observation.userId = this.principal.getAccountId();
               this.observation.date = this.formatEnDate(new Date());
               this.observation.sinisterPecId = this.sinisterPec.id; //update when insert prestation pec
               this.observation.prive = false;
               this.observationss.push(this.observation);
               this.observation = new Observation();
           }
       } */
    deleteObservation(observation) {
        observation.deleted = true;
    }

    prepareEditObservation(observation) {
        this.observation = observation;
        this.isObsModeEdit = true;
    }

    editObservation(observation) {
        this.observation = new Observation();
        this.isObsModeEdit = false;
    }
    motifNonConfirme(etat) {
        if (etat == true) {
            this.confirme = false;
            this.sysActionUtilisateurService.find(202)
                .subscribe((subRes: SysActionUtilisateur) => {
                    this.SysAction = subRes;
                    this.motifs = this.SysAction.motifs
                });
            this.dropdownSettings = {
                singleSelection: false,
                idField: 'id',
                textField: 'nom',
                selectAllText: 'Select All',
                unSelectAllText: 'UnSelect All',
                itemsShowLimit: 6,
                allowSearchFilter: true
            };
        }
        else {
            this.confirme = true;
        }
    }

    testQuote() {
        /*this.detailsPiecesService.deleteByQuery(this.sinisterPec.primaryQuotationId).subscribe((response) => {
                        this.detailsPiecesService.deleteByQueryMP(this.sinisterPec.id).subscribe((response) => {
                            this.quotation.id = this.sinisterPec.primaryQuotationId;
                            this.quotation.statusId = 10;
                            let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                            listPieces.forEach(listPieceDetails => {
                                listPieceDetails.isModified = false;
                                listPieceDetails.id = null;
                            });
                            this.quotation.listPieces = listPieces;
                            this.sinisterPec.quotation = this.quotation;
                            this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                this.sinisterPec = res;
                                this.apecService.deleteAPecDevisCompl(this.sinisterPec.id).subscribe((resDel) => {

                                    this.apecService.generateAccordPrisCharge(this.sinisterPec, this.sinisterPec.primaryQuotationId, true, 3).subscribe((resPdf) => {
                                        this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                            this.apecFacturation = res;
                                            this.apecFacturation.etat = 13;
                                            this.apecService.update(this.apecFacturation).subscribe((res) => {
                                                this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                    this.saveAttachments(this.sinisterPec.id);
                                                    this.sendNotif('facturation');
                                                    this.router.navigate(['../facturation-devis-complementaire']);
                                                });
                                            });
                                        })
                                    });
                                });
                            });
                        });
                    });*/
    }

    /******************************************************** Détails Main d'oeuvre ***************************************************/
    /**
    *
    * @param detailsMoLine
    *
    /**
    * Add a new row in mo list
    */
    addNewMoLine() {

        this.detailsPiecesMO.push(new DetailsPieces());

    }
    /**
       * Remove mo line
       * @param index
       */
    removeMoLine(detailsPieceMo, index) {
        if (detailsPieceMo.id != undefined) {
            this.detailsPiecesMO.splice(index, 1);
            this.detailsPiecesService.delete(detailsPieceMo.id).subscribe((res) => {
                this.deleteDetailsMo = true;
            });
        }
        else {
            this.detailsPiecesMO.splice(index, 1);
        }
        this.calculateGlobalMoTtc();
    }
    /**
       *
       * @param detailsMoLine
       */
    changeMo(detailsMoLine: DetailsPieces) {
        detailsMoLine.totalHt = +(detailsMoLine.prixUnit * detailsMoLine.nombreHeures).toFixed(3);
        detailsMoLine.amountDiscount = detailsMoLine.totalHt * detailsMoLine.discount / 100;
        detailsMoLine.amountAfterDiscount = detailsMoLine.totalHt - detailsMoLine.amountDiscount;
        detailsMoLine.amountVat = detailsMoLine.amountAfterDiscount * detailsMoLine.tva / 100;
        detailsMoLine.totalTtc = detailsMoLine.amountAfterDiscount + detailsMoLine.amountVat;

        this.calculateGlobalMoTtc();
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

    loadAllDetailsMo() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, true).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPiecesMO.push(element);
            });


            this.calculateGlobalMoTtc();
            this.getSum4();
        })
    }

    /**
       *
       * @param detailsIngredientLine
       */
    changeIngredient(dpIngredient: DetailsPieces) {
        dpIngredient.totalHt = +(dpIngredient.prixUnit * dpIngredient.quantite).toFixed(3);
        dpIngredient.amountDiscount = dpIngredient.totalHt * dpIngredient.discount / 100;
        dpIngredient.amountAfterDiscount = dpIngredient.totalHt - dpIngredient.amountDiscount;
        dpIngredient.amountVat = dpIngredient.amountAfterDiscount * dpIngredient.tva / 100;
        dpIngredient.totalTtc = dpIngredient.amountAfterDiscount + dpIngredient.amountVat;
        this.calculateGlobalIngTtc();
    }
    /**
      * 
      * @param detailsFournitureLine
      */
    changeFourniture(fourniture: DetailsPieces) {
        fourniture.totalHt = +(fourniture.prixUnit * fourniture.quantite).toFixed(3);
        fourniture.amountDiscount = fourniture.totalHt * fourniture.discount / 100;
        fourniture.amountAfterDiscount = fourniture.totalHt - fourniture.amountDiscount;
        fourniture.amountVat = fourniture.amountAfterDiscount * fourniture.tva / 100;
        fourniture.totalTtc = fourniture.amountAfterDiscount + fourniture.amountVat;
        this.calculateGlobalFournTtc();
    }

    /**
       * 
       * @param detailsPieceRechangeLine
       */
    changePieceRechange(rechange: DetailsPieces) {
        rechange.totalHt = +(rechange.prixUnit * rechange.quantite).toFixed(3);
        rechange.amountDiscount = rechange.totalHt * rechange.discount / 100;
        rechange.amountAfterDiscount = rechange.totalHt - rechange.amountDiscount;
        rechange.amountVat = rechange.amountAfterDiscount * rechange.tva / 100;
        rechange.totalTtc = rechange.amountAfterDiscount + rechange.amountVat;
        this.calculateGlobalPiecesTtc();
    }


    /**
       * Remove mo line
       * @param index
       */
    getSum4(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMos.length; i++) {
            if (this.detailsMos[i].typeInterventionId && this.detailsMos[i].nombreHeures) {
                sum += this.detailsMos[i].totalHt;
            }
        }
        return sum;
    }
    getSum7(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMos.length; i++) {
            if (this.detailsMos[i].typeInterventionId && this.detailsMos[i].nombreHeures) {
                sum += this.detailsMos[i].totalTtc;
            }
        }
        return sum;
    }


    /******************************************************** Détails Ingredient de peinture ***************************************************/

    addNewIngredientLine() {

        this.detailsPiecesIngredient.push(new DetailsPieces());
    }
    addNewPieceRechangeLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.discount = this.discountMarque;
        this.detailsPieces.push(this.detailsPiece);
    }
    addNewPieceFournitureLine() {
        this.detailsPiecesFourniture.push(new DetailsPieces());
    }
    confirmationQuots(etat) {
        if (etat) {
            this.quotation.confirmationDecisionQuote = true;
        } else
            this.quotation.confirmationDecisionQuote = false;
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
    removePieceRechangeLine(pieces, index) {
        if (pieces.id != undefined) {
            this.detailsPieces.splice(index, 1);
            this.detailsPiecesService.delete(pieces.id).subscribe((res) => {
                this.deletedpRechange = true;
            });
        } else {
            this.detailsPieces.splice(index, 1);
        }
        this.calculateGlobalPiecesTtc();
    }

    /**
     * calculate totalTTC
     * @param detailsLine
     */

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

    /**
       * Remove ingredient line
       * @param index
       */
    removeIngredientLine(dpIngredient, index) {
        if (dpIngredient.id != undefined) {
            this.detailsPiecesIngredient.splice(index, 1);
            this.detailsPiecesService.delete(dpIngredient.id).subscribe((res) => {
                this.deletedpIngredient = true;
            });
        } else {
            this.detailsPiecesIngredient.splice(index, 1);
        }
        this.calculateGlobalIngTtc();
    }
    /**
       * Load all ingredient line
       */

    loadAllIngredient() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.INGREDIENT_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPiecesIngredient.push(element);
            });

            this.calculateGlobalIngTtc();
        })
    }
    /******************************************************** Détails Piéces de Rechange ***************************************************/

    getSumPieceHT(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsPieces.length; i++) {
            if (this.detailsPieces[i].prixUnit && this.detailsPieces[i].quantite && this.detailsPieces[i].tva) {
                sum += this.detailsPieces[i].totalHt;
            }
        }
        return sum;
    }

    getSumPieceTTC(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsPieces.length; i++) {
            if (this.detailsPieces[i].isModified != true) {
                if (this.detailsPieces[i].prixUnit && this.detailsPieces[i].quantite && this.detailsPieces[i].tva) {
                    sum += this.detailsPieces[i].totalTtc;
                }
            }
        }
        return sum;
    }

    loadAllRechange() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPieces.push(element);
            });

            this.calculateGlobalPiecesTtc();
        })
    }

    downloadFactureFile() {
        if (this.factureFiles) {
            saveAs(this.factureFiles);
        }
    }

    downloadPhotoReparationFile() {
        if (this.photoReparationFiles) {
            saveAs(this.photoReparationFiles);
        }
    }

    onPhotoReparationFileChange(fileInput: any) {
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.photoReparationAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.photoReparationAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.photoReparationFiles = fileInput.target.files[0];
        this.photoReparationPreview = true;
        console.log(this.photoReparationFiles);
    }

    onFactureFileChange(fileInput: any) {
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.factureAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.factureAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.factureFiles = fileInput.target.files[0];
        this.facturePreview = true;
        console.log(this.factureFiles);
    }

    deleteFactureFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showFactureAttachment = true;
                this.facturePreview = false;
                this.factureFiles = null;
                this.factureAttachment.label = null;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deletePhotoReparationFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showPhotoReparationAttachment = true;
                this.photoReparationPreview = false;
                this.photoReparationFiles = null;
                this.photoReparationAttachment.label = null;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    dataURItoBlobAttchment(dataURI, dataUnit) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: 'image/' + dataUnit });
        return blob;
    }

    saveAttachments(prestationPecId: number) {

        //Face Avant Droit

        if (this.factureFiles !== null) {
            this.sinisterPecService.saveAttachmentsFactureNw(prestationPecId, this.factureFiles, this.labelFacture, this.factureAttachment.name).subscribe((res: Attachment) => {
                this.factureAttachment = res;
            });
        }

        //Carte Grise

        if (this.photoReparationFiles !== null) {
            this.sinisterPecService.saveAttachmentsFactureNw(prestationPecId, this.photoReparationFiles, this.labelPhotoReparation, this.photoReparationAttachment.name).subscribe((res: Attachment) => {
                this.photoReparationAttachment = res;
            });
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

    /******************************************************** Détails Piéces Fourniture ***************************************************/


    /**
       * Remove fourniture line
       * @param index
       */
    removeFournitureLine(fourniture, index) {
        if (fourniture.id != undefined) {
            this.detailsPiecesFourniture.splice(index, 1);
            this.detailsPiecesService.delete(fourniture.id).subscribe((res) => {
                this.deletedpIngredient = true;
            });
        } else {
            this.detailsPiecesFourniture.splice(index, 1);
        }

        this.calculateGlobalFournTtc();

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
    /**
     * Load all pieces fourniture
     */

    loadAllFourniture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.PIECES_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPiecesFourniture.push(element);
            });
            this.calculateGlobalFournTtc();
        })
    }
    setDefaultDate(): NgbDateStruct {
        var startDate = new Date();
        let startYear = startDate.getFullYear().toString();
        let startMonth = startDate.getMonth() + 1;
        let startDay = "1";
        return this.ngbDateParserFormatter.parse(startYear + "-" + startMonth.toString() + "-" + startDay);
    }

    onSelectDate(date: NgbDateStruct) {
        if (date != null) {
            this.model = date;
            this.dateString = this.ngbDateParserFormatter.format(date);
        }
    }
    addObservation() {
        console.log('Add Observation');
        console.log(this.principal.getCurrentAccount());
        if (this.observation.commentaire === null || this.observation.commentaire === undefined) {
            this.isCommentError = true;
        } else {
            this.observation.type = TypeObservation.Observation;
            this.observation.userId = this.principal.getAccountId();
            this.observation.firstName = this.principal.getCurrentAccount().firstName;
            this.observation.lastName = this.principal.getCurrentAccount().lastName;
            this.observation.date = new Date();
            this.observation.sinisterPecId = this.sinisterPec.id;
            this.observation.prive = false;
            this.observationss.push(this.observation);
            this.observation.date = this.formatEnDate(new Date());
            this.observationService.create(this.observation).subscribe((res) => {
            });
            this.observation = new Observation();
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

    trackPrestationPECById(index: number, item: SinisterPec) {
        return item.id;
    }
    trackRefRefUsageContrat(item: VehicleUsage) {
        return item.id;
    }


    dateAsYYYYMMDDHHNNSS(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + ' ' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    nbreMissionByExpert(id) {
        this.sinisterPecService.getNbreMissionExpert(id).subscribe((res: any) => {
            this.nbmissionExpert = res.json;
        })
    }

}
