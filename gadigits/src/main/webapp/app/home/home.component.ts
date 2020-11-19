import { GaDatatable, StatusSinister, GaDatatablePA } from './../constants/app.constants';
import { Component, OnInit, OnDestroy, AfterViewInit, QueryList, ViewChild } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService, JhiDateUtils } from 'ng-jhipster';

import { PaginationConfig } from '../blocks/config/uib-pagination.config';

import { SinisterService } from '../entities/sinister/sinister.service';
import { PriseEnCharges } from '../entities/sinister/priseencharge.model';
import { Assitances } from '../entities/sinister/assitances.model';
import { Dossiers } from '../entities/sinister/dossiers.model';
import { Subject } from 'rxjs/Rx';
import { ResponseWrapper, Principal } from '../shared';
import { Cells, Authorities } from "../constants/app.constants";
import { DataTableDirective } from 'angular-datatables';
import { Recherche } from "../entities/sinister/recherche.model";
import { NgbProgressbar } from '@ng-bootstrap/ng-bootstrap';
import { Http } from '@angular/http';
import { NgProgress } from 'ngx-progressbar';
import { SinisterPecService } from '../entities/sinister-pec';
import { RefStepPec } from '../entities/sinister-pec/refStepPec.model';
import { StatusPecService, StatusPec } from '../entities/status-pec';
import { Search } from '../entities/sinister';
import { StepService } from '../entities/step';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RefTypeServiceService, RefStatusSinister } from '../entities/ref-type-service';
import { UserExtraService, UserExtra } from '../entities/user-extra';
import { HttpClient } from '@angular/common/http';

class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})

export class HomeComponent implements OnInit, OnDestroy, AfterViewInit {


    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    @ViewChild(DataTableDirective)
    datatableElement2: DataTableDirective;
    @ViewChild(DataTableDirective)
    datatableElement3: DataTableDirective;

    assitances: Assitances[];
    assitancesOrg: Assitances[];

    dossiers: Dossiers[];
    dossiersOrg: Dossiers[];
    reparationCondition = false;
    assistanceCondition = false;
    priseencharges: PriseEnCharges[];
    priseenchargesOrg: PriseEnCharges[];
    reparateurConnecte: boolean = false;


    eventSubscriber: Subscription;

    dtTrigger2: Subject<Dossiers> = new Subject();
    dtTrigger3: Subject<PriseEnCharges> = new Subject();
    RefStep: RefStepPec[];
    userExtra: UserExtra = new UserExtra();
    typeService: any;


    dtOptions: DataTables.Settings[] = [];
    dtOptionsAssistance = {};
    error: any;
    isHiddenDossier: boolean = false;
    isHiddenAssistance: boolean = false;
    isHiddenPriseEnCharge: boolean = false;
    isHiddenAcueil: boolean = true;
    searc: Search = new Search();
    onInit = true;
    /* immatriculation: string;
     startDate: any;
     endDate: any;
     types: any;*/
    recherche = new Recherche();
    statusAss: RefStatusSinister[];
    currentAccount: any;
    selectedRow: Number;
    setClickedRow: Function;

