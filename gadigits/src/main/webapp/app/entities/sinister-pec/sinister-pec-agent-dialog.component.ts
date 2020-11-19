import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { FileSelectDirective, FileDropDirective, FileUploader } from 'ng2-file-upload/ng2-file-upload';
import { Observable, Subscription, Subject } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager, JhiDateUtils } from 'ng-jhipster';
import { Sinister, EtatSinister } from '../sinister/sinister.model';
import { SinisterService } from '../sinister/sinister.service';
import { Delegation, DelegationService } from '../delegation';
import { Governorate, GovernorateService } from '../governorate';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
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
import { DemandSinisterPecService } from "./demand-sinister-pec.service";
import { PieceJointe, PieceJointeService } from "../piece-jointe";
import { DatePipe } from '@angular/common';
import { UserPartnerMode, UserPartnerModeService } from '../user-partner-mode';
import { RefPackService, RefPack, SinisterTypeSetting } from '../ref-pack';
import { Client } from '../client';
import { UserExtraService, UserExtra, PermissionAccess } from '../user-extra';
import { StatusSinister, TypeService, PrestationPecStep } from '../../constants/app.constants';
import { Result } from './result.model';
import { Attachment } from '../attachments';
import { SinisterPec } from './sinister-pec.model';
import { Agency, AgencyService } from '../agency';
import { GaDatatable, Global } from '../../constants/app.constants';
import { User } from '../../shared/user/user.model';
import { UserService } from '../../shared/user/user.service';
import { ConfirmationDialogService } from '../../shared';
import { forEach } from '@angular/router/src/utils/collection';
import { ConventionService } from '../../entities/convention/convention.service';
import * as Stomp from 'stompjs';
import { DomSanitizer } from '@angular/platform-browser';
import { saveAs } from 'file-saver/FileSaver';
import { Convention } from '../convention/convention.model';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import { JhiNotification } from '../notification/notification.model';
import { RefTypeService } from '../ref-type-service';
import { NgForm } from '@angular/forms';
@Component({
    selector: 'jhi-dossier-dialog',
    templateUrl: './sinister-pec-agent-dialog.component.html'
})
export class SinisterPecAgentDialogComponent implements OnInit {
    contratAssurance: ContratAssurance = new ContratAssurance();
    sinister: Sinister = new Sinister();
    sinisterPec = new SinisterPec();
    isSaving: boolean;
    success: boolean;
    isCommentError = false;
    isObsModeEdit = false;
    gov: boolean = true;
    dtOptions: any = {};
    refBar: boolean = false;
    refBarShow: boolean = false;
    nbrMaxTiers: boolean = true;
    otherField: boolean = false;
    nameAgence: boolean = false;
    govRep: boolean = true;
    showAlertTiers: boolean = false;
    isTierModeEdit = false;
    nbrTiersResponsable: number = 0;
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
    consultation: boolean = false;
    vehiculeassures: VehiculeAssure[];
    sysgouvernorats: Governorate[];
    sysGouvernorat: Governorate;
    selectedItem: boolean = true;
    sysGouvernoratRep: Governorate;
    assures: Assure[];
    usersPartnerModes: UserPartnerMode[];
    userExtra: UserExtra = new UserExtra();
    userExtraCnv: UserExtra = new UserExtra();
    usersPartner: UserPartnerMode[];
    userExtraPartner: UserExtra = new UserExtra();
    user: User = new User();
    assure: any;
    assureur: Assure = new Assure();
    currentAccount: any;
    datePCirculation: any;
    debut: any;
    fin: any;
    debut1: any;
    fin1: any;
    edit: any;
    date: any;
    dateD: any;
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
    miseAjour: any;
    userCellule: any;
    prestations: any;
    ville: any;
    companyId: number;
    gouvernorat: any;
    telephone: any;
    reparateur: any;
    newFormTiers: boolean = true;
    searchActive: boolean = false;
    sysvilles: Delegation[];
    governorates: Governorate[];
    contacts: any;
    testDateAccident = true;
    selectedIdCompagnies: number[] = [];
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
    observationss: Observation[] = [];
    observation = new Observation();
    tiers: Tiers[] = [];
    tier = new Tiers();
    labelPiece1: string;
    compagnies: any;
    agences: any;
    sinisterTypeSettings: RefTypeService[] = [];
    sinisterTypeSettingsAvenant: RefTypeService[] = [];
    isvalideDate: any;
    casDeBareme = false;
    minVinNumber;
    VinNumber;
    counter: any;
    isContractLoaded = false;
    nbrVehPattern = "^[1-9]$";
    floatPattern = "^[0-9]*\\.?[0-9]*$";
    piecesJointes: PieceJointe[];
    pieceJointelength: boolean = true;
    vhclNmbrReq: boolean = true;
    BtnSaveValide: boolean = false;
    refBareme = new RefBareme();
    CasBareme = new RefBareme();
    refBid: number;
    imgBareme: string;
    idBaremeLoaded: number;
    codeBaremeLoaded: number;
    descriptionBaremeLoaded: String;
    myDate: any;
    pack: RefPack = new RefPack();
    listModeGestionByPack: any[] = [];
    listModeByConvension: any[] = [];
    listModeByCnvByUser: any[] = [];
    contratAssuranceDuTiers: ContratAssurance = new ContratAssurance();
    tierIsCompagnie: boolean = false;
    tierRaisonSocial: any;
    agenceTierNom: any;
    compagnieTierNom: any;
    debut2: any;
    fin2: any;
    nowNgbDate: any;
    pieceFiles1: File;
    tiersIsAssure: boolean = false;
    tiersIsNotAssure: boolean = false;
    tierContratNumero: String = null;
    client: Client = new Client();
    refagences: Agency[];
    trouveTiers: Tiers = new Tiers();
    ajoutNouvelpieceform = false;
    ajoutNouvelpiece = false;
    pieceAttachment1: Attachment = new Attachment();
    piecePreview1 = false;
    listeTiersByImmatriculation: Tiers[] = [];
    assureCherche: Assure = new Assure();
    exist: boolean = false;
    ajoutContrat: boolean = false;
    agentCompanyRO: boolean = false;
    vehNmbrOblg: boolean = false;
    vehNmbrIda: boolean = false;
    tiersLength: boolean = true;
    showFrmTiers: boolean = false;
    tiersLengthForIda: boolean = true;
    incidentDateUpdate = true;
    isCentreExpertise: boolean = false;
    tierResponsableVerif: boolean = true;
    old: boolean = false;
    nbrTiersRespMin: number = 0;
    nbrTiers: number = 0;
    pieceJointe: PieceJointe = new PieceJointe();
    constatFiles: File;
    carteGriseFiles: File;
    oneTime = false;
    vehiculeContratTiers: VehiculeAssure = new VehiculeAssure();
    acteCessionFiles: File;
    chemin: Result = new Result();
    selectedFiles: FileList;
    constatAttachment: Attachment = new Attachment();
    carteGriseAttachment: Attachment = new Attachment();
    acteCessionAttachment: Attachment = new Attachment();
    constatPreview = false;
    updatePieceJointe1 = false;
    carteGrisePreview = false;
    acteCessionPreview = false;
    containt: boolean = false;
    tierRespMin: boolean = true;
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    attachmentList: Attachment[];
    piecesAttachments: Attachment[] = [];
    modeChoisi: RefModeGestion = new RefModeGestion;
    piecesFiles: File[] = [];
    partner: Partner;
    textPattern = Global.textPattern;
    ws: any;
    agentCompanyAcces: boolean = false;
    private readonly imageType: string = 'data:image/PNG;base64,';
    private readonly imageTypeJpeg: string = 'data:image/jpeg;base64,';
    showConstatAttachment: boolean = true;
    extensionImageOriginal: string;
    showCarteGriseAttachment: boolean = true;
    showActeDeCessionAttachment: boolean = true;
    testConvention = false;
    immatriculationForAnotherAgent: boolean = false;
    agentAccess: boolean = false;
    companyAccess: boolean = false;
    userWithRelation: boolean = false;
    userWithAnyRelation: boolean = false;
    testService: boolean = false;
    convention: Convention = new Convention();
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    listNotifUser: NotifAlertUser[] = [];
    listUserPartnerModesForNotifs: UserPartnerMode[] = [];
    userExNotif: UserExtra[] = [];
    oneClickForButton: boolean = true;
    oneClickForButton1: boolean = true;
    oneClickForButton2: boolean = true;
    oneClickForButton3: boolean = true;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    showtraitDemendPecAlert = false;
    blockAgentForAddPrest = false;
    contratSuspendu = false;
    sowContratSuspendu = false;
    extensionImage: string;
    nomImage: string;
    updateConstat = false;
    updateCarteGrise = false;
    hiddenEnvoyerButton = false;
    updateActeDeCession = false;
    showConstat = false;
    showCarteGrise = false;
    showActeDeCession = false;
    compagniesListes = [];
    compagnieName: string;
    showCompanyImmat = false;
    showResponsible = true;

