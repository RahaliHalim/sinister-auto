import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { ViewSinisterPecService, ViewSinisterPec } from '../view-sinister-pec';


@Component({
    selector: 'jhi-sinister-pec-a-approuver',
    templateUrl: './sinister-pec-to-approve.component.html'
})
export class SinisterPecToApproveComponent implements OnInit, OnDestroy {
    sinisterPecsToApprove: ViewSinisterPec[];
    currentAccount: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private sinisterPecService: SinisterPecService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        public principal: Principal,
        private userExtraService: UserExtraService,
        private viewSinisterPecService: ViewSinisterPecService,
    ) {

    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.registerChangeInSinisterPecs();
    }
    loadAll() {


        this.principal.identity().then((account) => {
            this.currentAccount = account;
           this.userExtraService.findFunctionnalityEntityByUser(85, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
                this.viewSinisterPecService.queryToApprove(this.currentAccount.id).subscribe((res) => {
                    this.sinisterPecsToApprove = res.json;
                    this.dtTrigger.next();
                });
            

        });
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: ViewSinisterPec) {
        return item.id;
    }
    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }
}
