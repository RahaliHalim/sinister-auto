import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload/ng2-file-upload';
import { Observable, Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager, JhiDateUtils } from 'ng-jhipster';
import { Sinister, EtatSinister } from '../sinister/sinister.model';
import { SinisterService } from '../sinister/sinister.service';
import { Delegation, DelegationService } from '../delegation';
import { Reason, ReasonService } from '../reason';
import { Governorate, GovernorateService } from '../governorate';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { ContratAssurance, ContratAssuranceService, HistoryAssistanceComponent } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';
import { ContactService } from '../contact';
import { Principal, ResponseWrapper } from '../../shared';
import { Tiers, TiersService } from '../tiers';
import { SinisterPecService, SinisterStatus } from '../sinister-pec';
import { ClientService } from '../client/client.service';
import { RefModeGestionService, RefModeGestion } from '../ref-mode-gestion';
import { RefPositionGa, RefPositionGaService } from '../ref-position-ga';
import { RefBareme, RefBaremeService, RefBaremePopupDetailService } from '../ref-bareme';
import { Expert, ExpertService } from '../expert';
import { Partner, PartnerService } from '../partner';
import { ObservationService } from '../observation/observation.service';
import { Observation, TypeObservation } from '../observation';
import { DemandSinisterPecService } from "../sinister-pec/demand-sinister-pec.service";
import { PieceJointe, PieceJointeService } from "../piece-jointe";
import { DatePipe } from '@angular/common';
import { RefPackService, RefPack } from '../ref-pack';
import { Client } from '../client';
import { StatusSinister, TypeService, PrestationPecStep } from '../../constants/app.constants';
import { Result } from './result.model';
import { Attachment } from '../attachments';/**
* Get city list by gouvernorat
* @param id
*/
import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { RefTypeServiceService } from '../ref-type-service';
import { Agency, AgencyService } from '../agency';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { NgbModalRef, NgbDateStruct, NgbDateParserFormatter, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPecPopupService } from './sinister-pec-popup.service';
import { DecisionSinisterPecComponent } from '../sinister-pec/decision-sinister-pec.component';
import { GaDatatable, DecisionPec } from '../../constants/app.constants';
import { ConfirmationDialogService } from '../../shared';
import { PointChoc } from './../sinister-pec/';
import { Reparateur, ReparateurService } from '../reparateur';
import { DateUtils } from '../../utils/date-utils';
import { UserExtraService, UserExtra } from '../user-extra';
import { UserPartnerMode } from '../user-partner-mode';
import { ConventionService } from '../../entities/convention/convention.service';
import { RefMotif } from '../ref-motif';
import { HistoryReparationComponent } from '../contrat-assurance/history-reparation/history-reparation.component';
import { PriseEnCharges } from '../sinister/priseencharge.model';
import { SinisterPrestationService } from '../sinister/sinister-prestation.service';
import { SinisterPrestation } from '../sinister/sinister-prestation.model';
import { RaisonAssistance, RaisonAssistanceService } from '../raison-assistance';
import { QuotationService, Quotation } from '../quotation';
import { DetailsPiecesService, DetailsPieces } from '../details-pieces';
import { TypePiecesDevis, QuoteStatus } from '../../constants/app.constants';
import { DetailsMo } from '../details-mo';
import { VehiclePiece } from '../vehicle-piece/vehicle-piece.model';
import { VehiclePieceService } from '../vehicle-piece/vehicle-piece.service';
import { RefTypeInterventionService, RefTypeIntervention } from '../ref-type-intervention';
import { TypeInterventionQuotation } from '../../constants/app.constants';
import { VehicleUsage, VehicleUsageService } from '../vehicle-usage';
import { StampDuty } from '../stamp-duty/stamp-duty.model';
import { VatRateService, VatRate } from '../vat-rate';
import { StampDutyService } from '../stamp-duty/stamp-duty.service';
import { Apec } from '../apec';
import { HistoryService } from '../history';
import { saveAs } from 'file-saver/FileSaver';
import { DomSanitizer } from '@angular/platform-browser';
@Component({
    selector: 'jhi-history-popup-details-pec',
    templateUrl: './historyPopupDetailsPec.component.html'
})
export class HistoryPopupDetailsPec implements OnInit {
    @Input() idSinisterPec: number;

    vehiculeContrat: VehiculeAssure = new VehiculeAssure();
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
    reparateurs: Reparateur[];
    otherField: boolean = false;
    nameAgence: boolean = false;
    isSinPecChangeStatus: boolean = false;
    govRep: boolean = true;
    isTierModeEdit = false;
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
    tiersLength: boolean = true;
    vehicule: VehiculeAssure = new VehiculeAssure();
    piecesAttachmentsAccordSigne: Attachment[] = [];
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
    miseAjour: any;
    userCellule: any;
    prestations: any;
    ville: any;
    gouvernorat: any;
    telephone: any;
    reparateur: any;
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
    garanties: any;
    baremes: RefBareme[];
    compagnie: any;
    agence: any;
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
    enableField: boolean = false;
    isSave: boolean = false;
    BtnSaveValide: boolean = false;
    refBareme = new RefBareme();
    CasBareme = new RefBareme();
    piecesAvantReparationAttachments: Attachment[] = [];
    refBid: number;
    imgBareme: string;
    idBaremeLoaded: number;
    codeBaremeLoaded: number;
    descriptionBaremeLoaded: String;
    showFrmTiers: boolean = false;
    myDate: any;
    motifs: RefMotif[];
    extensionImage: string;
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
    reparateurArrivalTime: any;
    pieceJointe: PieceJointe = new PieceJointe();
    constatFiles: File;
    carteGriseFiles: File;
    acteCessionFiles: File;
    faceAvantDroitFiles: File;
    faceAvantGaucheFiles: File;
    faceArriereDroitFiles: File;
    faceArriereGaucheFiles: File;
    finitionFiles: File;
    immatriculationFiles: File;
    compteurFiles: File;
    nSerieFiles: File;
    carteGriseFilesAvtReparation: File;
    factureFiles: File;
    photoReparationFiles: File;
    bonDeSortieFiles: File;
    accordFiles: File;
    accordComplFiles: File;
    chemin: Result = new Result();
    selectedFiles: FileList;
    constatAttachment: Attachment = new Attachment();
    carteGriseAttachment: Attachment = new Attachment();
    acteCessionAttachment: Attachment = new Attachment();
    constatPreview = false;
    carteGrisePreview = false;
    acteCessionPreview = false;
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    attachmentList: Attachment[];
    modeChoisi: RefModeGestion = new RefModeGestion;
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
    motifsReopened: Reason[];
    isCanceled: boolean = false;
    isRefused: boolean = false;
    isApprouved: boolean = false;
    reopenPrest: boolean = false;
    isDecided: boolean = false;
    isAcceptedWithReserve: boolean = false;
    isAcceptedWithChangeStatus: boolean = false;
    motfReopened: boolean = false;
    decisionPrest: boolean = false;
    isSinPecRefusedCanceled: boolean = false;
    isSinPecVerifOrgPrinted: boolean = false;
    isSinPecBonSortie: boolean = false;
    showModeToModif: boolean = false;
    sendPrest: boolean = false;
    pointChoc: PointChoc = new PointChoc();
    userExtra: UserExtra;
    usersPartnerModes: UserPartnerMode[];
    isCentreExpertise: boolean = false;
    userExtraCnv: UserExtra = new UserExtra();
    piecesAttachmentsPecPlus: Attachment[] = [];
    selectedIdCompagnies: number[] = [];
    containt: boolean = false;
    observationss: Observation[] = [];
    priseencharges: PriseEnCharges = new PriseEnCharges();
    obs: Observation = new Observation();
    insured: Assure = new Assure();
    sinisterReparation: SinisterPec = new SinisterPec();

