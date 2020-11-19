import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { SinisterService } from '../sinister/sinister.service';
import { Sinister } from '../sinister/sinister.model';
import { GaDatatable } from '../../constants/app.constants';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-affectation-reparateur',
    templateUrl: './prestation-pec-affect-reparation.component.html'

})
export class PrestationPECAffectReparationComponent implements OnInit, OnDestroy {
    sinister: Sinister;
    sinisterPecsBeingAffectReparateur: ViewSinisterPec[];
    permissionToAccess: PermissionAccess = new PermissionAccess();
    currentAccount: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    constructor(
        private principal: Principal,
        private sinisterPecService: SinisterPecService,
        private sinisterService: SinisterService,
        private alertService: JhiAlertService,
        private viewSinisterPecService: ViewSinisterPecService,
        private eventManager: JhiEventManager,
        private userExtraService: UserExtraService
    ) {

    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            this.userExtraService.findFunctionnalityEntityByUser(27, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInSinisterPecs();
    }
    loadAll() {
        this.viewSinisterPecService.getAllAcceptedAndNoReparator(this.currentAccount.id).subscribe((res) => {
            this.sinisterPecsBeingAffectReparateur = res.json;
            this.dtTrigger.next();
        });
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: ViewSinisterPec) {
        return item.id;
    }
    dateAsYYYYMMDDHHNNSS(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + ' ' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
            + String(val)).slice(String(val).length);
    }
    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }
}