    constructor(
        private userExtraService: UserExtraService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private sinisterService: SinisterService,
        public ngProgress: NgProgress,
        private sinisterPecService: SinisterPecService,
        private StatusPecService: StepService,
        private StatutsAssistance: RefTypeServiceService,
        private http: HttpClient,
        private dateUtils: JhiDateUtils
    ) {
        this.setClickedRow = function (index) {
            this.selectedRow = index;
        }
        this.searc.types = 1;

    }
    ngOnInit() {
        this.search();

        this.dtOptions[1] = GaDatatablePA.defaultDtOptions;
        this.dtOptions[2] = GaDatatablePA.defaultDtOptions;

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtra = usr;
                if (this.userExtra.profileId == 28 || this.userExtra.profileId == 27) {
                    this.reparateurConnecte = false;
                }
                else { this.reparateurConnecte = true; }

            });

        })



    }




    ngAfterViewInit(): void {
        //this.dtTrigger1.next();
        this.dtTrigger2.next();
        this.dtTrigger3.next();
    }

    reparationClick() {
        this.reparationCondition = true;
        this.assistanceCondition = false;
        this.sinisterPecService.findStepPecByNumber().subscribe(
            (res: ResponseWrapper) => {

                this.RefStep = res.json;
            });


    }
    dossierClick() {

        this.reparationCondition = false;
        this.assistanceCondition = false;

    }

    assistanceClick() {
        this.reparationCondition = false;
        this.assistanceCondition = true;


        this.StatutsAssistance.queryRefStatusSinister().subscribe((res: ResponseWrapper) => {

            this.statusAss = res.json;

        });

    }
    VRClick() {
        this.reparationCondition = false;
        this.assistanceCondition = false;


        this.StatutsAssistance.queryRefStatusSinister().subscribe((res: ResponseWrapper) => {

            this.statusAss = res.json;

        });

    }
    cancel(searc) {
        searc.reference = undefined;
        searc.immatriculation = undefined;
        searc.startDate = undefined;
        searc.endDate = undefined;
        searc.idEtapePrestation = undefined;
        searc.statusId = undefined;
        this.isHiddenDossier = false;
        this.isHiddenAssistance = false;
        this.isHiddenPriseEnCharge = false;
        this.isHiddenAcueil = true;

        //this.dtTrigger1 = new Subject();
        this.dtTrigger2 = new Subject();
        this.dtTrigger3 = new Subject();


    }

    search() {
        console.log(this.searc.types);
        if (this.searc.types == "2" || this.onInit == true) {
            console.log('______________1234');

            if (this.onInit == false) {
                this.isHiddenDossier = false;
                this.isHiddenAssistance = true;
                this.isHiddenPriseEnCharge = false;
                this.isHiddenAcueil = false;
                //this.ngProgress.start();
            }
            //this.onInit = false
            const that = this;
            that.searc.typeService = "notVr";
            that.searc.reference = that.searc.reference !== "" ? that.searc.reference : null;
            that.searc.immatriculation = that.searc.immatriculation !== "" ? that.searc.immatriculation : null;
            that.searc.endDateNC = that.searc.endDateNC !== "" ? that.searc.endDateNC : null;
            that.searc.startDateNC = that.searc.startDateNC !== "" ? that.searc.startDateNC : null;
            that.dtOptionsAssistance = {
                pagingType: 'full_numbers',
                pageLength: 10,
                serverSide: true,
                processing: true,
                ajax: (dataTablesParameters: any, callback) => {
                    that.searc.startDate = this.dateUtils.convertLocalDateToServer(that.searc.startDateNC);
                    that.searc.endDate = this.dateUtils.convertLocalDateToServer(that.searc.endDateNC);
                    that.searc.dataTablesParameters = dataTablesParameters;
                    if (that.onInit == false) {
                        that.http
                            .post<DataTablesResponse>(
                                "api/view-assitances/page",
                                that.searc,
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
                    }
                    that.onInit = false
                },
                columns: [
                    // { data: "id" },
                    { data: "reference" },
                    { data: "creationDate" },
                    { data: "incidentDate" },

                    { data: "incidentNature" },
                    { data: "companyName" },
                    { data: "nomAgentAssurance" },
                    { data: "numeroContrat" },
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
                    { data: "motif" }
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
                                .exportAssitancesToExcel(that.searc)
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
            this.rerender1();







            /*this.sinisterService.queryAssistanceSearch(searc).subscribe(
                (res: Assitances[]) => {
                    this.ngProgress.done();
                    this.assitances = null;
                    this.assitances = res;
                    this.assitancesOrg = res;
                    this.assitances = this.assitancesOrg.filter(this.filterArray.bind(this));


                    this.assitances.forEach(element => {
                        element.nomPrenomRaison = element.isCompany ? element.raisonSociale : (element.prenom + ' ' + element.nom);
                    });

                    //this.dtTrigger1.next();
                    //this.rerender1();

                },
                (res: ResponseWrapper) => this.onError(res.json)
            );*/

        }
        else if (this.searc.types == "3") {

            this.isHiddenDossier = false;
            this.isHiddenAssistance = false;
            this.isHiddenPriseEnCharge = true;
            this.isHiddenAcueil = false;
            this.ngProgress.start();
            this.searc.startDate = this.dateUtils.convertLocalDateToServer(this.searc.startDateNC);
            this.searc.endDate = this.dateUtils.convertLocalDateToServer(this.searc.endDateNC);
            this.sinisterService.queryPriseEnChargeSearch(this.searc).subscribe(
                (res: PriseEnCharges[]) => {
                    this.ngProgress.done();
                    this.priseencharges = null;
                    this.priseencharges = res;
                    this.priseenchargesOrg = res;
                    this.priseencharges = this.priseencharges.filter(this.filterArray.bind(this));
                    var cache = {};
                    this.priseencharges = this.priseencharges.filter(function (elem) {
                        return cache[elem.id] ? 0 : cache[elem.id] = 1;
                    });
                    this.dtTrigger3.next();
                    this.rerender3();
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );



        }
        else
        //if (recherches.types == "3")
        {
            this.isHiddenDossier = true;
            this.isHiddenAssistance = false;
            this.isHiddenPriseEnCharge = false;
            this.isHiddenAcueil = false;
            this.ngProgress.start();
            this.searc.startDate = this.dateUtils.convertLocalDateToServer(this.searc.startDateNC);
            this.searc.endDate = this.dateUtils.convertLocalDateToServer(this.searc.endDateNC);
            this.sinisterService.queryDossiersSearch(this.searc).subscribe(
                (res: Dossiers[]) => {
                    this.ngProgress.done();
                    this.dossiers = res;
                    this.dossiersOrg = res;
                    this.dossiers = this.dossiersOrg.filter(this.filterArray.bind(this));

                    this.dossiers.forEach(element => {
                        const opdate = new Date(element.incidentDate);
                        var month = new Array();
                        var mois = (opdate.getMonth());
                        month[0] = "Janvier";
                        month[1] = "Février";
                        month[2] = "Mars";
                        month[3] = "Avril";
                        month[4] = "Mai";
                        month[5] = "Juin";
                        month[6] = "Juillet";
                        month[7] = "août";
                        month[8] = "septembre";
                        month[9] = "Octobre";
                        month[10] = "Novembre";
                        month[11] = "Décembre";
                        element.incidentMois = month[mois];
                        element.nomPrenomRaison = element.isCompany ? element.raisonSociale : (element.prenom + ' ' + element.nom);
                    });
                    this.dtTrigger2.next();
                    this.rerender2();

                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
    }

    acueil() {
        this.isHiddenDossier = false;
        this.isHiddenAssistance = false;
        this.isHiddenPriseEnCharge = false;
        this.isHiddenAcueil = true;
    }



    /**
     * Load pecs list and services list
     */
    /*loadAll() {
        // calculate boolean
        if (this.principal.isAuthenticated()) {

        }

    }*/

    rerender1(): void {
        try {
            this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                dtInstance.ajax.reload();
            });
        } catch (err) {
            console.log(err);
        }
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
        this.alertService.error(this.error.message, null, null);
    }


    filterArray(element, index, array) {
        console.log('___________________________________________________________');

        const immatriculationFlt = this.searc.immatriculation != undefined ? this.searc.immatriculation == element.immatriculationVehicule : true;
        const referenceFlt = this.searc.reference != undefined ? this.searc.reference == element.reference : true;
        const statusIdFlt = this.searc.statusId != undefined ? this.searc.statusId == element.statusId : true;

        const idEtapePrestationFlt = this.searc.idEtapePrestation != undefined ? this.searc.idEtapePrestation == element.idEtapePrestation : true;

        return immatriculationFlt && referenceFlt && idEtapePrestationFlt && statusIdFlt;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
    ngOnDestroy(): void {
        /* this.dtTrigger1.unsubscribe();
         this.dtTrigger2.unsubscribe();
         this.dtTrigger3.unsubscribe();*/
    }
}

