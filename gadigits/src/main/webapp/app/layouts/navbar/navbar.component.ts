import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModalRef, NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { JhiLanguageService } from "ng-jhipster";
import { ProfileService } from "../profiles/profile.service";
import {
    JhiLanguageHelper,
    Principal,
    LoginModalService,
    LoginService,
    User,
    Account,
} from "../../shared";
import { VERSION } from "../../app.constants";
import { JhiEventManager } from "ng-jhipster";
import { Authorities, NotificationAccord } from "../../constants/app.constants";
import { NavbarService } from "./navbar.service";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import { UserExtraService, UserExtra } from "../../entities/user-extra";
import { UserPartnerMode } from "../../entities/user-partner-mode";
import { NotifAlertUserService } from "../../entities/notif-alert-user/notif-alert-user.service";
import { NotifAlertUser } from "../../entities/notif-alert-user/notif-alert-user.model";
import { ApecService } from "../../entities/apec/apec.service";
import { SinisterPecService, SinisterPec } from "../../entities/sinister-pec";
import { Observable, Subscription } from "rxjs";
import { fromEvent } from "rxjs/observable/fromEvent";
import { AgencyPopupService } from "../../entities/agency";
import { CnxErrorComponent } from "../../entities/contrat-assurance";

@Component({
    selector: "jhi-navbar",
    templateUrl: "./navbar.component.html",
    providers: [NavbarService],
})
export class NavbarComponent implements OnInit {
    currentAccount: any;
    authorities: any;
    isAdmin: any;
    isActiveSidebar = true;
    account: Account;
    user: User;
    userExtra: UserExtra;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    profilName: string;
    version: string;
    @ViewChild("sideNavbar") sidebar;
    @ViewChild("wholePage") page;
    notifications: NotifAlertUser[] = [];
    alerts: NotifAlertUser[] = [];
    ws: any;
    nbreNotif = 0;
    messageParsed: any;
    usersPartnerModes: UserPartnerMode[];
    isMode: boolean = false;
    idPartner;
    isPartner: boolean = false;
    status = "ONLINE";
    typeNotif = "N";
    typeAlert = "A";
    isConnected = true;
    public onlineEvent: Observable<Event>;
    public offlineEvent: Observable<Event>;
    public subscriptions: Subscription[] = [];
    public connectionStatusMessage: string;
    public connectionStatusMessage1: string;
    public connectionStatus: string;
    private ngbModalRef: NgbModalRef;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        public principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private userExtraService: UserExtraService,
        private router: Router,
        private navbarService: NavbarService,
        private notifAlertUserService: NotifAlertUserService,
        private apecService: ApecService,
        private sinisterPecService: SinisterPecService,
        private agencyPopupService: AgencyPopupService
    ) {
        this.version = VERSION ? "v" + VERSION : "";
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.onlineEvent = fromEvent(window, "online");
        this.offlineEvent = fromEvent(window, "offline");
        this.subscriptions.push(
            this.onlineEvent.subscribe((event) => {
                this.connectionStatusMessage = "";
                this.connectionStatusMessage1 = "";
                this.connectionStatus = "online";
                console.log("on");
                this.ngbModalRef.close();
            })
        );
        this.subscriptions.push(
            this.offlineEvent.subscribe((e) => {
                //    this.connectionStatusMessage = 'Votre connexion a été interrompue !';
                //     this.connectionStatusMessage1 = ' Veuillez garder la fenêtre ouverte pour ne pas perdre vos données. ';

                this.connectionStatus = "offline";
                this.ngbModalRef = this.agencyPopupService.openpopup(
                    CnxErrorComponent as Component
                );
                console.log("off");
            })
        );

        this.isAdminLogin();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.connect();
        this.principal.newInitNavBar();
    }

    connect() {
        //connect to stomp where stomp endpoint is exposed
        let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
        //let sockets = new WebSocket("ws://197.13.12.139:8099/my-ws/websocket");
        this.ws = Stomp.over(sockets);
        this.ws.connect(
            {},
            this.onConnected.bind(this),
            this.onError.bind(this)
        );
    }
    onError(error) {
        console.log(error);
    }
    onConnected() {
        this.ws.subscribe("/topics/event", (message) => {
            if (message.body) {
                console.log("message body");
                const messageParsed = JSON.parse(message.body)[
                    "description"
                ].replace(/]/g, '"');
                let obj = JSON.parse(messageParsed);
                if (
                    obj.typenotification !== "gtEstimateSendEvent" &&
                    obj.typenotification !== "fakeEventGtEstimateSendEvent"
                ) {
                    console.log("testTypeNotification " + obj.typenotification);

                    if (
                        obj.typenotification != "annulation notification" &&
                        obj.typenotification != "AnnulerNotification" &&
                        obj.typenotification != "null" &&
                        obj.typenotification !=
                        "notificationAfficheConfirmArriv"
                    ) {
                        if (obj.gageo !== "null" && obj.gageo !== null) {
                            this.notifications.push(obj);
                            this.nbreNotif = this.notifications.length;
                        }

                        // nombre de notif
                    }
                    // Filter notifs or alert
                    this.alerts = [];
                    this.principal.alerts = [];
                    //this.notifications = [];
                    this.principal.notifications = [];
                    this.notifAlertUserService
                        .viewQuery(this.typeNotif)
                        .subscribe((resViewNotif) => {
                            this.principal.notifications = resViewNotif;
                            this.principal.notifications.sort(function (a, b) {
                                return b.id - a.id;
                            });
                            var cache = {};
                            this.principal.notifications = this.principal.notifications.filter(
                                function (elem) {
                                    return cache[elem.id]
                                        ? 0
                                        : (cache[elem.id] = 1);
                                }
                            );
                            this.principal.alerts = [];
                        });
                }
            }
        });
    }

    parseJson(json: any) {

        return JSON.parse(json);
    }

    routeNotif(notif) {
        console.log("settings notif string*--*-*" + notif.settings);
        var obj = JSON.parse(notif.settings);
        console.log("id apec objjjj*--*-*" + obj.sinisterPecId);
        if (obj.sinisterPecId != null && obj.sinisterPecId != undefined) {
            if (obj.typenotification == "notifSendDemandPec") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/sinister-pec/consulter/" + obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "traiterDemandePec") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "sinister-pec-reprise-in-progress/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "notifPrestEnCours") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "sinister-pec-reprise-to-approuve/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "prestApprouve") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            if (obj.etatPrestation == "Annulé") {
                                this.spliceNotifFromListNotifs(notif);
                                this.router.navigate([
                                    "/sinister-pec-reprise-canceled/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (
                                obj.etatPrestation == "Accepté avec réserves"
                            ) {
                                this.router.navigate([
                                    "/sinister-pec-reprise-accepted-with-reserve/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (
                                obj.etatPrestation ==
                                "Accepté avec changement de statut"
                            ) {
                                this.router.navigate([
                                    "/sinister-pec-reprise-accepted-with-change-status/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (obj.etatPrestation == "Refusé") {
                                this.spliceNotifFromListNotifs(notif);
                                this.router.navigate([
                                    "/sinister-pec-reprise-refused/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (obj.etatPrestation == "Accepté") {
                                this.router.navigate([
                                    "/sinister-pec-affectation-reparateur/" +
                                    obj.sinisterPecId,
                                ]);
                            }
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification == "prestApprouveWithNotification"
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            if (obj.etatPrestation == "Annulé") {
                                this.spliceNotifFromListNotifs(notif);
                                this.router.navigate([
                                    "/sinister-pec-reprise-canceled/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (
                                obj.etatPrestation == "Accepté avec réserves"
                            ) {
                                this.router.navigate([
                                    "/sinister-pec-reprise-accepted-with-reserve/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (
                                obj.etatPrestation ==
                                "Accepté avec changement de statut"
                            ) {
                                this.router.navigate([
                                    "/sinister-pec-reprise-accepted-with-change-status/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (obj.etatPrestation == "Refusé") {
                                this.spliceNotifFromListNotifs(notif);
                                this.router.navigate([
                                    "/sinister-pec-reprise-refused/" +
                                    obj.sinisterPecId,
                                ]);
                            } else if (obj.etatPrestation == "Accepté") {
                                this.router.navigate([
                                    "/sinister-pec-affectation-reparateur/" +
                                    obj.sinisterPecId,
                                ]);
                            }
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "PrestationApprouvAvecModif") {
                this.router.navigate([
                    "/sinister-pec-reprise-to-approuve/" + obj.sinisterPecId,
                ]);
            } else if (
                obj.typenotification ==
                "prestApprouveWithNotificationInformative"
            ) {
                //this.router.navigate(['/sinister-pec-reprise-accepted-with-change-status/' + obj.sinisterPecId]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "accepteModifStatus") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/sinister-pec-affectation-reparateur/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "pecReserveLifted") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "sinister-pec-reprise-in-progress/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "refusModifStatus") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/sinister-pec-reprise-in-progress/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "reouvertureSinisterPec") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            if (resPec.fromDemandeOuverture) {
                                this.router.navigate([
                                    "/sinister-pec-agent-new/" +
                                    obj.sinisterPecId,
                                ]);
                            } else {
                                this.router.navigate([
                                    "/acueilpriseencharge/" +
                                    obj.sinisterPecId,
                                ]);
                            }
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "affectation Reparateur") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        console.log(
                            "logggggggggg too pec idd 1step" + obj.stepPecId
                        );
                        console.log(
                            "logggggggggg too pec idd 2step" + resPec.stepId
                        );
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/devis-new/" + obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (obj.typenotification == "Mission annuler") {
                this.router.navigate(["/expert-opinion"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "Missionner un expert") {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        this.router.navigate([
                            "/devis-missionary-expert/" + obj.sinisterPecId,
                        ]);

                        this.spliceNotifFromListNotifs(notif);
                    });
            } else if (obj.typenotification == "nouvelle mission") {
                this.router.navigate(["/expert-opinion/" + obj.sinisterPecId]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == "AccordPourReparationDevisCompl"
            ) {
                this.router.navigate([
                    "/confirmation-devis-complementary/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "DevisComplemantaryRealize") {
                this.router.navigate([
                    "/verification-devis/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "Avis Expert") {
                this.router.navigate(["/expert-opinion/" + obj.sinisterPecId]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "OK pour démontage") {
                this.router.navigate(["/demontage/" + obj.sinisterPecId]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "Vérification") {
                this.router.navigate([
                    "verification-devis/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "Validation Devis") {
                this.router.navigate(["validation-devis/" + obj.sinisterPecId]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification ==
                "Accord pour réparation avec modification"
            ) {
                this.router.navigate([
                    "/confirmation-devis/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == "Devis Confirmé par le réparateur"
            ) {
                if (
                    obj.primaryQuotationId != null &&
                    obj.primaryQuotationId != undefined
                ) {
                    this.router.navigate([
                        "devis/" +
                        obj.sinisterPecId +
                        "/edit/" +
                        obj.primaryQuotationId,
                    ]);
                }
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "Rectification Devis") {
                if (
                    obj.primaryQuotationId != null &&
                    obj.primaryQuotationId != undefined
                ) {
                    this.router.navigate([
                        "/confirmation-devis/" + obj.sinisterPecId,
                    ]);
                }
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "Accord pour réparation Compl") {
                this.router.navigate([
                    "/confirmation-devis/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "ACCORD_POUR_REPARATION") {
                this.apecService
                    .findByQuotation(obj.quotationId)
                    .subscribe((apec) => {
                        if (obj.accord == "approuveapec") {
                            this.router.navigate([
                                "/apec/" + apec.id + "/edit/approuv",
                            ]);
                            this.spliceNotifFromListNotifs(notif);
                        }
                        if (obj.accord == "validationapec") {
                            this.router.navigate([
                                "/apec/" + apec.id + "/edit/valid",
                            ]);
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_ACCORD_FAVORABLE
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/apec/" + obj.idApec + "/edit/valid",
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_MODIF
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/apec/" + obj.idApec + "/edit/modif",
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_ACCORD_FAVORABLE_WITH_RESERV
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/apec/" + obj.idApec + "/edit/valid",
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_ACCORD_DEFAVORABLE
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/sinister-pec-reprise-confirm-canc-Pec/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification == NotificationAccord.NOTIF_ACCORD_VALIDE
            ) {
                this.router.navigate([
                    "/apec/" + obj.idApec + "/edit/valid-part",
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_NON_ACCORD_VALIDE
            ) {
                this.router.navigate([
                    "/sinister-pec-reprise-confirm-canc-Pec/" +
                    obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == NotificationAccord.NOTIF_ASSURE_ACCEPTED
            ) {
                //this.router.navigate(['/apec/' + obj.idApec + '/edit/imprimer']);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == NotificationAccord.NOTIF_ASSURE_REFUSED
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/sinister-pec-reprise-confirm-refus-Pec/" +
                                obj.sinisterPecId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_ASSURE_ACCEPTED_WITH_P_GEN
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "devis/" +
                                obj.sinisterPecId +
                                "/edit/" +
                                obj.quotationId,
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });

                //this.router.navigate(['/devis/' + obj.sinisterPecId + '/edit/' + obj.quotationId]);
            } else if (
                obj.typenotification ==
                NotificationAccord.NOTIF_ASSURE_INSTANCE_CONFIRM
            ) {
                this.sinisterPecService
                    .findPrestationPec(obj.sinisterPecId)
                    .subscribe((resPec: SinisterPec) => {
                        if (obj.stepPecId == resPec.stepId) {
                            this.router.navigate([
                                "/apec/" + obj.idApec + "/edit/approuv",
                            ]);
                        } else {
                            this.spliceNotifFromListNotifs(notif);
                        }
                    });
            } else if (
                obj.typenotification ==
                "l’accord de prise en charge signé par l’assuré a été rattaché"
            ) {
                this.router.navigate(["/facturation-devis-complementaire"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "validationDevisNonConfirmer") {
                if (obj.quotationId != null && obj.quotationId != undefined) {
                    this.router.navigate([
                        "devis/" +
                        obj.sinisterPecId +
                        "/edit/" +
                        obj.quotationId,
                    ]);
                }
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "validDevisConfirmExpertNull") {
                this.router.navigate([
                    "/devis-missionary-expert/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == "validDevisConfirmExpertNotNull"
            ) {
                this.router.navigate(["/apec/" + obj.idApec + "/edit/valid"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "NotifSoumettreDevis") {
                this.router.navigate(["validation-devis/" + obj.sinisterPecId]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "notifAssistanceRespPec") {
                this.router.navigate([
                    "/prestation/" + obj.sinisterPecId + "/view",
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "imprimAccord") {
                this.router.navigate(["/signature-accord"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "DevisComplementaireAnnule") {
                this.router.navigate(["/facturation-devis-complementaire"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "confirmRefusWthRespPec") {
                this.router.navigate([
                    "/sinister-pec-reprise-refused/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "imprime") {
                this.router.navigate([
                    "/acueilpriseencharge/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == "devisComplementaireConfirmeValid"
            ) {
                this.router.navigate(["/apec/" + obj.idApec + "/edit/valid"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (
                obj.typenotification == "devisComplementaireConfirmeApprouve"
            ) {
                this.router.navigate(["/apec/" + obj.idApec + "/edit/approuv"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "notifConformOriginalsPrinted") {
                this.router.navigate(["/signature-bon-sortie"]);
                this.spliceNotifFromListNotifs(notif);
            } else if (obj.typenotification == "modifStatusChangementCharge") {
                this.router.navigate([
                    "/acueilpriseencharge/" + obj.sinisterPecId,
                ]);
                this.spliceNotifFromListNotifs(notif);
            } else {
                this.spliceNotifFromListNotifs(notif);
            }
        }
    }

    spliceNotifFromListNotifs(notif) {
        var pos = this.principal.notifications.indexOf(notif);
        this.principal.notifications.splice(pos, 1);
        notif.read = true;
        this.notifAlertUserService.update(notif).subscribe((res) => { });
    }

    detailsSinistre(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.idPrest != null && notif.idPrest != undefined) {
            this.router.navigate([
                "../../prestation/" + notif.idPrest + "/edit",
            ]);
        }
    }

    notifNoTug(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        this.router.navigate(["../../ga-geo"]);
    }
    showHideSidebar(e) {
        this.navbarService.showHideSidebar(e);
    }
    // add by Helmi  Rahali
    affectationPecReparateur(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(["/devis-new/" + notif.sinisterPecId]);
        }
    }
    annulationAffectationPecReparateur(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
    }
    verificationDevis(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(["verification-devis/", notif.sinisterPecId]);
        }
    }
    missionnerExpert(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "/devis-missionary-expert/" + notif.sinisterPecId,
            ]);
        }
    }
    validationDevis(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(["/confirmation-devis/", notif.sinisterPecId]);
        }
    }
    newMissionExpert(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(["/expert-opinion/", notif.sinisterPecId]);
        }
    }
    cancelMissionExpert(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        this.router.navigate(["/expert-opinion"]);
    }
    accordtoRepair(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        /* if (notif.idPrest != null && notif.idPrest != undefined) {
             this.router.navigate(['../../prestation/' + notif.idPrest + '/edit']);
         }*/
    }
    accordtoRepairWithUpdate(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(["/confirmation-devis/", notif.sinisterPecId]);
        }
    }
    cancelFollowingExpertDecision(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (
            notif.sinisterPecId != null &&
            notif.sinisterPecId != undefined &&
            notif.primaryQuotationId != null &&
            notif.primaryQuotationId != undefined
        ) {
            this.router.navigate([
                "devis/" +
                notif.sinisterPecId +
                "/edit/" +
                notif.primaryQuotationId,
            ]);
        }
    }

    quotationNotConfirmedByRepair(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (
            notif.sinisterPecId != null &&
            notif.sinisterPecId != undefined &&
            notif.primaryQuotationId != null &&
            notif.primaryQuotationId != undefined
        ) {
            this.router.navigate([
                "devis/" +
                notif.sinisterPecId +
                "/edit/" +
                notif.primaryQuotationId,
            ]);
        }
    }
    quotationConfirmedByRepair(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (
            notif.sinisterPecId != null &&
            notif.sinisterPecId != undefined &&
            notif.primaryQuotationId != null &&
            notif.primaryQuotationId != undefined
        ) {
            this.router.navigate([
                "devis/" +
                notif.sinisterPecId +
                "/edit/" +
                notif.primaryQuotationId,
            ]);
        }
    }

    demontage(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(["/demontage/", notif.sinisterPecId]);
        }
    }

    // Traitement Notifs Accord
    accordFavorable(notif) {
        console.log("settings notif string*--*-*" + notif.settings);
        var obj = JSON.parse(notif.settings);
        console.log("id apec objjjj*--*-*" + obj.idApec);
        if (obj.idApec != null && obj.idApec != undefined) {
            this.router.navigate(["/apec/" + obj.idApec + "/edit/valid"]);
        }
    }

    //med

    envoyerDemandePec(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "/sinister-pec/consulter/" + notif.sinisterPecId,
            ]);
        }
    }

    notifPrestEnCours(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "sinister-pec-reprise-to-approuve/" + notif.sinisterPecId,
            ]);
        }
    }

    traiterDemandePec(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "/sinister-pec-reprise-in-progress/" + notif.sinisterPecId,
            ]);
        }
    }

    approveSinisterPec(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            if (notif.etatPrestation == "Annulé") {
                this.router.navigate([
                    "/sinister-pec-reprise-canceled/" + notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Accepté avec réserves") {
                this.router.navigate([
                    "/sinister-pec-reprise-accepted-with-reserve/" +
                    notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Accepté avec changement de statut") {
                this.router.navigate([
                    "/sinister-pec-reprise-accepted-with-change-status/" +
                    notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Refusé") {
                this.router.navigate([
                    "/sinister-pec-reprise-refused/" + notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Accepté") {
                this.router.navigate([
                    "/sinister-pec-affectation-reparateur/" +
                    notif.sinisterPecId,
                ]);
            }
        }
    }

    approveWithModifSinisterPec(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            if (notif.etatPrestation == "Annulé") {
                this.router.navigate([
                    "/sinister-pec-reprise-canceled/" + notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Accepté avec réserves") {
                this.router.navigate([
                    "/sinister-pec-reprise-accepted-with-reserve/" +
                    notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Accepté avec changement de statut") {
                this.router.navigate([
                    "/sinister-pec-reprise-accepted-with-change-status/" +
                    notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Refusé") {
                this.router.navigate([
                    "/sinister-pec-reprise-refused/" + notif.sinisterPecId,
                ]);
            }
            if (notif.etatPrestation == "Accepté") {
                this.router.navigate([
                    "/sinister-pec-affectation-reparateur/" +
                    notif.sinisterPecId,
                ]);
            }
        }
    }

    repriseSinisterPec(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "/sinister-pec-reprise-in-progress/" + notif.sinisterPecId,
            ]);
        }
    }

    refuserChangementStatus(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "/sinister-pec-reprise-in-progress/" + notif.sinisterPecId,
            ]);
        }
    }

    accepterChangementStatus(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate([
                "/sinister-pec-affectation-reparateur/" + notif.sinisterPecId,
            ]);
        }
    }

    modificationCharge(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        /*if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(['/sinister-pec-affectation-reparateur/' + notif.sinisterPecId ]);
        }*/
    }

    annulerPrest(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        /*if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(['/sinister-pec-affectation-reparateur/' + notif.sinisterPecId ]);
        }*/
    }

    refusPrest(notif) {
        var pos = this.notifications.indexOf(notif);
        this.notifications.splice(pos, 1);
        this.nbreNotif = this.notifications.length;
        /*if (notif.sinisterPecId != null && notif.sinisterPecId != undefined) {
            this.router.navigate(['/sinister-pec-affectation-reparateur/' + notif.sinisterPecId ]);
        }*/
    }

    // Traitement Notifs Accord
    accordValide(notif) {
        console.log("settings notif string*--*-*" + notif.settings);
        var obj = JSON.parse(notif.settings);
        console.log("id apec objjjj*--*-*" + obj.idApec);
        if (obj.idApec != null && obj.idApec != undefined) {
            this.router.navigate(["/apec/" + obj.idApec + "/edit/valid-part"]);
        }
    }
    accordValideAssure(notif) {
        console.log("settings notif string*--*-*" + notif.settings);
        var obj = JSON.parse(notif.settings);
        console.log("id apec objjjj*--*-*" + obj.idApec);
        if (obj.idApec != null && obj.idApec != undefined) {
            this.router.navigate(["/apec/" + obj.idApec + "/edit/imprimer"]);
        }
    }

    alertClick(alert) {
        console.log("settings notif string*--*-*" + alert.settings);
        var obj = JSON.parse(alert.settings);
        console.log("id apec objjjj*--*-*" + obj.idApec);
        if (obj.idPec != null && obj.idPec != undefined) {
            //aprouver
            if (obj.step == 30) {
                this.router.navigate([
                    "/sinister-pec-reprise-to-approuve/" + obj.idPec,
                ]);
            }
            //reprise
            else if (obj.step == 9 && obj.step1 == 10) {
                this.router.navigate([
                    "/sinister-pec-reprise-refused-canceled/" + obj.idPec,
                ]);
            }
            // validation   du modification de status
            else if (obj.step == 13) {
                this.router.navigate([
                    "/sinister-pec-reprise-change-status/" + obj.idPec,
                ]);
            }
            // livéé de reserve
            else if (obj.step == 12) {
                this.router.navigate([
                    "/sinister-pec-reprise-accepted-with-reserve/" + obj.idPec,
                ]);
            }
            // encours de traitement
            else if (obj.step == 3) {
                this.router.navigate([
                    "/sinister-pec-reprise-in-progress/" + obj.idPec,
                ]);
            }
        }
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe("authenticationSuccess", (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.currentAccount = account;
                this.userExtraService
                    .finPersonneByUser(this.currentAccount.id)
                    .subscribe((res) => {
                        this.userExtra = res;
                        this.usersPartnerModes = this.userExtra.userPartnerModes;
                    });
                if (this.principal.hasAuthority(Authorities.AGCOMPAGNIE)) {
                    this.profilName = "Compagnie";
                } else if (
                    this.principal.hasAuthority(Authorities.GESTIONNAIRE)
                ) {
                    this.profilName = "Gestionnaire";
                } else if (this.principal.hasAuthority(Authorities.AGGENERAL)) {
                    this.profilName = "Agent Général";
                } else if (this.principal.hasAuthority(Authorities.CCELLULE)) {
                    this.profilName = "C. Cellule";
                } else if (this.principal.hasAuthority(Authorities.ADMIN)) {
                    this.profilName = "Admin";
                } else if (this.principal.hasAuthority(Authorities.EXPERT)) {
                    this.profilName = "Expert";
                } else if (
                    this.principal.hasAuthority(Authorities.REPARATEUR)
                ) {
                    this.profilName = "Réparateur";
                } else if (
                    this.principal.hasAuthority(Authorities.RESPONSABLE)
                ) {
                    this.profilName = "Responsable";
                }
            });
        });
    }
    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }
    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }
    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
    login() {
        this.loginModalService.open();
    }
    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        localStorage.setItem('logout-event', 'logout' + Math.random());
        this.router.navigate(["/login"]);
    }
    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }
    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
    /**
     * Test if user is admin
     */
    isAdminLogin() {
        this.isAdmin = false;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            if (account !== null && account !== undefined) {
                this.authorities = this.currentAccount.authorities;
                const indexAdmin = this.authorities.indexOf("ROLE_ADMIN");
                if (indexAdmin !== -1) {
                    this.isAdmin = true;
                }
            }
        });
    }
}
