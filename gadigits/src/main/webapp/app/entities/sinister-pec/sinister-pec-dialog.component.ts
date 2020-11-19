import { OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPecPopupService } from './sinister-pec-popup.service';
import { ConfirmationDialogService } from './../../shared/confirmation-dialog/confirmation-dialog.service';
import { Sinister } from '../sinister/sinister.model';
import { Camion } from './../camion/camion.model';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefTarifService } from './../ref-tarif/ref-tarif.service';
import { SinisterPrestationTugComponent } from '../sinister/sinister-prestation-tug.component';
import { SiniterPrestationTugPopupService } from '../sinister/sinister-prestation-popup.service';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { Tiers, TiersService } from '../tiers';
import { VehiculeAssure } from '../vehicule-assure';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';
import { RefTypeServiceService } from '../ref-type-service';
import { ResponseWrapper, Principal } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { StatusSinister, TypeService } from '../../constants/app.constants';
import { SinisterPrestation } from '../sinister/sinister-prestation.model';
import { TugPricing } from '../ref-tarif';
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { SinisterService } from '../sinister/sinister.service';
import { RefGroundsService } from '../sinister/ref-grounds.service';
import { DateUtils } from '../../utils/date-utils';
import { RefAgenceService } from '../ref-agence/ref-agence.service';
import { RefCompagnie, RefCompagnieService } from '../ref-compagnie';
import { RefAgence } from '../ref-agence/ref-agence.model';
import { RefBareme, RefBaremeService, RefBaremePopupDetailService } from '../ref-bareme';
import { RefModeGestionService, RefModeGestion } from '../ref-mode-gestion';
import { ObservationService } from '../observation/observation.service';
import { Observation, TypeObservation } from '../observation';
import { DemandSinisterPecService } from "./demand-sinister-pec.service";
import { idLocale } from 'ngx-bootstrap';
import { Attachment } from '../attachments';
import { SinisterPec, SinisterPecService, SinisterStatus } from '../sinister-pec';
import { RefPackService, RefPack } from '../ref-pack';
import { ClientService } from '../client/client.service';
@Component({
    selector: 'jhi-sinister-pec-dialog',
    templateUrl: './sinister-pec-dialog.component.html'
})
export class SinisterPecDialogComponent implements OnInit {

    sinisterPec: SinisterPec;
    isSaving: boolean;
    private ngbModalRef: NgbModalRef;
    contratAssurance: ContratAssurance = new ContratAssurance();
    vehiculeContrat: VehiculeAssure = new VehiculeAssure();
    insured: Assure = new Assure();
    refagences: RefAgence[];
    sinister: Sinister = new Sinister();
    sinisterPrestation: SinisterPrestation = new SinisterPrestation();
    isEditMode = false; // Siniter mode edit
    isServiceTypeSelected = false; // Service type selected
    isPrestationEdit = false; // Prestion form printed
    isPrestationEditMode = false; // Sinister prestion mode edit
    isServiceGrutage = false;
    governorates: Governorate[];
    isObsModeEdit = false;
    listAgenceByCompagnie: any;
    search: any;
    checkDateSurvenance: any;
    delegations: Delegation[];
    constatFiles: File;
    delegationsDest: Delegation[];
    serviceTypes: RefTypeService[];
    eventSubscriber: Subscription;
    testDateAccident = true;
    compagnie: any;
    carteGriseFiles: File;
    nbrVehPattern = '^[1-9]*$';
    floatPattern = '^[0-9]*\\.?[0-9]*$';
    sinisterSettings: any = {};
    tugArrivalTime: any;
    insuredArrivalTime: any;
    nowNgbDate: any;
    tiers: Tiers[] = [];
    ville: any;
    debut: any;
    isCommentError = false;
    fin: any;
    agence: any;
    isTierModeEdit = false;
    constatPreview = false;
    carteGrisePreview = false;
    acteCessionPreview = false;
    tier = new Tiers();
    telephone: any;
    listModeGestionByPack: any[] = [];
    myDate: number = Date.now();
    tiersIsActive = false;
    isvalideDate: any;  

    exist: boolean = false;
    tierIsCompagnie: boolean = false;
    old: boolean = false;
    listGarantiesForModeGestion: any[] = [];
    tierRaisonSocial: any;
    debut2: any;
    fin2: any;
    assure: any;
    agenceTierNom: any;
    compagnieTierNom: any;
    compagnies: any;
    casDeBareme = false;
    refBid: number;
    modesDeGestion: any[] = [];
    agences: any;
    CasBareme = new RefBareme();
    tiersIsNotAssure: boolean = false;
    tierContratNumero: String = null;
    modeChoisi: RefModeGestion = new RefModeGestion;
    observations: Observation[] = [];
    miseAjour: any;
    creation: any;
    cloture: any;
    acteCessionFiles: File;
    garantieDommageVehicule = false;
    garantieDommageCollision = false;
    garantieVolIncendiePartiel = false;
    garantieBrisGlace = false;
    attachmentList: Attachment[];
    labelConstat: String = "Constat";
    labelCarteGrise: String = "Carte Grise";
    labelActeCession: String = "Acte de cession";
    constatAttachment: Attachment = new Attachment();
    carteGriseAttachment: Attachment = new Attachment();
    acteCessionAttachment: Attachment = new Attachment();
    isContractLoaded = false;
    observation = new Observation();
    pack: RefPack = new RefPack();
    testCouverte: boolean = false;
    testservice: boolean = false;
    declarationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sinisterPecService: SinisterPecService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sinisterPec.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sinisterPecService.update(this.sinisterPec));
        } else {
            this.subscribeToSaveResponse(
                this.sinisterPecService.create(this.sinisterPec));
        }
    }

    private subscribeToSaveResponse(result: Observable<SinisterPec>) {
        result.subscribe((res: SinisterPec) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SinisterPec) {
        this.eventManager.broadcast({ name: 'sinisterPecListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
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

    trackGovernorateById(index: number, item: Governorate) {
        return item.id;
    }

    trackDelegationById(index: number, item: Delegation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sinister-pec-popup',
    template: ''
})
export class SinisterPecPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sinisterPecPopupService: SinisterPecPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sinisterPecPopupService
                    .open(SinisterPecDialogComponent as Component, params['id']);
            } else {
                this.sinisterPecPopupService
                    .open(SinisterPecDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
