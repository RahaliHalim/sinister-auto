import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiAlertService } from 'ng-jhipster';

import { Agency } from './agency.model';
import { AgencyService } from './agency.service';
import { Partner, PartnerService } from '../partner';
import { ResponseWrapper } from '../../shared';

import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { Region, RegionService } from '../region';
import { Type, Categorie } from '../../constants/app.constants';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';
import { AgencyPopupService } from './agency-popup.service';
import { AgenceConcessPopupDetail } from './agenceConcessHistoryPopup';
import { GaDatatable, Global } from './../../constants/app.constants';

@Component({
    selector: 'jhi-agency-detail',
    templateUrl: './agency-detail.component.html'
})
export class AgencyDetailComponent implements OnInit, OnDestroy {

    agency: Agency = new Agency();
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    partners: Partner[] = [];
    governorates: Governorate[] = [];

    delegations: Delegation[] = [];
    private ngbModalRef: NgbModalRef;
    textPattern = Global.textPattern;
    textPatternAlphaNum = '^[a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';

    regions: Region[] = [];
    entityName = 'Agency';

    types : Type[] = [
        {id: 1, libelle: "Agent"},
        {id: 2, libelle: "Courtier"},
        {id: 3, libelle: "Bureau direct"}];
    
    categories : Categorie[] = [
            {id: 1, libelle: "A"},
            {id: 2, libelle: "B"},
            {id: 3, libelle: "C"}];
    
    type : Type = new Type();
    
    categorie : Categorie = new Categorie;

    constructor(
        private eventManager: JhiEventManager,
        private partnerService: PartnerService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private regionService: RegionService,
        private agencyService: AgencyService,
        private route: ActivatedRoute,
        private alertService: JhiAlertService,
        private historyPopupService: HistoryPopupService,
        private agencyPopupService: AgencyPopupService,
    ) {
    }

    ngOnInit() {
        this.partnerService.findAllCompanies()
            .subscribe((res: ResponseWrapper) => { this.partners = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.regionService.query()
            .subscribe((res: ResponseWrapper) => { this.regions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
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

    trackRegionById(index: number, item: Region) {
        return item.id;
    }

    trackTypeById(index: number, item: Type) {
        return item.libelle;
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.libelle;
    }


    

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    registerChangeInAgencies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agencyListModification',
            (response) => this.load(this.agency.id)
        );
    }

    selectHistory(id, entityName) {
        this.ngbModalRef = this.agencyPopupService.openHist(AgenceConcessPopupDetail as Component, id,entityName);
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
}
