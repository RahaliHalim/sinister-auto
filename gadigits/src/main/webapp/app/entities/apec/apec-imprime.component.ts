import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Apec } from './apec.model';
import { ApecService } from './apec.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { GaDatatable, EtatAccord } from '../../constants/app.constants';
import { PermissionAccess } from '../user-extra/permission-access.model';
import { UserExtraService } from '../user-extra/user-extra.service';

@Component({
    selector: 'jhi-apec',
    templateUrl: './apec-imprime.component.html'
})
export class ApecImprimeComponent implements OnInit, OnDestroy {

    currentAccount: any;
    apecs: Apec[];
    error: any;
    apecsFinals: Apec[] = [];
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    dtOptions: any = {};
    dtTrigger: Subject<Apec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private apecService: ApecService,
        private userExtraService: UserExtraService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,

    ) {
        
    }

    loadAll() {  
        this.apecService.queryByStatus(EtatAccord.ACC_VALIDE_PART_ASSURE).subscribe(
            ( res: ResponseWrapper ) => this.onSuccess( res.json, res.headers ),
            ( res: ResponseWrapper ) => this.onError( res.json )
        );
    }
    
    ngOnInit() {
        // Init datatable options
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(55, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInApecs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Apec) {
        return item.id;
    }
    registerChangeInApecs() {
        this.eventSubscriber = this.eventManager.subscribe('apecListModification', (response) => this.loadAll());
    }

    private onSuccess(data, headers) {
        this.apecs = data;
        for (let i = 0; i < this.apecs.length; i++) {
            if(this.apecs[i].sinisterPec.stepId == 103){
                this.apecsFinals.push(this.apecs[i]);
            }
        }
        this.dtTrigger.next();
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}