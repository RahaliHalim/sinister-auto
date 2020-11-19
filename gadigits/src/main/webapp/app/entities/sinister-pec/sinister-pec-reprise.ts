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
import { StatusSinister, PrestationPecStep, EtatAccord, DecisionAssure, TypePiecesDevis, ApprovPec } from '../../constants/app.constants';
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
import { UserPartnerMode, UserPartnerModeService } from '../user-partner-mode';
import { ConventionService } from '../../entities/convention/convention.service';
import { RefMotif } from '../ref-motif';
import * as Stomp from 'stompjs';
import { Apec, ApecService } from '../apec';
import { saveAs } from 'file-saver/FileSaver';
import { Convention } from '../convention/convention.model';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { ViewSinisterPecService } from '../view-sinister-pec';
import { Quotation, QuotationService } from '../quotation';
import { DetailsPiecesService, DetailsPieces } from '../details-pieces';
import { DetailsMo } from '../details-mo';
import { StampDuty } from '../stamp-duty/stamp-duty.model';
import { StampDutyService } from '../stamp-duty/stamp-duty.service';
import { HistoryPopupDetailsPec } from './historyPopupDetailsPec.component';
import { DomSanitizer } from '@angular/platform-browser';
@Component({
    selector: 'jhi-sinister-reprise-dialog',
    templateUrl: './sinister-pec-reprise.html'
})
export class SinisterRepriseDialogComponent implements OnInit {
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
    dtTrigger: Subject<any> = new Subject();
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
    oneClickForButton19: boolean = true;
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
    reparateurObservations = [];
    expertObservations = [];
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
    refpositiongas: RefPositionGa[];
    listGarantiesForModeGestion: any[] = [];
    mode: any;
    test = false;
    garanties: any;
    baremes: RefBareme[];
    compagnie: any;
    agence: any;
    approveWithModifButton = true;
    approveButton = true;
    existMode = false;
    observations: Observation[] = [];
    observationss: Observation[] = [];
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
    enableField: boolean = false;
    isSave: boolean = false;
    BtnSaveValide: boolean = false;
    refBareme = new RefBareme();
    CasBareme = new RefBareme();
    refBid: number;
    imgBareme: string;
    idBaremeLoaded: number;
    piecePreview1 = false;
    codeBaremeLoaded: number;
    descriptionBaremeLoaded: String;
    showFrmTiers: boolean = false;
    myDate: any;
    motifs: RefMotif[];
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
    disabledMotif = false;
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    amountPattern = '^[0-9]+(\.[0-9]{1,3})?';
    nbrPassgrPattern = '^[1-9]*$';
    attachmentList: Attachment[];
    partner: Partner;
    serviceTypes: RefTypeService[];
    tierResponsableVerif: boolean = true;
    listModeByConvension: any[] = [];
    listModeByCnvByUser: any[] = [];
    private ngbModalRef: NgbModalRef;
    vehNmbrIda: boolean = false;
    listeTiersByImmatriculation: Tiers[] = [];
    modeId: any;
    cmpRf: boolean = false;
    selectedReparateur: any;
    motifsReopened: RaisonPec[];
    reasonsCanceled: RaisonPec[];
    reasonsRefused: RaisonPec[];
    isCanceled: boolean = false;
    isRefused: boolean = false;
    isApprouved: boolean = false;
    reopenPrest: boolean = false;
    isDecided: boolean = false;
    isAcceptedWithReserve: boolean = false;
    isAcceptedWithChangeStatus: boolean = false;
    motfReopened: boolean = false;
    showMotifAction: boolean = false;
    annulPrest: boolean = false;
    showMsgMotifRqrdCancel: boolean = true;
    showMsgMotifRqrdRefus: boolean = true;
    confirmPrest: boolean = false;
    decisionPrest: boolean = false;
    isSinPecRefusedCanceled: boolean = false;
    isSinPecVerifOrgPrinted: boolean = false;
    isCancPec: boolean = false;
    isRefusPec: boolean = false;
    isConfirmCancPec: boolean = false;
    labelAccordCP: String = "ConstatPreliminaire";
    labelAccordDebloquage: String = "Debloquage";
    labelAccordNormal: String = "AccordNormal";
    private readonly imageType: string = 'data:image/PNG;base64,';
    private readonly imageTypeJpeg: string = 'data:image/jpeg;base64,';
    isConfirmRefusPec: boolean = false;
    isSinPecBonSortie: boolean = false;
    showModeToModif: boolean = false;
    companyYes = false;
    ajoutNouvelpieceform = false;
    ajoutNouvelpiece = false;
    pieceAttachment1: Attachment = new Attachment();
    sendPrest: boolean = false;
    extensionImageOriginal: string;
    affectReparateurWithCapacity: boolean = false;
    pointChoc: PointChoc = new PointChoc();
    userExtra: UserExtra;
    usersPartnerModes: UserPartnerMode[];
    isCentreExpertise: boolean = false;
    userExtraCnv: UserExtra = new UserExtra();
    selectedIdCompagnies: number[] = [];
    containt: boolean = false;
    ws: any;
    detailsPiece: DetailsPieces = new DetailsPieces();
    detailsPiecesIngredient: DetailsPieces[] = [];
    detailsPiecesFourniture: DetailsPieces[] = [];
    detailsPiecesMO: DetailsPieces[] = [];
    detailsPieces: DetailsPieces[] = [];
    nbrTiers: number = 0;
    imprimesAttachments: Attachment[] = [];
    apecsValid: Apec[] = [];
    listUserPartnerModesForNotifs: UserPartnerMode[] = [];
    apecTotale: Apec = new Apec();
    isFromInProgress: boolean = false;
    raisonLevesReserves: any;
    apec: Apec;
    conformedisabled: boolean = false;
    detailsMos: DetailsMo[] = [];
    showConstatAttachment: boolean = true;
    showCarteGriseAttachment: boolean = true;
    showActeDeCessionAttachment: boolean = true;
    nbrTiersRespMin: number = 0;
    vehiculeContratTiers: VehiculeAssure = new VehiculeAssure();
    tierRespMin: boolean = true;
    assureur: Assure = new Assure();
    convention: Convention = new Convention();
    labelPiece1: string;
    notification: RefNotifAlert = new RefNotifAlert();
    notificationC: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    notifUserResp: NotifAlertUser;
    notifUserCompagny: NotifAlertUser;
    notifUserAgent: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    userExNotifAgency: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];
    userExNotifReparateurs: UserExtra[] = [];
    userExNotifChargeAdministrative: UserExtra[] = [];
    userExNotifResponsableAdministrative: UserExtra[] = [];
    testConvention = false;
    oneClickForButton: boolean = true;
    oneClickForButton1: boolean = true;
    oneClickForButton2: boolean = true;
    oneClickForButton3: boolean = true;
    oneClickForButton4: boolean = true;
    oneClickForButton5: boolean = true;
    oneClickForButton6: boolean = true;
    oneClickForButton7: boolean = true;
    oneClickForButton8: boolean = true;
    oneClickForButton9: boolean = true;
    apecSettings: ApecSettings[] = [];
    apecSettingsAvenant: ApecSettings[] = [];
    updatePieceJointe1 = false;
    updateConstat = false;
    updateCarteGrise = false;
    updateActeDeCession = false;
    piecesAttachments: Attachment[] = [];
    apecModifStatus: Apec = new Apec();
    quotation: Quotation = new Quotation();
    status: any;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    testService: boolean = false;
    pieceFiles1: File;
    blockAgentForAddPrest = false;
    testModifStatus = false;
    refPack: RefPack = new RefPack();
    buttonMissExpert: boolean = false;
    extensionImage: string;
    nomImage: string;
    buttonAffReparateur: boolean = false;
    responsablePec = false;
    selectedItem: boolean = true;
    buttonConfirmAnnul: boolean = true;
    buttonConfirmRefus: boolean = false;
    totalTtc = 0;
    moTotalTtc = 0;
    ingTotalTtc = 0;
    piecesFiles: File[] = [];
    fournTotalTtc = 0;
    oneTime = false;
    piecesTotalTtc = 0;
    ttcAmount = 0;
    activeStamp: StampDuty;
    complementaryQuotationMS: Quotation = new Quotation();
    preliminaryReport = false;
    expertDecision: any;
    canCancelMissionementExpert = true;
    showConstat = false;
    showCarteGrise = false;
    showActeDeCession = false;
    showValeurANeufExpert = false;
    companyId: number;
    showResponsible = true;
    piecesJointesAttachments: Attachment[] = [];



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
        private clientService: ClientService,
        private reparateurService: ReparateurService,
        private refModeGestionService: RefModeGestionService,
        public principal: Principal,
        private route: ActivatedRoute,
        private router: Router,
        private viewSinisterPecService: ViewSinisterPecService,
        private delegationService: DelegationService,
        private raisonPecService: RaisonPecService,
        private siniterPecPopupService: SinisterPecPopupService,
        private eventManager: JhiEventManager,
        private refBaremeService: RefBaremeService,
        private agenceAssuranceService: AgencyService,
        private refCompagnieService: PartnerService,
        private observationService: ObservationService,
        private demandePecService: DemandSinisterPecService,
        private pieceJointeService: PieceJointeService,
        private refBaremePopupDetailService: RefBaremePopupDetailService,
        private refPackService: RefPackService,
        private dateUtils: JhiDateUtils,
        private confirmationDialogService: ConfirmationDialogService,
        private expertService: ExpertService,
        private owerDateUtils: DateUtils,
        private userExtraService: UserExtraService,
        private refPositionGaService: RefPositionGaService,
        private conventionService: ConventionService,
        private sinisterPecService: SinisterPecService,
        private apecService: ApecService,
        private notificationAlerteService: NotifAlertUserService,
        private quotationService: QuotationService,
        private detailsPiecesService: DetailsPiecesService,
        private stampDutyService: StampDutyService,
        private sanitizer: DomSanitizer,
        private userPartnerModeService: UserPartnerModeService

    ) {

    }
    ngOnInit() {
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
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
        })
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = true;
        this.typeServiceId = 11;
        this.casDeBareme = false; // TODO : verifiy utility
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => {
                this.sysvilles = res.json;
                this.sysvillesRep = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.raisonPecService.findAllMotifReopened().subscribe((res) => {
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


        });
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

        this.reparateurObservations = [
            { id: 1, label: "MECATOL" },
            { id: 2, label: "SOCIETE EL FATH" },
            { id: 3, label: "PROCAR GARAGE" }
        ];

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

    downloadPieceFile1() {
        if (this.pieceFiles1) {
            saveAs(this.pieceFiles1);
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
            if (params['idPecRefused']) {
                this.pecId = params['idPecRefused'];
                this.enableField = false;
                this.sinisterPec.prestAApprouv = false;
                this.isRefused = true;
                this.showMotifAction = true;
                this.decisionPrest = false;
                this.motfReopened = true;
                this.sendPrest = false;
                this.isSave = false;
            }
            if (params['idPecCanceled']) {
                this.pecId = params['idPecCanceled'];
                this.enableField = false;
                this.sinisterPec.prestAApprouv = false;
                this.isCanceled = true;
                this.showMotifAction = true;
                this.decisionPrest = false;
                this.motfReopened = true;
                this.sendPrest = false;
                this.isSave = false;
            }
            if (params['idPecToApprouve']) {
                console.log("testRouteidPecToApprouve");
                this.pecId = params['idPecToApprouve'];
                this.enableField = true;
                this.sinisterPec.prestAApprouv = true;
                this.showMotifAction = true;
                this.isApprouved = true;
                this.decisionPrest = true;
                this.motfReopened = false;
                this.sendPrest = false;
                this.isSave = true;

            }
            if (params['idPecInProgress']) {
                console.log("testRouteidPecInProgress");
                this.pecId = params['idPecInProgress'];
                this.enableField = true;
                this.sinisterPec.prestAApprouv = false;
                this.decisionPrest = true;
                this.motfReopened = false;
                this.isDecided = true;
                this.sendPrest = false;
                this.isFromInProgress = true;
                this.sinisterPec.isDecidedFromInProgress = true;
                this.isSave = true;
            }
            if (params['sinisterPecId']) {
                this.pecId = params['sinisterPecId'];
                this.affectReparateur = true;

            }
            if (params['sinisterPecAnnuleAffectationId']) {
                this.pecId = params['sinisterPecAnnuleAffectationId'];
                this.annulerAffectReparateur = true;
                this.affectReparateur = true;
                this.raisonPecService.findMotifsByOperation(4).subscribe((subRes: RaisonPec[]) => {
                    this.motifs = subRes;
                });
            }
            if (params['idPecAccWithReserve']) {
                this.pecId = params['idPecAccWithReserve'];
                this.enableField = true;
                this.sinisterPec.prestAApprouv = false;
                this.isAcceptedWithReserve = true;
                this.showMotifAction = true;
                this.decisionPrest = false;
                this.motfReopened = false;
                this.sendPrest = true;
                this.isSave = true;
            }
            if (params['idPecAccWithChangeStatus']) {
                this.pecId = params['idPecAccWithChangeStatus'];
                this.enableField = true;
                this.sinisterPec.prestAApprouv = false;
                this.isAcceptedWithChangeStatus = true;
                this.showMotifAction = true;
                this.decisionPrest = false;
                this.motfReopened = false;
                this.sendPrest = false;
                this.isSave = false;
            }
            if (params['idPecChangeStatus']) {
                this.pecId = params['idPecChangeStatus'];
                this.enableField = false;
                this.sinisterPec.prestAApprouv = false;
                this.isSinPecChangeStatus = true;
                this.showMotifAction = true;
                this.decisionPrest = false;
                this.motfReopened = false;
                this.sendPrest = false;
                this.isSave = false;
            }
            if (params['idPecRefusedCanceled']) {
                this.pecId = params['idPecRefusedCanceled'];
                this.enableField = false;
                this.sinisterPec.prestAApprouv = false;
                this.isSinPecRefusedCanceled = true;
                this.showMotifAction = true;
                this.decisionPrest = false;
                this.motfReopened = true;
                this.sendPrest = false;
                this.isSave = false;
                this.reopenPrest = true;
            }
            if (params['idPecBonSortie']) {
                this.pecId = params['idPecBonSortie'];
                this.enableField = false;
                this.isSinPecBonSortie = true;
                this.isSave = false;
            }
            if (params['idPecVerOrgPrint']) {
                this.pecId = params['idPecVerOrgPrint'];
                this.enableField = false;
                this.isSinPecVerifOrgPrinted = true;
                this.isSave = false;
            }

            if (params['idCancPec']) {
                this.pecId = params['idCancPec'];
                this.enableField = false;
                this.isCancPec = true;
                this.isSave = false;
                this.annulPrest = true;
            }
            if (params['idConfirmCancPec']) {
                this.pecId = params['idConfirmCancPec'];
                this.enableField = false;
                this.isConfirmCancPec = true;
                this.isSave = false;
                this.confirmPrest = true;
            }
            if (params['sinisterPecMissionExpertId']) {
                console.log("iciii log  mission expert");
                this.pecId = params['sinisterPecMissionExpertId'];
                this.expertMissionary = true;


            }
            if (params['sinisterPecCancelExpertId']) {
                console.log("iciii log annnuler mission expert");
                this.pecId = params['sinisterPecCancelExpertId'];
                this.expertMissionary = true;
                this.cancelExpertMissionary = true;
                this.raisonPecService.findMotifsByOperation(5).subscribe((subRes: RaisonPec[]) => {
                    this.reasons = subRes;
                });

            }
            if (params['idRefusPec']) {
                this.pecId = params['idRefusPec'];
                this.enableField = false;
                this.isRefusPec = true;
                this.isSave = false;
                this.annulPrest = true;
            }
            if (params['idConfirmRefPec']) {
                this.pecId = params['idConfirmRefPec'];
                this.enableField = false;
                this.isConfirmRefusPec = true;
                this.isSave = false;
                this.confirmPrest = true;
            }

            // Get pec prestation
            if (this.pecId) {
                this.prestationPECService.findPrestationPec(this.pecId).subscribe((pecRes: SinisterPec) => {
                    this.sinisterPec = pecRes;
                    if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                        this.showExpertAttachmentAndButtons();
                        this.getPhotoPlusPec();

                        if (this.isAcceptedWithChangeStatus) {
                            this.getDevis(this.sinisterPec);
                        }

                        if (this.cancelExpertMissionary) {
                            this.initCancelAffectExpert();
                        }

                        if (this.expertMissionary == true && this.cancelExpertMissionary == false) {
                            this.initAffectExpert();
                        }

                        if (this.isApprouved) {
                            this.sinisterPec.prestAApprouv = true;
                        }

                        if (this.sinisterPec.pointChoc !== null) {
                            this.pointChoc = this.sinisterPec.pointChoc;
                            this.pointChocObligatoire();
                        }

                        if (this.isSinPecBonSortie) {
                            this.showBlockBonSortie();
                        }
                        if (this.isFromInProgress) {
                        this.prestationPECService.getAutresPiecesJointes(this.sinisterPec.id).subscribe((resImprime) => {
                            this.piecesJointesAttachments = resImprime.json;
        
                        });
                        }
                        this.testRefernceSinister();

                        this.verifTiers();
                        if (this.sinisterPec.reasonCanceledId == 91) {
                            this.disabledMotif = true;
                            this.raisonPecService.find(91).subscribe((resRais: RaisonPec) => {
                                this.reasonsCanceled.push(resRais);
                            });
                        }
                        if (this.sinisterPec.reasonCanceledId == 154) {
                            this.disabledMotif = true;
                            this.raisonPecService.find(154).subscribe((resRais: RaisonPec) => {
                                this.reasonsCanceled.push(resRais);
                            });
                        }
                        if (this.sinisterPec.reasonCanceledId == 155) {
                            this.disabledMotif = true;
                            this.raisonPecService.find(155).subscribe((resRais: RaisonPec) => {
                                this.reasonsCanceled.push(resRais);
                            });
                        }
                        if (this.isApprouved) {
                            if (this.sinisterPec.decision == 'ACC_WITH_CHANGE_STATUS') {
                                this.showModeToModif = true;
                                this.changeModeGestion(this.sinisterPec.modeModifId);
                            } else {
                                this.showModeToModif = false;
                                this.changeModeGestion(this.sinisterPec.modeId);
                            }
                        } else if (this.isAcceptedWithChangeStatus || this.isSinPecChangeStatus) {
                            this.changeModeGestion(this.sinisterPec.modeModifId);
                        } else {
                            this.changeModeGestion(this.sinisterPec.modeId);
                        }
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
                                    if (this.vehicule.datePCirculation) {
                                        this.datePCirculation = this.principal.parseDateJour(this.vehicule.datePCirculation);
                                    }
                                    if (this.affectReparateur && this.sinisterPec.gouvernoratRepId != null && this.sinisterPec.gouvernoratRepId != undefined) {
                                        this.selectedGouvernorat = this.sinisterPec.gouvernoratRepId;
                                        this.listReparateursByGouvernorat(this.selectedGouvernorat)

                                    }

                                    this.initContrat();
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

                        this.getTiers();

                        if (this.sinisterPec.baremeId) {
                            this.LoadBareme(this.sinisterPec.baremeId);
                        }

                        if (this.sinisterPec.reparateurId != null && this.annulerAffectReparateur) {
                            this.initCancelAffectReparateur();
                        }
                    } else {
                        this.router.navigate(["/accessdenied"]);
                    }

                });
                this.sinisterPecService.getImprimeAttachments(this.pecId).subscribe((resImprime) => {
                    this.imprimesAttachments = resImprime.json;
                });
                this.observationService.findByPrestation(this.pecId).subscribe((subRes: ResponseWrapper) => {
                    this.observationss = subRes.json;
                    if (this.observationss.length == 0) {
                        this.observationss = [];
                    }
                });
            }
        });
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
        if (this.sinister.vehicleId) {
            this.refPackService.find(this.vehicule.packId).subscribe((res: RefPack) => {
                this.pack = res;
                this.refPack = res;
                this.listModeGestionByPack = this.pack.modeGestions;
                this.apecSettings = this.refPack.apecSettings;
                if (this.refPack.conventionId != null) {
                    this.conventionService.find(this.refPack.conventionId).subscribe((convention: Convention) => {
                        if (convention.conventionAmendments.length > 0) {
                            convention.conventionAmendments.forEach(convensionAmendment => {
                                if (convensionAmendment.oldRefPackId == this.refPack.id) {
                                    const dateToday = (new Date()).getTime();
                                    const dateEffet = (new Date(convensionAmendment.startDate.toString())).getTime();
                                    const dateFin = (new Date(convensionAmendment.endDate.toString())).getTime();
                                    if (dateToday >= dateEffet && dateToday < dateFin) {
                                        if (!this.testConvention) {
                                            this.testConvention = true;
                                            this.listModeGestionByPack = [];
                                        }
                                        convensionAmendment.refPack.apecSettings.forEach(apSett => {
                                            this.apecSettingsAvenant.push(apSett);
                                        });
                                        convensionAmendment.refPack.modeGestions.forEach(modeGestion => {
                                            this.listModeGestionByPack.push(modeGestion);
                                        });
                                    }
                                }
                            });
                        }
                        this.initConvention();
                    });
                } else {
                    this.initConvention();
                }

                this.contratAssuranceService.find(this.sinister.contractId).subscribe((contractRes: ContratAssurance) => {
                    this.contratAssurance = contractRes;
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
                    this.isContractLoaded = false;
                    if (this.contratAssurance) {
                        this.isContractLoaded = true;
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
            });
        }
    }

    initConvention() {

        this.sinister.vehicleId = this.vehicule.id;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtraCnv = usr;
                this.usersPartnerModes = this.userExtraCnv.userPartnerModes;
                if (this.usersPartnerModes.length > 0) {
                    this.usersPartnerModes.forEach(usrPrtnMd => {
                        this.listModeGestionByPack.forEach(mdGstBCnv => {
                            if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.vehicule.partnerId) {
                                this.listModeByCnvByUser.push(mdGstBCnv);
                            }
                        });
                    });
                } else {
                    this.listModeByCnvByUser = this.listModeGestionByPack;
                }
                var cache = {};
                this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                    return cache[elem.id] ? 0 : cache[elem.id] = 1;
                });
            });
        });

        if (this.sinisterPec.prestAApprouv || this.isAcceptedWithChangeStatus || this.isSinPecChangeStatus) {
            if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                if (this.sinisterPec.primaryQuotationId != null && this.sinisterPec.primaryQuotationId != undefined) {
                    this.showValeurANeufExpert = true;
                    this.quotationService.find(this.sinisterPec.primaryQuotationId).subscribe((res) => {  // Find devid By ID
                        this.quotation = res;
                        if (this.quotation.preliminaryReport == true) {
                            this.preliminaryReport = true;
                        } else {
                            this.preliminaryReport = false;
                        }

                        if (this.apecSettingsAvenant.length !== 0) {
                            for (let index = 0; index < this.apecSettingsAvenant.length; index++) {
                                const element = this.apecSettingsAvenant[index];
                                if (element.mgntModeId == this.sinisterPec.modeId) {
                                    this.existMode = true;
                                    if (element.operator == Operator.GREATER) {
                                        if (this.quotation.ttcAmount > element.ceiling) {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                            this.testModifStatus = true;
                                            break;
                                        } else {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                            this.testModifStatus = false;
                                            break;
                                        }
                                    } else {
                                        if (this.quotation.ttcAmount <= element.ceiling) {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                            this.testModifStatus = true;
                                            break;
                                        } else {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                            this.testModifStatus = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (this.apecSettingsAvenant.length == 0 || this.existMode == false) {
                            for (let index = 0; index < this.apecSettings.length; index++) {
                                const element = this.apecSettings[index];
                                if (element.mgntModeId == this.sinisterPec.modeId) {
                                    if (element.operator == Operator.GREATER) {
                                        if (this.quotation.ttcAmount > element.ceiling) {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                            this.testModifStatus = true;
                                            break;
                                        } else {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                            this.testModifStatus = false;
                                            break;
                                        }
                                    } else {
                                        if (this.quotation.ttcAmount <= element.ceiling) {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                            this.testModifStatus = true;
                                            break;
                                        } else {
                                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                            this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                            this.testModifStatus = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            } else {
                this.showValeurANeufExpert = true;
                this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((res) => {  // Find devid By ID
                    this.quotation = res;
                    this.expertDecision = this.quotation.expertDecision;
                    if (this.quotation.preliminaryReport == true) {
                        this.preliminaryReport = true;
                    } else {
                        this.preliminaryReport = false;
                    }
                    if (this.apecSettingsAvenant.length !== 0) {
                        for (let index = 0; index < this.apecSettingsAvenant.length; index++) {
                            const element = this.apecSettingsAvenant[index];
                            if (element.mgntModeId == this.sinisterPec.modeId) {
                                this.existMode = true;
                                if (element.operator == Operator.GREATER) {
                                    if (this.quotation.ttcAmount > element.ceiling) {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                        this.testModifStatus = true;
                                        break;
                                    } else {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                        this.testModifStatus = false;
                                        break;
                                    }
                                } else {
                                    if (this.quotation.ttcAmount <= element.ceiling) {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                        this.testModifStatus = true;
                                        break;
                                    } else {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                        this.testModifStatus = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (this.apecSettingsAvenant.length == 0 || this.existMode == false) {
                        for (let index = 0; index < this.apecSettings.length; index++) {
                            const element = this.apecSettings[index];
                            if (element.mgntModeId == this.sinisterPec.modeId) {
                                if (element.operator == Operator.GREATER) {
                                    if (this.quotation.ttcAmount > element.ceiling) {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                        this.testModifStatus = true;
                                        break;
                                    } else {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                        this.testModifStatus = false;
                                        break;
                                    }
                                } else {
                                    if (this.quotation.ttcAmount <= element.ceiling) {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.APPROUVER_APEC;
                                        this.testModifStatus = true;
                                        break;
                                    } else {
                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                        this.sinisterPec.stepId = PrestationPecStep.VALIDATION_APEC;
                                        this.testModifStatus = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }

    }

    showExpertAttachmentAndButtons() {
        this.showActeDeCession = true;
        this.showCarteGrise = true;
        this.showConstat = true;
        this.showActeDeCessionAttachment = false;
        this.showCarteGriseAttachment = false;
        this.showConstatAttachment = false;
        if (this.sinisterPec.stepId == 8) {
            this.buttonMissExpert = true;
        }

        if (this.sinisterPec.stepId == 11) {
            this.buttonAffReparateur = true;
        }

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(85, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtra = usr;
                    if (this.userExtra.profileId === 26 || this.permissionToAccess.canAjoutDemandPecExpertise) {
                        this.isCentreExpertise = true;
                    } else {
                        if (this.sinisterPec.expertId !== undefined && this.sinisterPec.expertId !== null) {
                            this.isCentreExpertise = true;
                        } else {
                            this.isCentreExpertise = false;
                        }
                    }
                });
            });
        });

    }

    showBlockBonSortie() {

        this.prestationPECService.findAccordValidBySinisterPec(this.pecId).subscribe((res) => {
            this.apecsValid = res.json;
            this.apecTotale.participationGa = 0;
            this.apecTotale.droitDeTimbre = 0;
            this.apecTotale.participationAssure = 0;
            this.apecTotale.participationVetuste = 0;
            this.apecTotale.participationTva = 0;
            this.apecTotale.participationRpc = 0;
            this.apecTotale.fraisDossier = 0;
            this.apecTotale.avanceFacture = 0;
            this.apecTotale.depacementPlafond = 0;
            this.apecTotale.estimationFranchise = 0;
            this.apecTotale.regleProportionnel = 0;
            if (this.apecsValid && this.apecsValid.length > 0) {
                this.apecsValid.forEach(apec => {
                    this.apecTotale.participationGa = + (this.apecTotale.participationGa + apec.participationGa).toFixed(3);
                    this.apecTotale.droitDeTimbre = + (this.apecTotale.droitDeTimbre + apec.droitDeTimbre).toFixed(3);
                    this.apecTotale.participationAssure = + (this.apecTotale.participationAssure + apec.participationAssure).toFixed(3);
                    this.apecTotale.participationVetuste = + (this.apecTotale.participationVetuste + apec.participationVetuste).toFixed(3);
                    this.apecTotale.participationTva = + (this.apecTotale.participationTva + apec.participationTva).toFixed(3);
                    this.apecTotale.participationRpc = + (this.apecTotale.participationRpc + apec.participationRpc).toFixed(3);
                    this.apecTotale.fraisDossier = + (this.apecTotale.fraisDossier + apec.fraisDossier).toFixed(3);
                    this.apecTotale.avanceFacture = + (this.apecTotale.avanceFacture + apec.avanceFacture).toFixed(3);
                    this.apecTotale.depacementPlafond = + (this.apecTotale.depacementPlafond + apec.depacementPlafond).toFixed(3);
                    this.apecTotale.estimationFranchise = + (this.apecTotale.estimationFranchise + apec.estimationFranchise).toFixed(3);
                    this.apecTotale.regleProportionnel = + (this.apecTotale.regleProportionnel + apec.regleProportionnel).toFixed(3);
                });
            }
        });

    }

    initCancelAffectExpert() {

        this.expertService.query().subscribe((listExpert: ResponseWrapper) => {
            listExpert.json.forEach(exp => {
                this.expertsAffectation.push(exp);
            });
        });
        if (this.sinisterPec.expertId !== null) {
            this.nbreMissionByExpert(this.sinisterPec.expertId);
        }

    }

    initAffectExpert() {

        if (this.sinisterPec.reparateurId != null && this.sinisterPec.reparateurId != undefined) {
            this.selectedGouvernorat = this.sinisterPec.gouvernoratRepId;
            this.expertsAffectation = [];
            if (this.selectedGouvernorat != null && this.selectedGouvernorat != undefined) {
                this.listExpertsByGouvernorat(this.selectedGouvernorat);
            }
        }

    }

    initCancelAffectReparateur() {

        this.reparateurService.query().subscribe((res) => {
            res.json.forEach(rep => {

                this.reparateurs.push(rep);
            });
            this.reparateursAffecte = this.reparateurs;
            if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                this.nbreMissionByReparateur(this.sinisterPec.reparateurId);
                this.reparateurService.find(this.sinisterPec.reparateurId).subscribe((res: Reparateur) => {
                    this.capacite = res.capacite;
                    this.selectedGouvernorat = res.gouvernoratId;
                    if (this.sinisterPec.dateRDVReparation) {
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
                });
            }

        });

    }

    MissionerExpert() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer votre choix ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = PrestationPecStep.EXPERT_ADVICE;
                    if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                        const dateAffecExpert = new Date();
                        this.sinisterPec.dateAffectExpert = this.dateAsYYYYMMDDHHNNSSLDT(dateAffecExpert);
                        console.log(this.sinisterPec.dateAffectExpert);

                        this.sinisterPecService.update(this.sinisterPec).subscribe((res) => {
                            this.sinisterPec = res;
                            console.log(res);
                            this.sinisterPecService.generateOrdreMissionExpert(this.sinisterPec).subscribe((res) => {
                                // window.open(res.headers.get('pdfname'), '_blank');
                            });
                            if (this.oneClickForButton19) {
                                this.oneClickForButton19 = false;
                                if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                                    this.sendNotifAffectExpertDevisInit("nouvelle mission");
                                }
                                else {
                                    this.sendNotifAffectExpertDevisCompl("nouvelle mission devis compl");

                                }

                            }
                            this.router.navigate(['../missionner-expert']);
                        });

                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));


    }

    ajoutNouvelpieceJointe() {
        this.ajoutNouvelpieceform = true;
        this.ajoutNouvelpiece = false;
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


    annulerMissionExpert() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregisté  ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                        if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                            this.sinisterPec.expertDecision = null;
                            if (this.sinisterPec.primaryQuotationId !== undefined && this.sinisterPec.primaryQuotationId !== null) {
                                this.apecService.deleteApecByDevis(this.sinisterPec.primaryQuotationId).subscribe((resDel) => {
                                    const id = this.sinisterPec.expertId;
                                    this.sinisterPec.expertId = null;
                                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                    this.sinisterPec.stepId = PrestationPecStep.MISSIONNER_EXPERT;
                                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                        this.sinisterPec = res;
                                        this.sendNotifCancelAffectExpert(id, "Mission annuler");
                                        this.router.navigate(['../cancel-missionner-expert']);
                                    });
                                });
                            } else {
                                const id = this.sinisterPec.expertId;
                                this.sinisterPec.expertId = null;
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId = PrestationPecStep.MISSIONNER_EXPERT;
                                this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.sendNotifCancelAffectExpert(id, "Mission annuler");
                                    this.router.navigate(['../cancel-missionner-expert']);
                                });
                            }
                        } else {
                            this.canCancelMissionementExpert = false;
                        }

                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    getTiers() {

        this.tiersService.findAllByPEC(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
            this.tiers = subRes.json;
            this.agentCompanyRO = true;
            if (this.tiers === null) {
                this.tiers = [];
                this.verifTiers();
            } else if (this.tiers.length > 0) {
                this.verifTiers();
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

    }


    nbreMissionByReparateur(id) {
        if (id !== null && id !== undefined) {
            this.affectReparateurWithCapacity = false;
            this.prestationPECService.getNbreMissionReparator(id).subscribe((res: any) => {
                this.nbmissionreparateur = res.json;
                this.reparateurService.find(id).subscribe((res: Reparateur) => {
                    this.capacite = res.capacite;
                })
            })
        }
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

    dataURItoBlob(dataURI) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: 'image/jpeg' });
        return blob;
    }

    dataURItoBlobAtt(dataURI, extention, type) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: type + '/' + extention });
        return blob;
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
        this.verifTiers();
        this.nameAgence = false;
        this.otherField = false;
        this.nbrTiersResponsable = 0;
        this.tiers.forEach(tr => {
            if (tr.responsible) {
                this.nbrTiersResponsable++;
            }
        });

        if (this.nbrTiersResponsable >= 1 && this.tier.responsible) {
            this.tierResponsableVerif = false;
            this.newFormTiers = true;
            this.showFrmTiers = true;
        } else {
            this.tierResponsableVerif = true;
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
            if (this.tiers.length == this.sinisterPec.vehicleNumber - 1) this.newFormTiers == false;
            this.verifTiers();
        }

        //this.verifTiers();


    }

    changeAssujettie(etat) {
        if (etat == false) {
            this.vehicule.assujettieTVA = false;
        } else {
            this.vehicule.assujettieTVA = true;
        }
    }

    listReparateursByGouvernorat(id) {
        this.reparateursAffecte = [];
        this.reparateurs = [];
        const age = new Date().getFullYear() - new Date(this.datePCirculation).getFullYear();
        this.test = false;
        this.reparateurService.findByGouvernorat(id).subscribe((listReparateur: Reparateur[]) => {
            this.reparateurs = listReparateur;
            if (this.reparateurs.length > 0) {
                for (let index = 0; index < this.reparateurs.length; index++) {
                    const reparateur = this.reparateurs[index];
                    if (!reparateur.isBloque && reparateur.isActive) {
                        this.test = false;
                        // filtre selon partner et mode gestion
                        for (let index = 0; index < reparateur.garantieImpliques.length; index++) {
                            const garantieImplique = reparateur.garantieImpliques[index];
                            if (this.sinisterPec.partnerId == garantieImplique.partnerId) {
                                for (let index2 = 0; index2 < garantieImplique.refModeGestions.length; index2++) {
                                    const refModeGestion = garantieImplique.refModeGestions[index2];
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
                            if (age > 10 && reparateur.isMultiMarque) {
                                for (let index4 = 0; index4 < reparateur.orientations.length; index4++) {
                                    const orientation = reparateur.orientations[index4];
                                    if (orientation.refAgeVehiculeId == 3 || orientation.refAgeVehiculeId == 4) {
                                        for (let ind = 0; ind < orientation.refMarques.length; ind++) {
                                            const marque = orientation.refMarques[ind];
                                            if (this.vehicule.marqueLibelle == marque.label) {
                                                this.reparateursAffecte.push(reparateur);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (age < 10 || age == 10) {
                                for (let index5 = 0; index5 < reparateur.orientations.length; index5++) {
                                    const orientation = reparateur.orientations[index5];
                                    if (orientation.refAgeVehiculeId == 2 || orientation.refAgeVehiculeId == 1 || orientation.refAgeVehiculeId == 4 || orientation.refAgeVehiculeId == 3) {
                                        for (let ind1 = 0; ind1 < orientation.refMarques.length; ind1++) {
                                            const marque = orientation.refMarques[ind1];
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
                if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
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


    reopen() {
        //this.enableField = true;
        this.isSave = true;
        this.reopenPrest = false;
        this.showMotifAction = false;
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

    testRefernceSinister() {
        if (this.sinisterPec.companyReference !== null && this.sinisterPec.companyReference !== undefined && this.sinisterPec.companyReference !== "") {
            this.cmpRf = true;
        } else {
            this.cmpRf = false;
        }
    }

    addForm() {
        this.nbrTiers = 0;
        this.showResponsible = true;

        this.tiers.forEach(element => {
            if (element.deleted == true) {
            } else {
                this.nbrTiers++;                
            }
            if (element.responsible)
            {
                this.showResponsible = false;
            }
        });
        this.verifTiers();
        this.tierRespMin = true;
        this.showFrmTiers = true;
        this.agentCompanyRO = false;
        if (this.sinisterPec.vehicleNumber <= this.nbrTiers + 1) {
            this.showAlertTiers = true;
            this.showFrmTiers = false;
        } else {
            this.showAlertTiers = false;
        }
    }

    envoyer() {



        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                this.sinisterPec.stepId = 3;
                this.sinisterPec.approvPec = null;
                this.sinisterPec.decision = null;
                this.updatePec();
                this.sendNotifReserveLifted('pecReserveLifted');
                this.alertService.success('auxiliumApp.sinisterPec.envoyerReserveLifted', null, null);
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    nbreMissionByExpert(id) {
        this.sinisterPecService.getNbreMissionExpert(id).subscribe((res: any) => {
            this.nbmissionExpert = res.json;
        })
    }

    accepter() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinisterPec.oldStepModifSinPec !== undefined && this.sinisterPec.oldStepModifSinPec !== null && this.sinisterPec.debloque == true) {
                    console.log("deblocage-------------------------------------------------");
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = this.sinisterPec.oldStepModifSinPec;
                    this.sinisterPec.oldStepModifSinPec = null;
                    this.sinisterPec.decision = 'ACCEPTED';
                    this.sinisterPec.modeId = this.sinisterPec.modeModifId;
                    this.sinisterPec.debloque = null;
                    if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                        if (this.sinisterPec.primaryQuotationId !== undefined && this.sinisterPec.primaryQuotationId !== null) {
                            this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                this.apecModifStatus = res;
                                let etatApec = this.apecModifStatus.etat;
                                if (this.apecModifStatus.id !== undefined && this.apecModifStatus.id !== null) {
                                    this.apecService.deleteApecByDevis(this.sinisterPec.primaryQuotationId).subscribe((resDel) => {

                                        this.quotation.statusId = 4;
                                        let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                                        listPieces.forEach(listPieceDetails => {
                                            listPieceDetails.isModified = false;
                                        });
                                        this.quotation.listPieces = listPieces;
                                        this.quotation.ttcAmount = this.ttcAmount;
                                        this.quotation.marketValue = this.sinisterPec.valeurVenaleExpert;
                                        this.quotation.priceNewVehicle = this.sinisterPec.valeurANeufExpert;
                                        this.sinisterPec.quotation = this.quotation;
                                        this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                            this.sinisterPec = res;
                                            this.apecService.generateAccordPrisCharge(this.sinisterPec, this.sinisterPec.primaryQuotationId, true, 0, this.labelAccordDebloquage).subscribe((resPdf) => {
                                                this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                                    this.apecModifStatus = res;
                                                    this.apecModifStatus.etat = etatApec;
                                                    this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                        this.router.navigate(['sinister-pec-modification-status']);
                                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                    });

                                                });
                                            });
                                        });
                                    });
                                } else {
                                    this.updatePec();
                                    this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                }
                            });
                        } else {
                            this.updatePec();
                            this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                        }

                    } else {

                        this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((resQuote) => {
                            this.quotation = resQuote;
                            this.apecService.findByQuotation(this.quotation.id).subscribe((res: Apec) => {
                                this.apecModifStatus = res;
                                let etatApec = this.apecModifStatus.etat;

                                this.detailsPiecesService.deleteByQuery(this.sinisterPec.primaryQuotationId).subscribe((response) => {
                                    this.detailsPiecesService.deleteByQueryMP(this.sinisterPec.id).subscribe((response) => {
                                        this.quotation.id = this.sinisterPec.primaryQuotationId;
                                        this.quotation.statusId = 4;
                                        let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                                        listPieces.forEach(listPieceDetails => {
                                            listPieceDetails.isModified = false;
                                            listPieceDetails.id = null;
                                        });
                                        this.quotation.listPieces = listPieces;
                                        this.quotation.ttcAmount = this.ttcAmount;
                                        this.quotation.marketValue = this.sinisterPec.valeurVenaleExpert;
                                        this.quotation.priceNewVehicle = this.sinisterPec.valeurANeufExpert;
                                        this.sinisterPec.quotation = this.quotation;
                                        this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                            this.sinisterPec = res;
                                            this.apecService.deleteAPecDevisCompl(this.sinisterPec.id).subscribe((resDel) => {
                                                if (this.apecModifStatus.id !== undefined && this.apecModifStatus.id !== null) {
                                                    this.apecService.generateAccordPrisCharge(this.sinisterPec, this.sinisterPec.primaryQuotationId, true, 3, this.labelAccordDebloquage).subscribe((resPdf) => {
                                                        this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                                            this.apecModifStatus = res;
                                                            if (etatApec !== 17 && this.sinisterPec.stepId !== 16) {
                                                                this.apecModifStatus.etat = etatApec;
                                                                this.apecService.update(this.apecModifStatus).subscribe((res) => {

                                                                    this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                                        this.router.navigate(['sinister-pec-modification-status']);
                                                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                                    });

                                                                });
                                                            } else {
                                                                if (this.testModifStatus == true) {
                                                                    this.apecModifStatus.etat = 0;
                                                                    this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                        this.sinisterPec.stepId = 100;

                                                                        this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                                            this.router.navigate(['sinister-pec-modification-status']);
                                                                            this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                                        });

                                                                    });
                                                                } else {
                                                                    this.apecModifStatus.etat = 6;
                                                                    this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                                        this.sinisterPec.stepId = 106;

                                                                        this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                                            this.router.navigate(['sinister-pec-modification-status']);
                                                                            this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                                        });

                                                                    });
                                                                }
                                                            }

                                                        })
                                                    });
                                                } else {
                                                    this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                        this.router.navigate(['sinister-pec-modification-status']);
                                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                    });
                                                }
                                            });
                                        });
                                    });
                                });
                            });
                        });


                    }
                } else {
                    if (this.sinisterPec.oldStep == 20) {
                        console.log("constatPreliminaire-------------------------------------------------");
                        this.sinisterPec.modeId = this.sinisterPec.modeModifId;
                        this.sinisterPec.decision = 'ACCEPTED';
                        if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                            if (this.sinisterPec.primaryQuotationId !== undefined && this.sinisterPec.primaryQuotationId) {
                                console.log("test2-----------------------------------");
                                if (this.sinisterPec.expertDecision == 'Accord pour réparation avec modification') {
                                    this.quotation.preliminaryReport = false;
                                    this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_QUOTE;
                                    this.sinisterPec.quotation = this.quotation;
                                    this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                        this.sinisterPec = res;
                                        this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                        this.router.navigate(['sinister-pec-modification-status']);
                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                    });
                                } else if (this.sinisterPec.expertDecision == 'Accord pour réparation') {
                                    if (this.testModifStatus == true) {
                                        this.apecService.deleteApecByDevis(this.sinisterPec.primaryQuotationId).subscribe((resDel) => {
                                            this.apecService.generateAccordPrisCharge(this.sinisterPec, this.sinisterPec.primaryQuotationId, true, 0, this.labelAccordCP).subscribe((resPdf) => {
                                                this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                                    this.apecModifStatus = res;
                                                    this.apecModifStatus.etat = 0;
                                                    this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId = 100;
                                                        this.quotation.preliminaryReport = false;
                                                        this.sinisterPec.quotation = this.quotation;
                                                        this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                                            this.sinisterPec = res;
                                                            this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                                            this.router.navigate(['sinister-pec-modification-status']);
                                                            this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                        });

                                                    });
                                                });
                                            });
                                        });
                                    } else {
                                        this.apecService.deleteApecByDevis(this.sinisterPec.primaryQuotationId).subscribe((resDel) => {
                                            this.apecService.generateAccordPrisCharge(this.sinisterPec, this.sinisterPec.primaryQuotationId, true, 0, this.labelAccordCP).subscribe((resPdf) => {
                                                this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                                    this.apecModifStatus = res;
                                                    this.apecModifStatus.etat = 6;
                                                    this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                                        this.sinisterPec.stepId = 106;
                                                        this.quotation.preliminaryReport = false;
                                                        this.sinisterPec.quotation = this.quotation;
                                                        this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                                            this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                                            this.router.navigate(['sinister-pec-modification-status']);
                                                            this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                        });

                                                    });
                                                });
                                            });
                                        });
                                    }
                                } else {
                                    this.sinisterPec.stepId = this.sinisterPec.oldStepModifSinPec;
                                    this.quotation.preliminaryReport = false;
                                    this.sinisterPec.oldStepModifSinPec = null;
                                    this.sinisterPec.quotation = this.quotation;
                                    this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                        this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                        this.router.navigate(['sinister-pec-modification-status']);
                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                    });
                                }

                            } else {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId = 21;
                                this.updatePec();
                                this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                            }


                        } else {
                            this.sinisterPec.expertDecision = this.expertDecision;
                            if (this.sinisterPec.expertDecision == 'Accord pour réparation avec modification') {
                                this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_QUOTE;
                                this.sinisterPec.preliminaryReport = true;
                                this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                    this.sinisterPec = res;
                                    this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                    this.router.navigate(['sinister-pec-modification-status']);
                                    this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                });
                            } else {
                                this.detailsPiecesService.deleteByQuery(this.sinisterPec.primaryQuotationId).subscribe((response) => {
                                    this.detailsPiecesService.deleteByQueryMP(this.sinisterPec.id).subscribe((response) => {
                                        this.quotation.id = this.sinisterPec.primaryQuotationId;
                                        this.quotation.statusId = 4;
                                        let listPieces = this.detailsPiecesMO.concat(this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
                                        listPieces.forEach(listPieceDetails => {
                                            listPieceDetails.isModified = false;
                                            listPieceDetails.id = null;
                                        });
                                        this.quotation.listPieces = listPieces;
                                        this.quotation.ttcAmount = this.ttcAmount;
                                        this.quotation.preliminaryReport = false;
                                        this.sinisterPec.quotation = this.quotation;
                                        this.sinisterPec.expertDecision = this.expertDecision;
                                        this.sinisterPecService.updatePecForQuotation(this.sinisterPec, true).subscribe((res) => {
                                            this.sinisterPec = res;
                                            this.apecService.deleteAPecDevisCompl(this.sinisterPec.id).subscribe((resDel) => {

                                                if (this.sinisterPec.expertDecision == 'Accord pour réparation avec modification') {
                                                    this.sinisterPec.stepId = PrestationPecStep.CONFIRMATION_QUOTE;
                                                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                                        this.sinisterPec = res;
                                                        this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                                        this.router.navigate(['sinister-pec-modification-status']);
                                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                    });
                                                } else if (this.sinisterPec.expertDecision == 'Accord pour réparation') {
                                                    this.apecService.generateAccordPrisCharge(this.sinisterPec, this.sinisterPec.primaryQuotationId, true, 3, this.labelAccordCP).subscribe((resPdf) => {
                                                        this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                                            this.apecModifStatus = res;
                                                            if (this.testModifStatus == true) {
                                                                this.apecModifStatus.etat = 0;
                                                                this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                                    this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resCPP) => {
                                                                        this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                                                        this.router.navigate(['sinister-pec-modification-status']);
                                                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                                    });
                                                                });
                                                            } else {
                                                                this.apecModifStatus.etat = 6;
                                                                this.apecService.update(this.apecModifStatus).subscribe((res) => {
                                                                    this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resCPP) => {
                                                                        this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                                                        this.router.navigate(['sinister-pec-modification-status']);
                                                                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                                    });
                                                                });
                                                            }
                                                        })
                                                    });
                                                } else {
                                                    this.sinisterPec.stepId = this.sinisterPec.oldStepModifSinPec;
                                                    this.sinisterPec.oldStepModifSinPec = null;
                                                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                                        this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resCPP) => {
                                                            this.sendNotifPecMSForCP('modifStatusChangementCharge');
                                                            this.router.navigate(['sinister-pec-modification-status']);
                                                            this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                                                        });
                                                    });
                                                }
                                            });
                                        });
                                    });
                                });
                            }

                        }

                    } else {
                        console.log("Changement du status-------------------------------------------------");
                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;

                        this.sinisterPec.stepId = 11;
                        this.sinisterPec.decision = 'ACCEPTED';
                        this.sinisterPec.modeId = this.sinisterPec.modeModifId;
                        this.sendNotifPecMS('accepteModifStatus');
                        this.updatePec();
                        console.log("test4-----------------------------------");
                        this.alertService.success('auxiliumApp.sinisterPec.accepterSinPec', null, null);
                    }
                }

            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    refuser() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinisterPec.oldStepModifSinPec !== undefined && this.sinisterPec.oldStepModifSinPec !== null && this.sinisterPec.debloque == true) {
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.debloque = null;
                    this.sinisterPec.stepId = this.sinisterPec.oldStepModifSinPec;
                    this.sinisterPec.oldStepModifSinPec = null;
                    this.updatePec();
                    this.alertService.success('auxiliumApp.sinisterPec.refuserSinPec', null, null);
                } else {
                    if (this.sinisterPec.oldStep == 20) {
                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                        this.sinisterPec.stepId = this.sinisterPec.oldStep;
                        this.sinisterPec.oldStep = null;
                        this.updatePec();
                        this.alertService.success('auxiliumApp.sinisterPec.refuserSinPec', null, null);
                    } else {
                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                        this.sinisterPec.stepId = 3;
                        this.sinisterPec.approvPec = null;
                        this.sinisterPec.decision = null;
                        this.sinisterPec.modeModifId = null;
                        this.sinisterPec.motifChangementStatus = 'Refus du  changement de statut';
                        this.sendNotifPecMS('refusModifStatus');
                        this.updatePec();
                        this.alertService.success('auxiliumApp.sinisterPec.refuserSinPec', null, null);
                    }

                }

            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }


    reprise() {

        this.confirmationDialogService.confirm('Confirmation', 'Etes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {

                if (this.isApprouved) {
                    this.updatePec();
                    this.alertService.success('auxiliumApp.sinisterPec.saveSinPec', null, null);
                }

                if (this.isAcceptedWithReserve) {
                    this.updatePec();
                    this.alertService.success('auxiliumApp.sinisterPec.saveSinPec', null, null);
                }
                if (this.isDecided) {
                    this.updatePec();
                    this.alertService.success('auxiliumApp.sinisterPec.saveSinPec', null, null);
                }

                if (this.isCanceled || this.isRefused || this.isSinPecRefusedCanceled) {
                    this.sinisterPec.dateReprise = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                    if (this.sinisterPec.fromDemandeOuverture) {
                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                        this.sinisterPec.stepId = 1;
                        this.sinisterPec.decision = null;
                        this.updatePec();
                        this.sendNotifReprisePrestation('reouvertureSinisterPec');
                        this.alertService.success('auxiliumApp.sinisterPec.reouvertSinPec', null, null);
                    } else {
                        this.reInitPec();
                    }
                }

            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    reInitPec() {
        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
        this.sinisterPec.oldStep = null;
        if (this.sinisterPec.repriseStep !== null && this.sinisterPec.repriseStep !== undefined) {
            this.sinisterPec.stepId = this.sinisterPec.repriseStep;
            this.sinisterPec.repriseStep = null;
            this.sinisterPec.decision = DecisionPec.ACCEPTED;
            this.sinisterPec.approvPec = ApprovPec.APPROVE;
        } else {
            this.sinisterPec.stepId = 3;
            this.sinisterPec.decision = null;
            this.sinisterPec.approvPec = null;
        }
        /*this.sinisterPec.stepId = 3;
        this.sinisterPec.decision = null;
        this.sinisterPec.oldStep = null;
        this.sinisterPec.lightShock = null;
        this.sinisterPec.receptionVehicule = null;
        this.sinisterPec.dateRDVReparation = null;
        this.sinisterPec.expertDecision = null;
        this.sinisterPec.oldStepModifSinPec = null;
        this.sinisterPec.reparateurId = null;
        this.sinisterPec.vehicleReceiptDate = null;
        this.sinisterPec.pieceGenerique = null;
        this.sinisterPec.expertId = null;
        if (this.sinisterPec.primaryQuotationId !== null && this.sinisterPec.primaryQuotationId !== undefined) {
            this.sinisterPec.primaryQuotation = null;
            this.detailsPiecesService.deleteByQuery(this.sinisterPec.primaryQuotationId).subscribe((response1) => {
                this.detailsPiecesService.deleteByQueryMP(this.sinisterPec.id).subscribe((response2) => {
                    this.apecService.deleteAPecDevisCompl(this.sinisterPec.id).subscribe((resDel) => {
                        this.sinisterPec.primaryQuotationId = null;
                        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resMP2) => {
                            this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                this.sendNotifReprisePrestation('reouvertureSinisterPec');
                                if (this.isRefused) { this.router.navigate(['/sinister-pec-refused']); }
                                if (this.isCanceled) { this.router.navigate(['/sinister-pec-canceled']); }
                                if (this.isSinPecRefusedCanceled) { this.router.navigate(['sinister-pec-reprise-prestation']); }
                                this.alertService.success('auxiliumApp.sinisterPec.reouvertSinPec', null, null);
                            });
                        });
                    });

                });
            });
        } else {*/
        this.updatePec();
        this.sendNotifReprisePrestation('reouvertureSinisterPec');
        this.alertService.success('auxiliumApp.sinisterPec.reouvertSinPec', null, null);
        //}
    }


    approuve() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {

                if (this.approveButton == true) {
                    this.approveButton = false;
                    /*this.sinisterPec.step = 3;
                      this.sinisterPec.decision = 'INPROGRESS';*/
                    switch (this.sinisterPec.decision) {
                        case 'CANCELED':
                            this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 9;
                            const dateAnnuleRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateAnnuleRefusePec);
                            break;
                        case 'ACC_WITH_RESRV':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 12;
                            break;
                        case 'ACC_WITH_CHANGE_STATUS':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 13;
                            if ((this.sinisterPec.modeModifId == 5 || this.sinisterPec.modeModifId == 6 || this.sinisterPec.modeModifId == 10
                                || this.sinisterPec.modeModifId == 11) && this.contratAssurance.clientId !== 3) {
                                this.companyYes = true;
                            } else if ((this.sinisterPec.modeModifId !== 5 && this.sinisterPec.modeModifId !== 6 && this.sinisterPec.modeModifId !== 10
                                && this.sinisterPec.modeModifId !== 11) || this.contratAssurance.clientId == 3) {
                                this.responsablePec = true;
                            }
                            break;
                        case 'REFUSED':
                            this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 10;
                            const dateRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateRefusePec);
                            break;
                        case 'ACCEPTED':
                            this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 11;

                            break;
                    }

                    let etatApp = '';
                    if (this.sinister.sinisterPec.decision == DecisionPec.CANCELED) { etatApp = 'Annulé' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_RESRV) { etatApp = 'Accepté avec réserves' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_CHANGE_STATUS) { etatApp = 'Accepté avec changement de statut' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.REFUSED) { etatApp = 'Refusé' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.ACCEPTED) { etatApp = 'Accepté' }

                    this.sinisterPec.approvPec = 'APPROVE';
                    if (this.sinisterPec.primaryQuotationId == null || this.sinisterPec.primaryQuotationId == undefined) {
                        this.sinisterPec.expertId = null;
                    }
                    this.updatePec();
                    console.log("1-------------------------------------------------------------------");
                    this.sendNotifPecApprove('prestApprouve', etatApp);
                    console.log("2-------------------------------------------------------------------");
                    this.alertService.success('auxiliumApp.sinisterPec.approveSinPec', null, null);

                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    approuveWithModif() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.approveWithModifButton == true) {
                    this.approveWithModifButton = false;

                    /*this.sinisterPec.step = 3;
                      this.sinisterPec.decision = 'INPROGRESS';*/

                    switch (this.sinisterPec.decision) {
                        case 'CANCELED':
                            this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 9;
                            const dateAnnuleRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateAnnuleRefusePec);
                            break;
                        case 'ACC_WITH_RESRV':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 12;
                            break;
                        case 'ACC_WITH_CHANGE_STATUS':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 13;
                            if ((this.sinisterPec.modeModifId == 5 || this.sinisterPec.modeModifId == 6 || this.sinisterPec.modeModifId == 10
                                || this.sinisterPec.modeModifId == 11) && this.contratAssurance.clientId !== 3) {
                                this.companyYes = true;
                            }
                            if ((this.sinisterPec.modeModifId !== 5 && this.sinisterPec.modeModifId !== 6 && this.sinisterPec.modeModifId !== 10
                                && this.sinisterPec.modeModifId !== 11) || this.contratAssurance.clientId == 3) {
                                this.responsablePec = true;
                            }
                            break;
                        case 'REFUSED':
                            this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 10;
                            const dateRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateRefusePec);
                            break;
                        case 'ACCEPTED':
                            this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 11;

                            break;
                    }
                    let etat = "";
                    if (this.sinister.sinisterPec.decision == DecisionPec.CANCELED) { etat = 'Annulé' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_RESRV) { etat = 'Accepté avec réserves' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_CHANGE_STATUS) { etat = 'Accepté avec changement de statut' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.REFUSED) { etat = 'Refusé' }
                    if (this.sinister.sinisterPec.decision == DecisionPec.ACCEPTED) { etat = 'Accepté' }

                    this.sinisterPec.approvPec = 'APPROVE_WITH_MODIFICATION';
                    if (this.sinisterPec.primaryQuotationId == null || this.sinisterPec.primaryQuotationId == undefined) {
                        this.sinisterPec.expertId = null;
                    }
                    this.updatePec();
                    this.sendNotifPecApproveWithModification('prestApprouveWithNotification', etat);
                    this.alertService.success('auxiliumApp.sinisterPec.approveWithModifSinPec', null, null);

                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }




    decider() {
        this.sinisterPec.baremeId = this.CasBareme.id;
        if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
            if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                this.pointChoc.id = this.sinisterPec.pointChoc.id;
            }
        }
        this.sinisterPec.pointChoc = this.pointChoc;
        this.sinisterPec.responsabilityRate = false;
        this.sinisterPec.tiers = this.tiers;
        if (this.isDecided) {
            this.sinisterPec.isDecidedFromInProgress = true;
            this.reglesGestion();
            this.sinister.sinisterPec = this.sinisterPec;
        } else {
            this.sinisterPec.isDecidedFromInProgress = false;
            this.sinister.sinisterPec = this.sinisterPec;
        }
        if (this.cmpRf) {
            this.sinisterPec.rfCmp = true;
            this.sinister.sinisterPec = this.sinisterPec;
        } else {
            this.sinisterPec.rfCmp = false;
            this.sinister.sinisterPec = this.sinisterPec;
        }
        this.ngbModalRef = this.siniterPecPopupService.openDecisionSinisterModal(DecisionSinisterPecComponent as Component, this.sinister, this.contratAssurance, this.constatAttachment, this.carteGriseAttachment, this.acteCessionAttachment, this.constatFiles, this.carteGriseFiles, this.acteCessionFiles, this.listModeGestionByPack, this.vehicule, this.updateConstat, this.updateCarteGrise, this.updateActeDeCession, this.piecesAttachments, this.updatePieceJointe1);
        this.ngbModalRef.result.then((result: any) => {
            if (result !== null && result !== undefined) {
                console.log("result select popup------" + result);
                //this.sinister.sinisterPec.decision = result.decision;
                this.changeModeGestion(result.modeModifId);
                this.modeId = result.modeId;

            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });

    }

    annulerPrestation() {
        this.annulPrest = false;
        this.confirmPrest = true;

    }

    getDevis(sinPec: SinisterPec) {
        if (sinPec.primaryQuotationId) {
            this.quotationService.find(sinPec.primaryQuotationId).subscribe((res) => {  // Find devid By ID
                this.quotation = res;
                console.log("rep icii devis priincipale" + this.quotation.id);
                this.quotation.confirmationDecisionQuote = false;
                this.loadAllDetailsMo();
                this.loadAllIngredient();
                this.loadAllRechange();
                this.loadAllFourniture();
                if (sinPec.listComplementaryQuotation.length > 0) {
                    console.log("rep icii devis  comp entrer");

                    sinPec.listComplementaryQuotation.forEach(element => {
                        console.log("rep icii devis compli list element" + element.id);
                        if (element.isConfirme) {
                            this.quotationService.find(element.id).subscribe((res) => {  // Find devid By ID
                                console.log("rep icii devis  quotation  complimentaire sub");
                                this.quotation = res;
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

    ConfirmerAnnulPrest() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinisterPec.reasonCanceledId !== null && this.sinisterPec.reasonCanceledId !== undefined) {
                    this.showMsgMotifRqrdCancel = true;
                    this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                    this.sinisterPec.oldStep = this.sinisterPec.stepId;
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = 22;
                    this.sendNotifPec('annulerPrest', 'annulPrest');
                    this.updatePec();
                } else {
                    this.showMsgMotifRqrdCancel = false;
                }


            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    ConfirmerAnnul() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinisterPec.approvPec == null) { this.sinisterPec.approvPec = 'APPROVE'; }
                this.sinisterPec.decision = 'CANCELED';
                const dateAnnuleRefusePec = new Date();
                this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateAnnuleRefusePec);
                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                this.sinisterPec.stepId = 9;
                if (this.sinisterPec.repriseEtat !== null && this.sinisterPec.repriseEtat !== undefined) {
                    if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                        this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((apecC: Apec) => {
                            this.apec = apecC;
                            if (this.apec !== null && this.apec !== undefined) {
                                this.apec.etat = this.sinisterPec.repriseEtat;
                                this.apecService.update(this.apec).subscribe((res) => {
                                    this.apec = res;
                                    this.sinisterPec.repriseEtat = null;
                                    if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                        this.updatePec();
                                        this.sendNotifConfirmeAnnulationPrestation('prestConfirmationAnnulation');
                                    }
                                });
                            }
                        });
                    } else {
                        this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((quote: Quotation) => {
                            this.apecService.findByQuotation(quote.id).subscribe((apecC: Apec) => {
                                this.apec = apecC;
                                if (this.apec !== null && this.apec !== undefined) {
                                    this.apec.etat = this.sinisterPec.repriseEtat;
                                    this.apecService.update(this.apec).subscribe((res) => {
                                        this.apec = res;
                                        this.sinisterPec.repriseEtat = null;
                                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                            this.updatePec();
                                            this.sendNotifConfirmeAnnulationPrestation('prestConfirmationAnnulation');
                                        }
                                    });
                                }
                            });
                        });
                    }
                } else {
                    if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                        this.updatePec();
                        this.sendNotifConfirmeAnnulationPrestation('prestConfirmationAnnulation');
                    }
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    nonConfirmerAnnul() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.oneClickForButton3) {
                    this.oneClickForButton3 = false;
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = this.sinisterPec.oldStep;
                    this.sinisterPec.oldStep = null;
                    this.sinisterPec.repriseStep = null;
                    if (this.sinisterPec.repriseEtat !== null && this.sinisterPec.repriseEtat !== undefined) {
                        if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                            this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((apecC: Apec) => {
                                this.apec = apecC;
                                if (this.apec !== null && this.apec !== undefined) {
                                    this.apec.etat = this.sinisterPec.repriseEtat;
                                    this.apecService.update(this.apec).subscribe((res) => {
                                        this.apec = res;
                                        this.sinisterPec.repriseEtat = null;
                                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                            this.updatePec();
                                        }
                                    });
                                }
                            });
                        } else {
                            this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((quote: Quotation) => {
                                this.apecService.findByQuotation(quote.id).subscribe((apecC: Apec) => {
                                    this.apec = apecC;
                                    if (this.apec !== null && this.apec !== undefined) {
                                        this.apec.etat = this.sinisterPec.repriseEtat;
                                        this.apecService.update(this.apec).subscribe((res) => {
                                            this.apec = res;
                                            this.sinisterPec.repriseEtat = null;
                                            if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                                this.updatePec();
                                            }
                                        });
                                    }
                                });
                            });
                        }
                    } else {
                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                            this.updatePec();
                        }
                    }
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    ConfirmerRefusPrest() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs  de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.oneClickForButton4) {
                    this.oneClickForButton4 = false;
                    if (this.sinisterPec.reasonRefusedId !== null && this.sinisterPec.reasonRefusedId !== undefined) {
                        this.showMsgMotifRqrdRefus = true;
                        this.sinisterPec.repriseStep = this.sinisterPec.stepId;
                        this.sinisterPec.oldStep = this.sinisterPec.stepId;
                        this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                        this.sinisterPec.stepId = 33;
                        this.sendNotifPec('refusPrest', 'refusPrest');
                        this.updatePec();
                    } else {
                        this.showMsgMotifRqrdRefus = false;
                    }
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    ConfirmerRefus() {

        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.oneClickForButton5) {
                    this.oneClickForButton5 = false;
                    if (this.sinisterPec.approvPec == null) { this.sinisterPec.approvPec = 'APPROVE'; }
                    this.sinisterPec.decision = 'REFUSED';
                    const dateAnnuleRefusePec = new Date();
                    this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateAnnuleRefusePec);
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = 10;
                    if (this.sinisterPec.repriseEtat !== null && this.sinisterPec.repriseEtat !== undefined) {
                        if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                            this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((apecC: Apec) => {
                                this.apec = apecC;
                                if (this.apec !== null && this.apec !== undefined) {
                                    this.apec.etat = this.sinisterPec.repriseEtat;
                                    this.apecService.update(this.apec).subscribe((res) => {
                                        this.apec = res;
                                        this.sinisterPec.repriseEtat = null;
                                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                            this.updatePec();
                                            this.sendNotifPecConfirmRefusParRespPec('confirmRefusWthRespPec', 'REFUSED');
                                        }
                                    });
                                }
                            });
                        } else {
                            this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((quote: Quotation) => {
                                this.apecService.findByQuotation(quote.id).subscribe((apecC: Apec) => {
                                    this.apec = apecC;
                                    if (this.apec !== null && this.apec !== undefined) {
                                        this.apec.etat = this.sinisterPec.repriseEtat;
                                        this.apecService.update(this.apec).subscribe((res) => {
                                            this.apec = res;
                                            this.sinisterPec.repriseEtat = null;
                                            if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                                this.updatePec();
                                                this.sendNotifPecConfirmRefusParRespPec('confirmRefusWthRespPec', 'REFUSED');
                                            }
                                        });
                                    }
                                });
                            });
                        }
                    } else {
                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                            this.updatePec();
                            this.sendNotifPecConfirmRefusParRespPec('confirmRefusWthRespPec', 'REFUSED');
                        }
                    }
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    nonConfirmerRefus() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.oneClickForButton6) {
                    this.oneClickForButton6 = false;
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = this.sinisterPec.oldStep;
                    this.sinisterPec.oldStep = null;
                    this.sinisterPec.repriseStep = null;
                    if (this.sinisterPec.repriseEtat !== null && this.sinisterPec.repriseEtat !== undefined) {
                        if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                            this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((apecC: Apec) => {
                                this.apec = apecC;
                                if (this.apec !== null && this.apec !== undefined) {
                                    this.apec.etat = this.sinisterPec.repriseEtat;
                                    this.apecService.update(this.apec).subscribe((res) => {
                                        this.apec = res;
                                        this.sinisterPec.repriseEtat = null;
                                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                            this.updateNonPecConfirmRefus();
                                        }
                                    });
                                }
                            });
                        } else {
                            this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((quote: Quotation) => {
                                this.apecService.findByQuotation(quote.id).subscribe((apecC: Apec) => {
                                    this.apec = apecC;
                                    if (this.apec !== null && this.apec !== undefined) {
                                        this.apec.etat = this.sinisterPec.repriseEtat;
                                        this.apecService.update(this.apec).subscribe((res) => {
                                            this.apec = res;
                                            this.sinisterPec.repriseEtat = null;
                                            if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                                                this.updateNonPecConfirmRefus();
                                            }
                                        });
                                    }
                                });
                            });
                        }
                    } else {
                        if (this.sinisterPec.stepId !== null && this.sinisterPec.stepId !== undefined) {
                            this.updateNonPecConfirmRefus();
                        }
                    }

                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    updateNonPecConfirmRefus() {
        this.tierRespMin = true;

        this.sinisterPec.dateModification = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        if (this.isAcceptedWithReserve) {
            this.sinisterPec.dateModifAfterReserved = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        }
        this.sinisterPec.baremeId = this.CasBareme.id;
        this.sinisterPec.responsabilityRate = false;
        if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
            if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                this.pointChoc.id = this.sinisterPec.pointChoc.id;
            }
        }

        this.sinisterPec.pointChoc = this.pointChoc;
        this.sinister.sinisterPec = this.sinisterPec;
        //this.sinister.sinisterPec.step = PrestationPecStep.REQUEST_OPENING;
        this.sinister.sinisterPec.baremeId = this.CasBareme.id;
        this.sinister.statusId = StatusSinister.INPROGRESS;
        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.sinisterPec = this.sinister.sinisterPec;
                this.saveAttachments(this.sinister.sinisterPec.id);
                this.savePhotoPlusPec();
                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                this.updateVehicule();
                if (this.isRefused) { this.router.navigate(['/sinister-pec-refused']); }
                if (this.isCanceled) { this.router.navigate(['/sinister-pec-canceled']); }
                if (this.isApprouved) { this.router.navigate(['/pec-to-approve']); }
                if (this.isDecided) { this.router.navigate(['/pec-being-processed']); }
                if (this.isAcceptedWithReserve) { this.router.navigate(['/sinister-pec-reserves-lifted']); }
                if (this.isAcceptedWithChangeStatus) { this.router.navigate(['sinister-pec-modification-status']); }
                if (this.isSinPecRefusedCanceled) { this.router.navigate(['sinister-pec-reprise-prestation']); }
                if (this.isSinPecVerifOrgPrinted) { this.router.navigate(['verification-originals-printed']); }
                if (this.isCancPec) { this.router.navigate(['annuler-prestation']); }
                if (this.isConfirmCancPec) { this.router.navigate(['confirmation-annuler-prestation']); }
                if (this.isRefusPec) { this.router.navigate(['refus-prestation']); }
                if (this.isConfirmRefusPec) { this.router.navigate(['confirmation-refus-prestation']); }
            });
        }
    }

    genererBonSortie() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinisterPec.generationBonSortieDate == null || this.sinisterPec.generationBonSortieDate == undefined) {
                    const dateBonSoetie = new Date();
                    this.sinisterPec.generationBonSortieDate = this.dateAsYYYYMMDDHHNNSSLDT(dateBonSoetie);
                }
                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                this.sinisterPec.stepId = 35;
                this.updatePec();
                //this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                this.sinisterPecService.generateBonSortie(this.sinisterPec).subscribe((res) => {
                    window.open(res.headers.get('pdfname'), '_blank');
                    this.router.navigate(['generation-bon-sortie']);
                    this.alertService.success('auxiliumApp.sinisterPec.genererBonSortie', null, null);
                });
                //});
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
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
    nonConforme() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {

                this.router.navigate(['verification-originals-printed']);
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    conforme() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.oneClickForButton7) {
                    this.oneClickForButton7 = false;
                    this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                    this.sinisterPec.stepId = 36;
                    this.updatePec();
                    this.sendNotifConformeOriginalsPrinted('notifConformOriginalsPrinted');
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }


    sendNotifAccordDecision(notificationId, destination, sourse, settings) {
        this.notifUser = new NotifAlertUser();
        this.notification.id = notificationId;
        this.notifUser.destination = destination;
        this.notifUser.source = sourse;
        this.notifUser.notification = this.notification;
        this.notifUser.entityId = this.sinisterPec.id;
        this.notifUser.entityName = "SinisterPec";
        this.notifUser.settings = JSON.stringify(settings);
        this.listNotifUser.push(this.notifUser);
    }
    sendNotifConformeOriginalsPrinted(typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.userExNotifPartner = [];
            this.userExNotifAgency = [];
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                        this.userExNotifPartner = userExNotifPartner.json
                        this.userExNotifPartner.forEach(element => {
                            let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId };
                            this.sendNotifAccordDecision(45, element.userId, usr.userId, settingJson);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json
                            this.userExNotifAgency.forEach(element => {
                                let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId };
                                this.sendNotifAccordDecision(45, element.userId, usr.userId, settingJson);
                            });
                            this.userExtraService.findByProfil(18).subscribe((userExNotifAgency: UserExtra[]) => {
                                this.userExNotifAgency = [];
                                this.userExNotifAgency = userExNotifAgency;
                                this.userExNotifAgency.forEach(element => {
                                    let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId };
                                    this.sendNotifAccordDecision(45, element.userId, usr.userId, settingJson);
                                });
                                this.userExtraService.findByProfil(19).subscribe((userExNotifAgency: UserExtra[]) => {
                                    this.userExNotifAgency = [];
                                    this.userExNotifAgency = userExNotifAgency;
                                    this.userExNotifAgency.forEach(element => {
                                        let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId };
                                        this.sendNotifAccordDecision(45, element.userId, usr.userId, settingJson);
                                    });
                                    this.userExtraService.findByProfil(20).subscribe((userExNotifAgency: UserExtra[]) => {
                                        this.userExNotifAgency = [];
                                        this.userExNotifAgency = userExNotifAgency;
                                        this.userExNotifAgency.forEach(element => {
                                            let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId };
                                            this.sendNotifAccordDecision(45, element.userId, usr.userId, settingJson);
                                        });

                                        this.userExtraService.finUsersByPersonProfil(this.sinisterPec.reparateurId, 28).subscribe((userExNotifRep) => {
                                            this.userExNotifAgency = [];
                                            this.userExNotifAgency = userExNotifRep.json;
                                            this.userExNotifAgency.forEach(element => {
                                                let settingJson = { 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId };
                                                this.sendNotifAccordDecision(45, element.userId, usr.userId, settingJson);
                                            });

                                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
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

    /**
      * Save sinister
      */
    updatePec() {

        this.nbrTiersRespMin = 0;
        this.tiers.forEach(tierR => {
            if (tierR.responsible) {
                this.nbrTiersRespMin++;
            }
        });

        if (this.sinisterPec.vehicleNumber > 1) {
            if (this.nbrTiersRespMin == 1) {
                this.tierRespMin = true;
                this.savePec();
            } else {
                this.tierRespMin = false;
            }
        } else {
            this.savePec();
        }

    }

    savePec() {
        this.sinisterPec.dateModification = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        if (this.isAcceptedWithReserve) {
            this.sinisterPec.dateModifAfterReserved = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
        }
        this.sinisterPec.baremeId = this.CasBareme.id;
        this.sinisterPec.responsabilityRate = false;
        if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
            if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                this.pointChoc.id = this.sinisterPec.pointChoc.id;
            }
        }
        this.sinisterPec.pointChoc = this.pointChoc;
        this.sinister.sinisterPec = this.sinisterPec;
        this.sinister.sinisterPec.baremeId = this.CasBareme.id;
        this.sinister.statusId = StatusSinister.INPROGRESS;
        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.sinisterPec = this.sinister.sinisterPec;
                this.saveAttachments(this.sinister.sinisterPec.id);
                this.savePhotoPlusPec();
                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                this.updateVehicule();
                if (this.isRefused) { this.router.navigate(['/sinister-pec-refused']); }
                if (this.isCanceled) { this.router.navigate(['/sinister-pec-canceled']); }
                if (this.isApprouved) { this.router.navigate(['/pec-to-approve']); }
                if (this.isDecided) { this.router.navigate(['/pec-being-processed']); }
                if (this.isAcceptedWithReserve) { this.router.navigate(['/sinister-pec-reserves-lifted']); }
                if (this.isAcceptedWithChangeStatus) { this.router.navigate(['sinister-pec-modification-status']); }
                if (this.isSinPecRefusedCanceled) { this.router.navigate(['sinister-pec-reprise-prestation']); }
                if (this.isSinPecVerifOrgPrinted) { this.router.navigate(['verification-originals-printed']); }
                if (this.isCancPec) { this.router.navigate(['annuler-prestation']); }
                if (this.isConfirmCancPec) { this.router.navigate(['confirmation-annuler-prestation']); }
                if (this.isRefusPec) { this.router.navigate(['refus-prestation']); }
                if (this.isConfirmRefusPec) { this.router.navigate(['confirmation-refus-prestation']); }
            });
        }
    }

    // Save affectation reparateur 
    AffectationReparateur() {
        if (this.nbmissionreparateur >= this.capacite) {
            this.affectReparateurWithCapacity = true;
        } else {
            this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir affecter le réparateur?', 'Oui', 'Non', 'lg').then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    if (this.oneClickForButton8) {
                        this.oneClickForButton8 = false;
                        const copy: SinisterPec = Object.assign({}, this.sinisterPec);
                        if (this.sinisterPec.dateRDVReparation !== undefined && this.reparateurArrivalTime !== undefined) {
                            if (this.reparateurArrivalTime.hour == '00' || this.reparateurArrivalTime.hour == '24') { this.reparateurArrivalTime.hour = '23'; }
                            this.sinisterPec.dateRDVReparation.hour = this.reparateurArrivalTime.hour;
                            this.sinisterPec.dateRDVReparation.minute = this.reparateurArrivalTime.minute;
                            this.sinisterPec.dateRDVReparation.second = 0;
                            copy.dateRDVReparation = this.owerDateUtils.convertDateTimeToServer(this.sinisterPec.dateRDVReparation, undefined);
                        }
                        this.sinister.sinisterPec = copy;
                        this.sinisterPec.stepId = PrestationPecStep.RECEPTION_VEHICLE;
                        this.sinister.sinisterPec.stepId = PrestationPecStep.RECEPTION_VEHICLE;
                        if (this.selectedGouvernorat !== null && this.selectedGouvernorat !== undefined) {
                            this.sinister.sinisterPec.gouvernoratRepId = this.selectedGouvernorat;
                        }
                        const dateAffecRep = new Date();
                        this.sinister.sinisterPec.dateAffectReparateur = this.dateAsYYYYMMDDHHNNSSLDT(dateAffecRep);
                        this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;
                            console.log(resSinister);

                            this.sendNotifAffectRepair(this.sinister.sinisterPec.reparateurId, "affectation Reparateur");
                            this.router.navigate(['sinister-pec-affectation-reparateur']);
                        });

                    }
                }
            })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
        }
    }

    //connect to send Notif
    sendNotifAffectRepair(id, typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            if (id != null && id != undefined) {
                this.userExNotifReparateurs = [];
                this.userExNotifPartner = [];
                this.userExNotifAgency = [];
                this.reparateurService.find(id).subscribe((resRep) => {
                    this.reparateur = resRep;
                    this.principal.identity().then((account) => {
                        this.currentAccount = account;
                        this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                            this.userExtraService.finUsersByPersonProfil(this.reparateur.id, 28).subscribe((userExNotifReparateurs) => {
                                this.userExNotifReparateurs = userExNotifReparateurs.json
                                this.userExNotifReparateurs.forEach(elementRep => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 22;
                                    this.notifUser.source = usr.id;
                                    this.notifUser.destination = elementRep.id;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinister.sinisterPec.id, 'stepPecId': this.sinisterPec.stepId });
                                    this.listNotifUser.push(this.notifUser);
                                });
                                this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                                    this.userExNotifAgency = userExNotifAgency.json
                                    this.userExNotifAgency.forEach(elementAgency => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 22;
                                        this.notifUser.source = usr.id;
                                        this.notifUser.destination = elementAgency.id;
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinister.sinisterPec.id, 'stepPecId': this.sinisterPec.stepId });
                                        this.listNotifUser.push(this.notifUser);
                                    });
                                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                                        this.userExNotifPartner = userExNotifPartner.json;
                                        this.userExNotifPartner.forEach(elementPartner => {
                                            this.notifUser = new NotifAlertUser();
                                            this.notification.id = 22;
                                            this.notifUser.source = usr.id;
                                            this.notifUser.destination = elementPartner.id;
                                            this.notifUser.entityId = this.sinisterPec.id;
                                            this.notifUser.entityName = "SinisterPec";
                                            this.notifUser.notification = this.notification;
                                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinister.sinisterPec.id, 'stepPecId': this.sinisterPec.stepId });
                                            this.listNotifUser.push(this.notifUser);
                                        });

                                        this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 11, usr.id).subscribe((prestToRead) => {
                                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': this.reparateur.id, 'nomReparateur': this.reparateur.raisonSociale, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinister.sinisterPec.id, 'stepPecId': this.sinisterPec.stepId }));
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            }
        }
    }
    sendNotifCancelAffectRepair(id, typeNotif) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtraService.finUsersByPersonProfil(id, 28).subscribe((userExNotifPartner) => {
                        this.userExNotifPartner = userExNotifPartner.json
                        this.userExNotifPartner.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 23;
                            this.notifUser.source = usr.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': id, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);

                        });
                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idReparateur': id, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'stepPecId': this.sinisterPec.stepId }));
                        });
                        this.userExNotifPartner.forEach(elementRep => {
                            this.notificationAlerteService.queryReadNotificationForUserReparateur(this.sinisterPec.id, 25, elementRep.userId).subscribe((read) => { });
                        });

                    });
                });
            });
        }
    }
    sendNotifAffectExpertDevisInit(typeNotif) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.expertId, 27).subscribe((userExNotifPartner) => {
                    this.userExNotifPartner = userExNotifPartner.json
                    this.userExNotifPartner.forEach(element => {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 26;
                        this.notifUser.source = usr.id;
                        this.notifUser.destination = element.userId;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.notification = this.notification;
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                    });
                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                        this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId }));
                    });

                });
            });
        });
    }

    sendNotifAffectExpertDevisCompl(typeNotif) {

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtraService.finUsersByPersonProfil(this.sinisterPec.expertId, 27).subscribe((userExNotifPartner) => {
                    this.userExNotifPartner = userExNotifPartner.json
                    this.userExNotifPartner.forEach(element => {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 26;
                        this.notifUser.source = usr.id;
                        this.notifUser.destination = element.userId;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.notification = this.notification;
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                    });
                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                        this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'expertId': this.sinisterPec.expertId, 'stepPecId': this.sinisterPec.stepId }));
                    });

                });
            });
        });

    }

    sendNotifCancelAffectExpert(id, typeNotif) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtraService.finUsersByPersonProfil(id, 27).subscribe((userExNotifPartner) => {
                        this.userExNotifPartner = userExNotifPartner.json
                        this.userExNotifPartner.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 24;
                            this.notifUser.source = usr.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'expertId': id, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'refSinister': this.sinister.reference, 'assure': this.assure.nom, 'sinisterId': this.sinister.id, 'sinisterPecId': this.sinisterPec.id, 'expertId': id, 'stepPecId': this.sinisterPec.stepId }));
                        });

                    });
                });
            });
        }

    }


    sendNotifPecApprove(typeNotif, etatPrestation) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;

            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 14;
                    this.notifUser.source = usr.id;
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.notification = this.notification;
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    this.userExtraService.finPersonneByUser(this.sinisterPec.assignedToId).subscribe((usrBoss: UserExtra) => {
                        if (usrBoss.userBossId !== null && usrBoss.userBossId !== undefined) {
                            let typeNotifResp = 'prestApprouveWithNotificationInformative';
                            if (this.responsablePec == true) {
                                typeNotifResp = typeNotif;
                            }
                            console.log("typeNotification" + typeNotifResp);
                            this.notifUserResp = new NotifAlertUser();
                            this.notification.id = 14;
                            this.notifUserResp.source = usr.id;
                            this.notifUserResp.destination = usrBoss.userBossId;
                            this.notifUserResp.entityId = this.sinisterPec.id;
                            this.notifUserResp.entityName = "SinisterPec";
                            this.notifUserResp.notification = this.notification;
                            this.notifUserResp.settings = JSON.stringify({ 'typenotification': typeNotifResp, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUserResp);
                        }
                        console.log("3-------------------------------------------------------------------");
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json;
                            let typeNotifCompany = 'prestApprouveWithNotificationInformative';
                            if (this.companyYes == true) {
                                typeNotifCompany = typeNotif;
                            }
                            console.log("typeNotification" + typeNotifCompany);
                            this.userExNotifAgency.forEach(elementA => {
                                this.notifUserAgent = new NotifAlertUser();
                                this.notification.id = 14;
                                this.notifUserAgent.source = usr.id;
                                this.notifUserAgent.destination = elementA.userId;
                                this.notifUserAgent.entityId = this.sinisterPec.id;
                                this.notifUserAgent.entityName = "SinisterPec";
                                this.notifUserAgent.notification = this.notification;
                                this.notifUserAgent.settings = JSON.stringify({ 'typenotification': typeNotifCompany, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUserAgent);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json
                                this.userExNotifPartner.forEach(elementC => {
                                    this.notifUserCompagny = new NotifAlertUser();
                                    this.notification.id = 14;
                                    this.notifUserCompagny.source = usr.id;
                                    this.notifUserCompagny.destination = elementC.userId;
                                    this.notifUserCompagny.entityId = this.sinisterPec.id;
                                    this.notifUserCompagny.entityName = "SinisterPec";
                                    this.notifUserCompagny.notification = this.notification;
                                    this.notifUserCompagny.settings = JSON.stringify({ 'typenotification': typeNotifCompany, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                    this.listNotifUser.push(this.notifUserCompagny);
                                });
                                this.userExtraService.findByProfil(9).subscribe((userExNotifChargeAdministrative: UserExtra[]) => {
                                    this.userExNotifChargeAdministrative = userExNotifChargeAdministrative;
                                    this.userExNotifChargeAdministrative.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 14;
                                        this.notifUser.source = usr.userId;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                        if (etatPrestation == 'Accepté') {
                                            this.listNotifUser.push(this.notifUser);
                                        }
                                    });
                                    this.userExtraService.findByProfil(10).subscribe((userExNotifResponsableAdministrative: UserExtra[]) => {
                                        this.userExNotifResponsableAdministrative = userExNotifResponsableAdministrative;
                                        this.userExNotifResponsableAdministrative.forEach(element => {
                                            this.notifUser = new NotifAlertUser();
                                            this.notification.id = 14;
                                            this.notifUser.source = usr.userId;
                                            this.notifUser.destination = element.userId;
                                            this.notifUser.entityId = this.sinisterPec.id;
                                            this.notifUser.entityName = "SinisterPec";
                                            this.notifUser.notification = this.notification;
                                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                            if (etatPrestation == 'Accepté') {
                                                this.listNotifUser.push(this.notifUser);
                                            }
                                        });
                                        this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 30, usr.id).subscribe((prestToRead) => {
                                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId }));
                                            });
                                        });
                                    });
                                });


                            });


                        });
                    });
                });
            });
        }
    }

    sendNotifPec(typeNotif, etatPrestation) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            if (typeNotif == 'refusPrest' && etatPrestation == 'refusPrest') {
                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                    this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                        if (usr.userBossId) {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 21;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = usr.userBossId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': this.userExtra.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': this.userExtra.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));

                            });
                        }
                    });
                });
            } else {
                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                    this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                        if (usr.userBossId) {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 20;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = usr.userBossId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': this.userExtra.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': this.userExtra.userBossId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));

                            });
                        }
                    });
                });
            }
        }
    }

    sendNotifPecApproveWithModification(typeNotif, etatPrestation) {
        console.log('_________________________________________11');
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 15;
                    this.notifUser.source = usr.userId;
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.notification = this.notification;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    this.userExtraService.finPersonneByUser(this.sinisterPec.assignedToId).subscribe((usrBoss: UserExtra) => {
                        if (usrBoss.userBossId !== null && usrBoss.userBossId !== undefined) {
                            let typeNotifResp = 'prestApprouveWithNotificationInformative';
                            if (this.responsablePec == true) {
                                typeNotifResp = typeNotif;
                            }
                            this.notifUserResp = new NotifAlertUser();
                            this.notification.id = 15;
                            this.notifUserResp.source = usr.id;
                            this.notifUserResp.destination = usrBoss.userBossId;
                            this.notifUserResp.notification = this.notification;
                            this.notifUserResp.entityId = this.sinisterPec.id;
                            this.notifUserResp.entityName = "SinisterPec";
                            this.notifUserResp.settings = JSON.stringify({ 'typenotification': typeNotifResp, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUserResp);
                        }
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json
                            let typeNotifCompany = 'prestApprouveWithNotificationInformative';
                            if (this.companyYes == true) {
                                typeNotifCompany = typeNotif;
                            }
                            this.userExNotifAgency.forEach(elementA => {
                                this.notifUserAgent = new NotifAlertUser();
                                this.notification.id = 15;
                                this.notifUserAgent.source = usr.userId;
                                this.notifUserAgent.destination = elementA.userId;
                                this.notifUserAgent.notification = this.notification;
                                this.notifUserAgent.entityId = this.sinisterPec.id;
                                this.notifUserAgent.entityName = "SinisterPec";
                                this.notifUserAgent.settings = JSON.stringify({ 'typenotification': typeNotifCompany, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUserAgent);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json;
                                this.userExNotifPartner.forEach(elementC => {
                                    this.notifUserCompagny = new NotifAlertUser();
                                    this.notification.id = 15;
                                    this.notifUserCompagny.source = usr.userId;
                                    this.notifUserCompagny.destination = elementC.userId;
                                    this.notifUserCompagny.notification = this.notification;
                                    this.notifUserCompagny.entityId = this.sinisterPec.id;
                                    this.notifUserCompagny.entityName = "SinisterPec";
                                    this.notifUserCompagny.settings = JSON.stringify({ 'typenotification': typeNotifCompany, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                    this.listNotifUser.push(this.notifUserCompagny);
                                });
                                this.userExtraService.findByProfil(9).subscribe((userExNotifChargeAdministrative: UserExtra[]) => {
                                    this.userExNotifChargeAdministrative = userExNotifChargeAdministrative;
                                    this.userExNotifChargeAdministrative.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 15;
                                        this.notifUser.source = usr.userId;
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                        if (etatPrestation == 'Accepté') {
                                            this.listNotifUser.push(this.notifUser);
                                        }
                                    });
                                    this.userExtraService.findByProfil(10).subscribe((userExNotifResponsableAdministrative: UserExtra[]) => {
                                        this.userExNotifResponsableAdministrative = userExNotifResponsableAdministrative;
                                        this.userExNotifResponsableAdministrative.forEach(element => {
                                            this.notifUser = new NotifAlertUser();
                                            this.notification.id = 15;
                                            this.notifUser.source = usr.userId;
                                            this.notifUser.destination = element.userId;
                                            this.notifUser.notification = this.notification;
                                            this.notifUser.entityId = this.sinisterPec.id;
                                            this.notifUser.entityName = "SinisterPec";
                                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                            if (etatPrestation == 'Accepté') {
                                                this.listNotifUser.push(this.notifUser);
                                            }
                                        });
                                        this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 30, usr.id).subscribe((prestToRead) => {
                                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        }
    }

    sendNotifPecMS(typeNotif) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    if (typeNotif == 'refusModifStatus') {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 17;
                        this.notifUser.source = usr.userId;
                        this.notifUser.destination = this.sinisterPec.assignedToId;
                        this.notifUser.notification = this.notification;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                        this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 13, usr.id).subscribe((prestToRead) => {
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId }));
                            });

                        });
                    } else {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 16;
                        this.notifUser.source = usr.userId;
                        this.notifUser.destination = this.sinisterPec.assignedToId;
                        this.notifUser.notification = this.notification;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                        this.userPartnerModeService.findUsersByPartnerAndMode(this.contratAssurance.clientId, this.sinisterPec.modeId).subscribe((subResPa: ResponseWrapper) => {
                            this.listUserPartnerModesForNotifs = subResPa.json;
                            this.listUserPartnerModesForNotifs.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notificationC = new RefNotifAlert();
                                this.notificationC.id = 62;
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = element.userExtraId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notificationC;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': 'modifStatusChangementCharge', 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                                this.userExNotifAgency = userExNotifAgency.json
                                this.userExNotifAgency.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 16;
                                    this.notifUser.source = usr.userId;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                                    this.listNotifUser.push(this.notifUser);
                                });

                                this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                                    this.userExNotifPartner = userExNotifPartner.json
                                    this.userExNotifPartner.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 16;
                                        this.notifUser.source = usr.userId;
                                        this.notifUser.entityId = this.sinisterPec.id;
                                        this.notifUser.entityName = "SinisterPec";
                                        this.notifUser.destination = element.userId;
                                        this.notifUser.notification = this.notification;
                                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                                        this.listNotifUser.push(this.notifUser);
                                    });

                                    this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 13, usr.id).subscribe((prestToRead) => {
                                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId }));

                                        });
                                    });

                                });


                            });
                        });
                    }
                });
            });
        }
    }

    sendNotifPecMSForCP(typeNotif) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {

                    this.userPartnerModeService.findUsersByPartnerAndMode(this.contratAssurance.clientId, this.sinisterPec.modeId).subscribe((subResPa: ResponseWrapper) => {
                        this.listUserPartnerModesForNotifs = subResPa.json;
                        this.listUserPartnerModesForNotifs.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notificationC = new RefNotifAlert();
                            this.notificationC.id = 62;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = element.userExtraId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notificationC;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId }));

                        });
                    });
                });
            });
        }
    }

    sendNotifReserveLifted(typeNotif) {

        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 34;
                    this.notifUser.source = usr.userId;
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.notification = this.notification;
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 34;
                            this.notifUser.source = usr.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });

                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 34;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });

                            this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 12, usr.id).subscribe((prestToRead) => {
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId }));

                                });
                            });

                        });


                    });
                });
            });
        }
    }

    getImage(refB: RefBareme) {
        if (refB.attachmentName == 'png' || refB.attachmentName == 'PNG') {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageType + refB.attachment64);
        } else {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageTypeJpeg + refB.attachment64);
        }
    }

    sendNotifReprisePrestation(typeNotif) {


        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 18;
                    this.notifUser.source = usr.userId;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.notification = this.notification;
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 18;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = element.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });

                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 18;
                                this.notifUser.source = usr.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 10, usr.id).subscribe((prestToRead) => {
                            });
                            this.notificationAlerteService.queryReadNotificationForUser(this.sinisterPec.id, 9, usr.id).subscribe((prestToRead) => {
                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId }));
                                });
                            });

                        });


                    });

                });
            });
        }
    }

    sendNotifConfirmeAnnulationPrestation(typeNotif) {


        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.notifUser = new NotifAlertUser();
                    this.notification.id = 20;
                    this.notifUser.source = usr.userId;
                    this.notifUser.destination = this.sinisterPec.assignedToId;
                    this.notifUser.notification = this.notification;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 20;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = element.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });

                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 20;
                                this.notifUser.source = usr.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.findByProfil(21).subscribe((userExNotifC: UserExtra[]) => {
                                this.userExNotifAgency = [];
                                this.userExNotifAgency = userExNotifC;
                                this.userExNotifAgency.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 20;
                                    this.notifUser.source = usr.userId;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                    this.listNotifUser.push(this.notifUser);
                                });

                                if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                                    this.userExtraService.finUsersByPersonProfil(this.sinisterPec.reparateurId, 28).subscribe((userExNotifPartner) => {
                                        this.userExNotifPartner = [];
                                        this.userExNotifPartner = userExNotifPartner.json
                                        this.userExNotifPartner.forEach(element => {
                                            this.notifUser = new NotifAlertUser();
                                            this.notification.id = 20;
                                            this.notifUser.source = usr.userId;
                                            this.notifUser.destination = element.userId;
                                            this.notifUser.entityId = this.sinisterPec.id;
                                            this.notifUser.entityName = "SinisterPec";
                                            this.notifUser.notification = this.notification;
                                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                            this.listNotifUser.push(this.notifUser);
                                        });
                                        if (this.sinisterPec.expertId !== null && this.sinisterPec.expertId !== undefined) {
                                            this.userExtraService.finUsersByPersonProfil(this.sinisterPec.expertId, 27).subscribe((userExNotifPartner) => {
                                                this.userExNotifPartner = [];
                                                this.userExNotifPartner = userExNotifPartner.json
                                                this.userExNotifPartner.forEach(element => {
                                                    this.notifUser = new NotifAlertUser();
                                                    this.notification.id = 20;
                                                    this.notifUser.source = usr.userId;
                                                    this.notifUser.destination = element.userId;
                                                    this.notifUser.entityId = this.sinisterPec.id;
                                                    this.notifUser.entityName = "SinisterPec";
                                                    this.notifUser.notification = this.notification;
                                                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                                    this.listNotifUser.push(this.notifUser);
                                                });
                                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId }));
                                                });
                                            });
                                        } else {
                                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId }));
                                            });
                                        }

                                    });
                                } else {
                                    this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                        this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'assignedTo': this.sinisterPec.assignedToId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId }));
                                    });
                                }
                            });

                        });


                    });

                });
            });
        }
    }

    sendNotifPecConfirmRefusParRespPec(typeNotif, etatPrestation) {
        console.log('_________________________________________11');
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 59;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = element.userId;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json;
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 59;
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = element.userId;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.notification = this.notification;
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });

                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId }));
                            });


                        });


                    });
                });
            });
        }
    }

    // Save affectation reparateur 
    annulerAffectationReparateur() {
        this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir Annuler affectation Reparateur?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.oneClickForButton9) {
                    this.oneClickForButton9 = false;
                    const id = this.sinister.sinisterPec.reparateurId;
                    this.sinisterPec.reparateurId = null;
                    this.sinisterPec.dateRDVReparation = null;
                    this.sinisterPec.vehicleReceiptDate = null;
                    this.sinisterPec.receptionVehicule = null;
                    this.sinisterPec.expertDecision = null;
                    this.sinisterPec.stepId = PrestationPecStep.ASSIGNMENT_REPAIRER;
                    this.sinister.sinisterPec = this.sinisterPec;
                    if (this.sinister.id !== null && this.sinister.id !== undefined) {
                        this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                            this.router.navigate(['/sinister-pec-annuler-affectation-reparateur']);
                            this.sendNotifCancelAffectRepair(id, "annulation Affectation Reparateur");
                        });
                    }
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

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
    saveTiers(prestationPecId: number, tiers: Tiers[]) {
        // Add tiers
        if (tiers && tiers.length > 0) {
            tiers.forEach(tier => {
                console.log("testTiersSave " + tier.deleted);
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

    /**
    * Save prestation pecs tiers
    * @param prestationPecId
    * @param Attachments
    */
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

    /**
     * Get garanties list by mode
     * @param modeId
     */
    getGarantiesByMode(modeId) {

        /*this.clientService.findGarantiesByClientAndMode(this.contratAssurance.clientId, modeId).subscribe((res: ResponseWrapper) => {
            this.listGarantiesForModeGestion = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));*/

        // TODO : parameter this in the database
        if (modeId == 1 || modeId == 2 || modeId == 4) {

            this.casDeBareme = true;
        } else {
            this.casDeBareme = false;
        }
    }
    verifNbTiers() {
        return this.BtnSaveValide = this.tiers.length < this.sinisterPec.vehicleNumber ? true : false;

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
        this.verifTiers();
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
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.sinisterPec.vehicleNumber = 2;
            this.nbrVehPattern = "^[2-9]$";
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        } else {
            this.sinisterPec.vehicleNumber = 1;
            this.nbrVehPattern = "^[1-9]$";
            //this.sinisterPec.vehicleNumber = 1;
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }

    }
    pointChocObligatoire() {
        this.PointChocRequired = false;
        if (this.pointChoc.avant == true) { this.PointChocRequired = true; }
        else if (this.pointChoc.arriere == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.arriereGauche == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleGauche == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleGauchearriere == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleGaucheAvant == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.arriereDroite == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraledroite == true) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleDroiteAvant) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleDroiteArriere) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.avantGauche) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.avantDroite) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.retroviseurGauche) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.retroviseurDroite) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lunetteArriere) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.pareBriseAvant) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.triangleArriereGauche) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.triangleArriereDroit) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.vitreAvantDroit) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.vitreAvantGauche) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.vitreArriereDroit) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.vitreArriereGauche) {
            this.PointChocRequired = true;
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
        this.verifTiers();
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
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.nbrVehPattern = "^[2-9]$";
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        } else {
            //this.sinisterPec.vehicleNumber = 1;
            this.nbrVehPattern = "^[1-9]$";
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }

    }

    diffCapDcCapR() {
        if (this.vehicule.dcCapital !== null && this.vehicule.dcCapital !== undefined && this.sinisterPec.remainingCapital !== null && this.sinisterPec.remainingCapital !== undefined) {
            if (this.vehicule.dcCapital < this.sinisterPec.remainingCapital) {
                this.alertService.error('auxiliumApp.sinisterPec.home.diffCapDcCapR', null, null);
            }
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

    editObservation(observation) {
        console.log('_____________________________________ edit Observation _______________________________________________');
        this.observation = new Observation();
        this.isObsModeEdit = false;
    }

    deleteTier(tier) {
        console.log('_____________________________________ delete Tier _______________________________________________');
        tier.deleted = true;
        tier.responsible = false;
        /*this.tiers.forEach((item, index) => {
            if (item === tier) this.tiers.splice(index, 1);
        });*/
        this.verifTiers();
    }

    downloadPieceFile(pieceFileAttachment: File) {
        if (pieceFileAttachment) {
            saveAs(pieceFileAttachment);
        }
    }

    deletePieceJ(pieceJ) {
        this.piecesAttachments.forEach((item, index) => {
            if (item === pieceJ) this.piecesAttachments.splice(index, 1);
        });
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

    getPhotoPlusPec() {
        this.sinisterPecService.getPlusPecAttachments(this.sinisterPec.id).subscribe((resPlus) => {
            this.piecesAttachments = resPlus.json;

        });
    }

    savePhotoPlusPec() {
        this.piecesAttachments.forEach(pieceAttFile => {
            pieceAttFile.creationDate = null;
            if (pieceAttFile.pieceFile !== null && this.updatePieceJointe1 == true && (pieceAttFile.id == null || pieceAttFile.id == undefined)) {
                this.sinisterPecService.saveAttachmentsPiecePhotoPlus(this.sinisterPec.id, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.name).subscribe((res: Attachment) => {
                });
            }
        });

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

                /*if (this.expertsAffectation && this.expertsAffectation.length > 0) {
                    //this.sinisterPec.expertId = this.expertsAffectation[0].id;
                    //this.nbreMissionByExpert(this.sinisterPec.expertId);
                } else {
                    this.nbmissionExpert = null;
                    this.nbmissionExpert = null;
                    this.sinisterPec.expertId = null;
                }*/
            });
        }
    }

    prepareEditTier(tier) {
        console.log('_____________________________________ edit Tier _______________________________________________');
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
        //tier.debutValidite = this.demandePecService.formatDateForDirectiveDate(tier.debutValidite);
        //tier.finValidite = this.demandePecService.formatDateForDirectiveDate(tier.finValidite);
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
        this.verifTiers();
        this.nbrTiersResponsable = 0;
        this.tiers.forEach(tr => {
            if (tr.responsible) {
                this.nbrTiersResponsable++;
            }
        });
        console.log("TESTrESPONSABLEtIERSForEdit " + this.nbrTiersResponsable);
        if (this.nbrTiersResponsable > 1 && this.tier.responsible) {
            this.tierResponsableVerif = false;
            this.newFormTiers = true;
            this.showFrmTiers = true;
        } else {
            this.nameAgence = false;
            this.otherField = false;
            this.tier = new Tiers();
            this.isTierModeEdit = false;
            if (this.tiers.length == this.sinisterPec.vehicleNumber - 1) this.newFormTiers == false;
            this.verifTiers();
        }


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
    toggleTiers() {
        this.tiersIsActive = !this.tiersIsActive;
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
    trackGouvernoratById(index: number, item: Governorate) {
        return item.id;
    }

    trackGouvernoratRepById(index: number, item: Governorate) {
        return item.id;
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

    private subscribeToSaveResponse(result: Observable<Sinister>) {
        result.subscribe((res: Sinister) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: Sinister) {
        this.eventManager.broadcast({ name: 'dossierListModification', content: 'OK' });
        this.isSaving = false;
        this.sinister.id = result.id;
        this.pars = this.principal.parseDateWithSeconde(result.creationDate);
        this.date = this.principal.parseDate(result.creationDate);
        this.creation = this.date;
        this.dossierCreated = true;
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

    trackSysVilleById(index: number, item: Delegation) {
        return item.id;
    }

    trackSysVilleRepById(index: number, item: Delegation) {
        return item.id;
    }


    trackVehiculeAssureById(index: number, item: VehiculeAssure) {
        return item.id;
    }

    trackAssureById(index: number, item: Assure) {
        return item.id;
    }
    trackId(index: number, item: Tiers) {
        return item.id;
    }
    generatePJ(pieceJointe) {
        console.log(pieceJointe);
        this.pieceJointeService.generatePJ(pieceJointe).subscribe((pieceJointePrint) => {
            window.open(pieceJointePrint.headers.get('pdfname'), '_blank');
        })
    }
    formatJsDate(date) {
        return this.demandePecService.formatDate(date);
    }
    trackGrantiesId(index: number, item: RefModeGestion) {
        return item.id;
    }
    trackPositionGaId(index: number, item: RefPositionGa) {
        return item.id;
    }
    trackReasonById(index: number, item: RaisonPec) {
        return item.id;
    }
    formatDate(date) {
        let ret = '';
        if (date !== null || date !== undefined) {
            var day = date.day, month = date.month, year = date.year;
            ret += date.day < 10 ? '0' + date.day : date.day; ret += '/';
            ret += date.month < 10 ? '0' + date.month : date.month; ret += '/';
            ret += '' + date.year;
        }
        return ret;
    }



}
