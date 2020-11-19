import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subject } from 'rxjs';
import { JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { Search } from './search.model';
import { PartnerService } from './../partner/partner.service';
import { Partner } from './../partner/partner.model';
import { ReportTugPerformance } from './report-tug-performance.model';
import { RefRemorqueurService, RefRemorqueur } from './../ref-remorqueur';
import { ReportAssistanceService } from './report-assistance.service';
import { ResponseWrapper } from '../../shared';
import { saveAs } from 'file-saver/FileSaver';
import { RefCompagnieService } from '../ref-compagnie';
import { Governorate, GovernorateService } from '../governorate';
import { HttpClient } from '@angular/common/http';
import { DataTableDirective } from 'angular-datatables';

class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}

@Component({
    selector: "jhi-report-tug-performance",
    templateUrl: "./report-tug-performance.component.html"
})
export class ReportTugPerformanceComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    reportTugPerformances: ReportTugPerformance[];
    reportTugPerformancesOrig: ReportTugPerformance[];
    partners: Partner[];
    tugs: RefRemorqueur[];
    operators = [{ key: 0, label: '=' }, { key: 1, label: '>' }, { key: 2, label: '<' }];
    dmis: any[];
    searchParams: Search = new Search();
    error: any;
    success: any;
    governorate: Governorate[];

    dtOptions: any = {};

    constructor(
        private reportAssistanceService: ReportAssistanceService,
        private partnerService: RefCompagnieService,
        private tugService: RefRemorqueurService,
        private alertService: JhiAlertService,
        private governorateService: GovernorateService,
        private http: HttpClient,
        private dateUtils: JhiDateUtils
    ) { }

    /**
     * Init sinister list screen
     */
    ngOnInit() {

        this.partnerService.getAgreementCompany().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
            }
        );
        this.tugService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tugs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );


        this.governorateService.query().subscribe((res: ResponseWrapper) => {
            this.governorate = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));


        // Build dmi
        this.dmis = [];
        const step = 15;
        for (let i = 1; i < 97; i++) {
            const tmin = i * step; // total minute
            const hours = Math.floor(tmin / 60); // hours number
            const mins = tmin % 60; // minutes number
            this.dmis.push({ key: tmin, label: (hours != 0 ? hours + 'h' : '') + (mins != 0 ? mins + 'min' : '') });
        }
        this.loadAll();

    }

    loadAll() {
        console.log('_____________________________________________________');
        const that = this;
        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            serverSide: true,
            processing: true,
            ajax: (dataTablesParameters: any, callback) => {
                that.searchParams.dataTablesParameters = dataTablesParameters;
                that.searchParams.startDate = this.dateUtils.convertLocalDateToServer(that.searchParams.startDateNC);
                that.searchParams.endDate = this.dateUtils.convertLocalDateToServer(that.searchParams.endDateNC);
                that.http
                    .post<DataTablesResponse>(
                        'api/report/assistance/report-tug-performance/page',
                        that.searchParams, {}
                    ).subscribe(resp => {
                        that.reportTugPerformances = resp.data;
                        /*that.reportTugPerformancesOrig = resp.data;
                        that.reportTugPerformances = this.reportTugPerformancesOrig.filter(this.filterArray.bind(this));*/
                        //console.log(that.reportTugPerformances);
                        that.reportTugPerformances.forEach(element => {
                            if (element.hasHabillage == true) { element.habillage = "Oui"; }
                            else if (element.hasHabillage == false) { element.habillage = "Non"; }
                        });

                        callback({
                            recordsTotal: resp.recordsTotal,
                            recordsFiltered: resp.recordsFiltered,
                            data: []
                        });
                    });
            },
            columns: [//{ data: 'id' },
                { data: 'affectedTugLabel' },
                { data: 'reference' },
                { data: 'creationDate' },
                { data: 'creationUser' },
                { data: 'registrationNumber' },
                { data: 'partnerName' },
                { data: 'serviceTypeLabel' },
                { data: 'usageLabel' },
                { data: 'tugAssignmentDate' },
                { data: 'tugArrivalDate' },
                { data: 'insuredArrivalDate' },
                { data: 'interventionTimeAvg' },
                { data: 'delaiOperation' },
                { data: 'habillage' },
                { data: 'incidentGovernorateLabel' },
                { data: 'priceTtc' },
                { data: 'cancelGroundsDescription' },
                { data: 'cancelDate' },
                { data: 'closureDate' },
                { data: 'closureUser' }],
            dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6"p>>',

            language: {
                processing: 'Traitement en cours...',
                search: 'Rechercher&nbsp;:',
                lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
                info: '_START_ - _END_ / _TOTAL_',
                infoEmpty: 'La liste est vide',
                infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
                infoPostFix: '',
                loadingRecords: 'Chargement en cours...',
                zeroRecords: '...',
                emptyTable: '...',
                paginate: {
                    first: '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                    previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                    next: '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                    last: '<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
                },
                aria: {
                    sortAscending: ': activer pour trier la colonne par ordre croissant',
                    sortDescending: ': activer pour trier la colonne par ordre décroissant'
                }
            },
            // Declare the use of the extension in the dom parameter
            //dom: 'Bfrtip',
            // Configure the buttons
            buttons: [
                {
                    extend: 'print',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
                },
                {
                    extend: 'pdfHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                    orientation: 'landscape',
                    pageSize: 'LEGAL'
                },
                {
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>',
                    key: '1',
                    action: function (e, dt, node, config) {
                        that.reportAssistanceService.exportTugPerfToExcel(that.searchParams).subscribe((blob: Blob) => {
                            let fileName = "Performance_du_remorqueur.xlsx";
                            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                                window.navigator.msSaveOrOpenBlob(blob, fileName);
                            } else {
                                var a = document.createElement('a');
                                a.href = URL.createObjectURL(blob);
                                a.download = fileName;
                                document.body.appendChild(a);
                                a.click();
                                document.body.removeChild(a);
                            }
                        },
                            err => {
                                alert("Error while downloading. File Not Found on the Server");
                            });
                    }
                }
            ]

        };

    }

    /**
     * Load all sinister
     */
    /*loadAll() {
        this.reportAssistanceService
            .findReportTugPerformance(this.search)
            .subscribe(
                (res: ReportTugPerformance[]) => {
                    this.reportTugPerformancesOrig = res;
                    this.reportTugPerformances = this.reportTugPerformancesOrig.filter(this.filterArray.bind(this));
                    this.reportTugPerformances.forEach(element => {
                        console.log("hey");

                        if (element.hasHabillage == true) { element.habillage = "Oui"; }

                        else if (element.hasHabillage == false) { element.habillage = "Non"; }




                    });
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
                            messageBottom: 'Total : ' + (res ? res.length : 0) + ' prestation(s)',
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
    }*/

    filter() {

        this.loadAll();
        this.rerender();
    }

    filterArray(element, index, array) {
        console.log('___________________________________________________________');

        const partnerFlt = this.searchParams.compagnieId != undefined ? this.searchParams.compagnieId == element.partnerId : true;
        const zoneFlt = this.searchParams.zoneId != undefined ? this.searchParams.zoneId == element.zoneId : true;
        const tugFlt = this.searchParams.tugId != undefined ? this.searchParams.tugId == element.affectedTugId : true;
        let dmiFlt = true;
        if (this.searchParams.operator != undefined && this.searchParams.dmi != undefined) {
            switch (this.searchParams.operator) {
                case 0: {
                    dmiFlt = element.interventionTimeAvgMin != undefined ? element.interventionTimeAvgMin == this.searchParams.dmi : false;
                    break;
                }
                case 1: {
                    dmiFlt = element.interventionTimeAvgMin != undefined ? element.interventionTimeAvgMin > this.searchParams.dmi : false;
                    break;
                }
                case 2: {
                    dmiFlt = element.interventionTimeAvgMin != undefined ? element.interventionTimeAvgMin < this.searchParams.dmi : false;
                    break;
                }
                default: {
                    //statements; 
                    break;
                }
            }
        }

        return partnerFlt && tugFlt && dmiFlt && zoneFlt;
    }

    clear() {

        this.searchParams = new Search();
        this.loadAll();
        this.rerender();
    }

    ngAfterViewInit(): void {
    }

    rerender(): void {
        try {
            this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                dtInstance.ajax.reload();
            });
        } catch (err) {
            console.log(err);
        }
    }


    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
    }
}
