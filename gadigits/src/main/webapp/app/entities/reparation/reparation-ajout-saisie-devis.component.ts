import { Component, OnInit,Pipe, OnDestroy, NgZone as zone, NgZone } from '@angular/core';
import {Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPecService,SinisterPec } from '../sinister-pec';
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from './../../constants/app.constants';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
import { UserExtraService, PermissionAccess} from '../user-extra';
import * as Stomp from 'stompjs';
@Component( {
    selector: 'jhi-reparation-ajout-saisie-devis',
    templateUrl: './reparation-ajout-saisie-devis.component.html',
 
} )

export class ReparationAjoutSaisieDevisComponent implements OnInit, OnDestroy {
    currentAccount: any;
    eventSubscriber: Subscription;
    sinisterPecsBeingProcessed: ViewSinisterPec[];
    account: Account;
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess : PermissionAccess = new PermissionAccess();

     constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private viewSinisterPecService: ViewSinisterPecService,
        private eventManager: JhiEventManager,
        private userExtraService : UserExtraService     
    ) {
       
    }
    loadAll() {
        this.viewSinisterPecService.getAllForReparator(this.currentAccount.id).subscribe(
            ( res: ResponseWrapper ) => {
            this.sinisterPecsBeingProcessed = res.json;
            this.dtTrigger.next(); 
            // Actualize datatables
      
        }, ( res: ResponseWrapper ) => this.onError( res.json )
            );
    } 
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log( this.currentAccount.id);
            this.userExtraService.findFunctionnalityEntityByUser(42,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
                this.loadAll();
            });
        });
        this.registerChangeInPrestationPECS(); 
    }

    


    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }
    trackId( index: number, item: ViewSinisterPec ) {
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

 
   
        actionReparateur(prestationPec) {
       
        if (prestationPec !== null) {
            this.router.navigate(['/devis-new/'+prestationPec.id]);
        }
  
   } 
       

}