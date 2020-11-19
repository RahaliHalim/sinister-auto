import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from '../sinister-pec';
import { SinisterPecService } from '../sinister-pec';
import { Principal, ResponseWrapper } from '../../shared';
import { PrestationPecStep } from '../../constants/app.constants';
import {Router } from '@angular/router';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { GaDatatable } from './../../constants/app.constants';
import { UserExtraService, PermissionAccess} from '../user-extra';
@Component( {
    selector: 'jhi-confirmation-devis',
    templateUrl: './confirmation.component.html'
} )
export class ConfirmationComponent implements OnInit, OnDestroy {
    currentAccount: any;
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: SinisterPec[] = [];
    account: Account;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    permissionToAccess : PermissionAccess = new PermissionAccess();
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
        this.prestationPECService.queryConfirmationDevis(this.currentAccount.id).subscribe(
            ( res: ResponseWrapper ) => {
                this.sinisterPecsBeingProcessed = res.json;
                this.dtTrigger.next(); // Actualize datatables
                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                   this.userExtraService.findFunctionnalityEntityByUser(42,this.currentAccount.id).subscribe(res => {
                      this.permissionToAccess = res;
                   });
                });





                },

                ( res: ResponseWrapper ) => this.onError( res.json )
                );
    }

       
    ngOnInit() { 
        
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.principal.identity().then(( account ) => {
            this.currentAccount = account;
            this.loadAll();
        } );
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

    consulter(prestationPec) {
            if (prestationPec.id !== null && prestationPec.primaryQuotationId!== null) {
                this.router.navigate(['/confirmation-devis/',  prestationPec.id]);
            }
      
       }  


}