import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PolicyType } from './policy-type.model';
import { PolicyTypePopupService } from './policy-type-popup.service';
import { PolicyTypeService } from './policy-type.service';

@Component({
    selector: 'jhi-policy-type-dialog',
    templateUrl: './policy-type-dialog.component.html'
})
export class PolicyTypeDialogComponent implements OnInit {

    policyType: PolicyType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private policyTypeService: PolicyTypeService,
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
        if (this.policyType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.policyTypeService.update(this.policyType));
        } else {
            this.subscribeToSaveResponse(
                this.policyTypeService.create(this.policyType));
        }
    }

    private subscribeToSaveResponse(result: Observable<PolicyType>) {
        result.subscribe((res: PolicyType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PolicyType) {
        this.eventManager.broadcast({ name: 'policyTypeListModification', content: 'OK'});
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
    selector: 'jhi-policy-type-popup',
    template: ''
})
export class PolicyTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyTypePopupService: PolicyTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.policyTypePopupService
                    .open(PolicyTypeDialogComponent as Component, params['id']);
            } else {
                this.policyTypePopupService
                    .open(PolicyTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
