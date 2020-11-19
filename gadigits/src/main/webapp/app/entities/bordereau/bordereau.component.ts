import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks,  JhiAlertService } from 'ng-jhipster';
import { RefRemorqueur } from '../ref-remorqueur/ref-remorqueur.model';
import { RefRemorqueurService } from '../ref-remorqueur/ref-remorqueur.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
//import { PdfViewerModule } from 'ng2-pdf-viewer';
@Component({
    selector: 'jhi-bordereau',
    templateUrl: './bordereau.component.html'
})
export class BordereauComponent implements OnInit, OnDestroy {
currentAccount: any;
    refRemorqueurs: RefRemorqueur[];
    refRemorqueur: RefRemorqueur;
    error: any;
    pdfSrc: string = 'Bureau/pdf-test.pdf';
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
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
    dtTrigger: Subject<RefRemorqueur> = new Subject();
    constructor(
        private refRemorqueurService: RefRemorqueurService,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        public  principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        this.refRemorqueurService.queryCloture().subscribe(
            (res: ResponseWrapper) => {
                this.refRemorqueurs = res.json;
                this.dtTrigger.next(); // Actualize datatables
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions; 
        this.loadAll();
        
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        
        this.registerChangeInRefRemorqueurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    bordereau(refRemorqueur){
        this.refRemorqueurService.generateRemorqueurBordereau(refRemorqueur).subscribe((res) => {
            window.open(res.headers.get('pdfname'), '_blank');
          
        }) 
      
    }

    trackId(index: number, item: RefRemorqueur) {
        return item.id;
    }
    registerChangeInRefRemorqueurs() {
        this.eventSubscriber = this.eventManager.subscribe('refRemorqueurListModification', (response) => this.loadAll());
    }

    
    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.refRemorqueurs = data;  
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
