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

@Component( {
    selector: 'jhi-sinister-prestation-tug',
    templateUrl: './sinister-prestation-tug.component.html'
} )
export class SinisterPrestationTugComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    
    @Input() sinisterPrestation: SinisterPrestation;

    currentAccount: any;
    trucks: Camion[];
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
        private tugTruckService: CamionService,
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
        this.trucks = [];
        this.dtTrigger.next();
    }

    private onSuccess( data, headers ) {
        this.trucks = [];
        console.log("testLength " + this.trucks.length);
        this.trucks = data;
        console.log("testLength1 " + this.trucks.length);
        this.rerender();
        this.dtTrigger.next();
        /*if (data && data.length > 0) {
            data.forEach((truck: Camion) => {
                this.tugService.find(truck.refTugId).subscribe((tug: RefRemorqueur) => {
                    truck.refTug = tug;
                    this.trucks.push(truck);
                    //this.rerender();
                });
            });
        }*/
    }

    private onError( error ) {
        this.alertService.error( error.message, null, null );
    }

    ngOnDestroy() {
    }

    listTruckGouvernoratLieu(id) {
        
        if(this.idGovernorate !== null && this.idGovernorate !== undefined){
            this.tugTruckService.findByServiceTypeAndByGovernorate(this.sinisterPrestation.serviceTypeId, id ).subscribe(
                ( res: ResponseWrapper ) => this.onSuccess( res.json, res.headers ),
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
