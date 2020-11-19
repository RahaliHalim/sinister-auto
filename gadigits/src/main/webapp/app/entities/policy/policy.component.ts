import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Policy } from './policy.model';
import { PolicyService } from './policy.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { GaDatatable } from '../../constants/app.constants';

@Component({
    selector: "jhi-policy",
    templateUrl: "./policy.component.html"
})
export class PolicyComponent implements OnInit, OnDestroy {
    policies: Policy[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    dtOptions: any = {};
    dtTrigger: Subject<Policy> = new Subject();

    constructor(
        private policyService: PolicyService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {}

    loadAll() {
        this.policyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.policies = res.json;
                this.dtTrigger.next();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    clear() {
        this.loadAll();
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPolicies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Policy) {
        return item.id;
    }
    registerChangeInPolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyListModification',
            response => this.loadAll()
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
