import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager  } from 'ng-jhipster';

import { Loueur } from './loueur.model';
import { LoueurService } from './loueur.service';
import { VehiculeLoueur } from '../vehicule-loueur';
import { VisAVis } from '../vis-a-vis';
import { RefModeReglement, RefModeReglementService } from '../ref-mode-reglement';
import { Delegation, DelegationService } from '../delegation';
import { Governorate, GovernorateService } from '../governorate';
import { VehicleBrandModelService } from '../vehicle-brand-model';
import { VehicleBrandService } from '../vehicle-brand';
import { RaisonAssistanceService } from '../raison-assistance';
import { ResponseWrapper } from '../../shared';
import { RefMotif } from '../ref-motif';
import { GaDatatable, Global, SettlementMode } from '../../constants/app.constants';

@Component({
    selector: 'jhi-loueur-detail',
    templateUrl: './loueur-detail.component.html'
})
export class LoueurDetailComponent implements OnInit, OnDestroy {

    loueur: Loueur = new Loueur();
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    listVehicules: VehiculeLoueur[] = [];
    visAVis: VisAVis = new VisAVis();
    listContacts: VisAVis[] = [];
    listContact: VisAVis[] = [];
    visAVisGeneral: VisAVis = new VisAVis();
    refModeReglement: RefModeReglement;
    refModeReglements: RefModeReglement[];
    sysgouvernorats: Governorate[];
    sysGouvernorat: Governorate;
    sysvilles: Delegation[];
    sysVille: Delegation;
    refMotif = new RefMotif();
    motifs: RefMotif[];
    dtOptions: any = {};
    textPattern = Global.textPattern;
    mandatoryRibFlag = false;
    errorMessage = '';
    disabledForm = false;
    disabledForm1 = false;
    refMotifOption: RefMotif;
    creervisavis = false;

    constructor(
        private eventManager: JhiEventManager,
        private loueurService: LoueurService,
        private route: ActivatedRoute,
        private refModeReglementService: RefModeReglementService,
        private sysVilleService: DelegationService,
        private sysGouvernoratService: GovernorateService,
        private vehicleBrandModelService: VehicleBrandModelService,
        private vehicleBrandService: VehicleBrandService,
        private raisonAssistanceService: RaisonAssistanceService,
        private alertService: JhiAlertService,

    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.refModeReglementService.query().subscribe((res: ResponseWrapper) => { this.refModeReglements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysVilleService.query().subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.raisonAssistanceService.findMotifsByOperation(3).subscribe((subRes: ResponseWrapper) => {
            this.motifs = subRes.json;
        })
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLoueurs();
    }

    load(id) {
        this.loueurService.find(id).subscribe((loueur) => {
            this.loueur = loueur;

            const dateDebut = new Date(this.loueur.dateEffetConvention);
            const dateFin = new Date(this.loueur.dateFinConvention);
            if (this.loueur.dateEffetConvention) {
                this.loueur.dateEffetConvention = {
                    year: dateDebut.getFullYear(),
                    month: dateDebut.getMonth() + 1,
                    day: dateDebut.getDate()
                };
            }
            if (this.loueur.dateFinConvention) {
                this.loueur.dateFinConvention = {
                    year: dateFin.getFullYear(),
                    month: dateFin.getMonth() + 1,
                    day: dateFin.getDate()
                };
            }

          if (this.loueur.id != null && this.loueur.id !== undefined && this.loueur.vehicules !== null) {
            this.listVehicules = this.loueur.vehicules;
          }

            if (this.loueur.id != null && this.loueur.id !== undefined && this.loueur.visAViss !== null) {
                this.listContact = this.loueur.visAViss;
                this.listContact.forEach((element) => {
                    if (element.isGerant !== true) {
                        this.listContacts.push(element);
                    }
                    else this.visAVisGeneral = element;
                });
            }
            this.loueur.blocage = loueur.blocage;
        });
    }
    previousState() {
        window.history.back();
    }

    etatRib(id) {
        if (SettlementMode.VIREMENT === id || SettlementMode.TRAITE === id) {
            this.mandatoryRibFlag = true;
        } else {
            this.mandatoryRibFlag = false;
        }
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLoueurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'loueurListModification',
            (response) => this.load(this.loueur.id)
        );
    }

    listVillesByGouvernoratLieu(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernorat = subRes;
            this.sysVilleService.findByGovernorate(this.sysGouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.sysvilles = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.loueur.delegationId = subRes1[0].id;
                }

            });
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);

    }

}
