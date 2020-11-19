import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec, SinisterPecService } from '.';
import { Principal, ResponseWrapper } from '../../shared';
import {Router } from '@angular/router';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { QuoteStatus } from '../../constants/app.constants';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
@Component( {
    selector: 'jhi-expert-missionary.component',
    templateUrl: './expert-missionary.component.html'
} )
export class ExpertMissionaryComponent implements OnInit, OnDestroy {
    currentAccount: any;
    prestationPECS: ViewSinisterPec[];
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: any[] = [];
    permissionToAccess : PermissionAccess = new PermissionAccess();
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
     constructor(
        private viewSinisterPecService: ViewSinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private primaryQuotationService: PrimaryQuotationService,
        private eventManager: JhiEventManager,
        private userExtraService : UserExtraService
    ) {
        
    }
    loadAll() {
        this.viewSinisterPecService.queryPrestationsInMissionExpert().subscribe(
            ( res: ResponseWrapper ) => {
                this.sinisterPecsBeingProcessed = res.json;
                this.dtTrigger.next(); // Actualize datatables
                },
                ( res: ResponseWrapper ) => this.onError( res.json )
                );
    }
       
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(26,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInPrestationPECS();
    }
    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }
    trackId( index: number, item: SinisterPec ) {
        return item.id;
    }
    registerChangeInPrestationPECS() {
        this.eventSubscriber = this.eventManager.subscribe( 'prestationPECListModification', ( response ) => this.loadAll() );
    }
       
    private onError( error ) {
        this.alertService.error( error.message, null, null );
    }
  
    /**
     * added from home
     */

    missionnerExpert(prestationPec: ViewSinisterPec) {
            if (prestationPec.id !== null ) {
                this.router.navigate(['/devis-missionary-expert/'+ prestationPec.id ]);
            }
      
       }  


}