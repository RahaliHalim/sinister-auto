import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription, Subject } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager, JhiDateUtils } from 'ng-jhipster';
import { Sinister } from '../sinister/sinister.model';
import { SinisterService } from '../sinister/sinister.service';
import { Delegation, DelegationService } from '../delegation';
import { RaisonPec, RaisonPecService } from '../raison-pec';
import { Governorate, GovernorateService } from '../governorate';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';
import { Principal, ResponseWrapper } from '../../shared';
import { Tiers, TiersService } from '../tiers';
import { SinisterPecService } from '../sinister-pec';
import { ClientService } from '../client/client.service';
import { RefModeGestionService, RefModeGestion } from '../ref-mode-gestion';
import { RefPositionGa, RefPositionGaService } from '../ref-position-ga';
import { RefBareme, RefBaremeService, RefBaremePopupDetailService } from '../ref-bareme';
import { Expert, ExpertService } from '../expert';
import { Partner, PartnerService } from '../partner';
import { ObservationService } from '../observation/observation.service';
import { Observation, TypeObservation } from '../observation';
import { DemandSinisterPecService } from "./demand-sinister-pec.service";
import { PieceJointe, PieceJointeService } from "../piece-jointe";
import { RefPackService, RefPack, Operator, ApecSettings } from '../ref-pack';
import { Client } from '../client';
import { StatusSinister, PrestationPecStep, EtatAccord, DecisionAssure, TypePiecesDevis, TypeInterventionQuotation } from '../../constants/app.constants';
import { Result } from './result.model';
import { Attachment } from '../attachments';
import { SinisterPec } from './sinister-pec.model';
import { RefTypeServiceService } from '../ref-type-service';
import { Agency, AgencyService } from '../agency';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPecPopupService } from './sinister-pec-popup.service';
import { DecisionSinisterPecComponent } from '../sinister-pec/decision-sinister-pec.component';
import { GaDatatable, DecisionPec } from '../../constants/app.constants';
import { ConfirmationDialogService } from '../../shared';
import { PointChoc } from './../sinister-pec/';
import { Reparateur, ReparateurService } from '../reparateur';
import { DateUtils } from '../../utils/date-utils';
import { UserExtraService, UserExtra, PermissionAccess } from '../user-extra';
import { UserPartnerMode } from '../user-partner-mode';
import { ConventionService } from '../../entities/convention/convention.service';
import { RefMotif } from '../ref-motif';
import * as Stomp from 'stompjs';
import { Apec, ApecService } from '../apec';
import { saveAs } from 'file-saver/FileSaver';
import { Convention } from '../convention/convention.model';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { Quotation, QuotationService } from '../quotation';
import { DetailsPiecesService, DetailsPieces } from '../details-pieces';
import { DetailsMo } from '../details-mo';
import { StampDuty } from '../stamp-duty/stamp-duty.model';
import { StampDutyService } from '../stamp-duty/stamp-duty.service';
import { HistoryPopupDetailsPec } from './historyPopupDetailsPec.component';
import { VehiclePiece, VehiclePieceService } from '../vehicle-piece';
import { QuotationPieceAddComponent } from '../devis';
import { RefTypeIntervention, RefTypeInterventionService } from '../ref-type-intervention';
import { VatRateService, VatRate } from '../vat-rate';
import { RefStepPec } from './refStepPec.model';
import { RefStepPecService } from '../ref-step-pec/ref-step-pec.service';
import { ObservationApec } from '../observationApec/observation-apec.model';
import { ObservationApecService } from '../observationApec/observation-apec.service';
import { DomSanitizer } from '@angular/platform-browser';
@Component({
    selector: 'jhi-sinister-reprise-dialog',
    templateUrl: './derogation-pec.component.html'
})
export class DerogationPecComponent implements OnInit {
    contratAssurance: ContratAssurance = new ContratAssurance();
    sinister: Sinister = new Sinister();
    sinisterPec = new SinisterPec();
    isSaving: boolean;
    showAlertTiers: boolean = false;
    success: boolean;
    isCommentError = false;
    isObsModeEdit = false;
    gov: boolean = true;
    dtOptions: any = {};
    refBar: boolean = false;
    nbrMaxTiers: boolean = true;
    reparateurs: Reparateur[] = [];
    reparateursAffecte: Reparateur[] = [];
    otherField: boolean = false;
    nameAgence: boolean = false;
    isSinPecChangeStatus: boolean = false;
    govRep: boolean = true;
    isTierModeEdit = false;

