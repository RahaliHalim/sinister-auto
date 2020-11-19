import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService  } from 'ng-jhipster';
import { ResponseWrapper } from '../../shared';

import { Agency } from './agency.model';
import { AgencyService } from './agency.service';
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { Partner, PartnerService } from '../partner';
import { TypeAgence } from '../../constants/app.constants';
import { AgencyPopupService } from './agency-popup.service';
import { AgenceConcessPopupDetail } from './agenceConcessHistoryPopup';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GaDatatable, Global } from './../../constants/app.constants';

@Component({
    selector: 'jhi-agency-detail',
    templateUrl: './agenceConcessionnaire-detail.component.html'
})
export class AgenceConcessionnaireDetailComponent implements OnInit, OnDestroy {

    agency: Agency = new Agency();
    private subscription: Subscription;
    private ngbModalRef: NgbModalRef;
    private eventSubscriber: Subscription;
    partners: Partner[] = [];
    governorates: Governorate[] = [];
    textPattern = Global.textPattern;

        textPatternNotAlphNul = '^[a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ\/]+( [a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ\/\s\(\)\-]+)*$';

    delegations: Delegation[] = [];
    entityName = 'Agency';

    typeAgences : TypeAgence[] = [
        {id: 1, libelle: "CET"},
        {id: 2, libelle: "TDA"},
        {id: 3, libelle: "CCS"},
        {id: 4, libelle: "CTN"}];
    
    typeAgence : TypeAgence;

    constructor(
        private eventManager: JhiEventManager,
        private partnerService: PartnerService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private agencyService: AgencyService,
        private route: ActivatedRoute,
        private alertService: JhiAlertService,
        private agencyPopupService: AgencyPopupService,
    ) {
    }

    ngOnInit() {
        this.partnerService.findAllDealers()
            .subscribe((res: ResponseWrapper) => { this.partners = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgencies();
    }

    load(id) {
        this.agencyService.find(id).subscribe((agency) => {
            this.agency = agency;
        });
    }
    previousState() {
        window.history.back();
    }

    trackPartnerById(index: number, item: Partner) {
        return item.id;
    }

    trackGovernorateById(index: number, item: Governorate) {
        return item.id;
    }

    trackDelegationById(index: number, item: Delegation) {
        return item.id;
    }

    trackTypeAgenceById(index: number, item: TypeAgence) {
        return item.libelle;
    }
    trimServiceTypeNumber() {
        this.agency.nom = this.agency.nom.trim();
        this.agency.prenom = this.agency.prenom.trim();
        this.agency.name = this.agency.name.trim();
    }

    fetchDelegationsByGovernorate(id) {
        this.delegationService.findByGovernorate(id).subscribe((delegations: Delegation[]) => {
            this.delegations = delegations;
        });
    }

    selectHistory(id, entityName) {
        this.ngbModalRef = this.agencyPopupService.openHist(AgenceConcessPopupDetail as Component, id, entityName);
        this.ngbModalRef.result.then((result: any) => {
            if (result !== null && result !== undefined) {
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    registerChangeInAgencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agenceConcessionnaireListModification',
            (response) => this.load(this.agency.id)
        );
    }
}
