import { Sinister } from './sinister.model';
import { Camion } from './../camion/camion.model';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefTarifService } from './../ref-tarif/ref-tarif.service';
import { PointChoc, HistoryPopupDetailsPec } from './../sinister-pec/';
import { SinisterPrestationTugComponent } from './sinister-prestation-tug.component';
import { SinisterPrestationViewComponent } from './sinister-prestation-view.component';
import { SiniterPrestationTugPopupService } from './sinister-prestation-popup.service';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { Component, OnInit, ViewChild, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { Agency, AgencyService } from '../agency';
import { Partner, PartnerService } from '../partner';
import { RefBareme, RefBaremeService, RefBaremePopupDetailService } from '../ref-bareme';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';
import { RefTypeServiceService } from '../ref-type-service';
import { ConfirmationDialogService, ResponseWrapper, Principal } from '../../shared';
import { Subscription, Subject } from 'rxjs/Rx';
import { StatusSinister, TypeService } from '../../constants/app.constants';
import { SinisterPrestation } from './sinister-prestation.model';
import { TugPricing } from '../ref-tarif';
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { SinisterService } from './sinister.service';
import { DateUtils } from '../../utils/date-utils';
import { RefPackService, RefPack } from '../ref-pack';
import { TiersService, Tiers } from '../tiers';
import { SinisterPec, SinisterPecService, } from '../sinister-pec';
import { DecisionPec, PrestationPecStep, ApprovPec } from '../../constants/app.constants';
import { Attachment } from '../attachments';
import { ExpertService, Expert } from '../expert';
import { GaDatatable } from '../../constants/app.constants';
import { RaisonAssistance, RaisonAssistanceService } from '../raison-assistance';
import { UserExtraService, UserExtra, PermissionAccess } from '../user-extra';
import { UserPartnerMode, UserPartnerModeService } from '../user-partner-mode';
import { ConventionService } from '../../entities/convention/convention.service';
import { RefModeGestionService, RefModeGestion } from '../ref-mode-gestion';
import { RefPositionGa, RefPositionGaService } from '../ref-position-ga';
import { SinisterPecPopupService } from '../sinister-pec';
import { DecisionSinisterPecComponent } from '../sinister-pec/decision-sinister-pec.component';
import { Convention } from '../convention/convention.model';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';
import { NotifAlertUser } from '../notif-alert-user/notif-alert-user.model';
import { NotifAlertUserService } from '../notif-alert-user/notif-alert-user.service';
import * as Stomp from 'stompjs';
import { ObservationService, Observation, TypeObservation } from '../observation';
import { ViewSinisterPrestation } from './view-sinister-prestation.model';
import { ViewSinisterPec } from '../view-sinister-pec';
import { saveAs } from 'file-saver/FileSaver';
import { DataTableDirective } from 'angular-datatables';
import { DomSanitizer } from '@angular/platform-browser';
import { FormControl, NgForm, Validators } from '@angular/forms';
import { NaturePanne } from '../ref-nature-panne/nature-panne.model';
import { NaturePanneService } from '../ref-nature-panne/nature-panne.service';

@Component({
    selector: 'jhi-sinister-dialog',
    templateUrl: './sinister-dialog.component.html'
})
export class SinisterDialogComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    datatableElement: DataTableDirective;

    private ngbModalRef: NgbModalRef;
    dtTrigger: Subject<ViewSinisterPec> = new Subject();

    contratAssurance: ContratAssurance = new ContratAssurance();
    vehiculeContrat: VehiculeAssure = new VehiculeAssure();
    vehiculeContratTiers: VehiculeAssure = new VehiculeAssure();
    insured: Assure = new Assure();
    sinister: Sinister = new Sinister();
    sinisterPrestation: SinisterPrestation = new SinisterPrestation();
    isEditMode = false; // Siniter mode edit
    isSaving = false;
    isServiceTypeSelected = false; // Service type selected
    isPrestationEdit = false; // Prestion form printed
    isVR= false; 
    

    isVRSelected=false;
    isPrestationEditMode = false; // Sinister prestion mode edit
    isServiceGrutage = false;
    constatPreview = false;
    testConvention = false;
    carteGrisePreview = false;
    acteCessionPreview = false;
    PointChocRequired = true;
    governorates: Governorate[];
    delegations: Delegation[];
    delegationsDest: Delegation[];
    serviceTypes: RefTypeService[];
    cancelGrounds: RaisonAssistance[] = [];
    reopenGrounds: RaisonAssistance[];
    eventSubscriber: Subscription;
    testDateAccident = true;
    nbrVehPattern = '^[1-9]*$';
    nbrVehiclePattern = '^[1-9]*$';
    floatPattern = '^[0-9]*\\.?[0-9]*$';
    sinisterSettings: any = {};
    tugArrivalTime: any;
    BtnSaveValide: boolean = false;
    insuredArrivalTime: any;
    declarationDate: any;
    shadowUrl: any;
    nowNgbDate: any;
    myDate: any;
    pack: RefPack = new RefPack();
    listModeGestionByPack: any[] = [];
    sinistersByVehicleRegistration: Sinister[] = [];
    sinisterPrestations: ViewSinisterPrestation[] = [];
    viewsinisterPec: ViewSinisterPec[] = [];
    numberInterventionRemaining = 0;
    numberInterventionDone = 0;
    incidentDate = true;
    // add by Halim
    assure: any;
    CasBareme = new RefBareme();
    casDeBareme = false;
    compagnies: Partner[] = [];
    agences: Agency[] = [];
    baremes: RefBareme[] = [];
    refBid: number;
    sinisterPec: SinisterPec = new SinisterPec();
    gov: boolean = true;
    miseAjour: any;
    creation: any;
    cloture: any;
    imgCarteGrise: any;
    imgConstat: any;
    imgacteDeCession: any;
    isGestionnaire: any;
    acteCessionFiles: File;
    constatFiles: File;
    carteGriseFiles: File;
    garantieDommageVehicule = false;
    garantieDommageCollision = false;
    garantieVolIncendiePartiel = false;
    tiers: Tiers[] = [];
    experts: Expert[] = [];
    tier: Tiers = new Tiers();
    debut2: any;
    fin2: any;
    tiersIsActive = false;
    govRep: boolean = true;
    attachmentList: Attachment[];
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    constatAttachment: Attachment = new Attachment();
    carteGriseAttachment: Attachment = new Attachment();
    acteCessionAttachment: Attachment = new Attachment();
    garantieBrisGlace = false;
    sysvillesRep: Delegation[];
    sysVille: Delegation;
    sysvilles: Delegation[];
    sysGouvernorat: Governorate;
    isTierModeEdit = false;
    newFormTiers: boolean = true;
    old: boolean = false;
    pieceFiles1: File;
    exist: boolean = false;
    tierContratNumero: String = null;
    isObsModeEdit = false;
    contratAssuranceDuTiers: ContratAssurance = new ContratAssurance();
    trouveTiers: Tiers = new Tiers();
    tierIsCompagnie: boolean = false;
    assureCherche: Assure = new Assure();
    pointChoc: PointChoc = new PointChoc();
    tierRaisonSocial: any;
    compagnieTierNom: any;
    agenceTierNom: any;
    dtOptions: any = {};
    testCouverte: boolean = false;
    testService: boolean = false;
    testSendNotifRespPeck = false;
    consultation: false;
    showFrmTiers: boolean = false;
    tiersLength: boolean = true;
    refBar: boolean = false;
    showAlertTiers: boolean = false;
    tierRespMin: boolean = true;
    nbrTiersResponsable: number = 0;
    tierResponsableVerif: boolean = true;
    private readonly imageType: string = 'data:image/PNG;base64,';
    private readonly imageTypeJpeg: string = 'data:image/jpeg;base64,';
    nameAgence: boolean = false;
    otherField: boolean = false;
    agentCompanyRO: boolean = false;
    listeTiersByImmatriculation: Tiers[] = [];
    minVinNumber;
    vehNmbrIda: boolean = false;
    garantieTierceCollision = false;
    garantieTierce = false;
    refagences: Agency[];
    currentAccount: any;
    requiredField: boolean = true;
    userExtraCnv: UserExtra = new UserExtra();
    usersPartnerModes: UserPartnerMode[];
    selectedIdCompagnies: number[] = [];
    listModeByConvension: any[] = [];
    listModeByCnvByUser: any[] = [];
    containt: boolean = false;
    listPositionGa: any[] = [];
    isDecided: boolean = false;
    cmpRf: boolean = false;
    approveWithModifButton = true;
    approveButton = true;
    modeId: any;
    decisionPrest: boolean = false;
    disabledDecisionButton: boolean = true;
    refBarShow: boolean = false;
    searchActive: boolean = false;
    nbrTiersRespMin: number = 0;
    convention: Convention = new Convention();
    prestationDateValide = true;
    notification: RefNotifAlert = new RefNotifAlert();
    notifUser: NotifAlertUser;
    labelPiece1: string;
    piecesFiles: File[] = [];
    piecesAttachments: Attachment[] = [];
    selectedItem: boolean = true;
    listNotifUser: NotifAlertUser[] = [];
    userExNotifAgency: UserExtra[] = [];
    userExNotifPartner: UserExtra[] = [];
    extensionImageOriginal: string;
    ws: any;
    oneClickForButton: boolean = true;
    oneClickForButtonAssistance: boolean = true;
    showIntervNumberAlert = false;
    ajoutNouvelpieceform = false;
    ajoutNouvelpiece = false;
    pieceAttachment1: Attachment = new Attachment();
    piecePreview1 = false;
    showServiceTypeInactiveAlert = false;
    observation = new Observation();
    observations: Observation[] = [];
    observationss: Observation[] = [];
    isCommentError = false;
    nbrTiers: number = 0;
    usersPartner: UserPartnerMode[];
    userExtraPartner: UserExtra = new UserExtra();
    immatriculationForAnotherAgent: boolean = false;
    agentAccess: boolean = false;
    companyAccess: boolean = false;
    userWithRelation: boolean = false;
    userWithAnyRelation: boolean = false;
    agentCompanyAcces: boolean = false;
    ajoutContrat: boolean = false;
    disabledNatureFieled = false;
    showCorrectDateDeclaration = false;
    listUserPartnerModesForNotifs: UserPartnerMode[] = [];
    userExtra: UserExtra = new UserExtra();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    showAlertNoCreateAccess = false;
    canCreatePec = true;
    canCreateService = true;
    oneTime = false;
    extensionImage: string;
    nomImage: string;
    updatePieceJointe1 = false;
    bloqueChargeAddPec = false;
    showAlertBloqueAgent = false;
    contratSuspendu = false;
    sowContratSuspendu = false;
    userExNotifChargeAdministrative: UserExtra[] = [];
    userExNotifResponsableAdministrative: UserExtra[] = [];
    updateConstat = false;
    updateCarteGrise = false;
    companyId: number;
    updateActeDeCession = false;
    sinisterTypeSettings: RefTypeService[] = [];
    sinisterTypeSettingsAvenant: RefTypeService[] = [];
    compagniesListes = [];
    compagnieName: string;
    showCompanyImmat = false;
    showResponsible = true;
    containsService = false;
    showNocontainsService = false;
    maxValueMileage = false;
    minValueMileage = false;
    maxMileage: number = 60000;
    minMileage: number = 0;
    naturePannes: NaturePanne[] = [];


    constructor(
        private alertService: JhiAlertService,
        private sinisterService: SinisterService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private contractService: ContratAssuranceService,
        private insuredService: AssureService,
        private serviceTypeService: RefTypeServiceService,
        public principal: Principal,
        private route: ActivatedRoute,
        private router: Router,
        private dateUtils: JhiDateUtils,
        private siniterPrestationTugPopupService: SiniterPrestationTugPopupService,
        private refTarifService: RefTarifService,
        private raisonAssistanceService: RaisonAssistanceService,
        private owerDateUtils: DateUtils,
        private confirmationDialogService: ConfirmationDialogService,
        private eventManager: JhiEventManager,
        private refPackService: RefPackService,
        private refBaremeService: RefBaremeService,
        private tiersService: TiersService,
        private refCompagnieService: PartnerService,
        private vehiculeAssureService: VehiculeAssureService,
        private refAgenceService: AgencyService,
        private refBaremePopupDetailService: RefBaremePopupDetailService,
        private expertService: ExpertService,
        private sysGouvernoratService: GovernorateService,
        private sysVilleService: DelegationService,
        private prestationPECService: SinisterPecService,
        private userExtraService: UserExtraService,
        private conventionService: ConventionService,
        private refModeGestionService: RefModeGestionService,
        private refPositionGaService: RefPositionGaService,
        private siniterPecPopupService: SinisterPecPopupService,
        private notificationAlerteService: NotifAlertUserService,
        private observationService: ObservationService,
        private userPartnerModeService: UserPartnerModeService,
        private sinisterPecPopupService: SinisterPecPopupService,
        private sanitizer: DomSanitizer,
        private naturePanneService: NaturePanneService

        


    ) {
    }
    mileageMinMax(){
        this.maxValueMileage = false;
        this.minValueMileage = false;
        this.minMileage = 0;
        this.maxMileage = 60000;
        if(this.sinisterPrestation.isU)
        {
            this.sinisterPrestation.mileage=60;
            this.maxMileage=60;
            this.minMileage=0;
            this.calculatePriceHt();
            //this.sinisterPrestation.mileage = new FormControl("", [Validators.max(100), Validators.min(0)])


        }
        if(this.sinisterPrestation.isR)
        {
            this.sinisterPrestation.mileage = null;
            this.minMileage = 60;
            this.maxMileage = 60000;
            this.calculatePriceHt();

        }
    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.dtOptions.scrollX = true;
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.casDeBareme = false; // TODO : verifiy utility
        this.refBar = true;
        this.refBarShow = false;
        this.requiredField = true;
        this.showCorrectDateDeclaration = false;
        // Get company list
        this.refCompagnieService.findAllCompaniesWithoutUser().subscribe((res: ResponseWrapper) => { this.compagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refAgenceService.query().subscribe((res: ResponseWrapper) => { this.agences = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        // Get cas de bareme list
        this.refBaremeService.getBaremesWithoutPagination().subscribe((res) => { this.baremes = res.json; });
        this.refBaremePopupDetailService.currentmessage.subscribe((idresu) => {
            this.refBid = idresu;
            if (this.oneTime == true) {
                this.LoadBareme(this.refBid);
            }
            this.oneTime = true;
        });
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => {
                this.sysvilles = res.json;
                this.sysvillesRep = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));
        // Get expert list
        this.expertService.query().subscribe((res: ResponseWrapper) => {
            this.experts = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.sinisterSettings = {
            heavyWeights: false,
            holidays: false,
            night: false,
            halfPremium: false,
            fourPorteF: false,
            fourPorteK: false,
            isU:false,
            isR:false,
            readOnlyPriceHt: true,
            canCancel: false,
            canReopen: false,
            showCancelGrounds: false,
            showReopenGrounds: false,
            activateServiceType: false,
            activateAddService: false,
            isServiceRemorquage: false
        }
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            /*
            this.userExtraService.findFunctionnalityEntityByUser(85, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
                if(!this.permissionToAccess.canCreate) {
                    this.canCreatePec = false;
                }
            });
            this.userExtraService.findFunctionnalityEntityByUser(98, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
                if(!this.permissionToAccess.canCreate) {
                    this.canCreateService = false;
                }
            });*/

            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtra = usr;
                if (this.userExtra.profileId === 5 || this.userExtra.profileId === 6 || this.userExtra.profileId === 7) {
                    this.canCreateService = false;
                }
                if (this.userExtra.profileId === 11 || this.userExtra.profileId === 12) {
                    this.canCreatePec = false;
                }
            });
        });
        this.myDate = this.dateAsYYYYMMDDHHNNSS(new Date());
        const now = new Date();
        this.nowNgbDate = {
            year: now.getFullYear(),
            month: now.getMonth() + 1,
            day: now.getDate()
        };
        this.route.params.subscribe((params) => {
            if (params['id']) {
                this.isEditMode = true;
                this.showCorrectDateDeclaration = true;
                this.sinisterService.find(params['id']).subscribe((sinister: Sinister) => { // Edition
                    this.sinister = sinister;
                    if (sinister.sinisterPec) {
                        this.bloqueChargeAddPec = true;
                    } else {
                        this.bloqueChargeAddPec = false;
                    }
                    this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                        this.contratAssurance = contract;
                        this.vehiculeAssureService.find(this.sinister.vehicleId).subscribe((vehAss: VehiculeAssure) => {
                            this.vehiculeContrat = vehAss;
                            this.insuredService.find(this.vehiculeContrat.insuredId).subscribe((assureRes: Assure) => {
                                this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                this.contratAssurance.adressePhysique = assureRes.adresse;

                            });
                        });
                    });
                    console.log(sinister.incidentDate);
                    if (this.sinister.incidentDate) {
                        const date = new Date(this.sinister.incidentDate);
                        this.sinister.incidentDate = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    }
                    this.initContrat(this.sinister.contractId, this.sinister.vehicleId);
                }, (error: ResponseWrapper) => this.onError(error.json));
            } else if (params['idcontrat']) {
                this.showCorrectDateDeclaration = true;
                this.sinister.vehicleRegistration = params['immatriculation'];
                //this.findContract(params['immatriculation']);
                //this.initContrat(+params['idcontrat'], this.sinister.vehicleRegistration);
                this.searchActive = true;
            } else if (params['idService']) {
                this.showCorrectDateDeclaration = true;
                this.isPrestationEditMode = true;
                this.isServiceTypeSelected = true;
                this.isPrestationEdit = true;
                this.isEditMode = true;
                this.sinisterService.findByPrestation(+params['idService']).subscribe((sinister: Sinister) => { // Edition
                    this.sinister = sinister;
                    this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                        this.contratAssurance = contract;
                        this.vehiculeAssureService.find(this.sinister.vehicleId).subscribe((subResult: VehiculeAssure) => {
                            this.vehiculeContrat = subResult;
                            this.insuredService.find(this.vehiculeContrat.insuredId).subscribe((assureRes: Assure) => {
                                this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                this.contratAssurance.adressePhysique = assureRes.adresse;

                            });
                        });
                    });
                    if (this.sinister.incidentDate) {
                        const date = new Date(this.sinister.incidentDate);
                        this.sinister.incidentDate = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    }
                    for (let i = 0; i < this.sinister.prestations.length; i++) {
                        if (+params['idService'] === this.sinister.prestations[i].id) {
                            this.sinisterPrestation = this.sinister.prestations[i];
                            this.observations = this.sinisterPrestation.observations;
                            this.buildSinisterSetting();
                            this.sinisterPrestation.tugArrivalDate = this.owerDateUtils.convertDateTimeFromServer(this.sinisterPrestation.tugArrivalDate);
                            this.sinisterPrestation.insuredArrivalDate = this.owerDateUtils.convertDateTimeFromServer(this.sinisterPrestation.insuredArrivalDate);
                            if (this.sinisterPrestation.tugArrivalDate) {
                                const date = new Date(this.sinisterPrestation.tugArrivalDate);
                                this.sinisterPrestation.tugArrivalDate = {
                                    year: date.getFullYear(),
                                    month: date.getMonth() + 1,
                                    day: date.getDate()
                                };
                                this.tugArrivalTime = {
                                    hour: date.getHours(),
                                    minute: date.getMinutes()
                                }
                            }
                            if (this.sinisterPrestation.insuredArrivalDate) {
                                const date = new Date(this.sinisterPrestation.insuredArrivalDate);
                                this.sinisterPrestation.insuredArrivalDate = {
                                    year: date.getFullYear(),
                                    month: date.getMonth() + 1,
                                    day: date.getDate()
                                };
                                this.insuredArrivalTime = {
                                    hour: date.getHours(),
                                    minute: date.getMinutes()
                                }

                            }

                            break;
                        }
                    }

                    if (this.sinisterPrestation.statusId !== null && this.sinisterPrestation.statusId !== undefined) {
                        if (this.sinisterPrestation.statusId === StatusSinister.CANCELED) {
                            this.sinisterSettings.showCancelGrounds = true;
                            this.sinisterSettings.canReopen = true;
                        } else if (this.sinisterPrestation.statusId === StatusSinister.INPROGRESS) {
                            this.sinisterSettings.canCancel = true;
                        }
                    }
                    // Reopen or cancel operation
                    if (params['action'] && params['action'] === 'reopen') {
                        if (this.sinister.statusId === StatusSinister.NOTELIGIBLE) {
                            this.sinisterPrestation.serviceTypeId = undefined;
                            this.isPrestationEdit = false;
                        }
                        this.reopen();
                    } else if (params['action'] && params['action'] === 'cancel') {
                        this.cancel();
                    }

                    if (this.sinisterPrestation.incidentGovernorateId !== null && this.sinisterPrestation.incidentGovernorateId !== undefined) {
                        this.fetchDelegationsByGovernorate(this.sinisterPrestation.incidentGovernorateId, false);
                    }
                    if (this.sinisterPrestation.destinationGovernorateId !== null && this.sinisterPrestation.destinationGovernorateId !== undefined) {
                        this.fetchDestDelegationsByGovernorate(this.sinisterPrestation.destinationGovernorateId, false);
                    }

                    this.initContrat(this.sinister.contractId, this.sinister.vehicleId);

                }, (error: ResponseWrapper) => this.onError(error.json));
            }
        });

        // Get governorate list
        this.governorateService.query().subscribe((res: ResponseWrapper) => {
            this.governorates = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        // Get service type list
        this.serviceTypeService.query().subscribe((res: ResponseWrapper) => {
            this.serviceTypes = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.raisonAssistanceService.findByStatus(StatusSinister.CANCELED).subscribe((response) => {
            this.cancelGrounds = response.json;
            console.log(this.cancelGrounds);
        });
        this.raisonAssistanceService.findByStatus(StatusSinister.REOPENED).subscribe((response) => { this.reopenGrounds = response.json; });

        this.refAgenceService.findAllAgentAssurance().subscribe((res: ResponseWrapper) => {
            this.refagences = res.json;
        });
        this.naturePanneService.findAllActive().subscribe((res: ResponseWrapper) => {
            this.naturePannes = res.json;
            console.log(this.naturePannes);
        });
        
        this.route.params.subscribe((params) => {
            if (params['sinisterId']) {

            } else {
                this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSS(new Date());
                console.log("declaration date " + this.sinisterPec.declarationDate);
            }
        });

        this.refPositionGaService.query().subscribe((res: ResponseWrapper) => {
            this.listPositionGa = res.json;
        });
    }

    printInsuredArrivalTime() {
        console.log(this.insuredArrivalTime);
        console.log(this.sinisterPrestation.insuredArrivalDate);
        console.log(this.owerDateUtils.convertDateTimeToServer(this.sinisterPrestation.insuredArrivalDate, undefined));
    }

    getImage(refB: RefBareme) {
        if (refB.attachmentName == 'png' || refB.attachmentName == 'PNG') {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageType + refB.attachment64);
        } else {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageTypeJpeg + refB.attachment64);
        }
    }

    validateInsuredArrivalTime() {
        console.log(this.sinisterPrestation.insuredArrivalDate);
        console.log(this.sinisterPrestation.tugArrivalDate);
        console.log(this.insuredArrivalTime);
        this.prestationDateValide = true;
        if (this.sinisterPrestation.insuredArrivalDate === null || this.sinisterPrestation.insuredArrivalDate === undefined ||
            this.sinisterPrestation.tugArrivalDate === null || this.sinisterPrestation.tugArrivalDate === undefined ||
            (this.sinisterPrestation.insuredArrivalDate.day === this.sinisterPrestation.tugArrivalDate.day &&
                this.sinisterPrestation.insuredArrivalDate.month === this.sinisterPrestation.tugArrivalDate.month &&
                this.sinisterPrestation.insuredArrivalDate.year === this.sinisterPrestation.tugArrivalDate.year)) {
            // verify time
            if (this.insuredArrivalTime !== null && this.insuredArrivalTime != undefined && this.tugArrivalTime !== null && this.tugArrivalTime != undefined &&
                (this.insuredArrivalTime.hour < this.tugArrivalTime.hour || (this.insuredArrivalTime.hour === this.tugArrivalTime.hour && this.insuredArrivalTime.minute < this.tugArrivalTime.minute))) {
                this.insuredArrivalTime = undefined;
                this.prestationDateValide = false;
                console.log('invalide');

            }
        }

    }

    validateTugArrivalTime() {
        console.log(this.sinisterPrestation.insuredArrivalDate);
        console.log(this.sinisterPrestation.tugArrivalDate);
        console.log(this.insuredArrivalTime);
        this.prestationDateValide = true;
        if (this.sinisterPrestation.insuredArrivalDate === null || this.sinisterPrestation.insuredArrivalDate === undefined ||
            this.sinisterPrestation.tugArrivalDate === null || this.sinisterPrestation.tugArrivalDate === undefined ||
            (this.sinisterPrestation.insuredArrivalDate.day === this.sinisterPrestation.tugArrivalDate.day &&
                this.sinisterPrestation.insuredArrivalDate.month === this.sinisterPrestation.tugArrivalDate.month &&
                this.sinisterPrestation.insuredArrivalDate.year === this.sinisterPrestation.tugArrivalDate.year)) {
            // verify time
            if (this.insuredArrivalTime !== null && this.insuredArrivalTime != undefined && this.tugArrivalTime !== null && this.tugArrivalTime != undefined &&
                (this.insuredArrivalTime.hour < this.tugArrivalTime.hour || (this.insuredArrivalTime.hour === this.tugArrivalTime.hour && this.insuredArrivalTime.minute < this.tugArrivalTime.minute))) {
                this.tugArrivalTime = undefined;
                this.prestationDateValide = false;
                console.log('invalide');

            }
        }
    }

    /* ToDo test of date servenance service  test eligibilité */

    testEligibilite() {
        this.sinister.nature = 'ACCIDENT';
        this.disabledNatureFieled = true;
        this.refBar = false;
        this.refBarShow = false;
        this.CasBareme = new RefBareme();
        this.requiredField = false;
        this.isDecided = true;
        this.decisionPrest = true;
        this.PointChocRequired = false;
        this.verifTiers();
        this.sinisterPec.isDecidedFromCreateSinister = true;
        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSS(new Date());
        const dateAccident = new Date(this.sinister.incidentDate.year, this.sinister.incidentDate.month - 1, this.sinister.incidentDate.day);
        console.log("testIncidentDate " + dateAccident);
        const datedeclaration = new Date(this.sinisterPec.declarationDate.year, this.sinisterPec.declarationDate.month - 1, this.sinisterPec.declarationDate.day);
        if (this.bloqueChargeAddPec) {

        } else {
            if (dateAccident < new Date(this.contratAssurance.debutValidite) || dateAccident > new Date(this.contratAssurance.finValidite)) {
                this.testCouverte = true;
            }
            else if (datedeclaration < new Date(this.contratAssurance.debutValidite) || datedeclaration > new Date(this.contratAssurance.finValidite)) {
                this.testCouverte = true;
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
                /*this.sinister.statusId = StatusSinister.NOTELIGIBLE;
                this.sinister.sinisterPec = new SinisterPec();
                this.sinister.sinisterPec.isDecidedFromCreateSinister = true;
                this.sinister.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                this.sinister.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                this.sinister.sinisterPec.oldStep = 1
                this.sinister.sinisterPec.stepId = 33;
                this.sinister.sinisterPec.fromDemandeOuverture = false;
                this.sinister.sinisterPec.reasonRefusedId = 94;
                this.sinisterPrestation = new SinisterPrestation();
                this.sinister.sinisterPec.assignedToId = this.userExtra.userId;
                if (this.sinister.id !== null && this.sinister.id !== undefined) {
                    this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                        this.sinister = resSinister;
                        this.router.navigate(['/sinister']);
                        this.alertService.error('auxiliumApp.sinisterPec.home.NoCouvert1');
                    });
                } else {
                    this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                        this.sinister = resSinister;
                        this.router.navigate(['/sinister']);
                        this.alertService.error('auxiliumApp.sinisterPec.home.NoCouvert1');
                    });
                }*/
            } else if (this.testCouverte == true) {
                /*this.sinister.statusId = StatusSinister.NOTELIGIBLE;
                this.sinister.sinisterPec = new SinisterPec();
                this.sinister.sinisterPec.isDecidedFromCreateSinister = true;
                this.sinister.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                this.sinister.sinisterPec.dateCreation = this.dateAsYYYYMMDDHHNNSSLDT(new Date());
                this.sinister.sinisterPec.oldStep = 1
                this.sinister.sinisterPec.stepId = 33;
                this.sinister.sinisterPec.fromDemandeOuverture = false;
                this.sinister.sinisterPec.reasonRefusedId = 94;
                this.sinisterPrestation = new SinisterPrestation();
                this.sinister.sinisterPec.assignedToId = this.userExtra.userId;

                if (this.sinister.id !== null && this.sinister.id !== undefined) {
                    this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                        this.sinister = resSinister;
                        this.router.navigate(['/sinister']);
                        this.alertService.error('auxiliumApp.sinisterPec.home.NoEligible');
                    });
                } else {
                    this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                        this.sinister = resSinister;
                        this.router.navigate(['/sinister']);
                        this.alertService.error('auxiliumApp.sinisterPec.home.NoEligible');
                    });
                }*/

            } else {
                this.sinisterSettings.activateAddService = true;
            }
        }
    }

    testEligibilityVr(){
        
        for (let i = 0; i < this.sinisterTypeSettingsAvenant.length; i++) {
            if (this.sinisterTypeSettingsAvenant[i].id == 12) {
                this.containsService = true;
                break;
            }
        }

        if (this.sinisterTypeSettingsAvenant.length == 0) {
            for (let i = 0; i < this.sinisterTypeSettings.length; i++) {
                if (this.sinisterTypeSettings[i].id == 12) {
                    this.containsService = true;
                    break;
                }
            }
        }

        this.showNocontainsService = this.containsService ? false : true;
    }


    buildSinisterSetting() {
        this.sinisterSettings.heavyWeights = false;
        this.sinisterSettings.holidays = false;
        this.sinisterSettings.night = false;
        this.sinisterSettings.halfPremium = false;
        this.sinisterSettings.fourPorteF = false;
        this.sinisterSettings.fourPorteK = false;
        this.sinisterSettings.isU = false;
        this.sinisterSettings.isR = false;

        this.sinisterSettings.readOnlyPriceHt = false;
        this.sinisterSettings.readOnlyPriceHt = false;
        this.sinisterSettings.isServiceRemorquage = false;
        console.log('____________________________________________________________________________________________________________change');

        if (this.sinisterPrestation.serviceTypeId !== null && this.sinisterPrestation.serviceTypeId !== undefined) {
            switch (this.sinisterPrestation.serviceTypeId) {
                case TypeService.REMORQUAGE:
                    this.sinisterSettings.heavyWeights = true;
                    this.sinisterSettings.holidays = true;
                    this.sinisterSettings.night = true;
                    this.sinisterSettings.halfPremium = true;
                    this.sinisterSettings.fourPorteF = true;
                    this.sinisterSettings.fourPorteK = true;
                    this.sinisterSettings.isU = true;
                    this.sinisterSettings.isR = true;
                    this.sinisterSettings.readOnlyPriceHt = true;
                    this.sinisterSettings.isServiceRemorquage = true;
                    break;
                case TypeService.EXTRACTION:
                    this.sinisterSettings.readOnlyPriceHt = true;
                    break;
                case TypeService.DEPANNAGE:
                case TypeService.DEPLACEMENT:
                    this.sinisterSettings.night = true;
                    this.sinisterSettings.readOnlyPriceHt = true;
                    break;
                default:
                    this.sinisterSettings.readOnlyPriceHt = false;
            }
        }
    }

    /**
     * Initialize contract informations
     */
    initContrat(contractId: number, vehiculeId: number) {
        if (contractId !== null && contractId !== undefined) {
            this.contractService.find(contractId).subscribe((contract: ContratAssurance) => {
                this.contratAssurance = contract;
                this.sinister.contractId = contract.id;
                this.initContratDetails(vehiculeId);

                if (this.vehiculeContrat.packId) {
                    this.principal.identity().then((account) => {
                        this.currentAccount = account;
                        this.refPackService.find(this.vehiculeContrat.packId).subscribe((res: RefPack) => {
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
                                                    if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.contratAssurance.clientId) {

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
                                                if (usrPrtnMd.modeId === mdGstBCnv.id && usrPrtnMd.partnerId === this.contratAssurance.clientId) {

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

        }
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

    updateVehicule() {

        if (this.vehiculeContrat.id !== undefined && this.vehiculeContrat.id !== null) {
            if (this.vehiculeContrat.datePCirculation !== null && this.vehiculeContrat.datePCirculation !== undefined) {
                const datePCirculation = new Date(this.vehiculeContrat.datePCirculation);
                this.vehiculeContrat.datePCirculation = {
                    year: datePCirculation.getFullYear(),
                    month: datePCirculation.getMonth() + 1,
                    day: datePCirculation.getDate()
                };
            }
            this.vehiculeAssureService.update(this.vehiculeContrat).subscribe((subRes: VehiculeAssure) => {
            });
        }
    }

    /**
     * Get contract details : vehicle and insured
     * @param vehicleRegistration
     */
    initContratDetails(vehiculeId: number) {
        if (this.sinister.incidentDate) {
            this.sinisterSettings.activateServiceType = true;
        }

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
            // find insured
            if (this.vehiculeContrat != null) {
                this.vehiculeAssureService.find(this.sinister.vehicleId).subscribe((vehAss: VehiculeAssure) => {
                    this.vehiculeContrat = vehAss;
                    this.insuredService.find(this.vehiculeContrat.insuredId).subscribe((insured: Assure) => {
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
                        if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                            this.delegationService.find(this.insured.delegationId).subscribe((delegation: Delegation) => {
                                this.governorateService.find(delegation.governorateId).subscribe((subRes: Governorate) => {
                                    this.insured.governorateLabel = subRes.label;
                                });
                            });
                        }
                    });
                });
            }
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
    /* verifNbTiers() {
         return this.BtnSaveValide = this.tiers.length == this.sinisterPec.vehicleNumber - 1 ? true : false;
 
     }*/

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
        console.log("testtt 664646464646" + fileInput.target.files[0].type);
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
        console.log("indexOfFirstPoint " + indexOfFirstPoint);
        if (indexOfFirstPoint !== -1) {
            //   console.log ('tt'+ (indexOfFirstPoint-1));
            //   this.nomImage = ((fileInput.target.files[0].name).substring(0,(indexOfFirstPoint))) ;
            // console.log(this.nomImage.concat('.',this.extensionImage));
            this.carteGriseAttachment.name = "noExtension";
            console.log("this.carteGriseAttachment.name-1 " + this.carteGriseAttachment.name);


        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.carteGriseAttachment.name = this.nomImage.concat('.', this.extensionImage);
            console.log("this.carteGriseAttachment.name " + this.carteGriseAttachment.name);

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

        console.log("kkk21  " + (fileInput.target.files[0].name));
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            //   console.log ('tt'+ (indexOfFirstPoint-1));
            //   this.nomImage = ((fileInput.target.files[0].name).substring(0,(indexOfFirstPoint))) ;
            // console.log(this.nomImage.concat('.',this.extensionImage));
            this.acteCessionAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.acteCessionAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.acteCessionFiles = fileInput.target.files[0];
        this.acteCessionPreview = true;
        console.log(this.acteCessionFiles);
        console.log("kkk3  " + (fileInput.target.files[0].type).substring(7, ((fileInput.target.files[0].type).length)));

    }



    /**
     * Find contract by car vin
     * @param query
     */
    findContract(compagnyName, immatriculationVehicle) {
        this.ajoutContrat = false;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log("testUser" + this.currentAccount.id);
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtraPartner = usr;
                this.usersPartner = this.userExtraPartner.userPartnerModes;

                this.contratSuspendu = false;
                this.sowContratSuspendu = false;

                this.vehiculeAssureService.findVehiculeByCompagnyNameAndImmatriculation(compagnyName, immatriculationVehicle).subscribe((vehAss: VehiculeAssure) => {
                    this.vehiculeContrat = vehAss;
                    this.sinister.vehicleRegistration = this.vehiculeContrat.immatriculationVehicule;
                    if (this.vehiculeContrat.contratId !== null && this.vehiculeContrat.contratId !== undefined) {
                        this.contractService.find(this.vehiculeContrat.contratId).subscribe((contratAssurancee: ContratAssurance) => {
                            this.contratAssurance = contratAssurancee;
                            if (this.contratAssurance) {
                                this.ajoutContrat = true;
                                this.immatriculationForAnotherAgent = false;
                                this.showIntervNumberAlert = false;
                                this.listModeGestionByPack = [];
                                this.listModeByCnvByUser = [];
                                this.usersPartnerModes = [];
                                this.sinisterSettings.activateServiceType = true;
                                this.insuredService.find(this.vehiculeContrat.insuredId).subscribe((assureRes: Assure) => {
                                    this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                    this.contratAssurance.adressePhysique = assureRes.adresse;

                                });
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
                                if (this.agentAccess || this.companyAccess || this.userWithRelation || this.userWithAnyRelation) {
                                    this.agentCompanyAcces = true;
                                } else {
                                    this.agentCompanyAcces = false;
                                }

                                if (this.contratAssurance.statusId == 5) {
                                    this.contratSuspendu = true;
                                } else {
                                    this.contratSuspendu = false;
                                }
                                if (this.agentCompanyAcces && !this.contratSuspendu) {

                                    if (this.contratAssurance) {
                                        this.sinister.contractId = this.contratAssurance.id;
                                        this.sinister.vehicleId = this.vehiculeContrat.id;
                                        this.verifTiers();
                                        this.initContratDetails(this.vehiculeContrat.id);

                                        if (this.vehiculeContrat.packId) {
                                            this.principal.identity().then((account) => {
                                                this.currentAccount = account;
                                                this.refPackService.find(this.vehiculeContrat.packId).subscribe((res: RefPack) => {
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
                                                                    this.listModeByCnvByUser = this.listModeGestionByPack;
                                                                    var cache = {};
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
                                            });
                                        }

                                        if (this.vehiculeContrat !== null) {

                                            this.contratAssurance.datePCirculation = this.principal.parseDateWithSeconde(this.vehiculeContrat.datePCirculation);
                                            this.contratAssurance.marqueLibelle = this.vehiculeContrat.marqueLibelle;
                                            this.contratAssurance.modeleLibelle = this.vehiculeContrat.modeleLibelle;

                                        }
                                        if (this.vehiculeContrat.insuredId !== null) {
                                            this.insuredService.find(this.vehiculeContrat.insuredId).subscribe((assureRes: Assure) => {
                                                this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                            });
                                        }

                                        // get sinister by vehicle registartion
                                        this.sinisterService.findViewSinistersPrestationsByVehicleRegistration(this.vehiculeContrat.id).subscribe((res) => {
                                            this.numberInterventionRemaining = 0;
                                            this.numberInterventionDone = 0;
                                            this.sinisterPrestations = res;
                                            // calculation of number intervention remaining
                                            for (let k = 0; k < this.sinisterPrestations.length; k++) {
                                                if (this.sinisterPrestations[k].statusId != null && this.sinisterPrestations[k].statusId != undefined && this.sinisterPrestations[k].statusId == 3) {
                                                    this.numberInterventionDone++;
                                                }
                                            }
                                            this.refPackService.find(this.vehiculeContrat.packId).subscribe((rfPack: RefPack) => {
                                                this.numberInterventionRemaining = rfPack.interventionNumber - this.numberInterventionDone;
                                            });

                                        });
                                        this.sinisterService.findViewSinistersPecByVehicleRegistration(this.vehiculeContrat.id).subscribe((res) => {
                                            //this.numberInterventionRemaining = 0;
                                            //this.numberInterventionDone = 0;
                                            this.viewsinisterPec = res;
                                            this.rerender();
                                            // calculation of number intervention remaining



                                        });
                                        /*this.sinisterService.findByVehicleRegistration(query).subscribe((res) => {
                                            this.numberInterventionRemaining = 0;
                                            this.numberInterventionDone = 0;
                                            this.sinistersByVehicleRegistration = res;
                                            for (let i = 0; i < this.sinistersByVehicleRegistration.length; i++) {
                                                if (this.sinistersByVehicleRegistration[i].prestations != null && this.sinistersByVehicleRegistration[i].prestations != undefined) {
                                                    for (let j = 0; j < this.sinistersByVehicleRegistration[i].prestations.length; j++) {
                                                        this.sinisterPrestations.push(this.sinistersByVehicleRegistration[i].prestations[j]); // get sinister prestation 
                                                    }
                                                }
                                            }
                                            // calculation of number intervention remaining
                                            for (let k = 0; k < this.sinisterPrestations.length; k++) {
                                                if (this.sinisterPrestations[k].statusId != null && this.sinisterPrestations[k].statusId != undefined && this.sinisterPrestations[k].statusId != StatusSinister.NOTELIGIBLE && this.sinisterPrestations[k].statusId != StatusSinister.CANCELED) {
                                                    this.numberInterventionDone++;
                                                }
                                            }
                                            this.numberInterventionRemaining = this.pack.interventionNumber - this.numberInterventionDone;
                                        });*/
                                    }

                                } else {
                                    this.immatriculationForAnotherAgent = true;
                                    if (this.contratSuspendu) {
                                        this.sowContratSuspendu = true;
                                    }
                                    if (!this.agentCompanyAcces) {
                                        this.immatriculationForAnotherAgent = true;
                                    }
                                }


                            }
                        });
                    }
                });
            });
        });
    }

    ajoutNouvelpieceJointe() {
        this.ajoutNouvelpieceform = true;
        this.ajoutNouvelpiece = false;
    }

    downloadPieceFile1() {
        if (this.pieceFiles1) {
            saveAs(this.pieceFiles1);
        }
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

    selectTapedVehiculeImmatriculation(immatriculationVehicule: string) {
        this.contratAssurance = new ContratAssurance();
        this.vehiculeContrat = new VehiculeAssure();
        this.insured = new Assure();
        this.pack = new RefPack();
        this.compagnieName = null;
        this.numberInterventionRemaining = null;
        this.compagniesListes = [];
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

    selectVehicule() {
        console.log(this.compagnieName);
        console.log(this.sinister.vehicleRegistration);
        if (this.compagnieName != null && this.compagnieName != undefined && this.compagnieName !== '' && this.sinister.vehicleRegistration !== null && this.sinister.vehicleRegistration !== undefined
            && this.sinister.vehicleRegistration !== '') {
            this.findContract(this.compagnieName, this.sinister.vehicleRegistration);
        }
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


    /**
     * Get city list by gouvernorat
     * @param id
     */
    fetchDelegationsByGovernorate(id, initFlag) {
        this.delegationService.findByGovernorate(id).subscribe((delegations: Delegation[]) => {
            this.delegations = delegations;
            if (delegations && delegations.length > 0 && initFlag) {
                this.sinisterPrestation.incidentLocationId = delegations[0].id;
            }
        });

        this.gov = false;
    }

    /**
     * Get city list by gouvernorat
     * @param id
     */
    fetchDestDelegationsByGovernorate(id, initFlag) {
        this.delegationService.findByGovernorate(id).subscribe((delegations: Delegation[]) => {
            this.delegationsDest = delegations;
            if (delegations && delegations.length > 0 && initFlag) {
                this.sinisterPrestation.destinationLocationId = delegations[0].id;
            }
        });
    }

    checkDateDeclaration() {
        this.incidentDate = true;
        this.sinisterSettings.activateAddService = false;
        const dateD = new Date(this.sinister.incidentDate.year, this.sinister.incidentDate.month - 1, this.sinister.incidentDate.day);

        if (dateD < new Date(this.contratAssurance.debutValidite) || dateD > new Date(this.contratAssurance.finValidite)) {
            alert("La date de l'accident doit être comprise entre les dates des validités du contrat d'assurance ");
            this.incidentDate = false;
            this.sinister.incidentDate = {};
        } else {
            if (this.sinister.vehicleId !== null && this.sinister.vehicleId !== undefined &&
                this.sinister.incidentDate !== null && this.sinister.incidentDate !== undefined) {
                this.sinisterService.isSinisterDuplicated(this.sinister).subscribe((sinister: Sinister) => {
                    if (sinister.id !== null && sinister.id !== undefined) {

                        this.confirmationDialogService.confirm('Confirmation', "Il existe un dossier avec le même numéro d'immatriculation et la même date de survenance. Voulez-vous le recharger?", 'Oui', 'Non', 'lg')
                            .then((confirmed) => {
                                if (confirmed) {
                                    this.router.navigate(['/sinister/' + sinister.id + '/edit']);
                                } else {
                                    this.sinister.incidentDate = {};
                                }
                            })
                            .catch(() => {
                                this.sinister.incidentDate = {};
                            });
                    } else {
                        this.sinisterSettings.activateAddService = true;
                        this.sinisterSettings.activateServiceType = true;
                    }
                });
            }
        }

    }


    selectServiceType() {
        // TODO: check eligibility service
        this.showNocontainsService = false;
        this.containsService = false;
        this.isVRSelected=false;
        this.isVR = false;
        this.showAlertBloqueAgent = false;
        this.showAlertNoCreateAccess = false;
        this.disabledNatureFieled = false;
        this.showIntervNumberAlert = false;
        this.showServiceTypeInactiveAlert = false;
        this.isServiceTypeSelected = true;
        this.decisionPrest = false;
        this.requiredField = true;
        this.refBar = true;
        console.log("tesServiceId " + this.sinisterPrestation.serviceTypeId);
        const sServiceType = this.sinisterPrestation.serviceTypeId;
        if (sServiceType === TypeService.AMBULANCE || sServiceType === TypeService.ASSISTANCE_JURIDIQUE || sServiceType === TypeService.ASSISTANCE_MEDICAL ||
            sServiceType === TypeService.ASSISTANCE_REMPLISSAGE_CONSTAT || sServiceType === TypeService.ASSISTANCE_VENTE_ACHAT || sServiceType === TypeService.CONSULTATION_TELEPHONIQUE_MEDECIN ||
            sServiceType === TypeService.FRAIS_TRANSPORT) {
            this.showServiceTypeInactiveAlert = true;
        }


        // Verify eligibility sinister Prise en charge 
        if (this.sinisterPrestation.serviceTypeId == 11) {
            if (this.canCreatePec) {
                this.testEligibilite();
                if (this.bloqueChargeAddPec) {
                    this.showAlertBloqueAgent = true;
                    this.sinisterSettings.isPecEdit = false;
                    this.isPrestationEdit = false;
                } else {
                    this.showAlertBloqueAgent = false;
                }
            } else {
                this.showAlertNoCreateAccess = true;
            }
        } 
        else if (this.sinisterPrestation.serviceTypeId == 12){
          this.isVRSelected=true;
          this.sinisterSettings.isPecEdit = false;
          this.isPrestationEdit = false;
          //this.testEligibilityVr();
          


        }
        else {
            if (this.canCreateService) {
                // Verify eligibility sinisterPrestation
                const copy: Sinister = Object.assign({}, this.sinister);
                const service = new SinisterPrestation();
                service.serviceTypeId = this.sinisterPrestation.serviceTypeId;
                copy.prestations = [];
                copy.prestations.push(service);
                this.sinisterService.canCreate(copy).subscribe((sinister: Sinister) => {
                    console.log("testSTATUSEligibilité " + sinister.statusId);
                    if (StatusSinister.NOTELIGIBLE === sinister.statusId) {
                        this.alertService.error('auxiliumApp.sinister.notEligible', null, null);
                        this.router.navigate(['/prestation-not-eligible']);
                    } else {
                        this.sinisterSettings.activateAddService = true;
                        this.buildSinisterSetting();
                        const serviceTypeId = this.sinisterPrestation.serviceTypeId;
                        //this.sinisterPrestation = new SinisterPrestation();
                        this.sinisterPrestation.serviceTypeId = serviceTypeId;
                        if (!this.sinisterSettings.holidays) { this.sinisterPrestation.holidays = undefined; }
                        if (!this.sinisterSettings.night) { this.sinisterPrestation.night = undefined; }
                        if (!this.sinisterSettings.heavyWeights) { this.sinisterPrestation.heavyWeights = undefined; }
                        if (!this.sinisterSettings.halfPremium) { this.sinisterPrestation.halfPremium = undefined; }
                        if (!this.sinisterSettings.fourPorteF) { this.sinisterPrestation.fourPorteF = undefined; }
                        if (!this.sinisterSettings.fourPorteK) { this.sinisterPrestation.fourPorteK = undefined; }
                        if (!this.sinisterSettings.isU) { this.sinisterPrestation.isU = undefined; }
                        if (!this.sinisterSettings.isR) { this.sinisterPrestation.isR = undefined; }

                        if (this.sinisterPrestation.serviceTypeId === TypeService.GRUTAGE) {
                            this.isServiceGrutage = true;
                        }
                        this.calculatePriceHt();

                    }
                });
            } 

        }

    }

    addPrestation() {
        this.testSendNotifRespPeck = false;
        this.disabledDecisionButton = false;
        if (this.showServiceTypeInactiveAlert) {
            this.sinisterSettings.isPecEdit = false;
            this.isPrestationEdit = false;
            this.isVR=false;

        }
        else if (this.sinisterPrestation.serviceTypeId == 11) {
            this.sinisterSettings.isPecEdit = true;
            this.isPrestationEdit = false;
            this.isVR=false;


        }
        else if (this.isVRSelected){
            this.isVR=true;
            this.showServiceTypeInactiveAlert = true;
            this.sinisterSettings.isPecEdit = false;
            this.isPrestationEdit = false;

        }
        else {
            this.isVR=false;

            this.sinisterService.canSavePrestation(this.vehiculeContrat.id).subscribe((intervNumber: boolean) => {
                if (intervNumber) {
                    this.showIntervNumberAlert = false;
                    this.sinisterSettings.isPecEdit = false;
                    this.isPrestationEdit = true;
                    this.buildSinisterSetting();
                    if (this.sinisterPrestation.serviceTypeId === TypeService.GRUTAGE) {
                        this.isServiceGrutage = true;
                    }
                } else {
                    this.showIntervNumberAlert = true;
                }
            });
            this.refPackService.find(this.vehiculeContrat.packId).subscribe((packTS: RefPack) => {
                for (let i = 0; i < packTS.serviceTypes.length; i++) {
                    if (packTS.serviceTypes[i].id == 11) {
                        this.testSendNotifRespPeck = true;
                        break;
                    }
                }
            });
        }
    }

    pointChocObligatoire() {
        this.PointChocRequired = false;
        if (this.pointChoc.avant) { this.PointChocRequired = true; }
        else if (this.pointChoc.arriere) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.arriereGauche) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleGauche) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleGauchearriere) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraleGaucheAvant) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.arriereDroite) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.lateraledroite) {
            this.PointChocRequired = true;
        }
        else if (this.pointChoc.toit) {
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
        console.log('test karaaa', this.pointChoc.lateraleGauchearriere);


    }
    selectTug() {
        this.ngbModalRef = this.siniterPrestationTugPopupService.openTugModal(SinisterPrestationTugComponent as Component, this.sinisterPrestation);
        this.ngbModalRef.result.then((result: Camion) => {
            if (result !== null && result !== undefined) {
                this.sinisterPrestation.affectedTugId = result.refTugId;
                this.sinisterPrestation.affectedTugLabel = result.refTug.societeRaisonSociale;
                this.sinisterPrestation.affectedTruckId = result.id;
                this.sinisterPrestation.affectedTruckLabel = result.immatriculation;
                this.sinisterPrestation.vatRate = result.refTug.vatRate; // Without /100
                this.calculatePriceHt();
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            this.ngbModalRef = null;
        });

    }

    openPrestationView(prestationId: number) {
        this.ngbModalRef = this.siniterPrestationTugPopupService.openViewPrestationModal(SinisterPrestationViewComponent as Component, prestationId);
        this.ngbModalRef.result.then((result) => {
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            this.ngbModalRef = null;
        });
    }

    openPecView(pecId: number) {
        this.ngbModalRef = this.sinisterPecPopupService.openHistoryDetailsSinisterPec(HistoryPopupDetailsPec as Component, pecId);
        this.ngbModalRef.result.then((result) => {
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            this.ngbModalRef = null;
        });
    }

    cancel() {
        this.sinisterPrestation.cancelGroundsId = null;
        this.sinisterSettings.showCancelGrounds = true;
        this.sinisterPrestation.statusId = StatusSinister.CANCELED;
    }

    reopen() {
        this.sinisterPrestation.reopenGroundsId = null;
        this.sinisterSettings.showReopenGrounds = true;
        this.sinisterSettings.showCancelGrounds = false;
        this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;
        this.sinisterPrestation.insuredArrivalDate = undefined;
        this.insuredArrivalTime = undefined;
    }

    /**
     * Calculate service price Ht
     */
    calculatePriceHt() {
        this.maxValueMileage = false;
        this.minValueMileage = false;
        if(this.sinisterSettings.isServiceRemorquage){
            if(this.sinisterPrestation.isR == true && this.sinisterPrestation.mileage !== null && this.sinisterPrestation.mileage !== undefined){
                this.minValueMileage = (this.sinisterPrestation.mileage >= 60 ) ? false : true;
                this.maxValueMileage = false;
            }
            if(this.sinisterPrestation.isU == true && this.sinisterPrestation.mileage !== null && this.sinisterPrestation.mileage !== undefined){
                this.maxValueMileage = (this.sinisterPrestation.mileage >= 0 &&  this.sinisterPrestation.mileage <= 60) ? false : true;
                this.minValueMileage = false;
            }
        }
        if (((this.sinisterSettings.isServiceRemorquage && this.sinisterPrestation.mileage !== null && this.sinisterPrestation.mileage !== undefined) || !this.sinisterSettings.isServiceRemorquage) &&
            this.sinisterPrestation.affectedTruckId !== null && this.sinisterPrestation.affectedTruckId !== undefined &&
            this.sinisterPrestation.affectedTugId !== null && this.sinisterPrestation.affectedTugId !== undefined) {
            this.refTarifService.findTugPricing(this.sinisterPrestation.affectedTugId).subscribe((tugPricing: TugPricing) => {
                if (tugPricing !== null && tugPricing !== undefined) {
                    let priceIncreaseRate = 1;
                    let priceHt = 0;
                    if (this.sinisterPrestation.holidays) { priceIncreaseRate *= 1.5; }
                    if (this.sinisterPrestation.night) { priceIncreaseRate *= 1.5; }
                    if (this.sinisterPrestation.heavyWeights) { priceIncreaseRate *= 1.5; }
                    if (this.sinisterPrestation.halfPremium) { priceIncreaseRate *= 1.25; }

                    switch (this.sinisterPrestation.serviceTypeId) {
                        case TypeService.REMORQUAGE:
                            const mileagePart1 = this.sinisterPrestation.mileage >= 60 ? 60 : this.sinisterPrestation.mileage;
                            const mileagePart2 = this.sinisterPrestation.mileage > 60 ? (this.sinisterPrestation.mileage >= 300 ? 240 : this.sinisterPrestation.mileage - 60) : 0;
                            const mileagePart3 = this.sinisterPrestation.mileage > 300 ? this.sinisterPrestation.mileage - 300 : 0;
                            priceHt = ((mileagePart1 > 0 ? tugPricing.priceUrbanPlanService : 0) +
                                mileagePart2 * tugPricing.priceKlmShortDistance + mileagePart3 * tugPricing.priceKlmLongDistance);
                            this.sinisterPrestation.priceHt = priceIncreaseRate !== 0 ? priceHt * priceIncreaseRate : priceHt;
                            if (this.sinisterPrestation.fourPorteF) { this.sinisterPrestation.priceHt = this.sinisterPrestation.priceHt + 5; }
                            if (this.sinisterPrestation.fourPorteK) { this.sinisterPrestation.priceHt = this.sinisterPrestation.priceHt + 24; }
                            this.sinisterPrestation.priceHt = +this.sinisterPrestation.priceHt.toFixed(3);
                            this.calculatePriceTtc();
                            break;
                        case TypeService.EXTRACTION:
                            this.sinisterPrestation.priceHt = tugPricing.priceExtraction;
                            this.sinisterPrestation.priceHt = +this.sinisterPrestation.priceHt.toFixed(3);
                            this.calculatePriceTtc();
                            break;
                        case TypeService.DEPANNAGE:
                            this.sinisterPrestation.priceHt = priceIncreaseRate !== 0 ? tugPricing.priceReparation * priceIncreaseRate : tugPricing.priceReparation;
                            this.sinisterPrestation.priceHt = +this.sinisterPrestation.priceHt.toFixed(3);
                            this.calculatePriceTtc();
                            break;
                        case TypeService.DEPLACEMENT:
                            this.sinisterPrestation.priceHt = priceIncreaseRate !== 0 ? tugPricing.priceUrbanMobility * priceIncreaseRate : tugPricing.priceUrbanMobility;
                            this.sinisterPrestation.priceHt = +this.sinisterPrestation.priceHt.toFixed(3);
                            this.calculatePriceTtc();
                            break;
                        case 5:
                        default: console.log('Service type inconnu.');
                    }

                }
            });

        }
    }

    /**
     * Calculate service price TTC
     */
    calculatePriceTtc() {
        if (this.sinisterPrestation.priceHt !== null && this.sinisterPrestation.priceHt !== undefined) {
            if (this.sinisterPrestation.serviceTypeId === TypeService.EXTRACTION) {
                this.sinisterPrestation.priceTtc = this.sinisterPrestation.priceHt * (1 + (this.sinisterPrestation.vatRate == 0 ? 0 : 0.19));
            } else {
                this.sinisterPrestation.priceTtc = this.sinisterPrestation.priceHt * (1 + this.sinisterPrestation.vatRate / 100);
            }
            this.sinisterPrestation.priceTtc = +this.sinisterPrestation.priceTtc.toFixed(3);
        }
    }

    // add by Halim 
    LoadBareme(id: number) {
        console.log("testCasBareme " + id);
        if (id) {
            this.refBaremeService.find(id).subscribe((baremeRes) => {
                if (baremeRes) {
                    this.CasBareme = baremeRes;
                    this.refBar = true;
                    this.sinisterPec.regleModeGestion = false;
                    this.reglesGestion();
                    if (!this.sinisterPec.regleModeGestion) {
                        this.sinisterPec.prestAApprouv = false;
                    }
                    this.refBarShow = true;
                    console.log("testCasBareme " + this.CasBareme.id);
                    // this.imgBareme = require('../../../content/images/refBareme/' + this.CasBareme.attachment.name);
                }
            });
        }

    }


    /*initializeDemand() {
        this.route.params.subscribe((params) => {
            if (params['id']) {
                const pecId = params['id'];
                // Get pec prestation
                this.sinisterService.find(pecId).subscribe((res: Sinister) => {
                    this.sinister = res;
                    this.sinisterPec = this.sinister.sinisterPec;
                    if (this.sinisterPec.pointChoc !== null) {
                        console.log(this.sinisterPec.pointChoc);
                        this.pointChoc = this.sinisterPec.pointChoc;
                    }
                    // Treatment of folder dates
                    if (this.sinister.incidentDate) {
                        const date = new Date(this.sinister.incidentDate);
                        this.sinister.incidentDate = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    }
                    this.creation = this.principal.parseDate(this.sinister.creationDate);
                    this.cloture = this.principal.parseDate(this.sinister.closureDate);
                    this.miseAjour = this.principal.parseDate(this.sinister.updateDate);
                    this.initContrat(this.sinister.contractId, this.sinister.vehicleId);
                    this.changeMode(this.sinisterPec.modeId);
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
                    this.loadAllAttachments();

                });
            } else if (params['immatriculation']) {
                this.sinister = new Sinister();
                this.sinister.vehicleRegistration = params['immatriculation'];
                this.sinisterPec = new SinisterPec();
                this.initContrat(this.sinister.contractId, this.sinister.vehicleRegistration);
            } else {
                this.sinisterPec = new SinisterPec();
                this.sinister = new Sinister();
                this.initContrat(this.sinister.contractId, this.sinister.vehicleRegistration);
            }
        });
    }*/
    /*** Load all attachments for  sinister pec */
    loadAllAttachments() {
        this.sinisterService.getAttachments(this.sinisterPec.id).subscribe((subRes: ResponseWrapper) => {
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
        this.affichePieceSJointes();
    }


    // view photos
    affichePieceSJointes() {

        if (this.attachmentList !== null) {
            if (this.attachmentList !== undefined && this.attachmentList !== null) {
                this.attachmentList.forEach(attachment => {
                    switch (attachment.label) {
                        case "Acte de cession":
                            this.imgacteDeCession = 'https://localhost:8091/helmi/' + attachment.entityName + '/' + attachment.name;
                            break;
                        case "Carte Grise":
                            this.imgConstat = 'https://localhost:8091/helmi/' + attachment.entityName + '/' + attachment.name;
                            break;
                        case "Constat":
                            this.imgCarteGrise = 'https://localhost:8091/helmi/' + attachment.entityName + '/' + attachment.name;
                            break;
                        default:
                            console.log("yes it's is")
                    }
                });
            }
        }

    }

    changeMode(value) {
        console.log(value);

        //this.sinisterPec.vehicleNumber = null;
        this.sinisterPec.regleModeGestion = false;
        this.reglesGestion();
        if (!this.sinisterPec.regleModeGestion) {
            this.sinisterPec.prestAApprouv = false;
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
            this.nbrVehiclePattern = '^[2-9]*$';
            this.minVinNumber = 2;
            //this.VinNumber = 2 ;
            this.vehNmbrIda = true;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.sinisterPec.vehicleNumber = 2;
            this.nbrVehiclePattern = '^[2-9]*$';
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        } else {
            this.sinisterPec.vehicleNumber = 1;
            this.nbrVehiclePattern = '^[1-9]*$';
            //this.sinisterPec.vehicleNumber = 1;
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }

    }
    //Get change pec by Mode
    changeModeGestion(value) {
        console.log(value);

        //this.sinisterPec.vehicleNumber = null;
        this.sinisterPec.regleModeGestion = false;
        this.reglesGestion();
        if (!this.sinisterPec.regleModeGestion) {
            this.sinisterPec.prestAApprouv = false;
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
            this.minVinNumber = 2;
            this.nbrVehiclePattern = '^[2-9]*$';
            //this.VinNumber = 2 ;
            this.vehNmbrIda = true;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }
        else if (value === 3 || value === 4 || value === 5 || value === 11) {
            //this.sinisterPec.vehicleNumber = 2;
            this.nbrVehiclePattern = '^[2-9]*$';
            this.vehNmbrIda = false;
            //this.VinNumber = 2 ;
            this.minVinNumber = 2;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        } else {
            //this.sinisterPec.vehicleNumber = 1;
            this.nbrVehiclePattern = '^[1-9]*$';
            this.vehNmbrIda = false;
            //this.VinNumber = 1 ;
            this.minVinNumber = 1;
            this.verifTiers();
            //this.vhclNmbrReq = false;
        }

    }

    fetchDelegationsByGovernorateRep(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernorat = subRes;
            this.delegationService.findByGovernorate(this.sysGouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.sysvillesRep = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.sinisterPec.villeRepId = subRes1[0].id;
                }
            });
        });
        this.govRep = false;
    }

    /**
     * Save sinister
     */
    traiter() {
        this.sinisterPec.stepId = PrestationPecStep.BEING_PROCESSED;
        this.sinister.sinisterPec = this.sinisterPec;

        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.router.navigate(['/pec-being-processed']);
            });
        }

    }


    /**
     * Save sinister
     */
    save(editForm:  NgForm) {

        if (this.isPrestationEdit) {
            this.sinister.statusId = StatusSinister.INPROGRESS;
            this.sinister.governorateId = this.sinisterPrestation.incidentGovernorateId;
            this.sinister.locationId = this.sinisterPrestation.incidentLocationId;
            if (this.sinisterPrestation.statusId === null || this.sinisterPrestation.statusId === undefined) {
                this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;
            }
            if (this.sinisterPrestation.insuredArrivalDate !== null && this.sinisterPrestation.insuredArrivalDate !== undefined) {
                this.sinisterPrestation.statusId = StatusSinister.CLOSED;
            }
            this.sinisterPrestation.observations = this.observations;
            const copy: SinisterPrestation = Object.assign({}, this.sinisterPrestation);
            if (this.sinisterPrestation.insuredArrivalDate !== undefined && this.insuredArrivalTime !== undefined) {
                this.sinisterPrestation.insuredArrivalDate.hour = this.insuredArrivalTime.hour;
                this.sinisterPrestation.insuredArrivalDate.minute = this.insuredArrivalTime.minute;
                this.sinisterPrestation.insuredArrivalDate.second = 0;
                copy.insuredArrivalDate = this.owerDateUtils.convertDateTimeToServer(this.sinisterPrestation.insuredArrivalDate, undefined);
            }
            if (this.sinisterPrestation.tugArrivalDate !== undefined && this.tugArrivalTime !== undefined) {
                this.sinisterPrestation.tugArrivalDate.hour = this.tugArrivalTime.hour;
                this.sinisterPrestation.tugArrivalDate.minute = this.tugArrivalTime.minute;
                this.sinisterPrestation.tugArrivalDate.second = 0;
                copy.tugArrivalDate = this.owerDateUtils.convertDateTimeToServer(this.sinisterPrestation.tugArrivalDate, undefined);
            }
            if (this.isPrestationEditMode) {
                for (let i = 0; i < this.sinister.prestations.length; i++) {
                    if (this.sinisterPrestation.id === this.sinister.prestations[i].id) {
                        this.sinister.prestations.splice(i, 1);
                    }
                }
            }
            this.sinister.prestations.push(copy);
            console.log("*************************************************************");

            console.log(copy);
        }
        if (this.sinisterSettings.isPecEdit) {
            this.nbrTiersRespMin = 0;
            this.tiers.forEach(tierR => {
                if (tierR.responsible) {
                    this.nbrTiersRespMin++;
                }
            });

            this.sinister.statusId = StatusSinister.INPROGRESS;
            const dateSavePec = new Date(this.sinisterPec.declarationDate);
            this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateSavePec);
            this.sinisterPec.stepId = PrestationPecStep.BEING_PROCESSED;
            this.sinisterPec.fromDemandeOuverture = false;
            this.sinisterPec.decision = null;
            this.sinisterPec.generatedLetter = false;
            this.sinisterPec.baremeId = this.CasBareme.id;
            if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
                if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                    this.pointChoc.id = this.sinisterPec.pointChoc.id;
                }
            }
            console.log('test 111', this.pointChoc);
            this.sinisterPec.pointChoc = this.pointChoc;
            console.log('test 111', this.sinisterPec.pointChoc);

            this.sinister.sinisterPec = this.sinisterPec;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.sinisterPec.assignedToId = this.currentAccount.id;
            });
        }
       
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                if (confirmed) {
                    if (this.sinisterSettings.isPecEdit) {

                        if (this.sinisterPec.vehicleNumber > 1) {
                            if (this.nbrTiersRespMin == 1) {
                                this.tierRespMin = true;
                                this.createOrUpdateSinister();
                            } else {
                                this.tierRespMin = false;
                            }
                        } else {
                            this.createOrUpdateSinister();
                        }

                    } else {
                        if (this.sinister.id !== null && this.sinister.id !== undefined) {
                            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                if (this.testSendNotifRespPeck == true && this.sinister.nature == 'ACCIDENT') {
                                    this.sendNotifPrestationAssistance('notifAssistanceRespPec', this.sinister.prestations[this.sinister.prestations.length - 1].id);
                                }
                                this.router.navigate(['/sinister']);
                            });
                        } else {
                            this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                                this.sinister = resSinister;
                                if (this.testSendNotifRespPeck == true && this.sinister.nature == 'ACCIDENT') {
                                    this.sendNotifPrestationAssistance('notifAssistanceRespPec', this.sinister.prestations[this.sinister.prestations.length - 1].id);
                                }
                                this.router.navigate(['/sinister']);
                            });
                        }
                    }
                }
            });

           
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

    verifTiers() {
        this.nbrTiers = 0;
        this.tiers.forEach(element => {
            if (element.deleted == true) {
            } else {
                this.nbrTiers++;
            }
        });
        this.reglesGestion();
        if (!this.sinisterPec.regleModeGestion) {
            this.sinisterPec.prestAApprouv = false;
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

    createOrUpdateSinister() {
        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.sinisterPec = this.sinister.sinisterPec;
                this.saveAttachments(this.sinister.sinisterPec.id);
                this.savePhotoPlusPec();
                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                this.updateVehicule();
                this.router.navigate(['/sinister']);
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
                this.router.navigate(['/sinister']);
            });
        }
    }

    listAgenceByCompagnie(id) {
        this.otherField = false;
        this.nameAgence = false;
        //this.refagences = [];


        this.refAgenceService.findAllByPartnerWithoutFiltrage(id).subscribe((res: ResponseWrapper) => {
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

    changeAssujettie(etat) {
        if (etat == false) {
            this.sinisterPec.assujettieTVA = false;
        } else {
            this.sinisterPec.assujettieTVA = true;
        }
    }

    newfield(nom) {
        if (nom == 'Autre') {
            this.nameAgence = true;
        } else {
            this.nameAgence = false;
        }
    }

    parseHtmlDate(date) {
        return this.principal.parseDateJour(this.dateUtils.convertLocalDateToServer(date));
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
    deleteTier(tier) {
        console.log('_____________________________________ delete Tier _______________________________________________');
        tier.deleted = true;
        tier.responsible = false;
        /*this.tiers.forEach((item, index) => {
            if (item === tier) this.tiers.splice(index, 1);
        });*/
        this.verifTiers();
    }
    searchTiers(query, compagnyId) {

        if (query !== null && query !== undefined && query !== '' && compagnyId !== null && compagnyId !== undefined) {
            this.isObsModeEdit = true;
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

            this.contractService.findVehiculeByImmatriculationAndClient(query, compagnyId).subscribe((contratAssurancee) => {
                this.contratAssuranceDuTiers = contratAssurancee;

                console.log(contratAssurancee);
                if (this.contratAssuranceDuTiers) {
                    this.vehiculeAssureService.findVehiculeByCompagnyIdAndImmatriculation(compagnyId, query).subscribe((vehAss: VehiculeAssure) => {
                        this.vehiculeContratTiers = vehAss;
                        this.agentCompanyRO = true;
                        this.exist = true;
                        this.old = true;
                        this.tier.numeroContrat = this.contratAssuranceDuTiers.numeroContrat;
                        this.insuredService.find(this.vehiculeContratTiers.insuredId).subscribe((assureRes: Assure) => {
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
                        this.tier.agenceId = this.contratAssuranceDuTiers.agenceId;
                        this.refAgenceService.find(this.contratAssuranceDuTiers.agenceId).subscribe((agenceRes: Agency) => {
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
                            this.tier.agenceId = this.trouveTiers.agenceId;
                            this.refAgenceService.find(this.trouveTiers.agenceId).subscribe((agenceRes: Agency) => {
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
    prepareEditTier(tier) {
        console.log('_____________________________________ edit Tier _______________________________________________');
        console.log(tier);
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
        this.refAgenceService.findAllAgentAssurance().subscribe((res: ResponseWrapper) => {
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
        this.contractService.findContractByImmatriculationForTiers(tier.immatriculation, tier.compagnieId).subscribe((existContract: boolean) => {
            if (existContract == false) {
                this.refAgenceService.findAllByPartnerWithoutFiltrage(tier.compagnieId).subscribe((listes: ResponseWrapper) => {
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
        /*let tiersAvant = this.tiers;
        this.tiers = [];
        this.tiers = tiersAvant;*/
    }

    decider() {
        this.sinisterPec.fromDemandeOuverture = false;
        this.sinisterPec.tiers = this.tiers;
        this.sinisterPec.observations = this.observationss;
        this.sinisterPec.baremeId = this.CasBareme.id;
        this.sinisterPec.responsabilityRate = false;
        this.sinisterPec.generatedLetter = false;
        if (this.sinisterPec.pointChoc != undefined && this.sinisterPec.pointChoc != null) {
            if (this.sinisterPec.pointChoc.id != undefined && this.sinisterPec.pointChoc.id != null) {
                this.pointChoc.id = this.sinisterPec.pointChoc.id;
            }
        }
        this.sinisterPec.pointChoc = this.pointChoc;
        if (this.isDecided) {
            this.sinisterPec.isDecidedFromCreateSinister = true;
            this.sinister.sinisterPec = this.sinisterPec;
            this.reglesGestion();
        } else {
            this.sinisterPec.isDecidedFromCreateSinister = false;
            this.sinister.sinisterPec = this.sinisterPec;
        }
        if (this.cmpRf) {
            this.sinisterPec.rfCmp = true;
            this.sinister.sinisterPec = this.sinisterPec;
        } else {
            this.sinisterPec.rfCmp = false;
            this.sinister.sinisterPec = this.sinisterPec;
        }
        this.nbrTiersRespMin = 0;
        this.tiers.forEach(tierR => {
            if (tierR.responsible) {
                this.nbrTiersRespMin++;
            }
        });
        if (this.sinisterPec.vehicleNumber > 1) {
            if (this.nbrTiersRespMin == 1) {
                this.tierRespMin = true;
                this.ngbModalRef = this.siniterPecPopupService.openDecisionSinisterModal(DecisionSinisterPecComponent as Component, this.sinister, this.contratAssurance, this.constatAttachment, this.carteGriseAttachment, this.acteCessionAttachment, this.constatFiles, this.carteGriseFiles, this.acteCessionFiles, this.listModeGestionByPack, this.vehiculeContrat, this.updateConstat, this.updateCarteGrise, this.updateActeDeCession, this.piecesAttachments, this.updatePieceJointe1);
                this.ngbModalRef.result.then((result: any) => {
                    if (result !== null && result !== undefined) {
                        console.log("result select popup------" + result);
                        //this.sinister.sinisterPec.decision = result.decision;
                        this.modeId = result.modeId;

                    }
                    this.ngbModalRef = null;
                }, (reason) => {
                    // TODO: print error message
                    console.log('______________________________________________________2');
                    this.ngbModalRef = null;
                });
            } else {
                this.tierRespMin = false;
            }
        } else {
            this.ngbModalRef = this.siniterPecPopupService.openDecisionSinisterModal(DecisionSinisterPecComponent as Component, this.sinister, this.contratAssurance, this.constatAttachment, this.carteGriseAttachment, this.acteCessionAttachment, this.constatFiles, this.carteGriseFiles, this.acteCessionFiles, this.listModeGestionByPack, this.vehiculeContrat, this.updateConstat, this.updateCarteGrise, this.updateActeDeCession, this.piecesAttachments, this.updatePieceJointe1);
            this.ngbModalRef.result.then((result: any) => {
                if (result !== null && result !== undefined) {
                    console.log("result select popup------" + result);
                    //this.sinister.sinisterPec.decision = result.decision;
                    this.modeId = result.modeId;

                }
                this.ngbModalRef = null;
            }, (reason) => {
                // TODO: print error message
                console.log('______________________________________________________2');
                this.ngbModalRef = null;
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

    approuve() {


        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.approveButton == true) {
                    this.approveButton = false;
                    /*this.sinisterPec.step = 3;
                  this.sinisterPec.decision = 'INPROGRESS';*/
                    switch (this.sinisterPec.decision) {
                        case 'CANCELED':
                            this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                            this.sinisterPec.stepId = 9;
                            const dateAnnuleRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateAnnuleRefusePec);
                            break;
                        case 'ACC_WITH_RESRV':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                            this.sinisterPec.stepId = 12;
                            break;
                        case 'ACC_WITH_CHANGE_STATUS':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                            this.sinisterPec.stepId = 13;
                            break;
                        case 'REFUSED':
                            this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                            this.sinisterPec.stepId = 10;
                            const dateRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateRefusePec);
                            break;
                        case 'ACCEPTED':
                            this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                            this.sinisterPec.stepId = 11;
                            break;
                    }

                    this.sinisterPec.approvPec = 'APPROVE';
                    this.updatePec();
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

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
                    this.notifUser.notification = this.notification;
                    this.notifUser.entityId = this.sinisterPec.id;
                    this.notifUser.entityName = "SinisterPec";
                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                    this.listNotifUser.push(this.notifUser);
                    if (usr.profileId == 6) {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 14;
                        this.notifUser.source = usr.id;
                        this.notifUser.destination = usr.userBossId;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.notification = this.notification;
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                    }
                    console.log("3-------------------------------------------------------------------");
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json;
                        console.log("4-------------------------------------------------------------------");
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 14;
                            this.notifUser.source = usr.id;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 14;
                                this.notifUser.source = usr.id;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.findByProfil(9).subscribe((userExNotifChargeAdministrative: UserExtra[]) => {
                                this.userExNotifChargeAdministrative = userExNotifChargeAdministrative;
                                this.userExNotifChargeAdministrative.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 14;
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
                                this.userExtraService.findByProfil(10).subscribe((userExNotifResponsableAdministrative: UserExtra[]) => {
                                    this.userExNotifResponsableAdministrative = userExNotifResponsableAdministrative;
                                    this.userExNotifResponsableAdministrative.forEach(element => {
                                        this.notifUser = new NotifAlertUser();
                                        this.notification.id = 14;
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
                                            this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId }));
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
                    if (usr.profileId == 6) {
                        this.notifUser = new NotifAlertUser();
                        this.notification.id = 15;
                        this.notifUser.source = usr.id;
                        this.notifUser.destination = usr.userBossId;
                        this.notifUser.notification = this.notification;
                        this.notifUser.entityId = this.sinisterPec.id;
                        this.notifUser.entityName = "SinisterPec";
                        this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'idPartner': this.sinisterPec.partnerId, 'agenceId': this.contratAssurance.agenceId, 'stepPecId': this.sinisterPec.stepId });
                        this.listNotifUser.push(this.notifUser);
                    }
                    this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                        this.userExNotifAgency = userExNotifAgency.json
                        this.userExNotifAgency.forEach(element => {
                            this.notifUser = new NotifAlertUser();
                            this.notification.id = 15;
                            this.notifUser.source = usr.userId;
                            this.notifUser.destination = element.userId;
                            this.notifUser.notification = this.notification;
                            this.notifUser.entityId = this.sinisterPec.id;
                            this.notifUser.entityName = "SinisterPec";
                            this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                            this.listNotifUser.push(this.notifUser);
                        });
                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                            this.userExNotifPartner = userExNotifPartner.json;
                            this.userExNotifPartner.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 15;
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'assignedTo': this.sinisterPec.assignedToId, 'etatPrestation': etatPrestation, 'sinisterPecId': this.sinisterPec.id, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.sinisterPec.partnerId, 'stepPecId': this.sinisterPec.stepId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.findByProfil(9).subscribe((userExNotifChargeAdministrative: UserExtra[]) => {
                                this.userExNotifChargeAdministrative = userExNotifChargeAdministrative;
                                this.userExNotifChargeAdministrative.forEach(element => {
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
        }
    }

    updatePec() {
        this.sinister.statusId = StatusSinister.INPROGRESS;
        const dateSavePec = new Date(this.sinisterPec.declarationDate);
        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateSavePec);
        this.sinisterPec.generatedLetter = false;
        this.sinisterPec.fromDemandeOuverture = false;
        if (this.sinisterPec.pointChoc.id != null) this.pointChoc.id = this.sinisterPec.pointChoc.id;
        this.sinisterPec.pointChoc = this.pointChoc;

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.sinisterPec.assignedToId = this.currentAccount.id;
        });
        this.sinister.sinisterPec = this.sinisterPec;
        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.sinisterPec = this.sinister.sinisterPec;
                this.saveAttachments(this.sinister.sinisterPec.id);
                this.savePhotoPlusPec();
                this.saveTiers(this.sinister.sinisterPec.id, this.tiers);
                this.saveObservations(this.sinister.sinisterPec.id, this.observationss);
                this.updateVehicule();

                let etat = "";
                if (this.sinister.sinisterPec.decision == DecisionPec.CANCELED) { etat = 'Annulé' }
                if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_RESRV) { etat = 'Accepté avec réserves' }
                if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_CHANGE_STATUS) { etat = 'Accepté avec changement de statut' }
                if (this.sinister.sinisterPec.decision == DecisionPec.REFUSED) { etat = 'Refusé' }
                if (this.sinister.sinisterPec.decision == DecisionPec.ACCEPTED) { etat = 'Accepté' }
                this.sinisterPec = this.sinister.sinisterPec;
                if (this.sinisterPec.approvPec = 'APPROVE') {
                    this.sendNotifPecApprove('prestApprouveWithNotification', etat);
                } else {
                    this.sendNotifPecApproveWithModification('prestApprouveWithNotification', etat);
                }

                this.router.navigate(['/sinister']);
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

                let etat = "";
                if (this.sinister.sinisterPec.decision == DecisionPec.CANCELED) { etat = 'Annulé' }
                if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_RESRV) { etat = 'Accepté avec réserves' }
                if (this.sinister.sinisterPec.decision == DecisionPec.ACC_WITH_CHANGE_STATUS) { etat = 'Accepté avec changement de statut' }
                if (this.sinister.sinisterPec.decision == DecisionPec.REFUSED) { etat = 'Refusé' }
                if (this.sinister.sinisterPec.decision == DecisionPec.ACCEPTED) { etat = 'Accepté' }
                this.sinisterPec = this.sinister.sinisterPec;
                if (this.sinisterPec.approvPec = 'APPROVE') {
                    this.sendNotifPecApprove('prestApprouveWithNotification', etat);
                } else {
                    this.sendNotifPecApproveWithModification('prestApprouveWithNotification', etat);
                }

                this.router.navigate(['/sinister']);
            });
        }
    }

    approuveWithModif() {



        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {


                if (this.approveWithModifButton == true) {
                    this.approveWithModifButton = false;
                    /*this.sinisterPec.step = 3;
                      this.sinisterPec.decision = 'INPROGRESS';*/

                    switch (this.sinisterPec.decision) {
                        case 'CANCELED':
                            this.sinister.sinisterPec.decision = DecisionPec.CANCELED;
                            this.sinisterPec.stepId = 9;
                            const dateAnnuleRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateAnnuleRefusePec);
                            break;
                        case 'ACC_WITH_RESRV':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_RESRV;
                            this.sinisterPec.stepId = 12;
                            break;
                        case 'ACC_WITH_CHANGE_STATUS':
                            this.sinister.sinisterPec.decision = DecisionPec.ACC_WITH_CHANGE_STATUS;
                            this.sinisterPec.stepId = 13;
                            break;
                        case 'REFUSED':
                            this.sinister.sinisterPec.decision = DecisionPec.REFUSED;
                            this.sinisterPec.stepId = 10;
                            const dateRefusePec = new Date();
                            this.sinisterPec.annuleRefusDate = this.dateAsYYYYMMDDHHNNSSLDT(dateRefusePec);
                            break;
                        case 'ACCEPTED':
                            this.sinister.sinisterPec.decision = DecisionPec.ACCEPTED;
                            this.sinisterPec.stepId = 11;
                            break;
                    }

                    this.sinisterPec.approvPec = 'APPROVE_WITH_MODIFICATION';
                    this.updatePec();
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

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
            //this.observation.sinisterPecId = this.sinisterPec.id; //update when insert prestation pec
            this.observation.prive = false;
            this.observations.push(this.observation);
            this.observation = new Observation();
        }
    }

    addObservationPec() {
        console.log('Add Observation');
        if (this.observation.commentaire === null || this.observation.commentaire === undefined) {
            this.isCommentError = true;
        } else {
            this.observation.type = TypeObservation.Observation;
            this.observation.userId = this.principal.getAccountId();
            this.observation.firstName = this.principal.getCurrentAccount().firstName;
            this.observation.lastName = this.principal.getCurrentAccount().lastName;
            this.observation.date = new Date();
            //this.observation.sinisterPecId = this.sinisterPec.id;
            this.observation.prive = false;
            this.observationss.push(this.observation);
            //this.observation.date = this.formatEnDate(new Date());
            //this.observationService.create(this.observation).subscribe((res) => {
            //});
            this.observation = new Observation();
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

    deleteObservation(observation) {
        observation.deleted = true;
        this.observations.forEach((item, index) => {
            if (item === observation) this.observations.splice(index, 1);

        });
    }

    prepareEditObservation(observation) {
        this.observation = observation;
        this.isObsModeEdit = true;
    }

    editObservation(observation) {
        this.observation = new Observation();
        this.isObsModeEdit = false;
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
    diffCapDcCapR() {
        if (this.vehiculeContrat.dcCapital !== null && this.vehiculeContrat.dcCapital !== undefined && this.sinisterPec.remainingCapital !== null && this.sinisterPec.remainingCapital !== undefined) {
            if (this.vehiculeContrat.dcCapital < this.sinisterPec.remainingCapital) {
                this.alertService.error('auxiliumApp.sinisterPec.home.diffCapDcCapR', null, null);
            }
        }
    }

    fetchDelegationsByGovernoratePec(id) {

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
    testRefernceSinister() {
        if (this.sinisterPec.companyReference !== null && this.sinisterPec.companyReference !== undefined && this.sinisterPec.companyReference !== "") {
            this.cmpRf = true;
        } else {
            this.cmpRf = false;
        }
    }

    activeRecherche() {
        if (this.sinister.vehicleRegistration !== null && this.sinister.vehicleRegistration !== undefined && this.sinister.vehicleRegistration !== "") {
            this.searchActive = true;
        } else {
            this.searchActive = false;
        }
    }

    trimRegistrationNumberTiers() {
        const strc = this.tier.immatriculation.replace(/\s/g, "");
        this.tier.immatriculation = strc.toUpperCase();
    }
    trimRegistrationNumber() {
        const str = this.sinister.vehicleRegistration.replace(/\s/g, "");
        this.sinister.vehicleRegistration = str.toUpperCase();
    }
    toggleTiers() {
        this.tiersIsActive = !this.tiersIsActive;
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPositionGaId(index: number, item: RefPositionGa) {
        return item.id;
    }

    trackGouvernoratById(index: number, item: Governorate) {
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

    sendNotifPrestationAssistance(typeNotif, sinisterPrestationId) {
        console.log('_________________________________________11');

        if (this.oneClickForButtonAssistance) {
            this.oneClickForButtonAssistance = false;
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {

                    this.userPartnerModeService.findUserPartnerModeByPartner(this.contratAssurance.clientId).subscribe((subRes: ResponseWrapper) => {
                        this.listUserPartnerModesForNotifs = subRes.json;
                        var cache = {};
                        this.listUserPartnerModesForNotifs = this.listUserPartnerModesForNotifs.filter(function (elem) {
                            return cache[elem.userExtraId] ? 0 : cache[elem.userExtraId] = 1;
                        });
                        this.listUserPartnerModesForNotifs.forEach(destNotif => {
                            let notifUser = new NotifAlertUser();
                            this.notification.id = 54;
                            notifUser.destination = destNotif.userExtraId;
                            notifUser.source = usr.id;
                            notifUser.notification = this.notification;
                            notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'sinisterPecId': sinisterPrestationId, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.contratAssurance.clientId });
                            this.listNotifUser.push(notifUser);
                        });

                        this.userExtraService.finUsersByPersonProfil(this.contratAssurance.agenceId, 24).subscribe((userExNotifAgency) => {
                            this.userExNotifAgency = userExNotifAgency.json
                            this.userExNotifAgency.forEach(element => {
                                this.notifUser = new NotifAlertUser();
                                this.notification.id = 54;
                                this.notifUser.source = usr.userId;
                                this.notifUser.destination = element.userId;
                                this.notifUser.notification = this.notification;
                                this.notifUser.entityId = this.sinisterPec.id;
                                this.notifUser.entityName = "SinisterPec";
                                this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'sinisterPecId': sinisterPrestationId, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.contratAssurance.clientId });
                                this.listNotifUser.push(this.notifUser);
                            });
                            this.userExtraService.finUsersByPersonProfil(this.contratAssurance.clientId, 25).subscribe((userExNotifPartner) => {
                                this.userExNotifPartner = userExNotifPartner.json;
                                this.userExNotifPartner.forEach(element => {
                                    this.notifUser = new NotifAlertUser();
                                    this.notification.id = 54;
                                    this.notifUser.source = usr.userId;
                                    this.notifUser.destination = element.userId;
                                    this.notifUser.notification = this.notification;
                                    this.notifUser.entityId = this.sinisterPec.id;
                                    this.notifUser.entityName = "SinisterPec";
                                    this.notifUser.settings = JSON.stringify({ 'typenotification': typeNotif, 'sinisterPecId': sinisterPrestationId, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.contratAssurance.clientId });
                                    this.listNotifUser.push(this.notifUser);
                                });

                                this.notificationAlerteService.create(this.listNotifUser).subscribe((res) => {
                                    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'sinisterPecId': sinisterPrestationId, 'refSinister': this.sinisterPec.reference, 'agenceId': this.contratAssurance.agenceId, 'idPartner': this.contratAssurance.clientId }));

                                });

                            });


                        });
                    });
                });
            });
        }
    }
    ngAfterViewInit(): void {
        this.dtTrigger.next();

    }
    rerender(): void {
        this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }
    ngOnDestroy(): void {
    }

    func(form: NgForm){
       console.log(this.sinisterPrestation);

       console.log(form.value);
    }

}
