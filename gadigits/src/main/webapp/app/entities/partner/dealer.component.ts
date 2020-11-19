import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Partner } from './partner.model';
import { PartnerService } from './partner.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { GaDatatable } from '../../constants/app.constants';
import { PermissionAccess, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-partner',
    templateUrl: './dealer.component.html'
})
export class DealerComponent implements OnInit, OnDestroy {
partners: Partner[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    dtTrigger: Subject<Partner> = new Subject();
    dtOptions: any = {};
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private partnerService: PartnerService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private router: Router,
        private userExtraService: UserExtraService
    ) {
    }

    loadAll() {
        
        this.partnerService.findAllDealers().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
                this.partners.forEach(element => {
                    if(!element.active){
                        element.activeStr = "Oui";
                    }
                    if(element.active){
                        element.activeStr = "Non";
                    }
                });
                this.dtTrigger.next();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions; 
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(14, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInPartners();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Partner) {
        return item.id;
    }
    registerChangeInPartners() {
        this.eventSubscriber = this.eventManager.subscribe('dealerListModification', (response) => this.loadAll());
        this.eventManager.broadcast({ name: 'dealerListModification', content: 'OK' });
        this.router.navigate(['../../dealer'])
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
