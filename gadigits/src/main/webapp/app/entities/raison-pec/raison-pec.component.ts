import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { GaDatatable, Global } from './../../constants/app.constants';
import { RaisonPec } from './raison-pec.model';
import { RaisonPecService } from './raison-pec.service';
import { ConfirmationDialogService, Principal, ResponseWrapper } from '../../shared';
import { Operation } from '../operation/operation.model';
import { OperationService } from '../operation/operation.service';
import { PecStatusChangeMatrix } from '../pec-status-change-matrix/pec-status-change-matrix.model';
import { PecStatusChangeMatrixService } from '../pec-status-change-matrix/pec-status-change-matrix.service';
import { StatusPec, StatusPecService } from '../status-pec';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupDetail } from '../history/history-popup-detail';
import { HistoryPopupService } from '../history';
import { PermissionAccess, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-raison-pec',
    templateUrl: './raison-pec.component.html'
})
export class RaisonPecComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    raisonPecs: RaisonPec[];
    raisonPec: RaisonPec = new RaisonPec();
    operations: Operation[];
    pecStatusChangeMatrixs: PecStatusChangeMatrix[];
    statusPecs: StatusPec[];
    isSaving: boolean;
    currentAccount: any;
    textPattern = Global.textPattern;
    private ngbModalRef: NgbModalRef;
    
    dtOptions: any = {};
    dtTrigger: Subject<RaisonPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private raisonPecService: RaisonPecService,
        private alertService: JhiAlertService,
        private statusPecService: StatusPecService,
        private operationService: OperationService,
        private pecStatusChangeMatrixService: PecStatusChangeMatrixService,
        private confirmationDialogService: ConfirmationDialogService,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private historyPopupService: HistoryPopupService,
        private userExtraService: UserExtraService
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
        this.raisonPecService.query().subscribe(
            (res: ResponseWrapper) => {
                this.raisonPecs = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.raisonPec.label = this.raisonPec.label.trim();
    }
    
    onOperationChange(){
        this.raisonPec.responsible = undefined;
    } 

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.raisonPec.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.raisonPecService.update(
                                this.raisonPec
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.raisonPecService.create(
                                this.raisonPec
                            )
                        );
                    }
                }
            })
    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"RaisonPec");
         this.ngbModalRef.result.then((result: any) => {
             if (result !== null && result !== undefined) {
             }
             this.ngbModalRef = null;
         }, (reason) => {
             // TODO: print error message
             console.log('______________________________________________________2');
             this.ngbModalRef = null;
         });
     }

    activate(raisonPec: RaisonPec) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    raisonPec.active = true;
                    this.subscribeToSaveResponse( this.raisonPecService.update(raisonPec) );
                }
            })
    }

    disable(raisonPec: RaisonPec) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    raisonPec.active = false;
                    this.subscribeToSaveResponse( this.raisonPecService.update(raisonPec) );
                }
            })
    }

    edit(id: number) {
        this.raisonPecService.find(id).subscribe(raisonPec => {
            this.raisonPec = raisonPec;
        });
    }

    cancel() {
        this.raisonPec = new RaisonPec();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                if(confirmed) {
                    this.raisonPecService.delete(id).subscribe((response) => {
                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
    }

    ngOnInit() {
        this.isSaving = false;
        this.operationService.findForPec()
            .subscribe((res: ResponseWrapper) => { this.operations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.pecStatusChangeMatrixService.query()
            .subscribe((res: ResponseWrapper) => { this.pecStatusChangeMatrixs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.statusPecService.findWitchHasReason().subscribe((res: ResponseWrapper) => { this.statusPecs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(105, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }

    ngOnDestroy() {
    }

    private subscribeToSaveResponse(result: Observable<RaisonPec>) {
        result.subscribe(
            (res: RaisonPec) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: RaisonPec) {
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

    private onError(error) {
        this.isSaving = false;
        this.alertService.error(error.message, null, null);
    }
}
