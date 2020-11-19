import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiAlertService } from 'ng-jhipster';
import { saveAs } from 'file-saver/FileSaver';
import { ResponseWrapper } from '../../shared';

import { Partner, PartnerService } from './../partner';
import { Search } from '../sinister/search.model';
import { AssistanceMonitoringCompany } from './assistance-monitoring-company.model';
import { ReportsService } from './reports.service';
import { GaDatatable } from '../../constants/app.constants';

@Component({
    selector: "jhi-assistance-monitoring-company",
    templateUrl: "./assistance-monitoring-company.component.html"
})
export class AssistanceMonitoringCompanyComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    dtOptions: any = {};
    dtTrigger: Subject<AssistanceMonitoringCompany> = new Subject();

    currentAccount: any;
    sinisterPrestations: AssistanceMonitoringCompany[];
    partners: Partner[];
    search: Search = new Search();

    constructor(
        private reportsService: ReportsService,
        private partnerService: PartnerService,
        private alertService: JhiAlertService
    ) {}

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        // Init datatable options
        this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        this.dtOptions.scrollX = true;
        this.partnerService.query().subscribe(
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

    /**
     * Load all sinister
     */
    loadAll() {
        this.reportsService
            .findReport1Prestations(this.search)
            .subscribe(
                (res: AssistanceMonitoringCompany[]) => {
                    this.sinisterPrestations = res;
                    let totalCount = res ? res.length : 0;
                    let totalPrice = 0;
                    let avgPrice = "0.000";
                    if(totalCount > 0) {
                        for (var i = 0; i < res.length; i++) {
                            totalPrice += res[i].priceTtc;
                        }
                        avgPrice = (totalPrice/totalCount).toFixed(3); 
                    }
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
                            messageBottom: function() {
                                return  '\n Total de prestation : ' + totalCount + ' prestation(s) \r\n' +
                                        '\n Total Coût : ' + totalPrice + '\r\n' +
                                        '\n Coût moyen : ' + avgPrice;
                                },
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

    clear() {
        this.search = new Search();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
    }
}
