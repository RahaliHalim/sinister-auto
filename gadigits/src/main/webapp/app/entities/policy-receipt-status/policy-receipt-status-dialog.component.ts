import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PolicyReceiptStatus } from './policy-receipt-status.model';
import { PolicyReceiptStatusPopupService } from './policy-receipt-status-popup.service';
import { PolicyReceiptStatusService } from './policy-receipt-status.service';

@Component({
    selector: 'jhi-policy-receipt-status-dialog',
    templateUrl: './policy-receipt-status-dialog.component.html'
})
export class PolicyReceiptStatusDialogComponent implements OnInit {

    policyReceiptStatus: PolicyReceiptStatus;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private policyReceiptStatusService: PolicyReceiptStatusService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.policyReceiptStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.policyReceiptStatusService.update(this.policyReceiptStatus));
        } else {
            this.subscribeToSaveResponse(
                this.policyReceiptStatusService.create(this.policyReceiptStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<PolicyReceiptStatus>) {
        result.subscribe((res: PolicyReceiptStatus) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PolicyReceiptStatus) {
        this.eventManager.broadcast({ name: 'policyReceiptStatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-policy-receipt-status-popup',
    template: ''
})
export class PolicyReceiptStatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyReceiptStatusPopupService: PolicyReceiptStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.policyReceiptStatusPopupService
                    .open(PolicyReceiptStatusDialogComponent as Component, params['id']);
            } else {
                this.policyReceiptStatusPopupService
                    .open(PolicyReceiptStatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
