import { GaDatatable } from './../../constants/app.constants';
import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { JhiAlertService } from 'ng-jhipster';
import { RefTypeService } from './ref-type-service.model';
import { RefTypeServiceService } from './ref-type-service.service';
import { ConfirmationDialogService, Principal, ResponseWrapper } from '../../shared';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { PermissionAccess, UserExtraService } from '../user-extra';

@Component({
    selector: "jhi-ref-type-service",
    templateUrl: "./ref-type-service.component.html"
})
export class RefTypeServiceComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    refTypeServices: RefTypeService[];
    refTypeService: RefTypeService = new RefTypeService();
    error: any;
    success: any;
    dtOptions: any = {};
    dtTrigger: Subject<RefTypeService> = new Subject();
    isCreateMode = true;
    isSaving: boolean;
    editt = false;
    validate = false;
    permissionToAccess: PermissionAccess = new PermissionAccess();


    constructor(
        private refTypeServiceService: RefTypeServiceService,
        private alertService: JhiAlertService,
        public principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService
    ) {}
    
    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    loadAll() {
        this.refTypeServiceService
            .query()
            .subscribe(
                (res: ResponseWrapper) => this.onSuccess(res.json),
                (res: ResponseWrapper) => this.onError(res.json)
            );
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
            this.userExtraService.findFunctionnalityEntityByUser(5, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }

    trimServiceTypeNumber() {
        this.refTypeService.nom = this.refTypeService.nom.trim();
    }

    save() {
        this.trimServiceTypeNumber();
        if(this.refTypeService.nom !== null && this.refTypeService.nom !== undefined) {
            let serviceTypeTmp = this.refTypeServices.find(x => x.nom === this.refTypeService.nom);
            if (serviceTypeTmp != undefined && serviceTypeTmp.id !== this.refTypeService.id) {
                this.alertService.error('auxiliumApp.refTypeService.unique', null, null);
            } else {
                this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
                .then(confirmed => {
                    if (confirmed) {
                        if (this.refTypeService.id !== undefined) {
                            this.subscribeToSaveResponse(this.refTypeServiceService.update(this.refTypeService));
                        } else {
                            this.subscribeToSaveResponse(this.refTypeServiceService.create(this.refTypeService));
                        }
                    }
                })
            }
        }
    }

    edit(id: number) {
        this.editt = true;
        this.refTypeServiceService.find(id).subscribe(refTypeService => {
            this.refTypeService = refTypeService;
        });
    }

    cancel() {
        this.refTypeService = new RefTypeService();
    }

    ngOnDestroy() {
    }

    private subscribeToSaveResponse(result: Observable<RefTypeService>) {
        result.subscribe(
            (res: RefTypeService) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: RefTypeService) {
        this.isSaving = false;
        this.cancel();
        this.loadAll();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onSuccess(data) {
        this.refTypeServices = data;
        this.rerender();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
