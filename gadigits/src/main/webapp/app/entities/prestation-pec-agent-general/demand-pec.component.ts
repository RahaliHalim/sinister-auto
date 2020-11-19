import { DemandPecService } from './demand-pec.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PrestationAgentService } from './prestation-agent.service';
import { Principal, ResponseWrapper } from '../../shared';
import { UserCelluleService } from '../user-cellule/user-cellule.service';
import { DemandPec } from './demand-pec.model';

@Component( {
    selector: 'jhi-demand-pec',
    templateUrl: './demand-pec.component.html'
} )
export class DemandPecComponent implements OnInit, OnDestroy {
    currentAccount: any;
    demands: DemandPec[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    currentSearchDossier: string;
    authorities: any;
    userCellule: any;
    isGestionnaireReparation: any;
    dtOptions: any = {};
    dtTrigger: Subject<DemandPec> = new Subject();

    constructor(
        private userCelluleService: UserCelluleService,
        private demandPecService: DemandPecService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {}

    someClickHandler(info: any): void {
        console.log(info);
        console.log(info.id + ' - ' + info.incidentNature);
    }

    /**
     * Init outstanding demand list
     */
    ngOnInit() {
        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            retrieve: true,
            // Declare the use of the extension in the dom parameter
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
                zeroRecords: 'Aucun &eacute;l&eacute;ment &agrave; afficher',
                emptyTable: 'Aucune donnée disponible dans le tableau',
                paginate: {
                    first: '<button type="button" class="btn btn-sm"><i class="fa fa-angle-double-left" style="font-size:16px"></i></button> ',
                    previous: '<button type="button" class="btn btn-sm"><i class="fa fa-angle-left" style="font-size:16px"></i></button> ',
                    next: '<button type="button" class="btn btn-sm"><i class="fa fa-angle-right" style="font-size:16px"></i></button> ',
                    last: '<button type="button" class="btn btn-sm"><i class="fa fa-angle-double-right" style="font-size:16px"></i></button> '
                },
                aria: {
                    sortAscending: ': activer pour trier la colonne par ordre croissant',
                    sortDescending: ': activer pour trier la colonne par ordre décroissant'
                }
            },
            rowCallback: (row: Node, data: any[] | Object, index: number) => {
                const self = this;
                // Unbind first in order to avoid any duplicate handler
                // (see https://github.com/l-lin/angular-datatables/issues/87)
                $('td', row).unbind('click');
                $('td', row).bind('click', () => {
                    self.someClickHandler(data);
                });

                return row;
            },
            buttons: [
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
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
                },
                {
                    extend: 'pdfHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                    orientation: 'landscape'
                }
            ]
        };

        // Load list of outstanding demands
        this.loadAll();
    }

    loadAll() {
        this.demandPecService.findAllNewExternanlDemands().subscribe(
            (res: ResponseWrapper) => {
                console.log('___________________________________________________1');
                this.demands = res.json;
                this.dtTrigger.next(); // Actualize datatables
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnDestroy() {
        if(this.eventSubscriber !== null && this.eventSubscriber !== undefined) {
            this.eventManager.destroy(this.eventSubscriber);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}