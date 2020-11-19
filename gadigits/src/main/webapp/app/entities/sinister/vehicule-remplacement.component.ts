import {
    Component,
    Input,
    OnInit,
    OnDestroy,
    AfterViewInit,
    SimpleChanges,
    OnChanges,
} from "@angular/core";
import { Governorate, GovernorateService } from "../governorate";
import {
    ConfirmationDialogService,
    ResponseWrapper,
    Principal,
} from "../../shared";
import { Delegation, DelegationService } from "../delegation";
import { JhiEventManager, JhiAlertService, JhiDateUtils } from "ng-jhipster";
import {
    ObservationService,
    Observation,
    TypeObservation,
} from "../observation";
import { Sinister } from "./sinister.model";
import { SinisterPrestation } from "./sinister-prestation.model";
import { StatusSinister } from "../../constants/app.constants";
import { SinisterService } from "./sinister.service";
import { ActivatedRoute, Router } from "@angular/router";
import { DateUtils } from "../../utils/date-utils";
import { NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { SinisterPrestationTugComponent } from "./sinister-prestation-tug.component";
import { SiniterPrestationTugPopupService } from "./sinister-prestation-popup.service";
import { Loueur } from "./../loueur/loueur.model";
import { VehiculeLoueurService } from "../vehicule-loueur/vehicule-loueur.service";
import { VehiculeLoueur } from "../vehicule-loueur/vehicule-loueur.model";
import { SinisterPrestationLoueurComponent } from "./sinister-prestation-loueur.component";
import { ControlContainer, NgForm } from "@angular/forms";
import {
    RaisonAssistance,
    RaisonAssistanceService,
} from "../raison-assistance";

@Component({
    selector: "vehicule-remplacement",
    templateUrl: "./vehicule-remplacement.component.html",
    viewProviders: [{ provide: ControlContainer, useExisting: NgForm }],
})
export class VehiculeRemplacementComponent
    implements OnInit, OnDestroy, AfterViewInit, OnChanges {
    @Input() observations: Observation[];
    @Input() sinister: Sinister;
    @Input() sinisterPrestation: SinisterPrestation;

    @Input() isViewVr: boolean;
    @Input() isEditVr: Boolean;
    @Input() isCancelVr: Boolean;
    @Input() isRefuseVr: Boolean;
    @Input() isReopenVr: Boolean;

    @Input() valid: Boolean;

    isObsModeEdit = false;
    observation: Observation = new Observation();
    isCommentError = false;

    governorates: Governorate[];
    delegations: Delegation[];
    delegationsDest: Delegation[];
    gov: boolean = true;
    prestationDateValide = true;
    sinisterSettings: any = {};
    showImmat = false;
    deliveryTime: any;
    acquisitionTime: any;
    expectedReturnTime: any;
    returnTime: any;

    private ngbModalRef: NgbModalRef;
    minExpectedReturnDate: any = new Date();
    minReturnDate: any = new Date();
    reopenGrounds: RaisonAssistance[];

    cancelGrounds: RaisonAssistance[] = [];
    motifRefus: RaisonAssistance[] = [];
    setFirstDriver = false;
    setSecondDriver = false;

    constructor(
        private alertService: JhiAlertService,
        private vehiculeLoueurService: VehiculeLoueurService,

        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        public principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private sinisterService: SinisterService,
        private router: Router,
        private owerDateUtils: DateUtils,
        private siniterPrestationTugPopupService: SiniterPrestationTugPopupService,
        private observationService: ObservationService,
        private raisonAssistanceService: RaisonAssistanceService
    ) {}

    ngOnInit() {
        if (
            this.isViewVr ||
            this.isEditVr ||
            this.isCancelVr ||
            this.isRefuseVr ||
            this.isReopenVr
        ) {
            this.deliveryTime = this.sinisterPrestation.deliveryTime;
            this.expectedReturnTime = this.sinisterPrestation.expectedReturnTime;
            this.returnTime = this.sinisterPrestation.returnTime;
            this.acquisitionTime = this.sinisterPrestation.acquisitionTime;
        }

        if (this.isCancelVr) {
            this.sinisterSettings = {
                shortDuration: true,
                longDuration: true,
                readOnlyPricePerDay: true,
                showCancelGrounds: true,
                showMotifRefus: false,
                showReopenGrounds: false,
            };
        } else if (this.isRefuseVr) {
            this.sinisterSettings = {
                shortDuration: true,
                longDuration: true,
                readOnlyPricePerDay: true,
                showCancelGrounds: false,
                showMotifRefus: true,
                showReopenGrounds: false,
            };
        } else if (this.isReopenVr) {
            this.sinisterSettings = {
                shortDuration: true,
                longDuration: true,
                readOnlyPricePerDay: true,
                showCancelGrounds: false,
                showMotifRefus: true,
                showReopenGrounds: true,
            };
        } else {
            this.sinisterSettings = {
                shortDuration: true,
                longDuration: true,
                readOnlyPricePerDay: true,
                showCancelGrounds: false,
                showMotifRefus: false,
                showReopenGrounds: false,
            };
        }

        this.governorateService.query().subscribe(
            (res: ResponseWrapper) => {
                this.governorates = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.raisonAssistanceService
            .findByStatus(StatusSinister.CANCELED)
            .subscribe((response) => {
                this.cancelGrounds = response.json;
                console.log(this.cancelGrounds);
            });

        this.raisonAssistanceService
            .findByStatus(StatusSinister.CANCELED)
            .subscribe((response) => {
                this.motifRefus = response.json;
                console.log(this.motifRefus);
            });

        this.raisonAssistanceService
            .findByStatus(StatusSinister.REOPENED)
            .subscribe((response) => {
                this.reopenGrounds = response.json;
            });
    }
    ngOnChanges(changes: SimpleChanges) {
        // only run when property "data" changed
        if (changes["sinisterPrestation"]) {
            this.sinisterPrestation = this.sinisterPrestation;
        }

        if (changes["sinister"]) {
            this.sinister = this.sinister;
        }
        if (changes["valid"]) {
            this.valid = this.valid;
        }
        console.log(this.sinisterPrestation);
        if (
            (this.isViewVr || this.isEditVr) &&
            this.sinisterPrestation.deliveryGovernorateId !== null &&
            this.sinisterPrestation.deliveryGovernorateId !== undefined &&
            this.sinisterPrestation.incidentGovernorateId !== null &&
            this.sinisterPrestation.incidentGovernorateId !== undefined
        ) {
            this.deliveryTime = this.sinisterPrestation.deliveryTime;
            this.expectedReturnTime = this.sinisterPrestation.expectedReturnTime;
            this.returnTime = this.sinisterPrestation.returnTime;
            this.acquisitionTime = this.sinisterPrestation.acquisitionTime;

            this.delegationService
                .findByGovernorate(
                    this.sinisterPrestation.deliveryGovernorateId
                )
                .subscribe((delegationsDest: Delegation[]) => {
                    this.delegationsDest = delegationsDest;
                });

            this.delegationService
                .findByGovernorate(
                    this.sinisterPrestation.incidentGovernorateId
                )
                .subscribe((delegations: Delegation[]) => {
                    this.delegations = delegations;
                });
        }
    }
    cancel() {
        this.sinisterPrestation.cancelGroundsId = null;
        this.sinisterSettings.showCancelGrounds = true;
        this.sinisterSettings.showReopenGrounds = false;
        this.sinisterSettings.showMotifRefus = false;

        this.sinisterPrestation.statusId = StatusSinister.CANCELED;
        if (this.isViewVr || this.isRefuseVr || this.isCancelVr) {
            this.router.navigate([
                "prestation-vr/" + this.sinisterPrestation.id + "/cancel",
            ]);
        }
    }

    refuse() {
        this.sinisterPrestation.motifRefusId = null;
        this.sinisterSettings.showMotifRefus = true;
        this.sinisterSettings.showCancelGrounds = false;
        this.sinisterSettings.showReopenGrounds = false;

        this.sinisterPrestation.statusId = StatusSinister.REFUSED;
        if (this.isViewVr || this.isCancelVr || this.isRefuseVr) {
            this.router.navigate([
                "prestation-vr/" + this.sinisterPrestation.id + "/refuse",
            ]);
        }
    }
    reopen() {
        // TODO: cancel a service
        this.sinisterPrestation.reopenGroundsId = null;

        this.sinisterSettings.showReopenGrounds = true;
        this.sinisterSettings.showMotifRefus = false;
        this.sinisterSettings.showCancelGrounds = false;
        this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;
        if (this.isViewVr) {
            this.router.navigate([
                "prestation-vr/" + this.sinisterPrestation.id + "/reopen",
            ]);
        }
    }
    newObservation() {
        if (this.isViewVr) {
            return this.saveObservation();
        } else {
            return this.addObservation();
        }
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
            //this.observation.sinisterPecId = this.sinisterPec.id; //update when insert prestation pec
            this.observation.prive = false;
            this.observations.push(this.observation);
            this.observation = new Observation();
        }
    }
    saveObservation() {
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
            this.observation.sinisterPrestationId = this.sinisterPrestation.id; //update when insert prestation pec
            this.observation.prive = false;
            this.observations.push(this.observation);
            this.observation.date = this.formatEnDate(new Date());
            this.observationService
                .create(this.observation)
                .subscribe((res) => {});
            this.observation = new Observation();
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
    ngAfterViewInit(): void {}

    initPerstation() {
        this.sinisterPrestation.immatriculationVr = null;
        this.sinisterPrestation.pricePerDay = null;
    }

    loadAll() {}

    calculateDays() {
        let deliveryDate, expectedReturnDate;
        if (
            this.sinisterPrestation.deliveryDate !== undefined &&
            this.deliveryTime !== undefined &&
            this.sinisterPrestation.expectedReturnDate !== undefined &&
            this.expectedReturnTime !== undefined
        ) {
            this.sinisterPrestation.deliveryDate.hour = this.deliveryTime.hour;
            this.sinisterPrestation.deliveryDate.minute = this.deliveryTime.minute;
            this.sinisterPrestation.deliveryDate.second = 0;
            deliveryDate = new Date(
                this.owerDateUtils.convertDateTimeToServer(
                    this.sinisterPrestation.deliveryDate,
                    undefined
                )
            );
        }
        if (
            this.sinisterPrestation.expectedReturnDate !== undefined &&
            this.expectedReturnTime !== undefined
        ) {
            this.sinisterPrestation.expectedReturnDate.hour = this.expectedReturnTime.hour;
            this.sinisterPrestation.expectedReturnDate.minute = this.expectedReturnTime.minute;
            this.sinisterPrestation.expectedReturnDate.second = 0;
            expectedReturnDate = new Date(
                this.owerDateUtils.convertDateTimeToServer(
                    this.sinisterPrestation.expectedReturnDate,
                    undefined
                )
            );
        }
        if (deliveryDate !== undefined && expectedReturnDate !== undefined) {
            this.sinisterPrestation.days =
                (expectedReturnDate.getTime() - deliveryDate.getTime()) /
                (1000 * 60 * 60 * 24);
            this.sinisterPrestation.days = +this.sinisterPrestation.days.toFixed(
                3
            );
        }
        this.calculatePriceTtc();
    }

    calculatePriceTtc() {
        if (
            this.sinisterPrestation.days !== null &&
            this.sinisterPrestation.pricePerDay !== null
        ) {
            this.sinisterPrestation.priceTtc =
                this.sinisterPrestation.pricePerDay *
                this.sinisterPrestation.days;

            this.sinisterPrestation.priceTtc = +this.sinisterPrestation.priceTtc.toFixed(
                3
            );
        }
    }
    calculatePricePerDay(id) {
        if (id !== null && id != undefined) {
            this.vehiculeLoueurService
                .find(id)
                .subscribe((vehiculeLoueur: VehiculeLoueur) => {
                    this.sinisterPrestation.pricePerDay =
                        vehiculeLoueur.totalPrix / 30;
                    this.sinisterPrestation.pricePerDay = +this.sinisterPrestation.pricePerDay.toFixed(
                        3
                    );
                    this.calculatePriceTtc();
                });
        }
    }

    validateDeliveryTime() {
        //console.log(this.sinisterPrestation.deliveryDate);
        //console.log(this.sinisterPrestation.expectedReturnDate);
        //console.log(this.deliveryTime);
        this.prestationDateValide = true;

        this.minExpectedReturnDate = {
            year: this.sinisterPrestation.deliveryDate.year,
            month: this.sinisterPrestation.deliveryDate.month,
            day: this.sinisterPrestation.deliveryDate.day + 1,
        };

        if (
            this.sinisterPrestation.deliveryDate === null ||
            this.sinisterPrestation.deliveryDate === undefined ||
            this.sinisterPrestation.expectedReturnDate === null ||
            this.sinisterPrestation.expectedReturnDate === undefined ||
            (this.sinisterPrestation.deliveryDate.day ===
                this.sinisterPrestation.expectedReturnDate.day &&
                this.sinisterPrestation.deliveryDate.month ===
                    this.sinisterPrestation.expectedReturnDate.month &&
                this.sinisterPrestation.deliveryDate.year ===
                    this.sinisterPrestation.expectedReturnDate.year)
        ) {
            // verify time
            if (
                this.deliveryTime !== null &&
                this.deliveryTime != undefined &&
                this.expectedReturnTime !== null &&
                this.expectedReturnTime != undefined &&
                (this.deliveryTime.hour < this.expectedReturnTime.hour ||
                    (this.deliveryTime.hour === this.expectedReturnTime.hour &&
                        this.deliveryTime.minute <
                            this.expectedReturnTime.minute))
            ) {
                this.deliveryTime = undefined;
                this.prestationDateValide = false;

                console.log("invalide");
            }
        }
    }

    validateExpectedReturnTime() {
        // console.log(this.sinisterPrestation.deliveryDate);
        // console.log(this.sinisterPrestation.expectedReturnDate);
        this.prestationDateValide = true;

        if (
            this.sinisterPrestation.deliveryDate === null ||
            this.sinisterPrestation.deliveryDate === undefined ||
            this.sinisterPrestation.expectedReturnDate === null ||
            this.sinisterPrestation.expectedReturnDate === undefined ||
            (this.sinisterPrestation.deliveryDate.day ===
                this.sinisterPrestation.expectedReturnDate.day &&
                this.sinisterPrestation.deliveryDate.month ===
                    this.sinisterPrestation.expectedReturnDate.month &&
                this.sinisterPrestation.deliveryDate.year ===
                    this.sinisterPrestation.expectedReturnDate.year)
        ) {
            // verify time
            if (
                this.deliveryTime !== null &&
                this.deliveryTime != undefined &&
                this.expectedReturnTime !== null &&
                this.expectedReturnTime != undefined &&
                (this.deliveryTime.hour < this.expectedReturnTime.hour ||
                    (this.deliveryTime.hour === this.expectedReturnTime.hour &&
                        this.deliveryTime.minute <
                            this.expectedReturnTime.minute))
            ) {
                this.expectedReturnTime = undefined;
                this.prestationDateValide = false;
                console.log("invalide");
            }
        }
    }
    validateReturnTime() {
        this.prestationDateValide = true;

        if (
            this.sinisterPrestation.acquisitionDate === null ||
            this.sinisterPrestation.acquisitionDate === undefined ||
            this.sinisterPrestation.returnDate === null ||
            this.sinisterPrestation.returnDate === undefined ||
            (this.sinisterPrestation.acquisitionDate.day ===
                this.sinisterPrestation.returnDate.day &&
                this.sinisterPrestation.acquisitionDate.month ===
                    this.sinisterPrestation.returnDate.month &&
                this.sinisterPrestation.acquisitionDate.year ===
                    this.sinisterPrestation.returnDate.year)
        ) {
            // verify time
            if (
                this.acquisitionTime !== null &&
                this.acquisitionTime != undefined &&
                this.returnTime !== null &&
                this.returnTime != undefined &&
                (this.acquisitionTime.hour < this.returnTime.hour ||
                    (this.acquisitionTime.hour === this.returnTime.hour &&
                        this.acquisitionTime.minute < this.returnTime.minute))
            ) {
                this.returnTime = undefined;
                this.prestationDateValide = false;
                console.log("invalide");
            }
        }
    }
    validateAcquisitionTime() {
        this.prestationDateValide = true;

        this.minReturnDate = {
            year: this.sinisterPrestation.acquisitionDate.year,
            month: this.sinisterPrestation.acquisitionDate.month,
            day: this.sinisterPrestation.acquisitionDate.day + 1,
        };
        console.log(this.minReturnDate);

        if (
            this.sinisterPrestation.acquisitionDate === null ||
            this.sinisterPrestation.acquisitionDate === undefined ||
            this.sinisterPrestation.returnDate === null ||
            this.sinisterPrestation.returnDate === undefined ||
            (this.sinisterPrestation.acquisitionDate.day ===
                this.sinisterPrestation.returnDate.day &&
                this.sinisterPrestation.acquisitionDate.month ===
                    this.sinisterPrestation.returnDate.month &&
                this.sinisterPrestation.acquisitionDate.year ===
                    this.sinisterPrestation.returnDate.year)
        ) {
            // verify time
            if (
                this.acquisitionTime !== null &&
                this.acquisitionTime != undefined &&
                this.returnTime !== null &&
                this.returnTime != undefined &&
                (this.acquisitionTime.hour < this.returnTime.hour ||
                    (this.acquisitionTime.hour === this.returnTime.hour &&
                        this.acquisitionTime.minute < this.returnTime.minute))
            ) {
                this.acquisitionTime = undefined;
                this.prestationDateValide = false;

                console.log("invalide");
            }
        }
    }
    selectLoueur() {
        this.ngbModalRef = this.siniterPrestationTugPopupService.openLoueurModal(
            SinisterPrestationLoueurComponent as Component,
            this.sinisterPrestation,
            this.sinisterPrestation.shortDuration,
            this.sinisterPrestation.longDuration
        );
        this.ngbModalRef.result.then(
            (result) => {
                if (result !== null && result !== undefined) {
                    if (this.sinisterPrestation.shortDuration) {
                        this.sinisterPrestation.loueurId = result.id;
                        this.sinisterPrestation.loueurLabel =
                            result.raisonSociale;
                    }
                    if (this.sinisterPrestation.longDuration) {
                        this.sinisterPrestation.loueurId = result.loueurId;
                        this.sinisterPrestation.loueurLabel =
                            result.loueurLabel;
                        this.sinisterPrestation.immatriculationVr =
                            result.immatriculation;
                        console.log(this.sinisterPrestation.days);

                        this.calculatePricePerDay(result.id);
                    }
                }
                this.ngbModalRef = null;
            },
            (reason) => {
                // TODO: print error message
                this.ngbModalRef = null;
            }
        );
    }
    /**
     * Get city list by gouvernorat
     * @param id
     */
    fetchDelegationsByGovernorate(id, initFlag) {
        this.delegationService
            .findByGovernorate(id)
            .subscribe((delegations: Delegation[]) => {
                this.delegations = delegations;
                if (delegations && delegations.length > 0 && initFlag) {
                    this.sinisterPrestation.incidentLocationId =
                        delegations[0].id;
                }
            });

        this.gov = false;
    }

    /**
     * Get city list by gouvernorat
     * @param id
     */
    fetchDestDelegationsByGovernorate(id, initFlag) {
        this.delegationService
            .findByGovernorate(id)
            .subscribe((delegations: Delegation[]) => {
                this.delegationsDest = delegations;
                if (delegations && delegations.length > 0 && initFlag) {
                    this.sinisterPrestation.deliveryLocationId =
                        delegations[0].id;
                }
            });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {}

    changeIsGenerated(etat) {
        this.sinisterPrestation.isGenerated = etat;
    }
    save() {
        this.sinister.statusId = StatusSinister.INPROGRESS;
        this.sinister.governorateId = this.sinisterPrestation.incidentGovernorateId;
        this.sinister.locationId = this.sinisterPrestation.incidentLocationId;
        if (
            this.sinisterPrestation.statusId === null ||
            this.sinisterPrestation.statusId === undefined
        ) {
            this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;
        }
        if (
            this.sinisterPrestation.returnDate !== null &&
            this.sinisterPrestation.returnDate !== undefined
        ) {
            this.sinisterPrestation.statusId = StatusSinister.CLOSED;
        }

        if (this.isCancelVr) {
            this.sinisterPrestation.statusId = StatusSinister.CANCELED;
            this.sinisterPrestation.reopenGroundsId = null;
            this.sinisterPrestation.motifRefusId = null;
        }
        if (this.isRefuseVr) {
            this.sinisterPrestation.statusId = StatusSinister.REFUSED;
            this.sinisterPrestation.cancelGroundsId = null;
            this.sinisterPrestation.reopenGroundsId = null;
        }
        if (this.isReopenVr) {
            this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;
            this.sinisterPrestation.cancelGroundsId = null;
            this.sinisterPrestation.motifRefusId = null;
        }
        this.sinisterPrestation.observations = this.observations;
        this.sinisterPrestation.serviceTypeId = 12;
        const copy: SinisterPrestation = Object.assign(
            {},
            this.sinisterPrestation
        );
        if (
            this.sinisterPrestation.deliveryDate !== undefined &&
            this.deliveryTime !== undefined
        ) {
            this.sinisterPrestation.deliveryDate.hour = this.deliveryTime.hour;
            this.sinisterPrestation.deliveryDate.minute = this.deliveryTime.minute;
            this.sinisterPrestation.deliveryDate.second = 0;
            copy.deliveryDate = this.owerDateUtils.convertDateTimeToServer(
                this.sinisterPrestation.deliveryDate,
                undefined
            );
        }
        if (
            this.sinisterPrestation.expectedReturnDate !== undefined &&
            this.expectedReturnTime !== undefined
        ) {
            this.sinisterPrestation.expectedReturnDate.hour = this.expectedReturnTime.hour;
            this.sinisterPrestation.expectedReturnDate.minute = this.expectedReturnTime.minute;
            this.sinisterPrestation.expectedReturnDate.second = 0;
            copy.expectedReturnDate = this.owerDateUtils.convertDateTimeToServer(
                this.sinisterPrestation.expectedReturnDate,
                undefined
            );
        }
        if (
            this.sinisterPrestation.acquisitionDate !== undefined &&
            this.acquisitionTime !== undefined
        ) {
            this.sinisterPrestation.acquisitionDate.hour = this.acquisitionTime.hour;
            this.sinisterPrestation.acquisitionDate.minute = this.acquisitionTime.minute;
            this.sinisterPrestation.acquisitionDate.second = 0;
            copy.acquisitionDate = this.owerDateUtils.convertDateTimeToServer(
                this.sinisterPrestation.acquisitionDate,
                undefined
            );
        }
        if (
            this.sinisterPrestation.returnDate !== undefined &&
            this.returnTime !== undefined
        ) {
            this.sinisterPrestation.returnDate.hour = this.returnTime.hour;
            this.sinisterPrestation.returnDate.minute = this.returnTime.minute;
            this.sinisterPrestation.returnDate.second = 0;
            copy.returnDate = this.owerDateUtils.convertDateTimeToServer(
                this.sinisterPrestation.returnDate,
                undefined
            );
        }
        if (this.isEditVr) {
            for (let i = 0; i < this.sinister.prestations.length; i++) {
                if (
                    this.sinisterPrestation.id ===
                    this.sinister.prestations[i].id
                ) {
                    this.sinister.prestations.splice(i, 1);
                }
            }
        }
        this.sinister.prestations.push(copy);

        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir enregistrer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                if (confirmed) {
                    if (
                        this.sinister.id !== null &&
                        this.sinister.id !== undefined
                    ) {
                        this.sinisterService
                            .update(this.sinister)
                            .subscribe((resSinister) => {
                                this.sinister = resSinister;

                                this.router.navigate(["/sinister"]);
                            });
                    } else {
                        this.sinisterService
                            .create(this.sinister)
                            .subscribe((resSinister) => {
                                this.sinister = resSinister;

                                this.router.navigate(["/sinister"]);
                            });
                    }
                }
            });
    }
}
