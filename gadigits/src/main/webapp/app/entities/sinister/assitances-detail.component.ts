import { Delegation } from './../delegation/delegation.model';
import { Governorate } from './../governorate/governorate.model';
import { DelegationService } from './../delegation/delegation.service';
import { GovernorateService } from './../governorate/governorate.service';
import { DateUtils } from './../../utils/date-utils';
import { RefTarifService } from './../ref-tarif/ref-tarif.service';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { Component, OnInit, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { SinisterService } from './sinister.service';
import { VehiculeAssure } from '../vehicule-assure';
import { ContratAssurance, ContratAssuranceService, HistoryAssistanceComponent } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';
import { RefTypeServiceService } from '../ref-type-service';
import { ResponseWrapper, Principal, ConfirmationDialogService } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { StatusSinister, TypeService, GaDatatable } from '../../constants/app.constants';
import { SinisterPrestation } from './sinister-prestation.model';
import { RaisonAssistance, RaisonAssistanceService } from '../raison-assistance';
import { Observation, TypeObservation, ObservationService } from '../observation';
import { SinisterPec, SinisterPecService, SinisterPecPopupService } from '../sinister-pec';
import { observableToBeFn } from 'rxjs/testing/TestScheduler';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPrestationService } from './sinister-prestation.service';
import { NaturePanne } from '../ref-nature-panne/nature-panne.model';
import { NaturePanneService } from '../ref-nature-panne/nature-panne.service';

@Component({
    selector: 'jhi-sinister-prestation-detail',
    templateUrl: './assitances-detail.component.html'
})
export class AssitancesDetailComponent implements OnInit {
    private ngbModalRef: NgbModalRef;

    contratAssurance: ContratAssurance = new ContratAssurance();
    vehiculeContrat: VehiculeAssure = new VehiculeAssure();
    insured: Assure = new Assure();
    sinister: Sinister = new Sinister();
    sinisterPrestation: SinisterPrestation = new SinisterPrestation();
    isEditMode = false; // Siniter mode edit
    isSaving = false;
    isServiceTypeSelected = false; // Service type selected
    isPrestationEdit = false; // Prestion form printed
    isPrestationEditMode = false; // Sinister prestion mode edit
    isServiceGrutage = false;
    governorates: Governorate[];
    cities: Delegation[];
    citiesDest: Delegation[];
    serviceTypes: RefTypeService[];
    cancelGrounds: RaisonAssistance[] = [];
    reopenGrounds: RaisonAssistance[];
    eventSubscriber: Subscription;
    testDateAccident = true;
    nbrVehPattern = '^[1-9]*$';
    floatPattern = '^[0-9]*\\.?[0-9]*$';
    sinisterSettings: any = {};
    tugArrivalTime: any;
    insuredArrivalTime: any;
    entityNamePrestation: string;
    observation = new Observation();
    observationss: Observation[] = [];
    observationsss: Observation[] = [];

    sinisterPec: SinisterPec = new SinisterPec();
    isObsModeEdit = false;
    @Output() idSinistrerPrestation: number;

