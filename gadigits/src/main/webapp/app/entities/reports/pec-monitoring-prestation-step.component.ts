import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiAlertService } from 'ng-jhipster';
import { saveAs } from 'file-saver/FileSaver';
import { ResponseWrapper } from '../../shared';

import { Partner, PartnerService } from './../partner';
import { Search } from '../sinister/search.model';
import { PecMonitoringPrestation } from './pec-monitoring-prestation.model';
import { ReportsService } from './reports.service';
import { GaDatatable } from '../../constants/app.constants';
import { Governorate, GovernorateService } from '../governorate';
import { RefModeGestion, RefModeGestionService } from '../ref-mode-gestion';
import { RefStepPec } from '../sinister-pec/refStepPec.model';
import { SinisterPecService } from '../sinister-pec';
import { StatusPecService, StatusPec } from '../status-pec';
import { ListCharge } from '../contrat-assurance/souscription-ga/listCharge.model';
import { ContratAssuranceService } from '../contrat-assurance';

@Component({
    selector: "jhi-pec-monitoring-prestation-step",
    templateUrl: "./pec-monitoring-prestation-step.component.html"
})
export class PecMonitoringPrestationStepComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    dtOptions: any = {};
    dtTrigger: Subject<PecMonitoringPrestation> = new Subject();
    listCharge : ListCharge[];

    currentAccount: any;
    sinisterPrestations: PecMonitoringPrestation[];
    sinisterPrestationsOrig: PecMonitoringPrestation[];
    partners: Partner[];
    governorates: Governorate[];
    managmentModes: RefModeGestion[];
    search: Search = new Search();
    RefStep : RefStepPec[] ;
    constructor(
        private reportsService: ReportsService,
        private partnerService: PartnerService,
        private governorateService: GovernorateService,
        private modeGestionService: RefModeGestionService,
        private alertService: JhiAlertService,
        private sinisterPecService : SinisterPecService,
        private contratAssuranceService : ContratAssuranceService

        ) {}

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        // Get governorate list
        this.governorateService.query().subscribe((res: ResponseWrapper) => {
            this.governorates = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        // Get managment mode list
        this.modeGestionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.managmentModes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        // Get Manager list

        this.contratAssuranceService.findAllListCharge().subscribe(
            (res: ResponseWrapper) => {
                this.listCharge = res.json;
            }
          );
        // Get Step list

           this.sinisterPecService.findStepPecByNumber().subscribe(
            (res: ResponseWrapper) => {
          
            this.RefStep = res.json;
          });

        // Init datatable options
        this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        //this.dtOptions.scrollX = true;
        this.partnerService.findAllCompanies().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

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
    changeMode(value) {
        console.log("mode"+value);
    
        this.modeGestionService.findModesGestionByClient(value).subscribe(
            (res: ResponseWrapper) => {
                this.managmentModes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    /**
     * Load all sinister
     */
    loadAll() {
        this.reportsService
            .findPecMonitoringPrestation(this.search)
            .subscribe(
                (res: PecMonitoringPrestation[]) => {
                    this.sinisterPrestations = res;
                    console.log(this.sinisterPrestations);
                    this.sinisterPrestationsOrig = res;
                    this.sinisterPrestations = this.sinisterPrestationsOrig.filter(this.filterArray.bind(this));
                    var cache = {};
                    this.sinisterPrestations = this.sinisterPrestations.filter(function (elem) {
                        return cache[elem.referenceGa] ? 0 : cache[elem.referenceGa] = 1;
                    })
                    
                    this.dtOptions.buttons = [
                        {
                            extend: 'print',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
                        },
                        {
                            extend: 'csvHtml5',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                            fieldSeparator: ';'
                        },
                        {
                            extend: 'excelHtml5',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>',
                            autoFilter: true,
                            customize: function (xlsx) {
                                var sheet = xlsx.xl.worksheets['sheet1.xml'];
                                $('row:first c', sheet).attr('s', '49');
                                // jQuery selector to add a border
                                $('row c*', sheet).attr('s', '25');
                            }
                        },
                        {
                            extend: 'pdfHtml5',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                            orientation: 'landscape',
                            pageSize: 'LEGAL'
                        }
                    ];

                    this.rerender();
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    filter() {
        this.loadAll();
    }

    filterArray(element, index, array) {
        console.log('___________________________________________________________');
        
        const partnerFlt = this.search.partnerId != undefined ? this.search.partnerId == element.partnerId : true;
        const etapeFlt = this.search.idEtapePrestation != undefined ? this.search.idEtapePrestation == element.stepId : true;
        const managmentModeFlt = this.search.managmentModeId != undefined ? this.search.managmentModeId == element.managmentModeId : true;
        const cngFlt = this.search.assignedToId != undefined ? this.search.assignedToId == element.assignedToId : true;

        return partnerFlt && etapeFlt && managmentModeFlt && cngFlt;
    }

    clear() {
        this.search = new Search();
        this.modeGestionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.managmentModes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.reportsService.queryPecMonitoringPrestation().subscribe(
            (res : ResponseWrapper) =>{
         
             this.sinisterPrestations = res.json;
             this.rerender();
            });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
    }
}
