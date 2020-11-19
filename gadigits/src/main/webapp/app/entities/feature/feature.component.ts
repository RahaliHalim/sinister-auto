import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ResponseWrapper } from '../../shared';
import { FeatureService } from './feature.service';
import { Features } from './feature.model';


@Component({
    selector: 'jhi-feature',
    templateUrl: './feature.component.html'
   
})
export class FeatureComponent {


    feature: Features;
    features: Features[];
    parentFeatures: Features[];
    itemsPerPage: any;
    routeData: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    currentSearch: string;
    links: any;
    totalItems: any;
    queryCount: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<Features> = new Subject();
    entities: any[];

    constructor(
        private featureService: FeatureService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
       
    ) {
        
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




        this.feature = new Features();
        this.loadAll();
        this.registerChangeInFeatures();
    }
    saveFeature(){
            this.featureService.create(this.feature).subscribe((res) => {
            this.feature = res;
            this.featureService.query().subscribe((res: ResponseWrapper) => {
            this.features = res.json;
            });
            this.featureService.findFeatureWithoutParent().subscribe((res: ResponseWrapper) => {
                this.parentFeatures = res.json;
            });
        })
                      
    }
    /**
     * Get feature to edit
     */
    getIdFeature(id:number){
        this.featureService.find(id).subscribe((subRes) => {
           this.feature = subRes;
     
        });
    }
    clear(){
        this.feature = new Features();
    }

    /**
     * Get all Profils
     */
    loadAll() {
        this.featureService.query().subscribe(
            (res: ResponseWrapper) => {
                this.features = res.json;
                this.dtTrigger.next(); // Actualize datatables
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    registerChangeInFeatures() {
        this.eventManager.subscribe('featuresListModification', (response) => {this.loadAll()});
    }
    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        //this.routeData.unsubscribe();
        if(this.eventSubscriber !== null && this.eventSubscriber !== undefined)
            this.eventManager.destroy(this.eventSubscriber);
    }
    trackIdentity(index, item: Features) {
        return item.id;
    }
   
}