    sinisterSettings: any = {};
    cities: Delegation[];
    cancelGrounds: RaisonAssistance[] = [];
    reopenGrounds: RaisonAssistance[];
    piecesAttachmentsExpertise: Attachment[] = [];
    citiesDest: Delegation[];
    tugArrivalTime: any;
    insuredArrivalTime: any;
    sinist: SinisterPec = new SinisterPec();
    sin: Sinister = new Sinister();
    modeRef: RefModeGestion = new RefModeGestion;
    showConstatAttachment: boolean = true;
    showCarteGriseAttachment: boolean = true;
    showActeDeCessionAttachment: boolean = true;
    quotation: Quotation = new Quotation();
    quotationAvisExpert: Quotation = new Quotation();
    quotationRectif: Quotation = new Quotation();
    quotationComplementary: Quotation = new Quotation();
    quotationMP: Quotation = new Quotation();
    status: any;
    statusAvisExpert: any;
    statusRectif: any;
    statusComplementary: any;
    ordreDeMissionPreview = false;
    statusMP: any;
    lettreIdaPreview = false;
    lettreOuverturePreview = false;
    detailsPiece: DetailsPieces = new DetailsPieces();
    detailsPiecesIngredient: DetailsPieces[] = [];
    detailsPiecesIngredientAvisExpert: DetailsPieces[] = [];
    detailsPiecesIngredientRectif: DetailsPieces[] = [];
    detailsPiecesIngredientComplementary: DetailsPieces[] = [];
    detailsPiecesIngredientMP: DetailsPieces[] = [];
    detailsPiecesDevis: DetailsPieces[] = [];
    detailsPiecesFourniture: DetailsPieces[] = [];
    detailsPiecesFournitureAvisExpert: DetailsPieces[] = [];
    detailsPiecesFournitureRectif: DetailsPieces[] = [];
    detailsPiecesFournitureComplementary: DetailsPieces[] = [];
    detailsPiecesFournitureMP: DetailsPieces[] = [];
    detailsPiecesMO: DetailsPieces[] = [];
    detailsPiecesMOAvisExpert: DetailsPieces[] = [];
    detailsPiecesMORectif: DetailsPieces[] = [];
    detailsPiecesMOComplementary: DetailsPieces[] = [];
    detailsPiecesMOMP: DetailsPieces[] = [];
    detailsPieces: DetailsPieces[] = [];
    detailsPiecesAvisExpert: DetailsPieces[] = [];
    detailsPiecesRectif: DetailsPieces[] = [];
    detailsPiecesComplementary: DetailsPieces[] = [];
    detailsPiecesMP: DetailsPieces[] = [];
    AvisExpert: boolean = false;
    detailsPieceMo: DetailsPieces = new DetailsPieces();
    totalTtc = 0;
    moTotalTtc = 0;
    ingTotalTtc = 0;
    fournTotalTtc = 0;
    piecesTotalTtc = 0;
    totalTtcAvisExpert = 0;
    moTotalTtcAvisExpert = 0;
    ingTotalTtcAvisExpert = 0;
    fournTotalTtcAvisExpert = 0;
    piecesTotalTtcAvisExpert = 0;
    totalTtcComplementary = 0;
    moTotalTtcComplementary = 0;
    ingTotalTtcComplementary = 0;
    fournTotalTtcComplementary = 0;
    piecesTotalTtcComplementary = 0;
    totalTtcMP = 0;
    moTotalTtcMP = 0;
    ingTotalTtcMP = 0;
    fournTotalTtcMP = 0;
    piecesTotalTtcMP = 0;
    totalTtcRectif = 0;
    moTotalTtcRectif = 0;
    ingTotalTtcRectif = 0;
    fournTotalTtcRectif = 0;
    piecesTotalTtcRectif = 0;
    detailsMos: DetailsMo[] = [];
    detailsMosAvisExpert: DetailsMo[] = [];
    detailsMosComplementary: DetailsMo[] = [];
    detailsMosRectif: DetailsMo[] = [];
    detailsMosMP: DetailsMo[] = [];
    lastDetailsPieces: DetailsPieces[] = [];
    lastDetailsPiecesAvisExpert: DetailsPieces[] = [];
    lastDetailsPiecesRectif: DetailsPieces[] = [];
    lastDetailsPiecesComplementary: DetailsPieces[] = [];
    lastDetailsPiecesMP: DetailsPieces[] = [];
    lastDetails: DetailsPieces[] = [];
    lastDetailsAvisExpert: DetailsPieces[] = [];
    lastDetailsRectif: DetailsPieces[] = [];
    lastDetailsComplementary: DetailsPieces[] = [];
    lastDetailsMP: DetailsPieces[] = [];
    obserExpert: boolean;
    refOperationTypes: RefTypeIntervention[];
    expertsAffectation: Expert[] = [];
    refusagecontrats: VehicleUsage[] = [];
    ingredientPieces: VehiclePiece[] = [];
    fourniturePieces: VehiclePiece[] = [];
    refOperationTypesMo: RefTypeIntervention[];
    refOperationTypesPeinture: RefTypeIntervention[];
    refOperationTypesFourniture: RefTypeIntervention[];
    moPieces: VehiclePiece[] = [];
    codepiece: String[] = [];
    activeStamp: StampDuty;
    listVat: VatRate[];
    model: NgbDateStruct;
    expertObservations = [];
    expertObservationsPF = [];
    dateString: string;
    isDevisComplementaire = false;
    isDevisInitiale = false;
    isDevisRectif = false;
    isDevisAvisExpert = false;
    apecsValid: Apec[] = [];
    apecTotale: Apec = new Apec();
    apecsValid1: Apec[] = [];
    apecTotale1: Apec = new Apec();
    isApecValid = false;
    isApecValid1 = false;
    detailsPiecesMOOpt: DetailsPieces[] = [];
    detailsPiecesOpt: DetailsPieces[] = [];
    detailsPiecesFournitureOpt: DetailsPieces[] = [];
    detailsPiecesIngredientOpt: DetailsPieces[] = [];
    detailsPiecesMOOptRectif: DetailsPieces[] = [];
    detailsPiecesOptRectif: DetailsPieces[] = [];
    detailsPiecesFournitureOptRectif: DetailsPieces[] = [];
    detailsPiecesIngredientOptRectif: DetailsPieces[] = [];
    detailsPiecesMOOptAvisExpert: DetailsPieces[] = [];
    detailsPiecesOptAvisExpert: DetailsPieces[] = [];
    detailsPiecesFournitureOptAvisExpert: DetailsPieces[] = [];
    detailsPiecesIngredientOptAvisExpert: DetailsPieces[] = [];
    MoDetails: DetailsPieces[] = [];
    piecesJointesAttachments: Attachment[] = [];
    faceAvantDroitPreview = false;
    faceAvantGauchePreview = false;
    faceArriereDroitPreview = false;
    faceArriereGauchePreview = false;
    finitionPreview = false;
    facturePreview = false;
    pointChocExpertiseAttachments: Attachment[] = [];
    photoReparationPreview = false;
    bondeSortiePreview = false;
    accordPreview = false;
    accordComplPreview = false;
    immatriculationPreview = false;
    compteurPreview = false;
    nSeriePreview = false;
    carteGriseAvtReparationPreview = false;
    detailsPiecesMORevue: DetailsPieces[] = [];
    detailsPiecesRevue: DetailsPieces[] = [];
    isRevue = false;
    private readonly imageType: string = 'data:image/PNG;base64,';
    private readonly imageTypeJpeg: string = 'data:image/jpeg;base64,';
    lastDetailsRevue: DetailsPieces[] = [];
    detailsPiecesIngredientRevue: DetailsPieces[] = [];
    quotationRevue: Quotation = new Quotation();
    detailsPiecesFournitureRevue: DetailsPieces[] = [];
    statusRevue: any;
    totalTtcRevue = 0;
    moTotalTtcRevue = 0;
    ingTotalTtcRevue = 0;
    fournTotalTtcRevue = 0;
    piecesTotalTtcRevue = 0;
    detailsMosRevue: DetailsMo[] = [];
    lastDetailsPiecesRevue: DetailsPieces[] = [];
    listDevis: History[] = [];
    testt: any;
    quotat: Quotation = new Quotation();
    detailPiecesMOTest: boolean = false;
    detailPiecesTest: boolean = false;
    detailPiecesIngrTest: boolean = false;
    detailPiecesFournTest: boolean = false;
    listAccord: History[] = [];
    piecesAttachments: Attachment[] = [];
    imprimesAttachments: Attachment[] = [];
    quotationPdf: Quotation = new Quotation();


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
        private delegationService: DelegationService,
        private reasonService: ReasonService,
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
        private sinisterPrestationService: SinisterPrestationService,
        private contractService: ContratAssuranceService,
        private insuredService: AssureService,
        private governorateService: GovernorateService,
        private raisonAssistanceService: RaisonAssistanceService,
        private quotationService: QuotationService,
        private detailsPiecesService: DetailsPiecesService,
        private vehiclePieceService: VehiclePieceService,
        private refTypeInterventionService: RefTypeInterventionService,
        private vehicleUsageService: VehicleUsageService,
        private vatRateService: VatRateService,
        private stampDutyService: StampDutyService,
        private ngbDateParserFormatter: NgbDateParserFormatter,
        public activeModal: NgbActiveModal,
        private historyService: HistoryService,
        private sanitizer: DomSanitizer


    ) {

    }
    ngOnInit() {

        //this.histObservation();
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
        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; })
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = true;
        this.typeServiceId = 11;
        this.casDeBareme = false; // TODO : verifiy utility
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => {
                this.sysvilles = res.json;
                this.sysvillesRep = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        // Get company list
        this.expertService.query().subscribe((res: ResponseWrapper) => {
            this.experts = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query()
            .subscribe((res: ResponseWrapper) => {
                this.governorates = res.json;
                this.sysGouvernoratRep = res.json;
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
        this.expertService.query().subscribe((listExpert: ResponseWrapper) => { this.expertsAffectation = listExpert.json; });
        this.governorateService.query().subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleUsageService.query().subscribe((res: ResponseWrapper) => { this.refusagecontrats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.MAIN_OEUVRE).subscribe((res) => {
            this.moPieces = res;
            for (let index = 0; index < this.moPieces.length; index++) {

                this.codepiece.push(this.moPieces[index].code);
            }


        }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.INGREDIENT_FOURNITURE).subscribe((res) => { this.ingredientPieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehiclePieceService.getPiecesByType(TypePiecesDevis.PIECES_FOURNITURE).subscribe((res) => { this.fourniturePieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.query().subscribe((res: ResponseWrapper) => { this.refOperationTypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_MO).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesMo = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_PEINTURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesPeinture = res }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.findByType(TypeInterventionQuotation.INTERVENTION_FOURNITURE).subscribe((res: RefTypeIntervention[]) => { this.refOperationTypesFourniture = res }, (res: ResponseWrapper) => this.onError(res.json));

        this.model = this.setDefaultDate();
        this.onSelectDate(this.model);
        this.vatRateService.query().subscribe((res) => {
            this.listVat = res.json;
        });

        this.stampDutyService.findActiveStamp().subscribe((res) => {
            this.activeStamp = res;
            this.quotation.stampDuty = this.activeStamp.amount;
            this.quotationComplementary.stampDuty = this.activeStamp.amount;

        });

        this.expertObservations = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 3, label: "A Réparer" },
            { id: 4, label: "A Remplacer" },
            { id: 5, label: "Accordé avec Modification" }
        ];

        this.expertObservationsPF = [
            { id: 1, label: "Accordé" },
            { id: 2, label: "Non Accordé" },
            { id: 5, label: "Accordé avec Modification" }
        ];
        // Get cas de bareme list
        this.refBaremeService.getBaremesWithoutPagination().subscribe((res) => { this.baremes = res.json; });

        this.refBaremePopupDetailService.currentmessage.subscribe((idresu) => {
            this.refBid = idresu;
            this.LoadBareme(this.refBid);
        });
        /*this.reasonService.findAllMotifReopened().subscribe((res) => {
            this.motifsReopened = res.json;
        });*/

        this.initializeDemand();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtra = usr;
                if (this.userExtra.profileId === 26) {
                    this.isCentreExpertise = true;
                } else {
                    this.isCentreExpertise = false;
                }
            });
        });
        this.refPositionGaService.query().subscribe((res: ResponseWrapper) => {
            this.listPositionGa = res.json;
        });
    }
    search(vehicleRegistration: any) {

    }

    getImage(refB: RefBareme) {
        if (refB.attachmentName == 'png' || refB.attachmentName == 'PNG') {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageType + refB.attachment64);
        } else {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageTypeJpeg + refB.attachment64);
        }
    }

    getPieceNew(entityName: string, sinisterPecId: number, label: string) {
        this.prestationPECService.getPieceBySinPecAndLabel(entityName, sinisterPecId, label).subscribe((blob: Blob) => {
            const indexOfFirst = (blob.type).indexOf('/');
            this.extensionImage = ((blob.type).substring((indexOfFirst + 1), ((blob.type).length)));
            this.extensionImage = this.extensionImage.toLowerCase();
            let fileName = label + "." + this.extensionImage;
            console.log(fileName);
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, fileName);
                console.log("if--------------------");
            } else {
                console.log("else--------------------");
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

    getPiece(piece: File) {
        if (piece) {
            saveAs(piece);
        }
    }

    downloadConstatFile() {
        if (this.constatFiles) {
            saveAs(this.constatFiles);
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
        this.constatFiles = fileInput.target.files[0];
        this.constatPreview = true;
        console.log(this.constatFiles);
    }
    onCarteGrisFileChange(fileInput: any) {
        this.carteGriseFiles = fileInput.target.files[0];
        this.carteGrisePreview = true;
        console.log(this.carteGriseFiles);
    }
    onActeCessionFileChange(fileInput: any) {
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
                    //this.imgBareme = require('../../../content/images/refBareme/' + this.CasBareme.attachment.name);
                }
            });
        }
    }

    receiveRefBareme(rB: RefBareme) {
        this.refBareme = rB;
    }

    downloadPieceFile(pieceFileAttachment: File) {
        console.log("eyyy laaa");
        if (pieceFileAttachment) {
            saveAs(pieceFileAttachment);
        }
    }

    getAttachmentAvantReparation() {

        this.prestationPECService.getPhotoAvantReparationAttachments("quotation", this.sinisterPec.id).subscribe((resAttt) => {
            this.piecesAvantReparationAttachments = resAttt.json;
            if (this.piecesAvantReparationAttachments.length !== 0) {
                this.faceAvantDroitPreview = true;
                this.faceAvantGauchePreview = true;
                this.faceArriereDroitPreview = true;
                this.faceArriereGauchePreview = true;
                this.nSeriePreview = true;
                this.immatriculationPreview = true;
                this.compteurPreview = true;
                this.finitionPreview = true;
                this.carteGriseAvtReparationPreview = true;
            }
        });


    }

    getPhotoPlusPec() {
        this.prestationPECService.getPlusPecAttachments(this.sinisterPec.id).subscribe((resPlus) => {
            this.piecesAttachmentsPecPlus = resPlus.json;

        });
    }

    getAttachmentPointChocExpertise() {

        this.prestationPECService.getPhotoAvantReparationAttachments("pointChocExpertise", this.sinisterPec.id).subscribe((resAttt) => {
            this.pointChocExpertiseAttachments = resAttt.json;
        });


    }

    getPhotoExpertise() {
        this.prestationPECService.getExpertiseAttachments(this.sinisterPec.id).subscribe((resImprime) => {
            this.piecesAttachmentsExpertise = resImprime.json;
        });


    }


    /**
     * Initialize dossier information
     */
    initializeDemand() {
        console.log('____________________________________________________________________________________________________________________________________________1');
        if (this.idSinisterPec) {
            this.pecId = this.idSinisterPec;
            this.enableField = false;
            // Get pec prestation
            this.prestationPECService.findPrestationPec(this.pecId).subscribe((pecRes: SinisterPec) => {
                this.sinisterPec = pecRes;
                if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                    this.getPhotoPlusPec();
                    this.getAttachmentPointChocExpertise();
                    this.getPhotoExpertise();

                    if (this.sinisterPec.generatedLetter == true) {
                        this.lettreIdaPreview = true;
                        this.lettreOuverturePreview = true;

                        switch (this.sinisterPec.modeId) {
                            case 1:
                                this.lettreIdaPreview = true;
                                this.lettreOuverturePreview = true;
                                break;
                            case 2:
                                this.lettreIdaPreview = true;
                                this.lettreOuverturePreview = true;
                                break;
                            case 3:
                                this.lettreOuverturePreview = true;
                                break;
                            case 4:
                                this.lettreOuverturePreview = true;
                                break;
                            default:
                                if (this.sinisterPec.labelPosGa == "Recours") {
                                    this.lettreIdaPreview = true;
                                    this.lettreOuverturePreview = true;
                                }
                                break;
                        }

                    }

                    this.getAttachmentAvantReparation();
                    this.getAttachmentAccordSigne();

                    if (this.sinisterPec.expertId !== null && this.sinisterPec.expertId !== undefined) {
                        this.ordreDeMissionPreview = true;
                    }

                    if (this.sinisterPec.stepId == 40) {
                        this.bondeSortiePreview = true;
                    }
                    if (this.sinisterPec.stepId == 40 || this.sinisterPec.stepId == 37 || this.sinisterPec.stepId == 36 || this.sinisterPec.stepId == 35) {
                        this.facturePreview = true;
                        this.photoReparationPreview = true;
                    }
                    if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                        if (this.sinisterPec.stepId == 110 || this.sinisterPec.stepId == 35 || this.sinisterPec.stepId == 36 || this.sinisterPec.stepId == 37
                            || this.sinisterPec.stepId == 40) {
                            this.accordPreview = true;
                        }
                    } else if (this.sinisterPec.listComplementaryQuotation.length == 1) {
                        if (this.sinisterPec.stepId == 110 || this.sinisterPec.stepId == 35 || this.sinisterPec.stepId == 36 || this.sinisterPec.stepId == 37
                            || this.sinisterPec.stepId == 40) {
                            this.accordComplPreview = true;
                            this.accordPreview = true;
                        }
                    } else {
                        this.accordPreview = true;
                        this.accordComplPreview = true;
                    }
                    this.getPhotoReparation();

                    this.historyService.findListDevisByHistoryPec(this.sinisterPec.id).subscribe((res) => {
                        this.listDevis = res.json;


                    });
                    this.historyService.findListAccordByHistoryPec(this.sinisterPec.id).subscribe((res) => {
                        this.listAccord = res.json;


                    });

                    //this.initializeDataPieceJointe();
                    this.getImprime();

                    if (this.sinisterPec.stepId == 37 || this.sinisterPec.stepId == 35 || this.sinisterPec.stepId == 36 || this.sinisterPec.stepId == 40) {
                        this.facturationBlock();
                    }

                    if (this.sinisterPec.pointChoc !== null) {
                        console.log(this.sinisterPec.pointChoc);
                        this.pointChoc = this.sinisterPec.pointChoc;
                    }
                    this.prestationPECService.getAutresPiecesJointes(this.sinisterPec.id).subscribe((resImprime) => {
                        this.piecesJointesAttachments = resImprime.json;

                    });
                    this.changeModeGestion(this.sinisterPec.modeId);
                    this.verifTiers();
                    this.sinisterService.find(this.sinisterPec.sinisterId).subscribe((sinister: Sinister) => {
                        this.sinister = sinister;
                        this.initContrat(this.sinister.contractId, this.sinister.vehicleId);
                        this.sinister.nature = 'ACCIDENT';

                        const date1 = new Date(this.sinisterPec.declarationDate);
                        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSS(date1);

                        this.creation = this.principal.parseDate(this.sinister.creationDate);
                        this.cloture = this.principal.parseDate(this.sinister.closureDate);
                        this.miseAjour = this.principal.parseDate(this.sinister.updateDate);
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
                    this.tiers = this.sinisterPec.tiers;
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
                        if (this.apecsValid && this.apecsValid.length > 0 && (this.sinisterPec.stepId == 40 || this.sinisterPec.stepId == 37 || this.sinisterPec.stepId == 36 || this.sinisterPec.stepId == 35)) {
                            this.isApecValid = true;
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


                    this.refModeGestionService.find(this.sinisterPec.modeId).subscribe((modeRes) => {
                        this.modeChoisi = modeRes;

                    });
                    if (this.sinisterPec.baremeId) {
                        this.LoadBareme(this.sinisterPec.baremeId);
                    }

                    this.observationService.findByPrestation(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                        this.observationss = subRes.json;
                        if (this.observationss.length == 0) {
                            this.observationss = [];
                        } else if (this.observationss.length > 0) {
                            this.observationss.forEach(observation => {
                                observation.date = this.demandePecService.formatEnDate(observation.date);
                            });
                        }
                    });
                } else {
                    this.router.navigate(["/accessdenied"]);
                }


            });
            this.reasonService.findAllMotifReopened().subscribe((res) => {
                this.motifsReopened = res.json;
            });
        }

    }

    onDevisSelect(id: number) {
        this.quotation = new Quotation();
        this.detailsPiecesMOOptAvisExpert = [];
        this.detailsPiecesIngredientOptAvisExpert = [];
        this.detailsPiecesOptAvisExpert = [];
        this.detailsPiecesFournitureOptAvisExpert = [];
        this.detailPiecesMOTest = false;
        this.detailPiecesTest = false;
        this.detailPiecesIngrTest = false;
        this.detailPiecesFournTest = false;
        this.historyService.findQuotationHistoryPecById(id).subscribe((quote: Quotation) => {
            this.quotat = quote;
            this.quotationPdf = quote;
            this.getDevisAvisExpert(quote);
        });


    }
    onAccordSelect(id: number) {
        this.apecTotale1 = new Apec();

        this.historyService.findApecHistoryPecById(id).subscribe((apec: Apec) => {
            this.apecTotale1 = apec;
        });


    }


    getDevis(quotation) {
        //this.quotationService.find(quotationId).subscribe((res) => {  // Find devid By ID
        this.quotation = quotation;
        console.log("testLengthListesPieces " + this.quotation.listPieces.length);
        console.log(this.quotation.listPieces);
        let z = 0;
        this.quotation.listPieces.forEach(element => {
            this.vehiclePieceService.find(element.designationId).subscribe((vehicPiece: VehiclePiece) => {
                z++;
                if (vehicPiece.typeId == TypePiecesDevis.MAIN_OEUVRE && element.isMo == true && element.modifiedLine == null) {
                    this.detailsPiecesMOOpt.push(element);
                } if (vehicPiece.typeId == TypePiecesDevis.INGREDIENT_FOURNITURE && element.isMo == false && element.modifiedLine == null) {
                    this.detailsPiecesIngredientOpt.push(element);
                } if (vehicPiece.typeId == TypePiecesDevis.MAIN_OEUVRE && element.isMo == false && element.modifiedLine == null) {
                    this.detailsPiecesOpt.push(element);
                } if (vehicPiece.typeId == TypePiecesDevis.PIECES_FOURNITURE && element.isMo == false && element.modifiedLine == null) {
                    this.detailsPiecesFournitureOpt.push(element);
                }
                if (z == this.quotation.listPieces.length) {
                    this.quotation.confirmationDecisionQuote = false;
                    this.loadAllDetailsMo(this.detailsPiecesMOOpt, this.detailsPiecesIngredientOpt, this.detailsPiecesOpt, this.detailsPiecesFournitureOpt);
                    this.loadAllIngredient(this.detailsPiecesIngredientOpt, this.detailsPiecesOpt, this.detailsPiecesFournitureOpt);
                    this.loadAllRechange(this.detailsPiecesOpt, this.detailsPiecesFournitureOpt);
                    this.loadAllFourniture(this.detailsPiecesFournitureOpt);
                    this.status = this.quotation.statusId; // Get etat de devis selectionné 
                    if (this.AvisExpert && this.quotation.expertiseDate == null) { this.quotation.expertiseDate = this.dateAsYYYYMMDDHHNNSS(new Date()); }
                }
            });
        });

        //})
    }

    getDevisAvisExpert(quotation) {


        this.quotationAvisExpert = quotation;

        this.detailsPiecesMOOptAvisExpert = this.quotationAvisExpert.listMainO;
        this.detailsPiecesIngredientOptAvisExpert = this.quotationAvisExpert.listIngredients;
        this.detailsPiecesOptAvisExpert = this.quotationAvisExpert.listItemsPieces;
        this.detailsPiecesFournitureOptAvisExpert = this.quotationAvisExpert.listFourniture;
        this.quotationAvisExpert.confirmationDecisionQuote = false;
        this.loadAllDetailsMoAvisExpert(this.detailsPiecesMOOptAvisExpert, this.detailsPiecesIngredientOptAvisExpert, this.detailsPiecesOptAvisExpert, this.detailsPiecesFournitureOptAvisExpert);
        this.statusAvisExpert = this.quotationAvisExpert.statusId; // Get etat de devis selectionné 
    }

    printQuotationPdf() {
        this.quotationPdf.listMainO = this.detailsPiecesMOOptAvisExpert;
        this.quotationPdf.listIngredients = this.detailsPiecesIngredientOptAvisExpert;
        this.quotationPdf.listFourniture = this.detailsPiecesFournitureOptAvisExpert;
        this.quotationPdf.listItemsPieces = this.detailsPiecesOptAvisExpert;
        this.quotationPdf.listPieces = [];
        if (this.detailPiecesIngrTest || this.detailPiecesMOTest || this.detailPiecesTest || this.detailPiecesFournTest) {
            this.quotationPdf.pdfGenerationAvisTechnique = true;
        }
        if ((this.quotationPdf.listMainO.length !== 0 || this.quotationPdf.listIngredients.length !== 0 || this.quotationPdf.listFourniture.length !== 0 || this.quotationPdf.listItemsPieces.length !== 0) && this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
            this.quotationPdf.sinPecId = this.sinisterPec.id;
            this.prestationPECService.generateQuoatationPdf(this.quotationPdf).subscribe((res) => {
                window.open(res.headers.get('pdfname'), '_blank');
            });
        }
    }


    loadAllDetailsMo(detailsPiecesMOOpt, detailsPiecesIngredientOpt, detailsPiecesOpt, detailsPiecesFournitureOpt) {
        this.detailsPiecesMO = detailsPiecesMOOpt;
        if (this.AvisExpert) {
            this.detailsPiecesMO.forEach(detailPiecesMO => {
                this.observationExpertByTypeIntervention(detailPiecesMO);
            });
        }
        this.calculateGlobalMoTtc();
        this.getSum4();
        this.loadAllIngredient(detailsPiecesIngredientOpt, detailsPiecesOpt, detailsPiecesFournitureOpt);

    }

    getOrdreMissionExpertPdf(sinisterPec: SinisterPec) {
        if (sinisterPec.id == null || sinisterPec.id === undefined) {
            console.log("Erreur lors de la génération");

        } else { // OK
            this.prestationPECService.getOrdreMissionExpertPdf(sinisterPec.id).subscribe((blob: Blob) => {
                let fileName = "OrdreDeMission" + ".pdf";
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
    }

    loadAllDetailsMoAvisExpert(detailsPiecesMOOpt, detailsPiecesIngredientOpt, detailsPiecesOpt, detailsPiecesFournitureOpt) {
        this.detailsPiecesMOAvisExpert = detailsPiecesMOOpt;
        this.MoDetails = detailsPiecesMOOpt;
        this.detailsPiecesMOAvisExpert.forEach(detailPiecesMO => {
            this.observationExpertByTypeIntervention(detailPiecesMO);
        });
        let som = 0;
        this.MoDetails.forEach(detailsPiece => {
            console.log("iciiiiiiiiiilogg" + som)
        });
        this.quotationAvisExpert.estimateJour = (som / 8) + 1;



        for (let i = 0; i < this.detailsPiecesMOAvisExpert.length; i++) {
            if (this.detailsPiecesMOAvisExpert[i].observationExpert != null) {

                this.detailPiecesMOTest = true;
            }


        }

        this.calculateGlobalMoTtcAvisExpert();
        this.getSum4AvisExpert();
        this.loadAllIngredientAvisExpert(detailsPiecesIngredientOpt, detailsPiecesOpt, detailsPiecesFournitureOpt);




    }

    getPieceForAttachment(entityName: string, attachmentId: number, originalName: string) {
        this.prestationPECService.getPieceByAttachmentIdAndEntityName(entityName, attachmentId).subscribe((blob: Blob) => {
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

    loadAllIngredientAvisExpert(detailsPiecesIngredientOpt, detailsPiecesOpt, detailsPiecesFournitureOpt) {

        this.detailsPiecesIngredientAvisExpert = detailsPiecesIngredientOpt;
        for (let i = 0; i < this.detailsPiecesIngredientAvisExpert.length; i++) {
            if (this.detailsPiecesIngredientAvisExpert[i].observationExpert != null) {

                this.detailPiecesIngrTest = true;
            }


        }
        this.calculateGlobalIngTtcAvisExpert();
        this.loadAllRechangeAvisExpert(detailsPiecesOpt, detailsPiecesFournitureOpt);
    }

    loadAllRechangeAvisExpert(detailsPiecesOpt, detailsPiecesFournitureOpt) {
        this.detailsPiecesAvisExpert = detailsPiecesOpt;
        console.log(" taillle piecess longeurrrr" + this.detailsPiecesAvisExpert.length);
        for (let i = 0; i < this.detailsPiecesAvisExpert.length; i++) {

        }
        for (let i = 0; i < this.detailsPiecesAvisExpert.length; i++) {
            if (this.detailsPiecesAvisExpert[i].observationExpert != null) {

                this.detailPiecesTest = true;
            }


        }
        this.calculateGlobalPiecesTtcAvisExpert();
        this.loadAllFournitureAvisExpert(detailsPiecesFournitureOpt);



    }

    loadAllFournitureAvisExpert(detailsPiecesFournitureOpt) {
        this.detailsPiecesFournitureAvisExpert = detailsPiecesFournitureOpt;
        for (let i = 0; i < this.detailsPiecesFournitureAvisExpert.length; i++) {
            if (this.detailsPiecesAvisExpert[i].observationExpert != null) {

                this.detailPiecesFournTest = true;
            }


        }
        this.calculateGlobalFournTtcAvisExpert();
        let object = this.lastDetailsAvisExpert.concat(this.detailsPiecesMOAvisExpert, this.detailsPiecesAvisExpert, this.detailsPiecesFournitureAvisExpert, this.detailsPiecesIngredientAvisExpert);
        object.forEach(lastDetail => {
            let obj = Object.assign({}, lastDetail)
            this.lastDetailsPiecesAvisExpert.push(obj);
        });
        this.DecisionExpertByObser(5, null);



    }

    calculateGlobalFournTtcAvisExpert() {
        this.quotationAvisExpert.ttcAmount = this.quotationAvisExpert.stampDuty ? this.quotationAvisExpert.stampDuty : 0;
        this.fournTotalTtcAvisExpert = 0;
        if (this.detailsPiecesFournitureAvisExpert && this.detailsPiecesFournitureAvisExpert.length > 0) {
            for (let i = 0; i < this.detailsPiecesFournitureAvisExpert.length; i++) {
                if (this.detailsPiecesFournitureAvisExpert[i].isModified != true) {
                    this.fournTotalTtcAvisExpert += this.detailsPiecesFournitureAvisExpert[i].totalTtc;
                }
            }
        }
        this.fournTotalTtcAvisExpert = +(this.fournTotalTtcAvisExpert).toFixed(3);
        this.quotationAvisExpert.ttcAmount += this.moTotalTtcAvisExpert + this.ingTotalTtcAvisExpert + this.fournTotalTtcAvisExpert + this.piecesTotalTtcAvisExpert;
        this.quotationAvisExpert.ttcAmount = +(this.quotationAvisExpert.ttcAmount).toFixed(3);
    }

    calculateGlobalPiecesTtcAvisExpert() {
        this.quotationAvisExpert.ttcAmount = this.quotationAvisExpert.stampDuty ? this.quotationAvisExpert.stampDuty : 0;
        this.piecesTotalTtcAvisExpert = 0;
        if (this.detailsPiecesAvisExpert && this.detailsPiecesAvisExpert.length > 0) {
            for (let i = 0; i < this.detailsPiecesAvisExpert.length; i++) {
                //if (this.detailsPieces[i].modified) {
                this.piecesTotalTtcAvisExpert += this.detailsPiecesAvisExpert[i].totalTtc;
                //}
            }
        }
        this.piecesTotalTtcAvisExpert = +(this.piecesTotalTtcAvisExpert).toFixed(3);
        this.quotationAvisExpert.ttcAmount += this.moTotalTtcAvisExpert + this.ingTotalTtcAvisExpert + this.fournTotalTtcAvisExpert + this.piecesTotalTtcAvisExpert;
        this.quotationAvisExpert.ttcAmount = +(this.quotationAvisExpert.ttcAmount).toFixed(3);
    }

    calculateGlobalIngTtcAvisExpert() {
        this.quotationAvisExpert.ttcAmount = this.quotationAvisExpert.stampDuty ? this.quotationAvisExpert.stampDuty : 0;
        this.ingTotalTtcAvisExpert = 0;
        if (this.detailsPiecesIngredientAvisExpert && this.detailsPiecesIngredientAvisExpert.length > 0) {
            for (let i = 0; i < this.detailsPiecesIngredientAvisExpert.length; i++) {
                //if (this.detailsPiecesIngredient[i].modified != true) {
                this.ingTotalTtcAvisExpert += this.detailsPiecesIngredientAvisExpert[i].totalTtc;
                //}
            }
        }
        this.ingTotalTtcAvisExpert = +(this.ingTotalTtcAvisExpert).toFixed(3);
        this.quotationAvisExpert.ttcAmount += this.moTotalTtcAvisExpert + this.ingTotalTtcAvisExpert + this.fournTotalTtcAvisExpert + this.piecesTotalTtcAvisExpert;
        this.quotationAvisExpert.ttcAmount = +(this.quotationAvisExpert.ttcAmount).toFixed(3);
    }

    getSum4AvisExpert(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMosAvisExpert.length; i++) {
            if (this.detailsMosAvisExpert[i].typeInterventionId && this.detailsMosAvisExpert[i].nombreHeures) {
                sum += this.detailsMosAvisExpert[i].totalHt;
            }
        }
        return sum;
    }

    calculateGlobalMoTtcAvisExpert() {
        this.quotationAvisExpert.ttcAmount = this.quotationAvisExpert.stampDuty ? this.quotationAvisExpert.stampDuty : 0;
        this.moTotalTtcAvisExpert = 0;
        if (this.detailsPiecesMOAvisExpert && this.detailsPiecesMOAvisExpert.length > 0) {
            for (let i = 0; i < this.detailsPiecesMOAvisExpert.length; i++) {
                this.moTotalTtcAvisExpert += this.detailsPiecesMOAvisExpert[i].totalTtc;
            }
        }
        this.moTotalTtcAvisExpert = +(this.moTotalTtcAvisExpert).toFixed(3);
        this.quotationAvisExpert.ttcAmount += this.moTotalTtcAvisExpert + this.ingTotalTtcAvisExpert + this.fournTotalTtcAvisExpert + this.piecesTotalTtcAvisExpert;
        this.quotationAvisExpert.ttcAmount = +(this.quotationAvisExpert.ttcAmount).toFixed(3);
    }



    loadAllIngredient(detailsPiecesIngredientOpt, detailsPiecesOpt, detailsPiecesFournitureOpt) {
        this.detailsPiecesIngredient = detailsPiecesIngredientOpt;
        this.calculateGlobalIngTtc();
        this.loadAllRechange(detailsPiecesOpt, detailsPiecesFournitureOpt);

    }

    loadAllRechange(detailsPiecesOpt, detailsPiecesFournitureOpt) {

        this.detailsPieces = detailsPiecesOpt;
        this.calculateGlobalPiecesTtc();
        this.loadAllFourniture(detailsPiecesFournitureOpt);


    }



    loadAllFourniture(detailsPiecesFournitureOpt) {
        this.detailsPiecesFourniture = detailsPiecesFournitureOpt;
        this.calculateGlobalFournTtc();
        let object = this.lastDetails.concat(this.detailsPiecesMO, this.detailsPieces, this.detailsPiecesFourniture, this.detailsPiecesIngredient);
        object.forEach(lastDetail => {
            let obj = Object.assign({}, lastDetail)
            this.lastDetailsPieces.push(obj);
        });
        if (this.AvisExpert) {
            this.DecisionExpertByObser(5, null);
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

    DecisionExpertByObser(id: number, detailsPiece: DetailsPieces) {
        if (id == 1) { detailsPiece.nombreMOEstime = 0; }
        if (id == 2) { detailsPiece.nombreMOEstime = 0; }
        if (id == 2 || id == 3 || id == 4) {
            this.obserExpert = false;

        } else {

            this.detailsPiecesDevis = [];
            let list = this.detailsPiecesIngredientAvisExpert.concat(this.detailsPiecesAvisExpert, this.detailsPiecesMOAvisExpert, this.detailsPiecesIngredientAvisExpert, this.detailsPiecesFournitureAvisExpert)
            for (let index = 0; index < list.length; index++) {
                const element = list[index];
                this.obserExpert = element.observationExpert == 1 ? true : false;
                if (!this.obserExpert) break;
            }
        }
    }

    changeFourniture(fourniture: DetailsPieces) {
        fourniture.totalHt = +(fourniture.prixUnit * fourniture.quantite).toFixed(3);
        fourniture.amountDiscount = +(fourniture.totalHt * fourniture.discount / 100).toFixed(3);
        fourniture.amountAfterDiscount = +(fourniture.totalHt - fourniture.amountDiscount).toFixed(3);
        fourniture.amountVat = +(fourniture.amountAfterDiscount * fourniture.tva / 100).toFixed(3);
        fourniture.totalTtc = +(fourniture.amountAfterDiscount + fourniture.amountVat).toFixed(3);
        this.calculateGlobalFournTtc();
    }

    calculerHTVVetusteAndTTCVetuste(detailsPiece: DetailsPieces) {
        detailsPiece.HTVetuste = detailsPiece.totalHt * detailsPiece.vetuste / 100;
        detailsPiece.TTCVetuste = detailsPiece.HTVetuste + (detailsPiece.HTVetuste * 19 / 100);
    }

    changeReference(pieces: any) {
        console.log("piece-**-*-*-" + pieces);
        this.vehiclePieceService.find(pieces.reference).subscribe((res: VehiclePiece) => {
            console.log("resssss idddd-*-*" + res.id);

            pieces.designationId = res.id;
        });
    }

    getAttachmentAccordSigne() {

        this.prestationPECService.getAttachmentsAccordSigne("APEC Accord", this.sinisterPec.id, "Attachement Signature accord").subscribe((resAttt) => {
            this.piecesAttachmentsAccordSigne = resAttt.json;
        });


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
    }

    calculateGlobalFournTtcRectif() {
        this.quotationRectif.ttcAmount = this.quotationRectif.stampDuty ? this.quotationRectif.stampDuty : 0;
        this.fournTotalTtcRectif = 0;
        if (this.detailsPiecesFournitureRectif && this.detailsPiecesFournitureRectif.length > 0) {
            for (let i = 0; i < this.detailsPiecesFournitureRectif.length; i++) {
                if (this.detailsPiecesFournitureRectif[i].isModified != true) {
                    this.fournTotalTtcRectif += this.detailsPiecesFournitureRectif[i].totalTtc;
                }
            }
        }
        this.fournTotalTtcRectif = +(this.fournTotalTtcRectif).toFixed(3);
        this.quotationRectif.ttcAmount += this.moTotalTtcRectif + this.ingTotalTtcRectif + this.fournTotalTtcRectif + this.piecesTotalTtcRectif;
        this.quotationRectif.ttcAmount = +(this.quotationRectif.ttcAmount).toFixed(3);
    }

    facturationBlock() {


        if (this.sinisterPec.primaryQuotationId) {

            this.quotationService.find(this.sinisterPec.primaryQuotationId).subscribe((res) => {  // Find devid By ID
                this.quotation = res;
                this.quotation.stampDuty = this.activeStamp.amount;
                this.quotation.confirmationDecisionQuote = false;

                this.loadAllDetailsMoFacture();
                this.loadAllIngredientFacture();
                this.loadAllRechangeFacture();
                this.loadAllFournitureFacture();

                if (this.sinisterPec.listComplementaryQuotation.length > 0) {

                    this.sinisterPec.listComplementaryQuotation.forEach(element => {
                        if (element.isConfirme) {
                            this.quotationService.find(element.id).subscribe((res) => {  // Find devid By ID
                                this.quotation = res;
                                this.quotation.stampDuty = this.activeStamp.amount;

                                this.loadAllDetailsMoFacture();
                                this.loadAllIngredientFacture();
                                this.loadAllRechangeFacture();
                                this.loadAllFournitureFacture();

                            });
                        }
                    });
                }
            })
        }
    }

    loadAllDetailsMoFacture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, true).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPiecesMO.push(element);
            });


            this.calculateGlobalMoTtc();
            this.getSum4();
        })
    }

    loadAllIngredientFacture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.INGREDIENT_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPiecesIngredient.push(element);
            });

            this.calculateGlobalIngTtc();
        })
    }

    loadAllRechangeFacture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.MAIN_OEUVRE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPieces.push(element);
            });

            this.calculateGlobalPiecesTtc();
        })
    }

    loadAllFournitureFacture() {
        this.detailsPiecesService.queryByDevisAndType(this.quotation.id, TypePiecesDevis.PIECES_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            subRes.json.forEach(element => {
                element.id = null;
                this.detailsPiecesFourniture.push(element);
            });
            this.calculateGlobalFournTtc();
        })
    }

    getPieceNewAccordComplementaire(entityName: string, sinisterPecId: number, label: string) {
        this.prestationPECService.getPieceBySinPecAndLabelForAccordComplementaire(entityName, sinisterPecId, label).subscribe((blob: Blob) => {
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

    calculateGlobalPiecesTtcRectif() {
        this.quotationRectif.ttcAmount = this.quotationRectif.stampDuty ? this.quotationRectif.stampDuty : 0;
        this.piecesTotalTtcRectif = 0;
        if (this.detailsPiecesRectif && this.detailsPiecesRectif.length > 0) {
            for (let i = 0; i < this.detailsPiecesRectif.length; i++) {
                //if (this.detailsPieces[i].modified) {
                this.piecesTotalTtcRectif += this.detailsPiecesRectif[i].totalTtc;
                //}
            }
        }
        this.piecesTotalTtcRectif = +(this.piecesTotalTtcRectif).toFixed(3);
        this.quotationRectif.ttcAmount += this.moTotalTtcRectif + this.ingTotalTtcRectif + this.fournTotalTtcRectif + this.piecesTotalTtcRectif;
        this.quotationRectif.ttcAmount = +(this.quotationRectif.ttcAmount).toFixed(3);
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
    }

    calculateGlobalIngTtcRectif() {
        this.quotationRectif.ttcAmount = this.quotationRectif.stampDuty ? this.quotationRectif.stampDuty : 0;
        this.ingTotalTtcRectif = 0;
        if (this.detailsPiecesIngredientRectif && this.detailsPiecesIngredientRectif.length > 0) {
            for (let i = 0; i < this.detailsPiecesIngredientRectif.length; i++) {
                //if (this.detailsPiecesIngredient[i].modified != true) {
                this.ingTotalTtcRectif += this.detailsPiecesIngredientRectif[i].totalTtc;
                //}
            }
        }
        this.ingTotalTtcRectif = +(this.ingTotalTtcRectif).toFixed(3);
        this.quotationRectif.ttcAmount += this.moTotalTtcRectif + this.ingTotalTtcRectif + this.fournTotalTtcRectif + this.piecesTotalTtcRectif;
        this.quotationRectif.ttcAmount = +(this.quotationRectif.ttcAmount).toFixed(3);
    }

    changeDesignation(pieces: any) {
        console.log("piece designation-**-*-*-" + pieces);
        this.vehiclePieceService.find(pieces.designationId).subscribe((res: VehiclePiece) => {
            console.log("resssss idddd designation-*-*" + res.id);
            pieces.reference = res.id;
        });
    }

    changePieceRechange(rechange: DetailsPieces) {
        rechange.totalHt = +(rechange.prixUnit * rechange.quantite).toFixed(3);
        rechange.amountDiscount = +(rechange.totalHt * rechange.discount / 100).toFixed(3);
        rechange.amountAfterDiscount = +(rechange.totalHt - rechange.amountDiscount).toFixed(3);
        rechange.amountVat = +(rechange.amountAfterDiscount * rechange.tva / 100).toFixed(3);
        rechange.totalTtc = +(rechange.amountAfterDiscount + rechange.amountVat).toFixed(3);
        this.calculateGlobalPiecesTtc();
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
    getPhotoReparation() {

        this.prestationPECService.getPhotoReparationAttachments(this.sinisterPec.id).subscribe((resImprime) => {
            this.piecesAttachments = resImprime.json;

        });


    }


    downloadfaceAvantDroitFile() {
        if (this.faceAvantDroitFiles) {
            saveAs(this.faceAvantDroitFiles);
        }
    }
    downloadFaceAvantGaucheFile() {
        if (this.faceAvantGaucheFiles) {
            saveAs(this.faceAvantGaucheFiles);
        }
    }
    downloadImmatriculationFile() {
        if (this.immatriculationFiles) {
            saveAs(this.immatriculationFiles);
        }
    }
    downloadFaceArriereDroitFile() {
        if (this.faceArriereDroitFiles) {
            saveAs(this.faceArriereDroitFiles);
        }
    }
    downloadFaceArriereGaucheFile() {
        if (this.faceArriereGaucheFiles) {
            saveAs(this.faceArriereGaucheFiles);
        }
    }
    downloadCompteurFile() {
        if (this.compteurFiles) {
            saveAs(this.compteurFiles);
        }
    }
    downloadFinitionFile() {
        if (this.finitionFiles) {
            saveAs(this.finitionFiles);
        }
    }
    downloadNSerieFile() {
        if (this.nSerieFiles) {
            saveAs(this.nSerieFiles);
        }
    }
    downloadCarteGriseFileAvantReparation() {
        if (this.carteGriseFilesAvtReparation) {
            saveAs(this.carteGriseFilesAvtReparation);
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
    downloadBonDeSortieFile() {
        if (this.bonDeSortieFiles) {
            saveAs(this.bonDeSortieFiles);
        }
    }
    downloadAccordFile() {
        if (this.accordFiles) {
            saveAs(this.accordFiles);
        }
    }
    downloadAccordComplFile() {
        if (this.accordComplFiles) {
            saveAs(this.accordComplFiles);
        }
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
    }

    calculateGlobalMoTtcRectif() {
        this.quotationRectif.ttcAmount = this.quotationRectif.stampDuty ? this.quotationRectif.stampDuty : 0;
        this.moTotalTtcRectif = 0;
        if (this.detailsPiecesMORectif && this.detailsPiecesMORectif.length > 0) {
            for (let i = 0; i < this.detailsPiecesMORectif.length; i++) {
                this.moTotalTtcRectif += this.detailsPiecesMORectif[i].totalTtc;
            }
        }
        this.moTotalTtcRectif = +(this.moTotalTtcRectif).toFixed(3);
        this.quotationRectif.ttcAmount += this.moTotalTtcRectif + this.ingTotalTtcRectif + this.fournTotalTtcRectif + this.piecesTotalTtcRectif;
        this.quotationRectif.ttcAmount = +(this.quotationRectif.ttcAmount).toFixed(3);
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

    changeMo(detailsMoLine: DetailsPieces) {
        detailsMoLine.totalHt = +(detailsMoLine.prixUnit * detailsMoLine.nombreHeures).toFixed(3);
        detailsMoLine.amountDiscount = +(detailsMoLine.totalHt * detailsMoLine.discount / 100).toFixed(3);
        detailsMoLine.amountAfterDiscount = +(detailsMoLine.totalHt - detailsMoLine.amountDiscount).toFixed(3);
        detailsMoLine.amountVat = +(detailsMoLine.amountAfterDiscount * detailsMoLine.tva / 100).toFixed(3);
        detailsMoLine.totalTtc = +(detailsMoLine.amountAfterDiscount + detailsMoLine.amountVat).toFixed(3);

        this.calculateGlobalMoTtc();
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

    getDevisComplementary(quotationId) {
        this.quotationService.find(quotationId).subscribe((res) => {  // Find devid By ID
            this.quotationComplementary = res;
            console.log("loooll");
            console.log(this.quotationComplementary);
            this.quotationComplementary.confirmationDecisionQuote = false;
            this.loadAllDetailsMoComplementary();
            this.loadAllIngredientComplementary();
            this.loadAllRechangeComplementary();
            this.loadAllFournitureComplementary();
            this.statusComplementary = this.quotationComplementary.statusId; // Get etat de devis selectionné 
            if (this.AvisExpert && this.quotationComplementary.expertiseDate == null) { this.quotationComplementary.expertiseDate = this.dateAsYYYYMMDDHHNNSS(new Date()); }
        })
    }

    loadAllDetailsMoComplementary() {
        this.detailsPiecesService.queryByDevisAndType(this.quotationComplementary.id, TypePiecesDevis.MAIN_OEUVRE, true).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesMOComplementary = subRes.json;
            if (this.AvisExpert) {
                this.detailsPiecesMOComplementary.forEach(detailPiecesMO => {
                    this.observationExpertByTypeIntervention(detailPiecesMO);
                });
            }

            this.calculateGlobalMoTtcComplementary();
            this.getSum4Complementary();
            this.loadAllIngredientComplementary();

        })
    }

    calculateGlobalMoTtcComplementary() {
        this.quotationComplementary.ttcAmount = this.quotationComplementary.stampDuty ? this.quotationComplementary.stampDuty : 0;
        this.moTotalTtcComplementary = 0;
        if (this.detailsPiecesMOComplementary && this.detailsPiecesMOComplementary.length > 0) {
            for (let i = 0; i < this.detailsPiecesMOComplementary.length; i++) {
                this.moTotalTtcComplementary += this.detailsPiecesMOComplementary[i].totalTtc;
            }
        }
        this.moTotalTtcComplementary = +(this.moTotalTtcComplementary).toFixed(3);
        this.quotationComplementary.ttcAmount += this.moTotalTtcComplementary + this.ingTotalTtcComplementary + this.fournTotalTtcComplementary + this.piecesTotalTtcComplementary;
        this.quotationComplementary.ttcAmount = +(this.quotationComplementary.ttcAmount).toFixed(3);
    }

    getSum4Complementary(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMosComplementary.length; i++) {
            if (this.detailsMosComplementary[i].typeInterventionId && this.detailsMosComplementary[i].nombreHeures) {
                sum += this.detailsMosComplementary[i].totalHt;
            }
        }
        return sum;
    }

    getSum4Rectif(): number {
        let sum = 0;
        for (let i = 0; i < this.detailsMosRectif.length; i++) {
            if (this.detailsMosRectif[i].typeInterventionId && this.detailsMosRectif[i].nombreHeures) {
                sum += this.detailsMosRectif[i].totalHt;
            }
        }
        return sum;
    }

    loadAllIngredientComplementary() {
        this.detailsPiecesService.queryByDevisAndType(this.quotationComplementary.id, TypePiecesDevis.INGREDIENT_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesIngredientComplementary = subRes.json;
            this.calculateGlobalIngTtcComplementary();
            this.loadAllRechangeComplementary();


        })
    }

    calculateGlobalIngTtcComplementary() {
        this.quotationComplementary.ttcAmount = this.quotationComplementary.stampDuty ? this.quotationComplementary.stampDuty : 0;
        this.ingTotalTtcComplementary = 0;
        if (this.detailsPiecesIngredientComplementary && this.detailsPiecesIngredientComplementary.length > 0) {
            for (let i = 0; i < this.detailsPiecesIngredientComplementary.length; i++) {
                //if (this.detailsPiecesIngredient[i].modified != true) {
                this.ingTotalTtcComplementary += this.detailsPiecesIngredientComplementary[i].totalTtc;
                //}
            }
        }
        this.ingTotalTtcComplementary = +(this.ingTotalTtcComplementary).toFixed(3);
        this.quotationComplementary.ttcAmount += this.moTotalTtcComplementary + this.ingTotalTtcComplementary + this.fournTotalTtcComplementary + this.piecesTotalTtcComplementary;
        this.quotationComplementary.ttcAmount = +(this.quotationComplementary.ttcAmount).toFixed(3);
    }

    loadAllRechangeComplementary() {
        this.detailsPiecesService.queryByDevisAndType(this.quotationComplementary.id, TypePiecesDevis.MAIN_OEUVRE, false).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesComplementary = subRes.json;
            this.calculateGlobalPiecesTtcComplementary();
            this.loadAllFournitureComplementary();


        })
    }

    calculateGlobalPiecesTtcComplementary() {
        this.quotationComplementary.ttcAmount = this.quotationComplementary.stampDuty ? this.quotationComplementary.stampDuty : 0;
        this.piecesTotalTtcComplementary = 0;
        if (this.detailsPiecesComplementary && this.detailsPiecesComplementary.length > 0) {
            for (let i = 0; i < this.detailsPiecesComplementary.length; i++) {
                //if (this.detailsPieces[i].modified) {
                this.piecesTotalTtcComplementary += this.detailsPiecesComplementary[i].totalTtc;
                //}
            }
        }
        this.piecesTotalTtcComplementary = +(this.piecesTotalTtcComplementary).toFixed(3);
        this.quotationComplementary.ttcAmount += this.moTotalTtcComplementary + this.ingTotalTtcComplementary + this.fournTotalTtcComplementary + this.piecesTotalTtcComplementary;
        this.quotationComplementary.ttcAmount = +(this.quotationComplementary.ttcAmount).toFixed(3);
    }

    loadAllFournitureComplementary() {
        this.detailsPiecesService.queryByDevisAndType(this.quotationComplementary.id, TypePiecesDevis.PIECES_FOURNITURE, false).subscribe((subRes: ResponseWrapper) => {
            this.detailsPiecesFournitureComplementary = subRes.json;
            this.calculateGlobalFournTtcComplementary();
            let object = this.lastDetailsComplementary.concat(this.detailsPiecesMOComplementary, this.detailsPiecesComplementary, this.detailsPiecesFournitureComplementary, this.detailsPiecesIngredientComplementary);
            object.forEach(lastDetail => {
                let obj = Object.assign({}, lastDetail)
                this.lastDetailsPiecesComplementary.push(obj);
            });
            if (this.AvisExpert) {
                this.DecisionExpertByObser(5, null);
            }


        });
    }

    calculateGlobalFournTtcComplementary() {
        this.quotationComplementary.ttcAmount = this.quotationComplementary.stampDuty ? this.quotationComplementary.stampDuty : 0;
        this.fournTotalTtcComplementary = 0;
        if (this.detailsPiecesFournitureComplementary && this.detailsPiecesFournitureComplementary.length > 0) {
            for (let i = 0; i < this.detailsPiecesFournitureComplementary.length; i++) {
                if (this.detailsPiecesFournitureComplementary[i].isModified != true) {
                    this.fournTotalTtcComplementary += this.detailsPiecesFournitureComplementary[i].totalTtc;
                }
            }
        }
        this.fournTotalTtcComplementary = +(this.fournTotalTtcComplementary).toFixed(3);
        this.quotationComplementary.ttcAmount += this.moTotalTtcComplementary + this.ingTotalTtcComplementary + this.fournTotalTtcComplementary + this.piecesTotalTtcComplementary;
        this.quotationComplementary.ttcAmount = +(this.quotationComplementary.ttcAmount).toFixed(3);
    }

    changeMoComplementary(detailsMoLine: DetailsPieces) {
        detailsMoLine.totalHt = +(detailsMoLine.prixUnit * detailsMoLine.nombreHeures).toFixed(3);
        detailsMoLine.amountDiscount = +(detailsMoLine.totalHt * detailsMoLine.discount / 100).toFixed(3);
        detailsMoLine.amountAfterDiscount = +(detailsMoLine.totalHt - detailsMoLine.amountDiscount).toFixed(3);
        detailsMoLine.amountVat = +(detailsMoLine.amountAfterDiscount * detailsMoLine.tva / 100).toFixed(3);
        detailsMoLine.totalTtc = +(detailsMoLine.amountAfterDiscount + detailsMoLine.amountVat).toFixed(3);

        this.calculateGlobalMoTtcComplementary();
    }

    changeFournitureComplementary(fourniture: DetailsPieces) {
        fourniture.totalHt = +(fourniture.prixUnit * fourniture.quantite).toFixed(3);
        fourniture.amountDiscount = +(fourniture.totalHt * fourniture.discount / 100).toFixed(3);
        fourniture.amountAfterDiscount = +(fourniture.totalHt - fourniture.amountDiscount).toFixed(3);
        fourniture.amountVat = +(fourniture.amountAfterDiscount * fourniture.tva / 100).toFixed(3);
        fourniture.totalTtc = +(fourniture.amountAfterDiscount + fourniture.amountVat).toFixed(3);
        this.calculateGlobalFournTtcComplementary();
    }

    changePieceRechangeComplementary(rechange: DetailsPieces) {
        rechange.totalHt = +(rechange.prixUnit * rechange.quantite).toFixed(3);
        rechange.amountDiscount = +(rechange.totalHt * rechange.discount / 100).toFixed(3);
        rechange.amountAfterDiscount = +(rechange.totalHt - rechange.amountDiscount).toFixed(3);
        rechange.amountVat = +(rechange.amountAfterDiscount * rechange.tva / 100).toFixed(3);
        rechange.totalTtc = +(rechange.amountAfterDiscount + rechange.amountVat).toFixed(3);
        this.calculateGlobalPiecesTtcComplementary();
    }

    changeIngredientComplementary(dpIngredient: DetailsPieces) {
        dpIngredient.totalHt = +(dpIngredient.prixUnit * dpIngredient.quantite).toFixed(3);
        dpIngredient.amountDiscount = +(dpIngredient.totalHt * dpIngredient.discount / 100).toFixed(3);
        dpIngredient.amountAfterDiscount = +(dpIngredient.totalHt - dpIngredient.amountDiscount).toFixed(3);
        dpIngredient.amountVat = +(dpIngredient.amountAfterDiscount * dpIngredient.tva / 100).toFixed(3);
        dpIngredient.totalTtc = +(dpIngredient.amountAfterDiscount + dpIngredient.amountVat).toFixed(3);
        this.calculateGlobalIngTtcComplementary();
    }



    /**
     * Initialize contract information
     */
    initContrat(contractId: number, vehiculeId: number) {

        if (contractId !== null && contractId !== undefined) {
            this.contractService.find(contractId).subscribe((contract: ContratAssurance) => {
                this.contratAssurance = contract;
                this.initContratDetails(vehiculeId);
            });
        }


    }

    setDefaultDate(): NgbDateStruct {
        var startDate = new Date();
        let startYear = startDate.getFullYear().toString();
        let startMonth = startDate.getMonth() + 1;
        let startDay = "1";
        return this.ngbDateParserFormatter.parse(startYear + "-" + startMonth.toString() + "-" + startDay);
    }

    initContratDetails(vehiculeId: number) {
        // find vehicle
        if (this.contratAssurance.vehicules && this.contratAssurance.vehicules.length > 0) {
            for (let i = 0; i < this.contratAssurance.vehicules.length; i++) {
                if (this.contratAssurance.vehicules[i].id === vehiculeId) {
                    this.vehiculeContrat = this.contratAssurance.vehicules[i];
                    this.sinister.vehicleId = this.vehiculeContrat.id;
                    this.contratAssurance.marqueLibelle = this.vehiculeContrat.marqueLibelle;
                    this.contratAssurance.modeleLibelle = this.vehiculeContrat.modeleLibelle;
                    this.contratAssurance.datePCirculation = this.vehiculeContrat.datePCirculation;
                    break;
                }
            }
        }
        // find insured
        this.insuredService.find(this.vehiculeContrat.insuredId).subscribe((insured: Assure) => {
            this.insured = insured;
            if (this.insured.company) {
                this.insured.fullName = this.insured.raisonSociale;
            } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
            } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                this.insured.fullName = this.insured.prenom;
            } else {
                this.insured.fullName = this.insured.nom;
            }
            if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                this.delegationService.find(this.insured.delegationId).subscribe((sysVille: Delegation) => {
                    this.governorateService.find(sysVille.governorateId).subscribe((subRes: Governorate) => {
                        this.insured.governorateLabel = subRes.label;
                    });
                });
            }
        });


    }

    onSelectDate(date: NgbDateStruct) {
        if (date != null) {
            this.model = date;
            this.dateString = this.ngbDateParserFormatter.format(date);
        }
    }


    nbreMissionByReparateur(id) {
        this.prestationPECService.getNbreMissionReparator(id).subscribe((res: any) => {
            this.nbmissionreparateur = res.json;
        })
        this.reparateurService.find(id).subscribe((res: Reparateur) => {
            this.capacite = res.capacite;
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

        this.agenceAssuranceService.findAllByPartner(id).subscribe((res: ResponseWrapper) => {
            this.refagences = res.json;
            if (res.json && res.json.length > 0) {
                this.tier.agenceId = res.json[0].id;
            }
            console.log("tetstAgence" + this.refagences.length);
            if (this.refagences.length == 0) {
                this.otherField = true;
                this.tier.optionalAgencyName = 'Autre';
                this.nameAgence = true;
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

    listReparateursByGouvernorat(id) {
        this.reparateurService.findByGouvernorat(id).subscribe((listReparateur: Reparateur[]) => {
            this.reparateurs = listReparateur;
        });
    }


    getImprime() {
        this.prestationPECService.getImprimeAttachments(this.pecId).subscribe((resImprime) => {
            this.imprimesAttachments = resImprime.json;

        });
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


            /*if(this.tiers.length + 1 == this.sinisterPec.vehicleNumber){
                this.showFrmTiers = false;
            }*/

        } else {
            this.tiersLength = true;
            this.showFrmTiers = false;
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



    pointChocObligatoire() {
        if (this.pointChoc.avant) this.PointChocRequired = true;
        else if (this.pointChoc.arriere) this.PointChocRequired = true;
        else if (this.pointChoc.arriereGauche) this.PointChocRequired = true;
        else if (this.pointChoc.lateraleGauche) this.PointChocRequired = true;
        else if (this.pointChoc.lateraleGauchearriere) this.PointChocRequired = true;
        else if (this.pointChoc.lateraleGaucheAvant) this.PointChocRequired = true;
        else if (this.pointChoc.arriereDroite) this.PointChocRequired = true;
        else if (this.pointChoc.lateraledroite) this.PointChocRequired = true;
        else if (this.pointChoc.lateraleDroiteAvant) this.PointChocRequired = true;
        else if (this.pointChoc.lateraleDroiteArriere) this.PointChocRequired = true;
        else if (this.pointChoc.avantGauche) this.PointChocRequired = true;
        else if (this.pointChoc.avantDroite) this.PointChocRequired = true;
        else if (this.pointChoc.retroviseurGauche) this.PointChocRequired = true;
        else if (this.pointChoc.retroviseurDroite) this.PointChocRequired = true;
        else if (this.pointChoc.lunetteArriere) this.PointChocRequired = true;
        else if (this.pointChoc.pareBriseAvant) this.PointChocRequired = true;
        else if (this.pointChoc.triangleArriereGauche) this.PointChocRequired = true;
        else if (this.pointChoc.triangleArriereDroit) this.PointChocRequired = true;
        else if (this.pointChoc.vitreAvantDroit) this.PointChocRequired = true;
        else if (this.pointChoc.vitreAvantGauche) this.PointChocRequired = true;
        else if (this.pointChoc.vitreArriereDroit) this.PointChocRequired = true;
        else if (this.pointChoc.vitreArriereGauche) this.PointChocRequired = true;
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
        //if (value == 1 || value == 2) this.sinisterPec.vehicleNumber = 2;
        if (value === 1 || value === 2) {
            this.sinisterPec.vehicleNumber = 2;
            this.minVinNumber = 2;
            //this.VinNumber = 2 ;
            this.vehNmbrIda = true;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4) {
            //this.sinisterPec.vehicleNumber = 2;
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        } else {
            //this.sinisterPec.vehicleNumber = 1;
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }

    }

    diffCapDcCapR() {
        if (this.contratAssurance.dcCapital !== null && this.contratAssurance.dcCapital !== undefined && this.sinisterPec.remainingCapital !== null && this.sinisterPec.remainingCapital !== undefined) {
            if (this.contratAssurance.dcCapital < this.sinisterPec.remainingCapital) {
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

    deleteObservation(observation: Observation) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer cette observation ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {

                this.observationService.delete(observation.id)
                    .subscribe(data => {

                        this.observationss = this.observationss.filter(u => u !== observation);


                    })
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));


    }

    prepareEditObservation(observation) {
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
        /*if(observation.id){
            this.observationService.update(observation).subscribe((res) => {});
        }*/
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
    trackGouvernoratById(index: number, item: Governorate) {
        return item.id;
    }

    trackGouvernoratRepById(index: number, item: Governorate) {
        return item.id;
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




    history() {
        this.ngbModalRef = this.siniterPecPopupService.openHistoryModalSinisterPec(HistoryReparationComponent as Component, 'sinister pec', this.sinisterPec.id);

    }

    /**
   * Get city list by gouvernorat
   * @param id
   */
    fetchCitiesByGovernorate(id, initFlag) {
        this.delegationService.findByGovernorate(id).subscribe((cities: Delegation[]) => {
            this.cities = cities;
            if (cities && cities.length > 0 && initFlag) {
                this.sinister.locationId = cities[0].id;
            }
        });
    }

    /**
     * Get city list by gouvernorat
     * @param id
     */
    fetchDestCitiesByGovernorate(id, initFlag) {
        this.delegationService.findByGovernorate(id).subscribe((cities: Delegation[]) => {
            this.citiesDest = cities;

        });
    }


}