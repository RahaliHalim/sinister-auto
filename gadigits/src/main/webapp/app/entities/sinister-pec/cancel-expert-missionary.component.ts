import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec, SinisterPecService } from '.';
import { Principal, ResponseWrapper } from '../../shared';
import { Router } from '@angular/router';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { QuoteStatus } from '../../constants/app.constants';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { ViewSinisterPecService, ViewSinisterPec } from '../view-sinister-pec';
@Component({
    selector: 'jhi-cancel-expert-missionary.component',
    templateUrl: './cancel-expert-missionary.component.html'
})
export class CancelExpertMissionaryComponent implements OnInit, OnDestroy {
    currentAccount: any;
    prestationPECS: ViewSinisterPec[];
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: any[] = [];
    accordPriseCharge: AccordPriseCharge = new AccordPriseCharge();
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private primaryQuotationService: PrimaryQuotationService,
        private eventManager: JhiEventManager,
        private userExtraService: UserExtraService,
        private viewSinisterPecService: ViewSinisterPecService
    ) {

    }
    loadAll() {
        this.viewSinisterPecService.queryPrestationsInNotCancelExpertMission(this.currentAccount.id).subscribe(
            (res: ResponseWrapper) => {
                this.sinisterPecsBeingProcessed = res.json;
                this.dtTrigger.next(); // Actualize datatables
            }, (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            this.userExtraService.findFunctionnalityEntityByUser(26, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
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

    missionnerExpert(prestationPec: ViewSinisterPec) {
        if (prestationPec.id !== null) {
            this.router.navigate(['/devis-cancel-missionary-expert/' + prestationPec.id]);
        }

    }


}