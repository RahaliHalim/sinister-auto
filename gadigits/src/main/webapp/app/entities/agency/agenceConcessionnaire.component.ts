import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription , Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Agency } from './agency.model';
import { AgencyService } from './agency.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess } from '../user-extra';


@Component({
    selector: 'jhi-agency',
    templateUrl: './agenceConcessionnaire.component.html'
})
export class AgenceConcessionnaireComponent implements OnInit, OnDestroy {
    agencies: Agency[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    dtOptions: any = {};
    dtTrigger: Subject<Agency> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    

    constructor(
        private agencyService: AgencyService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private router: Router,
        private userExtraService: UserExtraService
    ) {
    }

    loadAll() {
        this.agencyService.findAllAgenceConcessionnares().subscribe(
            (res: ResponseWrapper) => {
                this.agencies = res.json;
                this.dtTrigger.next();
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
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(95, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInAgencies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Agency) {
        return item.id;
    }
    registerChangeInAgencies() {
        this.eventSubscriber = this.eventManager.subscribe('agenceConcessionnaireListModification', (response) => this.loadAll());
        this.eventManager.broadcast({ name: 'agenceConcessionnaireListModification', content: 'OK' });
        this.router.navigate(['../../agenceConcessionnaire'])
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
