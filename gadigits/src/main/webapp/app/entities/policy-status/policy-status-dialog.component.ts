import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PolicyStatus } from './policy-status.model';
import { PolicyStatusPopupService } from './policy-status-popup.service';
import { PolicyStatusService } from './policy-status.service';

@Component({
    selector: 'jhi-policy-status-dialog',
    templateUrl: './policy-status-dialog.component.html'
})
export class PolicyStatusDialogComponent implements OnInit {

    policyStatus: PolicyStatus;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private policyStatusService: PolicyStatusService,
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
        if (this.policyStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.policyStatusService.update(this.policyStatus));
        } else {
            this.subscribeToSaveResponse(
                this.policyStatusService.create(this.policyStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<PolicyStatus>) {
        result.subscribe((res: PolicyStatus) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PolicyStatus) {
        this.eventManager.broadcast({ name: 'policyStatusListModification', content: 'OK'});
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
    selector: 'jhi-policy-status-popup',
    template: ''
})
export class PolicyStatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyStatusPopupService: PolicyStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.policyStatusPopupService
                    .open(PolicyStatusDialogComponent as Component, params['id']);
            } else {
                this.policyStatusPopupService
                    .open(PolicyStatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