    reasons: RaisonPec[] = [];
    affectReparateur = false;
    PointChocRequired = false;
    annulerAffectReparateur = false;
    garantieDommageVehicule = false;
    garantieDommageCollision = false;
    garantieVolIncendiePartiel = false;
    garantieBrisGlace = false;
    garantieTierceCollision = false;
    garantieTierce = false;
    sysvillesRep: Delegation[];
    sysVille: Delegation;
    experts: Expert[] = [];
    avt: any;
    selectedGouvernorat: number;
    vehiculeassures: VehiculeAssure[];
    sysgouvernorats: Governorate[];
    sysGouvernorat: Governorate;
    sysGouvernoratRep: Governorate;
    assures: Assure[];
    assure: any;
    typeServiceId: any;
    currentAccount: any;
    datePCirculation: any;
    agentCompanyRO: boolean = false;
    debut: any;
    fin: any;
    debut1: any;
    fin1: any;
    edit: any;
    date: any;
    dateD: any;
    reasonRefused = new RaisonPec();
    tiersLength: boolean = true;
    vehicule: VehiculeAssure = new VehiculeAssure();;
    hour: any;
    pars: any;
    creation: any;
    dossierCreated: boolean;
    cloture: any;
    eventSubscriber: Subscription;
    authorities: any[];
    tiersIsActive = false;
    typeSinister: string;
    rmq: any;
    nbmissionExpert: number;
    miseAjour: any;
    userCellule: any;
    prestations: any;
    ville: any;
    gouvernorat: any;
    telephone: any;
    expertObservations = [];
    expertDecisions = [];
    etatsQuotation = [];
    etatsApec = [];
    pecsDecisions = [];
    pecsApprouves = [];
    expertMissionary: boolean = false;
    cancelExpertMissionary: boolean = false;
    reparateur: Reparateur = new Reparateur();
    newFormTiers: boolean = true;
    prestAApprouv: boolean = false;
    sysvilles: Delegation[];
    governorates: Governorate[];
    contacts: any;
    testDateAccident = true;
    isGestionnaireReparation: any;
    vehiculeAssure: VehiculeAssure = new VehiculeAssure();
    isCelluleAssistance: any;
    clientId: any;
    modesDeGestion: any[] = [];
    refpositiongas: RefPositionGa[];
    listGarantiesForModeGestion: any[] = [];
    mode: any;
    test = false;
    garanties: any;
    baremes: RefBareme[];
    compagnie: any;
    agence: any;
    existMode = false;
    observations: Observation[] = [];
    observation = new Observation();
    tiers: Tiers[] = [];
    tier = new Tiers();
    compagnies: any;
    nbrTiersResponsable: number = 0;
    agences: any;
    isvalideDate: any;
    casDeBareme = false;
    minVinNumber;
    counter: any;
    isContractLoaded = false;
    minPssgrNumber = 1;
    nbrVehPattern = "^[1-9]$";
    floatPattern = "^[0-9]*\\.?[0-9]*$";
    piecesJointes: PieceJointe[];
    pieceJointelength: boolean = true;
    isSave: boolean = false;
    BtnSaveValide: boolean = false;
    refBareme = new RefBareme();
    CasBareme = new RefBareme();
    isApecEntry = false;
    refBid: number;
    imgBareme: string;
    idBaremeLoaded: number;
    codeBaremeLoaded: number;
    descriptionBaremeLoaded: String;
    showFrmTiers: boolean = false;
    myDate: any;
    motifs: RefMotif[];
    refStepPecs: RefStepPec[];
    //myDate: any;
    pack: RefPack = new RefPack();
    listModeGestionByPack: any[] = [];
    contratAssuranceDuTiers: ContratAssurance = new ContratAssurance();
    tierIsCompagnie: boolean = false;
    tierRaisonSocial: any;
    agenceTierNom: any;
    compagnieTierNom: any;
    debut2: any;
    fin2: any;
    pecId: any;
    listPositionGa: any[] = [];
    tiersIsAssure: boolean = false;
    tiersIsNotAssure: boolean = false;
    tierContratNumero: String = null;
    client: Client = new Client();
    refagences: Agency[];
    updateFaceAvantDroit = false;
    updateFaceAvantGauche = false;
    updateFaceArriereDroit = false;
    updateFinition = false;
    updateImmatriculation = false;
    updateCompteur = false;
    updateFacture = false;
    updatePhotoReparation = false;
    updateNSerie = false;
    updateFaceArriereGauche = false;
    updateCarteGrise = false;
    devisComplementaire = false;
    devisInitial = false;
    trouveTiers: Tiers = new Tiers();
    assureCherche: Assure = new Assure();
    exist: boolean = false;
    old: boolean = false;
    nbmissionreparateur: any;
    capacite: any;
    expertsAffectation: Expert[] = [];
    expertsMission: Expert[] = [];
    expertsIntial: Expert[] = [];
    reparateurArrivalTime: any;
    pieceJointe: PieceJointe = new PieceJointe();
    constatFiles: File;
    carteGriseFiles: File;
    acteCessionFiles: File;
    chemin: Result = new Result();
    selectedFiles: FileList;
    constatAttachment: Attachment = new Attachment();
    carteGriseAttachment: Attachment = new Attachment();
    acteCessionAttachment: Attachment = new Attachment();
    constatPreview = false;
    carteGrisePreview = false;
    acteCessionPreview = false;
    carteGriseQuotePreview = false;
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    labelFaceAvantDroit: String = "Face Avant Droit";
    labelFaceAvantGauche: String = "Face Avant Gauche";
    labelFaceArriereDroit: String = "Face Arriere Droit";
    labelFaceArriereGauche: String = "Face Arriere Gauche";
    labelFinition: String = "Finition";
    labelNSerie: String = "NSerie";
    labelImmatriculation: String = "Immatriculation";
    labelCompteur: String = "Compteur";
    labelCarteGriseQuote: String = "Carte Grise Quotation";
    amountPattern = '^[0-9]+(\.[0-9]{1,3})?';
    nbrPassgrPattern = '^[1-9]*$';
    attachmentList: Attachment[];
    modeChoisi: RefModeGestion = new RefModeGestion;
    partner: Partner;
    moPiecesF: VehiclePiece[] = [];
    ingredientPieces: VehiclePiece[] = [];
    fourniturePieces: VehiclePiece[] = [];
    filtredPieces: VehiclePiece[] = [];
    serviceTypes: RefTypeService[];
    tierResponsableVerif: boolean = true;
    listModeByConvension: any[] = [];
    showFaceAvantDroitAttachment: boolean = true;
    ajoutNouvelpieceform = false;
    ajoutNouvelpieceformPecPlus = false;
    showFaceAvantGaucheAttachment: boolean = true;
    showFaceArriereDroitAttachment: boolean = true;
    showFaceArriereGaucheAttachment: boolean = true;
    showFinitionAttachment: boolean = true;
    showFaceArriereGauche = false;
    showImmatriculationAttachment: boolean = true;
    extensionImageOriginal: string;
    showCompteurAttachment: boolean = true;
    showPhotoReparationAttachment: boolean = true;
    showFactureAttachment: boolean = true;
    showNSerieAttachment: boolean = true;
    showCarteGriseAttachment: boolean = true;
    listModeByCnvByUser: any[] = [];
    isQuoteEntry = false;
    showFaceAvantDroit = false;
    showFaceAvantGauche = false;
    showFaceArriereDroit = false;
    private ngbModalRef: NgbModalRef;
    vehNmbrIda: boolean = false;
    listeTiersByImmatriculation: Tiers[] = [];
    modeId: any;
    cmpRf: boolean = false;
    deletedpIngredient: boolean = true;
    selectedReparateur: any;
    motifsReopened: RaisonPec[];
    reasonsCanceled: RaisonPec[];
    reasonsRefused: RaisonPec[];
    isCanceled: boolean = false;
    isRefused: boolean = false;
    detailsPieceMo: DetailsPieces = new DetailsPieces();
    isApprouved: boolean = false;
    faceAvantGauchePreview = false;
    reopenPrest: boolean = false;
    isDecided: boolean = false;
    companyId: number;
    isAcceptedWithReserve: boolean = false;
    updatePieceJointe1 = false;
    updatePieceJointe1PecPlus = false;
    piecePreview1 = false;
    piecePreview1PecPlus = false;
    isAcceptedWithChangeStatus: boolean = false;
    motfReopened: boolean = false;
    showMotifAction: boolean = false;
    annulPrest: boolean = false;
    faceAvantDroitFiles: File;
    faceAvantGaucheFiles: File;
    faceArriereDroitFiles: File;
    faceArriereGaucheFiles: File;
    finitionFiles: File;
    nSerieFiles: File;
    immatriculationFiles: File;
    piecesFiles: File[] = [];
    piecesFilesPecPlus: File[] = [];
    compteurFiles: File;
    carteGriseQuoteFiles: File;
    showMsgMotifRqrdCancel: boolean = true;
    showMsgMotifRqrdRefus: boolean = true;
    confirmPrest: boolean = false;
    decisionPrest: boolean = false;
    isSinPecRefusedCanceled: boolean = false;
    isSinPecVerifOrgPrinted: boolean = false;
    isCancPec: boolean = false;
    isRefusPec: boolean = false;
    isConfirmCancPec: boolean = false;
    piecesJointesAttachments: Attachment[] = [];
    moPieces: VehiclePiece[] = [];
    expertObservationsPF = [];
    lettreIDAOuverture = [];
    labelAccordCP: String = "ConstatPreliminaire";
    labelAccordDebloquage: String = "Debloquage";
    labelAccordNormal: String = "AccordDerogation";
    isConfirmRefusPec: boolean = false;
    isSinPecBonSortie: boolean = false;
    showModeToModif: boolean = false;
    faceArriereGauchePreview = false;
    sendPrest: boolean = false;
    affectReparateurWithCapacity: boolean = false;
    pieceFiles1: File;
    pieceFiles1PecPlus: File;
    pointChoc: PointChoc = new PointChoc();
    userExtra: UserExtra;
    usersPartnerModes: UserPartnerMode[];
    testChocLeger = false;
    isCentreExpertise: boolean = false;
    modeEdit = false;
    userExtraCnv: UserExtra = new UserExtra();
    deletedpRechange: boolean = true;
    selectedIdCompagnies: number[] = [];
    containt: boolean = false;
    detailsPiece: DetailsPieces = new DetailsPieces();
    detailsPiecesIngredient: DetailsPieces[] = [];
    detailsPiecesFourniture: DetailsPieces[] = [];
    detailsPiecesMO: DetailsPieces[] = [];
    detailsPieces: DetailsPieces[] = [];
    nbrTiers: number = 0;
    imprimesAttachments: Attachment[] = [];
    piecesAttachments: Attachment[] = [];
    piecesAttachmentsPecPlus: Attachment[] = [];
    apecsValid: Apec[] = [];
    listesApecs: Apec[] = [];
    apecTotale: Apec = new Apec();
    isFromInProgress: boolean = false;
    deleteDetailsMo: boolean = false;
    etatQuotation: number;
    selectedItem: boolean = true;
    showEtatQuotation = false;
    raisonLevesReserves: any;
    faceAvantDroitAttachment: Attachment = new Attachment();
    faceAvantGaucheAttachment: Attachment = new Attachment();
    faceArriereDroitAttachment: Attachment = new Attachment();
    faceArriereGaucheAttachment: Attachment = new Attachment();
    finitionAttachment: Attachment = new Attachment();
    nSerieAttachment: Attachment = new Attachment();
    immatriculationAttachment: Attachment = new Attachment();
    compteurAttachment: Attachment = new Attachment();
    carteGriseQuoteAttachment: Attachment = new Attachment();
    apec: Apec;
    conformedisabled: boolean = false;
    detailsMos: DetailsMo[] = [];
    showConstatAttachment: boolean = true;
    showFinition = false;
    showCarteGriseQuoteAttachment: boolean = true;
    showActeDeCessionAttachment: boolean = true;
    nbrTiersRespMin: number = 0;
    vehiculeContratTiers: VehiculeAssure = new VehiculeAssure();
    tierRespMin: boolean = true;
    assureur: Assure = new Assure();
    convention: Convention = new Convention();
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    userExNotifAgency: UserExtra[] = [];
    listesAssignedToId: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];
    userExNotifChargeAdministrative: UserExtra[] = [];
    userExNotifResponsableAdministrative: UserExtra[] = [];
    testConvention = false;
    showImmatriculation = false;
    showCompteur = false;
    showNSerie = false;
    faceAvantDroitPreview = false;
    apecSettings: ApecSettings[] = [];
    apecSettingsAvenant: ApecSettings[] = [];
    updateConstat = false;
    refOperationTypesMo: RefTypeIntervention[];
    refOperationTypesPeinture: RefTypeIntervention[];
    refOperationTypesFourniture: RefTypeIntervention[];
    updateCarteGriseQuote = false;
    updateActeDeCession = false;
    showPieceAttachment = false;
    apecModifStatus: Apec = new Apec();
    quotation: Quotation = new Quotation();
    piecesAvantReparationAttachments: Attachment[] = [];
    piecesApresReparationAttachments: Attachment[] = [];
    listesQuotations: Quotation[] = [];
    status: any;
    faceArriereDroitPreview = false;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    testService: boolean = false;
    blockAgentForAddPrest = false;
    testModifStatus = false;
    showAlertSuccess = false;
    showAlertSuccessSinister = false;
    observationss: Observation[] = [];
    refPack: RefPack = new RefPack();
    buttonMissExpert: boolean = false;
    extensionImage: string;
    immatriculationPreview = false;
    nomImage: string;
    nSeriePreview = false;
    buttonAffReparateur: boolean = false;
    finitionPreview = false;
    compteurPreview = false;
    labelFacture: String = "Facture";
    labelPhotoReparation: String = "Photo de Reparation";
    factureFiles: File;
    photoReparationFiles: File;
    factureAttachment: Attachment = new Attachment();
    photoReparationAttachment: Attachment = new Attachment();
    facturePreview = false;
    photoReparationPreview = false;
    buttonConfirmAnnul: boolean = true;
    ajoutNouvelpiece = false;
    ajoutNouvelpiecePecPlus = false;
    buttonConfirmRefus: boolean = false;
    labelPiece1: string;
    totalTtc = 0;
    moTotalTtc = 0;
    ingTotalTtc = 0;
    oneTime = false;
    private readonly imageType: string = 'data:image/PNG;base64,';
    private readonly imageTypeJpeg: string = 'data:image/jpeg;base64,';
    fournTotalTtc = 0;
    myDateQuotation: any;
    piecesTotalTtc = 0;
    ttcAmount = 0;
    activeStamp: StampDuty;
    refStepId: number;
    complementaryQuotationMS: Quotation = new Quotation();
    preliminaryReport = false;
    expertDecision: any;
    canCancelMissionementExpert = true;
    pieceAttachment1: Attachment = new Attachment();
    pieceAttachment1PecPlus: Attachment = new Attachment();
    showConstat = false;
    showCarteGrise = false;
    showCarteGriseQuote = false;
    showFacture = false;
    showPhotoReparation = false;
    listVat: VatRate[];
    showActeDeCession = false;
    verif = false;
    observationApec: ObservationApec = new ObservationApec();
    obsApec: ObservationApec = new ObservationApec();
    showResponsible = true;



    constructor(
        private alertService: JhiAlertService,
        private sinisterService: SinisterService,
        private sysGouvernoratService: GovernorateService,
        private sysVilleService: DelegationService,
        private vehiculeAssureService: VehiculeAssureService,
        private contratAssuranceService: ContratAssuranceService,
        private assureService: AssureService,
        private serviceTypeService: RefTypeServiceService,
        private prestationPECService: SinisterPecService,
        private tiersService: TiersService,
        private reparateurService: ReparateurService,
        private refModeGestionService: RefModeGestionService,
        public principal: Principal,
        private route: ActivatedRoute,
        private router: Router,
        private delegationService: DelegationService,
        private raisonPecService: RaisonPecService,
        private siniterPecPopupService: SinisterPecPopupService,
        private eventManager: JhiEventManager,
        private refBaremeService: RefBaremeService,
        private agenceAssuranceService: AgencyService,
        private refCompagnieService: PartnerService,
        private observationService: ObservationService,
        private demandePecService: DemandSinisterPecService,
        private refBaremePopupDetailService: RefBaremePopupDetailService,
        private refPackService: RefPackService,
        private vehiclePieceService: VehiclePieceService,
        private dateUtils: JhiDateUtils,
        private confirmationDialogService: ConfirmationDialogService,
        private expertService: ExpertService,
        private owerDateUtils: DateUtils,
        private userExtraService: UserExtraService,
        private refPositionGaService: RefPositionGaService,
        private conventionService: ConventionService,
        private sinisterPecService: SinisterPecService,
        private apecService: ApecService,
        private quotationService: QuotationService,
        private detailsPiecesService: DetailsPiecesService,
        private stampDutyService: StampDutyService,
        private vatRateService: VatRateService,
        private refTypeInterventionService: RefTypeInterventionService,
        private refStepPecService: RefStepPecService,
        private observationApecService: ObservationApecService,
        private sanitizer: DomSanitizer

    ) {

    }
    ngOnInit() {
        this.sinisterPec.regleModeGestion = false;
        this.sinisterPec.prestAApprouv = false;
        this.myDate = this.dateAsYYYYMMDDHHNNSS(new Date());
        const date1 = new Date(this.myDate);
        if (this.myDate) {
            this.myDate = {
                year: date1.getFullYear(),
                month: date1.getMonth() + 1,
                day: date1.getDate()
            };
        }

        this.cmpRf = false;
        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => {
            this.sysgouvernorats = res.json;
            this.governorates = res.json;
            this.sysGouvernoratRep = res.json;
        });
        this.refModeGestionService.query().subscribe((resMG: ResponseWrapper) => {
            this.listModeByCnvByUser = resMG.json;
        });
        this.refStepPecService.query().subscribe((resSt: ResponseWrapper) => {
            this.refStepPecs = resSt.json;
        });
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = true;
        this.typeServiceId = 11;
        this.casDeBareme = false; // TODO : verifiy utility
        this.userExtraService.findByProfil(6).subscribe((listesCharge: UserExtra[]) => {
            this.userExtraService.findByProfil(7).subscribe((listesChargeBG: UserExtra[]) => {
                this.listesAssignedToId = listesCharge;
                listesChargeBG.forEach(element => {
                    this.listesAssignedToId.push(element);
                });
            });
        });
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => {
                this.sysvilles = res.json;
                this.sysvillesRep = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.sinisterPec.gouvernorat == null) {
            this.sinisterPec.gouvernorat = new Governorate()
        }
        this.serviceTypeService.query().subscribe((res: ResponseWrapper) => {
            this.serviceTypes = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.refCompagnieService.findAllCompaniesWithoutUser().subscribe((res: ResponseWrapper) => { this.compagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.agenceAssuranceService.findAllAgentAssurance().subscribe((res: ResponseWrapper) => {
            this.refagences = res.json;
        });

        this.expertObservations = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 3, label: "A Réparer" },
            { id: 4, label: "A Remplacer" },
            { id: 5, label: "Accordé avec Modification" }
        ];

        this.lettreIDAOuverture = [
            { id: 1, label: "Oui", code: true },
            { id: 2, label: "Non", code: false }
        ];

        this.expertDecisions = [
            { id: 1, label: null },
            { id: 2, label: "Accord pour réparation" },
            { id: 3, label: "Accord pour réparation avec modification" },
            { id: 4, label: "Devis Complementaire Annulé" },
            { id: 5, label: "Circonstance à vérifier, Expertise terrain" },
            { id: 6, label: "Circonstance de l’accident non conforme" },
            { id: 7, label: "Reconstitution" },
            { id: 8, label: "Circonstance conforme OK pour démontage" },
            { id: 9, label: "Expertise terrain" }
        ];

        this.etatsQuotation = [
            { id: 1, label: "En cours" },
            { id: 2, label: "Cloturé" },
            { id: 3, label: "Annulé" }
        ];

        this.etatsApec = [
            { id: 0, label: "Approuver Accord" },
            { id: 6, label: "Validation Accord" },
            { id: 3, label: "Imprimé Accord" },
            { id: 7, label: "Validation participation assuré" },
            { id: 9, label: "Signature de l’Accord" },
            { id: 4, label: "Générer Accord modifié" },
            { id: 13, label: "Génération bon de sortie" },
            { id: 17, label: "Confirmation Devis Complémentaire" },
            { id: 10, label: "En instance de réparation" },
            { id: 20, label: "Accord cloturé" }
        ];

        this.pecsDecisions = [
            { id: 1, label: " ", code: null },
            { id: 2, label: "Accepté", code: "ACCEPTED" },
            { id: 3, label: "Accepté avec réserves", code: "ACC_WITH_RESRV" },
            { id: 4, label: "Accepté avec changement de statut", code: "ACC_WITH_CHANGE_STATUS" },
            { id: 5, label: "Annulé", code: "CANCELED" },
            { id: 6, label: "Refusé", code: "REFUSED" }
        ];

        this.pecsApprouves = [
            { id: 1, label: " ", code: null },
            { id: 2, label: "Approuvé", code: "APPROVE" },
            { id: 3, label: "Approuvé avec modification", code: "APPROVE_WITH_MODIFICATION" }
        ];

        this.expertObservationsPF = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 5, label: "Accordé avec Modification" }
        ];

        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_MO).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesMo = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_PEINTURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesPeinture = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_FOURNITURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesFourniture = res; }, (res: ResponseWrapper) => this.onError(res.json));

        this.vatRateService.query().subscribe((res) => { this.listVat = res.json; });
        /*this.raisonPecService.findAllMotifReopened().subscribe((res) => {
            this.motifsReopened = res.json;

        });
        this.raisonPecService.findAllMotifCanceled().subscribe((res) => {
            this.reasonsCanceled = res.json;

        });
        this.raisonPecService.findAllMotifRefused().subscribe((res) => {
            this.reasonsRefused = res.json;
            this.raisonPecService.find(94).subscribe((res: RaisonPec) => {
                this.reasonsRefused.push(res);
            });
        });*/
        this.expertService.query().subscribe((listExpert: ResponseWrapper) => {
            this.expertsAffectation = listExpert.json;
        });

        this.stampDutyService.findActiveStamp().subscribe((res) => {
            this.activeStamp = res;
            this.quotation.stampDuty = this.activeStamp.amount;
        });
        this.refBaremePopupDetailService.currentmessage.subscribe((idresu) => {
            this.refBid = idresu;
            if (this.oneTime == true) {
                this.LoadBareme(this.refBid);
            }
            this.oneTime = true;
        });

        this.raisonPecService.query().subscribe((res: ResponseWrapper) => {
            this.raisonLevesReserves = res.json;
        });

        this.initializeDemand();

        this.refPositionGaService.query().subscribe((res: ResponseWrapper) => {
            this.listPositionGa = res.json;
        });


    }

    ajoutNouvelpieceJointePecPlus() {
        this.ajoutNouvelpieceformPecPlus = true;
        this.ajoutNouvelpiecePecPlus = false;
    }


    downloadConstatFile() {
        if (this.constatFiles) {
            saveAs(this.constatFiles);
        }
    }

    getPiece(piece: File) {
        if (piece) {
            saveAs(piece);
        }
    }
    downloadCarteGriseFile() {
        if (this.carteGriseFiles) {
            saveAs(this.carteGriseFiles);
        }
    }
    downloadActeCessionFile() {
        if (this.acteCessionFiles) {
            saveAs(this.acteCessionFiles);
        }
    }





    onConstatFileChange(fileInput: any) {
        this.updateConstat = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImage.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.constatAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.constatAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.constatFiles = fileInput.target.files[0];
        this.constatPreview = true;
        console.log(this.constatFiles);
    }
    onCarteGrisFileChange(fileInput: any) {
        this.updateCarteGrise = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImage.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.carteGriseAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.carteGriseAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.carteGriseFiles = fileInput.target.files[0];
        this.carteGrisePreview = true;
        console.log(this.carteGriseFiles);
    }
    onActeCessionFileChange(fileInput: any) {
        this.updateActeDeCession = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImage.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.acteCessionAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.acteCessionAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.acteCessionFiles = fileInput.target.files[0];
        this.acteCessionPreview = true;
        console.log(this.acteCessionFiles);
    }
    LoadBareme(id: number) {
        if (id) {
            this.refBaremeService.find(id).subscribe((baremeRes) => {
                if (baremeRes) {
                    this.CasBareme = baremeRes;
                    this.refBar = true;
                    this.sinisterPec.regleModeGestion = false;
                    this.reglesGestion();
                    if (this.isFromInProgress) {
                        if (!this.sinisterPec.regleModeGestion) {
                            this.sinisterPec.prestAApprouv = false;
                        }
                    }

                }
            });
        }
    }

    receiveRefBareme(rB: RefBareme) {
        this.refBareme = rB;
    }


    /**
     * Initialize dossier information
     */
    initializeDemand() {
        console.log('____________________________________________________________________________________________________________________________________________1');
        this.route.params.subscribe((params) => {
            if (params['id']) {
                this.pecId = params['id'];

                // Get pec prestation
                this.prestationPECService.findPrestationPec(this.pecId).subscribe((pecRes: SinisterPec) => {
                    this.sinisterPec = pecRes;
                    this.refStepId = this.sinisterPec.stepId;
                    this.getAttachmentAvantReparation();
                    this.getPhotoReparation();
                    this.getDateReceptionVehicule();
                    console.log("test--------------------------111111111111111111");
                    this.getDevis(this.sinisterPec);

                    if (this.sinisterPec.pointChoc !== null) {
                        this.pointChoc = this.sinisterPec.pointChoc;
                    }

                    this.changeModeGestion(this.sinisterPec.modeId);

                    this.sinisterService.find(this.sinisterPec.sinisterId).subscribe((sinister: Sinister) => {
                        this.sinister = sinister;
                        this.sinister.nature = 'ACCIDENT';

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
                        if (this.sinister.vehicleId) {
                            this.vehiculeAssureService.find(this.sinister.vehicleId).subscribe((vehicule: VehiculeAssure) => {
                                this.vehicule = vehicule;
                                this.initContrat();
                                if (this.vehicule.datePCirculation) {
                                    this.datePCirculation = this.principal.parseDateJour(this.vehicule.datePCirculation);
                                }

                                if (this.sinisterPec.gouvernoratRepId != null && this.sinisterPec.gouvernoratRepId != undefined) {
                                    this.selectedGouvernorat = this.sinisterPec.gouvernoratRepId;
                                    this.listReparateursByGouvernorat(this.selectedGouvernorat)

                                }
                            });
                        }
                    });
                    if (this.sinisterPec.delegationId != null) {
                        this.findGouvernoratOfVille(this.sinisterPec.delegationId)
                        this.gov = false;
                    }
                    if (this.sinisterPec.villeRepId != null) {
                        this.findGouvernoratOfVille(this.sinisterPec.villeRepId)
                        this.govRep = false;
                    }
                    if (this.sinisterPec.modeId) {
                        if (this.sinisterPec.modeId == 1 || this.sinisterPec.modeId == 2 || this.sinisterPec.modeId == 4) {
                            this.casDeBareme = true;
                        } else {
                            this.casDeBareme = false;
                        }
                    }
                    this.tiersService.findAllByPEC(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                        this.tiers = subRes.json;
                        this.agentCompanyRO = true;
                        if (this.tiers === null) {
                            this.tiers = [];
                        } else if (this.tiers.length > 0) {
                            this.tiers.forEach(tier => {
                                console.log(tier);

                                this.debut2 = this.principal.parseDateJour(tier.debutValidite);
                                this.fin2 = this.principal.parseDateJour(tier.finValidite);
                                const date = new Date(tier.debutValidite);
                                if (tier.debutValidite) {
                                    tier.debutValidite = {
                                        year: date.getFullYear(),
                                        month: date.getMonth() + 1,
                                        day: date.getDate()
                                    };
                                }
                                const date1 = new Date(tier.finValidite);
                                if (tier.finValidite) {
                                    tier.finValidite = {
                                        year: date1.getFullYear(),
                                        month: date1.getMonth() + 1,
                                        day: date1.getDate()
                                    };
                                }
                            });
                        }
                    });
                    if (this.sinisterPec.baremeId) {
                        this.LoadBareme(this.sinisterPec.baremeId);
                    }
                    if (this.sinisterPec.reparateurId != null) {
                        this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                            this.capacite = res.capacite;
                            this.selectedGouvernorat = res.gouvernoratId;
                            if (this.sinisterPec.dateRDVReparation) {
                                console.log(this.sinisterPec.dateRDVReparation);
                                const date = new Date(this.sinisterPec.dateRDVReparation);
                                this.sinisterPec.dateRDVReparation = {
                                    year: date.getFullYear(),
                                    month: date.getMonth() + 1,
                                    day: date.getDate()
                                };
                                this.reparateurArrivalTime = {
                                    hour: date.getHours(),
                                    minute: date.getMinutes()
                                }
                            }
                        })
                    }
                    this.prestationPECService.getAutresPiecesJointes(this.sinisterPec.id).subscribe((resA) => {
                        this.piecesJointesAttachments = resA.json;
                    });
                    this.sinisterPecService.getImprimeAttachments(this.sinisterPec.id).subscribe((resImprime) => {
                        this.imprimesAttachments = resImprime.json;
                    });
                    this.observationService.findByPrestation(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                        this.observationss = subRes.json;
                        if (this.observationss.length == 0) {
                            this.observationss = [];
                        }
                    });
                });
            }
        });
    }

    deletePieceJointesImprimes(attachment: Attachment) {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.sinisterPecService.deleteAttachmentsImprimes(attachment.id).subscribe((resAttR) => {
                        this.imprimesAttachments.forEach((item, index) => {
                            if (item === attachment) this.imprimesAttachments.splice(index, 1);
                        });
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    updateSinister() {

        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    if (this.sinister.id !== null && this.sinister.id !== undefined) {
                        this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;

                            this.sinister.nature = 'ACCIDENT';

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

                            this.showAlertSuccessSinister = true;

                        });
                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    deletePieceJointesPH(attachment: Attachment) {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.sinisterPecService.deleteAttachmentsImprimes(attachment.id).subscribe((resAttR) => {
                        this.piecesAttachments.forEach((item, index) => {
                            if (item === attachment) this.piecesAttachments.splice(index, 1);
                        });
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deletePieceJointesPHPecPlus(attachment: Attachment) {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.sinisterPecService.deleteAttachmentsImprimes(attachment.id).subscribe((resAttR) => {
                        this.piecesAttachmentsPecPlus.forEach((item, index) => {
                            if (item === attachment) this.piecesAttachmentsPecPlus.splice(index, 1);
                        });
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deletePieceJointesAutresPiecesJointes(attachment: Attachment) {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.sinisterPecService.deleteAttachmentsImprimes(attachment.id).subscribe((resAttR) => {
                        this.piecesJointesAttachments.forEach((item, index) => {
                            if (item === attachment) this.piecesJointesAttachments.splice(index, 1);
                        });
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deletePieceJ(pieceJ) {
        this.piecesAttachments.forEach((item, index) => {
            if (item === pieceJ) this.piecesAttachments.splice(index, 1);
        });
    }

    deletePieceJPecPlus(pieceJ) {
        this.piecesAttachmentsPecPlus.forEach((item, index) => {
            if (item === pieceJ) this.piecesAttachmentsPecPlus.splice(index, 1);
        });
    }

    AffectationReparateurOrExpertOrUpdatePec() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.sinisterPec.stepId = this.refStepId;
                    this.sinisterPec.baremeId = this.CasBareme.id;
                    this.sinisterPec.responsabilityRate = false;
                    if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
                        if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                            this.pointChoc.id = this.sinisterPec.pointChoc.id;
                        }
                    }
                    this.sinisterPec.pointChoc = this.pointChoc;
                    const copy: SinisterPec = Object.assign({}, this.sinisterPec);
                    if (this.sinisterPec.dateRDVReparation !== undefined && this.reparateurArrivalTime !== undefined) {
                        if (this.reparateurArrivalTime.hour == '00' || this.reparateurArrivalTime.hour == '24') { this.reparateurArrivalTime.hour = '23'; }
                        this.sinisterPec.dateRDVReparation.hour = this.reparateurArrivalTime.hour;
                        this.sinisterPec.dateRDVReparation.minute = this.reparateurArrivalTime.minute;
                        this.sinisterPec.dateRDVReparation.second = 0;
                        copy.dateRDVReparation = this.owerDateUtils.convertDateTimeToServer(this.sinisterPec.dateRDVReparation, undefined);
                    }
                    this.sinisterPec = copy;
                    if (this.selectedGouvernorat !== null && this.selectedGouvernorat !== undefined) {
                        this.sinisterPec.gouvernoratRepId = this.selectedGouvernorat;
                    }
                    if (this.sinisterPec.vehicleReceiptDate || this.myDateQuotation) { this.sendDateReceptionVehicule(); }
                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resSinPec) => {
                        this.sinisterPec = resSinPec;
                        this.showAlertSuccess = true;
                        if (this.sinisterPec.vehicleReceiptDate) { this.getDateReceptionVehicule(); }
                        if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                            this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                            this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                                this.capacite = res.capacite;
                                this.selectedGouvernorat = res.gouvernoratId;
                                if (this.sinisterPec.dateRDVReparation) {
                                    console.log(this.sinisterPec.dateRDVReparation);
                                    const date = new Date(this.sinisterPec.dateRDVReparation);
                                    this.sinisterPec.dateRDVReparation = {
                                        year: date.getFullYear(),
                                        month: date.getMonth() + 1,
                                        day: date.getDate()
                                    };
                                    this.reparateurArrivalTime = {
                                        hour: date.getHours(),
                                        minute: date.getMinutes()
                                    }
                                }
                            })
                        }
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    addFormQuotation() {
        this.isQuoteEntry = true;
        this.detailsPiecesMO = [];
        this.detailsPiecesIngredient = [];
        this.detailsPieces = [];
        this.detailsPiecesFourniture = [];
        this.quotation = new Quotation();
        this.quotation.stampDuty = this.activeStamp.amount;
        this.apecTotale = new Apec();
        this.showEtatQuotation = false;
        this.isApecEntry = false;
        this.listesApecs = [];
        if (this.sinisterPec.primaryQuotationId !== null && this.sinisterPec.primaryQuotationId !== undefined) {
            this.devisComplementaire = true;
        } else {
            this.devisComplementaire = false;
        }
    }

    genererOrRegenererApec(quotationId: number) {

        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    if (quotationId !== null && quotationId !== undefined) {
                        this.sinisterPec.stepId = this.refStepId;
                        this.sinisterPec.baremeId = this.CasBareme.id;
                        this.sinisterPec.responsabilityRate = false;
                        if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
                            if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                                this.pointChoc.id = this.sinisterPec.pointChoc.id;
                            }
                        }
                        this.sinisterPec.pointChoc = this.pointChoc;
                        const copy: SinisterPec = Object.assign({}, this.sinisterPec);
                        if (this.sinisterPec.dateRDVReparation !== undefined && this.reparateurArrivalTime !== undefined) {
                            if (this.reparateurArrivalTime.hour == '00' || this.reparateurArrivalTime.hour == '24') { this.reparateurArrivalTime.hour = '23'; }
                            this.sinisterPec.dateRDVReparation.hour = this.reparateurArrivalTime.hour;
                            this.sinisterPec.dateRDVReparation.minute = this.reparateurArrivalTime.minute;
                            this.sinisterPec.dateRDVReparation.second = 0;
                            copy.dateRDVReparation = this.owerDateUtils.convertDateTimeToServer(this.sinisterPec.dateRDVReparation, undefined);
                        }
                        this.sinisterPec = copy;
                        if (this.selectedGouvernorat !== null && this.selectedGouvernorat !== undefined) {
                            this.sinisterPec.gouvernoratRepId = this.selectedGouvernorat;
                        }
                        if (this.sinisterPec.vehicleReceiptDate || this.myDateQuotation) { this.sendDateReceptionVehicule(); }
                        this.apecService.generateAccordPrisCharge(this.sinisterPec, quotationId, true, 3, this.labelAccordNormal).subscribe((resPdf) => {
                            this.apecService.findByQuotation(quotationId).subscribe((apecDerog: Apec) => {
                                this.observationApec.apecId = apecDerog.id;
                                this.observationApecService.create(this.observationApec).subscribe((resSave) => {
                                    this.observationApec = resSave;
                                    this.isQuoteEntry = false;
                                    this.isApecEntry = false;
                                    this.detailsPiecesMO = [];
                                    this.detailsPiecesIngredient = [];
                                    this.detailsPieces = [];
                                    this.detailsPiecesFourniture = [];
                                    this.quotation = new Quotation();
                                    this.quotation.stampDuty = this.activeStamp.amount;
                                    this.apecTotale = new Apec();
                                    this.showAlertSuccess = true;
                                    if (this.sinisterPec.vehicleReceiptDate) { this.getDateReceptionVehicule(); }
                                    if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                                        this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                                        this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                                            this.capacite = res.capacite;
                                            this.selectedGouvernorat = res.gouvernoratId;
                                            if (this.sinisterPec.dateRDVReparation) {
                                                console.log(this.sinisterPec.dateRDVReparation);
                                                const date = new Date(this.sinisterPec.dateRDVReparation);
                                                this.sinisterPec.dateRDVReparation = {
                                                    year: date.getFullYear(),
                                                    month: date.getMonth() + 1,
                                                    day: date.getDate()
                                                };
                                                this.reparateurArrivalTime = {
                                                    hour: date.getHours(),
                                                    minute: date.getMinutes()
                                                }
                                            }
                                        })
                                    }
                                });
                            });
                        });
                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }


    saveApec() {

        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.listesApecs = [];
                    this.apecTotale.decriptionObservation = "AccordModifieDerogation";
                    this.apecService.update(this.apecTotale).subscribe((resApecTotale) => {
                        this.apecTotale = resApecTotale;
                        this.listesApecs.push(this.apecTotale);
                        this.listesApecs.forEach(elementApec => {
                            if (elementApec.isComplementaire == true) { elementApec.typeApecString = 'Accord complementaire'; } else { elementApec.typeApecString = 'Accord initial'; }
                            if (elementApec.etat == 0) { elementApec.etatApecString = 'Approuver Accord'; }
                            else if (elementApec.etat == 6) { elementApec.etatApecString = 'Validation Accord'; }
                            else if (elementApec.etat == 3) { elementApec.etatApecString = 'Imprimé Accord'; }
                            else if (elementApec.etat == 7) { elementApec.etatApecString = 'Validation participation assuré'; }
                            else if (elementApec.etat == 9) { elementApec.etatApecString = 'Signature de l’Accord'; }
                            else if (elementApec.etat == 4) { elementApec.etatApecString = 'Générer Accord modifié'; }
                            else if (elementApec.etat == 13) { elementApec.etatApecString = 'Génération bon de sortie'; }
                            else if (elementApec.etat == 17) { elementApec.etatApecString = 'Confirmation Devis Complémentaire'; }
                            else if (elementApec.etat == 10) { elementApec.etatApecString = 'En instance de réparation'; }
                            else if (elementApec.etat == 20) { elementApec.etatApecString = 'Accord cloturé'; }
                            else { elementApec.etatApecString = ''; }
                        });
                        this.apecTotale = new Apec();
                        this.isApecEntry = false;
                        this.showAlertSuccess = true;
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    onPieceFileChange1PecPlus(fileInput: any) {
        this.updatePieceJointe1PecPlus = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.pieceAttachment1PecPlus.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.pieceAttachment1PecPlus.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.pieceFiles1PecPlus = fileInput.target.files[0];
        this.piecePreview1PecPlus = true;
    }

    cancelApec() {
        this.apecTotale = new Apec();
        this.isApecEntry = false;
    }

    deleteApec(apecId) {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.apecService.delete(apecId).subscribe((apecDelete) => {
                        this.listesApecs = [];
                        this.apecTotale = new Apec();
                        this.isApecEntry = false;
                        this.showAlertSuccess = true;
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteQuotation(quote: Quotation) {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    if (quote.id == this.sinisterPec.primaryQuotationId) {
                        const copy: SinisterPec = Object.assign({}, this.sinisterPec);
                        if (this.sinisterPec.dateRDVReparation !== undefined && this.reparateurArrivalTime !== undefined) {
                            if (this.reparateurArrivalTime.hour == '00' || this.reparateurArrivalTime.hour == '24') { this.reparateurArrivalTime.hour = '23'; }
                            this.sinisterPec.dateRDVReparation.hour = this.reparateurArrivalTime.hour;
                            this.sinisterPec.dateRDVReparation.minute = this.reparateurArrivalTime.minute;
                            this.sinisterPec.dateRDVReparation.second = 0;
                            copy.dateRDVReparation = this.owerDateUtils.convertDateTimeToServer(this.sinisterPec.dateRDVReparation, undefined);
                        }
                        this.sinisterPec = copy;
                        if (this.selectedGouvernorat !== null && this.selectedGouvernorat !== undefined) {
                            this.sinisterPec.gouvernoratRepId = this.selectedGouvernorat;
                        }
                        if (this.sinisterPec.vehicleReceiptDate || this.myDateQuotation) { this.sendDateReceptionVehicule(); }
                        this.sinisterPec.primaryQuotationId = null;
                        this.sinisterPec.primaryQuotation = null;
                        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resSinPec) => {
                            this.sinisterPec = resSinPec;
                            this.detailsPiecesService.deleteByQuery(quote.id).subscribe((response) => {
                                this.detailsPiecesService.deleteByQuotationId(quote.id).subscribe((responseApec) => {
                                    this.listesApecs = [];
                                    this.apecTotale = new Apec();
                                    this.isApecEntry = false;
                                    this.quotationService.delete(quote.id).subscribe((responseDeleteQuote) => {
                                        this.showEtatQuotation = true;
                                        this.listesApecs = [];
                                        this.detailsPiecesMO = [];
                                        this.detailsPiecesIngredient = [];
                                        this.detailsPieces = [];
                                        this.detailsPiecesFourniture = [];
                                        this.quotation = new Quotation();
                                        this.quotation.stampDuty = this.activeStamp.amount;
                                        this.isApecEntry = false;
                                        this.prestationPECService.findPrestationPec(this.pecId).subscribe((pecRes: SinisterPec) => {
                                            this.sinisterPec = pecRes;
                                            if (this.sinisterPec.vehicleReceiptDate) { this.getDateReceptionVehicule(); }
                                            if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                                                this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                                                this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                                                    this.capacite = res.capacite;
                                                    this.selectedGouvernorat = res.gouvernoratId;
                                                    if (this.sinisterPec.dateRDVReparation) {
                                                        console.log(this.sinisterPec.dateRDVReparation);
                                                        const date = new Date(this.sinisterPec.dateRDVReparation);
                                                        this.sinisterPec.dateRDVReparation = {
                                                            year: date.getFullYear(),
                                                            month: date.getMonth() + 1,
                                                            day: date.getDate()
                                                        };
                                                        this.reparateurArrivalTime = {
                                                            hour: date.getHours(),
                                                            minute: date.getMinutes()
                                                        }
                                                    }
                                                })
                                            }
                                            this.listesQuotations.forEach((item, index) => {
                                                if (item === quote) this.listesQuotations.splice(index, 1);
                                            });
                                            this.showAlertSuccess = true;
                                        });

                                    });

                                });

                            });
                        });
                    } else {
                        this.detailsPiecesService.deleteByQuery(quote.id).subscribe((response) => {
                            this.detailsPiecesService.deleteByQuotationId(quote.id).subscribe((responseApec) => {
                                this.listesApecs = [];
                                this.apecTotale = new Apec();
                                this.isApecEntry = false;
                                this.quotationService.delete(quote.id).subscribe((responseDeleteQuote) => {
                                    this.showEtatQuotation = true;
                                    this.listesApecs = [];
                                    this.detailsPiecesMO = [];
                                    this.detailsPiecesIngredient = [];
                                    this.detailsPieces = [];
                                    this.detailsPiecesFourniture = [];
                                    this.quotation = new Quotation();
                                    this.quotation.stampDuty = this.activeStamp.amount;
                                    this.isApecEntry = false;
                                    this.prestationPECService.findPrestationPec(this.pecId).subscribe((pecRes: SinisterPec) => {
                                        this.sinisterPec = pecRes;
                                        if (this.sinisterPec.vehicleReceiptDate) { this.getDateReceptionVehicule(); }
                                        if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                                            this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                                            this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                                                this.capacite = res.capacite;
                                                this.selectedGouvernorat = res.gouvernoratId;
                                                if (this.sinisterPec.dateRDVReparation) {
                                                    console.log(this.sinisterPec.dateRDVReparation);
                                                    const date = new Date(this.sinisterPec.dateRDVReparation);
                                                    this.sinisterPec.dateRDVReparation = {
                                                        year: date.getFullYear(),
                                                        month: date.getMonth() + 1,
                                                        day: date.getDate()
                                                    };
                                                    this.reparateurArrivalTime = {
                                                        hour: date.getHours(),
                                                        minute: date.getMinutes()
                                                    }
                                                }
                                            })
                                        }
                                        this.listesQuotations.forEach((item, index) => {
                                            if (item === quote) this.listesQuotations.splice(index, 1);
                                        });
                                        this.showAlertSuccess = true;
                                    });

                                });

                            });

                        });
                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    //recéption vehicule (change etat d'interface selon etat de choc )
    changeNatureReparation(etat) {
        if (etat == false) {
            this.sinisterPec.disassemblyRequest = true;
            this.sinisterPec.lightShock = false;
        }
        else if (etat == true) {
            this.sinisterPec.disassemblyRequest = false;
            this.sinisterPec.lightShock = true;
        }
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


    saveQuotation() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                for (let i = 0; i < this.detailsPiecesMO.length; i++) {
                    this.detailsPiecesMO[i].isMo = true;
                }
                let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                listPieces.forEach(listPieceDetails => {
                    listPieceDetails.isModified = false;
                });
                this.quotation.listPieces = listPieces;
                if (this.quotation.preliminaryReport) { this.sinisterPec.oldStep = 20; }
                if (this.quotation.id) {
                    if (this.devisComplementaire) {
                        if (this.etatQuotation == 1) {
                            this.quotation.statusId = 4;
                            this.quotation.isConfirme = true;
                        } else if (this.etatQuotation == 2) {
                            this.quotation.fromSignature = true;
                            this.quotation.isConfirme = true;
                        } else {
                            this.quotation.fromSignature = true;
                            this.quotation.isConfirme = false;
                        }
                    } else {
                        this.quotation.statusId = 4;
                    }
                } else {
                    this.quotation.statusId = 4;
                }
                if (listPieces.length > 0) { this.testChocLeger = true; } else { this.testChocLeger = false; }
                if (this.testChocLeger == true) {
                    this.sinisterPec.quotation = this.quotation;
                }
                if (this.quotation.id) { this.modeEdit = true; } else { this.modeEdit = false; }
                this.sinisterPec.stepId = this.refStepId;
                this.sinisterPec.baremeId = this.CasBareme.id;
                this.sinisterPec.responsabilityRate = false;
                if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
                    if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                        this.pointChoc.id = this.sinisterPec.pointChoc.id;
                    }
                }
                this.sinisterPec.pointChoc = this.pointChoc;
                const copy: SinisterPec = Object.assign({}, this.sinisterPec);
                if (this.sinisterPec.dateRDVReparation !== undefined && this.reparateurArrivalTime !== undefined) {
                    if (this.reparateurArrivalTime.hour == '00' || this.reparateurArrivalTime.hour == '24') { this.reparateurArrivalTime.hour = '23'; }
                    this.sinisterPec.dateRDVReparation.hour = this.reparateurArrivalTime.hour;
                    this.sinisterPec.dateRDVReparation.minute = this.reparateurArrivalTime.minute;
                    this.sinisterPec.dateRDVReparation.second = 0;
                    copy.dateRDVReparation = this.owerDateUtils.convertDateTimeToServer(this.sinisterPec.dateRDVReparation, undefined);
                }
                this.sinisterPec = copy;
                if (this.selectedGouvernorat !== null && this.selectedGouvernorat !== undefined) {
                    this.sinisterPec.gouvernoratRepId = this.selectedGouvernorat;
                }
                this.sinisterPec.historyAvisExpert = "DerogationQuotation";
                if (this.sinisterPec.vehicleReceiptDate || this.myDateQuotation) { this.sendDateReceptionVehicule(); }
                if (this.testChocLeger == true) {
                    this.sinisterPecService.updatePecForQuotation(this.sinisterPec, this.modeEdit).subscribe((resQuote) => {
                        this.sinisterPec = resQuote;
                        if (this.sinisterPec.vehicleReceiptDate) { this.getDateReceptionVehicule(); }
                        this.isQuoteEntry = false;
                        this.detailsPiecesMO = [];
                        this.detailsPiecesIngredient = [];
                        this.detailsPieces = [];
                        this.detailsPiecesFourniture = [];
                        this.quotation = new Quotation();
                        this.quotation.stampDuty = this.activeStamp.amount;
                        this.showEtatQuotation = false;
                        this.listesQuotations = [];
                        this.getDevis(this.sinisterPec);
                        this.showAlertSuccess = true;
                        if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                            this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                            this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                                this.capacite = res.capacite;
                                this.selectedGouvernorat = res.gouvernoratId;
                                if (this.sinisterPec.dateRDVReparation) {
                                    console.log(this.sinisterPec.dateRDVReparation);
                                    const date = new Date(this.sinisterPec.dateRDVReparation);
                                    this.sinisterPec.dateRDVReparation = {
                                        year: date.getFullYear(),
                                        month: date.getMonth() + 1,
                                        day: date.getDate()
                                    };
                                    this.reparateurArrivalTime = {
                                        hour: date.getHours(),
                                        minute: date.getMinutes()
                                    }
                                }
                            })
                        }
                    });
                } else {
                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resQuote) => {
                        this.sinisterPec = resQuote;
                        if (this.sinisterPec.vehicleReceiptDate) { this.getDateReceptionVehicule(); }
                        this.isQuoteEntry = false;
                        this.detailsPiecesMO = [];
                        this.detailsPiecesIngredient = [];
                        this.detailsPieces = [];
                        this.detailsPiecesFourniture = [];
                        this.quotation = new Quotation();
                        this.quotation.stampDuty = this.activeStamp.amount;
                        this.showEtatQuotation = false;
                        this.listesQuotations = [];
                        this.getDevis(this.sinisterPec);
                        this.showAlertSuccess = true;
                        if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                            this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                            this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                                this.capacite = res.capacite;
                                this.selectedGouvernorat = res.gouvernoratId;
                                if (this.sinisterPec.dateRDVReparation) {
                                    console.log(this.sinisterPec.dateRDVReparation);
                                    const date = new Date(this.sinisterPec.dateRDVReparation);
                                    this.sinisterPec.dateRDVReparation = {
                                        year: date.getFullYear(),
                                        month: date.getMonth() + 1,
                                        day: date.getDate()
                                    };
                                    this.reparateurArrivalTime = {
                                        hour: date.getHours(),
                                        minute: date.getMinutes()
                                    }
                                }
                            })
                        }
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    cancelQuotation() {
        this.isQuoteEntry = false;
        this.detailsPiecesMO = [];
        this.detailsPiecesIngredient = [];
        this.detailsPieces = [];
        this.detailsPiecesFourniture = [];
        this.quotation = new Quotation();
        this.quotation.stampDuty = this.activeStamp.amount;
        this.showEtatQuotation = false;
    }

    // send date reception vehicule
    sendDateReceptionVehicule() {
        if (this.sinisterPec.vehicleReceiptDate && !this.sinisterPec.receptionVehicule) {
            this.sinisterPec.vehicleReceiptDate.hour = '00';
            this.sinisterPec.vehicleReceiptDate.minute = '00';
            this.sinisterPec.vehicleReceiptDate.second = 0;
            this.sinisterPec.vehicleReceiptDate = this.owerDateUtils.convertDateTimeToServer(this.sinisterPec.vehicleReceiptDate, undefined);
        }
        else {
            const dateUp = new Date(this.myDateQuotation);
            this.sinisterPec.vehicleReceiptDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUp);
        }

    }

    // get date reception vehicule
    getDateReceptionVehicule() {
        if (this.sinisterPec.vehicleReceiptDate) {
            if (this.sinisterPec.vehicleReceiptDate && !this.sinisterPec.receptionVehicule) {
                const date = new Date(this.sinisterPec.vehicleReceiptDate);
                this.sinisterPec.vehicleReceiptDate = {
                    year: date.getFullYear(),
                    month: date.getMonth() + 1,
                    day: date.getDate()
                };
            }
            else { this.myDateQuotation = this.sinisterPec.vehicleReceiptDate; }
        }
    }

    dateQuotationNow() {
        this.myDateQuotation = this.dateAsYYYYMMDDHHNNSS(new Date());
    }


    getAttachmentAvantReparation() {
        this.showActeDeCession = true;
        this.showCarteGrise = true;
        this.showConstat = true;
        this.showActeDeCessionAttachment = false;
        this.showCarteGriseAttachment = false;
        this.showConstatAttachment = false;
        this.sinisterPecService.getPhotoAvantReparationAttachments("quotation", this.sinisterPec.id).subscribe((resAttt) => {
            this.piecesAvantReparationAttachments = resAttt.json;
            if (this.piecesAvantReparationAttachments.length !== 0) {
                this.showAttachments();
            }
        });
        this.sinisterPecService.getPhotoAvantReparationAttachments("facture", this.sinisterPec.id).subscribe((resAprestt) => {
            this.piecesApresReparationAttachments = resAprestt.json;
            if (this.piecesApresReparationAttachments.length !== 0) {
                this.showFacture = true;
                this.showFactureAttachment = false;
                this.showPhotoReparation = true;
                this.showPhotoReparationAttachment = false;
            }
        });


    }

    closeAlert() {
        this.showAlertSuccess = false;
    }

    closeAlertSinister() {
        this.showAlertSuccessSinister = false;
    }

    getPhotoReparation() {
        this.piecesAttachments = [];
        this.sinisterPecService.getPhotoReparationAttachments(this.sinisterPec.id).subscribe((resImprime) => {
            this.piecesAttachments = resImprime.json;
            if (this.piecesAttachments.length !== 0) {
                this.showPieceAttachment = true;
            }

        });


    }

    getImage(refB: RefBareme) {
        if (refB.attachmentName == 'png' || refB.attachmentName == 'PNG') {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageType + refB.attachment64);
        } else {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageTypeJpeg + refB.attachment64);
        }
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
        this.showCarteGriseQuote = true;
        this.showFaceAvantDroitAttachment = false;
        this.showFaceArriereDroitAttachment = false;
        this.showFaceAvantGaucheAttachment = false;
        this.showFaceArriereGaucheAttachment = false;
        this.showFinitionAttachment = false;
        this.showNSerieAttachment = false;
        this.showCompteurAttachment = false;
        this.showImmatriculationAttachment = false;
        this.showCarteGriseQuoteAttachment = false;
    }

    saveAttachmentsPhotoAvantReparation() {

        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    //Face Avant Droit

                    if (this.faceAvantDroitFiles !== null && this.updateFaceAvantDroit == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.faceAvantDroitFiles, this.labelFaceAvantDroit, this.faceAvantDroitAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.faceAvantDroitAttachment = res;
                            this.updateFaceAvantDroit = false;
                            this.showFaceAvantDroit = true;
                            this.faceAvantDroitPreview = false;
                            this.showFaceAvantDroitAttachment = false;
                            this.faceAvantDroitAttachment = new Attachment();
                            this.faceAvantDroitAttachment.name = null;
                            this.faceAvantDroitFiles = null;
                        });
                    }

                    //Carte Grise

                    if (this.carteGriseQuoteFiles !== null && this.updateCarteGriseQuote == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.carteGriseQuoteFiles, this.labelCarteGriseQuote, this.carteGriseQuoteAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.carteGriseQuoteAttachment = res;
                            this.updateCarteGriseQuote = false;
                            this.showCarteGriseQuote = true;
                            this.carteGriseQuotePreview = false;
                            this.showCarteGriseQuoteAttachment = false;
                            this.carteGriseQuoteAttachment = new Attachment();
                            this.carteGriseQuoteAttachment.name = null;
                            this.carteGriseQuoteFiles = null;
                        });
                    }

                    //Face Avant Gauche

                    if (this.faceAvantGaucheFiles !== null && this.updateFaceAvantGauche == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.faceAvantGaucheFiles, this.labelFaceAvantGauche, this.faceAvantGaucheAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.faceAvantGaucheAttachment = res;
                            this.updateFaceAvantGauche = false;
                            this.showFaceAvantGauche = true;
                            this.faceAvantGauchePreview = false;
                            this.showFaceAvantGaucheAttachment = false;
                            this.faceAvantGaucheAttachment = new Attachment();
                            this.faceAvantGaucheAttachment.name = null;
                            this.faceAvantGaucheFiles = null;

                        });
                    }

                    //Face Arriere Droit

                    if (this.faceArriereDroitFiles !== null && this.updateFaceArriereDroit == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.faceArriereDroitFiles, this.labelFaceArriereDroit, this.faceArriereDroitAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.faceArriereDroitAttachment = res;
                            this.updateFaceArriereDroit = false;
                            this.showFaceArriereDroit = true;
                            this.faceArriereDroitPreview = false;
                            this.showFaceArriereDroitAttachment = false;
                            this.faceArriereDroitAttachment = new Attachment();
                            this.faceArriereDroitAttachment.name = null;
                            this.faceArriereDroitFiles = null;
                        });
                    }

                    //Face Arriere Gauche

                    if (this.faceArriereGaucheFiles !== null && this.updateFaceArriereGauche == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.faceArriereGaucheFiles, this.labelFaceArriereGauche, this.faceArriereGaucheAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.faceArriereGaucheAttachment = res;
                            this.updateFaceArriereGauche = false;
                            this.showFaceArriereGauche = true;
                            this.faceArriereGauchePreview = false;
                            this.showFaceArriereGaucheAttachment = false;
                            this.faceArriereGaucheAttachment = new Attachment();
                            this.faceArriereGaucheAttachment.name = null;
                            this.faceArriereGaucheFiles = null;

                        });
                    }

                    //Finition

                    if (this.finitionFiles !== null && this.updateFinition == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.finitionFiles, this.labelFinition, this.finitionAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.finitionAttachment = res;
                            this.updateFinition = false;
                            this.showFinition = true;
                            this.finitionPreview = false;
                            this.showFinitionAttachment = false;
                            this.finitionAttachment = new Attachment();
                            this.finitionAttachment.name = null;
                            this.finitionFiles = null;
                        });
                    }

                    //NSerie

                    if (this.nSerieFiles !== null && this.updateNSerie == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.nSerieFiles, this.labelNSerie, this.nSerieAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.nSerieAttachment = res;
                            this.updateNSerie = false;
                            this.showNSerie = true;
                            this.nSeriePreview = false;
                            this.showNSerieAttachment = false;
                            this.nSerieAttachment = new Attachment();
                            this.nSerieAttachment.name = null;
                            this.nSerieFiles = null;

                        });
                    }

                    //Immatriculation

                    if (this.immatriculationFiles !== null && this.updateImmatriculation == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.immatriculationFiles, this.labelImmatriculation, this.immatriculationAttachment.name,'quotation').subscribe((res: Attachment) => {
                            this.immatriculationAttachment = res;
                            this.updateImmatriculation = false;
                            this.showImmatriculation = true;
                            this.immatriculationPreview = false;
                            this.showImmatriculationAttachment = false;
                            this.immatriculationAttachment = new Attachment();
                            this.immatriculationAttachment.name = null;
                            this.immatriculationFiles = null;
                        });
                    }

                    //Compteur

                    if (this.compteurFiles !== null && this.updateCompteur == true) {
                        this.sinisterPecService.saveAttachmentsQuotationNw(this.sinisterPec.id, this.compteurFiles, this.labelCompteur, this.compteurAttachment.name, 'quotation').subscribe((res: Attachment) => {
                            this.compteurAttachment = res;
                            this.updateCompteur = false;
                            this.showCompteur = true;
                            this.compteurPreview = false;
                            this.showCompteurAttachment = false;
                            this.compteurAttachment = new Attachment();
                            this.compteurAttachment.name = null;
                            this.compteurFiles = null;

                        });
                    }

                    if (this.factureFiles !== null && this.updateFacture == true) {
                        this.sinisterPecService.saveAttachmentsFactureNw(this.sinisterPec.id, this.factureFiles, this.labelFacture, this.factureAttachment.name).subscribe((res: Attachment) => {
                            this.factureAttachment = res;
                            this.updateFacture = false;
                            this.showFacture = true;
                            this.facturePreview = false;
                            this.showFactureAttachment = false;
                            this.factureAttachment = new Attachment();
                            this.factureAttachment.name = null;
                            this.factureFiles = null;
                        });

                    }

                    if (this.photoReparationFiles !== null && this.updatePhotoReparation == true) {
                        this.sinisterPecService.saveAttachmentsFactureNw(this.sinisterPec.id, this.photoReparationFiles, this.labelPhotoReparation, this.photoReparationAttachment.name).subscribe((res: Attachment) => {
                            this.photoReparationAttachment = res;
                            this.updatePhotoReparation = false;
                            this.showPhotoReparation = true;
                            this.photoReparationPreview = false;
                            this.showPhotoReparationAttachment = false;
                            this.photoReparationAttachment = new Attachment();
                            this.photoReparationAttachment.name = null;
                            this.photoReparationFiles = null;
                        });
                    }

                    this.savePhotoReparation();

                    this.showAlertSuccess = true;
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    savePhotoReparation() {
        this.piecesAttachments.forEach(pieceAttFile => {
            pieceAttFile.creationDate = null;
            if (pieceAttFile.pieceFile !== null && this.updatePieceJointe1 == true && (pieceAttFile.id == null || pieceAttFile.id == undefined)) {
                this.sinisterPecService.saveAttachmentsPiecePhotoReparation(this.sinisterPec.id, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.name, 'PHOTOREPARATION').subscribe((res: Attachment) => {
                    //this.alertService.success('auxiliumApp.sinisterPec.createdImprime',null,null);
                    this.getPhotoReparation();
                });
            }
        });

    }

    ajoutPiece1PecPlus() {
        this.pieceAttachment1PecPlus.type = this.labelPiece1;
        this.pieceAttachment1PecPlus.originalFr = 'Oui';
        this.pieceAttachment1PecPlus.note = ' ';
        this.pieceAttachment1PecPlus.creationDateP = this.dateAsYYYYMMDDHHNNSS(new Date());
        this.pieceAttachment1PecPlus.creationDate = new Date();
        this.pieceAttachment1PecPlus.pieceFile = this.pieceFiles1PecPlus;
        this.piecesFilesPecPlus.push(this.pieceFiles1PecPlus);
        console.log(this.piecesFilesPecPlus[0]);
        this.piecesAttachmentsPecPlus.push(this.pieceAttachment1PecPlus);
        this.pieceAttachment1PecPlus = new Attachment();
        this.pieceAttachment1PecPlus.original = true;
        this.labelPiece1 = undefined;
        this.selectedItem = true;
        this.piecePreview1PecPlus = false;
        this.ajoutNouvelpieceformPecPlus = false;
    }

    downloadPieceFile1() {
        if (this.pieceFiles1) {
            saveAs(this.pieceFiles1);
        }
    }

    downloadPieceFile1PecPlus() {
        if (this.pieceFiles1PecPlus) {
            saveAs(this.pieceFiles1PecPlus);
        }
    }

    downloadPieceFile(pieceFileAttachment: File) {
        if (pieceFileAttachment) {
            saveAs(pieceFileAttachment);
        }
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

    /**
     * Initialize contract information
     */
    initContrat() {
        this.contratAssurance = new ContratAssurance();
        if (this.sinister.vehicleRegistration) {

            this.contratAssuranceService.find(this.sinister.contractId).subscribe((contractRes: ContratAssurance) => {
                this.contratAssurance = contractRes;
                this.debut = this.principal.parseDateJour(this.contratAssurance.debutValidite);
                this.fin = this.principal.parseDateJour(this.contratAssurance.finValidite);
                if (this.contratAssurance.agenceId) {
                    this.agenceAssuranceService.find(this.contratAssurance.agenceId).subscribe((agenceRes: Agency) => {
                        this.agence = agenceRes.name;
                        if (agenceRes.partnerId) {
                            this.refCompagnieService.find(agenceRes.partnerId).subscribe((compagnieRes: Partner) => {
                                this.compagnie = compagnieRes.companyName;
                            });
                        }
                    });
                }
                this.sinister.vehicleId = this.vehicule.id;
                this.isContractLoaded = false;
                if (this.contratAssurance) {
                    this.isContractLoaded = true;
                    if (this.contratAssurance.clientId) {
                        // List modes de gestion by client
                        this.refModeGestionService.findModesGestionByClient(this.contratAssurance.clientId).subscribe((res: ResponseWrapper) => {
                            this.modesDeGestion = res.json;
                        }, (res: ResponseWrapper) => this.onError(res.json));
                    }

                    this.assureService.find(this.vehicule.insuredId).subscribe((assureRes: Assure) => {
                        this.assureur = assureRes;
                        this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                        this.telephone = assureRes.premTelephone;
                        this.contratAssurance.adressePhysique = assureRes.adresse;
                        if (assureRes.delegationId) {
                            this.sysVilleService.find(assureRes.delegationId).subscribe((resVille: Delegation) => {
                                this.ville = resVille;
                                this.contratAssurance.villeLibelle = resVille.label;
                                this.sysGouvernoratService.find(resVille.governorateId).subscribe((resGouv: Governorate) => {
                                    this.gouvernorat = resGouv.label;
                                    this.contratAssurance.govLibelle = resGouv.label;
                                });
                            })
                        }
                    });
                }
            });
        }
    }

    nbreMissionByReparateur(id) {
        this.affectReparateurWithCapacity = false;
        this.prestationPECService.getNbreMissionReparator(id).subscribe((res: any) => {
            this.nbmissionreparateur = res.json;
            this.reparateurService.find(id).subscribe((res: Reparateur) => {
                this.capacite = res.capacite;
            })
        })
    }
    trimRegistrationNumber() {
        const str = this.sinister.vehicleRegistration.replace(/\s/g, "");
        this.sinister.vehicleRegistration = str.toUpperCase();
    }
    trimRegistrationNumberTiers() {
        const strc = this.tier.immatriculation.replace(/\s/g, "");
        this.tier.immatriculation = strc.toUpperCase();
    }
    listAgenceByCompagnie(id) {
        this.otherField = false;
        this.nameAgence = false;
        //this.refagences = [];


        this.agenceAssuranceService.findAllByPartnerWithoutFiltrage(id).subscribe((res: ResponseWrapper) => {
            this.refagences = res.json;
            if (res.json && res.json.length > 0) {
                this.tier.agenceId = res.json[0].id;
            }
            console.log("tetstAgence" + this.refagences.length);
            if (this.refagences.length == 0) {
                this.otherField = true;
                this.tier.optionalAgencyName = 'Autre';
                this.nameAgence = true;
            } else {
                this.otherField = false;
                this.nameAgence = false;
            }
        });
    }
    newfield(nom) {
        if (nom == 'Autre') {
            this.nameAgence = true;
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

    parseHtmlDate(date) {
        return this.principal.parseDateJour(this.dateUtils.convertLocalDateToServer(date));
    }
    searchTiers(query, compagnyId) {

        if (query !== null && query !== undefined && query !== '' && compagnyId !== null && compagnyId !== undefined) {
            this.nameAgence = false;
            this.otherField = false;
            console.log(query);
            // Search contract by vin
            this.tier = new Tiers();
            this.contratAssuranceDuTiers = new ContratAssurance();
            this.trouveTiers = new Tiers();
            this.exist = false;
            this.tierIsCompagnie = false;
            this.old = false;
            this.tier.immatriculation = query;
            this.contratAssuranceService.findVehiculeByImmatriculationAndClient(query, compagnyId).subscribe((contratAssurancee) => {
                this.contratAssuranceDuTiers = contratAssurancee;


                console.log(contratAssurancee);
                if (this.contratAssuranceDuTiers) {
                    this.vehiculeAssureService.findVehiculeByCompagnyIdAndImmatriculation(compagnyId, query).subscribe((vehicule: VehiculeAssure) => {
                        this.vehiculeContratTiers = vehicule;
                        this.agentCompanyRO = true;
                        this.exist = true;
                        this.old = true;
                        this.tier.numeroContrat = this.contratAssuranceDuTiers.numeroContrat;
                        this.assureService.find(this.vehiculeContratTiers.insuredId).subscribe((assureRes: Assure) => {
                            this.assureCherche = assureRes;
                            if (this.assureCherche.company) {
                                this.tierIsCompagnie = true;
                                this.tierRaisonSocial = this.assureCherche.raisonSociale;
                                this.tier.raisonSocial = this.tierRaisonSocial;
                                this.tier.fullName = this.tierRaisonSocial;
                            }
                            if (!this.assureCherche.company) {
                                this.tierIsCompagnie = false;
                                this.tier.nom = assureRes.nom;
                                this.tier.prenom = this.assureCherche.prenom;
                                this.tier.fullName = this.tier.nom + ' ' + this.tier.prenom;

                            }
                        });
                        this.debut2 = this.principal.parseDateJour(this.contratAssuranceDuTiers.debutValidite);
                        this.fin2 = this.principal.parseDateJour(this.contratAssuranceDuTiers.finValidite);
                        const date = new Date(this.contratAssuranceDuTiers.debutValidite);
                        if (this.contratAssuranceDuTiers.debutValidite) {
                            this.tier.debutValidite = {
                                year: date.getFullYear(),
                                month: date.getMonth() + 1,
                                day: date.getDate()
                            };
                        }
                        const date1 = new Date(this.contratAssuranceDuTiers.finValidite);
                        if (this.contratAssuranceDuTiers.finValidite) {
                            this.tier.finValidite = {
                                year: date1.getFullYear(),
                                month: date1.getMonth() + 1,
                                day: date1.getDate()
                            };
                        }

                        this.agenceAssuranceService.find(this.contratAssuranceDuTiers.agenceId).subscribe((agenceRes: Agency) => {
                            this.agenceTierNom = agenceRes.name;
                            this.tier.agenceNom = agenceRes.name;
                            this.tier.agenceId = agenceRes.id;
                            console.log("testagenceId" + this.tier.agenceId);
                            if (agenceRes.partnerId) {
                                this.refCompagnieService.find(agenceRes.partnerId).subscribe((compagnieRes: Partner) => {
                                    this.compagnieTierNom = compagnieRes.companyName;
                                    this.tier.compagnieNom = compagnieRes.companyName;
                                    this.tier.compagnieId = compagnieRes.id;
                                    console.log("testagenceId" + this.tier.compagnieId);
                                });
                            }
                        });
                    });
                }

                else {
                    this.agentCompanyRO = false;
                    this.old = false;
                    this.tiersService.findTiersByImmatriculation(query, compagnyId).subscribe((res) => {
                        this.listeTiersByImmatriculation = res.json;
                        console.log("testListesImmatriculation " + this.listeTiersByImmatriculation.length);
                        if (this.listeTiersByImmatriculation.length > 0) {
                            this.trouveTiers = this.listeTiersByImmatriculation[0];
                            this.exist = true;

                            this.debut2 = this.principal.parseDateJour(this.trouveTiers.debutValidite);
                            this.fin2 = this.principal.parseDateJour(this.trouveTiers.finValidite);
                            this.tier.numeroContrat = this.trouveTiers.numeroContrat;
                            console.log("testTiersnumeroContrat" + this.trouveTiers.numeroContrat);
                            this.agenceAssuranceService.find(this.trouveTiers.agenceId).subscribe((agenceRes: Agency) => {
                                this.agenceTierNom = agenceRes.name;
                                this.tier.agenceNom = agenceRes.name;
                                this.tier.agenceId = agenceRes.id;
                                if (agenceRes.partnerId) {
                                    this.refCompagnieService.find(agenceRes.partnerId).subscribe((compagnieRes: Partner) => {
                                        this.compagnieTierNom = compagnieRes.companyName;
                                        this.tier.compagnieNom = compagnieRes.companyName;
                                        this.tier.compagnieId = compagnieRes.id;
                                    });
                                }
                            });
                            const date = new Date(this.trouveTiers.debutValidite);
                            if (this.trouveTiers.debutValidite) {
                                this.tier.debutValidite = {
                                    year: date.getFullYear(),
                                    month: date.getMonth() + 1,
                                    day: date.getDate()
                                };
                            }
                            const date1 = new Date(this.trouveTiers.finValidite);
                            if (this.trouveTiers.finValidite) {
                                this.tier.finValidite = {
                                    year: date1.getFullYear(),
                                    month: date1.getMonth() + 1,
                                    day: date1.getDate()
                                };
                            }
                            this.tier.fullName = this.trouveTiers.fullName;
                            this.tier.responsible = this.trouveTiers.responsible;
                            this.tier.agenceTier = this.trouveTiers.agenceTier;
                        }
                        else {
                            this.tier.compagnieId=this.companyId;
                            this.listAgenceByCompagnie(this.tier.compagnieId);
                        }
                    });
                }

            });
        }

    }

    addTier() {
        this.nameAgence = false;
        this.otherField = false;


        if (this.compagnies && this.compagnies.length > 0) {
            for (let i = 0; i < this.compagnies.length; i++) {
                if (this.compagnies[i].id === this.tier.compagnieId) {
                    this.tier.compagnieNom = this.compagnies[i].companyName;
                    break;
                }
            }
        }
        if (this.agences && this.agences.length > 0) {
            for (let i = 0; i < this.agences.length; i++) {
                if (this.agences[i].id === this.tier.agenceId) {
                    this.tier.agenceNom = this.agences[i].name;
                    break;
                }
            }
        }
        this.tiers.push(this.tier);
        console.log(this.tier);
        this.tier = new Tiers();
        this.old = false;
        this.exist = false;
        this.tierContratNumero = null;
        this.showFrmTiers = false;
        if (this.tiers.length == this.sinisterPec.vehicleNumber - 1) this.newFormTiers == false;




    }

    changeAssujettie(etat) {
        if (etat == false) {
            this.sinisterPec.assujettieTVA = false;
        } else {
            this.sinisterPec.assujettieTVA = true;
        }
    }

    listReparateursByGouvernorat(id) {
        console.log(" date pc circulation tail");
        this.reparateursAffecte = [];
        this.reparateurs = [];
        const age = new Date().getFullYear() - new Date(this.datePCirculation).getFullYear();
        console.log(" date pc circulation" + this.datePCirculation);
        this.test = false;
        this.reparateurService.findByGouvernorat(id).subscribe((listReparateur: Reparateur[]) => {
            console.log(" date pc circulation taille liste reparateur  by reparateur gov " + this.reparateurs.length);
            console.log(this.reparateurs);
            this.reparateurs = listReparateur;
            if (this.reparateurs.length > 0) {
                for (let index = 0; index < this.reparateurs.length; index++) {
                    const reparateur = this.reparateurs[index];
                    if (!reparateur.isBloque && reparateur.isActive) {
                        this.test = false;
                        // filtre selon partner et mode gestion
                        for (let index = 0; index < reparateur.garantieImpliques.length; index++) {
                            const garantieImplique = reparateur.garantieImpliques[index];
                            console.log(this.sinisterPec.partnerId + "comparaison partner " + garantieImplique.partnerId);
                            if (this.sinisterPec.partnerId == garantieImplique.partnerId) {
                                for (let index2 = 0; index2 < garantieImplique.refModeGestions.length; index2++) {
                                    const refModeGestion = garantieImplique.refModeGestions[index2];
                                    console.log(this.sinisterPec.modeId + "comparaison mode gestion" + refModeGestion.id);
                                    if (this.sinisterPec.modeId == refModeGestion.id) {
                                        this.test = true;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        // filtre selon age vehicule
                        if (this.test) {
                            console.log("enter testtttt apres ok reparateur" + reparateur.id);
                            console.log("en k age" + age);
                            if (age > 10 && reparateur.isMultiMarque) {
                                console.log("enter testtttt apres ok age > 10");
                                for (let index4 = 0; index4 < reparateur.orientations.length; index4++) {
                                    const orientation = reparateur.orientations[index4];
                                    console.log("enter testtttt apres ok orientation.refAgeVehiculeId" + orientation.refAgeVehiculeId);
                                    if (orientation.refAgeVehiculeId == 3 || orientation.refAgeVehiculeId == 4) {
                                        for (let ind = 0; ind < orientation.refMarques.length; ind++) {
                                            const marque = orientation.refMarques[ind];
                                            console.log(this.vehicule.marqueLibelle + "enter testtttt marqueLibelle a" + marque.label);
                                            if (this.vehicule.marqueLibelle == marque.label) {
                                                this.reparateursAffecte.push(reparateur);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (age < 10 || age == 10) {
                                console.log("enter testtttt apres ok age <10");
                                for (let index5 = 0; index5 < reparateur.orientations.length; index5++) {
                                    const orientation = reparateur.orientations[index5];
                                    console.log("enter testtttt apres ok  age <10 orientation.refAgeVehiculeId" + orientation.refAgeVehiculeId);
                                    if (orientation.refAgeVehiculeId == 2 || orientation.refAgeVehiculeId == 1 || orientation.refAgeVehiculeId == 4) {
                                        for (let ind1 = 0; ind1 < orientation.refMarques.length; ind1++) {
                                            const marque = orientation.refMarques[ind1];
                                            console.log(this.vehicule.marqueLibelle + "enter testtttt marqueLibelle a" + marque.label);
                                            if (this.vehicule.marqueLibelle == marque.label) {
                                                this.reparateursAffecte.push(reparateur);
                                                break;
                                            }
                                        }
                                    }

                                }

                            }
                        }

                    }
                }
            }
            if (this.reparateursAffecte.length > 0) {
                this.convertirReparateur();
                //this.sinisterPec.reparateurId = this.reparateursAffecte[0].id;
                if (this.sinisterPec.reparateurId) {
                    this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                }
            } else {
                this.capacite = null;
                this.nbmissionreparateur = null;
                //this.sinisterPec.reparateurId = null;
            }

        });
    }
    convertirReparateur() {
        this.reparateursAffecte.forEach(reparateur => {
            reparateur.info = reparateur.raisonSociale;
            if (reparateur.isagentOfficiel && reparateur.isMultiMarque) {
                reparateur.info = reparateur.info + "  : Agent Officiel // Multi Marque"
            }
            else if (reparateur.isagentOfficiel) {
                reparateur.info = reparateur.info + " Agent Officiel"
            }
            else if (reparateur.isagentOfficiel && reparateur.isMultiMarque) {
                reparateur.info = reparateur.info + " Multi Marque";
            }
            for (let ind = 0; ind < reparateur.orientations.length; ind++) {
                const orientation = reparateur.orientations[ind];
                for (let ind1 = 0; ind1 < orientation.refMarques.length; ind1++) {
                    const marque = orientation.refMarques[ind1];
                    if (this.vehicule.marqueLibelle == marque.label) {
                        //    this.reparateursAffecte.push(reparateur);
                        reparateur.info = reparateur.info + " : " + orientation.remiseMarque + "%";

                        break;
                    }
                }
            }


        });
    }

    onCarteGriseQuoteFileChange(fileInput: any) {
        this.updateCarteGriseQuote = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.carteGriseQuoteAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.carteGriseQuoteAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.carteGriseQuoteFiles = fileInput.target.files[0];
        this.carteGriseQuotePreview = true;
        console.log(this.carteGriseQuoteFiles);
    }

    onCompteurFileChange(fileInput: any) {
        this.updateCompteur = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.compteurAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.compteurAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.compteurFiles = fileInput.target.files[0];
        this.compteurPreview = true;
        console.log(this.compteurFiles);
    }

    onImmatriculationFileChange(fileInput: any) {
        this.updateImmatriculation = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.immatriculationAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.immatriculationAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.immatriculationFiles = fileInput.target.files[0];
        this.immatriculationPreview = true;
        console.log(this.immatriculationFiles);
    }

    onNSerieFileChange(fileInput: any) {
        this.updateNSerie = true;

        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.nSerieAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.nSerieAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.nSerieFiles = fileInput.target.files[0];
        this.nSeriePreview = true;
        console.log(this.nSerieFiles);
    }

    onFinitionFileChange(fileInput: any) {
        this.updateFinition = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.finitionAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.finitionAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.finitionFiles = fileInput.target.files[0];
        this.finitionPreview = true;
        console.log(this.finitionFiles);
    }

    onFaceArriereGaucheFileChange(fileInput: any) {
        this.updateFaceArriereGauche = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.faceArriereGaucheAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.faceArriereGaucheAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.faceArriereGaucheFiles = fileInput.target.files[0];
        this.faceArriereGauchePreview = true;
        console.log(this.faceArriereGaucheFiles);
    }

    onFaceArriereDroitFileChange(fileInput: any) {
        this.updateFaceArriereDroit = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.faceArriereDroitAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.faceArriereDroitAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.faceArriereDroitFiles = fileInput.target.files[0];
        this.faceArriereDroitPreview = true;
        console.log(this.faceArriereDroitFiles);
    }

    onFaceAvantGaucheFileChange(fileInput: any) {
        this.updateFaceAvantGauche = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();

        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.faceAvantGaucheAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.faceAvantGaucheAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.faceAvantGaucheFiles = fileInput.target.files[0];
        this.faceAvantGauchePreview = true;
        console.log(this.faceAvantGaucheFiles);
    }

    onFaceAvantDroitFileChange(fileInput: any) {
        this.updateFaceAvantDroit = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');

        this.extensionImageOriginal = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        this.extensionImage = this.extensionImageOriginal.toLowerCase();
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.faceAvantDroitAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.faceAvantDroitAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.faceAvantDroitFiles = fileInput.target.files[0];
        this.faceAvantDroitPreview = true;
        console.log(this.faceAvantDroitFiles);
    }

    downloadCarteGriseQuoteFile() {
        if (this.carteGriseQuoteFiles) {
            saveAs(this.carteGriseQuoteFiles);
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
        this.updatePhotoReparation = true;
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
        this.updateFacture = true;
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
                this.showFacture = false;
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
                this.showPhotoReparation = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }


    deleteCarteGriseFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showCarteGriseAttachment = true;
                this.carteGrisePreview = false;
                this.carteGriseFiles = null;
                this.carteGriseAttachment.label = null;
                this.showCarteGrise = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteActeDeCessionFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showActeDeCessionAttachment = true;
                this.acteCessionPreview = false;
                this.acteCessionFiles = null;
                this.acteCessionAttachment.label = null;
                this.showActeDeCession = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteConstatFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showConstatAttachment = true;
                this.constatPreview = false;
                this.constatFiles = null;
                this.constatAttachment.label = null;
                this.showConstat = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteFaceAvantDroitFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showFaceAvantDroitAttachment = true;
                this.faceAvantDroitPreview = false;
                this.faceAvantDroitFiles = null;
                this.faceAvantDroitAttachment.label = null;
                this.showFaceAvantDroit = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteFaceAvantGaucheFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showFaceAvantGaucheAttachment = true;
                this.faceAvantGauchePreview = false;
                this.faceAvantGaucheFiles = null;
                this.faceAvantGaucheAttachment.label = null;
                this.showFaceAvantGauche = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteFaceArriereDroitFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showFaceArriereDroitAttachment = true;
                this.faceArriereDroitPreview = false;
                this.faceArriereDroitFiles = null;
                this.faceArriereDroitAttachment.label = null;
                this.showFaceArriereDroit = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteFaceArriereGaucheFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showFaceArriereGaucheAttachment = true;
                this.faceArriereGauchePreview = false;
                this.faceArriereGaucheFiles = null;
                this.faceArriereGaucheAttachment.label = null;
                this.showFaceArriereGauche = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteFinitionFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showFinitionAttachment = true;
                this.finitionPreview = false;
                this.finitionFiles = null;
                this.finitionAttachment.label = null;
                this.showFinition = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteNSerieFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showNSerieAttachment = true;
                this.nSeriePreview = false;
                this.nSerieFiles = null;
                this.nSerieAttachment.label = null;
                this.showNSerie = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteImmatriculationFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showImmatriculationAttachment = true;
                this.immatriculationPreview = false;
                this.immatriculationFiles = null;
                this.immatriculationAttachment.label = null;
                this.showImmatriculation = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteCompteurFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showCompteurAttachment = true;
                this.compteurPreview = false;
                this.compteurFiles = null;
                this.compteurAttachment.label = null;
                this.showCompteur = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteCarteGriseQuoteFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showCarteGriseQuoteAttachment = true;
                this.carteGriseQuotePreview = false;
                this.carteGriseQuoteFiles = null;
                this.carteGriseQuoteAttachment.label = null;
                this.showCarteGriseQuote = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    verifTiers() {
        this.nbrTiers = 0;
        this.tiers.forEach(element => {
            if (element.deleted == true) {
            } else {
                this.nbrTiers++;
            }
        });
        this.reglesGestion();
        if (this.isFromInProgress) {
            if (!this.sinisterPec.regleModeGestion) {
                this.sinisterPec.prestAApprouv = false;
            }
        }
        this.showFrmTiers = false;
        if (this.sinisterPec.vehicleNumber !== null && this.sinisterPec.vehicleNumber !== undefined) {
            if (this.sinisterPec.vehicleNumber > 1) {
                this.tiersLength = ((this.nbrTiers < this.sinisterPec.vehicleNumber) && this.nbrTiers > 0) ? true : false;
                if (!this.CasBareme.id) {
                    this.refBar = false;
                }
                if ((this.nbrTiers < this.sinisterPec.vehicleNumber)) {
                    this.showAlertTiers = false;
                }
            }
            else if (this.sinisterPec.vehicleNumber === 1) {
                if (this.nbrTiers > 0) {
                    this.tiersLength = false;
                } else {
                    this.tiersLength = true;
                    this.refBar = true;
                }

            }


            /*if(this.tiers.length + 1 == this.sinisterPec.vehicleNumber){
                this.showFrmTiers = false;
            }*/

        } else {
            this.tiersLength = true;
            this.showFrmTiers = false;
        }

    }

    addForm() {

        this.tierRespMin = true;
        this.showFrmTiers = true;
        this.agentCompanyRO = false;
        this.showAlertTiers = false;

        this.showResponsible = true;

        this.tiers.forEach(element => {
           
            if (element.responsible)
            {
                this.showResponsible = false;
            }
        });

    }

    deleteForm() {
        this.showFrmTiers = false;
        this.tier = new Tiers();
    }


    nbreMissionByExpert(id) {
        this.sinisterPecService.getNbreMissionExpert(id).subscribe((res: any) => {
            this.nbmissionExpert = res.json;
        })
    }







    getDevis(sinPec: SinisterPec) {
        if (sinPec.primaryQuotationId !== null && sinPec.primaryQuotationId !== undefined) {
            this.quotationService.find(sinPec.primaryQuotationId).subscribe((res) => {  // Find devid By ID
                this.quotation = res;
                this.quotation.typeString = 'Devis Initiale';
                if (sinPec.listComplementaryQuotation.length > 0) { this.quotation.etatString = 'Cloturé'; } else { this.quotation.etatString = 'En cours'; }
                this.listesQuotations.push(this.quotation);


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
        if (sinPec.listComplementaryQuotation.length > 0) {
            this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((quotCompl) => {  // Find devid By ID
                //if (QuotCompl.isConfirme) {
                if (quotCompl.id !== null && quotCompl.id !== undefined) {
                    quotCompl.typeString = 'Devis Complementaire';
                    if (quotCompl.statusId == 4) {
                        quotCompl.etatString = 'En cours';
                    } else {
                        if (quotCompl.statusId == 10 && quotCompl.isConfirme == false) {
                            quotCompl.etatString = 'Annulé';
                        } else {
                            quotCompl.etatString = 'Cloturé';
                        }
                    }
                    this.listesQuotations.push(quotCompl);
                }
                //}
            });
        }
    }

    editQuotation(quoteId) {
        this.showEtatQuotation = true;
        this.listesApecs = [];
        this.detailsPiecesMO = [];
        this.detailsPiecesIngredient = [];
        this.detailsPieces = [];
        this.detailsPiecesFourniture = [];
        this.quotation = new Quotation();
        this.quotation.stampDuty = this.activeStamp.amount;
        this.isApecEntry = false;
        this.quotationService.find(quoteId).subscribe((resQuote) => {  // Find devid By ID
            this.quotation = resQuote;
            if (this.quotation.statusId == 4) {
                this.etatQuotation = 1;
            } else if (this.quotation.statusId == 10 && this.quotation.isConfirme == false) {
                this.etatQuotation = 3;
            } else {
                this.etatQuotation = 2;
            }
            if (this.quotation.sinisterPecId) { this.devisComplementaire = true; } else { this.devisComplementaire = false; }
            this.isQuoteEntry = true;
            this.getApec(this.quotation);
            this.loadAllDetailsMo();
            this.loadAllIngredient();
            this.loadAllRechange();
            this.loadAllFourniture();
        });
    }

    getApec(quotation: Quotation) {
        this.apecService.queryListBySinPec(quotation.id).subscribe((resApec) => {
            this.listesApecs = resApec.json;
            this.listesApecs.forEach(elementApec => {
                if (quotation.sinisterPecId) { elementApec.typeApecString = 'Accord complementaire'; } else { elementApec.typeApecString = 'Accord initial'; }
                if (elementApec.etat == 0) { elementApec.etatApecString = 'Approuver Accord'; }
                else if (elementApec.etat == 6) { elementApec.etatApecString = 'Validation Accord'; }
                else if (elementApec.etat == 3) { elementApec.etatApecString = 'Imprimé Accord'; }
                else if (elementApec.etat == 7) { elementApec.etatApecString = 'Validation participation assuré'; }
                else if (elementApec.etat == 9) { elementApec.etatApecString = 'Signature de l’Accord'; }
                else if (elementApec.etat == 4) { elementApec.etatApecString = 'Générer Accord modifié'; }
                else if (elementApec.etat == 13) { elementApec.etatApecString = 'Génération bon de sortie'; }
                else if (elementApec.etat == 17) { elementApec.etatApecString = 'Confirmation Devis Complémentaire'; }
                else if (elementApec.etat == 10) { elementApec.etatApecString = 'En instance de réparation'; }
                else if (elementApec.etat == 20) { elementApec.etatApecString = 'Accord cloturé'; }
                else { elementApec.etatApecString = ''; }
                if (elementApec.id !== null && elementApec.id !== undefined) {
                    this.observationApecService.findByApec(elementApec.id).subscribe((resObsApec) => {
                        this.obsApec = resObsApec;
                        if (this.obsApec.decisionApprobationCompagnie == 1) { elementApec.decisionCompany = 'Favorable'; }
                        if (this.obsApec.decisionApprobationCompagnie == 2) { elementApec.decisionCompany = 'Défavorable'; }
                        if (this.obsApec.decisionApprobationCompagnie == 4) { elementApec.decisionCompany = 'Favorable avec modification'; }
                        if (this.obsApec.decisionApprobationCompagnie == 5) { elementApec.decisionCompany = 'Favorable avec réserve'; }
                    });
                } else {
                    elementApec.decisionCompany = '';
                }
            });
        });
    }

    editApec(apec: Apec) {
        this.isApecEntry = true;
        this.apecTotale = apec;
        if (this.apecTotale.participationGa == null || this.apecTotale.participationGa == undefined) { this.apecTotale.participationGa = 0; }
        if (this.apecTotale.droitDeTimbre == null || this.apecTotale.droitDeTimbre == undefined) { this.apecTotale.droitDeTimbre = 0; }
        if (this.apecTotale.participationAssure == null || this.apecTotale.participationAssure == undefined) { this.apecTotale.participationAssure = 0; }
        if (this.apecTotale.participationVetuste == null || this.apecTotale.participationVetuste == undefined) { this.apecTotale.participationVetuste = 0; }
        if (this.apecTotale.participationTva == null || this.apecTotale.participationTva == undefined) { this.apecTotale.participationTva = 0; }
        if (this.apecTotale.participationRpc == null || this.apecTotale.participationRpc == undefined) { this.apecTotale.participationRpc = 0; }
        if (this.apecTotale.fraisDossier == null || this.apecTotale.fraisDossier == undefined) { this.apecTotale.fraisDossier = 0; }
        if (this.apecTotale.avanceFacture == null || this.apecTotale.avanceFacture == undefined) { this.apecTotale.avanceFacture = 0; }
        if (this.apecTotale.depacementPlafond == null || this.apecTotale.depacementPlafond == undefined) { this.apecTotale.depacementPlafond = 0; }
        if (this.apecTotale.estimationFranchise == null || this.apecTotale.estimationFranchise == undefined) { this.apecTotale.estimationFranchise = 0; }
        if (this.apecTotale.regleProportionnel == null || this.apecTotale.regleProportionnel == undefined) { this.apecTotale.regleProportionnel = 0; }

    }

    addNewMoLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.tva = 19;
        this.detailsPiecesMO.push(this.detailsPiece);
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
                this.detailsPiecesIngredient.forEach(element => {
                    quant = quant + element.quantite;
                    tauxForFour = element.prixUnit;
                });
                this.detailsPiece.prixUnit = (this.reparateur.petiteFourniture * tauxForFour) / 100;
                this.detailsPiece.quantite = quant;
                this.detailsPiece.totalHt = +(this.detailsPiece.prixUnit * this.detailsPiece.quantite).toFixed(3);
                this.detailsPiece.totalTtc = +(this.detailsPiece.prixUnit * this.detailsPiece.quantite + ((this.detailsPiece.prixUnit * this.detailsPiece.quantite * 19) / 100)).toFixed(3);;
                this.detailsPiecesFourniture.push(this.detailsPiece);
                this.calculateGlobalFournTtc();
            } else {

            }


        }


    }

    openAddPiece(detailsPieceMo, type: number) {
        let vehiclePiece = new VehiclePiece();
        vehiclePiece.id = detailsPieceMo.designationId;
        vehiclePiece.reference = detailsPieceMo.designationReference;
        vehiclePiece.label = detailsPieceMo.designation;
        vehiclePiece.active = true;
        vehiclePiece.typeId = type;
        vehiclePiece.source = 'REPARATOR';

        this.ngbModalRef = this.siniterPecPopupService.openAddPieceModal(QuotationPieceAddComponent as Component, vehiclePiece);
        this.ngbModalRef.result.then((result: any) => {
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
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });
    }

    changeTypeInterventionIP(dpIngredient: DetailsPieces) {
        console.log("peinture designation-*-*-*-*-" + dpIngredient.typeInterventionId);
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

    selectTapedPieceFoDesignation(fourniture: DetailsPieces) {
        this.fourniturePieces = [];
        fourniture.designationId = null;
        if (fourniture.designation !== null && fourniture.designation !== undefined && fourniture.designation.length >= 7) {
            let fournDesignation = '';
            if (fourniture.designation.includes('/') == true) {
                fournDesignation = fourniture.designation.replace('/', '@');
            } else {
                fournDesignation = fourniture.designation;
            }
            this.vehiclePieceService.getByTypeAndTapedDesignation(fournDesignation, 3).subscribe((res: VehiclePiece[]) => {
                this.fourniturePieces = res;
            });
            this.verif = true;
        }
    }

    changeTypeInterventionPF(fourniture: DetailsPieces) {
        if (fourniture.typeInterventionId == 6) {
            fourniture.prixUnit = this.reparateur.petiteFourniture;
            this.changeFourniture(fourniture);
        }
    }

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

    changeFourniture(fourniture: DetailsPieces) {
        fourniture.totalHt = +(fourniture.prixUnit * fourniture.quantite).toFixed(3);
        fourniture.amountDiscount = +(fourniture.totalHt * fourniture.discount / 100).toFixed(3);
        fourniture.amountAfterDiscount = +(fourniture.totalHt - fourniture.amountDiscount).toFixed(3);
        fourniture.amountVat = +(fourniture.amountAfterDiscount * fourniture.tva / 100).toFixed(3);
        fourniture.totalTtc = +(fourniture.amountAfterDiscount + fourniture.amountVat).toFixed(3);
        this.calculateGlobalFournTtc();
    }

    selectPieceFoDesignation(fourniture: DetailsPieces) {
        if (fourniture != null && fourniture != undefined) {
            if (fourniture.designation !== null && fourniture.designation !== undefined) {
                let fournDesignation = '';
                if (fourniture.designation.includes('/') == true) {
                    fournDesignation = fourniture.designation.replace('/', '@');
                } else {
                    fournDesignation = fourniture.designation;
                }
                this.vehiclePieceService.getPieceByDesignation(fournDesignation, 3).subscribe((res: VehiclePiece) => {
                    fourniture.designationId = res.id;
                    fourniture.designation = res.label;
                });
            }
        }
    }


    changeIngredient(dpIngredient: DetailsPieces) {
        dpIngredient.totalHt = +(dpIngredient.prixUnit * dpIngredient.quantite).toFixed(3);
        dpIngredient.amountDiscount = +(dpIngredient.totalHt * dpIngredient.discount / 100).toFixed(3);
        dpIngredient.amountAfterDiscount = +(dpIngredient.totalHt - dpIngredient.amountDiscount).toFixed(3);
        dpIngredient.amountVat = +(dpIngredient.amountAfterDiscount * dpIngredient.tva / 100).toFixed(3);
        dpIngredient.totalTtc = +(dpIngredient.amountAfterDiscount + dpIngredient.amountVat).toFixed(3);
        this.calculateGlobalIngTtc();
    }

    calculerHTVVetusteAndTTCVetuste(detailsPiece: DetailsPieces) {
        detailsPiece.HTVetuste = detailsPiece.totalHt * detailsPiece.vetuste / 100;
        detailsPiece.TTCVetuste = detailsPiece.HTVetuste + (detailsPiece.HTVetuste * 19 / 100);
    }

    selectPieceCode(pieces: DetailsPieces) {
        if (pieces != null && pieces != undefined) {
            if (pieces.designationReference !== null && pieces.designationReference !== undefined) {
                let pieceDesRef = '';
                if (pieces.designationReference.includes('/') == true) {
                    pieceDesRef = pieces.designationReference.replace('/', '@');
                } else {
                    pieceDesRef = pieces.designationReference;
                }
                this.vehiclePieceService.getPieceByReference(pieceDesRef, 1).subscribe((res: VehiclePiece) => {
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
        if (pieces.designationReference !== null && pieces.designationReference !== undefined && pieces.designationReference.length >= 4) {
            let pieceDesRef = '';
            if (pieces.designationReference.includes('/') == true) {
                pieceDesRef = pieces.designationReference.replace('/', '@');
            } else {
                pieceDesRef = pieces.designationReference;
            }
            this.vehiclePieceService.getByTypeAndTapedReference(pieceDesRef, 1).subscribe((res: VehiclePiece[]) => {
                this.moPieces = res;
            });

        }
    }

    selectPieceMoDesignation(detailsPieceMo: DetailsPieces) {
        if (detailsPieceMo != null && detailsPieceMo != undefined) {
            if (detailsPieceMo.designation !== null && detailsPieceMo.designation !== undefined) {
                let pieceMoDes = '';
                if (detailsPieceMo.designation.includes('/') == true) {
                    pieceMoDes = detailsPieceMo.designation.replace('/', '@');
                } else {
                    pieceMoDes = detailsPieceMo.designation;
                }
                this.vehiclePieceService.getPieceByDesignation(pieceMoDes, 1).subscribe((res: VehiclePiece) => {
                    detailsPieceMo.designationId = res.id;
                    detailsPieceMo.designation = res.label;
                });
            }
        }
    }

    selectPieceDesignation(pieces: DetailsPieces) {
        if (pieces != null && pieces != undefined) {
            if (pieces.designation !== null && pieces.designation !== undefined) {
                let pieceDes = '';
                if (pieces.designation.includes('/') == true) {
                    pieceDes = pieces.designation.replace('/', '@');
                } else {
                    pieceDes = pieces.designation;
                }
                this.vehiclePieceService.getPieceByDesignation(pieceDes, 1).subscribe((res: VehiclePiece) => {
                    pieces.designationId = res.id;
                    pieces.reference = res.id;
                    pieces.designationReference = res.code;

                });
            }
        }
    }

    changeNaturePieceRechange(nature: string, piece: DetailsPieces) {
        if (nature == "ORIGINE" || nature == "GENERIQUE") {
            piece.tva = 19;
        }
        if (nature == "CASSE") {
            piece.tva = 0;
        }
        this.changePieceRechange(piece);
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

    changePieceRechange(rechange: DetailsPieces) {
        rechange.totalHt = +(rechange.prixUnit * rechange.quantite).toFixed(3);
        rechange.amountDiscount = +(rechange.totalHt * rechange.discount / 100).toFixed(3);
        rechange.amountAfterDiscount = +(rechange.totalHt - rechange.amountDiscount).toFixed(3);
        rechange.amountVat = +(rechange.amountAfterDiscount * rechange.tva / 100).toFixed(3);
        rechange.totalTtc = +(rechange.amountAfterDiscount + rechange.amountVat).toFixed(3);
        this.calculateGlobalPiecesTtc();
    }

    selectTapedPieceDesignation(pieces: DetailsPieces) {
        this.moPieces = [];
        pieces.designationId = null;
        if (pieces.designation !== null && pieces.designation !== undefined && pieces.designation.length >= 7) {
            let pieceDes = '';
            if (pieces.designation.includes('/') == true) {
                pieceDes = pieces.designation.replace('/', '@');
            } else {
                pieceDes = pieces.designation;
            }
            this.vehiclePieceService.getByTypeAndTapedDesignation(pieceDes, 1).subscribe((res: VehiclePiece[]) => {
                this.moPieces = res;
                this.verif = true;
            });

        }
    }

    addNewPieceRechangeLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPieces.push(this.detailsPiece);
    }

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

    DecisionExpertByObser(id: number, detailsPiece: DetailsPieces) {
    }

    selectTapedPiecePeDesignation(dpIngredient: DetailsPieces) {
        this.ingredientPieces = [];
        dpIngredient.designationId = null;
        if (dpIngredient.designation !== null && dpIngredient.designation !== undefined && dpIngredient.designation.length >= 7) {
            let ingrDesignation = '';
            if (dpIngredient.designation.includes('/') == true) {
                ingrDesignation = dpIngredient.designation.replace('/', '@');
            } else {
                ingrDesignation = dpIngredient.designation;
            }
            this.vehiclePieceService.getByTypeAndTapedDesignation(ingrDesignation, 2).subscribe((res: VehiclePiece[]) => {
                this.ingredientPieces = res;
            });

            this.verif = true;

        }
    }

    // pieces peinture
    selectPiecePeDesignation(dpIngredient: DetailsPieces) {
        if (dpIngredient != null && dpIngredient != undefined) {
            if (dpIngredient.designation !== null && dpIngredient.designation !== undefined) {
                let ingrDesignation = '';
                if (dpIngredient.designation.includes('/') == true) {
                    ingrDesignation = dpIngredient.designation.replace('/', '@');
                } else {
                    ingrDesignation = dpIngredient.designation;
                }
                this.vehiclePieceService.getPieceByDesignation(ingrDesignation, 2).subscribe((res: VehiclePiece) => {
                    dpIngredient.designationId = res.id;
                    dpIngredient.designation = res.label;
                });
            }
        }
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

    addNewIngredientLine() {
        this.detailsPiece = new DetailsPieces();
        this.detailsPiece.tva = 19;
        this.detailsPiecesIngredient.push(this.detailsPiece);
    }

    changeMo(detailsMoLine: DetailsPieces) {
        detailsMoLine.totalHt = +(detailsMoLine.prixUnit * detailsMoLine.nombreHeures).toFixed(3);
        detailsMoLine.amountDiscount = +(detailsMoLine.totalHt * detailsMoLine.discount / 100).toFixed(3);
        detailsMoLine.amountAfterDiscount = +(detailsMoLine.totalHt - detailsMoLine.amountDiscount).toFixed(3);
        detailsMoLine.amountVat = +(detailsMoLine.amountAfterDiscount * detailsMoLine.tva / 100).toFixed(3);
        detailsMoLine.totalTtc = +(detailsMoLine.amountAfterDiscount + detailsMoLine.amountVat).toFixed(3);

        this.calculateGlobalMoTtc();
    }

    loadAllFourniture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.PIECES_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                //element.id = null;
                this.detailsPiecesFourniture.push(element);
            });
            this.calculateGlobalFournTtc();
        })
    }

    selectTapedPieceMoDesignation(detailsPieceMo: DetailsPieces) {
        this.moPieces = [];
        detailsPieceMo.designationId = null;
        if (detailsPieceMo.designation !== null && detailsPieceMo.designation !== undefined && detailsPieceMo.designation.length >= 7) {
            let pieceMoDes = '';
            if (detailsPieceMo.designation.includes('/') == true) {
                pieceMoDes = detailsPieceMo.designation.replace('/', '@');
            } else {
                pieceMoDes = detailsPieceMo.designation;
            }
            this.vehiclePieceService.getByTypeAndTapedDesignation(pieceMoDes, 1).subscribe((res: VehiclePiece[]) => {
                this.moPieces = res;
            });
            this.verif = true;
        }

    }

    observationExpertByTypeIntervention(detailsPieceMo: DetailsPieces) {
        this.detailsPieceMo.listobservationExpert = [];
        var expertObservation = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 3, label: "A Réparer" },
            { id: 4, label: "A Remplacer" },
            { id: 5, label: "Accordé avec Modification" }
        ];
        if (detailsPieceMo.typeInterventionId === 1) {
            expertObservation.forEach((item, index) => {
                if (item.id === 3) expertObservation.splice(index, 1);
            });

        }
        else if (detailsPieceMo.typeInterventionId === 2) {
            expertObservation.forEach((item, index) => {
                if (item.id === 4) expertObservation.splice(index, 1);
            });

        }
        else if (detailsPieceMo.typeInterventionId === 3) {
            expertObservation.forEach((item, index) => {
                if (item.id === 3) expertObservation.splice(index, 1);
            });
            expertObservation.forEach((item, index) => {
                if (item.id === 4) expertObservation.splice(index, 1);
            });
        }
        detailsPieceMo.listobservationExpert = expertObservation;
    }

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
        this.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }

    loadAllRechange() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                //element.id = null;
                this.detailsPieces.push(element);
            });

            this.calculateGlobalPiecesTtc();
        })
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

    ajoutNouvelpieceJointe() {
        this.ajoutNouvelpieceform = true;
        this.ajoutNouvelpiece = false;
    }

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
        this.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }

    loadAllIngredient() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.INGREDIENT_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                //element.id = null;
                this.detailsPiecesIngredient.push(element);
            });

            this.calculateGlobalIngTtc();
        })
    }

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
        this.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }

    loadAllDetailsMo() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, true).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                //element.id = null;
                this.detailsPiecesMO.push(element);
            });


            this.calculateGlobalMoTtc();
            this.getSum4();
        })
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
        this.ttcAmount = +(this.quotation.ttcAmount).toFixed(3);
    }

    //Les règles pour missionner un expert
    listExpertsByGouvernorat(id) {
        this.expertsAffectation = null;
        this.expertsAffectation = [];
        this.expertsIntial = [];
        this.expertsMission = [];
        this.nbmissionExpert = null;
        if (this.sinisterPec.partnerId !== null && this.sinisterPec.partnerId !== undefined && this.sinisterPec.modeId !== null && this.sinisterPec.modeId != undefined) {
            this.expertService.findListesByGouvernorat(id, this.sinisterPec.partnerId, this.sinisterPec.modeId).subscribe((listExpert: ResponseWrapper) => {
                this.expertsAffectation = listExpert.json;

                if (this.expertsAffectation && this.expertsAffectation.length > 0) {
                    //this.sinisterPec.expertId = this.expertsAffectation[0].id;
                    if (this.sinisterPec.expertId !== null) {
                        this.nbreMissionByExpert(this.sinisterPec.expertId);
                    }
                } else {
                    this.nbmissionExpert = null;
                    this.nbmissionExpert = null;
                    //this.sinisterPec.expertId = null;
                }
            });
        }
    }

    reglesGestion() {
        this.nbrTiers = 0;
        this.tiers.forEach(element => {
            if (element.deleted == true) {
            } else {
                this.nbrTiers++;
            }
        });
        this.sinisterPec.regleModeGestion = false;
        if (this.sinisterPec.modeId == 1 && (this.CasBareme.code == 10 || this.CasBareme.code == 1)) {
            this.sinisterPec.regleModeGestion = true;
        }
        if (this.sinisterPec.modeId == 7) {
            this.sinisterPec.regleModeGestion = true;
        }
        if (this.sinisterPec.modeId == 6 && this.nbrTiers == 0) {
            this.sinisterPec.regleModeGestion = true;
        }
        if (this.sinisterPec.modeId == 10 && this.nbrTiers == 0) {
            this.sinisterPec.regleModeGestion = true;
        }
        if (this.sinisterPec.modeId == 2) {
            this.sinisterPec.regleModeGestion = true;
        }

    }

    /**
     * Save prestation pecs tiers
     * @param prestationPecId
     * @param tiers
     */
    saveTiers() {
        // Add tiers
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.nbrTiersRespMin = 0;
                    if (this.tiers && this.tiers.length > 0) {
                        this.tiers.forEach(tier => {
                            if (tier.responsible == true) { this.nbrTiersRespMin++; }
                            if (this.nbrTiersRespMin > 1) { tier.responsible = false; }
                            if (tier.deleted && tier.id !== null && tier.id !== undefined) {
                                this.tiersService.delete(tier.id).subscribe(((resultTier) => {
                                    this.showAlertSuccess = true;
                                }));
                            } else if (!tier.deleted && (tier.id === null || tier.id === undefined)) {
                                tier.sinisterPecId = this.sinisterPec.id;
                                this.tiersService.create(tier).subscribe(((resultTier) => { this.showAlertSuccess = true; }));
                            } else if (!tier.deleted && tier.id !== null && tier.id !== undefined) {
                                this.tiersService.update(tier).subscribe(((resultTier) => { this.showAlertSuccess = true; }));
                            }
                        });
                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    /**
    * Save prestation pecs tiers
    * @param prestationPecId
    * @param Attachments
    */
    saveAttachments() {

        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    if (this.constatFiles !== null && this.updateConstat == true) {
                        this.prestationPECService.saveAttachmentsNw(this.sinisterPec.id, this.constatFiles, this.labelConstat, this.constatAttachment.name).subscribe((res: Attachment) => {
                            this.constatAttachment = res;
                            this.updateConstat = false;
                            this.showConstat = true;
                            this.constatPreview = false;
                            this.showConstatAttachment = false;
                            this.constatAttachment = new Attachment();
                            this.constatAttachment.name = null;
                            this.constatFiles = null;
                        });
                    }

                    if (this.carteGriseFiles !== null && this.updateCarteGrise == true) {
                        this.prestationPECService.saveAttachmentsNw(this.sinisterPec.id, this.carteGriseFiles, this.labelCarteGrise, this.carteGriseAttachment.name).subscribe((res: Attachment) => {
                            this.carteGriseAttachment = res;
                            this.updateCarteGrise = false;
                            this.showCarteGrise = true;
                            this.carteGrisePreview = false;
                            this.showCarteGriseAttachment = false;
                            this.carteGriseAttachment = new Attachment();
                            this.carteGriseAttachment.name = null;
                            this.carteGriseFiles = null;
                        });
                    }

                    if (this.acteCessionFiles !== null && this.updateActeDeCession == true) {
                        this.prestationPECService.saveAttachmentsNw(this.sinisterPec.id, this.acteCessionFiles, this.labelActeCession, this.acteCessionAttachment.name).subscribe((res: Attachment) => {
                            this.acteCessionAttachment = res;
                            this.updateActeDeCession = false;
                            this.showActeDeCession = true;
                            this.acteCessionPreview = false;
                            this.showActeDeCessionAttachment = false;
                            this.acteCessionAttachment = new Attachment();
                            this.acteCessionAttachment.name = null;
                            this.acteCessionFiles = null;

                        });
                    }

                    this.savePhotoPlusPec();
                    this.showAlertSuccess = true;
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));



    }

    getPhotoPlusPec() {
        this.piecesAttachmentsPecPlus = [];
        this.sinisterPecService.getPlusPecAttachments(this.sinisterPec.id).subscribe((resPlus) => {
            this.piecesAttachmentsPecPlus = resPlus.json;

        });
    }

    savePhotoPlusPec() {
        this.piecesAttachmentsPecPlus.forEach(pieceAttFile => {
            pieceAttFile.creationDate = null;
            if (pieceAttFile.pieceFile !== null && this.updatePieceJointe1PecPlus == true && (pieceAttFile.id == null || pieceAttFile.id == undefined)) {
                this.sinisterPecService.saveAttachmentsPiecePhotoPlus(this.sinisterPec.id, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.name).subscribe((res: Attachment) => {
                    this.getPhotoPlusPec();
                });
            }
        });

    }

    /**
     * Save prestation observations
     * @param prestationId
     * @param observations
     */
    saveObservations(prestationId: number, observations: Observation[]) {
        if (observations && observations.length > 0) {
            observations.forEach(observation => {
                if (observation.deleted && observation.id !== null && observation.id !== undefined) {
                    this.observationService.delete(observation.id).subscribe(((resultObs) => {
                    }));
                } else if (!observation.deleted && (observation.id === null || observation.id === undefined)) {
                    observation.sinisterPecId = prestationId;
                    this.observationService.create(observation).subscribe(((resultObs: Observation) => {
                    }));
                } else if (!observation.deleted && observation.id !== null && observation.id !== undefined) {
                    observation.sinisterPecId = prestationId;
                    this.observationService.update(observation).subscribe(((resultObs: Observation) => { }));
                }
            });
        }
    }



    changeMode(value) {
        console.log(value);

        //this.sinisterPec.vehicleNumber = null;
        this.sinisterPec.regleModeGestion = false;
        this.reglesGestion();
        if (this.isFromInProgress) {
            if (!this.sinisterPec.regleModeGestion) {
                this.sinisterPec.prestAApprouv = false;
            }
        }
        this.garantieDommageVehicule = value === 6 ? true : false;
        this.garantieDommageCollision = value === 5 ? true : false;
        this.garantieVolIncendiePartiel = (value === 8 || value === 9) ? true : false;
        this.garantieBrisGlace = value === 7 ? true : false;
        this.garantieTierceCollision = value === 11 ? true : false;
        this.garantieTierce = value === 10 ? true : false;
        //if (value == 1 || value == 2) this.sinisterPec.vehicleNumber = 2;
        if (value === 1 || value === 2) {
            this.sinisterPec.vehicleNumber = 2;
            this.nbrVehPattern = "^[2-9]$";
            this.minVinNumber = 2;
            //this.VinNumber = 2 ;
            this.vehNmbrIda = true;
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.sinisterPec.vehicleNumber = 2;
            this.nbrVehPattern = "^[2-9]$";
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            //this.vhclNmbrReq = false;
        } else {
            this.sinisterPec.vehicleNumber = 1;
            this.nbrVehPattern = "^[1-9]$";
            //this.sinisterPec.vehicleNumber = 1;
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            //this.vhclNmbrReq = false;
        }

    }

    changeModeGestion(value) {
        console.log(value);

        //this.sinisterPec.vehicleNumber = null;
        this.sinisterPec.regleModeGestion = false;
        this.reglesGestion();
        if (this.isFromInProgress) {
            if (!this.sinisterPec.regleModeGestion) {
                this.sinisterPec.prestAApprouv = false;
            }
        }
        this.garantieDommageVehicule = value === 6 ? true : false;
        this.garantieDommageCollision = value === 5 ? true : false;
        this.garantieVolIncendiePartiel = (value === 8 || value === 9) ? true : false;
        this.garantieBrisGlace = value === 7 ? true : false;
        this.garantieTierceCollision = value === 11 ? true : false;
        this.garantieTierce = value === 10 ? true : false;
        //if (value == 1 || value == 2) this.sinisterPec.vehicleNumber = 2;
        if (value === 1 || value === 2) {
            this.sinisterPec.vehicleNumber = 2;
            this.nbrVehPattern = "^[2-9]$";
            this.minVinNumber = 2;
            //this.VinNumber = 2 ;
            this.vehNmbrIda = true;
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.nbrVehPattern = "^[2-9]$";
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            //this.vhclNmbrReq = false;
        } else {
            //this.sinisterPec.vehicleNumber = 1;
            this.nbrVehPattern = "^[1-9]$";
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            //this.vhclNmbrReq = false;
        }

    }

    trackGrantiesById(index: number, item: RefStepPec) {
        return item.id;
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


    deleteObservation(observation) {
        console.log('_____________________________________ delete Observation _______________________________________________');
        observation.deleted = true;
    }

    prepareEditObservation(observation) {
        console.log('_____________________________________ edit Observation _______________________________________________');
        this.observation = observation;
        this.isObsModeEdit = true;
    }

    editObservation() {

        if (this.observation.commentaire === null || this.observation.commentaire === undefined) {
            this.isCommentError = true;
        } else {
            this.observationService.update(this.observation).subscribe((res) => {
            });
        }
        this.isObsModeEdit = false;
        this.observation = new Observation();
    }

    deleteTier(tier) {
        console.log('_____________________________________ delete Tier _______________________________________________');
        tier.deleted = true;
        tier.responsible = false;
        /*this.tiers.forEach((item, index) => {
            if (item === tier) this.tiers.splice(index, 1);
        });*/
    }


    listExpertsByGouvernoratRep(id) {
        this.expertsAffectation = null;
        this.expertsAffectation = [];
        this.expertsIntial = [];
        this.expertsMission = [];
        this.nbmissionExpert = null;
        if (this.sinisterPec.partnerId !== null && this.sinisterPec.partnerId !== undefined && this.sinisterPec.modeId !== null && this.sinisterPec.modeId != undefined) {
            this.expertService.findListesByGouvernorat(id, this.sinisterPec.partnerId, this.sinisterPec.modeId).subscribe((listExpert: ResponseWrapper) => {
                this.expertsAffectation = listExpert.json;
            });
        }
    }

    prepareEditTier(tier) {
        this.showResponsible = true;

       
        if(tier.responsible){
            this.showResponsible= true
        }else {
            this.tiers.forEach(tr => {
                if (tr.responsible) {
                    this.showResponsible = false;
    
                }
    
            });
        }
        this.agenceAssuranceService.findAllAgentAssurance().subscribe((res: ResponseWrapper) => {
            this.refagences = res.json;
        });
        this.nameAgence = false;
        this.otherField = false;
        this.tierRespMin = true;
        this.tier = tier;
        this.companyId = this.tier.compagnieId;
        if (this.tier.prenom !== null && this.tier.nom !== null && this.tier.prenom !== undefined && this.tier.nom !== undefined) {
            this.tierIsCompagnie = false;
        } else {
            this.tierIsCompagnie = true;
        }
        this.old = false;
        this.exist = true;
        this.isTierModeEdit = true;
        this.newFormTiers == true;
        this.showFrmTiers = true;
        this.agentCompanyRO = true;
        this.contratAssuranceService.findContractByImmatriculationForTiers(tier.immatriculation, tier.compagnieId).subscribe((existContract: boolean) => {
            if (existContract == false) {
                this.agenceAssuranceService.findAllByPartnerWithoutFiltrage(tier.compagnieId).subscribe((listes: ResponseWrapper) => {
                    if (listes.json.length == 0) {
                        this.nameAgence = true;
                        this.otherField = true;
                        this.tier.optionalAgencyName = 'Autre';
                    }
                });
                this.agentCompanyRO = false;
            }
        });
    }
    editTier() {
        console.log('_____________________________________ edit Tier _______________________________________________');
        this.nameAgence = false;
        this.otherField = false;
        this.tier = new Tiers();
        this.isTierModeEdit = false;
        this.showFrmTiers = false;
        if (this.tiers.length == this.sinisterPec.vehicleNumber - 1) this.newFormTiers == false;
        let tiersAvant = this.tiers;
        this.tiers = [];
        this.tiers = tiersAvant;

    }
    fetchDelegationsByGovernorate(id) {

        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernorat = subRes;
            this.delegationService.findByGovernorate(this.sysGouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.sysvilles = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.sinisterPec.delegationId = subRes1[0].id;
                }
            });
        });
        this.gov = false;
    }
    fetchDelegationsByGovernorateRep(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernoratRep = subRes;
            this.delegationService.findByGovernorate(this.sysGouvernoratRep.id).subscribe((subRes1: Delegation[]) => {
                this.sysvillesRep = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.sinisterPec.villeRepId = subRes1[0].id;
                }
                this.listExpertsByGouvernoratRep(id);
            });
        });
        this.govRep = false;
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

    historyPec() {
        this.ngbModalRef = this.siniterPecPopupService.openHistoryDetailsSinisterPec(HistoryPopupDetailsPec as Component, this.sinisterPec.id);
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
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}