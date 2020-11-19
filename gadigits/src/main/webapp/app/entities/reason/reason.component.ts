import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { Reason } from './reason.model';
import { ReasonService } from './reason.service';
import { Step, StepService } from '../step';
import { GaDatatable, Global } from './../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
import { ConfirmationDialogService, Principal, ResponseWrapper } from '../../shared';
import { UserExtraService, PermissionAccess} from '../user-extra';

@Component({
    selector: 'jhi-reason',
    templateUrl: './reason.component.html'
})
export class ReasonComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    reasons: Reason[];
    currentAccount: any;
    currentSearch: string;
    reason: Reason = new Reason();
    isSaving: boolean;
    textPattern = Global.textPattern;
    steps: Step[];
    permissionToAccess : PermissionAccess = new PermissionAccess();
    dtOptions: any = {};
    dtTrigger: Subject<Reason> = new Subject();

    constructor(
        private reasonService: ReasonService,
        private stepService: StepService,
        private alertService: JhiAlertService,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private userExtraService : UserExtraService
    ) {
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


    loadAll() {
        this.reasonService
            .query()
            .subscribe(
                (res: ResponseWrapper) => this.onSuccess(res.json),
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    clear() {
        this.loadAll();
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(89,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.stepService.query().subscribe((res: ResponseWrapper) => { this.steps = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    edit(id: number) {
        this.reasonService.find(id).subscribe(reason => {
            this.reason = reason;
        });
    }

    cancel() {
        this.reason = new Reason();
    }

    enable(id) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(( confirmed ) => {
                if(confirmed) {
                    this.reasonService.find(id).subscribe(reason => {
                        reason.active = true;
                        this.subscribeToSaveResponse(
                            this.reasonService.update(reason)
                        );

                    });
                }
            });
    }

    disable(id) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(( confirmed ) => {
                if(confirmed) {
                    this.reasonService.find(id).subscribe(reason => {
                        reason.active = false;
                        this.subscribeToSaveResponse(
                            this.reasonService.update(reason)
                        );

                    });
                }
            });
    }

    save() {
        this.confirmationDialogService
            .confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.reason.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.reasonService.update(this.reason)
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.reasonService.create(this.reason)
                        );
                    }
                }
            });
    }

    private subscribeToSaveResponse(result: Observable<Reason>) {
        result.subscribe(
            (res: Reason) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: Reason) {
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

    ngOnDestroy() {
    }

    trackId(index: number, item: Reason) {
        return item.id;
    }

    trackStepById(index: number, item: Step) {
        return item.id;
    }

    private onSuccess(data) {
        this.reasons = data;
        this.rerender();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
