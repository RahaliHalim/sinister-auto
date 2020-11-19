import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess } from '../user-extra';


@Component({
    selector: 'jhi-annulation-prestation',
    templateUrl: './confirmation-annulation-prestation.component.html'
})
export class ConfirmationAnnulationPrestationComponent implements OnInit, OnDestroy {
    sinisterPecsToApprove: SinisterPec[];
    currentAccount: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
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
                this.sinisterPecService.queryToConfirmCanceledPec(this.currentAccount.id).subscribe((res) => {
                    this.sinisterPecsToApprove = res.json;
                    this.sinisterPecsToApprove.forEach(sinPec => {
                        const opdate = new Date(sinPec.declarationDate);
                        sinPec.declarationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' + opdate.getHours() + ':' + opdate.getMinutes() + ':' + opdate.getSeconds());
                        sinPec.tiers.forEach(tr => {
                            if (tr.responsible) {
                                sinPec.immatriculationTier = tr.immatriculation;
                            }
                        });
                    });
                    this.dtTrigger.next();
                });
            
        });
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: SinisterPec) {
        return item.id;
    }
    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }
}
