import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ConfirmationDialogService, Principal, ResponseWrapper, AlertUtil } from '../../shared';
import { GaDatatable } from './../../constants/app.constants';
import { VatRate } from './vat-rate.model';
import { VatRateService } from './vat-rate.service';
import { DataTableDirective } from 'angular-datatables';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';

@Component({
    selector: 'jhi-vat-rate',
    templateUrl: './vat-rate.component.html'
})
export class VatRateComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    
    vatRates: VatRate[];
    vatRate: VatRate = new VatRate();
    vatRateWork: VatRate;
    
    permissionToAccess : PermissionAccess = new PermissionAccess();
    private ngbModalRef: NgbModalRef;

    isSaving: boolean;
    currentAccount: any;
    eventSubscriber: Subscription;
    amountPattern = '^[0-9]+(\.[0-9]{1,3})?';
    dtOptions: any = {};
    dtTrigger: Subject<VatRate> = new Subject();
    existVatRate = true;
    constructor(
        private vatRateService: VatRateService,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private userExtraService : UserExtraService,
        private historyPopupService: HistoryPopupService,
    ) {
    }
    ngAfterViewInit(): void {
        this.dtTrigger.next();
        console.log(this.dtElement)
    }
    loadAll() {
        this.vatRateService.query().subscribe(
            (res: ResponseWrapper) => {
                this.vatRates = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
  
    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }

    trimLabel() {
        this.vatRate.vatRate = this.vatRate.vatRate != undefined ? +('' + this.vatRate.vatRate).trim() : undefined;
    }

    selectHistory(id) {
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"TauxTVA");
         this.ngbModalRef.result.then((result: any) => {
             this.ngbModalRef = null;
         }, (reason) => {
             this.ngbModalRef = null;
         });
     }

    save() {
        this.existVatRate = true;
        for (let i = 0; i < this.vatRates.length; i++) {
            if(this.vatRate.id !== undefined && this.vatRate.id !== null) { // Update
                if (this.vatRate.vatRate === this.vatRates[i].vatRate && this.vatRate.id !== this.vatRates[i].id){
                    this.existVatRate = true;
                    this.alertUtil.addError("auxiliumApp.vatRate.exist");
                    break;
                } else {
                    this.existVatRate = false;
                } 
            } else { // Create
                if (this.vatRate.vatRate === this.vatRates[i].vatRate){
                    this.existVatRate = true;
                    this.alertUtil.addError("auxiliumApp.vatRate.exist");
                    break;
                } else {
                    this.existVatRate = false;
                } 

            } 
        }
        if(!this.existVatRate) { 
            this.confirmationDialogService
                .confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
                .then(confirmed => {
                    if (confirmed) {
                        this.isSaving = true;
                        if (this.vatRate.id !== undefined) {
                            
                            this.subscribeToSaveResponse(
                                this.vatRateService.update(
                                    this.vatRate
                                )
                            );
                        } else {
                            this.subscribeToSaveResponse(
                                this.vatRateService.create(
                                    this.vatRate
                                )
                            );
                        }
                    }
                })
                .catch(() =>
                    console.log(
                        'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'
                    )
                );
        } 
    }

    edit(id: number) {
        this.vatRateService.find(id).subscribe(vatRate => {
            this.vatRate = vatRate;
            this.vatRateWork = vatRate;
            if (this.vatRate.effectiveDate) {
                const date = new Date(this.vatRate.effectiveDate);
                this.vatRate.effectiveDate = {
                    year: date.getFullYear(),
                    month: date.getMonth() + 1,
                    day: date.getDate()
                };
            }
        });
    }

    cancel() {
        this.vatRate = new VatRate();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                console.log( 'User confirmed:', confirmed );
                if(confirmed) {
                    this.vatRateService.delete(id).subscribe((response) => {

                        console.log( 'User confirmed delete:', id );

                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
            .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(86,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInVatRates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VatRate) {
        return item.id;
    }
    registerChangeInVatRates() {
        this.eventSubscriber = this.eventManager.subscribe('vatRateListModification', (response) => this.loadAll());
    }
    private subscribeToSaveResponse(result: Observable<VatRate>) {
        result.subscribe(
            (res: VatRate) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: VatRate) {
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
        this.alertUtil.addError(error.message);
    }
}
