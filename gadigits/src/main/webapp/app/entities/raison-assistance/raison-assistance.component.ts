import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiEventManager, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { GaDatatable, Global } from './../../constants/app.constants';
import { RaisonAssistance } from './raison-assistance.model';
import { RaisonAssistanceService } from './raison-assistance.service';
import { Operation } from '../operation/operation.model';
import { OperationService } from '../operation/operation.service';

import { ConfirmationDialogService, Principal, ResponseWrapper } from '../../shared';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';


@Component({
    selector: 'jhi-raison-assistance',
    templateUrl: './raison-assistance.component.html'
})
export class RaisonAssistanceComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    raisonAssistances: RaisonAssistance[];
    raisonAssistance: RaisonAssistance = new RaisonAssistance();
    operations: Operation[];
    statuss = [{id: 2, label: "Annulé"}, {id: 4, label: "Réouvert"}];
    isSaving: boolean;
    currentAccount: any;
    textPattern = Global.textPattern;
    private ngbModalRef: NgbModalRef;
    
    dtOptions: any = {};
    dtTrigger: Subject<RaisonAssistance> = new Subject();

    constructor(
        private raisonAssistanceService: RaisonAssistanceService,
        private alertService: JhiAlertService,
        private operationService: OperationService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private historyPopupService: HistoryPopupService,
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
        this.raisonAssistanceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.raisonAssistances = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.raisonAssistance.label = this.raisonAssistance.label.trim();
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.raisonAssistance.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.raisonAssistanceService.update(
                                this.raisonAssistance
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.raisonAssistanceService.create(
                                this.raisonAssistance
                            )
                        );
                    }
                }
            })
    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"RaisonAssistance");
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

    activate(raisonAssistance: RaisonAssistance) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    raisonAssistance.active = true;
                    this.subscribeToSaveResponse( this.raisonAssistanceService.update(raisonAssistance) );
                }
            })
    }

    disable(raisonAssistance: RaisonAssistance) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    raisonAssistance.active = false;
                    this.subscribeToSaveResponse( this.raisonAssistanceService.update(raisonAssistance) );
                }
            })
    }

    edit(id: number) {
        this.raisonAssistanceService.find(id).subscribe(raisonAssistance => {
            this.raisonAssistance = raisonAssistance;
        });
    }

    cancel() {
        this.raisonAssistance = new RaisonAssistance();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                if(confirmed) {
                    this.raisonAssistanceService.delete(id).subscribe((response) => {
                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
    }

    ngOnInit() {
        this.isSaving = false;
        this.operationService.findForAssistance()
            .subscribe((res: ResponseWrapper) => { this.operations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
    }

    private subscribeToSaveResponse(result: Observable<RaisonAssistance>) {
        result.subscribe(
            (res: RaisonAssistance) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: RaisonAssistance) {
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
