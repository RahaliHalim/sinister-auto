import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { PrestationPEC } from './reparation.model';
import { SinisterPec ,SinisterPecService} from '../sinister-pec';
import { Principal, ResponseWrapper } from '../../shared';
import { PrestationPecStep } from '../../constants/app.constants';
import {Router } from '@angular/router';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { QuoteStatus } from '../../constants/app.constants';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { ViewSinisterPecService, ViewSinisterPec } from '../view-sinister-pec';
@Component( {
    selector: 'jhi-verification',
    templateUrl: './rectification.component.html'
} )
export class RectificationComponent implements OnInit, OnDestroy {
    currentAccount: any;
    prestationPECS: SinisterPec[];
    eventSubscriber: Subscription;
    listePrestation: any[] = [];
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess : PermissionAccess = new PermissionAccess();
     constructor(
        private prestationPECService: SinisterPecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,
        private userExtraService : UserExtraService,
        private viewSinisterPecService: ViewSinisterPecService,
    ) {
        
    }
    loadAll() {
        this.viewSinisterPecService.getAllPecsForUpdateDevis(this.currentAccount.id).subscribe(
            ( res: ResponseWrapper ) => {
                console.log("length 1---------------------- " + res.json.length);
                this.listePrestation = res.json;
               
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
                // calculate boolean
        this.principal.identity().then(( account ) => {
            this.currentAccount = account;
            this.loadAll();
        } );
        this.registerChangeInPrestationPECS();
    }
    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }
    trackId( index: number, item: PrestationPEC ) {
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

    consulter(prestationPec: ViewSinisterPec) {
        
            if (prestationPec.id !== null && prestationPec.primaryQuotationId!== null) {
                this.router.navigate(['devis/' + prestationPec.id + '/edit/' + prestationPec.primaryQuotationId]);
            }
      
       }  

       consulterMP(prestationPec: ViewSinisterPec) {
        if (prestationPec.id !== null && prestationPec.primaryQuotationId!== null) {
            this.router.navigate(['/modification-prix/'+prestationPec.id]);
        }
  
   }  


}