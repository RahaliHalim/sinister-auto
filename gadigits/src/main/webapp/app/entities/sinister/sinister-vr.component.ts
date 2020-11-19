import { ViewSinisterPrestation } from "./view-sinister-prestation.model";
import {
    Component,
    OnInit,
    OnDestroy,
    ViewChild,
    AfterViewInit,
} from "@angular/core";
import { Subject } from "rxjs";
import { JhiAlertService } from "ng-jhipster";

import { SinisterService } from "./sinister.service";
import { ResponseWrapper, Principal } from "../../shared";
import { GaDatatable, StatusSinister } from "../../constants/app.constants";
import { PermissionAccess, UserExtraService } from "../user-extra";
import { HttpClient } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { DataTableDirective } from "angular-datatables";
import { SinisterPecService } from "../sinister-pec";
class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}
@Component({
    selector: "jhi-sinister-prestation-vr",
    templateUrl: "./sinister-vr.component.html",
})
export class SinisterVrComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    sinisterPrestations: ViewSinisterPrestation[];
    error: any;
    success: any;
    status: number;
    statusVr: number;

    permissionToAccess: PermissionAccess = new PermissionAccess();
    extensionImage: any;

    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPrestation> = new Subject();

    constructor(
        private sinisterService: SinisterService,
        private alertService: JhiAlertService,
        private http: HttpClient,
        private userExtraService: UserExtraService,
        public principal: Principal,
        private sinisterPecService: SinisterPecService,

        private route: ActivatedRoute
    ) {}

    ngOnInit() {
       this.loadAll();
    }
    printOrdrePrestationVr(sinisterPrestation: ViewSinisterPrestation) {
        this.sinisterService.generateOrdrePrestationVr(sinisterPrestation.id).subscribe((res) => {
            window.open(res.headers.get('pdfname'), '_blank');
            sinisterPrestation.isGenerated = true;
            //this.rerender();
            //this.loadAll();
        });
    }

    getOrdrePrestationVr(entityName: string, sinisterPrestation: ViewSinisterPrestation, label: string) {
        this.sinisterPecService.getPieceBySinPecAndLabelIOOB(entityName, sinisterPrestation.id, label).subscribe((blob: Blob) => {
            const indexOfFirst = (blob.type).indexOf('/');
            this.extensionImage = ((blob.type).substring((indexOfFirst + 1), ((blob.type).length)));
            this.extensionImage = this.extensionImage.toLowerCase();
            let fileName = label + "." + this.extensionImage;
            console.log(fileName);
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, fileName);
                console.log("if--------------------");
            } else {
                console.log("else--------------------");
                var a = document.createElement('a');
                a.href = URL.createObjectURL(blob);
                a.download = fileName;
                a.target = '_blank';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
        },
            err => {
                alert("Error while downloading. File Not Found on the Server");
            });
    }

    loadAll() {
        this.route.params.subscribe((params) => {
            if (params["status"]) {
                this.status = 0;
                if (params["status"] == "in-progress") {
                    this.status = StatusSinister.INPROGRESS;
                } else if (params["status"] == "closed") {
                    this.status = StatusSinister.CLOSED;
                } else if (params["status"] == "refused") {
                    this.status = StatusSinister.REFUSED;
                } else if (params["status"] == "canceled") {
                    this.status = StatusSinister.CANCELED;
                }

                console.log(this.status);
                const that = this;
                that.dtOptions = {
                    pagingType: "full_numbers",
                    pageLength: 10,
                    serverSide: true,
                    processing: true,
                    ajax: (dataTablesParameters: any, callback) => {
                       
                        that.http
                            .post<DataTablesResponse>(
                                "api/view/view-prestation-vr/page/" +
                                that.status,
                                dataTablesParameters,
                                {}
                            )
                            .subscribe((resp) => {
                                that.sinisterPrestations = resp.data;
                                console.log(resp.data);
                                callback({
                                    recordsTotal: resp.recordsTotal,
                                    recordsFiltered: resp.recordsFiltered,
                                    data: [],
                                });
                            });
                    },
                    columns: [
                        { data: "reference" },
                        { data: "creationDate" },
                        { data: "registrationNumber" },
                        { data: "fullName" },
                        { data: "incidentGovernorateLabel" },
                        { data: "deliveryGovernorateLabel" },
                        { data: "days" },
                        { data: "priceTtc" },
                        { data: "loueurLabel" },
                        { data: "acquisitionDate" },
                        { data: "returnDate" },
                        { data: "cancelGroundsDescription" },
                        { data: "motifRefusLabel" },
                        { data: "charge" },
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
                                    .exportprestationVrToExcel(
                                        dt.search(),
                                        that.status
                                    )
                                    .subscribe(
                                        (blob: Blob) => {
                                            let fileName = "PrestationVr.xlsx";
                                            if (
                                                window.navigator &&
                                                window.navigator
                                                    .msSaveOrOpenBlob
                                            ) {
                                                window.navigator.msSaveOrOpenBlob(
                                                    blob,
                                                    fileName
                                                );
                                            } else {
                                                var a = document.createElement(
                                                    "a"
                                                );
                                                a.href = URL.createObjectURL(
                                                    blob
                                                );
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
                this.rerender();

                this.principal.identity().then((account) => {
                    this.currentAccount = account;
                    this.userExtraService
                        .findFunctionnalityEntityByUser(
                            34,
                            this.currentAccount.id
                        )
                        .subscribe((res) => {
                            this.permissionToAccess = res;
                        });
                });

                //this.registerChangeInContratAssurances();
            }
        });
    }

    ngAfterViewInit(): void {}

    rerender(): void {
        try {
            this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                dtInstance.ajax.reload();
            });
        } catch (err) {
            console.log(err);
        }
    }

    ngOnDestroy() {}
}
