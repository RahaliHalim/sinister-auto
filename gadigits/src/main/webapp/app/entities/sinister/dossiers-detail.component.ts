import { SinisterPrestation } from './sinister-prestation.model';
import { Assure } from './../assure/assure.model';
import { VehiculeAssure } from './../vehicule-assure/vehicule-assure.model';
import { VehiculeAssureService } from './../vehicule-assure/vehicule-assure.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { SinisterService } from './sinister.service';
import { ContratAssurance } from './../contrat-assurance/contrat-assurance.model';
import { ContratAssuranceService } from './../contrat-assurance/contrat-assurance.service';
import { ResponseWrapper, Principal } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { Features, GaDatatable } from '../../constants/app.constants';
import { Subject } from 'rxjs';
import { AssureService } from '../assure';
import { HistoryDossierComponent } from '../contrat-assurance/history-dossier/history-dossier.component';
import { SinisterPecPopupService, HistoryPopupDetailsPec, SinisterPec } from '../sinister-pec';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPrestationViewComponent } from './sinister-prestation-view.component';
import { SiniterPrestationTugPopupService } from './sinister-prestation-popup.service';


@Component({
    selector: 'jhi-sinister-detail',
    templateUrl: './dossiers-detail.component.html'
})
export class DossiersDetailComponent implements OnInit {

    contratAssurance: ContratAssurance = new ContratAssurance();
    sinister: Sinister = new Sinister();

    insured: Assure = new Assure();
    vehiculeAssure: VehiculeAssure = new VehiculeAssure();
    sinisterPecs: SinisterPec[] = [];

    currentAccount: any;
    eventSubscriber: Subscription;
    authorities: any[];
    prestations: any;
    userTypeId: any;
    private ngbModalRef: NgbModalRef;


    dtOptions: any = {};
    dtTrigger: Subject<SinisterPrestation> = new Subject();

    constructor(
        private siniterPecPopupService: SinisterPecPopupService,

        private alertService: JhiAlertService,
        private sinisterService: SinisterService,
        private contractService: ContratAssuranceService,
        private vehicleService: VehiculeAssureService,
        private insuredService: AssureService,
        public principal: Principal,
        private route: ActivatedRoute,
        private siniterPrestationTugPopupService: SiniterPrestationTugPopupService,
        private sinisterPecPopupService: SinisterPecPopupService
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.initSinister();
        console.log(this.sinister.id);
    }

    /**
     * Init sinister
     */
    initSinister() {
        this.route.params.subscribe((params) => {
            if (params['id']) {
                const id = params['id'];
                this.sinisterService.find(id).subscribe((result: Sinister) => {
                    this.sinister = result;
                    console.log(this.sinister.prestations);
                    if (this.sinister.sinisterPec !== null && this.sinister.sinisterPec !== undefined) {
                        this.sinisterPecs.push(this.sinister.sinisterPec);
                    }
                    console.log(this.sinisterPecs);
                    if (this.sinister.contractId !== null && this.sinister.contractId !== undefined) {
                        this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                            this.contratAssurance = contract;
                            this.vehicleService.find(this.sinister.vehicleId).subscribe((vehicle: VehiculeAssure) => {
                                this.contratAssurance.marqueLibelle = vehicle.marqueLibelle;
                                this.contratAssurance.modeleLibelle = vehicle.modeleLibelle;
                                this.contratAssurance.datePCirculation = vehicle.datePCirculation;
                                this.vehiculeAssure = vehicle;
                                this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((insured: Assure) => {
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

                                });
                            });
                        });
                    }
                    this.dtTrigger.next();
                }, (error: ResponseWrapper) => this.onError(error.json));
            }
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
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


    history() {
        this.ngbModalRef = this.siniterPecPopupService.openHistoryModalSinister(HistoryDossierComponent as Component, 'sinister', this.sinister.id);

    }
}