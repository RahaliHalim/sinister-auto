import {
    Component,
    OnInit,
    OnDestroy,
    AfterViewInit,
    ViewChild,
} from "@angular/core";
import { SinisterService } from "../../sinister/sinister.service";

import { PriseEnCharges } from "../../sinister/priseencharge.model";

import { Subscription, Subject } from "rxjs/Rx";
import { JhiAlertService } from "ng-jhipster";
import { Principal, ResponseWrapper } from "../../../shared";
import { NgProgress } from "ngx-progressbar";
import { GaDatatable } from "../../../constants/app.constants";
import { HistoryReparationComponent } from "../history-reparation/history-reparation.component";
import { NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { SinisterPecPopupService } from "../../sinister-pec";
import { HttpClient } from "@angular/common/http";
import { Search } from "../../sinister";
class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}
@Component({
    selector: "jhi-reparation",
    templateUrl: "./reparation.component.html",
    styles: [],
})
export class ReparationComponent implements OnInit, OnDestroy {
    priseencharges: PriseEnCharges[];
    eventSubscriber: Subscription;
    selectedRow: Number;
    error: any;
    isHiddenDossier: boolean = false;
    isHiddenAssistance: boolean = false;
    isHiddenPriseEnCharge: boolean = false;
    isHiddenAcueil: boolean = true;
    private ngbModalRef: NgbModalRef;
    dtOptions: any = {};
    dtTrigger: Subject<PriseEnCharges> = new Subject();
    searchParams: Search = new Search();

    constructor(
        private alertService: JhiAlertService,
        private principal: Principal,
        private sinisterService: SinisterService,
        public ngProgress: NgProgress,
        private siniterPecPopupService: SinisterPecPopupService,
        private http: HttpClient
    ) {}

    ngOnInit() {
       // this.dtOptions = GaDatatable.defaultDtOptions;
        // this.loadAll();

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
                        "api/view-prise-en-charge/page",
                        that.searchParams,
                        {}
                    )
                    .subscribe((resp) => {
                        that.priseencharges = resp.data;
                        callback({
                            recordsTotal: resp.recordsTotal,
                            recordsFiltered: resp.recordsFiltered,
                            data: [],
                        });
                    });
            },
            columns: [
                { data: "reference" },
                { data: "refSinister" },
                { data: "incidentDate" },
                { data: "declarationDate" },

                { data: "companyName" },
                { data: "nomAgentAssurance" },
                { data: "codeAgentAssurance" },
                { data: "numeroContrat" },

                { data: "immatriculationVehicule" },
                { data: "raisonSocialeAssure" },
                { data: "numberTeleInsured" },
                { data: "marque" },

                { data: "compagnieAdverse" },
                { data: "immatriculationTiers" },
                { data: "raisonSocialTiers" },
                { data: "creationDate" },

                { data: "modeGestion" },
                { data: "casbareme" },
                { data: "positionGa" },
                { data: "pointChock" },

                { data: "dateRDVReparation" },
                { data: "lightShock" },
                { data: "vehicleReceiptDate" },
                { data: "montantTotalDevis" },

                { data: "imprimDate" },
                { data: "bonDeSortie" },
                { data: "chargeNom" },

                { data: "reparateur" },
                { data: "expert" },
                { data: "bossNom" },

                { data: "responsableNom" },
                { data: "etatPrestation" },
                { data: "motifGeneral" },

                { data: "approvPec" },
                { data: "etapePrestation" },
                { data: "dateReprise" },
                { data: "signatureDate" },
                { data: "dateEnvoiDemandePec" }
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
                            .exportPriseEnChargesToExcel(that.searchParams)
                            .subscribe(
                                (blob: Blob) => {
                                    let fileName = "PriseEnCharges.xlsx";
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
    }

    // loadAll() {
    //     this.ngProgress.start();
    //     this.sinisterService.queryPriseEncharge().subscribe(
    //         (res: ResponseWrapper) => {
    //             this.ngProgress.done();
    //             this.priseencharges = res.json;
    //             var cache = {};
    //             this.priseencharges = this.priseencharges.filter(function (elem) {
    //                 return cache[elem.id] ? 0 : cache[elem.id] = 1;
    //             });
    //             this.priseencharges.forEach(element => {
    //                 if (element.signatureDate !== null && element.signatureDate !== undefined) {
    //                     let dateDelai = new Date(element.signatureDate);
    //                     element.signatureDate = dateDelai.setDate(dateDelai.getDate() + element.totalMo);
    //                 }
    //             });
    //             this.dtTrigger.next();

    //         },
    //         (res: ResponseWrapper) => this.onError(res.json)
    //     );

    // }

    private onError(error) {
        this.alertService.error(this.error.message, null, null);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
    ngOnDestroy(): void {}

    decider() {
        this.ngbModalRef = this.siniterPecPopupService.openHistoryModal(
            HistoryReparationComponent as Component
        );
    }
}
