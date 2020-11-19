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
import { RefCompagnieService } from '../ref-compagnie';

@Component({
    selector: "jhi-pec-monitoring-prestation",
    templateUrl: "./pec-monitoring-prestation.component.html"
})
export class PecMonitoringPrestationComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    dtOptions: any = {};
    dtTrigger: Subject<PecMonitoringPrestation> = new Subject();

    currentAccount: any;
    sinisterPrestations: PecMonitoringPrestation[];
    sinisterPrestationsOrig: PecMonitoringPrestation[];
    partners: Partner[];
    governorates: Governorate[];
    managmentModes: RefModeGestion[];
    search: Search = new Search();

    constructor(
        private reportsService: ReportsService,
        private partnerService: PartnerService,
        private governorateService: GovernorateService,
        private modeGestionService: RefModeGestionService,
        private alertService: JhiAlertService
    ) { }

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

        // Init datatable options
        this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        this.dtOptions.scrollX = true;
        this.partnerService.allPartners().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
            }
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

    /**
     * Load all sinister
     */

    loadAll() {
        this.reportsService
            .findPecMonitoringPrestation(this.search)
            .subscribe(
                (res: PecMonitoringPrestation[]) => {
                    this.sinisterPrestations = res;
                    this.sinisterPrestations.forEach(element => {
                        if (element.dateChangeStatut == null) {
                            element.dateInter = element.dateReserves;
                        } else if (element.dateReserves == null) {
                            element.dateInter = element.dateChangeStatut;
                        }
                        else {
                            element.dateInter = element.delaiMoyenLeveReserve;
                        }

                        element.cngFr = "";
                        if (element.cng == true) { element.cngFr = "Oui "; }
                        else if (element.cng == false) { element.cngFr = "Non "; }



                    });

                    console.log(this.sinisterPrestations);
                    this.sinisterPrestationsOrig = res;
                    this.sinisterPrestations = this.sinisterPrestationsOrig.filter(this.filterArray.bind(this));

                    var cache = {};
                    this.sinisterPrestations = this.sinisterPrestations.filter(function (elem) {
                        return cache[elem.id] ? 0 : cache[elem.id] = 1;
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
        const zoneFlt = this.search.zoneId != undefined ? this.search.zoneId == element.agencyGovernorateId : true;
        const managmentModeFlt = this.search.managmentModeId != undefined ? this.search.managmentModeId == element.managmentModeId : true;
        const cngFlt = this.search.cng != undefined ? this.search.cng == element.cng : true;
        return partnerFlt && zoneFlt && managmentModeFlt && cngFlt;
    }
    changeMode(value) {
        console.log("mode" + value);

        this.modeGestionService.findModesGestionByClient(value).subscribe(
            (res: ResponseWrapper) => {
                this.managmentModes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }


    clear() {

        this.modeGestionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.managmentModes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.search = new Search();
        this.reportsService.queryPecMonitoringPrestation().subscribe(
            (res: ResponseWrapper) => {

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
