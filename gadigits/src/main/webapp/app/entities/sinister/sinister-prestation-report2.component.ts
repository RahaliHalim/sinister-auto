import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiAlertService ,JhiDateUtils} from 'ng-jhipster';

import { SinisterService } from './sinister.service';
import { ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { Partner, PartnerService } from './../partner';
import { Search } from './search.model';
import { SinisterPrestation } from './sinister-prestation.model';
import { Sinister } from './sinister.model';
import { ReportService } from '../report/report.service';
import { ReportFrequency } from './reportFrequency.model';
import { ReportTugPerformance } from './report-tug-performance.model';
import { HttpClient } from '@angular/common/http';
class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}
@Component({
    selector: "jhi-sinister-prestation-report2",
    templateUrl: "./sinister-prestation-report2.component.html"
})
export class SinisterPrestationReport2Component implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    dtOptions: any = {};
    dtTrigger: Subject<Sinister> = new Subject();
   
    currentAccount: any;
    sinisters: Sinister[];
    partners: Partner[];
    search: Search = new Search();
    reportFreq : ReportFrequency [];
    reportFrequency: ReportFrequency[];


    constructor(
        private sinisterService: SinisterService,
        private partnerService: PartnerService,
        private alertService: JhiAlertService,
        private http: HttpClient,
        private reportService : ReportService,
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
        this.search = new Search();

        this.loadAll();


           /*     this.reportService.queryTauxDeFreq().subscribe(
                    (res : ResponseWrapper) => { 
                         this.reportFreq = res.json;
                       } ); */

                       console.log(this.sinisters);

    }

    ngAfterViewInit(): void {
       // this.dtTrigger.next();
    }

    /*rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }*/
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
                        'api/sinister/report2/page',
                        that.search, {}
                    ).subscribe(resp => {
                        that.reportFrequency = resp.data;
                        console.log(that.reportFrequency);
                    
                        callback({
                            recordsTotal: resp.recordsTotal,
                            recordsFiltered: resp.recordsFiltered,
                            data: []
                        });
                    });
            },
            columns: [//{ data: 'id' },
                { data: 'partnerLabel' },
                { data: 'usageLabel' },
                { data: 'nature' },
                { data: 'frequencyRate' }
               ],
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
                        that.reportService.exportReport2ServiceToExcel(that.search).subscribe((blob: Blob) => {
                            let fileName = "Taux_de_fréquence.xlsx";
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
   /* loadAll() {
        this.sinisterService
            .findReport2Prestations(this.search)
            .subscribe(
                (res: Sinister[]) => {
                    this.sinisters = res;
                    this.dtOptions.buttons = [
                        {
                            extend: 'print',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
                        },
                        {
                            extend: 'csvHtml5',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                            fieldSeparator: ';'
                        },  {
                            extend: 'excelHtml5',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>',
                            autoFilter: true
                        },
                       
                        {
                            extend: 'pdfHtml5',
                            text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                            orientation: 'landscape'                        }
                    ];
                     console.log(res);
                    this.rerender();
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }*/

    filter() {
        this.loadAll();
        this.rerender();

    }

   /* clear() {
      
        this.search = new Search();
        this.loadAll();
    } */
    clear() {

        this.search = new Search();
        this.loadAll();
        this.rerender();
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
