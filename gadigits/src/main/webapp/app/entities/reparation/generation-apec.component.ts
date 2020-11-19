import { Component, OnInit, OnDestroy } from '@angular/core';
import {Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { PrestationPEC } from './reparation.model';
import { PrestationPECService } from './reparation.service';
import { Principal, ResponseWrapper } from '../../shared';
import { AccordPriseCharge } from '../devis/accord-prise-charge.model';
import { QuoteStatus } from '../../constants/app.constants';
import { QuotationService } from '../quotation/quotation.service';
@Component( {
    selector: 'jhi-reparation-ajout-saisie-devis',
    templateUrl: './generation-apec.component.html'
} )
export class GenerationAPECComponent implements OnInit, OnDestroy {
    currentAccount: any;
    prestationPECS: PrestationPEC[];
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
    currentSearch: string;
    authorities: any;
    userCellule: any;
    isCcelluleAssistance: any;
    isGestionnaireAcceptation: any;
    reparateur: any;
    
    toDoListPrestation: any;
    devisAccepte: any;
    etat: any;
    listePrestation: any[] = [];
    devisFacture: any;
    devisSoumis: any;
    devisBonSortie: any;
    devisRefuseAfterFacture: any;
    devisValide: any;
    devisDecider: any;

    isCcelluleOrGestionnaireAssistance: any;
    isCcelluleOrGestionnaireAcceptation: any;
    isResponsable: any;
    isGestionnaire: any;
    isChefCellule: any;
    expert: any;
    admin: any;
    usersCellule: any;
    isGestionnaireTechnique: any;
    isAgentGeneral: any;
    account: Account;
    prestation: any;
    soumis: any;
    devisForExpert: any;
    devisSauvegarde: any;
    devisValid: any;
    accordPriseCharge: AccordPriseCharge = new AccordPriseCharge();
    prestationpec: any;
    dossier: any;
    contratAssurance: any;
    vehicule: any;
    assure: any;
    cpl : any;
    dtOptions: any = {};
    dtTrigger: Subject<PrestationPEC> = new Subject();

     constructor(
        
        private prestationPECService: PrestationPECService,
        private quotationService: QuotationService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,     

    ) {
       
    }
    loadAll() {
        
        this.prestationPECService.queryReparateur(QuoteStatus.QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA,1).subscribe(
            ( res: ResponseWrapper ) => {
                this.listePrestation = res.json;
                this.dtTrigger.next(); // Actualize datatables
                },
                ( res: ResponseWrapper ) => this.onError( res.json )
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

                // calculate boolean
        this.reparateur = this.principal.hasAnyAuthorityDirect(['ROLE_REPARATEUR']);
        this.expert = this.principal.hasAnyAuthorityDirect(['ROLE_EXPERT']);
        this.admin = this.principal.hasAnyAuthorityDirect(['ROLE_ADMIN']);
        this.isResponsable = this.principal.hasAnyAuthorityDirect(['ROLE_RESPONSABLE']);
        this.isGestionnaire = this.principal.hasAnyAuthorityDirect(['ROLE_GESTIONNAIRE']);
       

        this.loadAll();

        this.principal.identity().then(( account ) => {
            this.currentAccount = account;
        } );
        this.registerChangeInPrestationPECS();
    }
    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }
    trackId( index: number, item: PrestationPEC ) {
        return item.id;
    }
    registerChangeInPrestationPECS() {
        this.eventSubscriber = this.eventManager.subscribe( 'prestationPECListModification', ( response ) => this.loadAll() );
    }
   
    private onError( error ) {
        this.alertService.error( error.message, null, null );
    }

    actionReparateur(prestationPEC: PrestationPEC) {
        if(prestationPEC.activeComplementaryId != null && prestationPEC.activeComplementaryId != undefined){

            this.quotationService.find(prestationPEC.activeComplementaryId).subscribe((resDevis) => {
                this.devisSauvegarde = resDevis;
                this.router.navigate(['devis/' + prestationPEC.id + '/edit/' + prestationPEC.activeComplementaryId]);
            });
        }
        if(prestationPEC.activeComplementaryId == null || prestationPEC.activeComplementaryId == undefined){
           
            this.quotationService.find(prestationPEC.primaryQuotationId).subscribe((resDevis) => {
                this.devisSauvegarde = resDevis;
                this.router.navigate(['devis/' + prestationPEC.id + '/edit/' + prestationPEC.primaryQuotationId]);
            });
        }
             
     }


}