import { Component, ViewChild, OnInit, OnDestroy, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPrestation } from './sinister-prestation.model';
import { RefRemorqueurService } from './../ref-remorqueur/ref-remorqueur.service';
import { SinisterService } from './sinister.service';
import { ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { CamionService, Camion } from '../camion';
import { TugPricing } from '../ref-tarif';
import { RefRemorqueur } from '../ref-remorqueur';
import { Delegation, DelegationService } from '../delegation';
import { Governorate, GovernorateService } from '../governorate';
import { LoueurService } from './../loueur/loueur.service';
import { Loueur } from '../loueur/loueur.model';
import { VehiculeLoueur } from '../vehicule-loueur';

@Component( {
    selector: 'sinister-prestation-loueur',
    templateUrl: './sinister-prestation-loueur.component.html'
} )
export class SinisterPrestationLoueurComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    
    @Input() prestation: SinisterPrestation;
    @Input() shortDuration: boolean;
    @Input() longDuration: boolean;


    currentAccount: any;
    loueurs: Loueur[];
    vehiculesLoueur: VehiculeLoueur[] = [];

    error: any;
    success: any;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPrestation> = new Subject();
    sysvilles: Delegation[];
    sysgouvernorats: Governorate[];
    idGovernorate: number;
    idDelegation: number;
    sysGouvernorat: Governorate;

    constructor(
        private sinisterService: SinisterService,
        private loueurService: LoueurService,
        private tugService: RefRemorqueurService,
        private alertService: JhiAlertService,
        public activeModal: NgbActiveModal,
        private sysVilleService: DelegationService,
        private sysGouvernoratService: GovernorateService,
    ) {
    }

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        // Init datatable options
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.sysVilleService.query().subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.loadAll();
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }

    /**
     * Load all sinister
     */
    loadAll() {
        this.loueurs = [];
        this.dtTrigger.next();
    }


    private onSuccess( data, headers ) {    
        this.loueurs = [];
        this.vehiculesLoueur = [];

        console.log("testLength " + this.loueurs.length);
        this.loueurs = data;
        console.log("testLength1 " + this.loueurs.length);
        console.log(data);
        if (data && data.length > 0) {
            data.forEach((loueur: Loueur) => {
                loueur.vehicules.forEach((v: VehiculeLoueur) => {
                    this.vehiculesLoueur.push(v);
                   
            });
        });
       }
        this.rerender();
        this.dtTrigger.next();       
    }

    private onError( error ) {
        this.alertService.error( error.message, null, null );
    }

    ngOnDestroy() {
    }

    listLoueurGouvernoratLieu() {
        
        if(this.idGovernorate !== null && this.idGovernorate !== undefined){
            this.loueurService.findByGovernorate( this.idGovernorate ).subscribe(
                ( res: ResponseWrapper ) => { console.log(res);
                    this.onSuccess( res.json, res.headers );},
                ( res: ResponseWrapper ) => this.onError( res.json )
            );
        }
        
    }
   


    trackGouvernoratById(index: number, item: Governorate) {
        return item.id;
    }

    trackSysVilleById(index: number, item: Delegation) {
        return item.id;
    }

}
