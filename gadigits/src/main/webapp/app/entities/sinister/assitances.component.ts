import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { Assitances} from './assitances.model';
import { SinisterService } from './sinister.service';
import { ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
@Component( {
    selector: 'jhi-sinister',
    templateUrl: './assitances.component.html'
} )
export class AssitancesComponent implements OnInit {

    currentAccount: any;
    assitances: Assitances[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    authorities: any;
    isAgentGeneral: any;
    dtOptions: any = {};
    dtTrigger: Subject<Assitances> = new Subject();
    dtElement: DataTableDirective;

    constructor(
        private sinisterService: SinisterService,
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
    }

    /**
     * Load all sinister
     */
    loadAll() {
        this.sinisterService.queryAssitances().subscribe(
            (res: ResponseWrapper) => {
                this.assitances = res.json;

                this.assitances.forEach(element => {  
                 element.nomPrenomRaison= element.isCompany ? element.raisonSociale : (element.prenom + ' ' + element.nom);
                 console.log("assistances"+element.isCompany);
                });
                
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

    private onError( error ) {
        this.alertService.error( this.error.message, null, null );
    }
}