    constructor(
        private alertService: JhiAlertService,
        private sinisterService: SinisterService,
        private sysGouvernoratService: GovernorateService,
        private sysVilleService: DelegationService,
        private vehiculeAssureService: VehiculeAssureService,
        private contratAssuranceService: ContratAssuranceService,
        private assureService: AssureService,
        private prestationPECService: SinisterPecService,
        private tiersService: TiersService,
        private clientService: ClientService,
        private refModeGestionService: RefModeGestionService,
        public principal: Principal,
        private route: ActivatedRoute,
        private router: Router,
        private delegationService: DelegationService,
        private eventManager: JhiEventManager,
        private refBaremeService: RefBaremeService,
        private agenceAssuranceService: AgencyService,
        private refCompagnieService: PartnerService,
        private confirmationDialogService: ConfirmationDialogService,
        private observationService: ObservationService,
        private demandePecService: DemandSinisterPecService,
        private pieceJointeService: PieceJointeService,
        private refBaremePopupDetailService: RefBaremePopupDetailService,
        private refPackService: RefPackService,
        private dateUtils: JhiDateUtils,
        private expertService: ExpertService,
        private userExtraService: UserExtraService,
        private userService: UserService,
        private conventionService: ConventionService,
        private sanitizer: DomSanitizer,
        private notificationAlerteService: NotifAlertUserService,
        private userPartnerModeService: UserPartnerModeService,

    ) {

    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.isSaving = true;
        //this.refBar = false;
        this.refBarShow = false;
        this.casDeBareme = false; // TODO : verifiy
        this.notification = new JhiNotification();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log("testUser" + this.currentAccount.id);
            this.userExtraService.findFunctionnalityEntityByUser(85, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtra = usr;
                    if (this.userExtra.profileId == 23 || this.userExtra.profileId == 24) {
                        this.agenceAssuranceService.find(this.userExtra.personId).subscribe((ag: Agency) => {
                            if (ag.partnerId == 5) {
                                this.hiddenEnvoyerButton = true;
                            }
                        });
                    }
                    if (this.userExtra.profileId === 26 && this.userExtra.personId == 5) {
                        this.hiddenEnvoyerButton = true;
                    }
                    if (this.userExtra.profileId === 26 || this.permissionToAccess.canAjoutDemandPecExpertise) {
                        this.isCentreExpertise = true;
                    } else {
                        this.isCentreExpertise = false;
                    }
                });
            });
        });
        this.route.params.subscribe((params) => {
            if (params['immatriculationPec']) {
                this.sinister.vehicleRegistration = params['immatriculationPec'];
            }
        });
        const now = new Date();
        this.nowNgbDate = {
            year: now.getFullYear(),
            month: now.getMonth() + 1,
            day: now.getDate()
        };

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
        this.refCompagnieService.findAllCompaniesWithoutUser().subscribe((res: ResponseWrapper) => { this.compagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.agenceAssuranceService.findAllAgentAssurance().subscribe((res: ResponseWrapper) => {
            this.refagences = res.json;
        });
        // Get cas de bareme list
        this.refBaremeService.getBaremesWithoutPagination().subscribe((res) => { this.baremes = res.json; });
        this.tier.deleted = false;
        this.refBaremePopupDetailService.currentmessage.subscribe((idresu) => {
            this.refBid = idresu;
            if (this.oneTime == true) {
                this.LoadBareme(this.refBid);
            }
            this.oneTime = true;
        });
        this.myDate = this.dateAsYYYYMMDDHHNNSS(new Date());
        this.initializeDemand();

        this.route.params.subscribe((params) => {
            if (params['id']) {

            } else {
                this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSS(new Date());
                this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSS(new Date());
            }
        });
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
        this.updateConstat = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.constatAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            this.constatAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.constatFiles = fileInput.target.files[0];
        this.constatPreview = true;
    }

    getImageB(refB: RefBareme) {
        if (refB.attachmentName == 'png' || refB.attachmentName == 'PNG') {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageType + refB.attachment64);
        } else {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageTypeJpeg + refB.attachment64);
        }
    }

    onCarteGrisFileChange(fileInput: any) {
        this.updateCarteGrise = true;
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
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
                    //this.refBar = true;
                    this.refBarShow = true;
                }
            });
        }
    }

    receiveRefBareme(rB: RefBareme) {
        this.refBareme = rB;
    }

    checkDateSurvenance() {
        if (this.incidentDateUpdate == true) {

            this.blockAgentForAddPrest = false;
            let vehicId = this.sinister.vehicleId;
            let Immm = this.sinister.vehicleRegistration;
            let contrId = this.sinister.contractId;
            let inciDate = this.sinister.incidentDate;
            this.sinister = new Sinister();

            this.sinister.contractId = contrId;
            this.sinister.vehicleRegistration = Immm;
            this.sinister.vehicleId = vehicId;
            this.sinister.incidentDate = inciDate;

            if (this.sinister.vehicleRegistration !== null && this.sinister.vehicleRegistration !== undefined &&
                this.sinister.incidentDate !== null && this.sinister.incidentDate !== undefined) {
                this.sinisterService.isSinisterDuplicated(this.sinister).subscribe((sinister: Sinister) => {
                    if (sinister.id !== null && sinister.id !== undefined) {
                        if (sinister.sinisterPec) {
                            this.blockAgentForAddPrest = true;
                            this.alertService.error('auxiliumApp.sinisterPec.home.blockAgentForAddPrest');
                        } else {
                            this.blockAgentForAddPrest = false;
                            this.sinister = sinister;
                            if (this.sinister.incidentDate) {
                                const date = new Date(this.sinister.incidentDate);
                                this.sinister.incidentDate = {
                                    year: date.getFullYear(),
                                    month: date.getMonth() + 1,
                                    day: date.getDate()
                                };
                            }
                        }
                    }

                    const today = new Date();
                    const date = new Date(this.sinister.incidentDate.year, this.sinister.incidentDate.month - 1, this.sinister.incidentDate.day);

                    if (date > today) {
                        // TODO : translate message
                        alert("La date d'accident ne doit pas être une date future");
                        this.testDateAccident = false;
                    } else {
                        this.testDateAccident = true;
                    }

                    if (date < new Date(this.contratAssurance.debutValidite) || date > new Date(this.contratAssurance.finValidite)) {
                        this.testDateAccident = false;
                        //this.alertService.error('auxiliumApp.sinisterPec.dateNonCouvert');
                        alert("La date de l'accident doit être comprise entre les dates des validités du contrat d'assurance ");
                        this.sinister.incidentDate = {};
                        /*this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                        this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                        this.sinisterPec.oldStep = 1
                        this.sinisterPec.stepId = 33;
                        this.sinisterPec.fromDemandeOuverture = true;
                        this.sinisterPec.reasonRefusedId = 94;
                        this.sinister.sinisterPec = this.sinisterPec;
                        this.sinister.statusId = StatusSinister.NOTELIGIBLE;
                        this.saveSinister("NoCouvert");*/

                    }
                    if (this.sinister.vehicleRegistration !== null) {

                        this.vehicule.datePCirculation = this.principal.parseDateWithSeconde(this.vehicule.datePCirculation);
                        if (date < new Date(this.vehicule.datePCirculation)) {
                            alert("La date d'accident ne doit pas être une date antérieure par rapport à la date de première mise en circulation de la véhicule de l’assuré");
                            this.testDateAccident = false;
                        }

                        for (let i = 0; i < this.sinisterTypeSettingsAvenant.length; i++) {
                            if (this.sinisterTypeSettingsAvenant[i].id == 11) {
                                this.testService = true;
                                break;
                            }
                        }

                        if (this.sinisterTypeSettingsAvenant.length == 0) {
                            for (let i = 0; i < this.sinisterTypeSettings.length; i++) {
                                if (this.sinisterTypeSettings[i].id == 11) {
                                    this.testService = true;
                                    break;
                                }
                            }
                        }

                        if (this.testService == false) {
                            alert("Type de service non conventionné ");
                            this.sinister.incidentDate = {};
                            /*this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                            this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                            //this.sinisterPec.decision = 'REFUSED';
                            this.sinisterPec.oldStep = 1
                            this.sinisterPec.stepId = 33;
                            this.sinisterPec.reasonRefusedId = 96;
                            this.sinisterPec.fromDemandeOuverture = true;
                            this.sinister.sinisterPec = this.sinisterPec;
                            this.sinister.statusId = StatusSinister.NOTELIGIBLE;
                            this.saveSinister("nonConventionne");*/
                        }
                    }
                });
            }
        }
    }

    /**
     * Initialize dossier information
     */
    initializeDemand() {
        console.log('____________________________________________________________________________________________________________________________________________1');
        this.route.params.subscribe((params) => {
            if (params['id']) {
                const pecId = params['id'];
                // Get pec prestation
                this.prestationPECService.findPrestationPec(pecId).subscribe((pecRes: SinisterPec) => {
                    this.sinisterPec = pecRes;
                    if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                        this.getPhotoPlusPec();
                        this.showActeDeCession = true;
                        this.incidentDateUpdate = false;
                        this.showCarteGrise = true;
                        this.showConstat = true;
                        this.showActeDeCessionAttachment = false;
                        this.showCarteGriseAttachment = false;
                        this.showConstatAttachment = false;
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

                        this.changeModeGestion(this.sinisterPec.modeId);
                        this.verifTiers();
                        this.sinisterService.find(this.sinisterPec.sinisterId).subscribe((sinister: Sinister) => {
                            this.sinister = sinister;
                            //this.sinister.vehicleRegistration = dossierRes.vehicleRegistration;
                            // Treatment of folder dates
                            const date1 = new Date(this.sinisterPec.declarationDate);
                            this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSS(date1);
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
                            this.initContrat();
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
                        if (this.sinisterPec.baremeId) {
                            this.LoadBareme(this.sinisterPec.baremeId);
                        }
                        this.observationService.findByPrestation(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                            this.observationss = subRes.json;
                            if (this.observationss.length == 0) {
                                this.observationss = [];
                            }
                        });
                    } else {
                        this.router.navigate(["/accessdenied"]);
                    }
                });
            } else if (params['idPec']) {
                this.consultation = true;
                const pecId = params['idPec'];
                // Get pec prestation
                this.prestationPECService.findPrestationPec(pecId).subscribe((pecRes: SinisterPec) => {
                    this.sinisterPec = pecRes;
                    if (this.sinisterPec.id !== null && this.sinisterPec.id !== undefined) {
                        this.getPhotoPlusPec();
                        this.showActeDeCession = true;
                        this.showCarteGrise = true;
                        this.showConstat = true;
                        this.showActeDeCessionAttachment = false;
                        this.showCarteGriseAttachment = false;
                        this.showConstatAttachment = false;
                        this.incidentDateUpdate = false;
                        this.principal.identity().then((account) => {
                            this.currentAccount = account;
                            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                this.userExtra = usr;
                                if (this.userExtra.profileId === 26) {
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

                        this.changeModeGestion(this.sinisterPec.modeId);
                        this.verifTiers();
                        this.sinisterService.find(this.sinisterPec.sinisterId).subscribe((sinister: Sinister) => {
                            this.sinister = sinister;
                            const date1 = new Date(this.sinisterPec.declarationDate);
                            this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSS(date1);
                            // Treatment of folder dates
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
                            this.initContrat();
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

                        if (this.sinisterPec.baremeId) {
                            this.LoadBareme(this.sinisterPec.baremeId);
                        }
                        this.observationService.findByPrestation(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
                            this.observationss = subRes.json;
                            if (this.observationss.length == 0) {
                                this.observationss = [];
                            }
                        });
                    } else {
                        this.router.navigate(["/accessdenied"]);
                    }
                });
            } else if (params['immatriculationPec']) {
                this.sinister = new Sinister();
                this.sinisterPec = new SinisterPec();
                this.sinister.vehicleRegistration = params['immatriculationPec'];
                //this.initContrat();
                this.searchActive = true;
                this.verifTiers();
            } else {
                this.sinisterPec = new SinisterPec();
                this.sinister = new Sinister();
                this.initContrat();
                this.verifTiers();
            }
        });
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

    downloadPieceFile1() {
        if (this.pieceFiles1) {
            saveAs(this.pieceFiles1);
        }
    }

    getPhotoPlusPec() {
        this.prestationPECService.getPlusPecAttachments(this.sinisterPec.id).subscribe((resPlus) => {
            this.piecesAttachments = resPlus.json;

        });
    }

    savePhotoPlusPec() {
        this.piecesAttachments.forEach(pieceAttFile => {
            pieceAttFile.creationDate = null;
            if (pieceAttFile.pieceFile !== null && this.updatePieceJointe1 == true && (pieceAttFile.id == null || pieceAttFile.id == undefined)) {
                this.prestationPECService.saveAttachmentsPiecePhotoPlus(this.sinisterPec.id, pieceAttFile.pieceFile, pieceAttFile.label, pieceAttFile.name).subscribe((res: Attachment) => {
                });
            }
        });

    }

    /**
     * traiter prestation pec
     */
    Traiter() {
        this.oneClickForButton3 = false;
        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                console.log("testUser" + this.currentAccount.id);
                const date1 = new Date(this.sinisterPec.declarationDate);
                this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(date1);
                this.sinister.sinisterPec = this.sinisterPec;
                this.sinister.sinisterPec.assignedToId = this.currentAccount.id;
                this.sinister.sinisterPec.stepId = PrestationPecStep.BEING_PROCESSED;
                this.prestationPECService.findPrestationPec(this.sinisterPec.id).subscribe((sinisterPec: SinisterPec) => {
                    if (sinisterPec.assignedToId !== null && sinisterPec.assignedToId !== undefined) {
                        this.showtraitDemendPecAlert = true;
                    } else {
                        this.showtraitDemendPecAlert = false;
                        this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;
                            this.sinisterPec = this.sinister.sinisterPec;
                            this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                            this.sendNotifInformeAgentTraitDemande('traiterDemandePec');
                            this.router.navigate(['/sinister-pec-reprise-in-progress/' + this.sinisterPec.id]);
                        });
                    }
                });
            });
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

    /**
     * Initialize contract information
     */
    initContrat() {
        this.contratAssurance = new ContratAssurance();
        if (this.sinister.vehicleId) { // Url opened from new contract
            this.vehiculeAssureService.find(this.sinister.vehicleId).subscribe((vehicule: VehiculeAssure) => {
                this.vehicule = vehicule;
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
                this.sinister.vehicleId = this.vehicule.id;
                if (this.vehicule.datePCirculation) {
                    this.datePCirculation = this.principal.parseDateJour(this.vehicule.datePCirculation);
                }
                if (this.vehicule.packId) {
                    this.principal.identity().then((account) => {
                        this.currentAccount = account;
                        this.refPackService.find(this.vehicule.packId).subscribe((res: RefPack) => {
                            this.pack = res;
                            this.listModeGestionByPack = this.pack.modeGestions;
                            if (this.pack.conventionId != null) {
                                this.conventionService.find(this.pack.conventionId).subscribe((convention: Convention) => {
                                    this.convention = convention;
                                    if (this.convention.conventionAmendments.length > 0) {
                                        this.convention.conventionAmendments.forEach(convensionAmendment => {
                                            if (convensionAmendment.oldRefPackId == this.pack.id) {
                                                const dateToday = (new Date()).getTime();
                                                const dateEffet = (new Date(convensionAmendment.startDate.toString())).getTime();
                                                const dateFin = (new Date(convensionAmendment.endDate.toString())).getTime();
                                                if (dateToday >= dateEffet && dateToday <= dateFin) {
                                                    if (!this.testConvention) {
                                                        this.testConvention = true;
                                                        this.listModeGestionByPack = [];
                                                    }
                                                    convensionAmendment.refPack.modeGestions.forEach(modeGestion => {
                                                        this.listModeGestionByPack.push(modeGestion);
                                                    });
                                                }
                                            }
                                        });
                                    }
                                    this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                        this.userExtraCnv = usr;
                                        this.usersPartnerModes = this.userExtraCnv.userPartnerModes;
                                        if (this.usersPartnerModes.length > 0) {
                                            this.usersPartnerModes.forEach(usrPrtnMd => {
                                                this.listModeGestionByPack.forEach(mdGstBCnv => {
                                                    if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.vehicule.partnerId) {

                                                        this.listModeByCnvByUser.push(mdGstBCnv);
                                                        var cache = {};
                                                        this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                            return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                                        });

                                                    }
                                                });
                                            });
                                        } else {
                                            this.listModeByCnvByUser = this.listModeGestionByPack;
                                            var cache = {};
                                            this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                            });
                                        }
                                    });

                                });
                            } else {
                                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                    this.userExtraCnv = usr;
                                    this.usersPartnerModes = this.userExtraCnv.userPartnerModes;
                                    if (this.usersPartnerModes.length > 0) {
                                        this.usersPartnerModes.forEach(usrPrtnMd => {
                                            this.listModeGestionByPack.forEach(mdGstBCnv => {
                                                if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.vehicule.partnerId) {

                                                    this.listModeByCnvByUser.push(mdGstBCnv);
                                                    var cache = {};
                                                    this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                        return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                                    });

                                                }
                                            });
                                        });
                                    } else {
                                        this.listModeByCnvByUser = this.listModeGestionByPack;
                                        var cache = {};
                                        this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                            return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                        });
                                    }
                                });
                            }
                        });
                    });
                }
            });
            this.isContractLoaded = false;
            this.contratAssuranceService.find(this.sinister.contractId).subscribe((contractRes: ContratAssurance) => {
                this.contratAssurance = contractRes;
                if (this.contratAssurance) {
                    this.isContractLoaded = true;

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
                }
            }, (subRes: ResponseWrapper) => this.onError(subRes.json));
        }
    }
    loadAllAttachments() {
        this.prestationPECService.getAttachments(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
            this.attachmentList = subRes.json;
            this.attachmentList.forEach(att => {
                switch (att.label) {
                    case this.labelConstat: this.constatAttachment = att;
                        break;
                    case this.labelCarteGrise: this.carteGriseAttachment = att;
                        break;
                    case this.labelActeCession: this.acteCessionAttachment = att;
                        break;
                    default: console.log("this attachment is different: " + att);
                }
            });

        });


    }

    getImage(image64: string) {
        return this.sanitizer.bypassSecurityTrustUrl(this.imageType + image64);
    }

    ajoutNouvelpieceJointe() {
        this.ajoutNouvelpieceform = true;
        this.ajoutNouvelpiece = false;
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
                    this.prestationPECService.deleteAttachmentsImprimes(attachment.id).subscribe((resAttR) => {
                        this.piecesAttachments.forEach((item, index) => {
                            if (item === attachment) this.piecesAttachments.splice(index, 1);
                        });
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    /**
     * Search contrat by Immatricule
     * @param query : vehicule vin
     */
    search(compagnyName, immatriculationVehicle) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtraPartner = usr;
                this.usersPartner = this.userExtraPartner.userPartnerModes;
                this.contratSuspendu = false;
                this.sowContratSuspendu = false;
                //this.refBar = false;
                this.refBarShow = false;
                this.immatriculationForAnotherAgent = false;
                this.CasBareme = new RefBareme();
                this.listModeGestionByPack = [];
                this.listModeByCnvByUser = [];
                this.usersPartnerModes = [];
                this.isContractLoaded = false;
                //this.sinisterPec = new SinisterPec();
                // Search contract by vin

                this.vehiculeAssureService.findVehiculeByCompagnyNameAndImmatriculation(compagnyName, immatriculationVehicle).subscribe((vehicule: VehiculeAssure) => {
                    this.vehicule = vehicule;
                    this.sinister.vehicleRegistration = this.vehicule.immatriculationVehicule;
                    if (this.vehicule.contratId !== null && this.vehicule.contratId !== undefined) {
                        this.contratAssuranceService.find(this.vehicule.contratId).subscribe((contratAssurancee: ContratAssurance) => {
                            this.contratAssurance = contratAssurancee;
                            if (this.contratAssurance) {

                                console.log("contratAssuranceID " + this.contratAssurance.id);

                                this.verifTiers();

                                if (this.userExtraPartner.profileId == 24) {
                                    if (this.userExtraPartner.personId == this.contratAssurance.agenceId) {
                                        this.agentAccess = true;
                                    } else {
                                        this.agentAccess = false;
                                    }
                                }

                                if (this.userExtraPartner.profileId == 25) {
                                    if (this.userExtraPartner.personId == this.contratAssurance.clientId) {
                                        this.companyAccess = true;
                                    } else {
                                        this.companyAccess = false;
                                    }
                                }

                                if (this.userExtraPartner.profileId !== 24 && this.userExtraPartner.profileId !== 25) {
                                    if (this.userExtraPartner.userPartnerModes.length > 0) {
                                        for (let i = 0; i < this.usersPartner.length; i++) {
                                            if (this.usersPartner[i].partnerId == this.contratAssurance.clientId) {
                                                this.userWithRelation = true;
                                                break;
                                            } else {
                                                this.userWithRelation = false;
                                            }
                                        };
                                    } else {
                                        this.userWithAnyRelation = true;
                                    }
                                }

                                if (this.contratAssurance.statusId == 5) {
                                    this.contratSuspendu = true;
                                } else {
                                    this.contratSuspendu = false;
                                }
                                if (this.agentAccess || this.companyAccess || this.userWithRelation || this.userWithAnyRelation) {
                                    this.agentCompanyAcces = true;
                                } else {
                                    this.agentCompanyAcces = false;
                                }

                                if (this.agentCompanyAcces && !this.contratSuspendu) {
                                    this.sinister.contractId = this.contratAssurance.id;

                                    this.sinister.vehicleId = this.vehicule.id;
                                    if (vehicule.datePCirculation) {
                                        this.datePCirculation = this.principal.parseDateJour(vehicule.datePCirculation);
                                    }
                                    if (this.vehicule.packId) {
                                        this.refPackService.find(this.vehicule.packId).subscribe((res: RefPack) => {
                                            this.pack = res;
                                            this.listModeGestionByPack = this.pack.modeGestions;
                                            this.sinisterTypeSettings = this.pack.serviceTypes;
                                            if (this.pack.conventionId != null) {
                                                this.conventionService.find(this.pack.conventionId).subscribe((convention: Convention) => {
                                                    this.convention = convention;
                                                    if (this.convention.conventionAmendments.length > 0) {
                                                        this.convention.conventionAmendments.forEach(convensionAmendment => {
                                                            if (convensionAmendment.oldRefPackId == this.pack.id) {
                                                                const dateToday = (new Date()).getTime();
                                                                const dateEffet = (new Date(convensionAmendment.startDate.toString())).getTime();
                                                                const dateFin = (new Date(convensionAmendment.endDate.toString())).getTime();
                                                                if (dateToday >= dateEffet && dateToday <= dateFin) {
                                                                    if (!this.testConvention) {
                                                                        this.testConvention = true;
                                                                        this.listModeGestionByPack = [];
                                                                    }
                                                                    convensionAmendment.refPack.modeGestions.forEach(modeGestion => {
                                                                        this.listModeGestionByPack.push(modeGestion);
                                                                    });
                                                                    convensionAmendment.refPack.serviceTypes.forEach(typeSett => {
                                                                        this.sinisterTypeSettingsAvenant.push(typeSett);
                                                                    });
                                                                }
                                                            }
                                                        });
                                                    }
                                                    this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                                        this.userExtraCnv = usr;
                                                        this.usersPartnerModes = this.userExtraCnv.userPartnerModes;
                                                        if (this.usersPartnerModes.length > 0) {
                                                            this.usersPartnerModes.forEach(usrPrtnMd => {
                                                                this.listModeGestionByPack.forEach(mdGstBCnv => {
                                                                    if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.contratAssurance.clientId) {

                                                                        this.listModeByCnvByUser.push(mdGstBCnv);
                                                                        var cache = {};
                                                                        this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                                            return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                                                        })

                                                                    }
                                                                });
                                                            });
                                                        } else {
                                                            var cache = {};
                                                            this.listModeByCnvByUser = this.listModeGestionByPack;
                                                            this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                                return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                                            })
                                                        }
                                                    });

                                                });
                                            } else {
                                                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                                                    this.userExtraCnv = usr;
                                                    this.usersPartnerModes = this.userExtraCnv.userPartnerModes;
                                                    if (this.usersPartnerModes.length > 0) {
                                                        this.usersPartnerModes.forEach(usrPrtnMd => {
                                                            this.listModeGestionByPack.forEach(mdGstBCnv => {
                                                                if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.contratAssurance.clientId) {

                                                                    this.listModeByCnvByUser.push(mdGstBCnv);
                                                                    var cache = {};
                                                                    this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                                        return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                                                    })

                                                                }
                                                            });
                                                        });
                                                    } else {
                                                        this.listModeByCnvByUser = this.listModeGestionByPack;
                                                        var cache = {};
                                                        this.listModeByCnvByUser = this.listModeByCnvByUser.filter(function (elem) {
                                                            return cache[elem.id] ? 0 : cache[elem.id] = 1;
                                                        })
                                                    }
                                                });
                                            }
                                        });
                                    }

                                    this.isContractLoaded = false;
                                    if (this.contratAssurance) {
                                        this.ajoutContrat = false;
                                        this.isContractLoaded = true;
                                        this.assureService.find(this.vehicule.insuredId).subscribe((assureRes: Assure) => {
                                            this.assureur = assureRes;
                                            this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                            this.telephone = assureRes.premTelephone;
                                            console.log('thisskara' + assureRes.adresse);

                                            this.contratAssurance.adressePhysique = assureRes.adresse;
                                            if (assureRes.delegationId) {
                                                this.sysVilleService.find(assureRes.delegationId).subscribe((resVille: Delegation) => {
                                                    this.ville = resVille;
                                                    this.contratAssurance.villeLibelle = resVille.label;

                                                    this.sysGouvernoratService.find(resVille.governorateId).subscribe((resGouv: Governorate) => {
                                                        this.gouvernorat = resGouv.label;
                                                    });
                                                })
                                            }
                                        });



                                        this.debut1 = this.principal.parseDateJour(this.contratAssurance.debutValidite);
                                        this.fin1 = this.principal.parseDateJour(this.contratAssurance.finValidite);

                                        console.log("testCompagny" + contratAssurancee.agenceId);
                                        this.agenceAssuranceService.find(this.contratAssurance.agenceId).subscribe((agenceRes: Agency) => {
                                            this.agence = agenceRes.name;
                                            if (agenceRes.partnerId) {
                                                this.refCompagnieService.find(agenceRes.partnerId).subscribe((compagnieRes: Partner) => {
                                                    this.compagnie = compagnieRes.companyName;
                                                });
                                            }
                                        });
                                        if (this.contratAssurance.clientId) {
                                            // liste modes de gestion by client
                                            this.refModeGestionService.findModesGestionByClient(this.contratAssurance.clientId).subscribe((res: ResponseWrapper) => {
                                                this.modesDeGestion = res.json;
                                                //this.listGarantiesForModeGestion = [];
                                            }, (res: ResponseWrapper) => this.onError(res.json));
                                        }

                                    } else {
                                        this.ajoutContrat = true;
                                    }
                                } else {


                                    if (this.contratSuspendu) {
                                        this.sowContratSuspendu = true;
                                        this.ajoutContrat = false;
                                    }
                                    if (!this.agentCompanyAcces) {
                                        this.immatriculationForAnotherAgent = true;
                                        this.ajoutContrat = false;
                                    }
                                }





                            } else {
                                this.ajoutContrat = true;
                            }
                        });
                    }

                });
            })
        });
    }

    selectTapedVehiculeImmatriculation(immatriculationVehicule: string) {
        this.contratAssurance = new ContratAssurance();
        this.vehicule = new VehiculeAssure();
        this.assure = null;
        this.gouvernorat = null;
        this.compagniesListes = [];
        this.compagnieName = null;
        if (immatriculationVehicule !== null && immatriculationVehicule !== undefined && immatriculationVehicule !== '') {
            this.vehiculeAssureService.findListesVehiculesByImmatriculation(immatriculationVehicule).subscribe((res: ResponseWrapper) => {
                this.compagniesListes = res.json;
                if (this.compagniesListes.length == 0) {
                    this.contratAssurance = null;
                    this.showCompanyImmat = false;
                } else {
                    this.showCompanyImmat = true;
                }
            });
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

    selectVehicule() {
        console.log(this.compagnieName);
        console.log(this.sinister.vehicleRegistration);
        if (this.compagnieName != null && this.compagnieName != undefined && this.compagnieName !== '' && this.sinister.vehicleRegistration !== null && this.sinister.vehicleRegistration !== undefined
            && this.sinister.vehicleRegistration !== '') {
            this.search(this.compagnieName, this.sinister.vehicleRegistration);
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
            console.log("tetstAgence" + this.refagences.length);
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
        /*this.clientService.find(id).subscribe((subRes: Client) => {
            this.client = subRes;
            if (this.client.compagnieId == null) {
                this.refagences = null;
            } else {
                this.refAgenceService.findAgenceByCompagnie(this.client.compagnieId).subscribe((subRes1: RefAgence[]) => {
                    this.refagences = subRes1;
                });
            }
            if (this.refagences.length == 0) { this.otherField = true; }
        });*/
    }
    newfield(nom) {
        if (nom == 'Autre') {
            this.nameAgence = true;
        } else {
            this.nameAgence = false;
        }
    }

    activeRecherche() {
        if (this.sinister.vehicleRegistration !== null && this.sinister.vehicleRegistration !== undefined && this.sinister.vehicleRegistration !== "") {
            this.searchActive = true;
        } else {
            this.searchActive = false;
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
            this.vehiculeContratTiers = new VehiculeAssure();
            this.trouveTiers = new Tiers();
            this.exist = false;
            this.tierIsCompagnie = false;
            this.old = false;
            this.tier.immatriculation = query;
            this.contratAssuranceService.findVehiculeByImmatriculationAndClient(query, compagnyId).subscribe((contratAssurancee) => {
                this.contratAssuranceDuTiers = contratAssurancee;

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
                            this.tier.compagnieId = this.companyId;
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
        console.log("TESTrESPONSABLEtIERS " + this.nbrTiersResponsable);
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
    saveDemandPec() {

        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer le dossier ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.oneClickForButton1 = false;

                this.nbrTiersRespMin = 0;
                this.tiers.forEach(tierR => {
                    if (tierR.responsible) {
                        this.nbrTiersRespMin++;
                    }
                });
                if (this.sinisterPec.vehicleNumber > 1) {
                    if (this.nbrTiersRespMin == 1) {
                        this.tierRespMin = true;
                        const dateSavePec = new Date(this.sinisterPec.declarationDate);
                        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateSavePec);
                        const dateCreationPec = new Date(this.sinisterPec.dateCreation);
                        this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(dateCreationPec);
                        this.sinisterPec.baremeId = this.CasBareme.id;
                        this.sinisterPec.responsabilityRate = false;
                        this.sinister.sinisterPec = this.sinisterPec;
                        this.sinister.sinisterPec.stepId = PrestationPecStep.REQUEST_OPENING;
                        this.sinister.sinisterPec.baremeId = this.CasBareme.id;
                        this.sinister.sinisterPec.generatedLetter = false;
                        this.sinister.sinisterPec.tiers = [];
                        this.sinister.statusId = StatusSinister.INPROGRESS;
                        if (this.sinister.id !== null && this.sinister.id !== undefined) {
                            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                this.sinisterPec = this.sinister.sinisterPec;
                                this.saveAttachments(this.sinister.sinisterPec.id);
                                this.savePhotoPlusPec();
                                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                this.updateVehicule();
                                this.router.navigate(['/demande-pec']);
                                this.alertService.success('auxiliumApp.sinisterPec.updatedDemandePec', null, null);
                            });
                        } else {
                            this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                this.sinisterPec = this.sinister.sinisterPec;
                                this.saveAttachments(this.sinister.sinisterPec.id);
                                this.savePhotoPlusPec();
                                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                this.updateVehicule();
                                this.router.navigate(['/demande-pec']);
                                this.alertService.success('auxiliumApp.sinisterPec.savedDemandePec', null, null);
                            });
                        }
                    } else {
                        this.tierRespMin = false;
                    }
                } else {
                    console.log("test" + this.sinisterPec.immatriculationVehicle);
                    const dateSavePec = new Date(this.sinisterPec.declarationDate);
                    this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateSavePec);
                    const dateCreationPec = new Date(this.sinisterPec.dateCreation);
                    this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(dateCreationPec);
                    this.sinisterPec.baremeId = this.CasBareme.id;
                    this.sinisterPec.responsabilityRate = false;
                    this.sinister.sinisterPec = this.sinisterPec;
                    this.sinister.sinisterPec.stepId = PrestationPecStep.REQUEST_OPENING;
                    this.sinister.sinisterPec.baremeId = this.CasBareme.id;
                    this.sinister.sinisterPec.generatedLetter = false;
                    this.sinister.statusId = StatusSinister.INPROGRESS;
                    if (this.sinister.id !== null && this.sinister.id !== undefined) {
                        this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;
                            this.sinisterPec = this.sinister.sinisterPec;
                            this.saveAttachments(this.sinister.sinisterPec.id);
                            this.savePhotoPlusPec();
                            this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                            this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                            this.updateVehicule();
                            this.router.navigate(['/demande-pec']);
                            this.alertService.success('auxiliumApp.sinisterPec.updatedDemandePec', null, null);
                        });
                    } else {
                        this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;
                            this.sinisterPec = this.sinister.sinisterPec;
                            this.saveAttachments(this.sinister.sinisterPec.id);
                            this.savePhotoPlusPec();
                            this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                            this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                            this.updateVehicule();
                            this.router.navigate(['/demande-pec']);
                            this.alertService.success('auxiliumApp.sinisterPec.savedDemandePec', null, null);
                        });
                    }
                }




            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }
    /**
    * Envoyer sinister
    */
    envoyerDemandPec() {



        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir envoyer le dossier ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.oneClickForButton2 = false;
                this.nbrTiersRespMin = 0;
                this.tiers.forEach(tierR => {
                    if (tierR.responsible) {
                        this.nbrTiersRespMin++;
                    }
                });

                if (this.sinisterPec.vehicleNumber > 1) {
                    if (this.nbrTiersRespMin == 1) {
                        this.tierRespMin = true;
                        const dateEnvoyerPec = new Date(this.sinisterPec.declarationDate);
                        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateEnvoyerPec);
                        const dateCreationPec = new Date(this.sinisterPec.dateCreation);
                        this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(dateCreationPec);
                        this.sinisterPec.baremeId = this.CasBareme.id;
                        this.sinisterPec.fromDemandeOuverture = false;
                        this.sinisterPec.responsabilityRate = false;
                        this.sinister.sinisterPec = this.sinisterPec;
                        this.sinister.sinisterPec.baremeId = this.CasBareme.id;
                        this.sinister.sinisterPec.stepId = PrestationPecStep.REQUEST_OPEN_SEND;
                        this.sinister.sinisterPec.generatedLetter = false;
                        this.sinister.statusId = StatusSinister.INPROGRESS;
                        if (this.sinister.id !== null && this.sinister.id !== undefined) {
                            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                this.sinisterPec = this.sinister.sinisterPec;
                                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                this.updateVehicule();
                                this.saveAttachments(this.sinister.sinisterPec.id);
                                this.savePhotoPlusPec();
                                this.sendNotifEnvoyerDemandePec('notifSendDemandPec');
                                this.router.navigate(['/demande-pec']);
                                this.alertService.success('auxiliumApp.sinisterPec.envoyerDemandePec', null, null);
                            });
                        } else {
                            this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                this.sinisterPec = this.sinister.sinisterPec;
                                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                                this.updateVehicule();
                                this.saveAttachments(this.sinister.sinisterPec.id);
                                this.savePhotoPlusPec();
                                this.sendNotifEnvoyerDemandePec('notifSendDemandPec');
                                this.router.navigate(['/demande-pec']);
                                this.alertService.success('auxiliumApp.sinisterPec.envoyerDemandePec', null, null);
                            });
                        }

                    } else {
                        this.tierRespMin = false;
                    }
                } else {
                    const dateEnvoyerPec = new Date(this.sinisterPec.declarationDate);
                    this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateEnvoyerPec);
                    const dateCreationPec = new Date(this.sinisterPec.dateCreation);
                    this.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(dateCreationPec);
                    this.sinisterPec.baremeId = this.CasBareme.id;
                    this.sinisterPec.responsabilityRate = false;
                    this.sinisterPec.fromDemandeOuverture = false;
                    this.sinister.sinisterPec = this.sinisterPec;
                    this.sinister.sinisterPec.baremeId = this.CasBareme.id;
                    this.sinister.sinisterPec.stepId = PrestationPecStep.REQUEST_OPEN_SEND;
                    this.sinister.sinisterPec.generatedLetter = false;
                    this.sinister.statusId = StatusSinister.INPROGRESS;
                    if (this.sinister.id !== null && this.sinister.id !== undefined) {
                        this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;
                            this.sinisterPec = this.sinister.sinisterPec;
                            this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                            this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                            this.updateVehicule();
                            this.saveAttachments(this.sinister.sinisterPec.id);
                            this.savePhotoPlusPec();
                            this.sendNotifEnvoyerDemandePec('notifSendDemandPec');
                            this.router.navigate(['/demande-pec']);
                            this.alertService.success('auxiliumApp.sinisterPec.envoyerDemandePec', null, null);
                        });
                    } else {
                        this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                            this.sinister = resSinister;
                            this.sinisterPec = this.sinister.sinisterPec;
                            this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                            this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                            this.updateVehicule();
                            this.saveAttachments(this.sinister.sinisterPec.id);
                            this.savePhotoPlusPec();
                            this.sendNotifEnvoyerDemandePec('notifSendDemandPec');
                            this.router.navigate(['/demande-pec']);
                            this.alertService.success('auxiliumApp.sinisterPec.envoyerDemandePec', null, null);
                        });
                    }
                }




            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    sendNotifEnvoyerDemandePec(typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.listUserPartnerModesForNotifs = [];
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    if (usr.userBossId) {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 11;
                        this.notifUser.destination = usr.userBossId;
                        this.notifUser.source = usr.id;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.notification = this.notification;
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                    }
                    this.userPartnerModeService.findUserPartnerModeByPartnerAndAgencyAndModeAndProfile(this.contratAssurance.clientId, this.sinisterPec.modeId).subscribe((subRes: ResponseWrapper) => {
                        this.listUserPartnerModesForNotifs = subRes.json;
                        this.listUserPartnerModesForNotifs.forEach(destNotif => {
                            let notifUser = new NotifAlertUser();
                            this.notification.id = 11;
                            notifUser.destination = destNotif.userExtraId;
                            notifUser.source = usr.id;
                            notifUser.notification = this.notification;
                            notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(notifUser);
                        });
                        this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idUserBoss': usr.userBossId, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId }));

                        });
                    });
                });
            });
        }
    }

    saveSinister(type: String) {

        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.sinisterPec = this.sinister.sinisterPec;
                this.router.navigate(['/demande-pec']);
                if (type == "nonConventionne") {
                    this.alertService.error('auxiliumApp.sinisterPec.home.typeNonConvensionne');
                } else if (type == "NoCouvert") {
                    this.alertService.error('auxiliumApp.sinisterPec.home.NoCouvert');
                }
            });
        } else {
            this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.sinisterPec = this.sinister.sinisterPec;
                this.router.navigate(['/demande-pec']);
                if (type == "nonConventionne") {
                    this.alertService.error('auxiliumApp.sinisterPec.home.typeNonConvensionne');
                } else if (type == "NoCouvert") {
                    this.alertService.error('auxiliumApp.sinisterPec.home.NoCouvert');
                }


            });
        }

    }

    sendNotifInformeAgentTraitDemande(typeNotif) {
        if (this.oneClickForButton) {
            this.oneClickForButton = false;
            this.userExNotif = [];
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotif) => {
                        this.userExNotif = userExNotif.json;
                        this.userExNotif.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 12;
                            this.notifUser.destination = element.userId;
                            this.notifUser.source = usr.id;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.notification = this.notification;
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        if (usr.profileId == 6) {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 12;
                            this.notifUser.source = usr.id;
                            this.notifUser.destination = usr.userBossId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'modeId': this.sinisterPec.modeId, 'idPartner': this.sinisterPec.partnerId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        }
                        this.notificationAlerteService.queryReadNotification(this.sinisterPec.id).subscribe((prestToRead) => {
                            this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'agenceId': this.contratAssurance.agenceId, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'stepPecId': this.sinisterPec.stepId }));

                            });
                        });
                    });
                });
            });
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
                console.log("tierDeleted-----------------------------------------------------------" + tier.deleted);
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

    verifTiersResponsable() {
        if (this.tiers) {
            this.tiers.forEach(tier => {
                if (tier.responsible) {
                    this.nbrTiersResponsable++;
                }
            });
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

    /**
     * Save prestation observations
     * @param prestationId
     * @param observations
     */
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
    /*verifNbTiers() {
        return this.BtnSaveValide = this.tiers.length < this.sinisterPec.vehicleNumber  ? true : false;

    }*/

    verifTiers() {
        this.nbrTiers = 0;
        this.tiers.forEach(element => {
            if (element.deleted == true) {
            } else {
                this.nbrTiers++;
            }
        });
        this.showFrmTiers = false;
        if (this.sinisterPec.vehicleNumber !== null && this.sinisterPec.vehicleNumber !== undefined) {
            if (this.sinisterPec.vehicleNumber > 1) {
                this.tiersLength = ((this.nbrTiers < this.sinisterPec.vehicleNumber) && this.nbrTiers > 0) ? true : false;
                if (!this.CasBareme.id) {
                    //this.refBar = false;
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
                    //this.refBar = true;
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

    changeMode(value) {
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
            this.nbrVehPattern = "^[2-9]$";
            this.minVinNumber = 2;
            //this.VinNumber = 2 ;
            this.vehNmbrIda = true;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.vehNmbrIda = false;
            this.nbrVehPattern = "^[2-9]$";
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


    addForm() {
        this.nbrTiers = 0;
        this.showResponsible = true;

        this.tiers.forEach(element => {
            if (element.deleted == true) {
            } else {
                this.nbrTiers++;
            }
            if (element.responsible) {
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
            //this.observation.date = this.formatEnDate(new Date());
            //this.observationService.create(this.observation).subscribe((res) => {
            //});
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

    prepareEditTier(tier) {

        console.log('_____________________________________ edit Tier _______________________________________________');
        this.showResponsible = true;


        if (tier.responsible) {
            this.showResponsible = true
        } else {
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

    deleteCarteGriseFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showCarteGriseAttachment = true;
                this.carteGrisePreview = false;
                this.carteGriseFiles = null;
                this.carteGriseAttachment.label = null;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteActeDeCessionFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showActeDeCessionAttachment = true;
                this.acteCessionPreview = false;
                this.acteCessionFiles = null;
                this.acteCessionAttachment.label = null;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteConstatFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showConstatAttachment = true;
                this.constatPreview = false;
                this.constatFiles = null;
                this.constatAttachment.label = null;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
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

    verifTiersLngth() {
        if (this.tiers.length == this.sinisterPec.vehicleNumber - 1) {
            return true;
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

    trackSysVilleById(index: number, item: Delegation) {
        return item.id;
    }

    trackCompanyById(index: number, item: Partner) {
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
