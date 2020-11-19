import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ObservationService } from '../observation/observation.service';
import { AgentGeneral } from './agent-general.model';
import { AgentGeneralPopupService } from './agent-general-popup.service';
import { AgentGeneralService } from './agent-general.service';
import { PersonnePhysique, PersonnePhysiqueService } from '../personne-physique';
import { RefAgence, RefAgenceService } from '../ref-agence';
import { ServiceAssurance, ServiceAssuranceService } from '../service-assurance';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-agent-general-dialog',
    templateUrl: './agent-general-dialog.component.html'
})
export class AgentGeneralDialogComponent implements OnInit {

    agentGeneral: AgentGeneral = new AgentGeneral()
    isSaving: boolean;

    personnephysiques: PersonnePhysique[];

    refagences: RefAgence[];

    serviceassurances: ServiceAssurance[];

    selectedAgences: any[] = [];
    agences: any[] = [];

    personnePhysique: PersonnePhysique = new PersonnePhysique();

    constructor(
        private alertService: JhiAlertService,
        private agentGeneralService: AgentGeneralService,
        private personnePhysiqueService: PersonnePhysiqueService,
        private refAgenceService: RefAgenceService,
        private serviceAssuranceService: ServiceAssuranceService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refAgenceService.query()
            .subscribe((res: ResponseWrapper) => { this.refagences = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.serviceAssuranceService.query()
            .subscribe((res: ResponseWrapper) => { this.serviceassurances = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        
            this.initAgentGeneral()
 }
    initAgentGeneral() {
        this.route.params.subscribe((params) => {
            if ( params['id']) {
                this.agentGeneralService.find(params['id'])
                .subscribe((subres: AgentGeneral) => {
                    this.agentGeneral = subres
                    this.initPersonnePhysique()
                })
            }
        })
    }
        initPersonnePhysique() {
            if (!this.agentGeneral.personnePhysiqueId) {
                this.personnePhysique = new PersonnePhysique()
            } else {
                this.personnePhysiqueService
                    .find(this.agentGeneral.personnePhysiqueId)
                    .subscribe((subRes: PersonnePhysique) => {
                        this.personnePhysique = subRes;
                    }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            }
}

    clear() {
    }

     save() {
        
        this.isSaving = true;
        if (this.agentGeneral.id !== undefined) {
            this.subscribeperToSaveResponse(
                this.personnePhysiqueService.update(this.personnePhysique));
        } else {
            this.subscribeperToSaveResponse(
                this.personnePhysiqueService.create(this.personnePhysique));
        }
    }

    private subscribeperToSaveResponse(result: Observable<PersonnePhysique>) {
        result.subscribe((res: PersonnePhysique) =>
            this.onPerSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onPerSaveSuccess(result: PersonnePhysique) {
        const list: any[] = [];
        for(let i = 0; i < this.selectedAgences.length; i++) {

            list.push(this.selectedAgences[i]) ;

        }
        this.agentGeneral.agences = list;
        this.agentGeneral.personnePhysiqueId = result.id;
        if (this.agentGeneral.id !== undefined) {
            this.subscribeToSaveResponse(
                this.agentGeneralService.update(this.agentGeneral));
        } else {
            this.subscribeToSaveResponse(

                this.agentGeneralService.create(this.agentGeneral));
        }
    }

    private subscribeToSaveResponse(result: Observable<AgentGeneral>) {
        result.subscribe((res: AgentGeneral) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AgentGeneral) {
        this.router.navigate(['../agent-general']);
        this.eventManager.broadcast({ name: 'agentGeneralListModification', content: 'OK'});
        this.isSaving = false;
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

    trackPersonnePhysiqueById(index: number, item: PersonnePhysique) {
        return item.id;
    }

    trackRefAgenceById(index: number, item: RefAgence) {
        return item.id;
    }

    trackServiceAssuranceById(index: number, item: ServiceAssurance) {
        return item.id;
    }

    

    getSelectedAgence(selectedVals: Array<any>, option: any) {

        console.log("id selected agence-------"+option.id);
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    console.log("selected agence --------------"+selectedVals[i]);
                    return selectedVals[i];

                   
                }
            }
        }
        return option.id;
    }
}

@Component({
    selector: 'jhi-agent-general-popup',
    template: ''
})
export class AgentGeneralPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agentGeneralPopupService: AgentGeneralPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agentGeneralPopupService
                    .open(AgentGeneralDialogComponent as Component, params['id']);
            } else {
                this.agentGeneralPopupService
                    .open(AgentGeneralDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
