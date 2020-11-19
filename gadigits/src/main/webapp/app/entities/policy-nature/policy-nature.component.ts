import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PolicyNature } from './policy-nature.model';
import { PolicyNatureService } from './policy-nature.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-policy-nature',
    templateUrl: './policy-nature.component.html'
})
export class PolicyNatureComponent implements OnInit, OnDestroy {
policyNatures: PolicyNature[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private policyNatureService: PolicyNatureService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.policyNatureService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.policyNatures = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.policyNatureService.query().subscribe(
            (res: ResponseWrapper) => {
                this.policyNatures = res.json;
                this.currentSearch = '';
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
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPolicyNatures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PolicyNature) {
        return item.id;
    }
    registerChangeInPolicyNatures() {
        this.eventSubscriber = this.eventManager.subscribe('policyNatureListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
