import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from '../sinister-pec';
import { SinisterPecService } from '../sinister-pec';
import { Principal, ResponseWrapper } from '../../shared';
import { PrestationPecStep } from '../../constants/app.constants';
import { Router } from '@angular/router';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess} from '../user-extra';
@Component({
    selector: 'jhi-confirmation-devis',
    templateUrl: './confirmation-devis-complementary.component.html'
})
export class ConfirmationDevisComplementaryComponent implements OnInit, OnDestroy {
    currentAccount: any;
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: SinisterPec[] = [];
    sinisterPecs: SinisterPec[] = [];
    account: Account;
    dtOptions: any = {};
    permissionToAccess : PermissionAccess = new PermissionAccess();
    dtTrigger: Subject<SinisterPec> = new Subject();

    constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private primaryQuotationService: PrimaryQuotationService,
        private eventManager: JhiEventManager,
        private userExtraService : UserExtraService
    ) {

    }
    loadAll() {
        this.prestationPECService.queryConfirmationDevisComplementaire().subscribe(
            (res: ResponseWrapper) => {
                this.sinisterPecs = res.json;
                this.sinisterPecs.forEach(sinPec => {
                    const opdate = new Date(sinPec.declarationDate);
                    sinPec.declarationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' + opdate.getHours() + ':' + opdate.getMinutes() + ':' + opdate.getSeconds());
                    if (sinPec.listComplementaryQuotation.length > 0) {
                        this.sinisterPecsBeingProcessed.push(sinPec)
                    }
                });
                this.dtTrigger.next(); // Actualize datatables
                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                this.userExtraService.findFunctionnalityEntityByUser(102,this.currentAccount.id).subscribe(res => {
                        this.permissionToAccess = res;
                 });
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

    consulter(prestationPec) {
        if (prestationPec.id !== null && prestationPec.primaryQuotationId !== null) {
            this.router.navigate(['/confirmation-devis-complementary/', prestationPec.id]);
        }

    }


}