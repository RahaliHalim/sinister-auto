import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { Subject } from "rxjs";
import { JhiEventManager, JhiParseLinks, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { RefPricing } from './ref-pricing.model';
import { RefPricingService } from './ref-pricing.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';

@Component({
    selector: 'jhi-ref-pricing',
    templateUrl: './ref-pricing.component.html'
})
export class RefPricingComponent implements OnInit, OnDestroy {

currentAccount: any;
    refPricings: RefPricing[];
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
    dtOptions: any = {};
    dtTrigger: Subject<RefPricing> = new Subject();

    constructor(
        private refPricingService: RefPricingService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        public  principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private confirmationDialogService: ConfirmationDialogService
    ) {
    }

    loadAll() {
        this.refPricingService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.dtOptions = {
                pagingType: 'full_numbers',
                pageLength: 10,
                retrieve: true,
                // Declare the use of the extension in the dom parameter
                dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6 text-align: center;"p>>',

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
                        first: '<button type="button" class="btn btn-sm btn-default btn-rounded"><i class="fa fa-angle-double-left" style="font-size:16px"></i></button>&nbsp;',
                        previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>&nbsp;',
                        next: '&nbsp;<i class="fa fa-angle-right" style="font-size:16px"></i>',
                        last: '&nbsp;<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
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

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRefPricings();
    }


    public deleteRefPricing(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cette ligne ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                console.log( 'User confirmed:', confirmed );
                if(confirmed) {
                    this.refPricingService.delete(id).subscribe((response) => {

                        console.log( 'User confirmed delete:', id );

                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
            .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
    }

    
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RefPricing) {
        return item.id;
    }
    registerChangeInRefPricings() {
        this.eventSubscriber = this.eventManager.subscribe('tarifListModification', (response) => this.loadAll());
    }

    private onSuccess(data, headers) {
        this.refPricings = data;
        this.dtTrigger.next();
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
