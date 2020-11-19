import { Component, OnInit, Pipe, OnDestroy, NgZone as zone, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { StateStorageService } from '../../shared/auth/state-storage.service';
import { SinisterPecService, SinisterPec } from '../sinister-pec';
import { Principal, ResponseWrapper } from '../../shared';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';;
import { Estimation } from './estimation.model';
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { QuoteStatus } from '../../constants/app.constants';
import { GAEstimateService } from "../devis/gaestimate.service";
import { DevisService } from "../devis/devis.service";
import { GaDatatable } from './../../constants/app.constants';
import { SafeResourceUrl, DomSanitizer, SafeUrl, EventManager } from '@angular/platform-browser';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { ViewChild, ElementRef } from '@angular/core';
@Component({
    selector: 'jhi-demontage-devis',
    templateUrl: './demontage.component.html'
})
export class DemontageComponent implements OnInit, OnDestroy {

    currentAccount: any;
    prestationPECS: SinisterPec[];
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: SinisterPec[];
    accordPriseCharge: AccordPriseCharge = new AccordPriseCharge();
    estimation: Estimation = new Estimation();
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();


    constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,
        private userExtraService: UserExtraService,
        private Zone: NgZone,
    ) {

    }

    loadAll() {
        this.prestationPECService.querySinisterPecForDismantling(this.currentAccount.id).subscribe(
            (res: ResponseWrapper) => {
                this.sinisterPecsBeingProcessed = res.json;
                this.dtTrigger.next(); // Actualize datatables*
                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                    this.userExtraService.findFunctionnalityEntityByUser(42, this.currentAccount.id).subscribe(res => {
                        this.permissionToAccess = res;
                    });
                });
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log(this.currentAccount.id);
            this.dtOptions = GaDatatable.defaultDtOptions;
            this.loadAll();
            console.log

        });




        this.principal.identity().then((account) => {
            this.currentAccount = account;
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



    demontage(prestationPec: SinisterPec) {
        console.log("iciiiiiiiiiiiiii log id" + prestationPec.id);
        this.router.navigate(['demontage/', prestationPec.id]);

    }


}


