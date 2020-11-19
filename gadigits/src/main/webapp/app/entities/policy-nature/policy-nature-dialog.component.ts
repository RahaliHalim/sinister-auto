import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PolicyNature } from './policy-nature.model';
import { PolicyNaturePopupService } from './policy-nature-popup.service';
import { PolicyNatureService } from './policy-nature.service';

@Component({
    selector: 'jhi-policy-nature-dialog',
    templateUrl: './policy-nature-dialog.component.html'
})
export class PolicyNatureDialogComponent implements OnInit {

    policyNature: PolicyNature;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private policyNatureService: PolicyNatureService,
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
        if (this.policyNature.id !== undefined) {
            this.subscribeToSaveResponse(
                this.policyNatureService.update(this.policyNature));
        } else {
            this.subscribeToSaveResponse(
                this.policyNatureService.create(this.policyNature));
        }
    }

    private subscribeToSaveResponse(result: Observable<PolicyNature>) {
        result.subscribe((res: PolicyNature) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PolicyNature) {
        this.eventManager.broadcast({ name: 'policyNatureListModification', content: 'OK'});
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
    selector: 'jhi-policy-nature-popup',
    template: ''
})
export class PolicyNaturePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyNaturePopupService: PolicyNaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.policyNaturePopupService
                    .open(PolicyNatureDialogComponent as Component, params['id']);
            } else {
                this.policyNaturePopupService
                    .open(PolicyNatureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
