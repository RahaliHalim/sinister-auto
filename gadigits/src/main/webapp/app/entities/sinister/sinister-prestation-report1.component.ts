import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { saveAs } from 'file-saver/FileSaver';

import { Partner, PartnerService } from './../partner';
import { Search } from './search.model';
import { ReportFollowUpAssistance } from './report-follow-up-assistance.model';
import { SinisterService } from './sinister.service';
import { ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { RefCompagnieService } from '../ref-compagnie';
import { HttpClient } from '@angular/common/http';

class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}

@Component({
    selector: "jhi-sinister-prestation-report1",
    templateUrl: "./sinister-prestation-report1.component.html"
})
export class SinisterPrestationReport1Component implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    dtOptions: any = {};
    //dtTrigger: Subject<ReportFollowUpAssistance> = new Subject();

    currentAccount: any;
    sinisterPrestations: ReportFollowUpAssistance[];
    partners: Partner[];
    search: Search = new Search();

    constructor(
        private sinisterService: SinisterService,
        private partnerService: PartnerService,
        private alertService: JhiAlertService,
        private http: HttpClient,
        private dateUtils: JhiDateUtils
    ) {}

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        // Init datatable options
        //this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        //this.dtOptions.scrollX = true;
        this.partnerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.loadAll();
    }

    ngAfterViewInit(): void {
        //this.dtTrigger.next();
    }

    /**
     * Load all sinister
     */

    loadAll() {
        console.log('_____________________________________________________');
        const that = this;
        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            serverSide: true,
            processing: true,
            ajax: (dataTablesParameters: any, callback) => {
                that.search.dataTablesParameters = dataTablesParameters;
                that.search.startDate = this.dateUtils.convertLocalDateToServer(that.search.startDateNC);
                that.search.endDate = this.dateUtils.convertLocalDateToServer(that.search.endDateNC);
                that.http
                    .post<DataTablesResponse>(
                        'api/sinister/prestation/report1/page',
                        that.search, {}
                    ).subscribe(resp => {
                        that.sinisterPrestations = resp.data;

                        callback({
                            recordsTotal: resp.recordsTotal,
                            recordsFiltered: resp.recordsFiltered,
                            data: []
                        });
                    });
            },
            columns: [//{ data: 'id' },
                { data: 'reference' },
                { data: 'creationDate' },
                { data: 'registrationNumber' },
                { data: 'fullName' },
                { data: 'partnerName' },
                { data: 'packLabel' },
                { data: 'usageLabel' },
                { data: 'serviceType' },
                { data: 'incidentDate' },
                { data: 'incidentNature' },
                { data: 'incidentMonth' },
                { data: 'affectedTugLabel' },
                { data: 'tugAssignmentDate' },
                { data: 'tugArrivalDate' },
                { data: 'insuredArrivalDate' },
                { data: 'interventionTimeAvg' },
                { data: 'incidentLocationLabel' },
                { data: 'destinationLocationLabel' },
                { data: 'mileage' },
                { data: 'prestationCount' },
                { data: 'priceTtc' },
                { data: 'interventionTimeAvgMin' },
                { data: 'statusLabel' },
                { data: 'creationUser' },
                { data: 'closureUser' },

                { data: 'heavyWeights' },
                { data: 'holidays' },
                { data: 'night' },
                { data: 'halfPremium' },
                { data: 'fourPorteF' },
                { data: 'fourPorteK' },
                { data: 'tugGovernorateLabel' }],
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
                        that.sinisterService.exportReport1ServicesToExcel(that.search).subscribe((blob: Blob) => {
                            let fileName = "Suivi_du_service_assistance.xlsx";
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


    /*loadAll() {
        this.sinisterService
            .findReport1Prestations(this.search)
            .subscribe(
                (res: ReportFollowUpAssistance[]) => {
                    this.sinisterPrestations = res;
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
                            orientation: 'landscape'                        }
                    ];

                    this.rerender();
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }*/

    rerender(): void {
        try {
            this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                dtInstance.ajax.reload();
            });
        } catch (err) {
            console.log(err);
        }
    }

    filter() {
        this.loadAll();
        this.rerender();
    }

    clear() {
        this.search = new Search();
        this.loadAll();
        this.rerender();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
    }
}
