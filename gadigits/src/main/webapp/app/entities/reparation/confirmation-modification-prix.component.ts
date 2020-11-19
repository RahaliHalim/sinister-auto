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
import { PermissionAccess, UserExtraService } from '../user-extra';
@Component( {
    selector: 'jhi-confirmation-devis',
    templateUrl: './confirmation-modification-prix.component.html'
} )
export class ConfirmationModificationPrixComponent implements OnInit, OnDestroy {
    currentAccount: any;
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: SinisterPec[] = [];
    account: Account;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

     constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private primaryQuotationService: PrimaryQuotationService,
        private eventManager: JhiEventManager,
        private userExtraService: UserExtraService
    ) {
        
    }
    loadAll() {
        
        this.prestationPECService.queryConfirmationModificationPrix(this.currentAccount.id).subscribe(
            ( res: ResponseWrapper ) => {
                this.sinisterPecsBeingProcessed = res.json;

                this.sinisterPecsBeingProcessed.forEach(sinPec => {
                    const opdate = new Date(sinPec.declarationDate);
                    sinPec.declarationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' +opdate.getHours() + ':' + opdate.getMinutes()+ ':' + opdate.getSeconds());
                });
                this.dtTrigger.next(); // Actualize datatables
                },
                ( res: ResponseWrapper ) => this.onError( res.json )
                );
    }

       
    ngOnInit() { 
        
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.principal.identity().then(( account ) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(102, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
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
                this.router.navigate(['/confirmation-modification-prix/'+prestationPec.id]);
            }
      
       }  


}