import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { PriseEnCharges} from './priseencharge.model';
import { SinisterService } from './sinister.service';
import { ResponseWrapper } from '../../shared';
//import { ProfilService } from '../profil/profil.service';
import { GaDatatable } from '../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
@Component( {
    selector: 'jhi-sinister',
    templateUrl: './priseencharge.component.html'
} )
export class PriseEnChargeComponent implements OnInit {

    currentAccount: any;
    priseencharges: PriseEnCharges[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    authorities: any;
    isAgentGeneral: any;
    listFeaturesSinister: any;
    editSinister = false;
    deleteSinister = false;
    createSinister = false;
    consultSinister = false;
    dtOptions: any = {};
    dtTrigger: Subject<PriseEnCharges> = new Subject();
    dtElement: DataTableDirective;

    constructor(
        private sinisterService: SinisterService,
        //private profilService: ProfilService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        // Init datatable options
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        //this.getFeatureProfile();

        
       // this.registerChangeInSinisters();
    }

    /**
     * Get current user access features
     */
   /* private getFeatureProfile(){
        this.profilService.findFeaturesByProfile().subscribe((res) => {
            this.listFeaturesSinister = res;
            for (let i = 0; i <  this.listFeaturesSinister.length; i++) {
                if ( this.listFeaturesSinister[i].id === 4) {
                    this.editSinister = true;
                }
                if ( this.listFeaturesSinister[i].id === 2) {
                    this.deleteSinister = true;
                }
                if ( this.listFeaturesSinister[i].id === 1) {
                    this.createSinister = true;
                }
                if ( this.listFeaturesSinister[i].id === 3) {
                    this.consultSinister = true;
                }
            }
        });
    }*/

    /**
     * Load all sinister
     */
    /*loadAll() {
        this.sinisterService.query().subscribe(
            ( res: ResponseWrapper ) => this.onSuccess( res.json, res.headers ),
            ( res: ResponseWrapper ) => this.onError( res.json )
        );
    }*/

    loadAll() {
        this.sinisterService.queryPriseEncharge().subscribe(
            (res: ResponseWrapper) => {
                this.priseencharges = res.json;
                this.dtTrigger.next();
                this.rerender();
          
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        }
        rerender(): void {
            this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                dtInstance.destroy();
                this.dtTrigger.next();
            });
        } 

    registerChangeInSinisters() {
        this.eventSubscriber = this.eventManager.subscribe( 'sinisterListModification', ( response ) => this.loadAll() );
    }

    /*private onSuccess( data, headers ) {
        this.sinisters = data;

        if (this.sinisters && this.sinisters.length > 0) {
            this.sinisters.forEach((sinister) => { // Iterate over sinisters
                if (sinister.prestations && sinister.prestations.length > 0) {
                    sinister.serviceTypesStr = '';
                    sinister.prestations.forEach((prestation) => { // Iterate over prestation
                        sinister.serviceTypesStr += prestation.serviceTypeLabel + ', ';
                    });
                    sinister.serviceTypesStr = sinister.serviceTypesStr.substring(0, sinister.serviceTypesStr.length - 2);
                //this.packslist.push(pack);
                }
            });
        }

        this.dtTrigger.next();
    }*/
    private onError( error ) {
        this.alertService.error( this.error.message, null, null );
    }

    /*ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }*/

}