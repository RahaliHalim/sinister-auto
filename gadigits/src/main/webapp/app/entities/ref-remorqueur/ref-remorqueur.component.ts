import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks,  JhiAlertService } from 'ng-jhipster';
import { RefRemorqueur } from './ref-remorqueur.model';
import { RefRemorqueurService } from './ref-remorqueur.service';
import {  Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { UserExtraService, PermissionAccess} from '../user-extra';

@Component({
    selector: 'jhi-ref-remorqueur',
    templateUrl: './ref-remorqueur.component.html'
})
export class RefRemorqueurComponent implements OnInit, OnDestroy {
currentAccount: any;
    refRemorqueurs: RefRemorqueur[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    permissionToAccess : PermissionAccess = new PermissionAccess();
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
        private userExtraService : UserExtraService
    ) {
      
    }

    loadAll() {
        this.refRemorqueurService.query().subscribe(
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
            this.userExtraService.findFunctionnalityEntityByUser(6,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });

        this.registerChangeInRefRemorqueurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RefRemorqueur) {
        return item.id;
    }
    registerChangeInRefRemorqueurs() {
        this.eventSubscriber = this.eventManager.subscribe('refRemorqueurListModification', (response) => this.loadAll());
    }

   
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
