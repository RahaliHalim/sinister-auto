import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { PermissionAccess, UserExtraService } from '../user-extra';
import { ViewSinisterPec } from '../view-sinister-pec';


@Component({
    selector: 'jhi-annulation-prestation',
    templateUrl: './annulation-prestation.component.html'
})
export class AnnulationPrestationComponent implements OnInit, OnDestroy {
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
        private userExtraService: UserExtraService
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
            this.userExtraService.findFunctionnalityEntityByUser(100, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });  
                this.sinisterPecService.queryToCanceledPec(this.currentAccount.id).subscribe((res) => {
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
