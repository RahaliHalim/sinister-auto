import { Component, OnInit, OnDestroy,ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';
import { GaDatatable } from '../../constants/app.constants';
import { Expert } from './expert.model';
import { ViewExpert } from './view-expert.model';
import { ExpertService } from './expert.service';
import { Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { ConfirmationDialogService } from '../../shared';
import { Observable } from 'rxjs/Rx';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { DataTableDirective } from 'angular-datatables';

@Component({
    selector: 'jhi-expert',
    templateUrl: './expert.component.html'
})
export class ExpertComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    currentAccount: any;
    experts: ViewExpert[];
    error: any;
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
    expert: Expert = new Expert();
    reverse: any;
    dtOptions: any = {};
    dtTrigger: Subject<ViewExpert> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private expertService: ExpertService,

        private alertService: JhiAlertService,
        public principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService
    ) {

    }
    ngOnInit() {
        this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        this.dtOptions.scrollX = true;

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(16, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInExperts();
    }
    loadAll() {
        this.expertService.findAllExperts().subscribe(
            (res: ResponseWrapper) => {
                this.experts = res.json;
                this.rerender();

            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }
    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }
 

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Expert) {
        return item.id;
    }

    registerChangeInExperts() {
        this.eventSubscriber = this.eventManager.subscribe('expertListModification', (response) => this.loadAll());
    }
    activeExpert(expert) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir desactiver cet expert ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    console.log("iciiiiii id expert activé" + expert.id);
                    this.expertService.find(expert.id).subscribe((res: Expert) => {
                        this.expert = res;
                        this.expert.isActive = true;
                        this.subscribeperToSaveResponse(
                            this.expertService.updateForActive(this.expert));
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }
    desactiveExpert(expert) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir activer cet expert ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    console.log("iciiiiii id expert desactivé" + expert.id);
                    this.expertService.find(expert.id).subscribe((res: Expert) => {
                        this.expert = res;
                        this.expert.isActive = false;
                        this.subscribeperToSaveResponse(
                            this.expertService.updateForActive(this.expert));
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }



    private subscribeperToSaveResponse(result: Observable<Expert>) {
        result.subscribe((res: Expert) =>
            this.onRepSaveSuccess(res), (res: Response) => this.onError(res));
    }
    private onRepSaveSuccess(result: Expert) {
       // this.eventManager.broadcast({ name: 'expertListModification', content: 'OK' });
       // this.router.navigate(['../../expert'])
       this.rerender();
        this.loadAll();

    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
