import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ElementMenu } from './element-menu.model';
import { ElementMenuService } from './element-menu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-element-menu',
    templateUrl: './element-menu.component.html'
})
export class ElementMenuComponent implements OnInit, OnDestroy {
elementMenus: ElementMenu[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private elementMenuService: ElementMenuService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.elementMenuService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.elementMenus = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.elementMenuService.query().subscribe(
            (res: ResponseWrapper) => {
                this.elementMenus = res.json;
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
        this.registerChangeInElementMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ElementMenu) {
        return item.id;
    }
    registerChangeInElementMenus() {
        this.eventSubscriber = this.eventManager.subscribe('elementMenuListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