    isCommentError = false;
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
        private owerDateUtils: DateUtils,
        private raisonAssistanceService: RaisonAssistanceService,
        private eventManager: JhiEventManager,
        private observationService: ObservationService,
        private confirmationDialogService: ConfirmationDialogService,
        private sinisterPecService: SinisterPecService,
        private siniterPecPopupService: SinisterPecPopupService,
        private sinisterPrestationService: SinisterPrestationService,
        private naturePanneService: NaturePanneService


    ) {
    }

    ngOnInit() {
        this.sinisterSettings = {
            heavyWeights: false,
            holidays: false,
            night: false,
            halfPremium: false,
            fourPorteF: false,
            fourPorteK: false,
            readOnlyPriceHt: true,
            canCancel: false,
            canReopen: false,
            showCancelGrounds: false,
            showReopenGrounds: false,
            isU: false,
            isR: false,
        }


        this.naturePanneService.findAllActive().subscribe((res: ResponseWrapper) => {
            this.naturePannes = res.json;
            console.log(this.naturePannes);
        });
        this.route.params.subscribe((params) => {
            this.idSinistrerPrestation = +params['id'];
            this.entityNamePrestation = 'Sinister_Prestation';

            if (params['id']) {
                this.delegationService.query().subscribe((res: ResponseWrapper) => { this.cities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
                // Get governorate list
                this.governorateService.query().subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
                // Get service type list
                this.serviceTypeService.query().subscribe((res: ResponseWrapper) => { this.serviceTypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
                this.raisonAssistanceService.findByStatus(StatusSinister.CANCELED).subscribe((response) => { this.cancelGrounds = response.json; });
                this.raisonAssistanceService.findByStatus(StatusSinister.REOPENED).subscribe((response) => { this.reopenGrounds = response.json; });





                this.sinisterService.findByPrestation(params['id']).subscribe((sinister: Sinister) => { // Edition
                    this.sinister = sinister;
                    if (this.sinister.id !== null && this.sinister.id !== undefined) {
                        if (this.sinister.incidentDate) {
                            const date = new Date(this.sinister.incidentDate);
                            this.sinister.incidentDate = {
                                year: date.getFullYear(),
                                month: date.getMonth() + 1,
                                day: date.getDate()
                            };
                        }
                        for (let i = 0; i < this.sinister.prestations.length; i++) {
                            if (+params['id'] === this.sinister.prestations[i].id) {
                                this.sinisterPrestation = this.sinister.prestations[i];
                                this.observationss = this.sinisterPrestation.observations;
                                this.buildSinisterSetting();
                                console.log("dis" + this.sinisterPrestation.mileage);
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
                            } else if (this.sinisterPrestation.statusId === StatusSinister.CLOSED || this.sinisterPrestation.statusId === StatusSinister.NOTELIGIBLE) {
                                this.sinisterSettings.canReopen = true;
                            }
                        }
                        console.log('_______________________________________________1');
                        if (this.sinisterPrestation.incidentGovernorateId !== null && this.sinisterPrestation.incidentGovernorateId !== undefined) {
                            this.fetchCitiesByGovernorate(this.sinisterPrestation.incidentGovernorateId, false);
                        }
                        if (this.sinisterPrestation.destinationGovernorateId !== null && this.sinisterPrestation.destinationGovernorateId !== undefined) {
                            this.fetchDestCitiesByGovernorate(this.sinisterPrestation.destinationGovernorateId, false);
                        }

                        this.initContrat(this.sinister.contractId, this.sinister.vehicleId);
                    } else {
                        this.router.navigate(["/accessdenied"]);
                    }

                }, (error: ResponseWrapper) => this.onError(error.json));

            }
        });

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

        if (this.sinisterPrestation.serviceTypeId !== null && this.sinisterPrestation.serviceTypeId !== undefined) {
            switch (this.sinisterPrestation.serviceTypeId) {
                case TypeService.REMORQUAGE:
                    this.sinisterSettings.heavyWeights = true;
                    this.sinisterSettings.holidays = true;
                    this.sinisterSettings.night = true;
                    this.sinisterSettings.halfPremium = true;
                    this.sinisterSettings.fourPorteF = true;
                    this.sinisterSettings.fourPorteK = true;
                    this.sinisterSettings.isR = true;
                    this.sinisterSettings.isU = true;
                    this.sinisterSettings.readOnlyPriceHt = true;
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
    initContrat(contractId: number, vehicleId: number) {
        if (contractId !== null && contractId !== undefined) {
            this.contractService.find(contractId).subscribe((contract: ContratAssurance) => {
                this.contratAssurance = contract;
                this.initContratDetails(vehicleId);
            });
        }
    }

    /**
     * Get contract details : vehicle and insured
     * @param vehicleRegistration
     */
    initContratDetails(vehicleId: number) {
        // find vehicle
        if (this.contratAssurance.vehicules && this.contratAssurance.vehicules.length > 0) {
            for (let i = 0; i < this.contratAssurance.vehicules.length; i++) {
                if (this.contratAssurance.vehicules[i].id === vehicleId) {
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
            if (cities && cities.length > 0 && initFlag) {
                this.sinisterPrestation.destinationLocationId = cities[0].id;
            }
        });
    }

    cancel() {
        // TODO: cancel a service
        this.sinisterPrestation.cancelGroundsId = null;
        this.sinisterSettings.showCancelGrounds = true;
        this.sinisterPrestation.statusId = StatusSinister.CANCELED;
    }

    reopen() {
        // TODO: cancel a service
        this.sinisterPrestation.reopenGroundsId = null;
        this.sinisterSettings.showReopenGrounds = true;
        this.sinisterSettings.showCancelGrounds = false;
        this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;

    }

    /**
     * Check incident date
     */
    checkDateAccident() {
        const today = new Date();
        if (this.sinister.incidentDate > today) {
            // TODO : translate message
            alert("La date d'accident ne doit pas être une date future");
            this.testDateAccident = false;
        } else {
            this.testDateAccident = true;
        }
    }

    selectServiceType() {
        // TODO: check eligibility service
        this.isServiceTypeSelected = true;
    }

    addPrestation() {
        this.isPrestationEdit = true;
        if (this.sinisterPrestation.serviceTypeId === TypeService.GRUTAGE) {
            this.isServiceGrutage = true;
        }
        console.log('__________________', this.sinister.prestations);
    }

    selectTug() {
        console.log('_________________________________________');
    }

    /**
     * Save sinister
     */
    save() {
        if (this.isPrestationEdit) {
            this.sinister.statusId = StatusSinister.INPROGRESS;
            this.sinisterPrestation.incidentGovernorateId = this.sinister.governorateId;
            this.sinisterPrestation.incidentLocationId = this.sinister.locationId;
            this.sinisterPrestation.statusId = StatusSinister.INPROGRESS;
            if (this.sinisterPrestation.insuredArrivalDate !== null && this.sinisterPrestation.insuredArrivalDate !== undefined) {
                this.sinisterPrestation.statusId = StatusSinister.CLOSED;
            }
            const copy: SinisterPrestation = Object.assign({}, this.sinisterPrestation);
            copy.tugArrivalDate = this.dateUtils.convertLocalDateToServer(this.sinisterPrestation.tugArrivalDate);
            copy.insuredArrivalDate = this.dateUtils.convertLocalDateToServer(this.sinisterPrestation.insuredArrivalDate);
            if (this.isPrestationEditMode) {
                for (let i = 0; i < this.sinister.prestations.length; i++) {
                    if (this.sinisterPrestation.id === this.sinister.prestations[i].id) {
                        this.sinister.prestations.splice(i, 1);
                    }
                }
            }

            this.sinister.prestations.push(copy);
        }
        if (this.sinister.id !== null && this.sinister.id !== undefined) {
            console.log('sinister update ...');
            this.sinisterService.update(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.router.navigate(['/sinister']);
            });
        } else {
            this.sinisterService.create(this.sinister).subscribe((resSinister) => {
                this.sinister = resSinister;
                this.router.navigate(['/sinister']);
            });
        }
    }

    trackSysVilleById(index: number, item: Delegation) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: Governorate) {
        return item.id;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
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



    //   histObservation()
    // {
    //    this.observationService.query().subscribe((res: ResponseWrapper) => {

    //     this.observationss = res.json;
    //   });
    //   }



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
            this.observation.sinisterPrestationId = this.sinisterPrestation.id; //update when insert prestation pec
            this.observation.prive = false;
            this.observationss.push(this.observation);
            this.observation.date = this.formatEnDate(new Date());
            this.observationService.create(this.observation).subscribe((res) => {
            });
            this.observation = new Observation();
        }
    }


    history() {
        if (this.idSinistrerPrestation !== null && this.idSinistrerPrestation !== undefined && this.entityNamePrestation !== null && this.entityNamePrestation !== undefined) {
            this.ngbModalRef = this.siniterPecPopupService.openHistoryModalSinisterPrestation(HistoryAssistanceComponent as Component, this.idSinistrerPrestation, this.entityNamePrestation);
        }
    }

}