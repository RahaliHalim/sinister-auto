import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { PrestationPEC } from './reparation.model';
import { Principal, ResponseWrapper } from '../../shared';
import { Router } from '@angular/router';
import { GaDatatable } from '../../constants/app.constants';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { UserExtraService, PermissionAccess } from '../user-extra';
@Component({
    selector: 'jhi-verification',
    templateUrl: './verification.component.html'
})
export class VerificationComponent implements OnInit, OnDestroy {
    currentAccount: any;
    prestationPECS: SinisterPec[];
    eventSubscriber: Subscription;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    listePrestation: any[] = [];
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,
        private userExtraService: UserExtraService
    ) {

    }
    loadAll() {
        this.prestationPECService.queryPrestationsToVerification(this.currentAccount.id).subscribe(
            (res: ResponseWrapper) => {
                this.listePrestation = res.json;
                this.dtTrigger.next(); // Actualize datatables
                this.userExtraService.findFunctionnalityEntityByUser(42, this.currentAccount.id).subscribe(res => {
                    this.permissionToAccess = res;
                });
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInPrestationPECS();
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: SinisterPec) {
        return item.id;
    }
    registerChangeInPrestationPECS() {
        this.eventSubscriber = this.eventManager.subscribe('prestationPECListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    /**
     * added from home
     */

    consulter(prestationPec: SinisterPec) {
        this.router.navigate(['verification-devis/', prestationPec.id]);

    }


}
