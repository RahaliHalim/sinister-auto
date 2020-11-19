import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';
import { GaDatatable } from '../../constants/app.constants';
import { Reparateur } from './reparateur.model';
import { ViewReparator } from './view-reparator.model';
import { ReparateurService } from './reparateur.service';
import { Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { ConfirmationDialogService } from '../../shared';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { DataTableDirective } from 'angular-datatables';
@Component({
    selector: 'jhi-reparateur',
    templateUrl: './reparateur.component.html'
})
export class ReparateurComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    currentAccount: any;
    reparateurs: ViewReparator[];
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
    reverse: any;
    dtOptions: any = {};
    dtTrigger: Subject<ViewReparator> = new Subject();
    reparateur: Reparateur = new Reparateur();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private http: Http, private dateUtils: JhiDateUtils,
        private reparateurService: ReparateurService,
        private paginationUtil: JhiPaginationUtil,
        private paginationConfig: PaginationConfig,
        private parseLinks: JhiParseLinks,
        private alertService: JhiAlertService,
        public principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService
    ) {

    }

    loadAll() {
        this.reparateurService.findAllReparators().subscribe(
            (res: ResponseWrapper) => {
                this.reparateurs = res.json;
                this.dtTrigger.next();
                //this.rerender();
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
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(7, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInReparateurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Reparateur) {
        return item.id;
    }
    registerChangeInReparateurs() {
        this.eventSubscriber = this.eventManager.subscribe('reparateurListModification', (response) => this.loadAll());
        this.eventManager.broadcast({ name: 'reparateurListModification', content: 'OK' });
        this.router.navigate(['../../reparateur'])
    }
    desactiverReparateur(reparateur) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir activer ce réparateur ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.reparateurService.find(reparateur.id).subscribe((res: Reparateur) => {
                        this.reparateur = res;
                        this.reparateur.isActive = true;
                        this.subscribeperToSaveResponse(
                            this.reparateurService.updateReparateur(this.reparateur));
                    });

                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }
    activerReparateur(reparateur) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir désactiver ce réparateur ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.reparateurService.find(reparateur.id).subscribe((res: Reparateur) => {
                        this.reparateur = res;
                        this.reparateur.isActive = false;
                        this.subscribeperToSaveResponse(
                            this.reparateurService.updateReparateur(this.reparateur));
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }
    private subscribeperToSaveResponse(result: Observable<Reparateur>) {
        result.subscribe((res: Reparateur) =>
            this.onRepSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onRepSaveSuccess(result: Reparateur) {
        //this.eventManager.broadcast({ name: 'reparateurListModification', content: 'OK' });
        // this.router.navigate(['../../reparateur'])
        this.rerender();
        this.loadAll();
    }
    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();

            this.onError(error);
        }
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
