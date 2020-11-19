import {
    Component,
    OnInit,
    OnDestroy,
    AfterViewInit,
    ViewChild,
} from "@angular/core";
import { DataTableDirective } from "angular-datatables";
import { Assitances } from "../../sinister/assitances.model";
import { Subject } from "rxjs";
import { GaDatatable } from "../../../constants/app.constants";
import { Dossiers, PriseEnCharges, Search } from "../../sinister";
import { Subscription } from "rxjs/Rx";
import {
    JhiParseLinks,
    JhiAlertService,
    JhiEventManager,
    JhiPaginationUtil,
} from "ng-jhipster";
import { Principal, ResponseWrapper, AlertUtil } from "../../../shared";
import { ActivatedRoute, Router } from "@angular/router";
import { PaginationConfig } from "../../../blocks/config/uib-pagination.config";
import { NgProgress } from "ngx-progressbar";
import { SinisterService } from "../../sinister/sinister.service";
import { Recherche } from "../../sinister/recherche.model";
import { NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { SinisterPecPopupService } from "../../sinister-pec";
import { HistoryAssistanceComponent } from "../history-assistance/history-assistance.component";
import { HttpClient } from "@angular/common/http";
import { PermissionAccess, UserExtraService } from "../../user-extra";

class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}
@Component({
    selector: "jhi-assistance",
    templateUrl: "./assistance.component.html",
    styles: [],
})
export class AssistanceComponent implements OnInit, OnDestroy, AfterViewInit {
    currentAccount: any;
    success: any;
    authorities: any[];

    @ViewChild(DataTableDirective)
    datatableElement1: DataTableDirective;
    //@ViewChild(DataTableDirective)
    datatableElement2: DataTableDirective;
    //@ViewChild(DataTableDirective)
    datatableElement3: DataTableDirective;
    assitances: Assitances[];
    dossiers: Dossiers[];
    priseencharges: PriseEnCharges[];
    eventSubscriber: Subscription;
    dtOptions1: any = {};
    dtOptions2: any = {};
    dtOptions3: any = {};
    dtTrigger1: Subject<Assitances> = new Subject();
    dtTrigger2: Subject<Dossiers> = new Subject();
    dtTrigger3: Subject<PriseEnCharges> = new Subject();
    // dtOptions: DataTables.Settings[] = [];
    dtOptions: any = {};

    error: any;
    private ngbModalRef: NgbModalRef;
    selectedRow: Number;
    setClickedRow: Function;
    typeService: String;
    searchParams: Search = new Search();

    /* immatriculation: string;
    startDate: any;
    endDate: any;
    types: any;*/
    recherche = new Recherche();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private principal: Principal,
        private router: Router,
        private route: ActivatedRoute,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private sinisterService: SinisterService,
        public ngProgress: NgProgress,
        private http: HttpClient,
        private siniterPecPopupService: SinisterPecPopupService,
        private userExtraService: UserExtraService
    ) {
        this.setClickedRow = function (index) {
            this.selectedRow = index;
        };
    }
    /*  ngOnInit() {
        this.dtOptions[0]=GaDatatable.defaultDtOptions;
        this.dtOptions[0].scrollX=true;
        this.dtOptions[1]=GaDatatable.defaultDtOptions;
        this.dtOptions[2]=GaDatatable.defaultDtOptions;
        this.loadAll();
        //this.reg
    }*/
    ngOnInit() {
        console.log("_____________________________________________________");
        this.route.params.subscribe((params) => {
            if (params['typeService']) {

                if (params['typeService'] == 'vr') {
                    this.searchParams.typeService = 'vr';
                }
                else
                    this.searchParams.typeService = 'assistance';

            }
        });
        const that = this;
        that.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            serverSide: true,
            processing: true,
            ajax: (dataTablesParameters: any, callback) => {
                that.searchParams.dataTablesParameters = dataTablesParameters;
                that.http
                    .post<DataTablesResponse>(
                        "api/view-assitances/page",
                        that.searchParams,
                        {}
                    )
                    .subscribe((resp) => {
                        that.assitances = resp.data;
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
                { data: "creationDate" },
                { data: "incidentDate" },

                { data: "incidentNature" },
                { data: "companyName" },
                { data: "nomAgentAssurance" },
                { data: "marque" },
                { data: "immatriculationVehicule" },
                { data: "nomPrenomRaison" },

                { data: "typeService" },
                { data: "remorqueur" },
                { data: "tugAssignmentDate" },
                { data: "tugArrivalDate" },
                { data: "insuredArrivalDate" },
                { data: "price_ttc" },
                { data: "etatPrestation" },
                { data: "charge" },
                { data: "motif" },
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
                            .exportAssitancesToExcel(that.searchParams)
                            .subscribe(
                                (blob: Blob) => {
                                    let fileName = "Assistances.xlsx";
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
                'http://localhost:9000/api/view-policies/page',
                dataTablesParameters, {}
              ).subscribe(resp => {
                that.policies = resp.data;

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

    ngAfterViewInit(): void {
        this.dtTrigger1.next();
        this.dtTrigger2.next();
        this.dtTrigger3.next();
    }

    cancel(recherches) {
        recherches.immatriculation = undefined;
        recherches.startDate = undefined;
        recherches.endDate = undefined;
    }

    loadAll() {
        this.ngProgress.start();
        this.sinisterService.queryAssitances().subscribe(
            (res: ResponseWrapper) => {
                this.ngProgress.done();
                this.assitances = null;
                this.assitances = res.json;

                this.assitances.forEach((element) => {
                    element.nomPrenomRaison = element.isCompany
                        ? element.raisonSociale
                        : element.prenom + " " + element.nom;
                });

                //this.dtTrigger1.next();
                this.rerender1();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadAll1() {
        // calculate boolean
        if (this.principal.isAuthenticated()) {
        }
    }

    rerender1(): void {
        this.datatableElement1.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger1.next();
        });
    }
    rerender2(): void {
        this.datatableElement2.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger2.next();
        });
    }
    rerender3(): void {
        this.datatableElement3.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger3.next();
        });
    }

    private onError(error) {
        this.alertUtil.addError(this.error.message);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    ngOnDestroy(): void { }

    decider() {
        this.ngbModalRef = this.siniterPecPopupService.openHistoryModal(
            HistoryAssistanceComponent as Component
        );
    }
}
