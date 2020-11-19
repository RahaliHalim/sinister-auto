import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { Dossiers} from './dossiers.model';
import { SinisterService } from './sinister.service';
import { ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
@Component( {
    selector: 'jhi-sinister',
    templateUrl: './dossiers.component.html'
} )
export class DossiersComponent implements OnInit {

    currentAccount: any;
    dossiers: Dossiers[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    authorities: any;
    isAgentGeneral: any;
    dtOptions: any = {};
    dtTrigger: Subject<Sinister> = new Subject();
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
        this.sinisterService.queryDossiers().subscribe(
            (res: ResponseWrapper) => {
                this.dossiers = res.json;
                console.log("alert"+ this.dossiers);
                this.dossiers.forEach(element => {
                    //console.log("test Date" + element.dateUpload);
                    const opdate = new Date(element.incidentDate);
                    console.log("date**"+ opdate);
                    var month = new Array();
                    var mois = (opdate.getMonth());
                    month[0] = "Janvier";
                    month[1] = "Février";
                    month[2] = "Mars";
                    month[3] = "Avril";
                    month[4] = "Mai";
                    month[5] = "Juin";
                    month[6] = "Juillet";
                    month[7] = "août";
                    month[8] = "septembre";
                    month[9] = "Octobre";
                    month[10] = "Novembre";
                    month[11] = "Décembre";
                    element.incidentMois = month[mois];
                    element.nomPrenomRaison= element.isCompany ? element.raisonSociale : (element.prenom + ' ' + element.nom);
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