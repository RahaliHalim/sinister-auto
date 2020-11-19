import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Response } from "@angular/http";
import { JhiAlertService, JhiDateUtils } from "ng-jhipster";
import { DevisPopupService } from "./devis-popup.service";
import {
    SinisterPec,
    SinisterPecService,
    SinisterPecPopupService,
} from "../sinister-pec";
import { DetailsMo } from "../details-mo";
import {
    PrestationPecStep,
    TypeInterventionQuotation,
} from "../../constants/app.constants";
import { DetailsPieces, DetailsPiecesService } from "../details-pieces";
import { Principal, ResponseWrapper } from "../../shared";
import { PieceJointe } from "../piece-jointe/piece-jointe.model";
import {
    SafeResourceUrl,
    DomSanitizer,
    EventManager,
} from "@angular/platform-browser";
import { GAEstimateService } from "./gaestimate.service";
import { QuotationService } from "../quotation/quotation.service";
import { Quotation } from "../quotation";
import { ReparateurService } from "../reparateur/reparateur.service";
import { Reparateur } from "../reparateur/reparateur.model";
import { Grille } from "../grille/grille.model";
import { DateUtils } from "../../utils/date-utils";
import {
    SysActionUtilisateur,
    SysActionUtilisateurService,
} from "../sys-action-utilisateur";
import { VehiculeAssure } from "../vehicule-assure/vehicule-assure.model";
import { Sinister } from "../sinister/sinister.model";
import {
    ContratAssurance,
    ContratAssuranceService,
} from "../contrat-assurance";
import { RefTypeIntervention } from "../ref-type-intervention/ref-type-intervention.model";
import { Devis } from "./devis.model";
import { ObservationService } from "../observation/observation.service";
import { RefTypeInterventionService } from "../ref-type-intervention/ref-type-intervention.service";
import { NgbDateStruct } from "@ng-bootstrap/ng-bootstrap/datepicker/ngb-date-struct";
import { NgbDateParserFormatter } from "@ng-bootstrap/ng-bootstrap/datepicker/ngb-date-parser-formatter";
import { TypePiecesDevis, QuoteStatus } from "../../constants/app.constants";
import { Estimation } from "../reparation/estimation.model";
import { RefMotif, RefMotifService } from "../ref-motif";
import { ViewChild, ElementRef } from "@angular/core";
import { SinisterService } from "../sinister/sinister.service";
import { VehiculeAssureService } from "../vehicule-assure/vehicule-assure.service";
import { Observation, TypeObservation } from "../observation";
import { ConfirmationDialogService } from "../../shared";
import { Attachment } from "../attachments";
import { Journal } from "../journal";
import { VehicleUsage, VehicleUsageService } from "../vehicle-usage";
import {
    RefPack,
    RefPackService,
    Operator,
    ApecSettings,
    SinisterTypeSetting,
} from "../ref-pack";
import { RaisonPecService, RaisonPec } from "../raison-pec";
import { RefModeGestion } from "../ref-mode-gestion";
import { VatRate, VatRateService } from "../vat-rate";
import { StampDutyService } from "../stamp-duty/stamp-duty.service";
import { StampDuty } from "../stamp-duty/stamp-duty.model";
import { AccordPriseCharge } from "./accord-prise-charge.model";
import { QuotationMP, QuotationMPService } from "../quotation-m-p";
import { Assure, AssureService } from "../assure";
import * as Stomp from "stompjs";
import { HistoryPopupDetailsPec } from "../sinister-pec/historyPopupDetailsPec.component";
import { ApecService, Apec } from "../apec";
import { saveAs } from "file-saver/FileSaver";
import { VehiclePiece } from "../vehicle-piece/vehicle-piece.model";
import { VehiclePieceService } from "../vehicle-piece/vehicle-piece.service";
import { ViewDetailQuotation } from "./view-detail-quotation.model";
import { ViewDetailQuotationService } from "./view-detail-quotation.service";
import { RefNotifAlert } from "../ref-notif-alert/ref-notif-alert.model";
import { NotifAlertUser } from "../notif-alert-user/notif-alert-user.model";
import { UserExtra, UserExtraService } from "../user-extra";
import { NotifAlertUserService } from "../notif-alert-user/notif-alert-user.service";
import { TypeaheadMatch } from "ngx-bootstrap";
import { NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { QuotationPieceAddComponent } from "./quotation-piece-add.component";
import { Convention } from "../convention/convention.model";
import { ConventionService } from "../convention/convention.service";
import { DetailsQuotationService } from "../details-quotation/details-quotation.service";
import { DetailsQuotation } from "../details-quotation/details-quotation.model";

@Component({
    selector: "jhi-devis-dialog",
    templateUrl: "./devis-dialog.component.html",
    providers: [GAEstimateService],
    styles: [
        " th  { position: sticky; top: 0;background-color:#1d96a7!important;color: white !important; font-family: Helvetica, sans-serif !important ;} table td { padding: 10px;} select:disabled ,button, button:disabled{  opacity: 1;}",
    ],
})
export class DevisDialogComponent implements OnInit ,OnChanges{
    @ViewChild("myFrame")
    myFrame: ElementRef;
    quotationSettings = {};

    // Pieces list
    refOperationTypesMo: RefTypeIntervention[];
    refOperationTypesPeinture: RefTypeIntervention[];
    refOperationTypesFourniture: RefTypeIntervention[];

    // -------------------------------
    primaryQuotation: Quotation = new Quotation();
    devis: Devis = new Devis();
    refPack: RefPack = new RefPack();
    quotation: Quotation = new Quotation();
    quotationCP: Quotation = new Quotation();
    quotationMP: QuotationMP = new QuotationMP();
    isSaving: boolean;
    isModeSaisie: boolean = false;
    viewGaDigits: boolean = true;
    addNewPiece: boolean = false;
    observation = new Observation();
    detailsQuote: DetailsQuotation = new DetailsQuotation();
    attachments: Attachment[] = [];
    sinisterPec: SinisterPec = new SinisterPec();
    pecQuotation: SinisterPec = new SinisterPec();
    AvisExpert: boolean = false;
    faceAvantPreview: any;
    lateralGauchePreview: any;
    lateralDroitPreview: any;
    expertObservations = [];
    expertObservationsPF = [];
    validationDevis = false;
    dossier: Sinister = new Sinister();
    contratAssurance: ContratAssurance = new ContratAssurance();
    vehicule: VehiculeAssure = new VehiculeAssure();
    assure: Assure = new Assure();
    refUsage: VehicleUsage = new VehicleUsage();
    refModeGestion: RefModeGestion = new RefModeGestion();
    vehiculeIsActive = false;
    assureIsActive = false;
    rectfyQuotsWithssucced = false;
    apecSettings: ApecSettings[] = [];
    apecSettingsAvenant: ApecSettings[] = [];
    test = false;
    etat: any = "Refuse_Apres_Facture";
    // Ref list
    moPieces: VehiclePiece[] = [];
    moPiecesF: VehiclePiece[] = [];
    ingredientPieces: VehiclePiece[] = [];
    fourniturePieces: VehiclePiece[] = [];
    filtredPieces: VehiclePiece[] = [];
    isCommentError = false;
    // list of pieces
    quotationTest: Quotation = new Quotation();
    detailsMos: DetailsMo[] = [];
    isCollapsed = false;
    testRevue = false;
    detailsPiece: DetailsPieces = new DetailsPieces();
    detailsPiecesIngredient: DetailsPieces[] = [];
    detailsPiecesIngredientCP: DetailsPieces[] = [];
    detailsPiecesIngredient1: DetailsPieces[] = [];
    detailsPiecesDevis: DetailsPieces[] = [];
    detailsPiecesFourniture: DetailsPieces[] = [];
    detailsPiecesFournitureCP: DetailsPieces[] = [];
    detailsPiecesFourniture1: DetailsPieces[] = [];
    detailsPiecesMO: DetailsPieces[] = [];
    detailsPiecesMOCP: DetailsPieces[] = [];
    detailsPiecesMO1: DetailsPieces[] = [];
    MoDetails: DetailsPieces[] = [];
    detailsPieces: DetailsPieces[] = [];
    detailsPiecesCP: DetailsPieces[] = [];
    detailsPieces1: DetailsPieces[] = [];
    detailsPiecesMP: DetailsPieces[] = [];
    detailsPiecesFournitureMP: DetailsPieces[] = [];
    detailsPiecesIngredientMP: DetailsPieces[] = [];
    detailsPiecesMOTest: DetailsPieces[] = [];
    detailsPiecesIngredientTest: DetailsPieces[] = [];
    detailsPiecesTest: DetailsPieces[] = [];
    detailsPiecesFournitureTest: DetailsPieces[] = [];
    lastListPiecesModified: DetailsPieces[] = [];
    lastDetailsPieces: DetailsPieces[] = [];
    lastDetails: DetailsPieces[] = [];
    iMoRow: number;
    sinisterTypeSettings: SinisterTypeSetting[] = [];
    sinisterTypeSettingsAvenant: SinisterTypeSetting[] = [];
    isObsModeEdit = false;
    isPcsModeEdit = false;
    modeEdit = false;
    detailsMo: DetailsMo = new DetailsMo();
    detailsPieceMo: DetailsPieces = new DetailsPieces();
    iPaintRow: number;
    grille: Grille = new Grille();
    lastDevis: any;
    showConstatAttachment: boolean = true;
    reparateur: Reparateur = new Reparateur();
    estimation: Estimation = new Estimation();
    expert: any;
    pec: any;
    observations: Observation[] = [];
    piecesJointes: PieceJointe[] = [];
    piecesAttachments: Attachment[] = [];
    piecesAttachmentsExpertise: Attachment[] = [];
    piecesAvantReparationAttachments: Attachment[] = [];
    pointChocExpertiseAttachments: Attachment[] = [];
    result: boolean;
    devisAccepte: any;
    devisPreview: any;
    devisValide: any;
    devisSaved: any;
    nbrReports: number;
    testerrr = false;
    DevisComplementary: boolean = false;
    expertMissionary: boolean = false;
    receiveVehiculeAdd: boolean = false;
    obserExpert: boolean;
    obserExpertQuoteCanceled = false;
    nbmissionExpert: number;
    piece: any;
    verifExport: any;
    status: any;
    motif: any;
    totalTtc = 0;
    moTotalTtc = 0;
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
    label1: string;
    label2: string;
    label3: string;
    label4: string;
    label5: string;
    label6: string;
    label7: string;
    label8: string;
    ttcAmountDevis: number;
    constatPreview = false;
    pieceJointe: PieceJointe;
    pj: number;
    oneClickForButton10: boolean = true;
    deleteDetailsMo: boolean = false;
    deletedpIngredient: boolean = true;
    deletedpRechange: boolean = true;
    resTotalHt = 0;
    resTotalTtc = 0;
    refusagecontrats: VehicleUsage[] = [];
    ajoutNouvelpieceExpertise = false;
    ajoutNouvelpiece = false;
    pieceJ: PieceJointe = new PieceJointe();
    PieceJointes: PieceJointe[] = [];
    deletePiece = false;
    dateExpertiseDp: any;
    detailsM: any;
    //devisFinded: Devis = new Devis();
    pecAnnule = false;
    gaestimateUrl: SafeResourceUrl;
    gaestimateLink: string;
    useGtEstimate = false;
    useGt = false;
    demontage: any;
    modeReparationLeger: any;
    model: NgbDateStruct;
    dateString: string;
    oneClickForButton9: boolean = true;
    myDate: any;
    selectedFiles: FileList;
    selectedFileList: File[] = [];
    ajoutPiece = false;
    signedAgreementPreview = false;
    signedAgreementUrl: any;
    currentFileUpload: File;
    selectedDescriptionList: String[] = [];
    imgFaceAvantGauche: String;
    imgFaceAvantDroit: String;
    imgFaceArriereDroit: String;
    imgFaceArriereGauche: String;
    isQuoteEntry: boolean = false;
    imgNSerie: String;
    imgImmatriculation: String;
    imgCompteur: String;
    imgCarteGrise: String;
    oneClickForButton8: boolean = true;
    imgFinition: String;
    refMotif = new RefMotif();
    motifs: RaisonPec[] = [];
    dropdownSettings = {};
    idMotif: number;
    ajoutNouvelpieceformExpertise = false;
    ajoutNouvelpieceform = false;
    actifModeAvisExpert = false;
    modeConfirmation = false;
    observationDetailPiece = "ORIGINE";
    confirme: any = null;
    userReparateur: any;
    oneClickForButton7: boolean = true;
    oneClickForButton77: boolean = true;
    SysAction: any;
    fileStorageUrl: any;
    url: any;
    formdata: FormData = new FormData();
    sinister: Sinister = new Sinister();
    listVat: VatRate[];
    totalDiscount: number;
    totalAfterDiscount: number = 0;
    activeStamp: StampDuty;
    apec: Apec = new Apec();
    apecMprix: Apec = new Apec();
    oneClickForButton6: boolean = true;
    // add by Helmi
    testModeID = false;
    verif = false;
    piecePreview1 = false;
    statusChange = false;
    expertLandExpertOpinion = false;
    avisExpertExpertiseTerrain = false;
    avisExpertReconstitution = false;
    demantageExpertOpinion = false;
    reconstitutionAvisExpertDemantage = false;
    avisExpertdevisDemantage = false;
    demantageExpertCirconstanceVerifExpertiseTerrain = false;
    avisExpertExpertiseTerrainDemantage = false;
    receiveVehicule = false;
    quoteValidation = false;
    quoteRectify = false;
    oneClickForButton5: boolean = true;
    selectedGouvernorat: number;
    ws: any;
    insured: Assure = new Assure();
    reasonCancelExpertId: number;
    notif: string;
    private ngbModalRef: NgbModalRef;
    accord: AccordPriseCharge = new AccordPriseCharge();
    isConfirmationModificationPrix = false;
    isModificationPrix: boolean = false;
    isNvModif: boolean = true;
    RevueValidationDevis: boolean = false;
    additionalQuote: boolean = false;
    expertDecision?: string;
    oneClickForButton4: boolean = true;
    gtShow = false;
    labelPointChoc1: String = "Point Choc 1";
    labelPointChoc2: String = "Point Choc 2";
    labelPointChoc3: String = "Point Choc 3";
    labelPointChoc4: String = "Point Choc 4";
    labelFaceAvantDroit: String = "Face Avant Droit";
    labelFaceAvantGauche: String = "Face Avant Gauche";
    labelFaceArriereDroit: String = "Face Arriere Droit";
    labelFaceArriereGauche: String = "Face Arriere Gauche";
    labelFinition: String = "Finition";
    labelNSerie: String = "NSerie";
    labelImmatriculation: String = "Immatriculation";
    labelCompteur: String = "Compteur";
    labelCarteGrise: String = "Carte Grise Quotation";
    labelGT: String = "GTEstimate";
    labelAccordNormal: String = "AccordNormal";
    labelAccordMP: String = "ModificationPrix";
    labelAccordAnnule: String = "AccordAnnule";
    faceAvantDroitFiles: File;
    pointChoc4Files: File;
    pointChoc3Files: File;
    pointChoc2Files: File;
    pointChoc1Files: File;
    faceAvantGaucheFiles: File;
    faceArriereDroitFiles: File;
    faceArriereGaucheFiles: File;
    finitionFiles: File;
    nSerieFiles: File;
    immatriculationFiles: File;
    compteurFiles: File;
    carteGriseFiles: File;
    gtFiles: File;
    listesPiecesJointes: File[] = [];
    showBlockGTRapport = false;
    showVetuste = false;
    oneClickForButton3: boolean = true;
    pointChoc4Attachment: Attachment = new Attachment();
    pointChoc3Attachment: Attachment = new Attachment();
    pointChoc2Attachment: Attachment = new Attachment();
    pointChoc1Attachment: Attachment = new Attachment();
    faceAvantDroitAttachment: Attachment = new Attachment();
    faceAvantGaucheAttachment: Attachment = new Attachment();
    faceArriereDroitAttachment: Attachment = new Attachment();
    faceArriereGaucheAttachment: Attachment = new Attachment();
    finitionAttachment: Attachment = new Attachment();
    nSerieAttachment: Attachment = new Attachment();
    immatriculationAttachment: Attachment = new Attachment();
    compteurAttachment: Attachment = new Attachment();
    carteGriseAttachment: Attachment = new Attachment();
    gtAttachment: Attachment = new Attachment();
    oneClickForButton11: boolean = true;
    oneClickForButton18: boolean = true;
    oneClickForButton19: boolean = true;
    oneClickForButton13: boolean = true;
    oneClickForButton14: boolean = true;
    oneClickForButton15: boolean = true;
    oneClickForButton16: boolean = true;
    oneClickForButton17: boolean = true;
    pointChoc4Preview = false;
    pointChoc3Preview = false;
    pointChoc2Preview = false;
    pointChoc1Preview = false;
    faceAvantDroitPreview = false;
    faceAvantGauchePreview = false;
    faceArriereDroitPreview = false;
    showFaceAvantDroit = false;
    showFaceAvantGauche = false;
    showFaceArriereDroit = false;
    updatePieceJointe1 = false;
    faceArriereGauchePreview = false;
    showFaceArriereGauche = false;
    oneClickForButton12: boolean = true;
    finitionPreview = false;
    showFinition = false;
    immatriculationPreview = false;
    showImmatriculation = false;
    compteurPreview = false;
    showCompteur = false;
    nSeriePreview = false;
    showNSerie = false;
    oneClickForButton2: boolean = true;
    carteGrisePreview = false;
    gtPreview = false;
    showCarteGrise = false;
    selectedItem: boolean = true;
    updatePointChoc4 = false;
    updatePointChoc3 = false;
    updatePointChoc2 = false;
    updatePointChoc1 = false;
    updateFaceAvantDroit = false;
    updateFaceAvantGauche = false;
    updateFaceArriereDroit = false;
    updateFinition = false;
    updateImmatriculation = false;
    updateCompteur = false;
    updateNSerie = false;
    updateFaceArriereGauche = false;
    updateCarteGrise = false;
    showPieceAttachment = false;
    showPieceAttachmentExpertise = false;
    updateGT = false;
    isReceptVeh = false;
    showPointChoc4Attachment = true;
    showPointChoc3Attachment = true;
    showPointChoc2Attachment = true;
    showPointChoc1Attachment: boolean = true;
    showFaceAvantDroitAttachment: boolean = true;
    showPointChoc4 = false;
    showPointChoc3 = false;
    showPointChoc2 = false;
    showPointChoc1 = false;
    showFaceAvantGaucheAttachment: boolean = true;
    showFaceArriereDroitAttachment: boolean = true;
    showFaceArriereGaucheAttachment: boolean = true;
    showFinitionAttachment: boolean = true;
    showImmatriculationAttachment: boolean = true;
    showCompteurAttachment: boolean = true;
    showNSerieAttachment: boolean = true;
    showCarteGriseAttachment: boolean = true;
    showGTAttachment: boolean = true;
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    userExNotifAgency: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];
    userExNotifExpert: UserExtra[] = [];
    userExNotifReparateur: UserExtra[] = [];
    currentAccount: any;
    isLightShock: boolean;
    isMaJDevis: boolean = false;
    public selectedName: any;
    detailQuotation: ViewDetailQuotation = new ViewDetailQuotation();
    //p: PiecePipe =  new PiecePipe();
    oneClickForButton1: boolean = true;
    userExNotif: UserExtra[] = [];
    oneClickForButtonAvis: boolean = true;
    userExtra: UserExtra;
    responsableControleTechnique: UserExtra = new UserExtra();
    apecNotif: Apec = new Apec();
    oneClickForButton20: boolean = true;
    codepiece: String;
    public model1: any;
    constatFiles: File;
    reference: any;
    selectedReference: string = "";
    selectedDesignation: string = "";
    piecesFiltred: DetailsPieces;
    pieceAttachment1: Attachment = new Attachment();
    labelPiece1: string;
    piecesFilesExpertise: File[] = [];
    piecesFiles: File[] = [];
    pieceFiles1: File;
    demontageGT: any;
    oneClickForButton21: boolean = true;
    oneClickForButton22: boolean = true;
    oneClickForButton23: boolean = true;
    oneClickForButton24: boolean = true;
    oneClickForButton25: boolean = true;
    oneClickForButton26: boolean = true;
    oneClickForButton27: boolean = true;
    oneClickForButton28: boolean = true;
    oneClickForButton29: boolean = true;
    oneClickForButton30: boolean = true;
    oneClickForButton31: boolean = true;
    oneClickForButton32: boolean = true;
    oneClickForButton33: boolean = true;
    isOther: boolean = false;
    oneClickForButton: boolean = true;
    showButtonSaveAttachments = false;
    enableSaveExpertise = false;
    enableSaveSoumettre = false;
    isReceptionVehicule = false;
    isDemontageVehicule = false;
    validDevis = false;
    notPf = false;
    sommeQuotation = 0;
    testConfirmDevis = false;
    testValidDevis = false;
    apecMAJ: Apec = new Apec();
    listModeGestion: any[] = [];
    listModeGestionAvenant: any[] = [];
    extensionImage: string;
    extensionImageOriginal: string;
    detailsPiecesCopy: DetailsPieces[] = [];
    nomImage: string;
    testAvExp = false;
    existModeValidation = false;
    observationExpertColumn: boolean = true;
    conditionTest: boolean = true;
    buttonExpert: boolean = true;
    buttonValid: boolean = false;
    buttonVerif: boolean = true;
    buttonConfirm: boolean = false;
    conditionDemontage: boolean = true;
    buttonConfirmModifPrix: boolean = true;
    existMode = false;
    gtestimateId: number = 0;
    quotationId: number = 0;
    i: number = 0;
    observationss: Observation[] = [];
    confirmationDevisComplementaire = false;
    rectifyOK = false;
    showRemoveLine = false;
    blockAddQuotation = false;
    showAlert = false;
    constructor(
        private alertService: JhiAlertService,
        private detailsPiecesService: DetailsPiecesService,
        private reparateurService: ReparateurService,
        private dossierService: SinisterService,
        private refMotifService: RefMotifService,
        private vehiculeAssureService: VehiculeAssureService,
        private quotationService: QuotationService,
        private refTypeInterventionService: RefTypeInterventionService,
        private contratAssuranceService: ContratAssuranceService,
        private route: ActivatedRoute,
        private principal: Principal,
        private owerDateUtils: DateUtils,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private router: Router,
        private vehicleUsageService: VehicleUsageService,
        private confirmationDialogService: ConfirmationDialogService,
        private sanitizer: DomSanitizer,
        private evtManager: EventManager,
        private ngbDateParserFormatter: NgbDateParserFormatter,
        private reasonPecService: RaisonPecService,
        private sinisterPecService: SinisterPecService,
        private vatRateService: VatRateService,
        private stampDutyService: StampDutyService,
        private apecService: ApecService,
        private quotationMPService: QuotationMPService,
        private insuredService: AssureService,
        private refPackservice: RefPackService,
        private vehiclePieceService: VehiclePieceService,
        private viewDetailQuotationService: ViewDetailQuotationService,
        private notificationAlerteService: NotifAlertUserService,
        private userExtraService: UserExtraService,
        private sinisterPecPopupService: SinisterPecPopupService,
        private conventionService: ConventionService,
        private observationService: ObservationService,
        private dateUtils: JhiDateUtils,
        private detailsQuotationService: DetailsQuotationService
    ) {}
    ngOnChanges(changes: SimpleChanges) {
        if (changes["detailsPieces"]) {
            this.detailsPieces = this.detailsPieces;
        }

      
    }
    openAddPiece(detailsPieceMo, type: number) {
        let vehiclePiece = new VehiclePiece();
        vehiclePiece.id = detailsPieceMo.designationId;
        vehiclePiece.reference = detailsPieceMo.designationReference;
        vehiclePiece.label = detailsPieceMo.designation;
        vehiclePiece.active = true;
        vehiclePiece.typeId = type;
        vehiclePiece.source = "REPARATOR";

        this.ngbModalRef = this.sinisterPecPopupService.openAddPieceModal(
            QuotationPieceAddComponent as Component,
            vehiclePiece
        );
        this.ngbModalRef.result.then(
            (result: any) => {
                if (result !== null && result !== undefined) {
                    console.log(result);
                    if (result.isNew == true) {
                        detailsPieceMo.isNew = true;
                    }
                    detailsPieceMo.reference = result.id;
                    detailsPieceMo.designationId = result.id;
                    detailsPieceMo.designation = result.label;
                    detailsPieceMo.designationReference = result.reference;
                }
                this.ngbModalRef = null;
            },
            (reason) => {
                // TODO: print error message
                console.log(
                    "______________________________________________________2"
                );
                this.ngbModalRef = null;
            }
        );
    }

    // pieces de rechange
    selectPieceCode(pieces: DetailsPieces) {
        if (pieces != null && pieces != undefined) {
            if (
                pieces.designationReference !== null &&
                pieces.designationReference !== undefined
            ) {
                this.vehiclePieceService
                    .getPieceByReferenceRef(pieces.designationReference, 1)
                    .subscribe((res: VehiclePiece) => {
                        pieces.reference = res.id;
                        pieces.designationId = res.id;
                        pieces.designation = res.label;
                    });
            }
        }
    }
    selectTapedPieceCode(pieces: DetailsPieces) {
        this.moPieces = [];
        pieces.designationId = null;
        if (
            pieces.designationReference !== null &&
            pieces.designationReference !== undefined &&
            pieces.designationReference.length >= 3
        ) {
            this.vehiclePieceService
                .getByTypeAndTapedReference(pieces.designationReference, 1)
                .subscribe((res: VehiclePiece[]) => {
                    this.moPieces = res;
                });
        }
    }
    selectPieceDesignation(pieces: DetailsPieces) {
        if (pieces != null && pieces != undefined) {
            if (
                pieces.designation !== null &&
                pieces.designation !== undefined
            ) {
                this.vehiclePieceService
                    .getPieceByDesignationIsPresent(pieces.designation, 1)
                    .subscribe((isPresent: Boolean) => {
                        if (isPresent == true) {
                            this.vehiclePieceService
                                .getPieceByDesignation(pieces.designation, 1)
                                .subscribe((res: VehiclePiece) => {
                                    pieces.designationId = res.id;
                                    pieces.reference = res.id;
                                    pieces.designationReference = res.code;
                                });
                        } else {
                            this.openAddPiece(pieces, 1);
                        }
                    });
            }
        }
    }
    selectTapedPieceDesignation(pieces: DetailsPieces) {
        this.moPieces = [];
        pieces.designationId = null;
        if (
            pieces.designation !== null &&
            pieces.designation !== undefined &&
            pieces.designation.length >= 3
        ) {
            this.vehiclePieceService
                .getByTypeAndTapedDesignation(pieces.designation, 1)
                .subscribe((res: VehiclePiece[]) => {
                    this.moPieces = res;
                    this.verif = true;
                });
        }
    }
    // Main d'oeuvre
    selectPieceMoDesignation(detailsPieceMo: DetailsPieces) {
        if (detailsPieceMo != null && detailsPieceMo != undefined) {
            if (
                detailsPieceMo.designation !== null &&
                detailsPieceMo.designation !== undefined
            ) {
                this.vehiclePieceService
                    .getPieceByDesignationAutoComplete(
                        detailsPieceMo.designation,
                        1
                    )
                    .subscribe((res: VehiclePiece) => {
                        detailsPieceMo.designationId = res.id;
                        detailsPieceMo.designation = res.label;
                    });
            }
        }
    }
    selectTapedPieceMoDesignation(detailsPieceMo: DetailsPieces) {
        this.moPieces = [];
        detailsPieceMo.designationId = null;
        if (
            detailsPieceMo.designation !== null &&
            detailsPieceMo.designation !== undefined &&
            detailsPieceMo.designation.length >= 3
        ) {
            this.vehiclePieceService
                .getByTypeAndTapedDesignation(detailsPieceMo.designation, 1)
                .subscribe((res: VehiclePiece[]) => {
                    this.moPieces = res;
                });
            this.verif = true;
        }
    }
    // pieces peinture
    selectPiecePeDesignation(dpIngredient: DetailsPieces) {
        if (dpIngredient != null && dpIngredient != undefined) {
            if (
                dpIngredient.designation !== null &&
                dpIngredient.designation !== undefined
            ) {
                this.vehiclePieceService
                    .getPieceByDesignationAutoComplete(
                        dpIngredient.designation,
                        2
                    )
                    .subscribe((res: VehiclePiece) => {
                        dpIngredient.designationId = res.id;
                        dpIngredient.designation = res.label;
                    });
            }
        }
    }
    selectTapedPiecePeDesignation(dpIngredient: DetailsPieces) {
        this.ingredientPieces = [];
        dpIngredient.designationId = null;
        if (
            dpIngredient.designation !== null &&
            dpIngredient.designation !== undefined &&
            dpIngredient.designation.length >= 3
        ) {
            this.vehiclePieceService
                .getByTypeAndTapedDesignation(dpIngredient.designation, 2)
                .subscribe((res: VehiclePiece[]) => {
                    this.ingredientPieces = res;
                });

            this.verif = true;
        }
    }
    // pieces Fourniture
    selectPieceFoDesignation(fourniture: DetailsPieces) {
        if (fourniture != null && fourniture != undefined) {
            if (
                fourniture.designation !== null &&
                fourniture.designation !== undefined
            ) {
                this.vehiclePieceService
                    .getPieceByDesignationAutoComplete(
                        fourniture.designation,
                        3
                    )
                    .subscribe((res: VehiclePiece) => {
                        fourniture.designationId = res.id;
                        fourniture.designation = res.label;
                    });
            }
        }
    }
    selectTapedPieceFoDesignation(fourniture: DetailsPieces) {
        this.fourniturePieces = [];
        fourniture.designationId = null;
        if (
            fourniture.designation !== null &&
            fourniture.designation !== undefined &&
            fourniture.designation.length >= 3
        ) {
            this.vehiclePieceService
                .getByTypeAndTapedDesignation(fourniture.designation, 3)
                .subscribe((res: VehiclePiece[]) => {
                    this.fourniturePieces = res;
                });
            this.verif = true;
        }
    }

    onConnected() {
        this.ws.subscribe("/topics/event", (message) => {
            if (message.body) {
                console.log("message body");
                const messageParsed = JSON.parse(message.body)[
                    "description"
                ].replace(/]/g, '"');
                let obj = JSON.parse(messageParsed);
                console.log("testTypeNotification " + obj.typenotification);
                if (
                    this.sinisterPec.id !== null &&
                    this.sinisterPec.id !== undefined &&
                    this.estimation.id !== null &&
                    this.estimation.id !== undefined
                ) {
                    if (
                        this.sinisterPec.id == obj.sinisterPecId &&
                        this.estimation.id == obj.quotationId &&
                        obj.typenotification == "gtEstimateSendEvent"
                    ) {
                        this.useGtEstimate = false;
                        this.viewGaDigits = true;
                        this.enregistre();
                    }
                }
            }
        });
    }

    connect() {
        //connect to stomp where stomp endpoint is exposed
        console.log("Socket init start _____________________" + new Date());
        let sockets = new WebSocket(
            "wss://notif.gadigits.com:8443/my-ws/websocket"
        );
        this.ws = Stomp.over(sockets);
        //this.ws.connect({}, this.onConnected.bind(this), this.onError.bind(this));
        //sendFirstConnectionEventForGtEstimate();
    }

    ngOnInit() {
        // Init socket for sending notification
        this.connect();
        console.log("Socket init end _____________________" + new Date());
        // Get connected user
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService
                .finPersonneByUser(this.currentAccount.id)
                .subscribe((res) => {
                    this.userExtra = res;
                    this.responsableControleTechnique = res;
                });
        });

        this.sinisterPec = new SinisterPec();

        // Get referential list
        // Get vehicle usage list
        this.vehicleUsageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.refusagecontrats = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        // Get piece lists by type
        this.refTypeInterventionService
            .findByType(TypeInterventionQuotation.INTERVENTION_MO)
            .subscribe(
                (res: RefTypeIntervention[]) => {
                    this.refOperationTypesMo = res;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        this.refTypeInterventionService
            .findByType(TypeInterventionQuotation.INTERVENTION_PEINTURE)
            .subscribe(
                (res: RefTypeIntervention[]) => {
                    this.refOperationTypesPeinture = res;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        this.refTypeInterventionService
            .findByType(TypeInterventionQuotation.INTERVENTION_FOURNITURE)
            .subscribe(
                (res: RefTypeIntervention[]) => {
                    this.refOperationTypesFourniture = res;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );

        this.vatRateService.query().subscribe((res) => {
            this.listVat = res.json;
        });

        this.init();

        this.model = this.setDefaultDate();
        this.onSelectDate(this.model);

        this.stampDutyService.findActiveStamp().subscribe((res) => {
            this.activeStamp = res;
            this.quotation.stampDuty = this.activeStamp.amount;
            this.quotationMP.stampDuty = this.activeStamp.amount;
        });

        this.expertObservations = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 3, label: "A Réparer" },
            { id: 4, label: "A Remplacer" },
            { id: 5, label: "Accordé avec Modification" },
        ];

        this.expertObservationsPF = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 5, label: "Accordé avec Modification" },
        ];
    }

    init() {
        this.route.params.subscribe((params) => {
            //Revue validation  devis
            if (params["idValidationDevisPrestationPec"]) {
                // idValidationDevisPrestationPec : prestation pec ID
                const prestationPecId =
                    params["idValidationDevisPrestationPec"];
                console.log(
                    "Revue & quotation validation for PEC " +
                        prestationPecId +
                        "______________________________________________________________________"
                );
                this.validDevis = true;
                this.RevueValidationDevis = true;
                this.isQuoteEntry = true;

                this.getDetailReceptionVehicule(prestationPecId);
                this.reasonPecService
                    .findMotifsByStep(5)
                    .subscribe((subRes) => {
                        this.motifs = subRes;
                    });
                this.sinisterPecService
                    .findPrestationPec(prestationPecId)
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showRemoveLine = true;
                            this.showAttachments();

                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            if (this.sinisterPec.stepId === 34) {
                                this.buttonValid = true;
                            }
                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });

                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = true;
                            this.modeReparationLeger = true;

                            if (
                                this.sinisterPec.listComplementaryQuotation
                                    .length == 0
                            ) {
                                if (
                                    this.sinisterPec.primaryQuotationId != null
                                ) {
                                    this.getDevis(
                                        this.sinisterPec.primaryQuotationId
                                    );
                                    this.isQuoteEntry = true;
                                }
                            } else {
                                for (
                                    let index = 0;
                                    index <
                                    this.sinisterPec.listComplementaryQuotation
                                        .length;
                                    index++
                                ) {
                                    const complementaryQuotation = this
                                        .sinisterPec.listComplementaryQuotation[
                                        index
                                    ];
                                    if (complementaryQuotation.statusId == 4) {
                                        this.additionalQuote = true;
                                        this.getDevis(
                                            complementaryQuotation.id
                                        );
                                        this.isQuoteEntry = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }

            //Vérification devis
            if (params["idVerificationPec"]) {
                const prestationPecId = params["idVerificationPec"];
                console.log(
                    "Quotation verification for PEC " +
                        prestationPecId +
                        "______________________________________________________________________"
                );
                this.quoteValidation = true;
                this.quoteRectify = true;
                this.isQuoteEntry = true;
                this.reasonPecService
                    .findMotifsByStep(5)
                    .subscribe((subRes) => {
                        this.motifs = subRes;
                    });

                this.getDetailReceptionVehicule(prestationPecId);
                // affiche devis
                this.isCollapsed = true;
                this.modeEdit = true;
                this.sinisterPecService
                    .findPrestationPec(prestationPecId)
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showRemoveLine = true;
                            this.showAttachments();
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            if (this.sinisterPec.stepId !== 26) {
                                this.buttonVerif = false;
                            }
                            this.dossierService
                                .find(this.sinisterPec.sinisterId)
                                .subscribe((res) => {
                                    this.dossier = res;
                                    this.vehiculeAssureService
                                        .find(this.dossier.vehicleId)
                                        .subscribe((res) => {
                                            this.vehicule = res;
                                            this.findInsured();
                                            this.contratAssuranceService
                                                .find(this.vehicule.contratId)
                                                .subscribe((res) => {
                                                    this.contratAssurance = res;
                                                });
                                        });
                                });

                            //this.getAttachmentsPec();

                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            //this.getDateReceptionVehicule();
                            if (
                                this.sinisterPec.listComplementaryQuotation
                                    .length == 0
                            ) {
                                if (
                                    this.sinisterPec.primaryQuotationId != null
                                ) {
                                    this.getDevis(
                                        this.sinisterPec.primaryQuotationId
                                    );
                                    this.isQuoteEntry = true;
                                }
                            } else {
                                for (
                                    let index = 0;
                                    index <
                                    this.sinisterPec.listComplementaryQuotation
                                        .length;
                                    index++
                                ) {
                                    const complementaryQuotation = this
                                        .sinisterPec.listComplementaryQuotation[
                                        index
                                    ];
                                    if (complementaryQuotation.statusId == 4) {
                                        this.additionalQuote = true;
                                        this.getDevis(
                                            complementaryQuotation.id
                                        );
                                        this.isQuoteEntry = true;
                                        break;
                                    }
                                }
                            }
                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            //complementary devis
            if (params["prestationDevisComplementaryId"]) {
                console.log(" devis complementaire ");
                this.DevisComplementary = true;
                this.isQuoteEntry = true;
                this.viewGaDigits = true;
                this.enableSaveSoumettre = true;
                this.receiveVehiculeAdd = true;
                this.getDetailReceptionVehicule(
                    params["prestationDevisComplementaryId"]
                );
                this.sinisterPecService
                    .findPrestationPec(params["prestationDevisComplementaryId"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showAttachments();
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                            this.sinisterPec.oldStepNw = 16;
                            this.sinisterPec.testDevisCmpl = false;
                            //this.getDateReceptionVehicule();
                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = false;
                            this.modeReparationLeger = true;
                            if (params["apecId"]) {
                                this.apecService
                                    .find(params["apecId"])
                                    .subscribe((res) => {
                                        this.apec = res;
                                    });
                            }
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            //confirmation devis
            if (params["idConfirmePec"]) {
                console.log("confirmation devis");
                this.modeConfirmation = true;
                this.isQuoteEntry = true;
                this.reasonPecService
                    .findMotifsByStep(5)
                    .subscribe((subRes) => {
                        this.motifs = subRes;
                    });
                this.getDetailReceptionVehicule(params["idConfirmePec"]);
                this.sinisterPecService
                    .findPrestationPec(params["idConfirmePec"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showAttachments();
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            console.log(
                                "confirmation devis" + this.sinisterPec.stepId
                            );

                            if (this.sinisterPec.stepId === 17) {
                                this.buttonConfirm = true;
                            }

                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = true;
                            this.modeReparationLeger = true;
                            if (
                                this.sinisterPec.listComplementaryQuotation
                                    .length == 0
                            ) {
                                if (
                                    this.sinisterPec.primaryQuotationId != null
                                ) {
                                    this.getDevis(
                                        this.sinisterPec.primaryQuotationId
                                    );
                                    this.isQuoteEntry = true;
                                }
                            } else {
                                for (
                                    let index = 0;
                                    index <
                                    this.sinisterPec.listComplementaryQuotation
                                        .length;
                                    index++
                                ) {
                                    const complementaryQuotation = this
                                        .sinisterPec.listComplementaryQuotation[
                                        index
                                    ];
                                    if (complementaryQuotation.statusId == 4) {
                                        this.additionalQuote = true;
                                        this.getDevis(
                                            complementaryQuotation.id
                                        );
                                        this.isQuoteEntry = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            //confirmation devis complementaire
            if (params["idConfirmePecComplementaire"]) {
                console.log("confirmation devis");
                this.modeConfirmation = true;
                this.confirmationDevisComplementaire = true;
                this.isQuoteEntry = true;
                this.reasonPecService
                    .findMotifsByStep(7)
                    .subscribe((subRes) => {
                        this.motifs = subRes;
                    });
                this.getDetailReceptionVehicule(
                    params["idConfirmePecComplementaire"]
                );
                this.sinisterPecService
                    .findPrestationPec(params["idConfirmePecComplementaire"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showAttachments();
                            this.sinisterPec.testDevisCmpl = false;

                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            console.log(
                                "confirmation devis" + this.sinisterPec.stepId
                            );
                            this.sinisterPec.oldStepNw = 16;

                            if (this.sinisterPec.stepId === 16) {
                                this.buttonConfirm = true;
                            }

                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = true;
                            this.modeReparationLeger = true;
                            if (this.sinisterPec.preliminaryReport) {
                                if (
                                    this.sinisterPec.primaryQuotationId != null
                                ) {
                                    this.getDevis(
                                        this.sinisterPec.primaryQuotationId
                                    );
                                    this.isQuoteEntry = true;
                                    this.modeEdit = true;
                                }
                            } else {
                                if (
                                    this.sinisterPec.listComplementaryQuotation
                                        .length == 0
                                ) {
                                    if (
                                        this.sinisterPec.primaryQuotationId !=
                                        null
                                    ) {
                                        this.getDevis(
                                            this.sinisterPec.primaryQuotationId
                                        );
                                        this.isQuoteEntry = true;
                                    }
                                } else {
                                    for (
                                        let index = 0;
                                        index <
                                        this.sinisterPec
                                            .listComplementaryQuotation.length;
                                        index++
                                    ) {
                                        const complementaryQuotation = this
                                            .sinisterPec
                                            .listComplementaryQuotation[index];
                                        if (
                                            complementaryQuotation.statusId == 4
                                        ) {
                                            this.additionalQuote = true;
                                            this.getDevis(
                                                complementaryQuotation.id
                                            );
                                            this.isQuoteEntry = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            //Démontage
            if (params["idSinisterPecDemontage"]) {
                console.log("Démontage");
                this.getDetailReceptionVehicule(
                    params["idSinisterPecDemontage"]
                );
                this.sinisterPecService
                    .findPrestationPec(params["idSinisterPecDemontage"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showButtonSaveAttachments = true;
                            this.showAttachments();
                            this.enableSaveSoumettre = true;
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            if (this.sinisterPec.stepId != 21) {
                                this.conditionDemontage = false;
                            }

                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            this.isModeSaisie = true;
                            this.isQuoteEntry = true;
                            this.isDemontageVehicule = true;
                            this.receiveVehicule = true;
                            this.demontageGT = true;
                            this.getDateReceptionVehicule();
                            if (this.sinisterPec.expertId) {
                                this.nbreMissionByExpert(
                                    this.sinisterPec.expertId
                                );
                            }
                            if (
                                this.sinisterPec.primaryQuotationId != null &&
                                this.sinisterPec.primaryQuotationId !==
                                    undefined
                            ) {
                                this.getDevis(
                                    this.sinisterPec.primaryQuotationId
                                );
                                this.isQuoteEntry = true;
                                this.modeEdit = true;
                            } else if (
                                this.sinisterPec.quoteId !== null &&
                                this.sinisterPec.quoteId !== undefined
                            ) {
                                this.getDevis(this.sinisterPec.quoteId);
                                this.isQuoteEntry = true;
                                this.modeEdit = true;
                            }
                            this.dossierService
                                .find(this.sinisterPec.sinisterId)
                                .subscribe((res) => {
                                    this.dossier = res;
                                    this.vehiculeAssureService
                                        .find(this.dossier.vehicleId)
                                        .subscribe((res) => {
                                            this.vehicule = res;
                                            this.findInsured();
                                            this.contratAssuranceService
                                                .find(this.vehicule.contratId)
                                                .subscribe((res) => {
                                                    this.contratAssurance = res;
                                                });
                                        });
                                });
                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            //avis expert
            if (params["idPrestationPec"]) {
                this.validationDevis = true;
                this.getDetailReceptionVehicule(params["idPrestationPec"]);
                this.sinisterPecService
                    .findPrestationPec(params["idPrestationPec"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showRemoveLine = true;
                            this.showAttachments();
                            if (this.sinisterPec.stepId == 18) {
                                this.getAttachmentPointChocExpertise();
                                this.getPhotoExpertise();
                            }
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            if (
                                this.sinisterPec.stepId != 20 &&
                                this.sinisterPec.stepId != 18 &&
                                this.sinisterPec.stepId != 19
                            ) {
                                this.buttonExpert = false;
                            }

                            this.AvisExpert = true;
                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            this.getDateReceptionVehicule();
                            this.getAvisTechnique(this.sinisterPec.id);

                            //get devis
                            if (
                                this.sinisterPec.listComplementaryQuotation
                                    .length == 0
                            ) {
                                if (
                                    this.sinisterPec.primaryQuotationId != null
                                ) {
                                    this.getDevis(
                                        this.sinisterPec.primaryQuotationId
                                    );
                                    this.isQuoteEntry = true;
                                } else {
                                    this.quotation.expertiseDate = this.dateAsYYYYMMDDHHNNSS(
                                        new Date()
                                    );
                                }
                            } else {
                                for (
                                    let index = 0;
                                    index <
                                    this.sinisterPec.listComplementaryQuotation
                                        .length;
                                    index++
                                ) {
                                    const complementaryQuotation = this
                                        .sinisterPec.listComplementaryQuotation[
                                        index
                                    ];
                                    if (complementaryQuotation.statusId == 4) {
                                        this.additionalQuote = true;
                                        this.reconstitutionAvisExpertDemantage = true;
                                        this.getDevis(
                                            complementaryQuotation.id
                                        );
                                        this.isQuoteEntry = true;
                                        break;
                                    } else
                                        this.quotation.expertiseDate = this.dateAsYYYYMMDDHHNNSS(
                                            new Date()
                                        );
                                }
                            }
                            //choc léger
                            if (!this.additionalQuote) {
                                if (
                                    this.sinisterPec.lightShock == true &&
                                    this.sinisterPec.stepId == 20
                                ) {
                                    this.expertLandExpertOpinion = true;
                                }
                                if (
                                    this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 18
                                ) {
                                    this.avisExpertExpertiseTerrain = true;
                                }
                                if (
                                    this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 19
                                ) {
                                    this.avisExpertReconstitution = true;
                                }
                                //choc  non léger
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 20 &&
                                    (this.sinisterPec.primaryQuotationId ==
                                        undefined ||
                                        this.sinisterPec.primaryQuotationId ==
                                            null) &&
                                    (this.sinisterPec.expertDecision == null ||
                                        this.sinisterPec.expertDecision ==
                                            undefined)
                                ) {
                                    this.demantageExpertOpinion = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 20 &&
                                    this.sinisterPec.primaryQuotationId !=
                                        null &&
                                    this.sinisterPec.primaryQuotationId !=
                                        undefined
                                ) {
                                    this.avisExpertdevisDemantage = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 19 &&
                                    (this.sinisterPec.expertDecision == null ||
                                        this.sinisterPec.expertDecision ==
                                            undefined)
                                ) {
                                    this.reconstitutionAvisExpertDemantage = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 18 &&
                                    this.sinisterPec.expertDecision ==
                                        "Circonstance à vérifier, Expertise terrain"
                                ) {
                                    this.demantageExpertCirconstanceVerifExpertiseTerrain = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 18 &&
                                    (this.sinisterPec.expertDecision ==
                                        "Épave technique" ||
                                        this.sinisterPec.expertDecision ==
                                            "Épave économique") &&
                                    (this.sinisterPec.primaryQuotationId ==
                                        undefined ||
                                        this.sinisterPec.primaryQuotationId ==
                                            null)
                                ) {
                                    this.demantageExpertCirconstanceVerifExpertiseTerrain = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 19 &&
                                    this.sinisterPec.expertDecision ==
                                        "Reconstitution"
                                ) {
                                    this.demantageExpertCirconstanceVerifExpertiseTerrain = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 20 &&
                                    this.sinisterPec.expertDecision ==
                                        "Circonstance conforme OK pour démontage"
                                ) {
                                    this.avisExpertdevisDemantage = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 18 &&
                                    this.sinisterPec.expertDecision ==
                                        "Expertise terrain"
                                ) {
                                    this.avisExpertExpertiseTerrainDemantage = true;
                                }
                                if (
                                    !this.sinisterPec.lightShock &&
                                    this.sinisterPec.stepId == 18 &&
                                    (this.sinisterPec.expertDecision ==
                                        "Épave technique" ||
                                        this.sinisterPec.expertDecision ==
                                            "Épave économique") &&
                                    this.sinisterPec.primaryQuotationId !==
                                        undefined &&
                                    this.sinisterPec.primaryQuotationId !== null
                                ) {
                                    this.avisExpertExpertiseTerrainDemantage = true;
                                }
                            }
                            this.dossierService
                                .find(this.sinisterPec.sinisterId)
                                .subscribe((res) => {
                                    this.dossier = res;
                                    this.vehiculeAssureService
                                        .find(this.dossier.vehicleId)
                                        .subscribe((res) => {
                                            this.vehicule = res;
                                            this.findInsured();
                                            this.contratAssuranceService
                                                .find(this.vehicule.contratId)
                                                .subscribe((res) => {
                                                    this.contratAssurance = res;
                                                });
                                        });
                                });
                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = true;
                            this.modeReparationLeger = true;
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            //ReceptionVehicule
            if (params["prestationId"]) {
                console.log("first");

                this.isOther = true;
                // Find prestation By ID
                this.receiveVehiculeAdd = true;
                this.showButtonSaveAttachments = true;
                this.getDetailReceptionVehicule(params["prestationId"]);
                this.sinisterPecService
                    .findPrestationPec(params["prestationId"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.getAttachmentAvantReparation();
                            this.getPhotoReparation();
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            if (
                                this.sinisterPec.stepId != 25 &&
                                this.sinisterPec.stepId != 15
                            ) {
                                this.conditionTest == false;
                            }

                            this.isReceptionVehicule = true;
                            if (
                                this.sinisterPec.vehicleReceiptDate == null ||
                                this.sinisterPec.vehicleReceiptDate == undefined
                            ) {
                                this.receiveVehicule = true;
                            }
                            if (
                                this.sinisterPec.stepId == 25 ||
                                this.sinisterPec.stepId == 15
                            ) {
                                this.isReceptVeh = true;
                                this.vehiculeAssureService
                                    .find(this.sinisterPec.vehiculeId)
                                    .subscribe((res) => {
                                        this.vehicule = res;
                                    });
                            }

                            if (
                                this.sinisterPec.stepId ==
                                PrestationPecStep.ACCORD_POUR_REPARATION_AVEC_MODIFICATION
                            ) {
                                this.demontage = true;
                                if (params["id"]) {
                                    this.quotationService
                                        .findjournal(params["id"])
                                        .subscribe((res: Journal) => {
                                            if (res) {
                                                res.motifs.forEach(
                                                    (element) => {
                                                        this.refMotifService
                                                            .find(element.id)
                                                            .subscribe(
                                                                (
                                                                    res: RefMotif
                                                                ) => {
                                                                    this.refMotif = res;
                                                                    this.idMotif =
                                                                        res.id;
                                                                }
                                                            );
                                                    }
                                                );
                                            }
                                        });
                                }
                            }
                            if (this.sinisterPec.modificationPrix) {
                                if (
                                    this.sinisterPec.primaryQuotationId != null
                                ) {
                                    this.getDevis(
                                        this.sinisterPec.primaryQuotationId
                                    );
                                    this.isQuoteEntry = true;
                                    this.modeEdit = true;
                                }
                            } else {
                                if (
                                    this.sinisterPec.listComplementaryQuotation
                                        .length == 0
                                ) {
                                    if (
                                        this.sinisterPec.primaryQuotationId !=
                                        null
                                    ) {
                                        this.getDevis(
                                            this.sinisterPec.primaryQuotationId
                                        );
                                        this.apecService
                                            .findByQuotation(
                                                this.sinisterPec
                                                    .primaryQuotationId
                                            )
                                            .subscribe((apc: Apec) => {
                                                this.apecMAJ = apc;
                                            });
                                        this.isQuoteEntry = true;
                                        this.modeEdit = true;
                                    } else if (
                                        this.sinisterPec.quoteId !== null &&
                                        this.sinisterPec.quoteId !== undefined
                                    ) {
                                        this.getDevis(this.sinisterPec.quoteId);
                                        this.isQuoteEntry = true;
                                        this.modeEdit = true;
                                    }
                                } else {
                                    for (
                                        let index = 0;
                                        index <
                                        this.sinisterPec
                                            .listComplementaryQuotation.length;
                                        index++
                                    ) {
                                        const complementaryQuotation = this
                                            .sinisterPec
                                            .listComplementaryQuotation[index];
                                        if (
                                            complementaryQuotation.statusId == 4
                                        ) {
                                            this.additionalQuote = true;
                                            this.getDevis(
                                                complementaryQuotation.id
                                            );
                                            this.modeEdit = true;
                                            this.apecService
                                                .findByQuotation(
                                                    complementaryQuotation.id
                                                )
                                                .subscribe((apc: Apec) => {
                                                    this.apecMAJ = apc;
                                                });
                                            this.isQuoteEntry = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
            if (params["id"]) {
                //Find quotation By ID

                console.log("second");

                this.notPf = false;
                this.showButtonSaveAttachments = true;
                this.isCollapsed = true;
                this.modeEdit = true;
                this.isMaJDevis = true;

                this.reasonPecService
                    .query()
                    .subscribe((subRes: ResponseWrapper) => {
                        this.motifs = subRes.json;
                    });
                this.validationDevis = true;
                this.changeNatureReparation(true);
                // Get etat de devis selectionné
                //this.getDevis(params['id']);
                this.isQuoteEntry = true;
            }

            //confirmation modification Prix
            if (params["idModificationPrix"]) {
                console.log("modification Prix");
                this.isNvModif = false;
                this.isModificationPrix = true;
                this.showButtonSaveAttachments = true;
                this.isQuoteEntry = true;
                this.getDetailReceptionVehicule(params["idModificationPrix"]);
                this.sinisterPecService
                    .findPrestationPec(params["idModificationPrix"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showAttachments();
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            // this.observations = this.sinisterPec.observations;
                            //this.getAttachmentsPec();
                            this.getPhotoReparation();
                            this.getDateReceptionVehicule();
                            if (
                                this.sinisterPec.vehicleReceiptDate &&
                                !this.sinisterPec.receptionVehicule
                            ) {
                                const date = new Date(
                                    this.sinisterPec.vehicleReceiptDate
                                );
                                this.sinisterPec.vehicleReceiptDate = {
                                    year: date.getFullYear(),
                                    month: date.getMonth() + 1,
                                    day: date.getDate(),
                                };
                            } else {
                                this.myDate = this.sinisterPec.vehicleReceiptDate;
                            }
                            this.dossierService
                                .find(this.sinisterPec.sinisterId)
                                .subscribe((res) => {
                                    this.dossier = res;

                                    this.vehiculeAssureService
                                        .find(this.dossier.vehicleId)
                                        .subscribe((res) => {
                                            this.vehicule = res;
                                            this.findInsured();
                                            this.contratAssuranceService
                                                .find(this.vehicule.contratId)
                                                .subscribe((res) => {
                                                    this.contratAssurance = res;
                                                });
                                        });
                                });
                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = true;
                            this.modeReparationLeger = true;
                            console.log(
                                "hello word quot" +
                                    this.sinisterPec.primaryQuotationId
                            );
                            if (
                                this.sinisterPec.primaryQuotationId != null &&
                                this.sinisterPec.primaryQuotationId != undefined
                            ) {
                                this.quotationMPService
                                    .find(this.sinisterPec.id)
                                    .subscribe((res: QuotationMP) => {
                                        // Find devid By ID
                                        this.quotationMP = res;
                                        this.quotationMP.confirmationDecisionQuote = false;
                                        this.quotation.statusId = this.quotationMP.statusId;
                                        this.quotation.ttcAmount = this.quotationMP.ttcAmount;
                                        this.quotation.stampDuty = this.quotationMP.stampDuty;
                                        this.quotation.htAmount = this.quotationMP.htAmount;
                                        this.quotation.priceNewVehicle = this.quotationMP.priceNewVehicle;
                                        this.quotation.marketValue = this.quotationMP.marketValue;

                                        this.quotation.creationDate = this.quotationMP.creationDate;
                                        this.quotation.expertDecision = this.quotationMP.expertDecision;
                                        this.quotation.revueDate = this.quotationMP.revueDate;
                                        this.quotation.avisExpertDate = this.quotationMP.avisExpertDate;
                                        this.quotation.verificationDevisValidationDate = this.quotationMP.verificationDevisValidationDate;
                                        this.quotation.verificationDevisRectificationDate = this.quotationMP.verificationDevisRectificationDate;
                                        this.quotation.confirmationDevisDate = this.quotationMP.confirmationDevisDate;
                                        this.quotation.miseAJourDevisDate = this.quotationMP.miseAJourDevisDate;
                                        this.quotation.confirmationDevisComplementaireDate = this.quotationMP.confirmationDevisComplementaireDate;
                                        this.quotation.repairableVehicle = this.quotationMP.repairableVehicle;
                                        this.quotation.concordanceReport = this.quotationMP.concordanceReport;
                                        this.quotation.preliminaryReport = this.quotationMP.preliminaryReport;
                                        this.quotation.immobilizedVehicle = this.quotationMP.immobilizedVehicle;
                                        this.quotation.conditionVehicle = this.quotationMP.conditionVehicle;
                                        this.quotation.kilometer = this.quotationMP.kilometer;
                                        this.quotation.totalMo = this.quotationMP.totalMo;
                                        this.quotation.heureMO = this.quotationMP.heureMO;

                                        this.quotation.confirmationDecisionQuote = false;
                                        this.loadAllDetailsMoQuotationMP();
                                        this.loadAllIngredientForQuotationMP();
                                        this.loadAllRechangeForQuotationMP();
                                        this.loadAllFournitureForQuotationMP();
                                        this.status = this.quotation.statusId; // Get etat de devis selectionné
                                    });
                            }
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }

            //confirmation modification Prix
            if (params["idConfirmationModificationPrix"]) {
                this.isConfirmationModificationPrix = true;
                //this.modeConfirmation = true;
                this.isQuoteEntry = true;
                this.showRemoveLine = true;
                this.getDetailReceptionVehicule(
                    params["idConfirmationModificationPrix"]
                );
                this.sinisterPecService
                    .findPrestationPec(params["idConfirmationModificationPrix"])
                    .subscribe((res) => {
                        this.sinisterPec = res;
                        if (
                            this.sinisterPec.id !== null &&
                            this.sinisterPec.id !== undefined
                        ) {
                            this.showAttachments();
                            this.observationService
                                .findByPrestation(this.sinisterPec.id)
                                .subscribe((subRes: ResponseWrapper) => {
                                    this.observationss = subRes.json;
                                });

                            if (this.sinisterPec.stepId != 27) {
                                this.buttonConfirmModifPrix = false;
                            }

                            this.getPhotoReparation();
                            //this.getAttachmentsPec();
                            this.getDateReceptionVehicule();
                            this.dossierService
                                .find(this.sinisterPec.sinisterId)
                                .subscribe((res) => {
                                    this.dossier = res;

                                    this.vehiculeAssureService
                                        .find(this.dossier.vehicleId)
                                        .subscribe((res) => {
                                            this.vehicule = res;
                                            this.findInsured();
                                            this.contratAssuranceService
                                                .find(this.vehicule.contratId)
                                                .subscribe((res) => {
                                                    this.contratAssurance = res;
                                                });
                                        });
                                });
                            this.reparateurService
                                .find(this.sinisterPec.reparateurId)
                                .subscribe((res) => {
                                    this.reparateur = res;
                                });
                            // affiche devis
                            this.isCollapsed = true;
                            this.modeEdit = true;
                            this.modeReparationLeger = true;
                            console.log(
                                "hello word quot" +
                                    this.sinisterPec.primaryQuotationId
                            );
                            if (
                                this.sinisterPec.primaryQuotationId != null &&
                                this.sinisterPec.primaryQuotationId != undefined
                            ) {
                                this.quotationMPService
                                    .find(this.sinisterPec.id)
                                    .subscribe((res: QuotationMP) => {
                                        // Find devid By ID
                                        this.quotationMP = res;
                                        this.quotationMP.confirmationDecisionQuote = false;
                                        this.quotation.statusId = this.quotationMP.statusId;
                                        this.quotation.ttcAmount = this.quotationMP.ttcAmount;
                                        this.quotation.stampDuty = this.quotationMP.stampDuty;
                                        this.quotation.htAmount = this.quotationMP.htAmount;
                                        this.quotation.priceNewVehicle = this.quotationMP.priceNewVehicle;
                                        this.quotation.marketValue = this.quotationMP.marketValue;

                                        this.quotation.creationDate = this.quotationMP.creationDate;
                                        this.quotation.expertDecision = this.quotationMP.expertDecision;
                                        this.quotation.revueDate = this.quotationMP.revueDate;
                                        this.quotation.avisExpertDate = this.quotationMP.avisExpertDate;

                                        this.quotation.verificationDevisValidationDate = this.quotationMP.verificationDevisValidationDate;
                                        this.quotation.verificationDevisRectificationDate = this.quotationMP.verificationDevisRectificationDate;
                                        this.quotation.confirmationDevisDate = this.quotationMP.confirmationDevisDate;
                                        this.quotation.miseAJourDevisDate = this.quotationMP.miseAJourDevisDate;
                                        this.quotation.confirmationDevisComplementaireDate = this.quotationMP.confirmationDevisComplementaireDate;

                                        this.quotation.repairableVehicle = this.quotationMP.repairableVehicle;
                                        this.quotation.concordanceReport = this.quotationMP.concordanceReport;
                                        this.quotation.preliminaryReport = this.quotationMP.preliminaryReport;
                                        this.quotation.immobilizedVehicle = this.quotationMP.immobilizedVehicle;
                                        this.quotation.conditionVehicle = this.quotationMP.conditionVehicle;
                                        this.quotation.kilometer = this.quotationMP.kilometer;
                                        this.quotation.totalMo = this.quotationMP.totalMo;
                                        this.quotation.heureMO = this.quotationMP.heureMO;

                                        this.quotation.confirmationDecisionQuote = false;
                                        this.quotation.id = this.sinisterPec.primaryQuotationId;
                                        this.loadAllDetailsMoQuotationMP();
                                        this.loadAllIngredientForQuotationMP();
                                        this.loadAllRechangeForQuotationMP();
                                        this.loadAllFournitureForQuotationMP();
                                        this.status = this.quotation.statusId; // Get etat de devis selectionné
                                    });
                            }
                        } else {
                            this.router.navigate(["/accessdenied"]);
                        }
                    });
            }
        });
    }
    historyApec() {
        this.ngbModalRef = this.sinisterPecPopupService.openHistoryDetailsSinisterPec(
            HistoryPopupDetailsPec as Component,
            this.sinisterPec.id
        );
        this.ngbModalRef.result.then(
            (result: any) => {
                if (result !== null && result !== undefined) {
                    console.log("result select popup------" + result);
                }
                this.ngbModalRef = null;
            },
            (reason) => {
                // TODO: print error message
                console.log(
                    "______________________________________________________2"
                );
                this.ngbModalRef = null;
            }
        );
    }
    onSelect(event: TypeaheadMatch): void {
        this.detailsPiece = event.item;
    }
    showAttachments() {
        this.showFaceAvantDroit = true;
        this.showFaceArriereDroit = true;
        this.showFaceAvantGauche = true;
        this.showFaceArriereGauche = true;
        this.showFinition = true;
        this.showNSerie = true;
        this.showCompteur = true;
        this.showImmatriculation = true;
        this.showCarteGrise = true;
        this.showFaceAvantDroitAttachment = false;
        this.showFaceArriereDroitAttachment = false;
        this.showFaceAvantGaucheAttachment = false;
        this.showFaceArriereGaucheAttachment = false;
        this.showFinitionAttachment = false;
        this.showNSerieAttachment = false;
        this.showCompteurAttachment = false;
        this.showImmatriculationAttachment = false;
        this.showCarteGriseAttachment = false;
    }

    showAttachmentsPointChocExpertise() {
        this.showPointChoc1 = true;
        this.showPointChoc2 = true;
        this.showPointChoc3 = true;
        this.showPointChoc4 = true;

        this.showPointChoc1Attachment = false;
        this.showPointChoc2Attachment = false;
        this.showPointChoc3Attachment = false;
        this.showPointChoc4Attachment = false;
    }

    addObservation() {
        console.log("Add Observation");
        console.log(this.principal.getCurrentAccount());
        if (
            this.observation.commentaire === null ||
            this.observation.commentaire === undefined
        ) {
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
            this.observationService
                .create(this.observation)
                .subscribe((res) => {});
            this.observation = new Observation();
        }
    }

    saveObservations(prestationId: number, observations: Observation[]) {
        if (observations && observations.length > 0) {
            observations.forEach((observation) => {
                if (
                    observation.deleted &&
                    observation.id !== null &&
                    observation.id !== undefined
                ) {
                    this.observationService
                        .delete(observation.id)
                        .subscribe((resultObs) => {});
                } else if (
                    !observation.deleted &&
                    (observation.id === null || observation.id === undefined)
                ) {
                    observation.sinisterPecId = prestationId;
                    this.observationService
                        .create(observation)
                        .subscribe((resultObs: Observation) => {});
                } else if (
                    !observation.deleted &&
                    observation.id !== null &&
                    observation.id !== undefined
                ) {
                    observation.sinisterPecId = prestationId;
                    this.observationService
                        .update(observation)
                        .subscribe((resultObs: Observation) => {});
                }
            });
        }
        this.observationService
            .findByPrestation(this.sinisterPec.id)
            .subscribe((subRes: ResponseWrapper) => {
                this.observationss = subRes.json;
            });
    }

    getAvisTechnique(prestationId) {
        this.detailsQuotationService
            .findBySinisterPec(prestationId)
            .subscribe((detailsQuote) => {
                this.detailsQuote = detailsQuote;
                if (
                    this.detailsQuote.id == null ||
                    this.detailsQuote.id == undefined
                ) {
                    this.detailsQuote = new DetailsQuotation();
                    this.detailsQuote.expertiseDate = this.dateAsYYYYMMDDHHNNSSLDT(
                        new Date()
                    );
                }
            });
    }

    saveAvisTechnique(detailsQuote: DetailsQuotation) {
        if (
            this.detailsQuote.id !== null &&
            this.detailsQuote.id !== undefined
        ) {
            this.detailsQuotationService
                .update(detailsQuote)
                .subscribe((detailsQuote) => {
                    this.detailsQuote = detailsQuote;
                });
        } else {
            detailsQuote.sinisterPecId = this.sinisterPec.id;
            this.detailsQuotationService
                .create(detailsQuote)
                .subscribe((detailsQuote) => {
                    this.detailsQuote = detailsQuote;
                });
        }
    }

    /**
     * Soumettre quotation
     */

    soumettre() {
        this.showAlert = false;
        this.blockAddQuotation = false;
        console.log(
            "TESTModificationPrix " + this.sinisterPec.modificationPrix
        );
        if (this.sinisterPec.modificationPrix) {
            this.confirmationDialogService
                .confirm(
                    "Confirmation",
                    " Êtes-vous sûrs de vouloir Soumettre ?",
                    "Oui",
                    "Non",
                    "lg"
                )
                .then((confirmed) => {
                    console.log("User confirmed:", confirmed);
                    if (confirmed) {
                        this.oneClickForButton16 = false;
                        this.sendDateReceptionVehicule();
                        this.sinisterPec.modificationPrix = false;
                        this.isSaving = true;

                        for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                            this.detailsPiecesMO[i].isMo = true;
                        }
                        let listPieces = this.detailsPiecesMO.concat(
                            this.detailsPieces,
                            this.detailsPiecesFourniture,
                            this.detailsPiecesIngredient
                        );
                        listPieces.forEach((item, index) => {
                            if (item.modifiedLine != null)
                                listPieces.splice(index, 1);
                        });
                        for (let i = 0; i < listPieces.length; i++) {
                            if (!this.sinisterPec.changeModificationPrix) {
                                listPieces[i].id = null;
                            }
                            listPieces[i].quotationId = null;
                        }
                        this.quotationMP.originalQuotationId = this.sinisterPec.primaryQuotationId;
                        // console.log("l beforeadd-**-*--" + listPieces.length);
                        this.quotationMP.listPieces = listPieces;
                        this.quotationMP.statusId = 1;
                        this.quotationMP.ttcAmount = this.quotation.ttcAmount;
                        this.quotationMP.priceNewVehicle = this.quotation.priceNewVehicle;
                        this.quotationMP.marketValue = this.quotation.marketValue;

                        this.quotationMP.creationDate = this.quotation.creationDate;
                        this.quotationMP.expertDecision = this.quotation.expertDecision;
                        this.quotationMP.revueDate = this.quotation.revueDate;

                        this.quotationMP.verificationDevisValidationDate = this.quotation.verificationDevisValidationDate;
                        this.quotationMP.verificationDevisRectificationDate = this.quotation.verificationDevisRectificationDate;
                        this.quotationMP.confirmationDevisDate = this.quotation.confirmationDevisDate;
                        this.quotationMP.miseAJourDevisDate = this.quotation.miseAJourDevisDate;
                        this.quotationMP.confirmationDevisComplementaireDate = this.quotation.confirmationDevisComplementaireDate;

                        this.quotationMP.avisExpertDate = this.quotation.avisExpertDate;
                        this.quotationMP.repairableVehicle = this.quotation.repairableVehicle;
                        this.quotationMP.concordanceReport = this.quotation.concordanceReport;
                        this.quotationMP.preliminaryReport = this.quotation.preliminaryReport;
                        this.quotationMP.immobilizedVehicle = this.quotation.immobilizedVehicle;
                        this.quotationMP.conditionVehicle = this.quotation.conditionVehicle;
                        this.quotationMP.kilometer = this.quotation.kilometer;
                        this.quotationMP.totalMo = this.quotation.totalMo;
                        this.quotationMP.heureMO = this.quotation.heureMO;

                        this.quotationMP.htAmount = this.quotation.htAmount;
                        this.quotationMP.stampDuty = this.quotation.stampDuty;
                        this.quotationMP.sinisterPecId = this.sinisterPec.id;

                        if (this.quotationMP.id == null) {
                            this.quotationMPService
                                .create(this.quotationMP)
                                .subscribe((res) => {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.CONFIRMATION_MODIFICATION_PRIX;
                                    this.sinisterPecService
                                        .updateIt(this.sinisterPec)
                                        .subscribe((res) => {
                                            //this.sendNotifSoumettreDevis('NotifSoumettreDevis');
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                        });
                                });
                        } else {
                            this.quotationMPService
                                .put(this.quotationMP)
                                .subscribe((res) => {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.CONFIRMATION_MODIFICATION_PRIX;
                                    this.sinisterPecService
                                        .updateIt(this.sinisterPec)
                                        .subscribe((res) => {
                                            //this.sendNotifSoumettreDevis('NotifSoumettreDevis');
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                        });
                                });
                        }
                    }
                })
                .catch(() =>
                    console.log(
                        "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                    )
                );
        } else {
            if (this.isQuoteEntry == true) {
                this.confirmationDialogService
                    .confirm(
                        "Confirmation",
                        "Êtes-vous sûr de vouloir soumettre ?",
                        "Oui",
                        "Non",
                        "lg"
                    )
                    .then((confirmed) => {
                        console.log("User confirmed:", confirmed);
                        if (confirmed) {
                            this.oneClickForButton16 = false;
                            this.sendDateReceptionVehicule();
                            this.isSaving = true;
                            for (
                                let i = 0;
                                i < this.detailsPiecesMO.length;
                                i++
                            ) {
                                this.detailsPiecesMO[i].isMo = true;
                            }
                            let listPieces = [];
                            if (this.isMaJDevis == true) {
                                listPieces = this.detailsPiecesMO.concat(
                                    this.detailsPieces,
                                    this.detailsPiecesFourniture,
                                    this.detailsPiecesIngredient,
                                    this.detailsPieces1,
                                    this.detailsPiecesIngredient1,
                                    this.detailsPiecesFourniture1,
                                    this.detailsPiecesMO1
                                );
                                var cache = {};
                                listPieces = listPieces.filter(function (elem) {
                                    return cache[elem.id]
                                        ? 0
                                        : (cache[elem.id] = 1);
                                });
                            } else {
                                listPieces = this.detailsPiecesMO.concat(
                                    this.detailsPieces,
                                    this.detailsPiecesFourniture,
                                    this.detailsPiecesIngredient
                                );
                            }
                            listPieces.forEach((listPieceDetails, index) => {
                                if (this.isMaJDevis == false) {
                                    listPieceDetails.isModified = false;
                                }
                                if (listPieceDetails.modifiedLine == null) {
                                    this.sommeQuotation =
                                        this.sommeQuotation +
                                        listPieceDetails.totalTtc;
                                }
                                //if (listPieceDetails.modifiedLine != null) listPieces.splice(index, 1);
                            });

                            for (let i = 0; i < listPieces.length; i++) {
                                if (
                                    listPieces[i].designationId == null ||
                                    listPieces[i].designationId == undefined
                                ) {
                                    this.blockAddQuotation = true;
                                    break;
                                }
                            }
                            if (this.blockAddQuotation == true) {
                                this.showAlert = true;
                            } else {
                                this.quotation.listPieces = listPieces;
                                this.quotation.statusId = 1;
                                if (this.DevisComplementary) {
                                    this.sinisterPec.historyAvisExpert =
                                        "ComplementaireQuotation";
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.Verification;
                                    this.quotation.statusId =
                                        QuoteStatus.QUOTATION_STATUS_VALID_GA;

                                    //this.sinisterPec.oldStepNw = 16;
                                    //this.sinisterPec.testDevisCmpl = false;
                                    //this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resP) => {

                                    //});
                                    //this.apec.testDevis = false;
                                    //this.apec.sinisterPec.stepId = PrestationPecStep.Verification;
                                    //this.apecService.update(this.apec).subscribe((res) => {
                                    //  this.apec = res;

                                    //});
                                } else if (
                                    this.sinisterPec.oldStep ==
                                    PrestationPecStep.VALIDATION_APEC
                                ) {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.Verification;
                                    this.sinisterPec.historyAvisExpert =
                                        "InitialeQuotation";
                                } else if (
                                    this.sinisterPec.stepId ==
                                    PrestationPecStep.UPDATE_QUOTE
                                ) {
                                    this.quotation.miseAJourDevisDate = this.dateAsYYYYMMDDHHNNSSLDT(
                                        new Date()
                                    );
                                    this.sinisterPec.historyAvisExpert =
                                        "UpdateQuote";
                                    if (this.sinisterPec.oldStep == 107) {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId =
                                            PrestationPecStep.Verification;
                                        this.sinisterPec.oldStep = null;
                                        this.apecService
                                            .delete(this.apecMAJ.id)
                                            .subscribe((ap) => {});
                                    } else {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId =
                                            PrestationPecStep.REVUE_VERIFICATION_DEVIS;
                                    }
                                } else {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.Verification;
                                    this.quotation.statusId =
                                        QuoteStatus.QUOTATION_STATUS_VALID_GA;
                                    this.sinisterPec.historyAvisExpert =
                                        "InitialeQuotation";
                                }

                                if (
                                    this.gtestimateId !== undefined &&
                                    this.gtestimateId !== null &&
                                    this.gtestimateId !== 0
                                ) {
                                    this.modeEdit = true;
                                    if (
                                        !this.DevisComplementary &&
                                        !this.additionalQuote
                                    ) {
                                        this.sinisterPec.primaryQuotationId = this.gtestimateId;
                                        if (
                                            this.quotation.id == null ||
                                            this.quotation.id == undefined
                                        ) {
                                            this.quotation.id = this.gtestimateId;
                                        }
                                    }
                                }
                                if (
                                    (this.sinisterPec.primaryQuotationId ==
                                        null ||
                                        this.sinisterPec.primaryQuotationId ==
                                            undefined) &&
                                    this.sinisterPec.quoteId !== null &&
                                    this.sinisterPec.quoteId !== undefined
                                ) {
                                    this.sinisterPec.primaryQuotationId = this.sinisterPec.quoteId;
                                }
                                this.sinisterPec.quotation = this.quotation;
                                this.sinisterPec.quoteViaGt = null;
                                this.sinisterPec.quoteId = null;
                                // this.sinisterPec.observations = this.observations;
                                this.sinisterPecService
                                    .updatePecForQuotation(
                                        this.sinisterPec,
                                        this.modeEdit
                                    )
                                    .subscribe((res) => {
                                        this.sinisterPec = res;
                                        console.log(this.sommeQuotation);
                                        if (this.DevisComplementary) {
                                            this.apec.etat = 20;
                                            this.apecService
                                                .update(this.apec)
                                                .subscribe((res) => {
                                                    this.apec = res;
                                                    this.sendNotifDevComp(
                                                        "DevisComplemantaryRealize"
                                                    );
                                                });
                                            //this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            //this.sinisterPec.stepId = PrestationPecStep.Verification;
                                            //this.sinisterPec.testDevisCmpl = false;
                                            //this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {

                                            //});
                                        } else {
                                            if (
                                                this.sinisterPec
                                                    .oldStepModifSinPec == 17
                                            ) {
                                                this.sendNotifSoumettreDevis(
                                                    "NotifSoumettreDevis"
                                                );
                                            } else if (!this.additionalQuote) {
                                                this.sendNotif(
                                                    this.sinisterPec
                                                        .reparateurId,
                                                    "Vérification"
                                                );
                                            } else if (this.additionalQuote) {
                                                this.sendNotifDevComp(
                                                    "DevisComplemantaryRealize"
                                                );
                                            }
                                        }
                                        if (this.isReceptVeh == true) {
                                            this.updateVehicule();
                                        }

                                        if (this.receiveVehicule)
                                            this.router.navigate([
                                                "../ajout-saisie-devis",
                                            ]);
                                        else
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                    });
                            }
                        }
                    })
                    .catch(() =>
                        console.log(
                            "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                        )
                    );
            } else {
                this.confirmationDialogService
                    .confirm(
                        "Confirmation",
                        "Êtes-vous sûrs de vouloir Soumettre le devis ?",
                        "Oui",
                        "Non",
                        "lg"
                    )
                    .then((confirmed) => {
                        console.log("User confirmed:", confirmed);
                        if (confirmed) {
                            this.oneClickForButton16 = false;
                            this.sendDateReceptionVehicule();
                            this.isSaving = true;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId =
                                PrestationPecStep.MISSIONNER_EXPERT;
                            //this.sinisterPec.quoteId = null;
                            this.sinisterPec.primaryQuotation = null;
                            this.sinisterPec.primaryQuotationId = null;
                            this.sinisterPecService
                                .updateIt(this.sinisterPec)
                                .subscribe((res) => {
                                    this.sinisterPec = res;
                                    if (this.isReceptVeh == true) {
                                        this.updateVehicule();
                                    }
                                    if (
                                        this.sinisterPec.oldStepModifSinPec ==
                                        17
                                    ) {
                                        this.sendNotifSoumettreDevis(
                                            "NotifSoumettreDevis"
                                        );
                                    } else {
                                        this.sendNotif(
                                            this.sinisterPec.reparateurId,
                                            "Vérification"
                                        );
                                    }
                                    this.sendNotif(
                                        this.sinisterPec.reparateurId,
                                        "Missionner un expert"
                                    );
                                    if (this.receiveVehicule)
                                        this.router.navigate([
                                            "../ajout-saisie-devis",
                                        ]);
                                    else
                                        this.router.navigate([
                                            "../rectification-devis",
                                        ]);
                                });
                        }
                    })
                    .catch(() =>
                        console.log(
                            "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                        )
                    );
            }
        }
    }
    /**
     * 0.2 quotation
     */

    save() {
        this.blockAddQuotation = false;
        this.showAlert = false;
        console.log("etat chock" + this.isQuoteEntry);
        if (this.sinisterPec.modificationPrix) {
            this.confirmationDialogService
                .confirm(
                    "Confirmation",
                    "Êtes-vous sûrs de vouloir soumettre le devis ?",
                    "Oui",
                    "Non",
                    "lg"
                )
                .then((confirmed) => {
                    console.log("User confirmed:", confirmed);
                    if (confirmed) {
                        this.oneClickForButton16 = false;
                        this.sendDateReceptionVehicule();
                        this.isSaving = true;
                        for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                            this.detailsPiecesMO[i].isMo = true;
                        }
                        let listPieces = this.detailsPiecesMO.concat(
                            this.detailsPieces,
                            this.detailsPiecesFourniture,
                            this.detailsPiecesIngredient
                        );
                        for (let i = 0; i < listPieces.length; i++) {
                            if (!this.sinisterPec.changeModificationPrix) {
                                listPieces[i].id = null;
                            }
                            listPieces[i].quotationId = null;
                        }
                        this.quotationMP.originalQuotationId = this.sinisterPec.primaryQuotationId;
                        this.quotationMP.listPieces = listPieces;
                        this.quotationMP.statusId = 1;
                        this.quotationMP.ttcAmount = this.quotation.ttcAmount;
                        this.quotationMP.htAmount = this.quotation.htAmount;
                        this.quotationMP.stampDuty = this.quotation.stampDuty;
                        this.quotationMP.priceNewVehicle = this.quotation.priceNewVehicle;
                        this.quotationMP.marketValue = this.quotation.marketValue;

                        this.quotationMP.creationDate = this.quotation.creationDate;
                        this.quotationMP.expertDecision = this.quotation.expertDecision;
                        this.quotationMP.revueDate = this.quotation.revueDate;
                        this.quotationMP.avisExpertDate = this.quotation.avisExpertDate;
                        this.quotationMP.verificationDevisValidationDate = this.quotation.verificationDevisValidationDate;
                        this.quotationMP.verificationDevisRectificationDate = this.quotation.verificationDevisRectificationDate;
                        this.quotationMP.confirmationDevisDate = this.quotation.confirmationDevisDate;
                        this.quotationMP.miseAJourDevisDate = this.quotation.miseAJourDevisDate;
                        this.quotationMP.confirmationDevisComplementaireDate = this.quotation.confirmationDevisComplementaireDate;
                        this.quotationMP.repairableVehicle = this.quotation.repairableVehicle;
                        this.quotationMP.concordanceReport = this.quotation.concordanceReport;
                        this.quotationMP.preliminaryReport = this.quotation.preliminaryReport;
                        this.quotationMP.immobilizedVehicle = this.quotation.immobilizedVehicle;
                        this.quotationMP.conditionVehicle = this.quotation.conditionVehicle;
                        this.quotationMP.kilometer = this.quotation.kilometer;
                        this.quotationMP.totalMo = this.quotation.totalMo;
                        this.quotationMP.heureMO = this.quotation.heureMO;

                        this.quotationMP.sinisterPecId = this.sinisterPec.id;
                        this.sinisterPec.changeModificationPrix = true;
                        // this.sinisterPec.observations = this.observations;
                        if (this.quotationMP.id == null) {
                            console.log(
                                "iciiii log  idd sinister pec" +
                                    this.quotationMP.sinisterPecId
                            );
                            this.quotationMPService
                                .create(this.quotationMP)
                                .subscribe((res) => {
                                    this.sinisterPecService
                                        .updateIt(this.sinisterPec)
                                        .subscribe((res) => {
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                        });
                                });
                        } else {
                            this.quotationMPService
                                .put(this.quotationMP)
                                .subscribe((res) => {
                                    this.sinisterPecService
                                        .updateIt(this.sinisterPec)
                                        .subscribe((res) => {
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                        });
                                });
                        }
                    }
                })
                .catch(() =>
                    console.log(
                        "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                    )
                );
        } else {
            if (this.isQuoteEntry == true) {
                this.confirmationDialogService
                    .confirm(
                        "Confirmation",
                        "Êtes-vous sûrs de vouloir enregister ?",
                        "Oui",
                        "Non",
                        "lg"
                    )
                    .then((confirmed) => {
                        console.log("User confirmed:", confirmed);
                        if (confirmed) {
                            this.oneClickForButton16 = false;
                            this.isSaving = true;
                            this.sendDateReceptionVehicule();
                            for (
                                let i = 0;
                                i < this.detailsPiecesMO.length;
                                i++
                            ) {
                                this.detailsPiecesMO[i].isMo = true;
                            }
                            let listPieces = this.detailsPiecesMO.concat(
                                this.detailsPieces,
                                this.detailsPiecesFourniture,
                                this.detailsPiecesIngredient
                            );
                            for (let i = 0; i < listPieces.length; i++) {
                                if (
                                    listPieces[i].designationId == null ||
                                    listPieces[i].designationId == undefined
                                ) {
                                    this.blockAddQuotation = true;
                                    break;
                                }
                            }
                            if (this.blockAddQuotation == true) {
                                this.showAlert = true;
                            } else {
                                this.quotation.listPieces = listPieces;

                                if (
                                    this.useGt == true &&
                                    this.sinisterPec.quoteViaGt == true
                                ) {
                                    this.sinisterPec.quoteViaGt = null;
                                }
                                if (
                                    listPieces.length == 0 &&
                                    this.useGt == false
                                ) {
                                    this.sinisterPec.quoteViaGt = true;
                                }
                                this.quotation.statusId = 1;
                                if (
                                    this.gtestimateId !== undefined &&
                                    this.gtestimateId !== null &&
                                    this.gtestimateId !== 0
                                ) {
                                    this.modeEdit = true;
                                    if (
                                        !this.DevisComplementary &&
                                        !this.additionalQuote
                                    ) {
                                        this.sinisterPec.primaryQuotationId = this.gtestimateId;
                                        if (
                                            this.quotation.id == null ||
                                            this.quotation.id == undefined
                                        ) {
                                            this.quotation.id = this.gtestimateId;
                                        }
                                    }
                                }
                                if (
                                    (this.quotation.id == null ||
                                        this.quotation.id == undefined) &&
                                    this.isDemontageVehicule == false
                                ) {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.ATTENTE_SAISIE_DVIS;
                                }
                                if (
                                    (this.sinisterPec.primaryQuotationId ==
                                        null ||
                                        this.sinisterPec.primaryQuotationId ==
                                            undefined) &&
                                    this.sinisterPec.quoteId !== null &&
                                    this.sinisterPec.quoteId !== undefined
                                ) {
                                    this.sinisterPec.primaryQuotationId = this.sinisterPec.quoteId;
                                }
                                this.sinisterPec.quotation = this.quotation;
                                this.sinisterPec.testDevisCmpl = false;
                                this.sinisterPec.quoteId = null;
                                //  this.sinisterPec.observations = this.observations;
                                this.sinisterPec.historyAvisExpert =
                                    "SaveQuotation";
                                this.sinisterPecService
                                    .updatePecForQuotation(
                                        this.sinisterPec,
                                        this.modeEdit
                                    )
                                    .subscribe((res) => {
                                        this.sinisterPec = res;
                                        if (this.isReceptVeh == true) {
                                            this.updateVehicule();
                                        }
                                        if (this.isMaJDevis) {
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                        } else if (this.isDemontageVehicule) {
                                            this.router.navigate([
                                                "../demontage-devis",
                                            ]);
                                        } else {
                                            this.router.navigate([
                                                "../ajout-saisie-devis",
                                            ]);
                                        }
                                    });
                            }
                        }
                    })
                    .catch(() =>
                        console.log(
                            "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                        )
                    );
            } else if (this.isQuoteEntry == false) {
                this.confirmationDialogService
                    .confirm(
                        "Confirmation",
                        "Êtes-vous sûrs de vouloir enregister la demande de démontage  ?",
                        "Oui",
                        "Non",
                        "lg"
                    )
                    .then((confirmed) => {
                        console.log("User confirmed:", confirmed);
                        if (confirmed) {
                            this.oneClickForButton16 = false;
                            if (
                                this.sinisterPec.id !== null &&
                                this.sinisterPec.id !== undefined
                            ) {
                                this.isSaving = true;
                                this.sendDateReceptionVehicule();
                                if (
                                    this.sinisterPec.lightShock == null ||
                                    this.sinisterPec.lightShock == undefined
                                ) {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.ATTENTE_SAISIE_DVIS;
                                }
                                //this.sinisterPec.quoteId = null;
                                this.sinisterPecService
                                    .updateIt(this.sinisterPec)
                                    .subscribe((res) => {
                                        this.sinisterPec = res;
                                        if (this.isReceptVeh == true) {
                                            this.updateVehicule();
                                        }
                                        if (this.isMaJDevis)
                                            this.router.navigate([
                                                "../rectification-devis",
                                            ]);
                                        else
                                            this.router.navigate([
                                                "../ajout-saisie-devis",
                                            ]);
                                    });
                            }
                        }
                    })
                    .catch(() =>
                        console.log(
                            "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                        )
                    );
            }
        }
    }

    updateVehicule() {
        if (this.vehicule.id !== undefined && this.vehicule.id !== null) {
            if (
                this.vehicule.datePCirculation !== null &&
                this.vehicule.datePCirculation !== undefined
            ) {
                const datePCirculation = new Date(
                    this.vehicule.datePCirculation
                );
                this.vehicule.datePCirculation = {
                    year: datePCirculation.getFullYear(),
                    month: datePCirculation.getMonth() + 1,
                    day: datePCirculation.getDate(),
                };
            }
            this.vehiculeAssureService
                .update(this.vehicule)
                .subscribe((veh: VehiculeAssure) => {});
        }
    }

    public highlightRow(emp) {
        this.selectedName = emp.id;
    }
    savePhotoReparation() {
        this.piecesAttachments.forEach((pieceAttFile) => {
            pieceAttFile.creationDate = null;
            if (
                pieceAttFile.pieceFile !== null &&
                this.updatePieceJointe1 == true &&
                (pieceAttFile.id == null || pieceAttFile.id == undefined)
            ) {
                this.sinisterPecService
                    .saveAttachmentsPiecePhotoReparation(
                        this.sinisterPec.id,
                        pieceAttFile.pieceFile,
                        pieceAttFile.label,
                        pieceAttFile.name,
                        "PHOTOREPARATION"
                    )
                    .subscribe((res: Attachment) => {
                        //this.alertService.success('auxiliumApp.sinisterPec.createdImprime',null,null);
                        this.getPhotoReparation();
                    });
            }
        });
    }

    savePhotoExpertise() {
        this.piecesAttachmentsExpertise.forEach((pieceAttFile) => {
            pieceAttFile.creationDate = null;
            if (
                pieceAttFile.pieceFile !== null &&
                this.updatePieceJointe1 == true &&
                (pieceAttFile.id == null || pieceAttFile.id == undefined)
            ) {
                this.sinisterPecService
                    .saveAttachmentsPiecePhotoReparation(
                        this.sinisterPec.id,
                        pieceAttFile.pieceFile,
                        pieceAttFile.label,
                        pieceAttFile.name,
                        "EXPERTISE"
                    )
                    .subscribe((res: Attachment) => {
                        //this.alertService.success('auxiliumApp.sinisterPec.createdImprime',null,null);
                        this.getPhotoExpertise();
                    });
            }
        });
    }

    getDetailReceptionVehicule(prestationId) {
        this.viewDetailQuotationService
            .getDetailDevisByPec(prestationId)
            .subscribe((res) => {
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
                this.vehicule.packId = this.detailQuotation.packid;
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
                if (
                    this.detailQuotation.vehicleReceiptDate &&
                    !this.detailQuotation.receptionVehicule
                ) {
                    const date = new Date(
                        this.detailQuotation.vehicleReceiptDate
                    );

                    this.detailQuotation.vehicleReceiptDate = {
                        year: date.getFullYear(),
                        month: date.getMonth() + 1,
                        day: date.getDate(),
                    };
                } else {
                    this.myDate = this.detailQuotation.vehicleReceiptDate;
                }
                this.refPackservice
                    .find(this.vehicule.packId)
                    .subscribe((res) => {
                        this.refPack = res;
                        this.apecSettings = this.refPack.apecSettings;
                        this.sinisterTypeSettings = this.refPack.sinisterTypeSettings;
                        this.listModeGestion = this.refPack.modeGestions;
                        if (this.refPack.conventionId != null) {
                            this.conventionService
                                .find(this.refPack.conventionId)
                                .subscribe((convention: Convention) => {
                                    if (
                                        convention.conventionAmendments.length >
                                        0
                                    ) {
                                        convention.conventionAmendments.forEach(
                                            (convensionAmendment) => {
                                                if (
                                                    convensionAmendment.oldRefPackId ==
                                                    this.refPack.id
                                                ) {
                                                    const dateToday = new Date().getTime();
                                                    const dateEffet = new Date(
                                                        convensionAmendment.startDate.toString()
                                                    ).getTime();
                                                    const dateFin = new Date(
                                                        convensionAmendment.endDate.toString()
                                                    ).getTime();
                                                    if (
                                                        dateToday >=
                                                            dateEffet &&
                                                        dateToday <= dateFin
                                                    ) {
                                                        convensionAmendment.refPack.apecSettings.forEach(
                                                            (apSett) => {
                                                                this.apecSettingsAvenant.push(
                                                                    apSett
                                                                );
                                                            }
                                                        );
                                                        convensionAmendment.refPack.sinisterTypeSettings.forEach(
                                                            (typeSett) => {
                                                                this.sinisterTypeSettingsAvenant.push(
                                                                    typeSett
                                                                );
                                                            }
                                                        );
                                                        convensionAmendment.refPack.modeGestions.forEach(
                                                            (mdg) => {
                                                                this.listModeGestionAvenant.push(
                                                                    mdg
                                                                );
                                                            }
                                                        );
                                                    }
                                                }
                                            }
                                        );
                                    }
                                });
                        }
                    });
                this.route.params.subscribe((params) => {
                    if (params["prestationId"]) {
                        if (
                            this.detailQuotation.primaryQuotationId !== null &&
                            this.detailQuotation.primaryQuotationId !==
                                undefined
                        ) {
                            if (
                                this.detailQuotation.vehicleReceiptDate ==
                                    null ||
                                this.detailQuotation.vehicleReceiptDate ==
                                    undefined
                            ) {
                                this.myDate = this.dateAsYYYYMMDDHHNNSS(
                                    new Date()
                                );
                            }
                        } else {
                            if (
                                this.detailQuotation.vehicleReceiptDate ==
                                    null ||
                                this.detailQuotation.vehicleReceiptDate ==
                                    undefined
                            ) {
                                this.myDate = this.dateAsYYYYMMDDHHNNSS(
                                    new Date()
                                );
                                this.receiveVehicule = true;
                            }
                        }
                    }
                });
            });
    }

    //getDevis
    getDevis(quotationId) {
        this.quotationService.find(quotationId).subscribe((res) => {
            // Find devid By ID
            this.quotation = res;
            this.quotation.stampDuty = this.activeStamp.amount;
            if (this.RevueValidationDevis) {
                if (
                    this.quotation.isConfirme !== null &&
                    this.quotation.isConfirme !== undefined
                ) {
                    if (
                        this.quotation.expertDecision ==
                        "Accord pour réparation avec modification"
                    ) {
                        this.showVetuste = true;
                    } else {
                        this.showVetuste = false;
                    }
                } else {
                    if (
                        this.sinisterPec.expertDecision ==
                        "Accord pour réparation avec modification"
                    ) {
                        this.showVetuste = true;
                    } else {
                        this.showVetuste = false;
                    }
                }
            }
            this.ttcAmountDevis = this.quotation.ttcAmount;
            console.log(this.quotation);
            this.quotation.confirmationDecisionQuote = false;
            this.loadAllDetailsMo();
            this.loadAllIngredient();
            this.loadAllRechange();
            this.loadAllFourniture();
            let list = this.detailsPiecesMO.concat(
                this.detailsPieces,
                this.detailsPiecesFourniture,
                this.detailsPiecesIngredient
            );
            list.forEach((ligne) => {
                if (ligne.observationExpert == null) {
                    this.observationExpertColumn == false;
                }
            });
            this.status = this.quotation.statusId; // Get etat de devis selectionné
            if (
                this.sinisterPec.modificationPrix ||
                (this.confirmationDevisComplementaire == true &&
                    this.sinisterPec.preliminaryReport == true)
            ) {
                if (this.sinisterPec.listComplementaryQuotation.length > 0) {
                    console.log("rep icii devis  comp entrer");
                    this.sinisterPec.listComplementaryQuotation.forEach(
                        (element) => {
                            console.log(
                                "rep icii devis compli list element" +
                                    element.id
                            );
                            if (element.statusId == 4) {
                                this.quotationId = element.id;
                            }
                            if (element.isConfirme) {
                                this.quotationService
                                    .find(element.id)
                                    .subscribe((res) => {
                                        // Find devid By ID
                                        console.log(
                                            "rep icii devis  quotation  complimentaire sub"
                                        );
                                        this.quotation = res;
                                        if (this.quotation.statusId == 4) {
                                            this.quotationCP = this.quotation;
                                        }
                                        if (
                                            this.sinisterPec.preliminaryReport
                                        ) {
                                            this.additionalQuote = true;
                                        }
                                        if (this.sinisterPec.modificationPrix) {
                                            this.quotation.isComplementary = true;
                                        } else {
                                            this.quotation.isComplementary = false;
                                        }
                                        this.loadAllDetailsMoForMP();
                                        this.loadAllIngredientForMP();
                                        this.loadAllRechangeForMP();
                                        this.loadAllFournitureForMP();
                                    });
                            }
                        }
                    );
                }
            }
            if (this.AvisExpert && this.quotation.expertiseDate == null) {
                this.quotation.expertiseDate = this.dateAsYYYYMMDDHHNNSS(
                    new Date()
                );
            }
        });
    }

    //connect to send Notif
    sendNotif(id, typeNotif) {
        if (id != null && id != undefined) {
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        if (typeNotif == "Missionner un expert") {
                            if (this.oneClickForButton18) {
                                this.oneClickForButton18 = false;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: id,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                };
                                this.sendNotifGlobal(
                                    25,
                                    this.sinisterPec.assignedToId,
                                    usr.id,
                                    settingJson
                                );
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 25;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 25;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.notificationAlerteService
                                                    .create(this.listNotifUser)
                                                    .subscribe((res) => {
                                                        this.ws.send(
                                                            "/app/hello",
                                                            {},
                                                            JSON.stringify({
                                                                typenotification: typeNotif,
                                                                idReparateur: id,
                                                                refSinister: this
                                                                    .sinisterPec
                                                                    .reference,
                                                                assure: this
                                                                    .assure.nom,
                                                                sinisterId: this
                                                                    .sinister
                                                                    .id,
                                                                sinisterPecId: this
                                                                    .sinisterPec
                                                                    .id,
                                                                expertId: this
                                                                    .sinisterPec
                                                                    .expertId,
                                                            })
                                                        );
                                                    });
                                            });
                                    });
                            }
                        }
                        if (typeNotif == "Avis Expert") {
                            if (this.oneClickForButton1) {
                                this.oneClickForButton1 = false;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: id,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                };
                                this.sendNotifGlobal(
                                    53,
                                    this.sinisterPec.assignedToId,
                                    usr.id,
                                    settingJson
                                );
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 53;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 53;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .expertId,
                                                        27
                                                    )
                                                    .subscribe(
                                                        (userExNotifExpert) => {
                                                            this.userExNotifExpert =
                                                                userExNotifExpert.json;
                                                            this.userExNotifExpert.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 53;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: id,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            }
                        }
                        if (typeNotif == "OK pour démontage") {
                            if (this.oneClickForButton2) {
                                this.oneClickForButton2 = false;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: id,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                };
                                this.sendNotifGlobal(
                                    27,
                                    this.sinisterPec.assignedToId,
                                    usr.id,
                                    settingJson
                                );
                                this.userExtraService
                                    .finUsersByPersonProfil(id, 28)
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.sendNotifGlobal(
                                                    27,
                                                    element.userId,
                                                    usr.id,
                                                    settingJson
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 27;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 27;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: id,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            }
                        }
                        if (
                            typeNotif ==
                            "le réparateur a rattaché accord de prise en charge signé par assuré"
                        ) {
                            if (this.oneClickForButton3) {
                                this.oneClickForButton3 = false;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: id,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                };
                                this.sendNotifGlobal(
                                    35,
                                    this.sinisterPec.assignedToId,
                                    usr.id,
                                    settingJson
                                );
                                this.userExtraService
                                    .finUsersByPersonProfil(id, 28)
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.sendNotifGlobal(
                                                    35,
                                                    element.userId,
                                                    usr.id,
                                                    settingJson
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 35;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 35;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: id,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            }
                        }
                        if (typeNotif == "Vérification") {
                            if (this.oneClickForButton4) {
                                this.oneClickForButton4 = false;
                                if (this.sinisterPec.modeId == 7) {
                                    let settingJson = {
                                        typenotification: typeNotif,
                                        idReparateur: id,
                                        refSinister: this.sinisterPec.reference,
                                        assure: this.assure.nom,
                                        sinisterId: this.sinister.id,
                                        sinisterPecId: this.sinisterPec.id,
                                        expertId: this.sinisterPec.expertId,
                                        primaryQuotationId: this.quotation.id,
                                        stepPecId: this.sinisterPec.stepId,
                                    };
                                    this.sendNotifGlobal(
                                        28,
                                        this.sinisterPec.assignedToId,
                                        usr.id,
                                        settingJson
                                    );
                                    this.userExtraService
                                        .finUsersByPersonProfil(
                                            this.sinisterPec.agencyId,
                                            24
                                        )
                                        .subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency =
                                                userExNotifAgency.json;
                                            console.log(
                                                "4-------------------------------------------------------------------"
                                            );
                                            this.userExNotifAgency.forEach(
                                                (element) => {
                                                    this.notifUser = new NotifAlertUser();
                                                    this.notification.id = 28;
                                                    this.notifUser.source =
                                                        usr.id;
                                                    this.notifUser.destination =
                                                        element.userId;
                                                    this.notifUser.notification = this.notification;
                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                    this.notifUser.entityName =
                                                        "SinisterPec";
                                                    this.notifUser.settings = JSON.stringify(
                                                        settingJson
                                                    );
                                                    this.listNotifUser.push(
                                                        this.notifUser
                                                    );
                                                }
                                            );
                                            this.userExtraService
                                                .finUsersByPersonProfil(
                                                    this.sinisterPec.partnerId,
                                                    25
                                                )
                                                .subscribe(
                                                    (userExNotifPartner) => {
                                                        this.userExNotifPartner =
                                                            userExNotifPartner.json;
                                                        this.userExNotifPartner.forEach(
                                                            (element) => {
                                                                this.notifUser = new NotifAlertUser();
                                                                this.notification.id = 28;
                                                                this.notifUser.source =
                                                                    usr.id;
                                                                this.notifUser.destination =
                                                                    element.userId;
                                                                this.notifUser.notification = this.notification;
                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                this.notifUser.entityName =
                                                                    "SinisterPec";
                                                                this.notifUser.settings = JSON.stringify(
                                                                    settingJson
                                                                );
                                                                this.listNotifUser.push(
                                                                    this
                                                                        .notifUser
                                                                );
                                                            }
                                                        );
                                                        this.notificationAlerteService
                                                            .queryReadNotificationForUser(
                                                                this.sinisterPec
                                                                    .id,
                                                                25,
                                                                usr.id
                                                            )
                                                            .subscribe(
                                                                (
                                                                    prestToRead
                                                                ) => {
                                                                    this.notificationAlerteService
                                                                        .create(
                                                                            this
                                                                                .listNotifUser
                                                                        )
                                                                        .subscribe(
                                                                            (
                                                                                res
                                                                            ) => {
                                                                                this.ws.send(
                                                                                    "/app/hello",
                                                                                    {},
                                                                                    JSON.stringify(
                                                                                        {
                                                                                            typenotification: typeNotif,
                                                                                            idReparateur: id,
                                                                                            refSinister: this
                                                                                                .sinisterPec
                                                                                                .reference,
                                                                                            assure: this
                                                                                                .assure
                                                                                                .nom,
                                                                                            sinisterId: this
                                                                                                .sinister
                                                                                                .id,
                                                                                            sinisterPecId: this
                                                                                                .sinisterPec
                                                                                                .id,
                                                                                            expertId: this
                                                                                                .sinisterPec
                                                                                                .expertId,
                                                                                            primaryQuotationId: this
                                                                                                .quotation
                                                                                                .id,
                                                                                            stepPecId: this
                                                                                                .sinisterPec
                                                                                                .stepId,
                                                                                        }
                                                                                    )
                                                                                );
                                                                            }
                                                                        );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                } else {
                                    if (this.sommeQuotation < 3000) {
                                        let settingJson = {
                                            typenotification: typeNotif,
                                            idReparateur: id,
                                            refSinister: this.sinisterPec
                                                .reference,
                                            assure: this.assure.nom,
                                            sinisterId: this.sinister.id,
                                            sinisterPecId: this.sinisterPec.id,
                                            expertId: this.sinisterPec.expertId,
                                            primaryQuotationId: this.quotation
                                                .id,
                                            stepPecId: this.sinisterPec.stepId,
                                        };
                                        this.sendNotifGlobal(
                                            28,
                                            this.sinisterPec.assignedToId,
                                            usr.id,
                                            settingJson
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 28;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 28;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .queryReadNotificationForUser(
                                                                    this
                                                                        .sinisterPec
                                                                        .id,
                                                                    25,
                                                                    usr.id
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        prestToRead
                                                                    ) => {
                                                                        this.notificationAlerteService
                                                                            .create(
                                                                                this
                                                                                    .listNotifUser
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    res
                                                                                ) => {
                                                                                    this.ws.send(
                                                                                        "/app/hello",
                                                                                        {},
                                                                                        JSON.stringify(
                                                                                            {
                                                                                                typenotification: typeNotif,
                                                                                                idReparateur: id,
                                                                                                refSinister: this
                                                                                                    .sinisterPec
                                                                                                    .reference,
                                                                                                assure: this
                                                                                                    .assure
                                                                                                    .nom,
                                                                                                sinisterId: this
                                                                                                    .sinister
                                                                                                    .id,
                                                                                                sinisterPecId: this
                                                                                                    .sinisterPec
                                                                                                    .id,
                                                                                                expertId: this
                                                                                                    .sinisterPec
                                                                                                    .expertId,
                                                                                                primaryQuotationId: this
                                                                                                    .quotation
                                                                                                    .id,
                                                                                                stepPecId: this
                                                                                                    .sinisterPec
                                                                                                    .stepId,
                                                                                            }
                                                                                        )
                                                                                    );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    } else if (
                                        this.sommeQuotation >= 3000 &&
                                        this.sommeQuotation < 5000
                                    ) {
                                        this.userExtraService
                                            .finPersonneByUser(
                                                this.sinisterPec.assignedToId
                                            )
                                            .subscribe(
                                                (usrAssigned: UserExtra) => {
                                                    let settingJson = {
                                                        typenotification: typeNotif,
                                                        idReparateur: id,
                                                        refSinister: this
                                                            .sinisterPec
                                                            .reference,
                                                        assure: this.assure.nom,
                                                        sinisterId: this
                                                            .sinister.id,
                                                        sinisterPecId: this
                                                            .sinisterPec.id,
                                                        expertId: this
                                                            .sinisterPec
                                                            .expertId,
                                                        primaryQuotationId: this
                                                            .quotation.id,
                                                        stepPecId: this
                                                            .sinisterPec.stepId,
                                                    };
                                                    this.sendNotifGlobal(
                                                        28,
                                                        usrAssigned.userBossId,
                                                        usr.id,
                                                        settingJson
                                                    );
                                                    this.userExtraService
                                                        .finUsersByPersonProfil(
                                                            this.sinisterPec
                                                                .agencyId,
                                                            24
                                                        )
                                                        .subscribe(
                                                            (
                                                                userExNotifAgency
                                                            ) => {
                                                                this.userExNotifAgency =
                                                                    userExNotifAgency.json;
                                                                console.log(
                                                                    "4-------------------------------------------------------------------"
                                                                );
                                                                this.userExNotifAgency.forEach(
                                                                    (
                                                                        element
                                                                    ) => {
                                                                        this.notifUser = new NotifAlertUser();
                                                                        this.notification.id = 28;
                                                                        this.notifUser.source =
                                                                            usr.id;
                                                                        this.notifUser.destination =
                                                                            element.userId;
                                                                        this.notifUser.notification = this.notification;
                                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                                        this.notifUser.entityName =
                                                                            "SinisterPec";
                                                                        this.notifUser.settings = JSON.stringify(
                                                                            settingJson
                                                                        );
                                                                        this.listNotifUser.push(
                                                                            this
                                                                                .notifUser
                                                                        );
                                                                    }
                                                                );
                                                                this.userExtraService
                                                                    .finUsersByPersonProfil(
                                                                        this
                                                                            .sinisterPec
                                                                            .partnerId,
                                                                        25
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            userExNotifPartner
                                                                        ) => {
                                                                            this.userExNotifPartner =
                                                                                userExNotifPartner.json;
                                                                            this.userExNotifPartner.forEach(
                                                                                (
                                                                                    element
                                                                                ) => {
                                                                                    this.notifUser = new NotifAlertUser();
                                                                                    this.notification.id = 28;
                                                                                    this.notifUser.source =
                                                                                        usr.id;
                                                                                    this.notifUser.destination =
                                                                                        element.userId;
                                                                                    this.notifUser.notification = this.notification;
                                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                                    this.notifUser.entityName =
                                                                                        "SinisterPec";
                                                                                    this.notifUser.settings = JSON.stringify(
                                                                                        settingJson
                                                                                    );
                                                                                    this.listNotifUser.push(
                                                                                        this
                                                                                            .notifUser
                                                                                    );
                                                                                }
                                                                            );
                                                                            this.notificationAlerteService
                                                                                .queryReadNotificationForUser(
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    25,
                                                                                    usr.id
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        prestToRead
                                                                                    ) => {
                                                                                        this.notificationAlerteService
                                                                                            .create(
                                                                                                this
                                                                                                    .listNotifUser
                                                                                            )
                                                                                            .subscribe(
                                                                                                (
                                                                                                    res
                                                                                                ) => {
                                                                                                    this.ws.send(
                                                                                                        "/app/hello",
                                                                                                        {},
                                                                                                        JSON.stringify(
                                                                                                            {
                                                                                                                typenotification: typeNotif,
                                                                                                                idReparateur: id,
                                                                                                                refSinister: this
                                                                                                                    .sinisterPec
                                                                                                                    .reference,
                                                                                                                assure: this
                                                                                                                    .assure
                                                                                                                    .nom,
                                                                                                                sinisterId: this
                                                                                                                    .sinister
                                                                                                                    .id,
                                                                                                                sinisterPecId: this
                                                                                                                    .sinisterPec
                                                                                                                    .id,
                                                                                                                expertId: this
                                                                                                                    .sinisterPec
                                                                                                                    .expertId,
                                                                                                                primaryQuotationId: this
                                                                                                                    .quotation
                                                                                                                    .id,
                                                                                                                stepPecId: this
                                                                                                                    .sinisterPec
                                                                                                                    .stepId,
                                                                                                            }
                                                                                                        )
                                                                                                    );
                                                                                                }
                                                                                            );
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                }
                                            );
                                    } else if (
                                        this.sommeQuotation >= 5000 &&
                                        this.sommeQuotation < 15000
                                    ) {
                                        this.userExtraService
                                            .findByProfil(21)
                                            .subscribe(
                                                (
                                                    userExNotifAgency: UserExtra[]
                                                ) => {
                                                    this.userExNotifAgency = userExNotifAgency;
                                                    let settingJson = {
                                                        typenotification: typeNotif,
                                                        idReparateur: id,
                                                        refSinister: this
                                                            .sinisterPec
                                                            .reference,
                                                        assure: this.assure.nom,
                                                        sinisterId: this
                                                            .sinister.id,
                                                        sinisterPecId: this
                                                            .sinisterPec.id,
                                                        expertId: this
                                                            .sinisterPec
                                                            .expertId,
                                                        primaryQuotationId: this
                                                            .quotation.id,
                                                        stepPecId: this
                                                            .sinisterPec.stepId,
                                                    };
                                                    this.userExNotifAgency.forEach(
                                                        (element) => {
                                                            this.sendNotifGlobal(
                                                                28,
                                                                element.userId,
                                                                usr.id,
                                                                settingJson
                                                            );
                                                        }
                                                    );
                                                    this.userExtraService
                                                        .finUsersByPersonProfil(
                                                            this.sinisterPec
                                                                .agencyId,
                                                            24
                                                        )
                                                        .subscribe(
                                                            (
                                                                userExNotifAgency
                                                            ) => {
                                                                this.userExNotifAgency =
                                                                    userExNotifAgency.json;
                                                                console.log(
                                                                    "4-------------------------------------------------------------------"
                                                                );
                                                                this.userExNotifAgency.forEach(
                                                                    (
                                                                        element
                                                                    ) => {
                                                                        this.notifUser = new NotifAlertUser();
                                                                        this.notification.id = 28;
                                                                        this.notifUser.source =
                                                                            usr.id;
                                                                        this.notifUser.destination =
                                                                            element.userId;
                                                                        this.notifUser.notification = this.notification;
                                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                                        this.notifUser.entityName =
                                                                            "SinisterPec";
                                                                        this.notifUser.settings = JSON.stringify(
                                                                            settingJson
                                                                        );
                                                                        this.listNotifUser.push(
                                                                            this
                                                                                .notifUser
                                                                        );
                                                                    }
                                                                );
                                                                this.userExtraService
                                                                    .finUsersByPersonProfil(
                                                                        this
                                                                            .sinisterPec
                                                                            .partnerId,
                                                                        25
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            userExNotifPartner
                                                                        ) => {
                                                                            this.userExNotifPartner =
                                                                                userExNotifPartner.json;
                                                                            this.userExNotifPartner.forEach(
                                                                                (
                                                                                    element
                                                                                ) => {
                                                                                    this.notifUser = new NotifAlertUser();
                                                                                    this.notification.id = 28;
                                                                                    this.notifUser.source =
                                                                                        usr.id;
                                                                                    this.notifUser.destination =
                                                                                        element.userId;
                                                                                    this.notifUser.notification = this.notification;
                                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                                    this.notifUser.entityName =
                                                                                        "SinisterPec";
                                                                                    this.notifUser.settings = JSON.stringify(
                                                                                        settingJson
                                                                                    );
                                                                                    this.listNotifUser.push(
                                                                                        this
                                                                                            .notifUser
                                                                                    );
                                                                                }
                                                                            );
                                                                            this.notificationAlerteService
                                                                                .queryReadNotificationForUser(
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    25,
                                                                                    usr.id
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        prestToRead
                                                                                    ) => {
                                                                                        this.notificationAlerteService
                                                                                            .create(
                                                                                                this
                                                                                                    .listNotifUser
                                                                                            )
                                                                                            .subscribe(
                                                                                                (
                                                                                                    res
                                                                                                ) => {
                                                                                                    this.ws.send(
                                                                                                        "/app/hello",
                                                                                                        {},
                                                                                                        JSON.stringify(
                                                                                                            {
                                                                                                                typenotification: typeNotif,
                                                                                                                idReparateur: id,
                                                                                                                refSinister: this
                                                                                                                    .sinisterPec
                                                                                                                    .reference,
                                                                                                                assure: this
                                                                                                                    .assure
                                                                                                                    .nom,
                                                                                                                sinisterId: this
                                                                                                                    .sinister
                                                                                                                    .id,
                                                                                                                sinisterPecId: this
                                                                                                                    .sinisterPec
                                                                                                                    .id,
                                                                                                                expertId: this
                                                                                                                    .sinisterPec
                                                                                                                    .expertId,
                                                                                                                primaryQuotationId: this
                                                                                                                    .quotation
                                                                                                                    .id,
                                                                                                                stepPecId: this
                                                                                                                    .sinisterPec
                                                                                                                    .stepId,
                                                                                                            }
                                                                                                        )
                                                                                                    );
                                                                                                }
                                                                                            );
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                }
                                            );
                                    } else if (this.sommeQuotation >= 15000) {
                                        this.userExtraService
                                            .finPersonneByUser(
                                                this.sinisterPec.assignedToId
                                            )
                                            .subscribe(
                                                (usrAssigned: UserExtra) => {
                                                    this.userExtraService
                                                        .finPersonneByUser(
                                                            usrAssigned.userBossId
                                                        )
                                                        .subscribe(
                                                            (
                                                                usrBoss: UserExtra
                                                            ) => {
                                                                let settingJson = {
                                                                    typenotification: typeNotif,
                                                                    idReparateur: id,
                                                                    refSinister: this
                                                                        .sinisterPec
                                                                        .reference,
                                                                    assure: this
                                                                        .assure
                                                                        .nom,
                                                                    sinisterId: this
                                                                        .sinister
                                                                        .id,
                                                                    sinisterPecId: this
                                                                        .sinisterPec
                                                                        .id,
                                                                    expertId: this
                                                                        .sinisterPec
                                                                        .expertId,
                                                                    primaryQuotationId: this
                                                                        .quotation
                                                                        .id,
                                                                    stepPecId: this
                                                                        .sinisterPec
                                                                        .stepId,
                                                                };
                                                                this.sendNotifGlobal(
                                                                    28,
                                                                    usrBoss.userBossId,
                                                                    usr.id,
                                                                    settingJson
                                                                );
                                                                this.userExtraService
                                                                    .findByProfil(
                                                                        21
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            userExNotifAgency: UserExtra[]
                                                                        ) => {
                                                                            this.userExNotifAgency = userExNotifAgency;
                                                                            this.userExNotifAgency.forEach(
                                                                                (
                                                                                    element
                                                                                ) => {
                                                                                    this.sendNotifGlobal(
                                                                                        28,
                                                                                        element.userId,
                                                                                        usr.id,
                                                                                        settingJson
                                                                                    );
                                                                                }
                                                                            );
                                                                            this.userExtraService
                                                                                .finUsersByPersonProfil(
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .agencyId,
                                                                                    24
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        userExNotifAgency
                                                                                    ) => {
                                                                                        this.userExNotifAgency =
                                                                                            userExNotifAgency.json;
                                                                                        console.log(
                                                                                            "4-------------------------------------------------------------------"
                                                                                        );
                                                                                        this.userExNotifAgency.forEach(
                                                                                            (
                                                                                                element
                                                                                            ) => {
                                                                                                this.notifUser = new NotifAlertUser();
                                                                                                this.notification.id = 28;
                                                                                                this.notifUser.source =
                                                                                                    usr.id;
                                                                                                this.notifUser.destination =
                                                                                                    element.userId;
                                                                                                this.notifUser.notification = this.notification;
                                                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                                                this.notifUser.entityName =
                                                                                                    "SinisterPec";
                                                                                                this.notifUser.settings = JSON.stringify(
                                                                                                    settingJson
                                                                                                );
                                                                                                this.listNotifUser.push(
                                                                                                    this
                                                                                                        .notifUser
                                                                                                );
                                                                                            }
                                                                                        );
                                                                                        this.userExtraService
                                                                                            .finUsersByPersonProfil(
                                                                                                this
                                                                                                    .sinisterPec
                                                                                                    .partnerId,
                                                                                                25
                                                                                            )
                                                                                            .subscribe(
                                                                                                (
                                                                                                    userExNotifPartner
                                                                                                ) => {
                                                                                                    this.userExNotifPartner =
                                                                                                        userExNotifPartner.json;
                                                                                                    this.userExNotifPartner.forEach(
                                                                                                        (
                                                                                                            element
                                                                                                        ) => {
                                                                                                            this.notifUser = new NotifAlertUser();
                                                                                                            this.notification.id = 28;
                                                                                                            this.notifUser.source =
                                                                                                                usr.id;
                                                                                                            this.notifUser.destination =
                                                                                                                element.userId;
                                                                                                            this.notifUser.entityId = this.sinisterPec.id;
                                                                                                            this.notifUser.entityName =
                                                                                                                "SinisterPec";
                                                                                                            this.notifUser.notification = this.notification;
                                                                                                            this.notifUser.settings = JSON.stringify(
                                                                                                                settingJson
                                                                                                            );
                                                                                                            this.listNotifUser.push(
                                                                                                                this
                                                                                                                    .notifUser
                                                                                                            );
                                                                                                        }
                                                                                                    );
                                                                                                    this.notificationAlerteService
                                                                                                        .queryReadNotificationForUser(
                                                                                                            this
                                                                                                                .sinisterPec
                                                                                                                .id,
                                                                                                            25,
                                                                                                            usr.id
                                                                                                        )
                                                                                                        .subscribe(
                                                                                                            (
                                                                                                                prestToRead
                                                                                                            ) => {
                                                                                                                this.notificationAlerteService
                                                                                                                    .create(
                                                                                                                        this
                                                                                                                            .listNotifUser
                                                                                                                    )
                                                                                                                    .subscribe(
                                                                                                                        (
                                                                                                                            res
                                                                                                                        ) => {
                                                                                                                            this.ws.send(
                                                                                                                                "/app/hello",
                                                                                                                                {},
                                                                                                                                JSON.stringify(
                                                                                                                                    {
                                                                                                                                        typenotification: typeNotif,
                                                                                                                                        idReparateur: id,
                                                                                                                                        refSinister: this
                                                                                                                                            .sinisterPec
                                                                                                                                            .reference,
                                                                                                                                        assure: this
                                                                                                                                            .assure
                                                                                                                                            .nom,
                                                                                                                                        sinisterId: this
                                                                                                                                            .sinister
                                                                                                                                            .id,
                                                                                                                                        sinisterPecId: this
                                                                                                                                            .sinisterPec
                                                                                                                                            .id,
                                                                                                                                        expertId: this
                                                                                                                                            .sinisterPec
                                                                                                                                            .expertId,
                                                                                                                                        primaryQuotationId: this
                                                                                                                                            .quotation
                                                                                                                                            .id,
                                                                                                                                        stepPecId: this
                                                                                                                                            .sinisterPec
                                                                                                                                            .stepId,
                                                                                                                                    }
                                                                                                                                )
                                                                                                                            );
                                                                                                                        }
                                                                                                                    );
                                                                                                            }
                                                                                                        );
                                                                                                }
                                                                                            );
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                }
                                            );
                                    }
                                }
                            }
                        }
                        if (typeNotif == "Validation Devis") {
                            if (this.oneClickForButton5) {
                                this.oneClickForButton5 = false;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: id,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                };
                                this.sendNotifGlobal(
                                    29,
                                    this.sinisterPec.assignedToId,
                                    usr.id,
                                    settingJson
                                );
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 29;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 29;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.notificationAlerteService
                                                    .create(this.listNotifUser)
                                                    .subscribe((res) => {
                                                        this.ws.send(
                                                            "/app/hello",
                                                            {},
                                                            JSON.stringify({
                                                                typenotification: typeNotif,
                                                                idReparateur: id,
                                                                refSinister: this
                                                                    .sinisterPec
                                                                    .reference,
                                                                assure: this
                                                                    .assure.nom,
                                                                sinisterId: this
                                                                    .sinister
                                                                    .id,
                                                                sinisterPecId: this
                                                                    .sinisterPec
                                                                    .id,
                                                                expertId: this
                                                                    .sinisterPec
                                                                    .expertId,
                                                            })
                                                        );
                                                    });
                                            });
                                    });
                            }
                        }
                        if (
                            typeNotif ==
                            "Accord pour réparation avec modification"
                        ) {
                            if (this.oneClickForButton6) {
                                this.oneClickForButton6 = false;
                                this.userExtraService
                                    .finUsersByPersonProfil(id, 28)
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                let settingJson = {
                                                    typenotification: typeNotif,
                                                    idReparateur: id,
                                                    refSinister: this
                                                        .sinisterPec.reference,
                                                    assure: this.assure.nom,
                                                    sinisterId: this.sinister
                                                        .id,
                                                    sinisterPecId: this
                                                        .sinisterPec.id,
                                                    expertId: this.sinisterPec
                                                        .expertId,
                                                    primaryQuotationId: this
                                                        .quotation.id,
                                                };
                                                this.sendNotifGlobal(
                                                    30,
                                                    element.userId,
                                                    usr.id,
                                                    settingJson
                                                );
                                            }
                                        );
                                        let settingJson = {
                                            typenotification: typeNotif,
                                            idReparateur: id,
                                            refSinister: this.sinisterPec
                                                .reference,
                                            assure: this.assure.nom,
                                            sinisterId: this.sinister.id,
                                            sinisterPecId: this.sinisterPec.id,
                                            expertId: this.sinisterPec.expertId,
                                            primaryQuotationId: this.quotation
                                                .id,
                                        };
                                        this.sendNotifGlobal(
                                            30,
                                            this.sinisterPec.assignedToId,
                                            usr.id,
                                            settingJson
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 30;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 30;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: id,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                    primaryQuotationId: this
                                                                                        .quotation
                                                                                        .id,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            }
                        }
                        if (typeNotif == "Rectification Devis") {
                            if (this.oneClickForButton77) {
                                this.oneClickForButton77 = false;
                                this.userExtraService
                                    .finUsersByPersonProfil(id, 28)
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        let settingJson = {
                                            typenotification: typeNotif,
                                            idReparateur: id,
                                            refSinister: this.sinisterPec
                                                .reference,
                                            assure: this.assure.nom,
                                            sinisterId: this.sinister.id,
                                            sinisterPecId: this.sinisterPec.id,
                                            expertId: this.sinisterPec.expertId,
                                            primaryQuotationId: this.quotation
                                                .id,
                                        };
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.sendNotifGlobal(
                                                    49,
                                                    element.userId,
                                                    usr.id,
                                                    settingJson
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 49;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 49;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: id,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                    primaryQuotationId: this
                                                                                        .quotation
                                                                                        .id,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            }
                        }

                        if (typeNotif == "Devis Confirmé par le réparateur") {
                            if (this.oneClickForButton7) {
                                this.oneClickForButton7 = false;
                                if (this.sinisterPec.expertId != null) {
                                    this.userExtraService
                                        .finUsersByPersonProfil(
                                            this.sinisterPec.expertId,
                                            27
                                        )
                                        .subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency =
                                                userExNotifAgency.json;
                                            let settingJson = {
                                                typenotification: typeNotif,
                                                idReparateur: id,
                                                refSinister: this.sinisterPec
                                                    .reference,
                                                assure: this.assure.nom,
                                                sinisterId: this.sinister.id,
                                                sinisterPecId: this.sinisterPec
                                                    .id,
                                                expertId: this.sinisterPec
                                                    .expertId,
                                                primaryQuotationId: this
                                                    .quotation.id,
                                            };
                                            this.userExNotifAgency.forEach(
                                                (element) => {
                                                    this.sendNotifGlobal(
                                                        31,
                                                        element.userId,
                                                        usr.id,
                                                        settingJson
                                                    );
                                                }
                                            );
                                            this.userExtraService
                                                .finUsersByPersonProfil(
                                                    this.sinisterPec.agencyId,
                                                    24
                                                )
                                                .subscribe(
                                                    (userExNotifAgency) => {
                                                        this.userExNotifAgency =
                                                            userExNotifAgency.json;
                                                        console.log(
                                                            "4-------------------------------------------------------------------"
                                                        );
                                                        this.userExNotifAgency.forEach(
                                                            (element) => {
                                                                this.notifUser = new NotifAlertUser();
                                                                this.notification.id = 31;
                                                                this.notifUser.source =
                                                                    usr.id;
                                                                this.notifUser.destination =
                                                                    element.userId;
                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                this.notifUser.entityName =
                                                                    "SinisterPec";
                                                                this.notifUser.notification = this.notification;
                                                                this.notifUser.settings = JSON.stringify(
                                                                    settingJson
                                                                );
                                                                this.listNotifUser.push(
                                                                    this
                                                                        .notifUser
                                                                );
                                                            }
                                                        );
                                                        this.userExtraService
                                                            .finUsersByPersonProfil(
                                                                this.sinisterPec
                                                                    .partnerId,
                                                                25
                                                            )
                                                            .subscribe(
                                                                (
                                                                    userExNotifPartner
                                                                ) => {
                                                                    this.userExNotifPartner =
                                                                        userExNotifPartner.json;
                                                                    this.userExNotifPartner.forEach(
                                                                        (
                                                                            element
                                                                        ) => {
                                                                            this.notifUser = new NotifAlertUser();
                                                                            this.notification.id = 31;
                                                                            this.notifUser.source =
                                                                                usr.id;
                                                                            this.notifUser.destination =
                                                                                element.userId;
                                                                            this.notifUser.entityId = this.sinisterPec.id;
                                                                            this.notifUser.entityName =
                                                                                "SinisterPec";
                                                                            this.notifUser.notification = this.notification;
                                                                            this.notifUser.settings = JSON.stringify(
                                                                                settingJson
                                                                            );
                                                                            this.listNotifUser.push(
                                                                                this
                                                                                    .notifUser
                                                                            );
                                                                        }
                                                                    );
                                                                    this.notificationAlerteService
                                                                        .create(
                                                                            this
                                                                                .listNotifUser
                                                                        )
                                                                        .subscribe(
                                                                            (
                                                                                res
                                                                            ) => {
                                                                                this.ws.send(
                                                                                    "/app/hello",
                                                                                    {},
                                                                                    JSON.stringify(
                                                                                        {
                                                                                            typenotification: typeNotif,
                                                                                            idReparateur: id,
                                                                                            refSinister: this
                                                                                                .sinisterPec
                                                                                                .reference,
                                                                                            assure: this
                                                                                                .assure
                                                                                                .nom,
                                                                                            sinisterId: this
                                                                                                .sinister
                                                                                                .id,
                                                                                            sinisterPecId: this
                                                                                                .sinisterPec
                                                                                                .id,
                                                                                            expertId: this
                                                                                                .sinisterPec
                                                                                                .expertId,
                                                                                            primaryQuotationId: this
                                                                                                .quotation
                                                                                                .id,
                                                                                        }
                                                                                    )
                                                                                );
                                                                            }
                                                                        );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                } else {
                                    let settingJson = {
                                        typenotification: typeNotif,
                                        idReparateur: id,
                                        refSinister: this.sinisterPec.reference,
                                        assure: this.assure.nom,
                                        sinisterId: this.sinister.id,
                                        sinisterPecId: this.sinisterPec.id,
                                        primaryQuotationId: this.quotation.id,
                                    };
                                    this.sendNotifGlobal(
                                        31,
                                        this.sinisterPec.assignedToId,
                                        usr.id,
                                        settingJson
                                    );
                                    this.userExtraService
                                        .finUsersByPersonProfil(
                                            this.sinisterPec.agencyId,
                                            24
                                        )
                                        .subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency =
                                                userExNotifAgency.json;
                                            console.log(
                                                "4-------------------------------------------------------------------"
                                            );
                                            this.userExNotifAgency.forEach(
                                                (element) => {
                                                    this.notifUser = new NotifAlertUser();
                                                    this.notification.id = 31;
                                                    this.notifUser.source =
                                                        usr.id;
                                                    this.notifUser.destination =
                                                        element.userId;
                                                    this.notifUser.notification = this.notification;
                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                    this.notifUser.entityName =
                                                        "SinisterPec";
                                                    this.notifUser.settings = JSON.stringify(
                                                        settingJson
                                                    );
                                                    this.listNotifUser.push(
                                                        this.notifUser
                                                    );
                                                }
                                            );
                                            this.userExtraService
                                                .finUsersByPersonProfil(
                                                    this.sinisterPec.partnerId,
                                                    25
                                                )
                                                .subscribe(
                                                    (userExNotifPartner) => {
                                                        this.userExNotifPartner =
                                                            userExNotifPartner.json;
                                                        this.userExNotifPartner.forEach(
                                                            (element) => {
                                                                this.notifUser = new NotifAlertUser();
                                                                this.notification.id = 31;
                                                                this.notifUser.source =
                                                                    usr.id;
                                                                this.notifUser.destination =
                                                                    element.userId;
                                                                this.notifUser.notification = this.notification;
                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                this.notifUser.entityName =
                                                                    "SinisterPec";
                                                                this.notifUser.settings = JSON.stringify(
                                                                    settingJson
                                                                );
                                                                this.listNotifUser.push(
                                                                    this
                                                                        .notifUser
                                                                );
                                                            }
                                                        );
                                                        this.notificationAlerteService
                                                            .create(
                                                                this
                                                                    .listNotifUser
                                                            )
                                                            .subscribe(
                                                                (res) => {
                                                                    this.ws.send(
                                                                        "/app/hello",
                                                                        {},
                                                                        JSON.stringify(
                                                                            {
                                                                                typenotification: typeNotif,
                                                                                idReparateur: id,
                                                                                refSinister: this
                                                                                    .sinisterPec
                                                                                    .reference,
                                                                                assure: this
                                                                                    .assure
                                                                                    .nom,
                                                                                sinisterId: this
                                                                                    .sinister
                                                                                    .id,
                                                                                sinisterPecId: this
                                                                                    .sinisterPec
                                                                                    .id,
                                                                                primaryQuotationId: this
                                                                                    .quotation
                                                                                    .id,
                                                                            }
                                                                        )
                                                                    );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                }
                            }
                        }
                        if (typeNotif == "Accord pour réparation") {
                            if (this.oneClickForButton8) {
                                this.oneClickForButton8 = false;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: id,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                    primaryQuotationId: this.quotation.id,
                                };
                                this.sendNotifGlobal(
                                    32,
                                    this.sinisterPec.assignedToId,
                                    usr.id,
                                    settingJson
                                );
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 32;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 32;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.notificationAlerteService
                                                    .create(this.listNotifUser)
                                                    .subscribe((res) => {
                                                        this.ws.send(
                                                            "/app/hello",
                                                            {},
                                                            JSON.stringify({
                                                                typenotification: typeNotif,
                                                                idReparateur: id,
                                                                refSinister: this
                                                                    .sinisterPec
                                                                    .reference,
                                                                assure: this
                                                                    .assure.nom,
                                                                sinisterId: this
                                                                    .sinister
                                                                    .id,
                                                                sinisterPecId: this
                                                                    .sinisterPec
                                                                    .id,
                                                                expertId: this
                                                                    .sinisterPec
                                                                    .expertId,
                                                                primaryQuotationId: this
                                                                    .quotation
                                                                    .id,
                                                            })
                                                        );
                                                    });
                                            });
                                    });
                            }
                        }
                        if (
                            typeNotif == "Devis non confirmé par le réparateur"
                        ) {
                            if (this.oneClickForButton9) {
                                this.oneClickForButton9 = false;
                                if (this.sinisterPec.expertId != null) {
                                    this.userExtraService
                                        .finUsersByPersonProfil(
                                            this.sinisterPec.expertId,
                                            27
                                        )
                                        .subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency =
                                                userExNotifAgency.json;
                                            let settingJson = {
                                                typenotification: typeNotif,
                                                idReparateur: id,
                                                refSinister: this.sinisterPec
                                                    .reference,
                                                assure: this.assure.nom,
                                                sinisterId: this.sinister.id,
                                                sinisterPecId: this.sinisterPec
                                                    .id,
                                                expertId: this.sinisterPec
                                                    .expertId,
                                                primaryQuotationId: this
                                                    .quotation.id,
                                            };
                                            this.userExNotifAgency.forEach(
                                                (element) => {
                                                    this.sendNotifGlobal(
                                                        33,
                                                        element.userId,
                                                        usr.id,
                                                        settingJson
                                                    );
                                                }
                                            );
                                            this.userExtraService
                                                .finUsersByPersonProfil(
                                                    this.sinisterPec.agencyId,
                                                    24
                                                )
                                                .subscribe(
                                                    (userExNotifAgency) => {
                                                        this.userExNotifAgency =
                                                            userExNotifAgency.json;
                                                        console.log(
                                                            "4-------------------------------------------------------------------"
                                                        );
                                                        this.userExNotifAgency.forEach(
                                                            (element) => {
                                                                this.notifUser = new NotifAlertUser();
                                                                this.notification.id = 33;
                                                                this.notifUser.source =
                                                                    usr.id;
                                                                this.notifUser.destination =
                                                                    element.userId;
                                                                this.notifUser.notification = this.notification;
                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                this.notifUser.entityName =
                                                                    "SinisterPec";
                                                                this.notifUser.settings = JSON.stringify(
                                                                    settingJson
                                                                );
                                                                this.listNotifUser.push(
                                                                    this
                                                                        .notifUser
                                                                );
                                                            }
                                                        );
                                                        this.userExtraService
                                                            .finUsersByPersonProfil(
                                                                this.sinisterPec
                                                                    .partnerId,
                                                                25
                                                            )
                                                            .subscribe(
                                                                (
                                                                    userExNotifPartner
                                                                ) => {
                                                                    this.userExNotifPartner =
                                                                        userExNotifPartner.json;
                                                                    this.userExNotifPartner.forEach(
                                                                        (
                                                                            element
                                                                        ) => {
                                                                            this.notifUser = new NotifAlertUser();
                                                                            this.notification.id = 33;
                                                                            this.notifUser.source =
                                                                                usr.id;
                                                                            this.notifUser.destination =
                                                                                element.userId;
                                                                            this.notifUser.notification = this.notification;
                                                                            this.notifUser.entityId = this.sinisterPec.id;
                                                                            this.notifUser.entityName =
                                                                                "SinisterPec";
                                                                            this.notifUser.settings = JSON.stringify(
                                                                                settingJson
                                                                            );
                                                                            this.listNotifUser.push(
                                                                                this
                                                                                    .notifUser
                                                                            );
                                                                        }
                                                                    );
                                                                    this.notificationAlerteService
                                                                        .create(
                                                                            this
                                                                                .listNotifUser
                                                                        )
                                                                        .subscribe(
                                                                            (
                                                                                res
                                                                            ) => {
                                                                                this.ws.send(
                                                                                    "/app/hello",
                                                                                    {},
                                                                                    JSON.stringify(
                                                                                        {
                                                                                            typenotification: typeNotif,
                                                                                            idReparateur: id,
                                                                                            refSinister: this
                                                                                                .sinisterPec
                                                                                                .reference,
                                                                                            assure: this
                                                                                                .assure
                                                                                                .nom,
                                                                                            sinisterId: this
                                                                                                .sinister
                                                                                                .id,
                                                                                            sinisterPecId: this
                                                                                                .sinisterPec
                                                                                                .id,
                                                                                            expertId: this
                                                                                                .sinisterPec
                                                                                                .expertId,
                                                                                            primaryQuotationId: this
                                                                                                .quotation
                                                                                                .id,
                                                                                        }
                                                                                    )
                                                                                );
                                                                            }
                                                                        );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                } else {
                                    let settingJson = {
                                        typenotification: typeNotif,
                                        idReparateur: id,
                                        refSinister: this.sinisterPec.reference,
                                        assure: this.assure.nom,
                                        sinisterId: this.sinister.id,
                                        sinisterPecId: this.sinisterPec.id,
                                        primaryQuotationId: this.quotation.id,
                                    };
                                    this.sendNotifGlobal(
                                        33,
                                        this.sinisterPec.assignedToId,
                                        usr.id,
                                        settingJson
                                    );
                                    this.userExtraService
                                        .finUsersByPersonProfil(
                                            this.sinisterPec.agencyId,
                                            24
                                        )
                                        .subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency =
                                                userExNotifAgency.json;
                                            console.log(
                                                "4-------------------------------------------------------------------"
                                            );
                                            this.userExNotifAgency.forEach(
                                                (element) => {
                                                    this.notifUser = new NotifAlertUser();
                                                    this.notification.id = 33;
                                                    this.notifUser.source =
                                                        usr.id;
                                                    this.notifUser.destination =
                                                        element.userId;
                                                    this.notifUser.notification = this.notification;
                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                    this.notifUser.entityName =
                                                        "SinisterPec";
                                                    this.notifUser.settings = JSON.stringify(
                                                        settingJson
                                                    );
                                                    this.listNotifUser.push(
                                                        this.notifUser
                                                    );
                                                }
                                            );
                                            this.userExtraService
                                                .finUsersByPersonProfil(
                                                    this.sinisterPec.partnerId,
                                                    25
                                                )
                                                .subscribe(
                                                    (userExNotifPartner) => {
                                                        this.userExNotifPartner =
                                                            userExNotifPartner.json;
                                                        this.userExNotifPartner.forEach(
                                                            (element) => {
                                                                this.notifUser = new NotifAlertUser();
                                                                this.notification.id = 33;
                                                                this.notifUser.source =
                                                                    usr.id;
                                                                this.notifUser.destination =
                                                                    element.userId;
                                                                this.notifUser.notification = this.notification;
                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                this.notifUser.entityName =
                                                                    "SinisterPec";
                                                                this.notifUser.settings = JSON.stringify(
                                                                    settingJson
                                                                );
                                                                this.listNotifUser.push(
                                                                    this
                                                                        .notifUser
                                                                );
                                                            }
                                                        );
                                                        this.notificationAlerteService
                                                            .create(
                                                                this
                                                                    .listNotifUser
                                                            )
                                                            .subscribe(
                                                                (res) => {
                                                                    this.ws.send(
                                                                        "/app/hello",
                                                                        {},
                                                                        JSON.stringify(
                                                                            {
                                                                                typenotification: typeNotif,
                                                                                idReparateur: id,
                                                                                refSinister: this
                                                                                    .sinisterPec
                                                                                    .reference,
                                                                                assure: this
                                                                                    .assure
                                                                                    .nom,
                                                                                sinisterId: this
                                                                                    .sinister
                                                                                    .id,
                                                                                sinisterPecId: this
                                                                                    .sinisterPec
                                                                                    .id,
                                                                                primaryQuotationId: this
                                                                                    .quotation
                                                                                    .id,
                                                                            }
                                                                        )
                                                                    );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                }
                            }
                        }
                        if (typeNotif == "Annulée suite à la décision expert") {
                            if (this.oneClickForButton10) {
                                this.oneClickForButton10 = false;
                                this.userExtraService
                                    .finPersonneByUser(
                                        this.sinisterPec.assignedToId
                                    )
                                    .subscribe((usrBossAssigned: UserExtra) => {
                                        let settingJson = {
                                            typenotification: typeNotif,
                                            idReparateur: id,
                                            refSinister: this.sinisterPec
                                                .reference,
                                            assure: this.assure.nom,
                                            sinisterId: this.sinister.id,
                                            sinisterPecId: this.sinisterPec.id,
                                            expertId: this.sinisterPec.expertId,
                                            primaryQuotationId: this.quotation
                                                .id,
                                        };
                                        this.sendNotifGlobal(
                                            35,
                                            usrBossAssigned.userBossId,
                                            usr.id,
                                            settingJson
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 35;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 35;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: id,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                    primaryQuotationId: this
                                                                                        .quotation
                                                                                        .id,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            }
                        }
                    });
            });
        }
    }

    getPieceForAttachment(
        entityName: string,
        attachmentId: number,
        originalName: string
    ) {
        this.sinisterPecService
            .getPieceByAttachmentIdAndEntityName(entityName, attachmentId)
            .subscribe(
                (blob: Blob) => {
                    let fileName = originalName;
                    console.log(fileName);

                    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                        window.navigator.msSaveOrOpenBlob(blob, fileName);
                    } else {
                        var a = document.createElement("a");
                        a.href = URL.createObjectURL(blob);
                        a.download = fileName;
                        a.target = "_blank";
                        document.body.appendChild(a);
                        a.click();
                        document.body.removeChild(a);
                    }
                },
                (err) => {
                    alert(
                        "Error while downloading. File Not Found on the Server"
                    );
                }
            );
    }

    sendNotifConfirmationDevisComplementaire(typeNotif, idApec) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService
                .finPersonneByUser(this.currentAccount.id)
                .subscribe((usr: UserExtra) => {
                    if (this.oneClickForButton5) {
                        this.oneClickForButton5 = false;
                        let settingJson = {
                            typenotification: typeNotif,
                            idReparateur: this.sinisterPec.reparateurId,
                            refSinister: this.sinisterPec.reference,
                            assure: this.assure.nom,
                            sinisterId: this.sinister.id,
                            sinisterPecId: this.sinisterPec.id,
                            expertId: this.sinisterPec.expertId,
                            idApec: this.apec.id,
                            stepPecId: this.sinisterPec.stepId,
                        };
                        this.sendNotifGlobal(
                            61,
                            this.sinisterPec.assignedToId,
                            usr.id,
                            settingJson
                        );
                        this.userExtraService
                            .finUsersByPersonProfil(
                                this.sinisterPec.expertId,
                                27
                            )
                            .subscribe((userExNotifExpert) => {
                                this.userExNotifExpert = userExNotifExpert.json;
                                console.log(
                                    "4-------------------------------------------------------------------"
                                );
                                this.userExNotifExpert.forEach((element) => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 61;
                                    this.notifUser.source = usr.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(
                                        settingJson
                                    );
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.reparateurId,
                                        28
                                    )
                                    .subscribe((userExNotifReparateur) => {
                                        this.userExNotifReparateur =
                                            userExNotifReparateur.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifReparateur.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 61;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.agencyId,
                                                24
                                            )
                                            .subscribe((userExNotifAgency) => {
                                                this.userExNotifAgency =
                                                    userExNotifAgency.json;
                                                console.log(
                                                    "4-------------------------------------------------------------------"
                                                );
                                                this.userExNotifAgency.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 61;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.notifUser = new NotifAlertUser();
                                                                    this.notification.id = 61;
                                                                    this.notifUser.source =
                                                                        usr.id;
                                                                    this.notifUser.destination =
                                                                        element.userId;
                                                                    this.notifUser.notification = this.notification;
                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                    this.notifUser.entityName =
                                                                        "SinisterPec";
                                                                    this.notifUser.settings = JSON.stringify(
                                                                        settingJson
                                                                    );
                                                                    this.listNotifUser.push(
                                                                        this
                                                                            .notifUser
                                                                    );
                                                                }
                                                            );
                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                {
                                                                                    typenotification: typeNotif,
                                                                                    idReparateur: this
                                                                                        .sinisterPec
                                                                                        .reparateurId,
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                    assure: this
                                                                                        .assure
                                                                                        .nom,
                                                                                    sinisterId: this
                                                                                        .sinister
                                                                                        .id,
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    expertId: this
                                                                                        .sinisterPec
                                                                                        .expertId,
                                                                                    idApec: this
                                                                                        .apec
                                                                                        .id,
                                                                                    stepPecId: this
                                                                                        .sinisterPec
                                                                                        .stepId,
                                                                                }
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            });
                    }
                });
        });
    }

    confirmer() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir soumettre le devis ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    if (this.oneClickForButton19) {
                        this.oneClickForButton19 = false;
                        this.sendDateReceptionVehicule();
                        this.isSaving = true;
                        if (
                            this.sinisterPec.primaryQuotation != null &&
                            this.sinisterPec.primaryQuotation != undefined
                        ) {
                            this.detailsPiecesService
                                .deleteByQuery(
                                    this.sinisterPec.primaryQuotationId
                                )
                                .subscribe((response) => {
                                    // Find devid By ID

                                    this.quotation.confirmationDecisionQuote = false;
                                    this.sinisterPec.changeModificationPrix = null;
                                    this.sinisterPec.modificationPrix = null;
                                    this.sinisterPec.activeModificationPrix = null;
                                    this.status = this.quotation.statusId; // Get etat de devis selectionné

                                    for (
                                        let i = 0;
                                        i < this.detailsPiecesMO.length;
                                        i++
                                    ) {
                                        this.detailsPiecesMO[i].isMo = true;
                                    }
                                    let listPieces = this.detailsPiecesMO.concat(
                                        this.detailsPieces,
                                        this.detailsPiecesFourniture,
                                        this.detailsPiecesIngredient
                                    );
                                    for (
                                        let i = 0;
                                        i < listPieces.length;
                                        i++
                                    ) {
                                        listPieces[
                                            i
                                        ].quotationId = this.sinisterPec.primaryQuotationId;
                                        listPieces[i].quotationMPId = null;
                                    }

                                    this.quotation.listPieces = listPieces;
                                    this.quotation.statusId = 4;
                                    this.quotation.id = this.sinisterPec.primaryQuotationId;
                                    this.sinisterPec.quotation = this.quotation;
                                    if (
                                        this.responsableControleTechnique
                                            .profileId == 21
                                    ) {
                                        this.sinisterPec.respControleTechnique = this.responsableControleTechnique.id;
                                    }

                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId = 106;
                                    //this.sinisterPec.observations = this.observations;
                                    this.sinisterPec.historyAvisExpert =
                                        "ModificationPrixQuotation";
                                    this.sinisterPecService
                                        .updatePecForQuotation(
                                            this.sinisterPec,
                                            true
                                        )
                                        .subscribe((res) => {
                                            this.sinisterPec = res;
                                            this.apecService
                                                .deleteAPecDevisCompl(
                                                    this.sinisterPec.id
                                                )
                                                .subscribe((resDel) => {
                                                    this.apecService
                                                        .generateAccordPrisCharge(
                                                            this.sinisterPec,
                                                            this.sinisterPec
                                                                .primaryQuotationId,
                                                            true,
                                                            3,
                                                            this.labelAccordMP
                                                        )
                                                        .subscribe((resPdf) => {
                                                            this.apecService
                                                                .findByQuotation(
                                                                    this
                                                                        .sinisterPec
                                                                        .primaryQuotationId
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        res: Apec
                                                                    ) => {
                                                                        this.apecMprix = res;
                                                                        this.apecMprix.etat = 6;
                                                                        this.apecMprix.testDevis = false;
                                                                        this.apecService
                                                                            .update(
                                                                                this
                                                                                    .apecMprix
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    res
                                                                                ) => {
                                                                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                                    this.sinisterPec.stepId = 106;
                                                                                    this.detailsPiecesService
                                                                                        .deleteByQueryMP(
                                                                                            this
                                                                                                .sinisterPec
                                                                                                .id
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                response
                                                                                            ) => {
                                                                                                this.quotationService
                                                                                                    .deleteAdditionnelQuote(
                                                                                                        this
                                                                                                            .sinisterPec
                                                                                                            .id
                                                                                                    )
                                                                                                    .subscribe(
                                                                                                        (
                                                                                                            resMP2
                                                                                                        ) => {
                                                                                                            this.sendNotifConfirmeModifPrix(
                                                                                                                "Modification des prix confirmée"
                                                                                                            );
                                                                                                            this.router.navigate(
                                                                                                                [
                                                                                                                    "../confirmation-modification-prix",
                                                                                                                ]
                                                                                                            );
                                                                                                        }
                                                                                                    );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        });
                                                });
                                            /*this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                        this.apecMprix = res;
                                        this.apecMprix.etat = 3;
                                        this.apecService.update(this.apecMprix).subscribe((res) => {
                                            this.sendNotifConfirmeModifPrix('Modification des prix confirmée');
                                            this.router.navigate(['../confirmation-modification-prix']);
                                        });
                                    });*/
                                        });
                                });
                        }
                    }
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    sendNotifGlobal(notificationId, destination, sourse, settings) {
        this.notifUser = new NotifAlertUser();
        this.notification.id = notificationId;
        this.notifUser.destination = destination;
        console.log("accord favorable-*-*-*-" + destination);
        this.notifUser.source = sourse;
        this.notifUser.notification = this.notification;
        this.notifUser.settings = JSON.stringify(settings);
        this.notifUser.entityId = this.sinisterPec.id;
        this.notifUser.entityName = "SinisterPec";
        this.listNotifUser.push(this.notifUser);
    }

    sendNotifConfirmeModifPrix(typeNotif) {
        if (this.oneClickForButton20) {
            this.oneClickForButton20 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        let settingJson = {
                            typenotification: typeNotif,
                            idReparateur: this.sinisterPec.reparateurId,
                            refSinister: this.sinisterPec.reference,
                            sinisterId: this.sinisterPec.sinisterId,
                            sinisterPecId: this.sinisterPec.id,
                            expertId: this.sinisterPec.expertId,
                            stepPecId: this.sinisterPec.stepId,
                        };
                        this.sendNotifGlobal(
                            43,
                            this.sinisterPec.assignedToId,
                            usr.id,
                            settingJson
                        );
                        this.userExtraService
                            .finUsersByPersonProfil(
                                this.sinisterPec.agencyId,
                                24
                            )
                            .subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                console.log(
                                    "4-------------------------------------------------------------------"
                                );
                                this.userExNotifAgency.forEach((element) => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 43;
                                    this.notifUser.source = usr.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(
                                        settingJson
                                    );
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.partnerId,
                                        25
                                    )
                                    .subscribe((userExNotifPartner) => {
                                        this.userExNotifPartner =
                                            userExNotifPartner.json;
                                        this.userExNotifPartner.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 43;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.notificationAlerteService
                                            .create(this.listNotifUser)
                                            .subscribe((res) => {
                                                this.ws.send(
                                                    "/app/hello",
                                                    {},
                                                    JSON.stringify(settingJson)
                                                );
                                            });
                                    });
                            });
                    });
            });
        }
    }

    sendNotifSoumettreDevis(typeNotif) {
        if (this.oneClickForButton21) {
            this.oneClickForButton21 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        let settingJson = {
                            typenotification: typeNotif,
                            idReparateur: this.sinisterPec.reparateurId,
                            refSinister: this.sinisterPec.reference,
                            sinisterId: this.sinisterPec.sinisterId,
                            sinisterPecId: this.sinisterPec.id,
                            expertId: this.sinisterPec.expertId,
                            stepPecId: this.sinisterPec.stepId,
                        };
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 52;
                        this.notifUser.source = usr.id;
                        this.notifUser.destination = this.sinisterPec.assignedToId;
                        this.notifUser.notification = this.notification;
                        this.notifUser.settings = JSON.stringify(settingJson);
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.listNotifUser.push(this.notifUser);
                        this.userExtraService
                            .finUsersByPersonProfil(
                                this.sinisterPec.agencyId,
                                24
                            )
                            .subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                console.log(
                                    "4-------------------------------------------------------------------"
                                );
                                this.userExNotifAgency.forEach((element) => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 52;
                                    this.notifUser.source = usr.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify(
                                        settingJson
                                    );
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.partnerId,
                                        25
                                    )
                                    .subscribe((userExNotifPartner) => {
                                        this.userExNotifPartner =
                                            userExNotifPartner.json;
                                        this.userExNotifPartner.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 52;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.notificationAlerteService
                                            .create(this.listNotifUser)
                                            .subscribe((res) => {
                                                this.ws.send(
                                                    "/app/hello",
                                                    {},
                                                    JSON.stringify(settingJson)
                                                );
                                            });
                                    });
                            });
                    });
            });
        }
    }

    nonConfirmer() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir enregistrer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    if (this.oneClickForButton22) {
                        this.oneClickForButton22 = false;
                        this.sendDateReceptionVehicule();
                        this.sinisterPec.stepId = this.sinisterPec.oldStep;
                        // this.sinisterPec.observations = this.observations;
                        this.sinisterPecService
                            .updateIt(this.sinisterPec)
                            .subscribe((res) => {
                                this.sendNotifNonConfirmeModifPrix(
                                    "Modification des prix non confirmée"
                                );
                                this.router.navigate([
                                    "../confirmation-modification-prix",
                                ]);
                                /*this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                            this.apecMprix = res;
                            this.apecMprix.etat = 10;
                            console.log("testUpdate " + this.apec.id);
                            this.apecService.update(this.apecMprix).subscribe((res) => {
                                
                            });
                        });*/
                            });
                    }
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
        console.log("Nonconfirmer");
    }

    getPhotoReparation() {
        this.piecesAttachments = [];
        this.sinisterPecService
            .getPhotoReparationAttachments(this.sinisterPec.id)
            .subscribe((resImprime) => {
                this.piecesAttachments = resImprime.json;
                if (this.piecesAttachments.length !== 0) {
                    this.showPieceAttachment = true;
                }
            });
    }

    getPhotoExpertise() {
        this.piecesAttachmentsExpertise = [];
        this.sinisterPecService
            .getExpertiseAttachments(this.sinisterPec.id)
            .subscribe((resImprime) => {
                this.piecesAttachmentsExpertise = resImprime.json;
                if (this.piecesAttachmentsExpertise.length !== 0) {
                    this.showPieceAttachmentExpertise = true;
                }
            });
    }

    getAttachmentAvantReparation() {
        this.sinisterPecService
            .getPhotoAvantReparationAttachments(
                "quotation",
                this.sinisterPec.id
            )
            .subscribe((resAttt) => {
                this.piecesAvantReparationAttachments = resAttt.json;
                if (this.piecesAvantReparationAttachments.length !== 0) {
                    this.showAttachments();
                    this.enableSaveSoumettre = true;
                }
            });
    }

    getAttachmentPointChocExpertise() {
        this.sinisterPecService
            .getPhotoAvantReparationAttachments(
                "pointChocExpertise",
                this.sinisterPec.id
            )
            .subscribe((resAttt) => {
                this.pointChocExpertiseAttachments = resAttt.json;
                if (this.pointChocExpertiseAttachments.length !== 0) {
                    this.showAttachmentsPointChocExpertise();
                    this.enableSaveExpertise = true;
                }
            });
    }

    sendNotifNonConfirmeModifPrix(typeNotif) {
        if (this.oneClickForButton23) {
            this.oneClickForButton23 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        this.userExtraService
                            .finUsersByPersonProfil(
                                this.sinisterPec.reparateurId,
                                28
                            )
                            .subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: this.sinisterPec.reparateurId,
                                    refSinister: this.sinisterPec.reference,
                                    sinisterId: this.sinisterPec.sinisterId,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                    stepPecId: this.sinisterPec.stepId,
                                };
                                this.userExNotifAgency.forEach((element) => {
                                    this.sendNotifGlobal(
                                        44,
                                        element.userId,
                                        usr.id,
                                        settingJson
                                    );
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 44;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 44;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.notificationAlerteService
                                                    .create(this.listNotifUser)
                                                    .subscribe((res) => {
                                                        this.ws.send(
                                                            "/app/hello",
                                                            {},
                                                            JSON.stringify({
                                                                typenotification: typeNotif,
                                                                idReparateur: this
                                                                    .sinisterPec
                                                                    .reparateurId,
                                                                refSinister: this
                                                                    .sinisterPec
                                                                    .reference,
                                                                sinisterId: this
                                                                    .sinisterPec
                                                                    .sinisterId,
                                                                sinisterPecId: this
                                                                    .sinisterPec
                                                                    .id,
                                                                expertId: this
                                                                    .sinisterPec
                                                                    .expertId,
                                                                stepPecId: this
                                                                    .sinisterPec
                                                                    .stepId,
                                                            })
                                                        );
                                                    });
                                            });
                                    });
                            });
                    });
            });
        }
    }

    /*** save Avis Expert ***/

    saveAvisExpert() {
        this.adaptateurQuotationDetailsQuotation();
        if (
            this.quotation.preliminaryReport &&
            (this.sinisterPec.modeId == 1 || this.sinisterPec.modeId == 3)
        ) {
            console.log("iciiiii   too get ttc amout enter ");
            if (this.quotation.ttcAmount > 5000)
                this.sinisterPec.modeModifId = 4;
            else this.sinisterPec.modeModifId = 4;
            this.sinisterPec.motifsDecisionId = 131;
            this.statusChange = true;

            if (this.listModeGestionAvenant.length !== 0) {
                for (
                    let index = 0;
                    index < this.listModeGestionAvenant.length;
                    index++
                ) {
                    const element = this.listModeGestionAvenant[index];
                    if (this.sinisterPec.modeModifId == element.id) {
                        this.testModeID = true;
                        break;
                    }
                }
            } else {
                for (
                    let index = 0;
                    index < this.listModeGestion.length;
                    index++
                ) {
                    const element = this.listModeGestion[index];
                    if (this.sinisterPec.modeModifId == element.id) {
                        this.testModeID = true;
                        break;
                    }
                }
            }

            //this.testModeID = false;
            if (this.testModeID) {
                this.sinisterPec.decision = "ACC_WITH_CHANGE_STATUS";
                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                this.sinisterPec.stepId = PrestationPecStep.APPROVE;
                this.sinisterPec.oldStep = PrestationPecStep.EXPERT_ADVICE;
                this.sendNotifPrestationApprouvAvecModif(
                    "PrestationApprouvAvecModif"
                );
            } else {
                this.sinisterPec.decision = "CANCELED";
                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                this.sinisterPec.stepId = PrestationPecStep.APPROVE;
                this.sinisterPec.oldStep = PrestationPecStep.EXPERT_ADVICE;
                this.sinisterPec.motifsDecisionId = 95;
            }
            if (
                this.expertDecision !==
                "Circonstance de l’accident non conforme"
            ) {
                this.sinisterPec.expertDecision = this.expertDecision;
            }
            switch (this.expertDecision) {
                case "Circonstance de l’accident non conforme":
                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.CONFIRMATION_REFUS;
                    break;
                case "Reconstitution":
                    this.sinisterPec.oldStepModifSinPec =
                        PrestationPecStep.NOTICE_EXPERT_RECONSTITUTION;
                    break;
                case "Épave technique":
                    this.sinisterPec.oldStepModifSinPec =
                        PrestationPecStep.CONFIRMATION_CANCELLATION;
                    break;
                case "Épave économique":
                    this.sinisterPec.oldStepModifSinPec =
                        PrestationPecStep.CONFIRMATION_CANCELLATION;
                    break;
                case "Expertise terrain":
                    this.sinisterPec.oldStepModifSinPec =
                        PrestationPecStep.ADVICE_EXPERT_EXPERTISE_TERRAIN;
                    break;
                case "Devis Complementaire Annulé":
                    this.sinisterPec.stepId = 110; //Instance Reparation
                    break;
                case "Circonstance à vérifier, Expertise terrain":
                    this.sinisterPec.oldStepModifSinPec =
                        PrestationPecStep.ADVICE_EXPERT_EXPERTISE_TERRAIN;
                    break;
                case "Circonstance conforme OK pour démontage":
                    this.sinisterPec.oldStepModifSinPec =
                        PrestationPecStep.CIRCUMSTANCE_CONFORMS_OK_FOR_DISASSEMBLY;
                    break;
                default:
                    console.log("hello word");
            }
        } else {
            if (
                this.expertDecision !==
                "Circonstance de l’accident non conforme"
            ) {
                this.sinisterPec.expertDecision = this.expertDecision;
            }
            switch (this.expertDecision) {
                case "Circonstance de l’accident non conforme":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.CONFIRMATION_REFUS;
                    this.notif = "Annulée suite à la décision expert";
                    break;
                case "Reconstitution":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.NOTICE_EXPERT_RECONSTITUTION;
                    break;
                case "Épave technique":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.CONFIRMATION_CANCELLATION;
                    break;
                case "Épave économique":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.CONFIRMATION_CANCELLATION;
                    break;
                case "Expertise terrain":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.ADVICE_EXPERT_EXPERTISE_TERRAIN;
                    break;
                case "Accord pour réparation avec modification":
                    /*if (this.additionalQuote) {
                        this.sinisterPec.stepId = PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                    }
                    else*/
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.CONFIRMATION_QUOTE;
                    this.notif = "Accord pour réparation avec modification";
                    break;
                case "Devis Complementaire Annulé":
                    /*if (this.additionalQuote) {
                        this.sinisterPec.stepId = PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                    }
                    else*/
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = 110; //Instance Reparation
                    this.notif = "Devis Complementaire Annulé";
                    break;
                case "Accord pour réparation":
                    console.log("decisionExpert = Accord pour réparation");
                    break;
                case "Circonstance à vérifier, Expertise terrain":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.ADVICE_EXPERT_EXPERTISE_TERRAIN;
                    break;
                case "Circonstance conforme OK pour démontage":
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId =
                        PrestationPecStep.CIRCUMSTANCE_CONFORMS_OK_FOR_DISASSEMBLY;
                    this.notif = "OK pour démontage";
                    break;
                default:
                    console.log("hello word");
            }
        }
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir enregister?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    if (this.oneClickForButton24) {
                        this.oneClickForButton24 = false;
                        this.isSaving = true;
                        this.sendDateReceptionVehicule();
                        if (this.apecSettingsAvenant.length !== 0) {
                            for (
                                let index = 0;
                                index < this.apecSettingsAvenant.length;
                                index++
                            ) {
                                const element = this.apecSettingsAvenant[index];
                                if (
                                    element.mgntModeId ==
                                    this.sinisterPec.modeId
                                ) {
                                    this.existMode = true;
                                    if (element.operator == Operator.GREATER) {
                                        if (
                                            this.quotation.ttcAmount >
                                            element.ceiling
                                        ) {
                                            this.testAvExp = true;
                                            break;
                                        } else {
                                            this.testAvExp = false;
                                            break;
                                        }
                                    } else {
                                        if (
                                            this.quotation.ttcAmount <=
                                            element.ceiling
                                        ) {
                                            this.testAvExp = true;
                                            break;
                                        } else {
                                            this.testAvExp = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (
                            this.apecSettingsAvenant.length == 0 ||
                            this.existMode == false
                        ) {
                            for (
                                let index = 0;
                                index < this.apecSettings.length;
                                index++
                            ) {
                                const element = this.apecSettings[index];
                                if (
                                    element.mgntModeId ==
                                    this.sinisterPec.modeId
                                ) {
                                    if (element.operator == Operator.GREATER) {
                                        if (
                                            this.quotation.ttcAmount >
                                            element.ceiling
                                        ) {
                                            this.testAvExp = true;
                                            break;
                                        } else {
                                            this.testAvExp = false;
                                            break;
                                        }
                                    } else {
                                        if (
                                            this.quotation.ttcAmount <=
                                            element.ceiling
                                        ) {
                                            this.testAvExp = true;
                                            break;
                                        } else {
                                            this.testAvExp = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (
                            this.expertDecision == "Accord pour réparation" &&
                            this.sinisterPec.stepId !==
                                PrestationPecStep.APPROVE
                        ) {
                            if (this.testAvExp == true) {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId =
                                    PrestationPecStep.APPROUVER_APEC;
                            } else {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId =
                                    PrestationPecStep.VALIDATION_APEC;
                            }
                        }

                        if (
                            (this.expertDecision == "Épave technique" ||
                                this.expertDecision == "Épave économique") &&
                            this.additionalQuote == false
                        ) {
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            if (
                                this.sinisterPec.stepId !==
                                PrestationPecStep.CONFIRMATION_CANCELLATION
                            ) {
                                this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                            }
                            this.sinisterPec.stepId =
                                PrestationPecStep.CONFIRMATION_CANCELLATION;
                            if (this.expertDecision == "Épave technique") {
                                this.sinisterPec.reasonCanceledId = 155;
                            }
                            if (this.expertDecision == "Épave économique") {
                                this.sinisterPec.reasonCanceledId = 154;
                            }
                            if (
                                this.sinisterPec.decision !==
                                    "ACC_WITH_CHANGE_STATUS" &&
                                this.sinisterPec.decision !== "CANCELED"
                            ) {
                                this.sinisterPec.oldStep =
                                    PrestationPecStep.ADVICE_EXPERT_EXPERTISE_TERRAIN;
                            }
                        }

                        for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                            this.detailsPiecesMO[i].isMo = true;
                        }
                        let listPieces = this.detailsPiecesMO.concat(
                            this.detailsPieces,
                            this.detailsPiecesFourniture,
                            this.detailsPiecesIngredient
                        );
                        this.compareDetailsPiece(
                            this.lastDetailsPieces,
                            listPieces
                        );
                        this.quotation.listPieces = listPieces;
                        const expertiseDate = new Date(
                            this.quotation.expertiseDate
                        );
                        this.quotation.expertiseDate = this.dateAsYYYYMMDDHHNNSSLDTRV(
                            expertiseDate
                        );
                        if (
                            this.quotation.avisExpertDate == null ||
                            this.quotation.avisExpertDate == undefined
                        ) {
                            this.quotation.avisExpertDate = this.dateAsYYYYMMDDHHNNSSLDT(
                                new Date()
                            );
                        }
                        this.quotation.statusId =
                            QuoteStatus.DEVIS_CONFERME_PAR_LE_REPARATEUR;
                        if (this.sinisterPec.stepId == 110) {
                            this.quotation.isConfirme = false;
                            this.quotation.fromSignature = true;
                        }
                        this.sinisterPec.quotation = this.quotation;
                        const dateEnvoyerPec = new Date(
                            this.sinisterPec.dateUpdateExpert
                        );
                        this.sinisterPec.dateUpdateExpert = this.dateAsYYYYMMDDHHNNSSLDT(
                            dateEnvoyerPec
                        );

                        if (
                            this.sinisterPec.quotation.ttcAmount != null &&
                            this.quotation.ttcAmount != undefined
                        ) {
                            this.sinisterPec.quotation.expertDecision = this.expertDecision;
                            if (this.additionalQuote) {
                                this.sinisterPec.expertDecision = null;
                                this.sinisterPec.quotation.statusId = 4;
                            }
                            if (
                                this.expertDecision ==
                                    "Accord pour réparation" ||
                                this.expertDecision ==
                                    "Accord pour réparation avec modification"
                            ) {
                                this.sinisterPec.historyAvisExpert =
                                    "AvisExpertQuotation";
                            }
                            if (
                                this.sinisterPec.stepId ==
                                    PrestationPecStep.APPROUVER_APEC ||
                                this.sinisterPec.stepId ==
                                    PrestationPecStep.VALIDATION_APEC
                            ) {
                                if (this.additionalQuote) {
                                    this.sinisterPec.testModifPrix = false;
                                }
                            }
                            this.sinisterPecService
                                .updatePecForQuotation(this.sinisterPec, true)
                                .subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.detailsQuote.expertDecision = this.sinisterPec.expertDecision;
                                    this.saveAvisTechnique(this.detailsQuote);
                                    if (this.sinisterPec.stepId == 110) {
                                        if (this.additionalQuote) {
                                            this.apecService
                                                .generateAccordPrisCharge(
                                                    this.sinisterPec,
                                                    this.quotation.id,
                                                    true,
                                                    6,
                                                    this.labelAccordAnnule
                                                )
                                                .subscribe((resPdf) => {
                                                    this.apecService
                                                        .findByQuotation(
                                                            this.quotation.id
                                                        )
                                                        .subscribe(
                                                            (apec: Apec) => {
                                                                apec.etat = 10;
                                                                apec.isConfirme = false;
                                                                apec.testDevis = false;
                                                                this.apecService
                                                                    .update(
                                                                        apec
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            res: Apec
                                                                        ) => {
                                                                            this.apec = res;
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.sendNotifNonConfirmeDevisComplementaire(
                                                                                    "DevisComplementaireAnnule"
                                                                                );
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        }
                                    }
                                    if (
                                        this.sinisterPec.stepId ==
                                        PrestationPecStep.APPROUVER_APEC
                                    ) {
                                        if (this.additionalQuote) {
                                            this.apecService
                                                .generateAccordPrisCharge(
                                                    this.sinisterPec,
                                                    this.quotation.id,
                                                    true,
                                                    6,
                                                    this.labelAccordNormal
                                                )
                                                .subscribe((resPdf) => {
                                                    this.apecService
                                                        .findByQuotation(
                                                            this.quotation.id
                                                        )
                                                        .subscribe(
                                                            (apec: Apec) => {
                                                                apec.etat = 17;
                                                                this.apecService
                                                                    .update(
                                                                        apec
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            res: Apec
                                                                        ) => {
                                                                            this.apec = res;
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.sendNotifAccordPourReparationDevComp(
                                                                                    "AccordPourReparationDevisCompl"
                                                                                );
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        } else {
                                            this.apecService
                                                .generateAccordPrisCharge(
                                                    this.sinisterPec,
                                                    this.quotation.id,
                                                    true,
                                                    0,
                                                    this.labelAccordNormal
                                                )
                                                .subscribe((resPdf) => {
                                                    this.apecService
                                                        .findByQuotation(
                                                            this.quotation.id
                                                        )
                                                        .subscribe(
                                                            (apec: Apec) => {
                                                                apec.etat = 0;
                                                                apec.testDevis = false;
                                                                this.apecService
                                                                    .update(
                                                                        apec
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            res: Apec
                                                                        ) => {
                                                                            this.apec = res;
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.listNotifUser = [];
                                                                                this.userExNotif = [];
                                                                                let settingJson = {
                                                                                    typenotification:
                                                                                        "ACCORD_POUR_REPARATION",
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    quotationId: this
                                                                                        .quotation
                                                                                        .id,
                                                                                    accord:
                                                                                        "approuveapec",
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                };
                                                                                this.userExtraService
                                                                                    .finUsersByPersonProfil(
                                                                                        this
                                                                                            .contratAssurance
                                                                                            .clientId,
                                                                                        25
                                                                                    )
                                                                                    .subscribe(
                                                                                        (
                                                                                            userExNotif
                                                                                        ) => {
                                                                                            this.userExNotif =
                                                                                                userExNotif.json;
                                                                                            this.userExNotif.forEach(
                                                                                                (
                                                                                                    element
                                                                                                ) => {
                                                                                                    this.sendNotifAccordPourReparation(
                                                                                                        32,
                                                                                                        element.id,
                                                                                                        element.id,
                                                                                                        settingJson
                                                                                                    );
                                                                                                }
                                                                                            );
                                                                                            this.notificationAlerteService
                                                                                                .create(
                                                                                                    this
                                                                                                        .listNotifUser
                                                                                                )
                                                                                                .subscribe(
                                                                                                    (
                                                                                                        res
                                                                                                    ) => {
                                                                                                        this.ws.send(
                                                                                                            "/app/hello",
                                                                                                            {},
                                                                                                            JSON.stringify(
                                                                                                                settingJson
                                                                                                            )
                                                                                                        );
                                                                                                    }
                                                                                                );
                                                                                        }
                                                                                    );
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        }
                                    }
                                    if (
                                        this.sinisterPec.stepId ==
                                        PrestationPecStep.VALIDATION_APEC
                                    ) {
                                        if (this.additionalQuote) {
                                            this.apecService
                                                .generateAccordPrisCharge(
                                                    this.sinisterPec,
                                                    this.quotation.id,
                                                    true,
                                                    6,
                                                    this.labelAccordNormal
                                                )
                                                .subscribe((resPdf) => {
                                                    this.apecService
                                                        .findByQuotation(
                                                            this.quotation.id
                                                        )
                                                        .subscribe(
                                                            (apec: Apec) => {
                                                                apec.etat = 17;
                                                                this.apecService
                                                                    .update(
                                                                        apec
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            res: Apec
                                                                        ) => {
                                                                            this.apec = res;
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.sendNotifAccordPourReparationDevComp(
                                                                                    "AccordPourReparationDevisCompl"
                                                                                );
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        } else {
                                            this.apecService
                                                .generateAccordPrisCharge(
                                                    this.sinisterPec,
                                                    this.quotation.id,
                                                    true,
                                                    6,
                                                    this.labelAccordNormal
                                                )
                                                .subscribe((resPdf) => {
                                                    this.apecService
                                                        .findByQuotation(
                                                            this.quotation.id
                                                        )
                                                        .subscribe(
                                                            (apec: Apec) => {
                                                                apec.etat = 6;
                                                                apec.testDevis = false;
                                                                this.apecService
                                                                    .update(
                                                                        apec
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            res: Apec
                                                                        ) => {
                                                                            this.apec = res;
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.listNotifUser = [];
                                                                                this.userExNotif = [];
                                                                                if (
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .listComplementaryQuotation
                                                                                        .length ==
                                                                                    0
                                                                                ) {
                                                                                    let settingJson = {
                                                                                        typenotification:
                                                                                            "ACCORD_POUR_REPARATION",
                                                                                        sinisterPecId: this
                                                                                            .sinisterPec
                                                                                            .id,
                                                                                        quotationId: this
                                                                                            .quotation
                                                                                            .id,
                                                                                        accord:
                                                                                            "validationapec",
                                                                                        refSinister: this
                                                                                            .sinisterPec
                                                                                            .reference,
                                                                                    };
                                                                                    this.sendNotifAccordPourReparation(
                                                                                        32,
                                                                                        this
                                                                                            .sinisterPec
                                                                                            .assignedToId,
                                                                                        this
                                                                                            .userExtra
                                                                                            .id,
                                                                                        settingJson
                                                                                    );
                                                                                    this.notificationAlerteService
                                                                                        .create(
                                                                                            this
                                                                                                .listNotifUser
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res
                                                                                            ) => {
                                                                                                this.ws.send(
                                                                                                    "/app/hello",
                                                                                                    {},
                                                                                                    JSON.stringify(
                                                                                                        settingJson
                                                                                                    )
                                                                                                );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        }
                                    }
                                    this.router.navigate(["../expert-opinion"]);
                                });
                        } else {
                            this.sinisterPecService
                                .update(this.sinisterPec)
                                .subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.detailsQuote.expertDecision = this.sinisterPec.expertDecision;
                                    this.saveAvisTechnique(this.detailsQuote);
                                    this.router.navigate(["../expert-opinion"]);
                                });
                        }

                        this.sendNotif(
                            this.sinisterPec.reparateurId,
                            this.notif
                        );
                    }
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    adaptateurQuotationDetailsQuotation() {
        if (this.quotation.id !== null && this.quotation.id !== undefined) {
            this.quotation.priceNewVehicle = this.detailsQuote.priceNewVehicle;
            this.quotation.marketValue = this.detailsQuote.marketValue;
            this.quotation.kilometer = this.detailsQuote.kilometer;
            this.quotation.heureMO = this.detailsQuote.heureMO;
            this.quotation.totalMo = this.detailsQuote.totalMo;
            this.quotation.conditionVehicle = this.detailsQuote.conditionVehicle;
            this.quotation.repairableVehicle = this.detailsQuote.repairableVehicle;
            this.quotation.preliminaryReport = this.detailsQuote.preliminaryReport;
            this.quotation.immobilizedVehicle = this.detailsQuote.immobilizedVehicle;
            this.quotation.concordanceReport = this.detailsQuote.concordanceReport;
            this.quotation.expertiseDate = this.detailsQuote.expertiseDate;
        }
    }

    sendNotifAccordPourReparationDevComp(typeNotif) {
        if (this.oneClickForButton25) {
            this.oneClickForButton25 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        this.userExtraService
                            .findByProfil(21)
                            .subscribe((userExNotif: UserExtra[]) => {
                                this.userExNotif = userExNotif;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: this.sinisterPec.reparateurId,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                    stepPecId: this.sinisterPec.stepId,
                                };
                                this.userExNotif.forEach((element) => {
                                    this.sendNotifGlobal(
                                        56,
                                        element.userId,
                                        usr.id,
                                        settingJson
                                    );
                                });

                                this.notificationAlerteService
                                    .create(this.listNotifUser)
                                    .subscribe((res) => {
                                        this.ws.send(
                                            "/app/hello",
                                            {},
                                            JSON.stringify(settingJson)
                                        );
                                    });
                            });
                    });
            });
        }
    }

    sendNotifNonConfirmeDevisComplementaire(typeNotif) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService
                .finPersonneByUser(this.currentAccount.id)
                .subscribe((usr: UserExtra) => {
                    this.userExtraService
                        .finUsersByPersonProfil(
                            this.sinisterPec.reparateurId,
                            28
                        )
                        .subscribe((userExNotifExpert) => {
                            this.userExNotifExpert = userExNotifExpert.json;
                            let settingJson = {
                                typenotification: typeNotif,
                                idReparateur: this.sinisterPec.reparateurId,
                                refSinister: this.sinisterPec.reference,
                                assure: this.assure.nom,
                                sinisterId: this.sinister.id,
                                sinisterPecId: this.sinisterPec.id,
                                expertId: this.sinisterPec.expertId,
                                stepPecId: this.sinisterPec.stepId,
                            };
                            this.userExNotifExpert.forEach((element) => {
                                this.sendNotifGlobal(
                                    58,
                                    element.userId,
                                    usr.id,
                                    settingJson
                                );
                            });

                            this.notificationAlerteService
                                .create(this.listNotifUser)
                                .subscribe((res) => {
                                    this.ws.send(
                                        "/app/hello",
                                        {},
                                        JSON.stringify(settingJson)
                                    );
                                });
                        });
                });
        });
    }

    sendFirstConnectionEventForGtEstimate() {
        this.quotationService
            .sendFirstConnectionEventForGtEstimate(this.sinisterPec.id, 1)
            .subscribe((resSend) => {});
    }

    sendNotifPrestationApprouvAvecModif(typeNotif) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService
                .finPersonneByUser(this.currentAccount.id)
                .subscribe((usr: UserExtra) => {
                    this.userExtraService
                        .finPersonneByUser(this.sinisterPec.assignedToId)
                        .subscribe((usrAssigned: UserExtra) => {
                            console.log(
                                "userId" + this.sinisterPec.assignedToId
                            );

                            console.log("yes notif" + usrAssigned.userBossId);
                            let settingJson = {
                                typenotification: typeNotif,
                                idReparateur: this.sinisterPec.reparateurId,
                                refSinister: this.sinisterPec.reference,
                                assure: this.assure.nom,
                                sinisterId: this.sinister.id,
                                sinisterPecId: this.sinisterPec.id,
                                expertId: this.sinisterPec.expertId,
                                stepPecId: this.sinisterPec.stepId,
                            };
                            console.log("yes notif1" + usrAssigned.userBossId);
                            this.sendNotifGlobal(
                                57,
                                usrAssigned.userBossId,
                                usr.id,
                                settingJson
                            );
                            console.log("here");
                            this.notificationAlerteService
                                .create(this.listNotifUser)
                                .subscribe((res) => {
                                    this.ws.send(
                                        "/app/hello",
                                        {},
                                        JSON.stringify(settingJson)
                                    );
                                });
                        });
                });
        });

        /*     this.principal.identity().then((account) => {
                 this.currentAccount = account;
                 this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                     console.log('userId'+this.currentAccount.id);
 
                         console.log('yes notif'+usr.userBossId );
                         this.notifUser = new NotifAlertUser();
                         this.notification.id = 57;
                         this.notifUser.destination = usr.userBossId;
                         this.notifUser.source = usr.id;
                         this.notifUser.entityId = this.sinisterPec.id;
                         this.notifUser.entityName = "SinisterPec";
                         this.notifUser.notification = this.notification;
                         this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                         this.listNotifUser.push(this.notifUser);
                         
                     
                     this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                         this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));
                         
                         });
                 });
             });
             */
    }

    downloadConstatFile() {
        if (this.constatFiles) {
            saveAs(this.constatFiles);
        }
    }

    getOrdreMissionExpertPdf(sinisterPec: SinisterPec) {
        if (sinisterPec.id == null || sinisterPec.id === undefined) {
            console.log("Erreur lors de la génération");
        } else {
            // OK
            this.sinisterPecService
                .getOrdreMissionExpertPdf(sinisterPec.id)
                .subscribe(
                    (blob: Blob) => {
                        let fileName = "OrdreDeMission" + ".pdf";
                        console.log(fileName);

                        if (
                            window.navigator &&
                            window.navigator.msSaveOrOpenBlob
                        ) {
                            window.navigator.msSaveOrOpenBlob(blob, fileName);
                        } else {
                            var a = document.createElement("a");
                            a.href = URL.createObjectURL(blob);
                            a.download = fileName;
                            a.target = "_blank";
                            document.body.appendChild(a);
                            a.click();
                            document.body.removeChild(a);
                        }
                    },
                    (err) => {
                        alert(
                            "Error while downloading. File Not Found on the Server"
                        );
                    }
                );
        }
    }

    getPieceNew(entityName: string, sinisterPecId: number, label: string) {
        this.sinisterPecService
            .getPieceBySinPecAndLabel(entityName, sinisterPecId, label)
            .subscribe(
                (blob: Blob) => {
                    const indexOfFirst = blob.type.indexOf("/");
                    this.extensionImage = blob.type.substring(
                        indexOfFirst + 1,
                        blob.type.length
                    );
                    this.extensionImage = this.extensionImage.toLowerCase();
                    let fileName = label + "." + this.extensionImage;
                    console.log(fileName);

                    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                        window.navigator.msSaveOrOpenBlob(blob, fileName);
                    } else {
                        var a = document.createElement("a");
                        a.href = URL.createObjectURL(blob);
                        a.download = fileName;
                        a.target = "_blank";
                        document.body.appendChild(a);
                        a.click();
                        document.body.removeChild(a);
                    }
                },
                (err) => {
                    alert(
                        "Error while downloading. File Not Found on the Server"
                    );
                }
            );
    }

    sendNotifAccordPourReparation(
        notificationId,
        destination,
        sourse,
        settings
    ) {
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

    sendNotifDevComp(typeNotif) {
        if (this.oneClickForButton25) {
            this.oneClickForButton25 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        let settingJson = {
                            typenotification: typeNotif,
                            idReparateur: this.sinisterPec.reparateurId,
                            refSinister: this.sinisterPec.reference,
                            assure: this.assure.nom,
                            sinisterId: this.sinister.id,
                            sinisterPecId: this.sinisterPec.id,
                            expertId: this.sinisterPec.expertId,
                            stepPecId: this.sinisterPec.stepId,
                        };
                        this.sendNotifGlobal(
                            48,
                            this.sinisterPec.assignedToId,
                            usr.id,
                            settingJson
                        );
                        this.userExtraService
                            .findByProfil(21)
                            .subscribe((userExNotif: UserExtra[]) => {
                                this.userExNotif = userExNotif;
                                this.userExNotif.forEach((element) => {
                                    this.sendNotifGlobal(
                                        48,
                                        element.userId,
                                        usr.id,
                                        settingJson
                                    );
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.sendNotifGlobal(
                                                    48,
                                                    element.userId,
                                                    usr.id,
                                                    settingJson
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.expertId,
                                                27
                                            )
                                            .subscribe((userExNotifExpert) => {
                                                this.userExNotifExpert =
                                                    userExNotifExpert.json;
                                                this.userExNotifExpert.forEach(
                                                    (element) => {
                                                        this.sendNotifGlobal(
                                                            48,
                                                            element.userId,
                                                            usr.id,
                                                            settingJson
                                                        );
                                                    }
                                                );
                                                this.userExtraService
                                                    .finUsersByPersonProfil(
                                                        this.sinisterPec
                                                            .partnerId,
                                                        25
                                                    )
                                                    .subscribe(
                                                        (
                                                            userExNotifPartner
                                                        ) => {
                                                            this.userExNotifPartner =
                                                                userExNotifPartner.json;
                                                            this.userExNotifPartner.forEach(
                                                                (element) => {
                                                                    this.sendNotifGlobal(
                                                                        48,
                                                                        element.userId,
                                                                        usr.id,
                                                                        settingJson
                                                                    );
                                                                }
                                                            );

                                                            this.notificationAlerteService
                                                                .create(
                                                                    this
                                                                        .listNotifUser
                                                                )
                                                                .subscribe(
                                                                    (res) => {
                                                                        this.ws.send(
                                                                            "/app/hello",
                                                                            {},
                                                                            JSON.stringify(
                                                                                settingJson
                                                                            )
                                                                        );
                                                                    }
                                                                );
                                                        }
                                                    );
                                            });
                                    });
                            });
                    });
            });
        }
    }

    /** rectification de devis */
    rectifyquote() {
        this.rectifyOK = false;
        let listPiecesRectify = this.detailsPiecesMO.concat(
            this.detailsPieces,
            this.detailsPiecesFourniture,
            this.detailsPiecesIngredient
        );
        listPiecesRectify.forEach((listPiece) => {
            for (
                let index = 0;
                index < this.lastDetailsPieces.length;
                index++
            ) {
                const lastDetailsPiece = this.lastDetailsPieces[index];

                if (
                    lastDetailsPiece.id == listPiece.id &&
                    (lastDetailsPiece.totalTtc != listPiece.totalTtc ||
                        lastDetailsPiece.nombreMOEstime !=
                            listPiece.nombreMOEstime ||
                        lastDetailsPiece.designationId !=
                            listPiece.designationId ||
                        lastDetailsPiece.naturePiece != listPiece.naturePiece)
                ) {
                    this.rectifyOK = true;
                    break;
                }
            }
        });
        console.log(this.rectifyOK);
        if (
            this.quotation.ttcAmount != this.ttcAmountDevis ||
            this.rectifyOK ||
            this.verif
        ) {
            this.confirmationDialogService
                .confirm(
                    "Confirmation",
                    "Etes vous sûrs  de rectifier  devis  ?",
                    "Oui",
                    "Non",
                    "lg"
                )
                .then((confirmed) => {
                    console.log("User confirmed:", confirmed);
                    if (confirmed) {
                        if (this.oneClickForButton26) {
                            this.oneClickForButton26 = false;
                            for (
                                let i = 0;
                                i < this.detailsPiecesMO.length;
                                i++
                            ) {
                                console.log(
                                    "hello word3" + this.sinisterPec.stepId
                                );
                                this.detailsPiecesMO[i].isMo = true;
                            }
                            let listPieces = this.detailsPiecesMO.concat(
                                this.detailsPieces,
                                this.detailsPiecesFourniture,
                                this.detailsPiecesIngredient
                            );
                            this.compareDetailsPiece(
                                this.lastDetailsPieces,
                                listPieces
                            );
                            listPieces.forEach((element) => {
                                element.isNew = false;
                            });
                            /*if (this.sinisterPec.pieceGenerique == true) {
                                listPieces.forEach(element => {
                                    element.observationExpert = null;
                                })

                            }
                            }*/
                            //     this.sinisterPec.pieceGenerique == null ;
                            this.quotation.listPieces = listPieces;
                            this.quotation.verificationDevisRectificationDate = this.dateAsYYYYMMDDHHNNSSLDT(
                                new Date()
                            );
                            // this.sinisterPec.observations = this.observations;
                            if (this.additionalQuote) {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId =
                                    PrestationPecStep.CONFIRMATION_QUOTE;
                                this.quotation.statusId =
                                    QuoteStatus.QUOTATION_STATUS_VALID_GA;
                            } else {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId =
                                    PrestationPecStep.CONFIRMATION_QUOTE;
                            }

                            this.sendDateReceptionVehicule();
                            this.sinisterPec.quotation = this.quotation;
                            if (
                                this.responsableControleTechnique.profileId ==
                                21
                            ) {
                                this.sinisterPec.respControleTechnique = this.responsableControleTechnique.id;
                            }
                            this.sinisterPec.historyAvisExpert =
                                "RectifyQuotation";
                            this.sinisterPecService
                                .updatePecForQuotation(this.sinisterPec, true)
                                .subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.sendNotif(
                                        this.sinisterPec.reparateurId,
                                        "Rectification Devis"
                                    );
                                    this.router.navigate(["../verification"]);
                                });
                        }
                    }
                })
                .catch(() =>
                    console.log(
                        "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                    )
                );
        } else {
            this.rectfyQuotsWithssucced = true;
        }
    }
    /** validatioon Devis*/
    validationQuote() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir valider le devis  ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    if (this.oneClickForButton27) {
                        this.oneClickForButton27 = false;
                        if (this.responsableControleTechnique.profileId == 21) {
                            this.sinisterPec.respControleTechnique = this.responsableControleTechnique.id;
                        }
                        for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                            this.detailsPiecesMO[i].isMo = true;
                        }
                        let listPieces = this.detailsPiecesMO.concat(
                            this.detailsPieces,
                            this.detailsPiecesFourniture,
                            this.detailsPiecesIngredient
                        );
                        this.compareDetailsPiece(
                            this.lastDetailsPieces,
                            listPieces
                        );
                        listPieces.forEach((element) => {
                            element.isNew = false;
                        });
                        this.quotation.listPieces = listPieces;
                        this.quotation.verificationDevisValidationDate = this.dateAsYYYYMMDDHHNNSSLDT(
                            new Date()
                        );
                        // this.sinisterPec.observations = this.observations;
                        if (this.additionalQuote) {
                            if (
                                this.sinisterPec.expertId != null &&
                                this.sinisterPec.expertId != undefined
                            ) {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId =
                                    PrestationPecStep.EXPERT_ADVICE;
                                this.quotation.statusId =
                                    QuoteStatus.QUOTATION_STATUS_VALID_GA;
                            } else {
                                this.test = true;
                            }
                        } else {
                            if (!this.sinisterPec.lightShock) {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId =
                                    PrestationPecStep.EXPERT_ADVICE;
                                console.log("send notif avis expert");
                                this.sendNotif(
                                    this.sinisterPec.reparateurId,
                                    "Avis Expert"
                                );
                            } else {
                                if (
                                    this.sinisterPec.expertId != null &&
                                    this.sinisterPec.expertId != undefined
                                ) {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.EXPERT_ADVICE;
                                } else {
                                    if (
                                        this.sinisterTypeSettingsAvenant
                                            .length !== 0
                                    ) {
                                        for (
                                            let index = 0;
                                            index <
                                            this.sinisterTypeSettingsAvenant
                                                .length;
                                            index++
                                        ) {
                                            const element = this
                                                .sinisterTypeSettingsAvenant[
                                                index
                                            ];
                                            if (
                                                element.sinisterTypeId ==
                                                this.sinisterPec.modeId
                                            ) {
                                                this.existModeValidation = true;
                                                if (
                                                    this.quotation.ttcAmount <
                                                    element.ceiling
                                                ) {
                                                    this.test = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (
                                        this.sinisterTypeSettingsAvenant
                                            .length == 0 ||
                                        this.existModeValidation == false
                                    ) {
                                        for (
                                            let index = 0;
                                            index <
                                            this.sinisterTypeSettings.length;
                                            index++
                                        ) {
                                            const element = this
                                                .sinisterTypeSettings[index];
                                            if (
                                                element.sinisterTypeId ==
                                                    this.sinisterPec.modeId &&
                                                this.quotation.ttcAmount <
                                                    element.ceiling
                                            ) {
                                                this.test = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (this.test == false) {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId =
                                            PrestationPecStep.MISSIONNER_EXPERT;
                                    }
                                }
                            }
                        }
                        this.sendDateReceptionVehicule();
                        this.sinisterPec.quotation = this.quotation;
                        if (this.test == false) {
                            this.sinisterPecService
                                .updatePecForQuotation(this.sinisterPec, true)
                                .subscribe((res) => {
                                    this.sinisterPec = res;
                                    if (this.sinisterPec.expertId == null) {
                                        this.sendNotif(
                                            this.sinisterPec.reparateurId,
                                            "Missionner un expert"
                                        );
                                    } else {
                                        this.sendNotif(
                                            this.sinisterPec.reparateurId,
                                            "Avis Expert"
                                        );
                                    }

                                    this.router.navigate(["../verification"]);
                                });
                        } else {
                            if (this.additionalQuote) {
                                this.apecService
                                    .generateAccordPrisCharge(
                                        this.sinisterPec,
                                        this.quotation.id,
                                        true,
                                        6,
                                        this.labelAccordNormal
                                    )
                                    .subscribe((resPdf) => {
                                        this.apecService
                                            .findByQuotation(this.quotation.id)
                                            .subscribe((apec: Apec) => {
                                                apec.etat = 17;
                                                this.apecService
                                                    .update(apec)
                                                    .subscribe((res: Apec) => {
                                                        this.apec = res;
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                                                        this.sinisterPecService
                                                            .updatePecForQuotation(
                                                                this
                                                                    .sinisterPec,
                                                                true
                                                            )
                                                            .subscribe(
                                                                (resN) => {
                                                                    if (
                                                                        this
                                                                            .oneClickForButtonAvis
                                                                    ) {
                                                                        this.oneClickForButtonAvis = false;
                                                                        this.listNotifUser = [];
                                                                        this.userExNotif = [];

                                                                        let settingJson = {
                                                                            typenotification:
                                                                                "ACCORD_POUR_REPARATION",
                                                                            sinisterPecId: this
                                                                                .sinisterPec
                                                                                .id,
                                                                            quotationId: this
                                                                                .quotation
                                                                                .id,
                                                                            accord:
                                                                                "validationapec",
                                                                            refSinister: this
                                                                                .sinisterPec
                                                                                .reference,
                                                                        };
                                                                        this.sendNotifAccordPourReparation(
                                                                            32,
                                                                            this
                                                                                .sinisterPec
                                                                                .assignedToId,
                                                                            this
                                                                                .userExtra
                                                                                .id,
                                                                            settingJson
                                                                        );
                                                                        this.notificationAlerteService
                                                                            .create(
                                                                                this
                                                                                    .listNotifUser
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    res
                                                                                ) => {
                                                                                    this.ws.send(
                                                                                        "/app/hello",
                                                                                        {},
                                                                                        JSON.stringify(
                                                                                            settingJson
                                                                                        )
                                                                                    );

                                                                                    this.router.navigate(
                                                                                        [
                                                                                            "../verification",
                                                                                        ]
                                                                                    );
                                                                                }
                                                                            );
                                                                    }
                                                                }
                                                            );
                                                    });
                                            });
                                    });
                            } else {
                                if (this.apecSettingsAvenant.length !== 0) {
                                    for (
                                        let index = 0;
                                        index < this.apecSettingsAvenant.length;
                                        index++
                                    ) {
                                        const element = this
                                            .apecSettingsAvenant[index];
                                        if (
                                            element.mgntModeId ==
                                            this.sinisterPec.modeId
                                        ) {
                                            this.existMode = true;
                                            if (
                                                element.operator ==
                                                Operator.GREATER
                                            ) {
                                                if (
                                                    this.quotation.ttcAmount >
                                                    element.ceiling
                                                ) {
                                                    this.testValidDevis = true;
                                                    break;
                                                } else {
                                                    this.testValidDevis = false;
                                                    break;
                                                }
                                            } else {
                                                if (
                                                    this.quotation.ttcAmount <=
                                                    element.ceiling
                                                ) {
                                                    this.testValidDevis = true;
                                                    break;
                                                } else {
                                                    this.testValidDevis = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (
                                    this.apecSettingsAvenant.length == 0 ||
                                    this.existMode == false
                                ) {
                                    for (
                                        let index = 0;
                                        index < this.apecSettings.length;
                                        index++
                                    ) {
                                        const element = this.apecSettings[
                                            index
                                        ];
                                        if (
                                            element.mgntModeId ==
                                            this.sinisterPec.modeId
                                        ) {
                                            if (
                                                element.operator ==
                                                Operator.GREATER
                                            ) {
                                                if (
                                                    this.quotation.ttcAmount >
                                                    element.ceiling
                                                ) {
                                                    this.testValidDevis = true;
                                                    break;
                                                } else {
                                                    this.testValidDevis = false;
                                                    break;
                                                }
                                            } else {
                                                if (
                                                    this.quotation.ttcAmount <=
                                                    element.ceiling
                                                ) {
                                                    this.testValidDevis = true;
                                                    break;
                                                } else {
                                                    this.testValidDevis = false;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (this.testValidDevis == true) {
                                    this.apecService
                                        .generateAccordPrisCharge(
                                            this.sinisterPec,
                                            this.quotation.id,
                                            true,
                                            6,
                                            this.labelAccordNormal
                                        )
                                        .subscribe((resPdf) => {
                                            this.apecService
                                                .findByQuotation(
                                                    this.quotation.id
                                                )
                                                .subscribe((apec: Apec) => {
                                                    apec.etat = 0;
                                                    apec.testDevis = false;
                                                    this.apecService
                                                        .update(apec)
                                                        .subscribe(
                                                            (res: Apec) => {
                                                                this.apec = res;
                                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                this.sinisterPec.stepId = 100;
                                                                this.sinisterPec.testModifPrix = false;
                                                                this.sinisterPecService
                                                                    .updatePecForQuotation(
                                                                        this
                                                                            .sinisterPec,
                                                                        true
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            resN
                                                                        ) => {
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.listNotifUser = [];
                                                                                this.userExNotif = [];

                                                                                let settingJson = {
                                                                                    typenotification:
                                                                                        "ACCORD_POUR_REPARATION",
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    quotationId: this
                                                                                        .quotation
                                                                                        .id,
                                                                                    accord:
                                                                                        "validationapec",
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                };
                                                                                this.sendNotifAccordPourReparation(
                                                                                    32,
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .assignedToId,
                                                                                    this
                                                                                        .userExtra
                                                                                        .id,
                                                                                    settingJson
                                                                                );
                                                                                this.notificationAlerteService
                                                                                    .create(
                                                                                        this
                                                                                            .listNotifUser
                                                                                    )
                                                                                    .subscribe(
                                                                                        (
                                                                                            res
                                                                                        ) => {
                                                                                            this.ws.send(
                                                                                                "/app/hello",
                                                                                                {},
                                                                                                JSON.stringify(
                                                                                                    settingJson
                                                                                                )
                                                                                            );

                                                                                            this.router.navigate(
                                                                                                [
                                                                                                    "../verification",
                                                                                                ]
                                                                                            );
                                                                                        }
                                                                                    );
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        });
                                } else {
                                    this.apecService
                                        .generateAccordPrisCharge(
                                            this.sinisterPec,
                                            this.quotation.id,
                                            true,
                                            6,
                                            this.labelAccordNormal
                                        )
                                        .subscribe((resPdf) => {
                                            this.apecService
                                                .findByQuotation(
                                                    this.quotation.id
                                                )
                                                .subscribe((apec: Apec) => {
                                                    apec.etat = 6;
                                                    apec.testDevis = false;
                                                    this.apecService
                                                        .update(apec)
                                                        .subscribe(
                                                            (res: Apec) => {
                                                                this.apec = res;

                                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                this.sinisterPec.stepId = 106;
                                                                //this.sinisterPec.testModifPrix = false;
                                                                this.sinisterPecService
                                                                    .updatePecForQuotation(
                                                                        this
                                                                            .sinisterPec,
                                                                        true
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            resN
                                                                        ) => {
                                                                            if (
                                                                                this
                                                                                    .oneClickForButtonAvis
                                                                            ) {
                                                                                this.oneClickForButtonAvis = false;
                                                                                this.listNotifUser = [];
                                                                                this.userExNotif = [];

                                                                                let settingJson = {
                                                                                    typenotification:
                                                                                        "ACCORD_POUR_REPARATION",
                                                                                    sinisterPecId: this
                                                                                        .sinisterPec
                                                                                        .id,
                                                                                    quotationId: this
                                                                                        .quotation
                                                                                        .id,
                                                                                    accord:
                                                                                        "validationapec",
                                                                                    refSinister: this
                                                                                        .sinisterPec
                                                                                        .reference,
                                                                                };
                                                                                this.sendNotifAccordPourReparation(
                                                                                    32,
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .assignedToId,
                                                                                    this
                                                                                        .userExtra
                                                                                        .id,
                                                                                    settingJson
                                                                                );
                                                                                this.notificationAlerteService
                                                                                    .create(
                                                                                        this
                                                                                            .listNotifUser
                                                                                    )
                                                                                    .subscribe(
                                                                                        (
                                                                                            res
                                                                                        ) => {
                                                                                            this.ws.send(
                                                                                                "/app/hello",
                                                                                                {},
                                                                                                JSON.stringify(
                                                                                                    settingJson
                                                                                                )
                                                                                            );

                                                                                            this.router.navigate(
                                                                                                [
                                                                                                    "../verification",
                                                                                                ]
                                                                                            );
                                                                                        }
                                                                                    );
                                                                            }
                                                                        }
                                                                    );
                                                            }
                                                        );
                                                });
                                        });
                                }
                            }
                        }
                    }
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    /***Function return  true if we have a observation with updatade when it is expert opinion ***/
    DecisionExpertByObser(id: number, detailsPiece: DetailsPieces) {
        if (id == 1) {
            detailsPiece.nombreMOEstime = 0;
        }
        if (id == 2) {
            detailsPiece.nombreMOEstime = 0;
        }

        if (this.additionalQuote && id == 2) {
            console.log("testAVISEXPERT");
            this.detailsPiecesDevis = [];
            let list = this.detailsPiecesDevis.concat(
                this.detailsPieces,
                this.detailsPiecesMO,
                this.detailsPiecesIngredient,
                this.detailsPiecesFourniture
            );
            for (let index = 0; index < list.length; index++) {
                const element = list[index];
                this.obserExpertQuoteCanceled =
                    element.observationExpert == 2 ? true : false;
                if (!this.obserExpertQuoteCanceled) break;
            }
            console.log(
                "testAVISEXPERToLLLLLLL " + this.obserExpertQuoteCanceled
            );
        } else {
            this.obserExpertQuoteCanceled = false;
            if (id == 2 || id == 3 || id == 4) {
                this.obserExpert = false;
                console.log("testAVISEXPERTTEST");
            } else {
                this.detailsPiecesDevis = [];
                let list = this.detailsPiecesDevis.concat(
                    this.detailsPieces,
                    this.detailsPiecesMO,
                    this.detailsPiecesIngredient,
                    this.detailsPiecesFourniture
                );
                for (let index = 0; index < list.length; index++) {
                    const element = list[index];
                    this.obserExpert =
                        element.observationExpert == 1 ? true : false;
                    if (!this.obserExpert) break;
                }
                console.log("testAVISEXPERTTESTTest " + this.obserExpert);
            }
        }
    }

    /*** confirmation devis principal et complémentaire ***/
    confirmationDevis() {
        if (this.quotation.confirmationDecisionQuote == true) {
            this.confirmationDialogService
                .confirm(
                    "Confirmation",
                    " Etes-vous sûr de vouloir valider le devis ?",
                    "Oui",
                    "Non",
                    "lg"
                )
                .then((confirmed) => {
                    console.log("User confirmed:", confirmed);
                    if (confirmed) {
                        if (this.oneClickForButton28) {
                            this.oneClickForButton28 = false;
                            this.sinisterPec.motifNonConfirmeId = null;
                            if (
                                this.responsableControleTechnique.profileId ==
                                21
                            ) {
                                this.sinisterPec.respControleTechnique = this.responsableControleTechnique.id;
                            }
                            this.sendDateReceptionVehicule();
                            this.quotation.statusId =
                                QuoteStatus.DEVIS_CONFERME_PAR_LE_REPARATEUR;
                            this.route.params.subscribe((params) => {
                                for (
                                    let i = 0;
                                    i < this.detailsPiecesMO.length;
                                    i++
                                ) {
                                    this.detailsPiecesMO[i].isMo = true;
                                }
                                if (
                                    this.sinisterPec.preliminaryReport ==
                                        true &&
                                    this.confirmationDevisComplementaire == true
                                ) {
                                    let listPieces = this.detailsPiecesMOCP.concat(
                                        this.detailsPiecesCP,
                                        this.detailsPiecesFournitureCP,
                                        this.detailsPiecesIngredientCP
                                    );
                                    var cache = {};
                                    listPieces = listPieces.filter(function (
                                        elem
                                    ) {
                                        return cache[elem.id]
                                            ? 0
                                            : (cache[elem.id] = 1);
                                    });
                                    listPieces.forEach((listPieceDetails) => {
                                        listPieceDetails.isModified = false;
                                        listPieceDetails.id = null;
                                    });
                                    this.quotation.id = this.sinisterPec.primaryQuotationId;
                                    this.quotation.listPieces = listPieces;
                                    this.sinisterPec.quotation = this.quotation;
                                } else {
                                    let listPieces = this.detailsPiecesMO.concat(
                                        this.detailsPieces,
                                        this.detailsPiecesFourniture,
                                        this.detailsPiecesIngredient,
                                        this.detailsPieces1,
                                        this.detailsPiecesIngredient1,
                                        this.detailsPiecesFourniture1,
                                        this.detailsPiecesMO1
                                    );
                                    var cache = {};
                                    listPieces = listPieces.filter(function (
                                        elem
                                    ) {
                                        return cache[elem.id]
                                            ? 0
                                            : (cache[elem.id] = 1);
                                    });
                                    this.quotation.listPieces = listPieces;
                                    this.sinisterPec.quotation = this.quotation;
                                }

                                if (
                                    this.sinisterPec.stepId ==
                                    PrestationPecStep.REVUE_VERIFICATION_DEVIS
                                ) {
                                    if (this.apecSettingsAvenant.length !== 0) {
                                        for (
                                            let index = 0;
                                            index <
                                            this.apecSettingsAvenant.length;
                                            index++
                                        ) {
                                            const element = this
                                                .apecSettingsAvenant[index];
                                            if (
                                                element.mgntModeId ==
                                                this.sinisterPec.modeId
                                            ) {
                                                this.existMode = true;
                                                if (
                                                    element.operator ==
                                                    Operator.GREATER
                                                ) {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount >
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                } else {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount <=
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (
                                        this.apecSettingsAvenant.length == 0 ||
                                        this.existMode == false
                                    ) {
                                        for (
                                            let index = 0;
                                            index < this.apecSettings.length;
                                            index++
                                        ) {
                                            const element = this.apecSettings[
                                                index
                                            ];
                                            if (
                                                element.mgntModeId ==
                                                this.sinisterPec.modeId
                                            ) {
                                                if (
                                                    element.operator ==
                                                    Operator.GREATER
                                                ) {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount >
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                } else {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount <=
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        //this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    this.quotation.revueDate = this.dateAsYYYYMMDDHHNNSSLDT(
                                        new Date()
                                    );
                                    this.sinisterPec.oldStepNw =
                                        PrestationPecStep.REVUE_VERIFICATION_DEVIS;
                                    this.sinisterPec.historyAvisExpert =
                                        "RevueQuotation";
                                    this.sinisterPecService
                                        .updatePecForQuotation(
                                            this.sinisterPec,
                                            true
                                        )
                                        .subscribe((resIn) => {
                                            this.sinisterPec = resIn;
                                            console.log("testRevue");
                                            this.testRevue = true;
                                            if (!this.sinisterPec.lightShock) {
                                                if (this.additionalQuote) {
                                                    if (
                                                        this.sinisterPec
                                                            .pieceGenerique ==
                                                        true
                                                    ) {
                                                        this.sinisterPec.pieceGenerique = null;
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.EXPERT_ADVICE;
                                                        this.sendNotifValidationDevisConfirmer(
                                                            "Avis Expert"
                                                        );
                                                    } else {
                                                        if (
                                                            this.quotation
                                                                .expertDecision ==
                                                            "Accord pour réparation avec modification"
                                                        ) {
                                                            // decision expert
                                                            console.log(
                                                                "revu verification devis and not light chock and accord avec modificationand generate accord"
                                                            );
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                                                            this.apecService
                                                                .generateAccordPrisCharge(
                                                                    this
                                                                        .sinisterPec,
                                                                    this
                                                                        .quotation
                                                                        .id,
                                                                    true,
                                                                    6,
                                                                    this
                                                                        .labelAccordNormal
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        resPdf
                                                                    ) => {
                                                                        this.apecService
                                                                            .findByQuotation(
                                                                                this
                                                                                    .quotation
                                                                                    .id
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    apec: Apec
                                                                                ) => {
                                                                                    apec.etat = 17;
                                                                                    this.apecService
                                                                                        .update(
                                                                                            apec
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res: Apec
                                                                                            ) => {
                                                                                                this.apec = res;
                                                                                                this.sendNotifValidationDevisConfirmer(
                                                                                                    "AccordPourReparationDevisCompl"
                                                                                                );
                                                                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                                                this.sinisterPec.stepId =
                                                                                                    PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                                                                                                this.sinisterPecService
                                                                                                    .updateIt(
                                                                                                        this
                                                                                                            .sinisterPec
                                                                                                    )
                                                                                                    .subscribe(
                                                                                                        (
                                                                                                            resN
                                                                                                        ) => {}
                                                                                                    );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        } else {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            console.log(
                                                                "revu verification devis and not light chock and not accord avec modification and generate accord"
                                                            );
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.EXPERT_ADVICE;
                                                            this.sendNotifValidationDevisConfirmer(
                                                                "Avis Expert"
                                                            );
                                                        }
                                                    }
                                                } else {
                                                    if (
                                                        this.sinisterPec
                                                            .pieceGenerique ==
                                                        true
                                                    ) {
                                                        this.sinisterPec.pieceGenerique = null;
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.EXPERT_ADVICE;
                                                        this.sendNotifValidationDevisConfirmer(
                                                            "Avis Expert"
                                                        );
                                                    } else {
                                                        if (
                                                            this.sinisterPec
                                                                .expertDecision ==
                                                            "Accord pour réparation avec modification"
                                                        ) {
                                                            // decision expert
                                                            if (
                                                                this
                                                                    .testConfirmDevis ==
                                                                true
                                                            ) {
                                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                this.sinisterPec.stepId =
                                                                    PrestationPecStep.APPROUVER_APEC;
                                                                this.apecService
                                                                    .generateAccordPrisCharge(
                                                                        this
                                                                            .sinisterPec,
                                                                        this
                                                                            .quotation
                                                                            .id,
                                                                        true,
                                                                        6,
                                                                        this
                                                                            .labelAccordNormal
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            resPdf
                                                                        ) => {
                                                                            this.apecService
                                                                                .findByQuotation(
                                                                                    this
                                                                                        .quotation
                                                                                        .id
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        apec: Apec
                                                                                    ) => {
                                                                                        apec.etat = 0;
                                                                                        apec.testDevis = false;
                                                                                        this.apecService
                                                                                            .update(
                                                                                                apec
                                                                                            )
                                                                                            .subscribe(
                                                                                                (
                                                                                                    res: Apec
                                                                                                ) => {
                                                                                                    this.apec = res;
                                                                                                    this.sendNotifValidationDevisConfirmer(
                                                                                                        "devisComplementaireConfirmeApprouve"
                                                                                                    );
                                                                                                }
                                                                                            );
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            } else {
                                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                this.sinisterPec.stepId =
                                                                    PrestationPecStep.VALIDATION_APEC;
                                                                this.apecService
                                                                    .generateAccordPrisCharge(
                                                                        this
                                                                            .sinisterPec,
                                                                        this
                                                                            .quotation
                                                                            .id,
                                                                        true,
                                                                        6,
                                                                        this
                                                                            .labelAccordNormal
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            resPdf
                                                                        ) => {
                                                                            this.apecService
                                                                                .findByQuotation(
                                                                                    this
                                                                                        .quotation
                                                                                        .id
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        apec: Apec
                                                                                    ) => {
                                                                                        apec.etat = 6;
                                                                                        apec.testDevis = false;
                                                                                        this.apecService
                                                                                            .update(
                                                                                                apec
                                                                                            )
                                                                                            .subscribe(
                                                                                                (
                                                                                                    res: Apec
                                                                                                ) => {
                                                                                                    this.apec = res;
                                                                                                    this.sendNotifValidationDevisConfirmer(
                                                                                                        "devisComplementaireConfirmeValid"
                                                                                                    );
                                                                                                }
                                                                                            );
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            }
                                                        } else {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            console.log(
                                                                "revu verification devis and not light chock and not accord avec modification and generate accord"
                                                            );
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.EXPERT_ADVICE;
                                                            this.sendNotifValidationDevisConfirmer(
                                                                "Avis Expert"
                                                            );
                                                        }
                                                    }
                                                }
                                            } else if (
                                                this.sinisterPec.expertId !=
                                                    null &&
                                                this.sinisterPec.expertId !=
                                                    undefined
                                            ) {
                                                // si affectation expert
                                                if (this.additionalQuote) {
                                                    if (
                                                        this.sinisterPec
                                                            .pieceGenerique ==
                                                        true
                                                    ) {
                                                        this.sinisterPec.pieceGenerique = null;
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.EXPERT_ADVICE;
                                                        this.sendNotifValidationDevisConfirmer(
                                                            "Avis Expert"
                                                        );
                                                    } else {
                                                        if (
                                                            this.quotation
                                                                .expertDecision !=
                                                                null &&
                                                            this.quotation
                                                                .expertDecision !=
                                                                undefined
                                                        ) {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                                                            this.apecService
                                                                .generateAccordPrisCharge(
                                                                    this
                                                                        .sinisterPec,
                                                                    this
                                                                        .quotation
                                                                        .id,
                                                                    true,
                                                                    6,
                                                                    this
                                                                        .labelAccordNormal
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        resPdf
                                                                    ) => {
                                                                        this.apecService
                                                                            .findByQuotation(
                                                                                this
                                                                                    .quotation
                                                                                    .id
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    apec: Apec
                                                                                ) => {
                                                                                    apec.etat = 17;
                                                                                    this.apecService
                                                                                        .update(
                                                                                            apec
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res: Apec
                                                                                            ) => {
                                                                                                this.apec = res;
                                                                                                this.sendNotifValidationDevisConfirmer(
                                                                                                    "AccordPourReparationDevisCompl"
                                                                                                );
                                                                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                                                this.sinisterPec.stepId =
                                                                                                    PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                                                                                                this.sinisterPecService
                                                                                                    .updateIt(
                                                                                                        this
                                                                                                            .sinisterPec
                                                                                                    )
                                                                                                    .subscribe(
                                                                                                        (
                                                                                                            resN
                                                                                                        ) => {}
                                                                                                    );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        } else {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.EXPERT_ADVICE;
                                                            this.sendNotifValidationDevisConfirmer(
                                                                "Avis Expert"
                                                            );
                                                        }
                                                    }
                                                } else {
                                                    if (
                                                        this.sinisterPec
                                                            .pieceGenerique ==
                                                        true
                                                    ) {
                                                        this.sinisterPec.pieceGenerique = null;
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.EXPERT_ADVICE;
                                                        this.sendNotifValidationDevisConfirmer(
                                                            "Avis Expert"
                                                        );
                                                    } else {
                                                        if (
                                                            this
                                                                .testConfirmDevis ==
                                                            true
                                                        ) {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.APPROUVER_APEC;
                                                            this.apecService
                                                                .generateAccordPrisCharge(
                                                                    this
                                                                        .sinisterPec,
                                                                    this
                                                                        .quotation
                                                                        .id,
                                                                    true,
                                                                    6,
                                                                    this
                                                                        .labelAccordNormal
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        resPdf
                                                                    ) => {
                                                                        this.apecService
                                                                            .findByQuotation(
                                                                                this
                                                                                    .quotation
                                                                                    .id
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    apec: Apec
                                                                                ) => {
                                                                                    apec.etat = 0;
                                                                                    apec.testDevis = false;
                                                                                    this.apecService
                                                                                        .update(
                                                                                            apec
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res: Apec
                                                                                            ) => {
                                                                                                this.apec = res;
                                                                                                this.sendNotifValidationDevisConfirmer(
                                                                                                    "devisComplementaireConfirmeApprouve"
                                                                                                );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        } else {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.VALIDATION_APEC;
                                                            this.apecService
                                                                .generateAccordPrisCharge(
                                                                    this
                                                                        .sinisterPec,
                                                                    this
                                                                        .quotation
                                                                        .id,
                                                                    true,
                                                                    6,
                                                                    this
                                                                        .labelAccordNormal
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        resPdf
                                                                    ) => {
                                                                        this.apecService
                                                                            .findByQuotation(
                                                                                this
                                                                                    .quotation
                                                                                    .id
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    apec: Apec
                                                                                ) => {
                                                                                    apec.etat = 6;
                                                                                    apec.testDevis = false;
                                                                                    this.apecService
                                                                                        .update(
                                                                                            apec
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res: Apec
                                                                                            ) => {
                                                                                                this.apec = res;
                                                                                                this.sendNotifValidationDevisConfirmer(
                                                                                                    "devisComplementaireConfirmeValid"
                                                                                                );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        }
                                                    }
                                                }
                                            } else {
                                                this.test = false;
                                                this.existModeValidation = false;
                                                if (
                                                    this
                                                        .sinisterTypeSettingsAvenant
                                                        .length !== 0
                                                ) {
                                                    for (
                                                        let index = 0;
                                                        index <
                                                        this
                                                            .sinisterTypeSettingsAvenant
                                                            .length;
                                                        index++
                                                    ) {
                                                        const element = this
                                                            .sinisterTypeSettingsAvenant[
                                                            index
                                                        ];
                                                        if (
                                                            element.sinisterTypeId ==
                                                            this.sinisterPec
                                                                .modeId
                                                        ) {
                                                            this.existModeValidation = true;
                                                            if (
                                                                this.quotation
                                                                    .ttcAmount <
                                                                element.ceiling
                                                            ) {
                                                                this.test = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (
                                                    this
                                                        .sinisterTypeSettingsAvenant
                                                        .length == 0 ||
                                                    this.existModeValidation ==
                                                        false
                                                ) {
                                                    for (
                                                        let index = 0;
                                                        index <
                                                        this
                                                            .sinisterTypeSettings
                                                            .length;
                                                        index++
                                                    ) {
                                                        const element = this
                                                            .sinisterTypeSettings[
                                                            index
                                                        ];
                                                        if (
                                                            element.sinisterTypeId ==
                                                                this.sinisterPec
                                                                    .modeId &&
                                                            this.quotation
                                                                .ttcAmount <
                                                                element.ceiling
                                                        ) {
                                                            this.test = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (this.additionalQuote) {
                                                    if (
                                                        this.sinisterPec
                                                            .pieceGenerique ==
                                                        true
                                                    ) {
                                                        this.sinisterPec.pieceGenerique = null;
                                                    }
                                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                    this.sinisterPec.stepId =
                                                        PrestationPecStep.RECTIFICATION_QUOTE_ADD;
                                                    this.apecService
                                                        .generateAccordPrisCharge(
                                                            this.sinisterPec,
                                                            this.quotation.id,
                                                            true,
                                                            6,
                                                            this
                                                                .labelAccordNormal
                                                        )
                                                        .subscribe((resPdf) => {
                                                            this.apecService
                                                                .findByQuotation(
                                                                    this
                                                                        .quotation
                                                                        .id
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        apec: Apec
                                                                    ) => {
                                                                        apec.etat = 17;
                                                                        this.apecService
                                                                            .update(
                                                                                apec
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    res: Apec
                                                                                ) => {
                                                                                    this.apec = res;
                                                                                    this.sendNotifValidationDevisConfirmer(
                                                                                        "AccordPourReparationDevisCompl"
                                                                                    );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        });
                                                } else {
                                                    if (
                                                        this.sinisterPec
                                                            .pieceGenerique ==
                                                        true
                                                    ) {
                                                        this.sinisterPec.pieceGenerique = null;
                                                    }
                                                    if (this.test == true) {
                                                        if (
                                                            this
                                                                .testConfirmDevis ==
                                                            true
                                                        ) {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.APPROUVER_APEC;
                                                            this.apecService
                                                                .generateAccordPrisCharge(
                                                                    this
                                                                        .sinisterPec,
                                                                    this
                                                                        .quotation
                                                                        .id,
                                                                    true,
                                                                    6,
                                                                    this
                                                                        .labelAccordNormal
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        resPdf
                                                                    ) => {
                                                                        this.apecService
                                                                            .findByQuotation(
                                                                                this
                                                                                    .quotation
                                                                                    .id
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    apec: Apec
                                                                                ) => {
                                                                                    apec.etat = 0;
                                                                                    apec.testDevis = false;
                                                                                    this.apecService
                                                                                        .update(
                                                                                            apec
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res: Apec
                                                                                            ) => {
                                                                                                this.apec = res;
                                                                                                this.sendNotifValidationDevisConfirmer(
                                                                                                    "devisComplementaireConfirmeApprouve"
                                                                                                );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        } else {
                                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                            this.sinisterPec.stepId =
                                                                PrestationPecStep.VALIDATION_APEC;
                                                            this.apecService
                                                                .generateAccordPrisCharge(
                                                                    this
                                                                        .sinisterPec,
                                                                    this
                                                                        .quotation
                                                                        .id,
                                                                    true,
                                                                    6,
                                                                    this
                                                                        .labelAccordNormal
                                                                )
                                                                .subscribe(
                                                                    (
                                                                        resPdf
                                                                    ) => {
                                                                        this.apecService
                                                                            .findByQuotation(
                                                                                this
                                                                                    .quotation
                                                                                    .id
                                                                            )
                                                                            .subscribe(
                                                                                (
                                                                                    apec: Apec
                                                                                ) => {
                                                                                    apec.etat = 6;
                                                                                    apec.testDevis = false;
                                                                                    this.apecService
                                                                                        .update(
                                                                                            apec
                                                                                        )
                                                                                        .subscribe(
                                                                                            (
                                                                                                res: Apec
                                                                                            ) => {
                                                                                                this.apec = res;
                                                                                                this.sendNotifValidationDevisConfirmer(
                                                                                                    "devisComplementaireConfirmeValid"
                                                                                                );
                                                                                            }
                                                                                        );
                                                                                }
                                                                            );
                                                                    }
                                                                );
                                                        }
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.MISSIONNER_EXPERT;
                                                        this.sendNotifValidationDevisConfirmer(
                                                            "Missionner un expert"
                                                        );
                                                    }
                                                }
                                            }

                                            this.sinisterPecService
                                                .updateIt(this.sinisterPec)
                                                .subscribe((res) => {
                                                    this.sinisterPec = res;

                                                    if (
                                                        this
                                                            .RevueValidationDevis
                                                    ) {
                                                        this.router.navigate([
                                                            "../revue-validation-devis",
                                                        ]);
                                                    } else if (
                                                        this.additionalQuote
                                                    ) {
                                                        this.router.navigate([
                                                            "../confirmation-devis-complemantary",
                                                        ]);
                                                    } else {
                                                        this.sendNotif(
                                                            this.sinisterPec
                                                                .reparateurId,
                                                            "Devis Confirmé par le réparateur"
                                                        );
                                                        this.router.navigate([
                                                            "../confirmation-devis",
                                                        ]);
                                                    }
                                                });
                                        });
                                } else if (!this.testRevue) {
                                    console.log("testNotRevue");
                                    console.log(
                                        "testConvension " +
                                            this.refPack.apecSettings.length
                                    );

                                    if (this.apecSettingsAvenant.length !== 0) {
                                        for (
                                            let index = 0;
                                            index <
                                            this.apecSettingsAvenant.length;
                                            index++
                                        ) {
                                            const element = this
                                                .apecSettingsAvenant[index];
                                            if (
                                                element.mgntModeId ==
                                                this.sinisterPec.modeId
                                            ) {
                                                this.existMode = true;
                                                if (
                                                    element.operator ==
                                                    Operator.GREATER
                                                ) {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount >
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                } else {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount <=
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (
                                        this.apecSettingsAvenant.length == 0 ||
                                        this.existMode == false
                                    ) {
                                        for (
                                            let index = 0;
                                            index < this.apecSettings.length;
                                            index++
                                        ) {
                                            const element = this.apecSettings[
                                                index
                                            ];
                                            if (
                                                element.mgntModeId ==
                                                this.sinisterPec.modeId
                                            ) {
                                                if (
                                                    element.operator ==
                                                    Operator.GREATER
                                                ) {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount >
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                } else {
                                                    if (
                                                        this.quotation
                                                            .ttcAmount <=
                                                        element.ceiling
                                                    ) {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.APPROUVER_APEC;
                                                        this.testConfirmDevis = true;
                                                        break;
                                                    } else {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId =
                                                            PrestationPecStep.VALIDATION_APEC;
                                                        this.testConfirmDevis = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    let quoteId = 0;
                                    if (
                                        this.sinisterPec.preliminaryReport ==
                                            true &&
                                        this.confirmationDevisComplementaire ==
                                            true &&
                                        this.quotationId !== 0
                                    ) {
                                        quoteId = this.quotationId;
                                    } else {
                                        quoteId = this.quotation.id;
                                    }
                                    this.apecService
                                        .findByQuotation(quoteId)
                                        .subscribe((apec: Apec) => {
                                            this.apec = apec;
                                            if (
                                                this.apec.id != null &&
                                                this.apec.id !== undefined &&
                                                this.additionalQuote
                                            ) {
                                                this.quotation.confirmationDevisComplementaireDate = this.dateAsYYYYMMDDHHNNSSLDT(
                                                    new Date()
                                                );
                                                if (
                                                    this.apec.etat == 17 &&
                                                    this.apec.isComplementaire
                                                ) {
                                                    // confirmation devis complementaire aprés imprim accord
                                                    this.apecService
                                                        .delete(this.apec.id)
                                                        .subscribe((res) => {
                                                            if (
                                                                this.sinisterPec
                                                                    .preliminaryReport ==
                                                                    true &&
                                                                this
                                                                    .confirmationDevisComplementaire ==
                                                                    true
                                                            ) {
                                                                this.sinisterPec.preliminaryReport = null;
                                                                this.detailsPiecesService
                                                                    .deleteByQuery(
                                                                        this
                                                                            .sinisterPec
                                                                            .primaryQuotationId
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            response
                                                                        ) => {
                                                                            this.detailsPiecesService
                                                                                .deleteByQueryMP(
                                                                                    this
                                                                                        .sinisterPec
                                                                                        .id
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        response
                                                                                    ) => {
                                                                                        this.sinisterPecService
                                                                                            .updatePecForQuotation(
                                                                                                this
                                                                                                    .sinisterPec,
                                                                                                true
                                                                                            )
                                                                                            .subscribe(
                                                                                                (
                                                                                                    res
                                                                                                ) => {
                                                                                                    this.sinisterPec = res;
                                                                                                    this.apecService
                                                                                                        .deleteAPecDevisCompl(
                                                                                                            this
                                                                                                                .sinisterPec
                                                                                                                .id
                                                                                                        )
                                                                                                        .subscribe(
                                                                                                            (
                                                                                                                resDel
                                                                                                            ) => {
                                                                                                                this.apecService
                                                                                                                    .generateAccordPrisCharge(
                                                                                                                        this
                                                                                                                            .sinisterPec,
                                                                                                                        this
                                                                                                                            .quotation
                                                                                                                            .id,
                                                                                                                        true,
                                                                                                                        6,
                                                                                                                        this
                                                                                                                            .labelAccordNormal
                                                                                                                    )
                                                                                                                    .subscribe(
                                                                                                                        (
                                                                                                                            resPdf
                                                                                                                        ) => {
                                                                                                                            if (
                                                                                                                                this
                                                                                                                                    .testConfirmDevis ==
                                                                                                                                true
                                                                                                                            ) {
                                                                                                                                this.apecService
                                                                                                                                    .findByQuotation(
                                                                                                                                        this
                                                                                                                                            .quotation
                                                                                                                                            .id
                                                                                                                                    )
                                                                                                                                    .subscribe(
                                                                                                                                        (
                                                                                                                                            apec: Apec
                                                                                                                                        ) => {
                                                                                                                                            apec.etat = 0;
                                                                                                                                            apec.testDevis = false;
                                                                                                                                            this.apecService
                                                                                                                                                .update(
                                                                                                                                                    apec
                                                                                                                                                )
                                                                                                                                                .subscribe(
                                                                                                                                                    (
                                                                                                                                                        res: Apec
                                                                                                                                                    ) => {
                                                                                                                                                        this.apec = res;
                                                                                                                                                        this.quotationService
                                                                                                                                                            .deleteAdditionnelQuote(
                                                                                                                                                                this
                                                                                                                                                                    .sinisterPec
                                                                                                                                                                    .id
                                                                                                                                                            )
                                                                                                                                                            .subscribe(
                                                                                                                                                                (
                                                                                                                                                                    resAQ
                                                                                                                                                                ) => {
                                                                                                                                                                    this.sendNotifConfirmationDevisComplementaire(
                                                                                                                                                                        "devisComplementaireConfirmeApprouve",
                                                                                                                                                                        this
                                                                                                                                                                            .apec
                                                                                                                                                                            .id
                                                                                                                                                                    );
                                                                                                                                                                    this.router.navigate(
                                                                                                                                                                        [
                                                                                                                                                                            "../confirmation-devis-complemantary",
                                                                                                                                                                        ]
                                                                                                                                                                    );
                                                                                                                                                                }
                                                                                                                                                            );
                                                                                                                                                    }
                                                                                                                                                );
                                                                                                                                        }
                                                                                                                                    );
                                                                                                                            } else {
                                                                                                                                this.apecService
                                                                                                                                    .findByQuotation(
                                                                                                                                        this
                                                                                                                                            .quotation
                                                                                                                                            .id
                                                                                                                                    )
                                                                                                                                    .subscribe(
                                                                                                                                        (
                                                                                                                                            apec: Apec
                                                                                                                                        ) => {
                                                                                                                                            apec.etat = 6;
                                                                                                                                            apec.testDevis = false;
                                                                                                                                            this.apecService
                                                                                                                                                .update(
                                                                                                                                                    apec
                                                                                                                                                )
                                                                                                                                                .subscribe(
                                                                                                                                                    (
                                                                                                                                                        res: Apec
                                                                                                                                                    ) => {
                                                                                                                                                        this.apec = res;
                                                                                                                                                        this.quotationService
                                                                                                                                                            .deleteAdditionnelQuote(
                                                                                                                                                                this
                                                                                                                                                                    .sinisterPec
                                                                                                                                                                    .id
                                                                                                                                                            )
                                                                                                                                                            .subscribe(
                                                                                                                                                                (
                                                                                                                                                                    resAQ
                                                                                                                                                                ) => {
                                                                                                                                                                    this.sendNotifConfirmationDevisComplementaire(
                                                                                                                                                                        "devisComplementaireConfirmeValid",
                                                                                                                                                                        this
                                                                                                                                                                            .apec
                                                                                                                                                                            .id
                                                                                                                                                                    );
                                                                                                                                                                    this.router.navigate(
                                                                                                                                                                        [
                                                                                                                                                                            "../confirmation-devis-complemantary",
                                                                                                                                                                        ]
                                                                                                                                                                    );
                                                                                                                                                                }
                                                                                                                                                            );
                                                                                                                                                    }
                                                                                                                                                );
                                                                                                                                        }
                                                                                                                                    );
                                                                                                                            }
                                                                                                                        }
                                                                                                                    );
                                                                                                            }
                                                                                                        );
                                                                                                }
                                                                                            );
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            } else {
                                                                if (
                                                                    this
                                                                        .testConfirmDevis ==
                                                                    true
                                                                ) {
                                                                    this.sinisterPec.stepId = 100;
                                                                } else {
                                                                    this.sinisterPec.stepId = 106;
                                                                }
                                                                this.sinisterPec.quotation.fromSignature = true;
                                                                this.sinisterPecService
                                                                    .updatePecForQuotation(
                                                                        this
                                                                            .sinisterPec,
                                                                        true
                                                                    )
                                                                    .subscribe(
                                                                        (
                                                                            res
                                                                        ) => {
                                                                            this.sinisterPec = res;
                                                                            this.apecService
                                                                                .generateAccordPrisCharge(
                                                                                    this
                                                                                        .sinisterPec,
                                                                                    this
                                                                                        .quotation
                                                                                        .id,
                                                                                    true,
                                                                                    6,
                                                                                    this
                                                                                        .labelAccordNormal
                                                                                )
                                                                                .subscribe(
                                                                                    (
                                                                                        resPdf
                                                                                    ) => {
                                                                                        if (
                                                                                            this
                                                                                                .testConfirmDevis ==
                                                                                            true
                                                                                        ) {
                                                                                            this.apecService
                                                                                                .findByQuotation(
                                                                                                    this
                                                                                                        .quotation
                                                                                                        .id
                                                                                                )
                                                                                                .subscribe(
                                                                                                    (
                                                                                                        apec: Apec
                                                                                                    ) => {
                                                                                                        apec.etat = 0;
                                                                                                        apec.testDevis = false;
                                                                                                        this.apecService
                                                                                                            .update(
                                                                                                                apec
                                                                                                            )
                                                                                                            .subscribe(
                                                                                                                (
                                                                                                                    res: Apec
                                                                                                                ) => {
                                                                                                                    this.apec = res;
                                                                                                                    this.sendNotifConfirmationDevisComplementaire(
                                                                                                                        "devisComplementaireConfirmeApprouve",
                                                                                                                        this
                                                                                                                            .apec
                                                                                                                            .id
                                                                                                                    );
                                                                                                                    this.router.navigate(
                                                                                                                        [
                                                                                                                            "../confirmation-devis-complemantary",
                                                                                                                        ]
                                                                                                                    );
                                                                                                                }
                                                                                                            );
                                                                                                    }
                                                                                                );
                                                                                        } else {
                                                                                            this.apecService
                                                                                                .findByQuotation(
                                                                                                    this
                                                                                                        .quotation
                                                                                                        .id
                                                                                                )
                                                                                                .subscribe(
                                                                                                    (
                                                                                                        apec: Apec
                                                                                                    ) => {
                                                                                                        apec.etat = 6;
                                                                                                        apec.testDevis = false;
                                                                                                        this.apecService
                                                                                                            .update(
                                                                                                                apec
                                                                                                            )
                                                                                                            .subscribe(
                                                                                                                (
                                                                                                                    res: Apec
                                                                                                                ) => {
                                                                                                                    this.apec = res;
                                                                                                                    this.sendNotifConfirmationDevisComplementaire(
                                                                                                                        "devisComplementaireConfirmeValid",
                                                                                                                        this
                                                                                                                            .apec
                                                                                                                            .id
                                                                                                                    );
                                                                                                                    this.router.navigate(
                                                                                                                        [
                                                                                                                            "../confirmation-devis-complemantary",
                                                                                                                        ]
                                                                                                                    );
                                                                                                                }
                                                                                                            );
                                                                                                    }
                                                                                                );
                                                                                        }
                                                                                    }
                                                                                );
                                                                        }
                                                                    );
                                                            }
                                                        });
                                                }
                                            }
                                            if (
                                                ((this.apec.id == null ||
                                                    this.apec.id ==
                                                        undefined) &&
                                                    this.additionalQuote) ||
                                                !this.additionalQuote
                                            ) {
                                                this.quotation.confirmationDevisDate = this.dateAsYYYYMMDDHHNNSSLDT(
                                                    new Date()
                                                );
                                                this.sinisterPec.oldStepModifSinPec = this.sinisterPec.stepId;
                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                this.sinisterPec.stepId =
                                                    PrestationPecStep.UPDATE_QUOTE;
                                                this.sinisterPecService
                                                    .updatePecForQuotation(
                                                        this.sinisterPec,
                                                        true
                                                    )
                                                    .subscribe((res) => {
                                                        this.sinisterPec = res;

                                                        if (
                                                            this
                                                                .RevueValidationDevis
                                                        ) {
                                                            this.router.navigate(
                                                                [
                                                                    "../revue-validation-devis",
                                                                ]
                                                            );
                                                        } else if (
                                                            this.additionalQuote
                                                        ) {
                                                            this.sendNotif(
                                                                this.sinisterPec
                                                                    .reparateurId,
                                                                "Devis Confirmé par le réparateur"
                                                            );
                                                            this.router.navigate(
                                                                [
                                                                    "../confirmation-devis",
                                                                ]
                                                            );
                                                        } else {
                                                            this.sendNotif(
                                                                this.sinisterPec
                                                                    .reparateurId,
                                                                "Devis Confirmé par le réparateur"
                                                            );
                                                            this.router.navigate(
                                                                [
                                                                    "../confirmation-devis",
                                                                ]
                                                            );
                                                        }
                                                    });
                                            }
                                        });
                                }
                            });
                        }
                    }
                })
                .catch(() =>
                    console.log(
                        "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                    )
                );
        } else if (this.quotation.confirmationDecisionQuote == false) {
            this.confirmationDialogService
                .confirm(
                    "Confirmation",
                    "Êtes-vous sûrs de ne pas Confirmer le devis  ?",
                    "Oui",
                    "Non",
                    "lg"
                )
                .then((confirmed) => {
                    console.log("User confirmed:", confirmed);
                    if (confirmed) {
                        if (this.oneClickForButton29) {
                            this.oneClickForButton29 = false;
                            if (
                                this.responsableControleTechnique.profileId ==
                                21
                            ) {
                                this.sinisterPec.respControleTechnique = this.responsableControleTechnique.id;
                            }
                            this.sendDateReceptionVehicule();
                            this.quotation.statusId =
                                QuoteStatus.DEVIS_NON_CONFIRME_PAR_LE_REPARATEUR;
                            this.route.params.subscribe((params) => {
                                for (
                                    let i = 0;
                                    i < this.detailsPiecesMO.length;
                                    i++
                                ) {
                                    this.detailsPiecesMO[i].isMo = true;
                                }
                                let listPieces = this.detailsPiecesMO.concat(
                                    this.detailsPieces,
                                    this.detailsPiecesFourniture,
                                    this.detailsPiecesIngredient
                                );
                                this.quotation.listPieces = listPieces;
                                if (this.RevueValidationDevis) {
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId =
                                        PrestationPecStep.UPDATE_QUOTE;
                                }
                                this.sinisterPec.quotation = this.quotation;
                                let quoteId = 0;
                                if (
                                    this.sinisterPec.preliminaryReport ==
                                        true &&
                                    this.confirmationDevisComplementaire ==
                                        true &&
                                    this.quotationId !== 0
                                ) {
                                    quoteId = this.quotationId;
                                } else {
                                    quoteId = this.quotation.id;
                                }
                                this.apecService
                                    .findByQuotation(quoteId)
                                    .subscribe((apec: Apec) => {
                                        this.apec = apec;
                                        if (
                                            this.apec.id != null &&
                                            this.apec.id !== undefined &&
                                            this.additionalQuote
                                        ) {
                                            if (
                                                this.apec.etat == 17 &&
                                                this.apec.isComplementaire
                                            ) {
                                                // non confirmation devis complementaire aprés imprim accord
                                                this.apec.etat = 10;
                                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                this.sinisterPec.stepId = 110;
                                                this.quotation.isConfirme = false;
                                                this.quotation.fromSignature = true;
                                                if (
                                                    this.sinisterPec
                                                        .preliminaryReport ==
                                                        true &&
                                                    this
                                                        .confirmationDevisComplementaire ==
                                                        true
                                                ) {
                                                    this.sinisterPec.modeId = 1;
                                                    this.sinisterPec.preliminaryReport = null;
                                                    this.quotationCP.isConfirme = false;
                                                    this.quotationCP.fromSignature = true;
                                                    this.quotationCP.listPieces = listPieces;
                                                    this.sinisterPec.quotation = this.quotationCP;
                                                } else {
                                                    this.sinisterPec.quotation = this.quotation;
                                                }

                                                this.apec.isConfirme = false;
                                                this.apecService
                                                    .update(this.apec)
                                                    .subscribe((apec: Apec) => {
                                                        this.apec = apec;
                                                        this.sinisterPec.oldStepNw = 1;
                                                        this.sinisterPecService
                                                            .updatePecForQuotation(
                                                                this
                                                                    .sinisterPec,
                                                                true
                                                            )
                                                            .subscribe(
                                                                (res) => {
                                                                    this.sinisterPec = res;
                                                                    if (
                                                                        this
                                                                            .RevueValidationDevis
                                                                    ) {
                                                                        //validation
                                                                        this.sendNotifValidationDevisNonConfirmer(
                                                                            "validationDevisNonConfirmer"
                                                                        );
                                                                        this.router.navigate(
                                                                            [
                                                                                "../revue-validation-devis",
                                                                            ]
                                                                        );
                                                                    } else if (
                                                                        this
                                                                            .additionalQuote
                                                                    ) {
                                                                        this.sendNotifDevisComplementaire(
                                                                            "DevisComplemantaryNonConforme"
                                                                        );
                                                                        this.router.navigate(
                                                                            [
                                                                                "../confirmation-devis-complemantary",
                                                                            ]
                                                                        );
                                                                    } else {
                                                                        this.sendNotif(
                                                                            this
                                                                                .sinisterPec
                                                                                .reparateurId,
                                                                            "Devis non confirmé par le réparateur"
                                                                        );
                                                                        this.router.navigate(
                                                                            [
                                                                                "../confirmation-devis",
                                                                            ]
                                                                        );
                                                                    }
                                                                }
                                                            );
                                                    });
                                            }
                                        }

                                        if (
                                            ((this.apec.id == null ||
                                                this.apec.id == undefined) &&
                                                this.additionalQuote) ||
                                            !this.additionalQuote
                                        ) {
                                            if (
                                                this.sinisterPec
                                                    .preliminaryReport == true
                                            ) {
                                                this.sinisterPec.modeId = 1;
                                                this.sinisterPec.preliminaryReport = null;
                                            }
                                            this.sinisterPecService
                                                .update(this.sinisterPec)
                                                .subscribe((res) => {
                                                    this.sinisterPec = res;
                                                    if (
                                                        this
                                                            .RevueValidationDevis
                                                    ) {
                                                        //validation
                                                        this.sendNotifValidationDevisNonConfirmer(
                                                            "validationDevisNonConfirmer"
                                                        );
                                                        this.router.navigate([
                                                            "../revue-validation-devis",
                                                        ]);
                                                    } else if (
                                                        this.additionalQuote
                                                    ) {
                                                        this.sendNotifDevisComplementaire(
                                                            "DevisComplemantaryNonConforme"
                                                        );
                                                        this.router.navigate([
                                                            "../confirmation-devis",
                                                        ]);
                                                    } else {
                                                        this.sendNotif(
                                                            this.sinisterPec
                                                                .reparateurId,
                                                            "Devis non confirmé par le réparateur"
                                                        );
                                                        this.router.navigate([
                                                            "../confirmation-devis",
                                                        ]);
                                                    }
                                                });
                                        }
                                    });
                            });
                        }
                    }
                })
                .catch(() =>
                    console.log(
                        "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                    )
                );
        }
    }

    sendNotifValidationDevisNonConfirmer(typeNotif) {
        if (this.oneClickForButton11) {
            this.oneClickForButton11 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        this.userExtraService
                            .finUsersByPersonProfil(
                                this.sinisterPec.reparateurId,
                                28
                            )
                            .subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                let settingJson = {
                                    typenotification: typeNotif,
                                    idReparateur: this.sinisterPec.reparateurId,
                                    refSinister: this.sinisterPec.reference,
                                    assure: this.assure.nom,
                                    sinisterId: this.sinister.id,
                                    sinisterPecId: this.sinisterPec.id,
                                    expertId: this.sinisterPec.expertId,
                                    quotationId: this.sinisterPec
                                        .primaryQuotationId,
                                    stepPecId: this.sinisterPec.stepId,
                                };
                                this.userExNotifAgency.forEach((element) => {
                                    this.sendNotifGlobal(
                                        50,
                                        element.userId,
                                        usr.id,
                                        settingJson
                                    );
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.agencyId,
                                        24
                                    )
                                    .subscribe((userExNotifAgency) => {
                                        this.userExNotifAgency =
                                            userExNotifAgency.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifAgency.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 50;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 50;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.notificationAlerteService
                                                    .create(this.listNotifUser)
                                                    .subscribe((res) => {
                                                        this.ws.send(
                                                            "/app/hello",
                                                            {},
                                                            JSON.stringify(
                                                                settingJson
                                                            )
                                                        );
                                                    });
                                            });
                                    });
                            });
                    });
            });
        }
    }

    sendNotifValidationDevisConfirmer(typeNotif) {
        if (this.oneClickForButton30) {
            this.oneClickForButton30 = false;
            this.apecService
                .findByQuotation(this.sinisterPec.primaryQuotationId)
                .subscribe((res: Apec) => {
                    this.apecNotif = res;
                    if (this.sinisterPec.expertId) {
                        console.log("1" + this.sinisterPec.primaryQuotationId);

                        console.log("1" + this.apecNotif.id);

                        let settingJson = {
                            typenotification: typeNotif,
                            idReparateur: this.sinisterPec.reparateurId,
                            refSinister: this.sinisterPec.reference,
                            assure: this.assure.nom,
                            sinisterId: this.sinister.id,
                            sinisterPecId: this.sinisterPec.id,
                            expertId: this.sinisterPec.expertId,
                            quotationId: this.sinisterPec.primaryQuotationId,
                            idApec: this.apecNotif.id,
                            stepPecId: this.sinisterPec.stepId,
                        };
                        this.principal.identity().then((account) => {
                            this.currentAccount = account;
                            this.userExtraService
                                .finPersonneByUser(this.currentAccount.id)
                                .subscribe((usr: UserExtra) => {
                                    if (
                                        usr.id !== this.sinisterPec.assignedToId
                                    ) {
                                        this.sendNotifGlobal(
                                            51,
                                            this.sinisterPec.assignedToId,
                                            usr.id,
                                            settingJson
                                        );
                                    }
                                    this.userExtraService
                                        .finPersonneByUser(
                                            this.sinisterPec.assignedToId
                                        )
                                        .subscribe((usrAssigned: UserExtra) => {
                                            this.sendNotifGlobal(
                                                51,
                                                usrAssigned.userBossId,
                                                usr.id,
                                                settingJson
                                            );
                                            this.userExtraService
                                                .finPersonneByUser(
                                                    usrAssigned.userBossId
                                                )
                                                .subscribe(
                                                    (usrBoss: UserExtra) => {
                                                        this.sendNotifGlobal(
                                                            51,
                                                            usrBoss.userBossId,
                                                            usr.id,
                                                            settingJson
                                                        );
                                                        this.userExtraService
                                                            .finUsersByPersonProfil(
                                                                this.sinisterPec
                                                                    .reparateurId,
                                                                28
                                                            )
                                                            .subscribe(
                                                                (
                                                                    userExNotifReparateur
                                                                ) => {
                                                                    this.userExNotifReparateur =
                                                                        userExNotifReparateur.json;
                                                                    this.userExNotifReparateur.forEach(
                                                                        (
                                                                            element
                                                                        ) => {
                                                                            this.sendNotifGlobal(
                                                                                51,
                                                                                element.userId,
                                                                                usr.id,
                                                                                settingJson
                                                                            );
                                                                        }
                                                                    );
                                                                    this.userExtraService
                                                                        .finUsersByPersonProfil(
                                                                            this
                                                                                .sinisterPec
                                                                                .agencyId,
                                                                            24
                                                                        )
                                                                        .subscribe(
                                                                            (
                                                                                userExNotifAgency
                                                                            ) => {
                                                                                this.userExNotifAgency =
                                                                                    userExNotifAgency.json;
                                                                                console.log(
                                                                                    "4-------------------------------------------------------------------"
                                                                                );
                                                                                this.userExNotifAgency.forEach(
                                                                                    (
                                                                                        element
                                                                                    ) => {
                                                                                        this.notifUser = new NotifAlertUser();
                                                                                        this.notification.id = 51;
                                                                                        this.notifUser.source =
                                                                                            usr.id;
                                                                                        this.notifUser.destination =
                                                                                            element.userId;
                                                                                        this.notifUser.notification = this.notification;
                                                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                                                        this.notifUser.entityName =
                                                                                            "SinisterPec";
                                                                                        this.notifUser.settings = JSON.stringify(
                                                                                            settingJson
                                                                                        );
                                                                                        this.listNotifUser.push(
                                                                                            this
                                                                                                .notifUser
                                                                                        );
                                                                                    }
                                                                                );
                                                                                this.userExtraService
                                                                                    .finUsersByPersonProfil(
                                                                                        this
                                                                                            .sinisterPec
                                                                                            .partnerId,
                                                                                        25
                                                                                    )
                                                                                    .subscribe(
                                                                                        (
                                                                                            userExNotifPartner
                                                                                        ) => {
                                                                                            this.userExNotifPartner =
                                                                                                userExNotifPartner.json;
                                                                                            this.userExNotifPartner.forEach(
                                                                                                (
                                                                                                    element
                                                                                                ) => {
                                                                                                    this.notifUser = new NotifAlertUser();
                                                                                                    this.notification.id = 51;
                                                                                                    this.notifUser.source =
                                                                                                        usr.id;
                                                                                                    this.notifUser.destination =
                                                                                                        element.userId;
                                                                                                    this.notifUser.notification = this.notification;
                                                                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                                                                    this.notifUser.entityName =
                                                                                                        "SinisterPec";
                                                                                                    this.notifUser.settings = JSON.stringify(
                                                                                                        settingJson
                                                                                                    );
                                                                                                    this.listNotifUser.push(
                                                                                                        this
                                                                                                            .notifUser
                                                                                                    );
                                                                                                }
                                                                                            );
                                                                                            this.userExtraService
                                                                                                .finUsersByPersonProfil(
                                                                                                    this
                                                                                                        .sinisterPec
                                                                                                        .expertId,
                                                                                                    27
                                                                                                )
                                                                                                .subscribe(
                                                                                                    (
                                                                                                        userExNotifExpert
                                                                                                    ) => {
                                                                                                        this.userExNotifExpert =
                                                                                                            userExNotifExpert.json;
                                                                                                        this.userExNotifExpert.forEach(
                                                                                                            (
                                                                                                                element
                                                                                                            ) => {
                                                                                                                this.notifUser = new NotifAlertUser();
                                                                                                                this.notification.id = 51;
                                                                                                                this.notifUser.source =
                                                                                                                    usr.id;
                                                                                                                this.notifUser.destination =
                                                                                                                    element.userId;
                                                                                                                this.notifUser.notification = this.notification;
                                                                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                                                                this.notifUser.entityName =
                                                                                                                    "SinisterPec";
                                                                                                                this.notifUser.settings = JSON.stringify(
                                                                                                                    settingJson
                                                                                                                );
                                                                                                                this.listNotifUser.push(
                                                                                                                    this
                                                                                                                        .notifUser
                                                                                                                );
                                                                                                            }
                                                                                                        );
                                                                                                        this.userExtraService
                                                                                                            .findByProfil(
                                                                                                                21
                                                                                                            )
                                                                                                            .subscribe(
                                                                                                                (
                                                                                                                    userExNotifReparateur: UserExtra[]
                                                                                                                ) => {
                                                                                                                    this.userExNotifReparateur = userExNotifReparateur;
                                                                                                                    this.userExNotifReparateur.forEach(
                                                                                                                        (
                                                                                                                            element
                                                                                                                        ) => {
                                                                                                                            this.sendNotifGlobal(
                                                                                                                                51,
                                                                                                                                element.userId,
                                                                                                                                usr.id,
                                                                                                                                settingJson
                                                                                                                            );
                                                                                                                        }
                                                                                                                    );
                                                                                                                    this.notificationAlerteService
                                                                                                                        .create(
                                                                                                                            this
                                                                                                                                .listNotifUser
                                                                                                                        )
                                                                                                                        .subscribe(
                                                                                                                            (
                                                                                                                                res
                                                                                                                            ) => {
                                                                                                                                this.ws.send(
                                                                                                                                    "/app/hello",
                                                                                                                                    {},
                                                                                                                                    JSON.stringify(
                                                                                                                                        settingJson
                                                                                                                                    )
                                                                                                                                );
                                                                                                                            }
                                                                                                                        );
                                                                                                                }
                                                                                                            );
                                                                                                    }
                                                                                                );
                                                                                        }
                                                                                    );
                                                                            }
                                                                        );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                });
                        });
                    } else {
                        console.log("2");

                        this.principal.identity().then((account) => {
                            this.currentAccount = account;
                            this.userExtraService
                                .finPersonneByUser(this.currentAccount.id)
                                .subscribe((usr: UserExtra) => {
                                    let settingJson = {
                                        typenotification: typeNotif,
                                        idReparateur: this.sinisterPec
                                            .reparateurId,
                                        refSinister: this.sinisterPec.reference,
                                        assure: this.assure.nom,
                                        sinisterId: this.sinister.id,
                                        sinisterPecId: this.sinisterPec.id,
                                        expertId: this.sinisterPec.expertId,
                                        quotationId: this.sinisterPec
                                            .primaryQuotationId,
                                        stepPecId: this.sinisterPec.stepId,
                                    };
                                    this.sendNotifGlobal(
                                        25,
                                        this.sinisterPec.assignedToId,
                                        usr.id,
                                        settingJson
                                    );
                                    this.userExtraService
                                        .finUsersByPersonProfil(
                                            this.sinisterPec.agencyId,
                                            24
                                        )
                                        .subscribe((userExNotifAgency) => {
                                            this.userExNotifAgency =
                                                userExNotifAgency.json;
                                            console.log(
                                                "4-------------------------------------------------------------------"
                                            );
                                            this.userExNotifAgency.forEach(
                                                (element) => {
                                                    this.notifUser = new NotifAlertUser();
                                                    this.notification.id = 25;
                                                    this.notifUser.source =
                                                        usr.id;
                                                    this.notifUser.destination =
                                                        element.userId;
                                                    this.notifUser.notification = this.notification;
                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                    this.notifUser.entityName =
                                                        "SinisterPec";
                                                    this.notifUser.settings = JSON.stringify(
                                                        settingJson
                                                    );
                                                    this.listNotifUser.push(
                                                        this.notifUser
                                                    );
                                                }
                                            );
                                            this.userExtraService
                                                .finUsersByPersonProfil(
                                                    this.sinisterPec.partnerId,
                                                    25
                                                )
                                                .subscribe(
                                                    (userExNotifPartner) => {
                                                        this.userExNotifPartner =
                                                            userExNotifPartner.json;
                                                        this.userExNotifPartner.forEach(
                                                            (element) => {
                                                                this.notifUser = new NotifAlertUser();
                                                                this.notification.id = 25;
                                                                this.notifUser.source =
                                                                    usr.id;
                                                                this.notifUser.destination =
                                                                    element.userId;
                                                                this.notifUser.entityId = this.sinisterPec.id;
                                                                this.notifUser.entityName =
                                                                    "SinisterPec";
                                                                this.notifUser.notification = this.notification;
                                                                this.notifUser.settings = JSON.stringify(
                                                                    settingJson
                                                                );
                                                                this.listNotifUser.push(
                                                                    this
                                                                        .notifUser
                                                                );
                                                            }
                                                        );
                                                        this.notificationAlerteService
                                                            .create(
                                                                this
                                                                    .listNotifUser
                                                            )
                                                            .subscribe(
                                                                (res) => {
                                                                    this.ws.send(
                                                                        "/app/hello",
                                                                        {},
                                                                        JSON.stringify(
                                                                            settingJson
                                                                        )
                                                                    );
                                                                }
                                                            );
                                                    }
                                                );
                                        });
                                });
                        });
                    }
                });
        }
    }

    sendNotifDevisComplementaire(typeNotif) {
        if (this.oneClickForButton31) {
            this.oneClickForButton31 = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((usr: UserExtra) => {
                        let settingJson = {
                            typenotification: typeNotif,
                            idReparateur: this.sinisterPec.reparateurId,
                            refSinister: this.sinisterPec.reference,
                            assure: this.assure.nom,
                            sinisterId: this.sinister.id,
                            sinisterPecId: this.sinisterPec.id,
                            expertId: this.sinisterPec.expertId,
                            stepPecId: this.sinisterPec.stepId,
                        };
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 47;
                        this.notifUser.source = usr.id;
                        this.notifUser.destination = this.sinisterPec.assignedToId;
                        this.notifUser.notification = this.notification;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.settings = JSON.stringify(settingJson);
                        this.listNotifUser.push(this.notifUser);
                        this.userExtraService
                            .finUsersByPersonProfil(
                                this.sinisterPec.agencyId,
                                24
                            )
                            .subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json;
                                console.log(
                                    "4-------------------------------------------------------------------"
                                );
                                this.userExNotifAgency.forEach((element) => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 47;
                                    this.notifUser.source = usr.id;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify(
                                        settingJson
                                    );
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService
                                    .finUsersByPersonProfil(
                                        this.sinisterPec.reparateurId,
                                        28
                                    )
                                    .subscribe((userExNotifExpert) => {
                                        this.userExNotifExpert =
                                            userExNotifExpert.json;
                                        console.log(
                                            "4-------------------------------------------------------------------"
                                        );
                                        this.userExNotifExpert.forEach(
                                            (element) => {
                                                this.notifUser = new NotifAlertUser();
                                                this.notification.id = 47;
                                                this.notifUser.source = usr.id;
                                                this.notifUser.destination =
                                                    element.userId;
                                                this.notifUser.entityId = this.sinisterPec.id;
                                                this.notifUser.entityName =
                                                    "SinisterPec";
                                                this.notifUser.notification = this.notification;
                                                this.notifUser.settings = JSON.stringify(
                                                    settingJson
                                                );
                                                this.listNotifUser.push(
                                                    this.notifUser
                                                );
                                            }
                                        );
                                        this.userExtraService
                                            .finUsersByPersonProfil(
                                                this.sinisterPec.partnerId,
                                                25
                                            )
                                            .subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner =
                                                    userExNotifPartner.json;
                                                this.userExNotifPartner.forEach(
                                                    (element) => {
                                                        this.notifUser = new NotifAlertUser();
                                                        this.notification.id = 47;
                                                        this.notifUser.source =
                                                            usr.id;
                                                        this.notifUser.destination =
                                                            element.userId;
                                                        this.notifUser.entityId = this.sinisterPec.id;
                                                        this.notifUser.entityName =
                                                            "SinisterPec";
                                                        this.notifUser.notification = this.notification;
                                                        this.notifUser.settings = JSON.stringify(
                                                            settingJson
                                                        );
                                                        this.listNotifUser.push(
                                                            this.notifUser
                                                        );
                                                    }
                                                );
                                                this.notificationAlerteService
                                                    .create(this.listNotifUser)
                                                    .subscribe((res) => {
                                                        this.ws.send(
                                                            "/app/hello",
                                                            {},
                                                            JSON.stringify({
                                                                typenotification: typeNotif,
                                                                idReparateur: this
                                                                    .sinisterPec
                                                                    .reparateurId,
                                                                refSinister: this
                                                                    .sinisterPec
                                                                    .reference,
                                                                assure: this
                                                                    .assure.nom,
                                                                sinisterId: this
                                                                    .sinister
                                                                    .id,
                                                                sinisterPecId: this
                                                                    .sinisterPec
                                                                    .id,
                                                                expertId: this
                                                                    .sinisterPec
                                                                    .expertId,
                                                                stepPecId: this
                                                                    .sinisterPec
                                                                    .stepId,
                                                            })
                                                        );
                                                    });
                                            });
                                    });
                            });
                    });
            });
        }
    }
    findInsured() {
        // find insured
        this.insuredService
            .find(this.vehicule.insuredId)
            .subscribe((insured: Assure) => {
                this.insured = insured;
                this.sinister.insuredId = insured.id;
                if (this.insured.company) {
                    this.insured.fullName = this.insured.raisonSociale;
                } else if (
                    this.insured.prenom !== null &&
                    this.insured.prenom !== undefined &&
                    this.insured.nom !== null &&
                    this.insured.nom !== undefined
                ) {
                    this.insured.fullName =
                        this.insured.prenom + " " + this.insured.nom;
                } else if (
                    this.insured.prenom !== null &&
                    this.insured.prenom !== undefined
                ) {
                    this.insured.fullName = this.insured.prenom;
                } else {
                    this.insured.fullName = this.insured.nom;
                }
            });
    }

    enregistre() {
        // open last page
        this.useGtEstimate = false;
        this.isSaving = false;
        //save estimate in BD

        console.log(
            "icii estimation id  when enregistreeeeeeeee" + this.estimation.id
        );
        this.sinisterPecService
            .saveEstimation(this.estimation.id, this.sinisterPec.id)
            .subscribe((resE) => {
                this.quotationService
                    .find(this.estimation.id)
                    .subscribe((res) => {
                        // Find devid By ID
                        console.log(
                            "iciiiiii log id quotaytion non modif " +
                                this.estimation.id
                        );
                        this.showBlockGTRapport = true;
                        this.quotation = res;
                        this.quotation.stampDuty = this.activeStamp.amount;
                        this.loadAllDetailsMo();
                        this.loadAllIngredient();
                        this.loadAllRechange();
                        this.loadAllFourniture();

                        this.status = this.quotation.statusId; // Get etat de devis selectionné
                        // this.evtManager.destroy()
                    });
            });
    }
    onPieceFileChange1(fileInput: any) {
        this.updatePieceJointe1 = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");
        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.pieceAttachment1.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.pieceAttachment1.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.pieceFiles1 = fileInput.target.files[0];
        this.piecePreview1 = true;
    }

    receiveMessage(event) {
        this.viewGaDigits = true;
        console.log("test gtEstimate New");
        var RegexTargetURI = /gtestimate.com/i;
        if (RegexTargetURI.test(event.origin)) {
            //Close iFrame
            // alert("Vous avez cloture GT estimate ");
            var iframe = document.getElementById("myFrame");
            //iframe.parentNode.removeChild(iframe);
            document
                .getElementById("myFrame")
                .removeEventListener("message", this.receiveMessage.bind(this));
            this.enregistre();
        } else {
        }
    }

    //listen to the event during the opening GTEstimate
    addListenerToIframe(e) {
        this.evtManager.addGlobalEventListener(
            "window",
            "message",
            this.receiveMessage.bind(this)
        );
    }
    // get URL GT estimate
    GTEstimate() {
        this.viewGaDigits = false;
        this.useGt = true;

        this.route.params.subscribe((params) => {
            if (
                this.quotation.id != null &&
                this.quotation.id != undefined &&
                this.sinisterPec.quoteViaGt !== true
            ) {
                // get URL to update
                this.sinisterPecService
                    .getEstimateUpdateUrl(
                        this.quotation.id,
                        this.sinisterPec.id
                    )
                    .subscribe((res: Response) => {
                        if ((this.estimation = res)) {
                            console.log(
                                "icii estimation id update " +
                                    this.estimation.id
                            );
                            this.useGtEstimate = true;
                            this.isSaving = true;
                            this.gaestimateUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
                                this.estimation.url.toString()
                            );
                            this.gaestimateLink = this.estimation.url;
                            this.gtestimateId = this.estimation.id;
                            this.sinisterPec.quoteViaGt = null;
                        } else {
                            alert(
                                " l'estimation n'existe pas sur GT estimate "
                            );
                        }
                    });
            } else {
                // get URL to  create
                this.sinisterPecService
                    .getEstimateCreationUrl(this.sinisterPec.id)
                    .subscribe((res: Response) => {
                        if ((this.estimation = res)) {
                            console.log(
                                "icii estimation id " + this.estimation.id
                            );
                            this.useGtEstimate = true;
                            this.isSaving = true;
                            this.gaestimateUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
                                this.estimation.url.toString()
                            );
                            console.log(this.gaestimateUrl);
                            this.gaestimateLink = this.estimation.url;
                            this.gtestimateId = this.estimation.id;
                            this.sinisterPec.quoteViaGt = null;
                        }
                    });
            }
        });
    }

    // send date reception vehicule
    sendDateReceptionVehicule() {
        if (
            this.sinisterPec.vehicleReceiptDate &&
            !this.sinisterPec.receptionVehicule
        ) {
            this.sinisterPec.vehicleReceiptDate.hour = "00";
            this.sinisterPec.vehicleReceiptDate.minute = "00";
            this.sinisterPec.vehicleReceiptDate.second = 0;
            this.sinisterPec.vehicleReceiptDate = this.owerDateUtils.convertDateTimeToServer(
                this.sinisterPec.vehicleReceiptDate,
                undefined
            );
        } else {
            const dateUp = new Date(this.myDate);
            this.sinisterPec.vehicleReceiptDate = this.dateAsYYYYMMDDHHNNSSLDT(
                dateUp
            );
        }
    }
    // get date reception vehicule
    getDateReceptionVehicule() {
        if (
            this.sinisterPec.vehicleReceiptDate &&
            !this.sinisterPec.receptionVehicule
        ) {
            const date = new Date(this.sinisterPec.vehicleReceiptDate);
            this.sinisterPec.vehicleReceiptDate = {
                year: date.getFullYear(),
                month: date.getMonth() + 1,
                day: date.getDate(),
            };
        } else {
            this.myDate = this.sinisterPec.vehicleReceiptDate;
        }
    }
    // open the form to add a piece
    ajoutNouvelpieceJointe() {
        this.ajoutNouvelpieceform = true;
        this.ajoutNouvelpiece = false;
        this.enableSaveSoumettre = false;
        if (this.piecesJointes.length == 6 || this.piecesJointes.length > 6) {
            this.ajoutNouvelpieceform = false;
        }
    }

    ajoutNouvelpieceJointeExpertise() {
        this.ajoutNouvelpieceformExpertise = true;
        this.ajoutNouvelpieceExpertise = false;
        this.enableSaveExpertise = false;
    }
    // open the form to add a piece
    downloadCarteGriseFile() {
        if (this.carteGriseFiles) {
            saveAs(this.carteGriseFiles);
        }
    }

    downloadGTFile() {
        if (this.gtFiles) {
            saveAs(this.gtFiles);
        }
    }

    downloadCompteurFile() {
        if (this.compteurFiles) {
            saveAs(this.compteurFiles);
        }
    }

    downloadImmatriculationFile() {
        if (this.immatriculationFiles) {
            saveAs(this.immatriculationFiles);
        }
    }

    downloadNSerieFile() {
        if (this.nSerieFiles) {
            saveAs(this.nSerieFiles);
        }
    }

    downloadFinitionFile() {
        if (this.finitionFiles) {
            saveAs(this.finitionFiles);
        }
    }

    downloadFaceArriereGaucheFile() {
        if (this.faceArriereGaucheFiles) {
            saveAs(this.faceArriereGaucheFiles);
        }
    }

    downloadFaceArriereDroitFile() {
        if (this.faceArriereDroitFiles) {
            saveAs(this.faceArriereDroitFiles);
        }
    }

    downloadFaceAvantGaucheFile() {
        if (this.faceAvantGaucheFiles) {
            saveAs(this.faceAvantGaucheFiles);
        }
    }

    downloadfaceAvantDroitFile() {
        if (this.faceAvantDroitFiles) {
            saveAs(this.faceAvantDroitFiles);
        }
    }

    downloadPointChoc1File() {
        if (this.pointChoc1Files) {
            saveAs(this.pointChoc1Files);
        }
    }

    downloadPointChoc2File() {
        if (this.pointChoc2Files) {
            saveAs(this.pointChoc2Files);
        }
    }

    downloadPointChoc3File() {
        if (this.pointChoc3Files) {
            saveAs(this.pointChoc3Files);
        }
    }

    downloadPointChoc4File() {
        if (this.pointChoc4Files) {
            saveAs(this.pointChoc4Files);
        }
    }

    onCarteGriseFileChange(fileInput: any) {
        this.updateCarteGrise = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");
        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.carteGriseAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.carteGriseAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.carteGriseFiles = fileInput.target.files[0];
        this.carteGrisePreview = true;
        console.log(this.carteGriseFiles);
    }

    onGTFileChange(fileInput: any) {
        this.updateGT = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.gtAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.gtAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.gtFiles = fileInput.target.files[0];
        this.gtPreview = true;
        console.log(this.gtFiles);
    }

    onCompteurFileChange(fileInput: any) {
        this.updateCompteur = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.compteurAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.compteurAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.compteurFiles = fileInput.target.files[0];
        this.compteurPreview = true;
        console.log(this.compteurFiles);
    }

    onImmatriculationFileChange(fileInput: any) {
        this.updateImmatriculation = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.immatriculationAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.immatriculationAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.immatriculationFiles = fileInput.target.files[0];
        this.immatriculationPreview = true;
        console.log(this.immatriculationFiles);
    }

    onNSerieFileChange(fileInput: any) {
        this.updateNSerie = true;

        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.nSerieAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.nSerieAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.nSerieFiles = fileInput.target.files[0];
        this.nSeriePreview = true;
        console.log(this.nSerieFiles);
    }

    onFinitionFileChange(fileInput: any) {
        this.updateFinition = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.finitionAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.finitionAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.finitionFiles = fileInput.target.files[0];
        this.finitionPreview = true;
        console.log(this.finitionFiles);
    }

    onFaceArriereGaucheFileChange(fileInput: any) {
        this.updateFaceArriereGauche = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.faceArriereGaucheAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.faceArriereGaucheAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.faceArriereGaucheFiles = fileInput.target.files[0];
        this.faceArriereGauchePreview = true;
        console.log(this.faceArriereGaucheFiles);
    }

    onFaceArriereDroitFileChange(fileInput: any) {
        this.updateFaceArriereDroit = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.faceArriereDroitAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.faceArriereDroitAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.faceArriereDroitFiles = fileInput.target.files[0];
        this.faceArriereDroitPreview = true;
        console.log(this.faceArriereDroitFiles);
    }

    onFaceAvantGaucheFileChange(fileInput: any) {
        this.updateFaceAvantGauche = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");
        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.faceAvantGaucheAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.faceAvantGaucheAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.faceAvantGaucheFiles = fileInput.target.files[0];
        this.faceAvantGauchePreview = true;
        console.log(this.faceAvantGaucheFiles);
    }

    onPointChoc2FileChange(fileInput: any) {
        this.updatePointChoc2 = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");
        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.pointChoc2Attachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.pointChoc2Attachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.pointChoc2Files = fileInput.target.files[0];
        this.pointChoc2Preview = true;
        console.log(this.pointChoc2Files);
    }

    onPointChoc3FileChange(fileInput: any) {
        this.updatePointChoc3 = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");
        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.pointChoc3Attachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.pointChoc3Attachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.pointChoc3Files = fileInput.target.files[0];
        this.pointChoc3Preview = true;
        console.log(this.pointChoc3Files);
    }

    onPointChoc4FileChange(fileInput: any) {
        this.updatePointChoc4 = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");
        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.pointChoc4Attachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.pointChoc4Attachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.pointChoc4Files = fileInput.target.files[0];
        this.pointChoc4Preview = true;
        console.log(this.pointChoc4Files);
    }

    onFaceAvantDroitFileChange(fileInput: any) {
        this.updateFaceAvantDroit = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.faceAvantDroitAttachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.faceAvantDroitAttachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.faceAvantDroitFiles = fileInput.target.files[0];
        this.faceAvantDroitPreview = true;
        console.log(this.faceAvantDroitFiles);
    }

    onPointChoc1FileChange(fileInput: any) {
        this.updatePointChoc1 = true;
        const indexOfFirst = fileInput.target.files[0].type.indexOf("/");

        this.extensionImageOriginal = fileInput.target.files[0].type.substring(
            indexOfFirst + 1,
            fileInput.target.files[0].type.length
        );
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = fileInput.target.files[0].name.indexOf(".");
        if (indexOfFirstPoint !== -1) {
            this.pointChoc1Attachment.name = "noExtension";
        } else {
            this.nomImage = fileInput.target.files[0].name.substring(
                0,
                indexOfFirstPoint
            );
            console.log(this.nomImage.concat(".", this.extensionImage));
            this.pointChoc1Attachment.name = this.nomImage.concat(
                ".",
                this.extensionImage
            );
        }
        this.pointChoc1Files = fileInput.target.files[0];
        this.pointChoc1Preview = true;
        console.log(this.pointChoc1Files);
    }

    deleteFaceAvantDroitFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showFaceAvantDroitAttachment = true;
                    this.faceAvantDroitPreview = false;
                    this.faceAvantDroitFiles = null;
                    this.faceAvantDroitAttachment.label = null;
                    this.showFaceAvantDroit = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deletePointChoc1File() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showPointChoc1Attachment = true;
                    this.pointChoc1Preview = false;
                    this.pointChoc1Files = null;
                    this.pointChoc1Attachment.label = null;
                    this.showPointChoc1 = false;
                    this.enableSaveExpertise = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deletePointChoc2File() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showPointChoc2Attachment = true;
                    this.pointChoc2Preview = false;
                    this.pointChoc2Files = null;
                    this.pointChoc2Attachment.label = null;
                    this.showPointChoc2 = false;
                    this.enableSaveExpertise = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deletePointChoc3File() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showPointChoc3Attachment = true;
                    this.pointChoc3Preview = false;
                    this.pointChoc3Files = null;
                    this.pointChoc3Attachment.label = null;
                    this.showPointChoc3 = false;
                    this.enableSaveExpertise = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deletePointChoc4File() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showPointChoc4Attachment = true;
                    this.pointChoc4Preview = false;
                    this.pointChoc4Files = null;
                    this.pointChoc4Attachment.label = null;
                    this.showPointChoc4 = false;
                    this.enableSaveExpertise = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteFaceAvantGaucheFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showFaceAvantGaucheAttachment = true;
                    this.faceAvantGauchePreview = false;
                    this.faceAvantGaucheFiles = null;
                    this.faceAvantGaucheAttachment.label = null;
                    this.showFaceAvantGauche = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteFaceArriereDroitFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showFaceArriereDroitAttachment = true;
                    this.faceArriereDroitPreview = false;
                    this.faceArriereDroitFiles = null;
                    this.faceArriereDroitAttachment.label = null;
                    this.showFaceArriereDroit = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteFaceArriereGaucheFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showFaceArriereGaucheAttachment = true;
                    this.faceArriereGauchePreview = false;
                    this.faceArriereGaucheFiles = null;
                    this.faceArriereGaucheAttachment.label = null;
                    this.showFaceArriereGauche = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteFinitionFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showFinitionAttachment = true;
                    this.finitionPreview = false;
                    this.finitionFiles = null;
                    this.finitionAttachment.label = null;
                    this.showFinition = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    dataURItoBlobAtt(dataURI, extention, type) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: type + "/" + extention });
        return blob;
    }
    deleteNSerieFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showNSerieAttachment = true;
                    this.nSeriePreview = false;
                    this.nSerieFiles = null;
                    this.nSerieAttachment.label = null;
                    this.showNSerie = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteImmatriculationFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showImmatriculationAttachment = true;
                    this.immatriculationPreview = false;
                    this.immatriculationFiles = null;
                    this.immatriculationAttachment.label = null;
                    this.showImmatriculation = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteCompteurFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showCompteurAttachment = true;
                    this.compteurPreview = false;
                    this.compteurFiles = null;
                    this.compteurAttachment.label = null;
                    this.showCompteur = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteCarteGriseFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showCarteGriseAttachment = true;
                    this.carteGrisePreview = false;
                    this.carteGriseFiles = null;
                    this.carteGriseAttachment.label = null;
                    this.showCarteGrise = false;
                    this.enableSaveSoumettre = false;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deleteGTFile() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.showGTAttachment = true;
                    this.gtPreview = false;
                    this.gtFiles = null;
                    this.gtAttachment.label = null;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    saveAttachments() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Etes-vous sûrs de vouloir enregistrer votre choix ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    let prestationPecId = this.sinisterPec.id;
                    //Face Avant Droit

                    if (
                        this.faceAvantDroitFiles !== null &&
                        this.updateFaceAvantDroit == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.faceAvantDroitFiles,
                                this.labelFaceAvantDroit,
                                this.faceAvantDroitAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.faceAvantDroitAttachment = res;
                                this.faceAvantDroitAttachment = new Attachment();
                                this.faceAvantDroitAttachment.name = null;
                                this.faceAvantDroitFiles = null;
                                this.showFaceAvantDroit = true;
                                this.showFaceAvantDroitAttachment = false;
                                this.faceAvantDroitPreview = false;
                            });
                    }

                    //Carte Grise

                    if (
                        this.carteGriseFiles !== null &&
                        this.updateCarteGrise == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.carteGriseFiles,
                                this.labelCarteGrise,
                                this.carteGriseAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.carteGriseAttachment = res;
                                this.carteGriseAttachment = new Attachment();
                                this.carteGriseAttachment.name = null;
                                this.carteGriseFiles = null;
                                this.showCarteGrise = true;
                                this.showCarteGriseAttachment = false;
                                this.carteGrisePreview = false;
                            });
                    }

                    //Face Avant Gauche

                    if (
                        this.faceAvantGaucheFiles !== null &&
                        this.updateFaceAvantGauche == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.faceAvantGaucheFiles,
                                this.labelFaceAvantGauche,
                                this.faceAvantGaucheAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.faceAvantGaucheAttachment = res;
                                this.faceAvantGaucheAttachment = new Attachment();
                                this.faceAvantGaucheAttachment.name = null;
                                this.faceAvantGaucheFiles = null;
                                this.showFaceAvantGauche = true;
                                this.showFaceAvantGaucheAttachment = false;
                                this.faceAvantGauchePreview = false;
                            });
                    }

                    //Face Arriere Droit

                    if (
                        this.faceArriereDroitFiles !== null &&
                        this.updateFaceArriereDroit == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.faceArriereDroitFiles,
                                this.labelFaceArriereDroit,
                                this.faceArriereDroitAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.faceArriereDroitAttachment = res;
                                this.faceArriereDroitAttachment = new Attachment();
                                this.faceArriereDroitAttachment.name = null;
                                this.faceArriereDroitFiles = null;
                                this.showFaceArriereDroit = true;
                                this.showFaceArriereDroitAttachment = false;
                                this.faceArriereDroitPreview = false;
                            });
                    }

                    //Face Arriere Gauche

                    if (
                        this.faceArriereGaucheFiles !== null &&
                        this.updateFaceArriereGauche == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.faceArriereGaucheFiles,
                                this.labelFaceArriereGauche,
                                this.faceArriereGaucheAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.faceArriereGaucheAttachment = res;
                                this.faceArriereGaucheAttachment = new Attachment();
                                this.faceArriereGaucheAttachment.name = null;
                                this.faceArriereGaucheFiles = null;
                                this.showFaceArriereGauche = true;
                                this.showFaceArriereGaucheAttachment = false;
                                this.faceArriereGauchePreview = false;
                            });
                    }

                    //Finition

                    if (
                        this.finitionFiles !== null &&
                        this.updateFinition == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.finitionFiles,
                                this.labelFinition,
                                this.finitionAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.finitionAttachment = res;
                                this.finitionAttachment = new Attachment();
                                this.finitionAttachment.name = null;
                                this.finitionFiles = null;
                                this.showFinition = true;
                                this.showFinitionAttachment = false;
                                this.finitionPreview = false;
                            });
                    }

                    //NSerie

                    if (
                        this.nSerieFiles !== null &&
                        this.updateNSerie == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.nSerieFiles,
                                this.labelNSerie,
                                this.nSerieAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.nSerieAttachment = res;
                                this.nSerieAttachment = new Attachment();
                                this.nSerieAttachment.name = null;
                                this.nSerieFiles = null;
                                this.showNSerie = true;
                                this.showNSerieAttachment = false;
                                this.nSeriePreview = false;
                            });
                    }

                    //Immatriculation

                    if (
                        this.immatriculationFiles !== null &&
                        this.updateImmatriculation == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.immatriculationFiles,
                                this.labelImmatriculation,
                                this.immatriculationAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.immatriculationAttachment = res;
                                this.immatriculationAttachment = new Attachment();
                                this.immatriculationAttachment.name = null;
                                this.immatriculationFiles = null;
                                this.showImmatriculation = true;
                                this.showImmatriculationAttachment = false;
                                this.immatriculationPreview = false;
                            });
                    }

                    //Compteur

                    if (
                        this.compteurFiles !== null &&
                        this.updateCompteur == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.compteurFiles,
                                this.labelCompteur,
                                this.compteurAttachment.name,
                                "quotation"
                            )
                            .subscribe((res: Attachment) => {
                                this.compteurAttachment = res;
                                this.compteurAttachment = new Attachment();
                                this.compteurAttachment.name = null;
                                this.compteurFiles = null;
                                this.showCompteur = true;
                                this.showCompteurAttachment = false;
                                this.compteurPreview = false;
                            });
                    }

                    this.savePhotoReparation();
                    this.enableSaveSoumettre = true;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    saveAttachmentsExpertise() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Etes-vous sûrs de vouloir enregistrer votre choix ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    let prestationPecId = this.sinisterPec.id;

                    //Point Choc 1

                    if (
                        this.pointChoc1Files !== null &&
                        this.updatePointChoc1 == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.pointChoc1Files,
                                this.labelPointChoc1,
                                this.pointChoc1Attachment.name,
                                "pointChocExpertise"
                            )
                            .subscribe((res: Attachment) => {
                                this.pointChoc1Attachment = res;
                                this.pointChoc1Attachment = new Attachment();
                                this.pointChoc1Attachment.name = null;
                                this.pointChoc1Files = null;
                                this.showPointChoc1 = true;
                                this.showPointChoc1Attachment = false;
                                this.pointChoc1Preview = false;
                            });
                    }

                    //Point Choc 2

                    if (
                        this.pointChoc2Files !== null &&
                        this.updatePointChoc2 == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.pointChoc2Files,
                                this.labelPointChoc2,
                                this.pointChoc2Attachment.name,
                                "pointChocExpertise"
                            )
                            .subscribe((res: Attachment) => {
                                this.pointChoc2Attachment = res;
                                this.pointChoc2Attachment = new Attachment();
                                this.pointChoc2Attachment.name = null;
                                this.pointChoc2Files = null;
                                this.showPointChoc2 = true;
                                this.showPointChoc2Attachment = false;
                                this.pointChoc2Preview = false;
                            });
                    }

                    //Point Choc 3

                    if (
                        this.pointChoc3Files !== null &&
                        this.updatePointChoc3 == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.pointChoc3Files,
                                this.labelPointChoc3,
                                this.pointChoc3Attachment.name,
                                "pointChocExpertise"
                            )
                            .subscribe((res: Attachment) => {
                                this.pointChoc3Attachment = res;
                                this.pointChoc3Attachment = new Attachment();
                                this.pointChoc3Attachment.name = null;
                                this.pointChoc3Files = null;
                                this.showPointChoc3 = true;
                                this.showPointChoc3Attachment = false;
                                this.pointChoc3Preview = false;
                            });
                    }

                    //Point Choc 4

                    if (
                        this.pointChoc4Files !== null &&
                        this.updatePointChoc4 == true
                    ) {
                        this.sinisterPecService
                            .saveAttachmentsQuotationNw(
                                prestationPecId,
                                this.pointChoc4Files,
                                this.labelPointChoc4,
                                this.pointChoc4Attachment.name,
                                "pointChocExpertise"
                            )
                            .subscribe((res: Attachment) => {
                                this.pointChoc4Attachment = res;
                                this.pointChoc4Attachment = new Attachment();
                                this.pointChoc4Attachment.name = null;
                                this.pointChoc4Files = null;
                                this.showPointChoc4 = true;
                                this.showPointChoc4Attachment = false;
                                this.pointChoc4Preview = false;
                            });
                    }

                    this.savePhotoExpertise();
                    this.enableSaveExpertise = true;
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    dataURItoBlob(dataURI) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: "image/png" });
        return blob;
    }

    dataURItoBlobAttchment(dataURI, extention, type) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: type + "/" + extention });
        return blob;
    }

    ModeAvisExpert(id) {
        this.actifModeAvisExpert = true;
    }

    dateAsYYYYMMDDHHNNSSLDT(date): string {
        return (
            date.getFullYear() +
            "-" +
            this.leftpad(date.getMonth() + 1, 2) +
            "-" +
            this.leftpad(date.getDate(), 2) +
            "T" +
            this.leftpad(date.getHours(), 2) +
            ":" +
            this.leftpad(date.getMinutes(), 2) +
            ":" +
            this.leftpad(date.getSeconds(), 2)
        );
    }
    dateAsYYYYMMDDHHNNSSLDTRV(date): string {
        return (
            date.getFullYear() +
            "-" +
            this.leftpad(date.getMonth() + 1, 2) +
            "-" +
            this.leftpad(date.getDate(), 2)
        );
    }
    leftpad(val, resultLength = 2, leftpadChar = "0"): string {
        return (String(leftpadChar).repeat(resultLength) + String(val)).slice(
            String(val).length
        );
    }

    addPieceJ() {
        if (
            this.pieceJ.typeLibelle === null ||
            this.pieceJ.typeLibelle === undefined
        ) {
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

    ajoutPiece1() {
        this.pieceAttachment1.type = this.labelPiece1;
        this.pieceAttachment1.originalFr = "Oui";
        this.pieceAttachment1.note = " ";
        this.pieceAttachment1.creationDateP = this.dateAsYYYYMMDDHHNNSS(
            new Date()
        );
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

    ajoutPiece1Expertise() {
        this.pieceAttachment1.type = this.labelPiece1;
        this.pieceAttachment1.originalFr = "Oui";
        this.pieceAttachment1.note = " ";
        this.pieceAttachment1.creationDateP = this.dateAsYYYYMMDDHHNNSS(
            new Date()
        );
        this.pieceAttachment1.creationDate = new Date();
        this.pieceAttachment1.pieceFile = this.pieceFiles1;
        this.piecesFilesExpertise.push(this.pieceFiles1);
        console.log(this.piecesFilesExpertise[0]);
        this.piecesAttachmentsExpertise.push(this.pieceAttachment1);
        this.pieceAttachment1 = new Attachment();
        this.pieceAttachment1.original = true;
        this.labelPiece1 = undefined;
        this.selectedItem = true;
        this.piecePreview1 = false;
        this.ajoutNouvelpieceformExpertise = false;
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
    //delete piece jointe
    deletePieceJ(pieceJ) {
        this.piecesAttachments.forEach((item, index) => {
            if (item === pieceJ) this.piecesAttachments.splice(index, 1);
        });
    }
    //edit piece jointe
    prepareEditPieceJ(piece) {
        this.pieceAttachment1 = new Attachment();
        this.pieceJ = piece;
        this.isPcsModeEdit = true;
        this.ajoutPiece = true;
        this.ajoutNouvelpieceform = true;
    }

    /*getGTRapportPdf(sinisterPec: SinisterPec) {
        if (sinisterPec.id == null || sinisterPec.id === undefined) {
            console.log("Erreur lors de la génération");

        } else { // OK
            this.sinisterPecService.getGTPdf(sinisterPec.id).subscribe((blob: Blob) => {
                let fileName = "Rapport_GT" + ".pdf";
                console.log(fileName);

                if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                    window.navigator.msSaveOrOpenBlob(blob, fileName);
                    console.log("test1-------");
                } else {
                    console.log("test2-------");
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
    }*/

    selectFile(event, libelle) {
        this.ajoutPiece = true;
        this.selectedFiles = event.target.files;
        // this.pieceFiles1 = event.target.files[0]; // read file as data url
        // this.piecesFiles.push(this.pieceFiles1);
    }

    // edit Piece Jointe
    editPieceJ() {
        this.pieceJ = new PieceJointe();
        this.isPcsModeEdit = false;
        this.ajoutNouvelpieceform = false;
    }

    //recéption vehicule (change etat d'interface selon etat de choc )
    changeNatureReparation(etat) {
        if (this.sinisterPec.stepId == 15 || this.sinisterPec.stepId == 25) {
            this.isModeSaisie = true;
            if (etat == false) {
                this.sinisterPec.disassemblyRequest = true;
                this.sinisterPec.lightShock = false;
                this.isQuoteEntry = false;
                this.isLightShock = false;
            } else if (etat == true) {
                this.sinisterPec.disassemblyRequest = false;
                this.sinisterPec.lightShock = true;
                this.isQuoteEntry = true;
                this.isLightShock = true;
            }
        }
    }

    formatEnDate(date) {
        var d = date,
            month = "" + (d.getMonth() + 1),
            day = "" + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = "0" + month;
        if (day.length < 2) day = "0" + day;

        return [year, month, day].join("-");
    }

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
            this.sysActionUtilisateurService
                .find(202)
                .subscribe((subRes: SysActionUtilisateur) => {
                    this.SysAction = subRes;
                    this.motifs = this.SysAction.motifs;
                });
            this.dropdownSettings = {
                singleSelection: false,
                idField: "id",
                textField: "nom",
                selectAllText: "Select All",
                unSelectAllText: "UnSelect All",
                itemsShowLimit: 6,
                allowSearchFilter: true,
            };
        } else {
            this.confirme = true;
        }
    }

    printQuotationPdf() {
        if (
            this.quotation.id !== null &&
            this.quotation.id !== undefined &&
            this.sinisterPec.id !== null &&
            this.sinisterPec.id !== undefined
        ) {
            this.quotation.sinPecId = this.sinisterPec.id;
            this.sinisterPecService
                .generateQuoatationPdf(this.quotation)
                .subscribe((res) => {
                    window.open(res.headers.get("pdfname"), "_blank");
                });
        }
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
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.tva = 19;
        this.detailsPiecesMO.push(this.detailsPiece);
    }
    /**
     * Remove mo line
     * @param index
     */
    removeMoLine(detailsPieceMo, index) {
        if (detailsPieceMo.id !== undefined && detailsPieceMo.id !== null) {
            this.detailsPiecesService
                .delete(detailsPieceMo.id)
                .subscribe((res) => {
                    this.deleteDetailsMo = true;
                    this.detailsPiecesMO.splice(index, 1);
                });
        } else {
            this.detailsPiecesMO.splice(index, 1);
        }
        this.calculateGlobalMoTtc();
    }

    deletePieceJointesPH(attachment: Attachment) {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Etes-vous sûrs de vouloir enregistrer votre choix ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.sinisterPecService
                        .deleteAttachmentsImprimes(attachment.id)
                        .subscribe((resAttR) => {
                            this.piecesAttachments.forEach((item, index) => {
                                if (item === attachment)
                                    this.piecesAttachments.splice(index, 1);
                            });
                        });
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    deletePieceJointesExpertise(attachment: Attachment) {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Etes-vous sûrs de vouloir enregistrer votre choix ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.sinisterPecService
                        .deleteAttachmentsImprimes(attachment.id)
                        .subscribe((resAttR) => {
                            this.piecesAttachmentsExpertise.forEach(
                                (item, index) => {
                                    if (item === attachment)
                                        this.piecesAttachmentsExpertise.splice(
                                            index,
                                            1
                                        );
                                }
                            );
                        });
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    /**
     *
     * @param detailsMoLine
     */
    changeMo(detailsMoLine: DetailsPieces) {
        detailsMoLine.totalHt = +(
            detailsMoLine.prixUnit * detailsMoLine.nombreHeures
        ).toFixed(3);
        detailsMoLine.amountDiscount = +(
            (detailsMoLine.totalHt * detailsMoLine.discount) /
            100
        ).toFixed(3);
        detailsMoLine.amountAfterDiscount = +(
            detailsMoLine.totalHt - detailsMoLine.amountDiscount
        ).toFixed(3);
        detailsMoLine.amountVat = +(
            (detailsMoLine.amountAfterDiscount * detailsMoLine.tva) /
            100
        ).toFixed(3);
        detailsMoLine.totalTtc = +(
            detailsMoLine.amountAfterDiscount + detailsMoLine.amountVat
        ).toFixed(3);

        this.calculateGlobalMoTtc();
    }
    changeNaturePieceRechange(nature: string, piece: DetailsPieces) {
        console.log("change nature-*-*");
        if (nature == "ORIGINE" || nature == "GENERIQUE") {
            console.log(" nature origine ou generique-******-*" + nature);
            piece.tva = 19;
        }
        if (nature == "CASSE") {
            console.log(" nature casse-******-*" + nature);
            piece.tva = 0;
        }
        this.changePieceRechange(piece);
    }

    getObservations(id) {
        this.observationService
            .findByPrestation(id)
            .subscribe((subRes: ResponseWrapper) => {
                this.observations = subRes.json;
                if (this.observations === null) {
                    this.observations = [];
                } else if (this.observations.length > 0) {
                    this.observations.forEach((observation) => {
                        observation.date = this.formatEnDate(observation.date);
                    });
                }
            });
    }

    formatDate(date) {
        var d = date,
            month = "" + (d.getMonth() + 1),
            day = "" + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = "0" + month;
        if (day.length < 2) day = "0" + day;

        return [day, month, year].join("/");
    }
    /**
     * Calculate the total Ttc in quotation where mo modified
     */
    calculateGlobalMoTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty
            ? this.quotation.stampDuty
            : 0;
        this.moTotalTtc = 0;
        if (this.detailsPiecesMO && this.detailsPiecesMO.length > 0) {
            for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                if (
                    this.detailsPiecesMO[i].modifiedLine === null ||
                    this.detailsPiecesMO[i].modifiedLine === undefined
                ) {
                    this.moTotalTtc += this.detailsPiecesMO[i].totalTtc;
                }
            }
        }
        this.moTotalTtc = +this.moTotalTtc.toFixed(3);
        this.quotation.ttcAmount +=
            this.moTotalTtc +
            this.ingTotalTtc +
            this.fournTotalTtc +
            this.piecesTotalTtc;
        this.quotation.ttcAmount = +this.quotation.ttcAmount.toFixed(3);
    }

    loadAllDetailsMo() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.MAIN_OEUVRE,
                true
            )
            .subscribe((subRes: ResponseWrapper) => {
                if (
                    this.sinisterPec.preliminaryReport == true &&
                    this.confirmationDevisComplementaire
                ) {
                    this.detailsPiecesMOCP = subRes.json;
                } else {
                    this.detailsPiecesMO = subRes.json;
                }

                if (this.sinisterPec.modificationPrix) {
                    this.detailsPiecesMO.forEach((element) => {
                        element.isComplementary = false;
                    });
                }
                this.MoDetails = subRes.json;
                if (this.AvisExpert) {
                    this.detailsPiecesMO.forEach((detailPiecesMO) => {
                        this.observationExpertByTypeIntervention(
                            detailPiecesMO
                        );
                        this.DecisionExpertByObser(
                            detailPiecesMO.observationExpert,
                            detailPiecesMO
                        );
                    });
                    let som = 0;
                    this.quotation.estimateJour = som / 8 + 1;
                }

                this.calculateGlobalMoTtc();
                this.getSum4();
                if (this.isMaJDevis) {
                    this.MoDetails.forEach((detailsPiece) => {
                        this.changeMo(detailsPiece);
                    });
                }

                let objectDetailsMo = this.detailsPiecesMO;
                objectDetailsMo.forEach((detailsPiecesMO) => {
                    let objDetailsMo = Object.assign({}, detailsPiecesMO);
                    this.lastDetailsPieces.push(objDetailsMo);
                });
            });

        if (this.modeConfirmation == true || this.isMaJDevis == true) {
            this.detailsPiecesService
                .queryByDevisAndTypeOther(
                    this.quotation.id,
                    TypePiecesDevis.MAIN_OEUVRE,
                    true
                )
                .subscribe((subRes: ResponseWrapper) => {
                    this.detailsPiecesMO1 = subRes.json;
                });
        }
    }

    loadAllDetailsMoForMP() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.MAIN_OEUVRE,
                true
            )
            .subscribe((subRes: ResponseWrapper) => {
                //this.detailsPiecesMOCP = subRes.json;
                subRes.json.forEach((element) => {
                    if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire &&
                        this.quotation.statusId == 4
                    ) {
                        this.detailsPiecesMO = subRes.json;
                        this.detailsPiecesMOCP.push(element);
                    } else if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire
                    ) {
                        this.detailsPiecesMOCP.push(element);
                    } else {
                        this.detailsPiecesMO.push(element);
                    }
                });
                this.detailsPiecesMO.forEach((element) => {
                    if (element.isComplementary == false) {
                    } else {
                        element.isComplementary = true;
                    }
                });

                this.calculateGlobalMoTtc();
                this.getSum4();
                //this.loadAllIngredient();
            });
    }

    loadAllDetailsMoTest() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotationMP.originalQuotationId,
                TypePiecesDevis.MAIN_OEUVRE,
                true
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesMOTest = subRes.json;
            });
    }

    loadAllDetailsMoQuotationMP() {
        this.detailsPiecesService
            .queryByQuotationMPAndType(
                this.quotationMP.id,
                TypePiecesDevis.MAIN_OEUVRE,
                true
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesMO = subRes.json;
                this.calculateGlobalMoTtc();
                this.getSum4();
                //this.loadAllIngredientForQuotationMP();
            });
    }

    /**
     *
     * @param detailsIngredientLine
     */
    changeIngredient(dpIngredient: DetailsPieces) {
        dpIngredient.totalHt = +(
            dpIngredient.prixUnit * dpIngredient.quantite
        ).toFixed(3);
        dpIngredient.amountDiscount = +(
            (dpIngredient.totalHt * dpIngredient.discount) /
            100
        ).toFixed(3);
        dpIngredient.amountAfterDiscount = +(
            dpIngredient.totalHt - dpIngredient.amountDiscount
        ).toFixed(3);
        dpIngredient.amountVat = +(
            (dpIngredient.amountAfterDiscount * dpIngredient.tva) /
            100
        ).toFixed(3);
        dpIngredient.totalTtc = +(
            dpIngredient.amountAfterDiscount + dpIngredient.amountVat
        ).toFixed(3);
        this.calculateGlobalIngTtc();
    }
    /**
     *
     * @param detailsFournitureLine
     */
    changeFourniture(fourniture: DetailsPieces) {
        fourniture.totalHt = +(
            fourniture.prixUnit * fourniture.quantite
        ).toFixed(3);
        fourniture.amountDiscount = +(
            (fourniture.totalHt * fourniture.discount) /
            100
        ).toFixed(3);
        fourniture.amountAfterDiscount = +(
            fourniture.totalHt - fourniture.amountDiscount
        ).toFixed(3);
        fourniture.amountVat = +(
            (fourniture.amountAfterDiscount * fourniture.tva) /
            100
        ).toFixed(3);
        fourniture.totalTtc = +(
            fourniture.amountAfterDiscount + fourniture.amountVat
        ).toFixed(3);
        this.calculateGlobalFournTtc();
    }

    /**
     *
     * @param detailsPieceRechangeLine
     */
    changePieceRechange(rechange: DetailsPieces) {
        rechange.totalHt = +(rechange.prixUnit * rechange.quantite).toFixed(3);
        rechange.amountDiscount = +(
            (rechange.totalHt * rechange.discount) /
            100
        ).toFixed(3);
        rechange.amountAfterDiscount = +(
            rechange.totalHt - rechange.amountDiscount
        ).toFixed(3);
        rechange.amountVat = +(
            (rechange.amountAfterDiscount * rechange.tva) /
            100
        ).toFixed(3);
        rechange.totalTtc = +(
            rechange.amountAfterDiscount + rechange.amountVat
        ).toFixed(3);
        this.calculateGlobalPiecesTtc();
    }
    /**
     *
     * @param calculateHTVVetusteAndTTCVetuste
     */
    calculerHTVVetusteAndTTCVetuste(detailsPiece: DetailsPieces) {
        detailsPiece.HTVetuste =
            (detailsPiece.totalHt * detailsPiece.vetuste) / 100;
        detailsPiece.TTCVetuste =
            detailsPiece.HTVetuste + (detailsPiece.HTVetuste * 19) / 100;
    }
    /**
     * Remove mo line
     * @param index
     */
    getSum4(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMos.length; i++) {
            if (
                this.detailsMos[i].typeInterventionId &&
                this.detailsMos[i].nombreHeures
            ) {
                sum += this.detailsMos[i].totalHt;
            }
        }
        return sum;
    }
    getSum7(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMos.length; i++) {
            if (
                this.detailsMos[i].typeInterventionId &&
                this.detailsMos[i].nombreHeures
            ) {
                sum += this.detailsMos[i].totalTtc;
            }
        }
        return sum;
    }

    /******************************************************** Détails Ingredient de peinture ***************************************************/

    addNewIngredientLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.tva = 19;
        this.detailsPiecesIngredient.push(this.detailsPiece);
        this.isLightShock = false;
    }
    addNewPieceRechangeLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPieces.push(this.detailsPiece);
        this.isLightShock = false;
    }
    addNewPieceFournitureLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.tva = 19;
        this.detailsPiecesFourniture.push(this.detailsPiece);
        this.isLightShock = false;
    }
    confirmationQuots(etat) {
        if (etat) {
            this.quotation.confirmationDecisionQuote = true;
        } else this.quotation.confirmationDecisionQuote = false;
    }

    removePieceRechangeLine(pieces, index) {
        if (pieces.id !== undefined && pieces.id !== null) {
            this.detailsPiecesService.delete(pieces.id).subscribe((res) => {
                this.deletedpRechange = true;
                this.detailsPieces.splice(index, 1);
            });
        } else {
            this.detailsPieces.splice(index, 1);
        }
        this.calculateGlobalPiecesTtc();
    }

    addNewPieceFournitureLineForPetiteFourniture() {
        if (this.detailsPiecesIngredient.length > 0) {
            if (this.detailsPiecesFourniture.length == 0) {
                this.detailsPiecesFourniture = [];
                this.detailsPiece = new DetailsPieces();
                this.detailsPiece.tva = 19;
                this.detailsPiece.typeInterventionId = 6;

                let quant = 0;
                let tauxForFour = 0;
                this.detailsPiecesIngredient.forEach((element) => {
                    quant = quant + element.quantite;
                    tauxForFour = element.prixUnit;
                });
                this.detailsPiece.prixUnit =
                    (this.reparateur.petiteFourniture * tauxForFour) / 100;
                this.detailsPiece.quantite = quant;
                this.detailsPiece.totalHt = +(
                    this.detailsPiece.prixUnit * this.detailsPiece.quantite
                ).toFixed(3);
                this.detailsPiece.totalTtc = +(
                    this.detailsPiece.prixUnit * this.detailsPiece.quantite +
                    (this.detailsPiece.prixUnit *
                        this.detailsPiece.quantite *
                        19) /
                        100
                ).toFixed(3);
                this.detailsPiecesFourniture.push(this.detailsPiece);
                this.isLightShock = false;
                this.calculateGlobalFournTtc();
            } else {
            }
        }
    }

    /**
     * calculate totalTTC
     * @param detailsLine
     */

    /**
     * Calculate the total Ttc in quotation where ing modified
     */
    calculateGlobalIngTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty
            ? this.quotation.stampDuty
            : 0;
        this.ingTotalTtc = 0;
        if (
            this.detailsPiecesIngredient &&
            this.detailsPiecesIngredient.length > 0
        ) {
            for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
                if (
                    this.detailsPiecesIngredient[i].modifiedLine != null &&
                    this.detailsPiecesIngredient[i].modifiedLine != undefined
                ) {
                } else {
                    this.ingTotalTtc += this.detailsPiecesIngredient[
                        i
                    ].totalTtc;
                }
            }
        }
        this.ingTotalTtc = +this.ingTotalTtc.toFixed(3);
        this.quotation.ttcAmount +=
            this.moTotalTtc +
            this.ingTotalTtc +
            this.fournTotalTtc +
            this.piecesTotalTtc;
        this.quotation.ttcAmount = +this.quotation.ttcAmount.toFixed(3);

        if (
            this.notPf ||
            this.sinisterPec.primaryQuotation == null ||
            this.sinisterPec.primaryQuotation == undefined
        ) {
            if (this.detailsPiecesIngredient.length > 0) {
                if (this.detailsPiecesFourniture.length == 0) {
                    this.detailsPiecesFourniture = [];
                    this.detailsPiece = new DetailsPieces();
                    this.detailsPiece.tva = 19;
                    this.detailsPiece.typeInterventionId = 6;

                    let quant = 0;
                    let tauxForFour = 0;
                    this.detailsPiecesIngredient.forEach((element) => {
                        quant = quant + element.quantite;
                        tauxForFour = element.prixUnit;
                    });
                    this.detailsPiece.prixUnit =
                        (this.reparateur.petiteFourniture * tauxForFour) / 100;
                    this.detailsPiece.quantite = quant;
                    this.detailsPiece.totalHt = +(
                        this.detailsPiece.prixUnit * this.detailsPiece.quantite
                    ).toFixed(3);
                    this.detailsPiece.totalTtc = +(
                        this.detailsPiece.prixUnit *
                            this.detailsPiece.quantite +
                        (this.detailsPiece.prixUnit *
                            this.detailsPiece.quantite *
                            19) /
                            100
                    ).toFixed(3);
                    this.detailsPiecesFourniture.push(this.detailsPiece);
                    this.isLightShock = false;
                    this.calculateGlobalFournTtc();
                } else {
                    this.detailsPiecesFourniture.forEach((fourniture) => {
                        //this.detailsPiecesFourniture = [];
                        //this.detailsPiece = new DetailsPieces();
                        fourniture.tva = 19;
                        fourniture.typeInterventionId = 6;

                        let quant = 0;
                        let tauxForFour = 0;
                        this.detailsPiecesIngredient.forEach((element) => {
                            quant = quant + element.quantite;
                            tauxForFour = element.prixUnit;
                        });
                        fourniture.prixUnit =
                            (this.reparateur.petiteFourniture * tauxForFour) /
                            100;
                        fourniture.quantite = quant;
                        fourniture.totalHt = +(
                            fourniture.prixUnit * fourniture.quantite
                        ).toFixed(3);
                        fourniture.totalTtc = +(
                            fourniture.prixUnit * fourniture.quantite +
                            (fourniture.prixUnit * fourniture.quantite * 19) /
                                100
                        ).toFixed(3);
                        //this.detailsPiecesFourniture.push(this.detailsPiece);
                        //this.isLightShock = false;
                        this.calculateGlobalFournTtc();
                    });
                }
            }
        }
        this.notPf = true;
    }

    calculateGlobalIngTtcMP() {
        this.quotation.ttcAmount = this.quotation.stampDuty
            ? this.quotation.stampDuty
            : 0;
        this.ingTotalTtc = 0;
        if (
            this.detailsPiecesIngredient &&
            this.detailsPiecesIngredient.length > 0
        ) {
            for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
                if (
                    this.detailsPiecesIngredient[i].modifiedLine != null &&
                    this.detailsPiecesIngredient[i].modifiedLine != undefined
                ) {
                } else {
                    this.ingTotalTtc += this.detailsPiecesIngredient[
                        i
                    ].totalTtc;
                }
            }
        }
        this.ingTotalTtc = +this.ingTotalTtc.toFixed(3);
        this.quotation.ttcAmount +=
            this.moTotalTtc +
            this.ingTotalTtc +
            this.fournTotalTtc +
            this.piecesTotalTtc;
        this.quotation.ttcAmount = +this.quotation.ttcAmount.toFixed(3);
    }

    compareDetailsPieceNv(
        lastDetailsPieces: DetailsPieces[],
        listPieces: DetailsPieces[]
    ) {
        listPieces.forEach((listPiece) => {
            for (let index = 0; index < lastDetailsPieces.length; index++) {
                const lastDetailsPiece = lastDetailsPieces[index];

                console.log("lastDetails" + lastDetailsPiece.totalTtc);
                console.log("listPiece" + listPiece.totalTtc);

                if (
                    lastDetailsPiece.id == listPiece.id &&
                    (lastDetailsPiece.totalTtc != listPiece.totalTtc ||
                        lastDetailsPiece.nombreMOEstime !=
                            listPiece.nombreMOEstime ||
                        lastDetailsPiece.designationId !=
                            listPiece.designationId)
                ) {
                    let lastDetail = Object.assign({}, lastDetailsPiece);
                    if (
                        lastDetailsPiece.nombreMOEstime !=
                            listPiece.nombreMOEstime &&
                        listPiece.nombreMOEstime > 0
                    ) {
                        listPiece.nombreHeures = listPiece.nombreMOEstime;
                        this.changeMo(lastDetail);
                    }
                    listPiece.isModified = true;
                    lastDetail.modifiedLine = listPiece.id;
                    lastDetail.id = null;
                    listPieces.push(lastDetail);
                    break;
                }
            }
        });
        console.log(
            "taille de last list " +
                lastDetailsPieces.length +
                "taille de new list" +
                listPieces.length
        );
    }

    compareDetailsPiece(
        lastDetailsPieces: DetailsPieces[],
        listPieces: DetailsPieces[]
    ) {
        listPieces.forEach((listPiece) => {
            for (let index = 0; index < lastDetailsPieces.length; index++) {
                const lastDetailsPiece = lastDetailsPieces[index];

                console.log("lastDetails" + lastDetailsPiece.totalTtc);
                console.log("listPiece" + listPiece.totalTtc);

                if (
                    lastDetailsPiece.observationExpert != 1 &&
                    lastDetailsPiece.observationExpert != 2 &&
                    listPiece.observationExpert != 1 &&
                    listPiece.observationExpert != 2 &&
                    lastDetailsPiece.id == listPiece.id &&
                    (lastDetailsPiece.totalTtc != listPiece.totalTtc ||
                        lastDetailsPiece.typeInterventionId !=
                            listPiece.typeInterventionId ||
                        lastDetailsPiece.nombreMOEstime !=
                            listPiece.nombreMOEstime ||
                        lastDetailsPiece.designationId !=
                            listPiece.designationId)
                ) {
                    let lastDetail = Object.assign({}, lastDetailsPiece);
                    if (
                        lastDetailsPiece.nombreMOEstime !=
                            listPiece.nombreMOEstime &&
                        listPiece.nombreMOEstime > 0
                    ) {
                        listPiece.nombreHeures = listPiece.nombreMOEstime;
                        this.changeMo(lastDetail);
                    }
                    listPiece.isModified = true;
                    lastDetail.modifiedLine = listPiece.id;
                    lastDetail.id = null;
                    listPieces.push(lastDetail);
                    break;
                }
            }
        });
        console.log(
            "taille de last list " +
                lastDetailsPieces.length +
                "taille de new list" +
                listPieces.length
        );
    }

    changeTypeInterventionMO(detailsPieceMo: DetailsPieces) {
        switch (detailsPieceMo.typeInterventionId) {
            case 1:
                detailsPieceMo.prixUnit = this.reparateur.tauxHoraireMOReparation;
                break;
            case 2:
                detailsPieceMo.prixUnit = this.reparateur.tauxHoraireMORemplacement;
                break;
            case 3:
                detailsPieceMo.prixUnit = this.reparateur.tauxHoraireMOPeintur;
                break;
            case 7:
                detailsPieceMo.prixUnit = this.reparateur.tauxHorairesReparationHauteTechnicite;
                break;
            case 8:
                detailsPieceMo.prixUnit = this.reparateur.montagePareBriseAvecColle;
                break;
            case 9:
                detailsPieceMo.prixUnit = this.reparateur.montagePareBriseAvecJoint;
                break;
            case 10:
                detailsPieceMo.prixUnit = this.reparateur.montageLunetteAriereAvecColle;
                break;
            case 11:
                detailsPieceMo.prixUnit = this.reparateur.montageLunetteAriereAvecjoint;
                break;
            case 12:
                detailsPieceMo.prixUnit = this.reparateur.montageVitreDePorte;
                break;
            case 13:
                detailsPieceMo.prixUnit = this.reparateur.montageVoletDairAvecColleOuJoint;
                break;
        }
        this.changeMo(detailsPieceMo);
    }
    changeTypeInterventionIP(dpIngredient: DetailsPieces) {
        console.log(
            "peinture designation-*-*-*-*-" + dpIngredient.typeInterventionId
        );
        switch (dpIngredient.typeInterventionId) {
            case 4:
                dpIngredient.prixUnit = this.reparateur.hydro;
                break;
            case 5:
                dpIngredient.prixUnit = this.reparateur.solvant;
                break;
        }

        this.changeIngredient(dpIngredient);
    }
    changeTypeInterventionPF(fourniture: DetailsPieces) {
        if (fourniture.typeInterventionId == 6) {
            fourniture.prixUnit = this.reparateur.petiteFourniture;
            this.changeFourniture(fourniture);
        }
    }

    observationExpertByTypeIntervention(detailsPieceMo: DetailsPieces) {
        this.detailsPieceMo.listobservationExpert = [];
        var expertObservation = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 3, label: "A Réparer" },
            { id: 4, label: "A Remplacer" },
            { id: 5, label: "Accordé avec Modification" },
        ];
        if (detailsPieceMo.typeInterventionId === 1) {
            expertObservation.forEach((item, index) => {
                if (item.id === 3) expertObservation.splice(index, 1);
            });
        } else if (detailsPieceMo.typeInterventionId === 2) {
            expertObservation.forEach((item, index) => {
                if (item.id === 4) expertObservation.splice(index, 1);
            });
        } else if (detailsPieceMo.typeInterventionId === 3) {
            expertObservation.forEach((item, index) => {
                if (item.id === 3) expertObservation.splice(index, 1);
            });
            expertObservation.forEach((item, index) => {
                if (item.id === 4) expertObservation.splice(index, 1);
            });
        }
        detailsPieceMo.listobservationExpert = expertObservation;
    }

    /**
     * Remove ingredient line
     * @param index
     */
    removeIngredientLine(dpIngredient, index) {
        if (dpIngredient.id !== undefined && dpIngredient.id !== null) {
            this.detailsPiecesService
                .delete(dpIngredient.id)
                .subscribe((res) => {
                    this.deletedpIngredient = true;
                    this.detailsPiecesIngredient.splice(index, 1);
                });
        } else {
            this.detailsPiecesIngredient.splice(index, 1);
        }
        this.calculateGlobalIngTtc();
        /*this.detailsPiecesFourniture = [];
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.tva = 19;
        this.detailsPiece.typeInterventionId = 6;

        let quant = 0;
        let tauxForFour = 0;
        this.detailsPiecesIngredient.forEach(element => {
            quant = quant + element.quantite;
            tauxForFour = element.prixUnit;
        });
        this.detailsPiece.prixUnit = (this.reparateur.petiteFourniture * tauxForFour) / 100;
        this.detailsPiece.quantite = quant;
        this.detailsPiece.totalHt = this.detailsPiece.prixUnit * this.detailsPiece.quantite;
        this.detailsPiece.totalTtc = this.detailsPiece.prixUnit * this.detailsPiece.quantite + ((this.detailsPiece.prixUnit * this.detailsPiece.quantite * 19) / 100);
        this.detailsPiecesFourniture.push(this.detailsPiece);
        this.isLightShock = false;
        this.calculateGlobalFournTtc();*/
    }
    /**
     * Load all ingredient line
     */

    loadAllIngredient() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.INGREDIENT_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                if (
                    this.sinisterPec.preliminaryReport == true &&
                    this.confirmationDevisComplementaire
                ) {
                    this.detailsPiecesIngredientCP = subRes.json;
                } else {
                    this.detailsPiecesIngredient = subRes.json;
                }
                if (this.sinisterPec.modificationPrix) {
                    this.detailsPiecesIngredient.forEach((element) => {
                        element.isComplementary = false;
                    });
                }

                this.calculateGlobalIngTtc();
                //this.loadAllRechange();
                if (this.AvisExpert) {
                    this.detailsPiecesIngredient.forEach(
                        (detailPiecesIngredient) => {
                            this.DecisionExpertByObser(
                                detailPiecesIngredient.observationExpert,
                                detailPiecesIngredient
                            );
                        }
                    );
                }

                let objectIngredient = this.detailsPiecesIngredient;
                objectIngredient.forEach((detailsPiecesIngredient) => {
                    let objIngredient = Object.assign(
                        {},
                        detailsPiecesIngredient
                    );
                    this.lastDetailsPieces.push(objIngredient);
                });
            });
        if (this.modeConfirmation == true || this.isMaJDevis == true) {
            this.detailsPiecesService
                .queryByDevisAndTypeOther(
                    this.quotation.id,
                    TypePiecesDevis.INGREDIENT_FOURNITURE,
                    false
                )
                .subscribe((subRes: ResponseWrapper) => {
                    this.detailsPiecesIngredient1 = subRes.json;
                });
        }
    }

    loadAllIngredientForMP() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.INGREDIENT_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                //this.detailsPiecesIngredientCP = subRes.json;
                subRes.json.forEach((element) => {
                    if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire &&
                        this.quotation.statusId == 4
                    ) {
                        this.detailsPiecesIngredient = subRes.json;
                        this.detailsPiecesIngredientCP.push(element);
                    } else if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire
                    ) {
                        this.detailsPiecesIngredientCP.push(element);
                    } else {
                        this.detailsPiecesIngredient.push(element);
                    }
                });

                this.detailsPiecesIngredient.forEach((element) => {
                    if (element.isComplementary == false) {
                    } else {
                        element.isComplementary = true;
                    }
                });

                this.calculateGlobalIngTtcMP();
            });
    }

    loadAllIngredientTest() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotationMP.originalQuotationId,
                TypePiecesDevis.INGREDIENT_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesIngredientTest = subRes.json;
            });
    }

    loadAllIngredientForQuotationMP() {
        this.detailsPiecesService
            .queryByQuotationMPAndType(
                this.quotationMP.id,
                TypePiecesDevis.INGREDIENT_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesIngredient = subRes.json;
                console.log(
                    "testLengthIngredient " +
                        this.detailsPiecesIngredient.length
                );
                this.calculateGlobalIngTtc();
                //this.loadAllRechangeForQuotationMP();
            });
    }
    /******************************************************** Détails Piéces de Rechange ***************************************************/

    getSumPieceHT(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsPieces.length; i++) {
            if (
                this.detailsPieces[i].prixUnit &&
                this.detailsPieces[i].quantite &&
                this.detailsPieces[i].tva
            ) {
                sum += this.detailsPieces[i].totalHt;
            }
        }
        return sum;
    }

    getSumPieceTTC(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsPieces.length; i++) {
            if (this.detailsPieces[i].isModified != true) {
                if (
                    this.detailsPieces[i].prixUnit &&
                    this.detailsPieces[i].quantite &&
                    this.detailsPieces[i].tva
                ) {
                    sum += this.detailsPieces[i].totalTtc;
                }
            }
        }
        return sum;
    }

    loadAllRechange() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.MAIN_OEUVRE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                if (
                    this.sinisterPec.preliminaryReport == true &&
                    this.confirmationDevisComplementaire
                ) {
                    this.detailsPiecesCP = subRes.json;
                } else {
                    this.detailsPieces = subRes.json;
                }
                if (this.sinisterPec.modificationPrix) {
                    this.detailsPieces.forEach((element) => {
                        element.isComplementary = false;
                    });
                }

                this.calculateGlobalPiecesTtc();
                //this.loadAllFourniture();

                if (this.AvisExpert) {
                    this.detailsPieces.forEach((detailPieces) => {
                        this.DecisionExpertByObser(
                            detailPieces.observationExpert,
                            detailPieces
                        );
                    });
                }

                let objectRechange = this.detailsPieces;
                objectRechange.forEach((detailsPieces) => {
                    let objRechange = Object.assign({}, detailsPieces);
                    this.lastDetailsPieces.push(objRechange);
                });
            });

        if (this.modeConfirmation == true || this.isMaJDevis == true) {
            this.detailsPiecesService
                .queryByDevisAndTypeOther(
                    this.quotation.id,
                    TypePiecesDevis.MAIN_OEUVRE,
                    false
                )
                .subscribe((subRes: ResponseWrapper) => {
                    this.detailsPieces1 = subRes.json;
                });
        }
    }

    loadAllRechangeForMP() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.MAIN_OEUVRE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                //this.detailsPiecesCP = subRes.json;
                subRes.json.forEach((element) => {
                    if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire &&
                        this.quotation.statusId == 4
                    ) {
                        this.detailsPieces = subRes.json;
                        this.detailsPiecesCP.push(element);
                    } else if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire
                    ) {
                        this.detailsPiecesCP.push(element);
                    } else {
                        this.detailsPieces.push(element);
                    }
                });
                this.detailsPieces.forEach((element) => {
                    if (element.isComplementary == false) {
                    } else {
                        element.isComplementary = true;
                    }
                });
                this.calculateGlobalPiecesTtc();
            });
    }

    loadAllRechangeTest() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotationMP.originalQuotationId,
                TypePiecesDevis.MAIN_OEUVRE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesTest = subRes.json;
            });
    }

    loadAllRechangeForQuotationMP() {
        this.detailsPiecesService
            .queryByQuotationMPAndType(
                this.quotationMP.id,
                TypePiecesDevis.MAIN_OEUVRE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPieces = subRes.json;
                this.calculateGlobalPiecesTtc();
                //this.loadAllFournitureForQuotationMP();
            });
    }

    /**
     * Calculate the total Ttc in quotation where pieces modified
     */
    calculateGlobalPiecesTtc() {
        this.quotation.ttcAmount = this.quotation.stampDuty
            ? this.quotation.stampDuty
            : 0;
        this.piecesTotalTtc = 0;
        if (this.detailsPieces && this.detailsPieces.length > 0) {
            for (let i = 0; i < this.detailsPieces.length; i++) {
                if (
                    this.detailsPieces[i].modifiedLine != null &&
                    this.detailsPieces[i].modifiedLine != undefined
                ) {
                } else {
                    this.piecesTotalTtc += this.detailsPieces[i].totalTtc;
                }
            }
        }
        this.piecesTotalTtc = +this.piecesTotalTtc.toFixed(3);
        this.quotation.ttcAmount +=
            this.moTotalTtc +
            this.ingTotalTtc +
            this.fournTotalTtc +
            this.piecesTotalTtc;
        this.quotation.ttcAmount = +this.quotation.ttcAmount.toFixed(3);
    }

    /**
     * Remove fourniture line
     * @param index
     */
    removeFournitureLine(fourniture, index) {
        if (fourniture.id !== undefined && fourniture.id !== null) {
            this.detailsPiecesService.delete(fourniture.id).subscribe((res) => {
                this.deletedpIngredient = true;
                this.detailsPiecesFourniture.splice(index, 1);
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
        this.quotation.ttcAmount = this.quotation.stampDuty
            ? this.quotation.stampDuty
            : 0;
        this.fournTotalTtc = 0;
        if (
            this.detailsPiecesFourniture &&
            this.detailsPiecesFourniture.length > 0
        ) {
            for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
                if (
                    this.detailsPiecesFourniture[i].modifiedLine != null &&
                    this.detailsPiecesFourniture[i].modifiedLine != undefined
                ) {
                } else {
                    this.fournTotalTtc += this.detailsPiecesFourniture[
                        i
                    ].totalTtc;
                }
            }
        }
        this.fournTotalTtc = +this.fournTotalTtc.toFixed(3);
        this.quotation.ttcAmount +=
            this.moTotalTtc +
            this.ingTotalTtc +
            this.fournTotalTtc +
            this.piecesTotalTtc;
        this.quotation.ttcAmount = +this.quotation.ttcAmount.toFixed(3);
    }
    /**
     * Load all pieces fourniture
     */
    addLineModified(
        detailsPieceMo: DetailsPieces,
        detailsPiecesMO: DetailsPieces[]
    ) {
        this.lastListPiecesModified = [];
        let list = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
        list.forEach(ligne => {
            if (ligne.isModified == true) {
                this.detailsPiecesService.getDetailsPieceBylineModified(this.quotation.id, ligne.id).subscribe((subRes: DetailsPieces) => {
                    this.lastListPiecesModified.push(subRes);
                });
            }
        });
        detailsPieceMo.scaledDown = true;
        this.detailsPiecesService
            .getDetailsPieceBylineModified(this.quotation.id, detailsPieceMo.id)
            .subscribe((subRes: DetailsPieces) => {
                detailsPiecesMO.push(subRes);                      
            });

        
   
    }

    addLineModifiedPiece(
        detailsPieceMo: DetailsPieces,
        detailsPiecesMO: DetailsPieces[]
    ) {
        this.lastListPiecesModified = [];
        let list = this.detailsPiecesMO.concat(this.detailsPieces);
        list.forEach(ligne => {
            if (ligne.isModified == true) {
                this.detailsPiecesService.getDetailsPieceBylineModified(this.quotation.id, ligne.id).subscribe((subRes: DetailsPieces) => {
                    this.lastListPiecesModified.push(subRes);
                });
            }
        });
        detailsPieceMo.scaledDown = true;
        this.detailsPiecesService
            .getDetailsPieceBylineModified(this.quotation.id, detailsPieceMo.id)
            .subscribe((subRes: DetailsPieces) => {
                detailsPiecesMO.push(subRes);
            });
    }

    removeLineModified(
        detailsPieceMo: DetailsPieces,
        detailsPiecesMO: DetailsPieces[]
    ) {
        console.log(detailsPiecesMO);
        detailsPieceMo.scaledDown = false;
        detailsPiecesMO.forEach((item, index) => {
            if (item.modifiedLine == detailsPieceMo.id)
                detailsPiecesMO.splice(index, 1);
        });
    }
    loadAllFourniture() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.PIECES_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                if (
                    this.sinisterPec.preliminaryReport == true &&
                    this.confirmationDevisComplementaire
                ) {
                    this.detailsPiecesFournitureCP = subRes.json;
                } else {
                    this.detailsPiecesFourniture = subRes.json;
                }
                if (this.sinisterPec.modificationPrix) {
                    this.detailsPiecesFourniture.forEach((element) => {
                        element.isComplementary = false;
                    });
                }
                this.calculateGlobalFournTtc();
                let objectFourniture = this.detailsPiecesFourniture;
                objectFourniture.forEach((detailsPiecesFourniture) => {
                    let objFourniture = Object.assign(
                        {},
                        detailsPiecesFourniture
                    );
                    this.lastDetailsPieces.push(objFourniture);
                });
                if (this.AvisExpert) {
                    this.DecisionExpertByObser(5, null);
                }
            });

        if (this.modeConfirmation == true || this.isMaJDevis == true) {
            this.detailsPiecesService
                .queryByDevisAndTypeOther(
                    this.quotation.id,
                    TypePiecesDevis.PIECES_FOURNITURE,
                    false
                )
                .subscribe((subRes: ResponseWrapper) => {
                    this.detailsPiecesFourniture1 = subRes.json;
                });
        }
    }

    loadAllFournitureForMP() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotation.id,
                TypePiecesDevis.PIECES_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                //this.detailsPiecesFourniture = subRes.json;
                subRes.json.forEach((element) => {
                    if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire &&
                        this.quotation.statusId == 4
                    ) {
                        this.detailsPiecesFourniture = subRes.json;
                        this.detailsPiecesFournitureCP.push(element);
                    } else if (
                        this.sinisterPec.preliminaryReport == true &&
                        this.confirmationDevisComplementaire
                    ) {
                        this.detailsPiecesFournitureCP.push(element);
                    } else {
                        this.detailsPiecesFourniture.push(element);
                    }
                });

                this.detailsPiecesFourniture.forEach((element) => {
                    if (element.isComplementary == false) {
                    } else {
                        element.isComplementary = true;
                    }
                });

                this.calculateGlobalFournTtc();
            });
    }

    loadAllFournitureTest() {
        this.detailsPiecesService
            .queryByDevisAndType(
                this.quotationMP.originalQuotationId,
                TypePiecesDevis.PIECES_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesFournitureTest = subRes.json;
                let object = this.lastDetails.concat(
                    this.detailsPiecesMO,
                    this.detailsPieces,
                    this.detailsPiecesFourniture,
                    this.detailsPiecesIngredient
                );
                object.forEach((lastDetail) => {
                    let obj = Object.assign({}, lastDetail);
                    this.lastDetailsPieces.push(obj);
                });
            });
    }

    loadAllFournitureForQuotationMP() {
        this.detailsPiecesService
            .queryByQuotationMPAndType(
                this.quotationMP.id,
                TypePiecesDevis.PIECES_FOURNITURE,
                false
            )
            .subscribe((subRes: ResponseWrapper) => {
                this.detailsPiecesFourniture = subRes.json;
                console.log(
                    "testDetailsPiecesFourniture " +
                        this.detailsPiecesFourniture.length
                );
                this.calculateGlobalFournTtc();
            });
    }

    setDefaultDate(): NgbDateStruct {
        var startDate = new Date();
        let startYear = startDate.getFullYear().toString();
        let startMonth = startDate.getMonth() + 1;
        let startDay = "1";
        return this.ngbDateParserFormatter.parse(
            startYear + "-" + startMonth.toString() + "-" + startDay
        );
    }

    onSelectDate(date: NgbDateStruct) {
        if (date != null) {
            this.model = date;
            this.dateString = this.ngbDateParserFormatter.format(date);
        }
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
        return (
            date.getFullYear() +
            "-" +
            this.leftpad(date.getMonth() + 1, 2) +
            "-" +
            this.leftpad(date.getDate(), 2) +
            " " +
            this.leftpad(date.getHours(), 2) +
            ":" +
            this.leftpad(date.getMinutes(), 2) +
            ":" +
            this.leftpad(date.getSeconds(), 2)
        );
    }

    dateAsYYYYMMDD(date): string {
        return (
            date.getFullYear() +
            "-" +
            this.leftpad(date.getMonth() + 1, 2) +
            "-" +
            this.leftpad(date.getDate(), 2)
        );
    }

    nbreMissionByExpert(id) {
        this.sinisterPecService
            .getNbreMissionExpert(id)
            .subscribe((res: any) => {
                this.nbmissionExpert = res.json;
            });
    }

    changeReference(pieces: any) {
        console.log("piece-**-*-*-" + pieces);
        this.vehiclePieceService
            .find(pieces.reference)
            .subscribe((res: VehiclePiece) => {
                console.log("resssss idddd-*-*" + res.id);

                pieces.designationId = res.id;
            });
    }
    changeDesignation(pieces: any) {
        console.log("piece designation-**-*-*-" + pieces);
        this.vehiclePieceService
            .find(pieces.designationId)
            .subscribe((res: VehiclePiece) => {
                console.log("resssss idddd designation-*-*" + res.id);
                pieces.reference = res.id;
            });
    }
}

@Component({
    selector: "jhi-devis-popup",
    template: "",
})
export class DevisPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devisPopupService: DevisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params["id"]) {
                this.devisPopupService.open(
                    DevisDialogComponent as Component,
                    params["id"]
                );
            } else {
                this.devisPopupService.open(DevisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
