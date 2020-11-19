import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { ViewSinister } from './view-sinister.model';
import { SinisterService } from './sinister.service';
import { ResponseWrapper, Principal } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { HttpClient } from "@angular/common/http";

class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}
@Component( {
    selector: 'jhi-sinister',
    templateUrl: './sinister.component.html'
} )
export class SinisterComponent implements OnInit, OnDestroy {

    currentAccount: any;
    sinisters: ViewSinister[];
    error: any;
    success: any;
    authorities: any;
    isAgentGeneral: any;
    permissionToAccess: PermissionAccess = new PermissionAccess();

    dtOptions: any = {};
    dtTrigger: Subject<ViewSinister> = new Subject();

    constructor(
        private sinisterService: SinisterService,
        private alertService: JhiAlertService,
        private userExtraService: UserExtraService,
        private principal: Principal,
        private http: HttpClient

    ) {
    }

    /**
     * Init sinister list screen
     */
    // ngOnInit() {
    //     // Init datatable options
    //     this.dtOptions = GaDatatable.defaultDtOptions;

    //     this.loadAll();
    // }
    ngOnInit() {
        console.log("_____________________________________________________");
        const that = this;
        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            serverSide: true,
            processing: true, 
            ajax: (dataTablesParameters: any, callback) => {
                that.http
                    .post<DataTablesResponse>(
                        "api/view/sinister/page",
                        dataTablesParameters,
                        {}
                    )
                    .subscribe((resp) => {
                        that.sinisters = resp.data;
                        console.log("*****"+this.sinisters.length);

                        callback({
                            recordsTotal: resp.recordsTotal,
                            recordsFiltered: resp.recordsFiltered,
                            data: [],
                        });
                    });
            },
            columns: [
                // { data: "id" },
                { data: "reference" },
                { data: "incidentDate" },
                { data: "registrationNumber" },
                { data: "fullName" },
                { data: "nature" },
                { data: "serviceTypes" },
                { data: "creationDate" }
                
            ],
            dom:
                '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6"p>>',

            language: {
                processing: "Traitement en cours...",
                search: "Rechercher&nbsp;:",
                lengthMenu: "Afficher _MENU_ &eacute;l&eacute;ments",
                info: "_START_ - _END_ / _TOTAL_",
                infoEmpty: "La liste est vide",
                infoFiltered:
                    "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
                infoPostFix: "",
                loadingRecords: "Chargement en cours...",
                zeroRecords: "...",
                emptyTable: "...",
                paginate: {
                    first:
                        '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                    previous:
                        '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                    next:
                        '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                    last:
                        '<i class="fa fa-angle-double-right" style="font-size:16px"></i>',
                },
                aria: {
                    sortAscending:
                        ": activer pour trier la colonne par ordre croissant",
                    sortDescending:
                        ": activer pour trier la colonne par ordre décroissant",
                },
            },
            // Declare the use of the extension in the dom parameter
            //dom: 'Bfrtip',
            // Configure the buttons
            buttons: [
                {
                    extend: "print",
                    text:
                        '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> ',
                },
                {
                    extend: "pdfHtml5",
                    text:
                        '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                    orientation: "landscape",
                    pageSize: "LEGAL",
                },
                {
                    text:
                        '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>',
                    key: "1",
                     action: function (e, dt, node, config) {
                        that.sinisterService
                            .exportSinisterToExcel(dt.search())
                            .subscribe(
                                (blob: Blob) => {
                                    let fileName = "Dossier_sinister.xlsx";
                                    if (
                                        window.navigator &&
                                        window.navigator.msSaveOrOpenBlob
                                    ) {
                                        window.navigator.msSaveOrOpenBlob(
                                            blob,
                                            fileName
                                        );
                                    } else {
                                        var a = document.createElement("a");
                                        a.href = URL.createObjectURL(blob);
                                        a.download = fileName;
                                        document.body.appendChild(a);
                                        a.click();
                                        document.body.removeChild(a);
                                    }
                                },
                                (err) => {
                                    alert(
                                        "Error while downloading. File Not Found on the Server"
                                    );
                                }
                            );
                     },
                },
            ],
        };
        /*this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        this.dtOptions.serverSide = true;
        this.dtOptions.processing = true;
        this.dtOptions.ajax = (dataTablesParameters: any, callback) => {
            that.http
              .post<DataTablesResponse>(
                'http://localhost:9000/api/view/sinister/page',
                dataTablesParameters, {}
              ).subscribe(resp => {
                that.sinisters = resp.data;

                callback({
                  recordsTotal: resp.recordsTotal,
                  recordsFiltered: resp.recordsFiltered,
                  data: []
                });
              });
        };*/
        //this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService
                .findFunctionnalityEntityByUser(34, this.currentAccount.id)
                .subscribe((res) => {
                    this.permissionToAccess = res;
                });
        });

        //this.registerChangeInContratAssurances();
    }

    /**
     * Load all sinister
     */
    loadAll() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(24, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        this.sinisterService.findAll().subscribe(
            ( res: ResponseWrapper ) => this.onSuccess( res.json, res.headers ),
            ( res: ResponseWrapper ) => this.onError( res.json )
        );
    }
        )};
    private onSuccess( data, headers ) {
        this.sinisters = data;
        this.dtTrigger.next();
    }
    private onError( error ) {
        this.alertService.error( this.error.message, null, null );
    }

    ngOnDestroy() {
    }

}
