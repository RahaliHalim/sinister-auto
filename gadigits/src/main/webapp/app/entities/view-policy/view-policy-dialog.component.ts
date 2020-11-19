import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ViewPolicy } from './view-policy.model';
import { ViewPolicyPopupService } from './view-policy-popup.service';
import { ViewPolicyService } from './view-policy.service';

@Component({
    selector: 'jhi-view-policy-dialog',
    templateUrl: './view-policy-dialog.component.html'
})
export class ViewPolicyDialogComponent implements OnInit {

    viewPolicy: ViewPolicy;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private viewPolicyService: ViewPolicyService,
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
        if (this.viewPolicy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.viewPolicyService.update(this.viewPolicy));
        } else {
            this.subscribeToSaveResponse(
                this.viewPolicyService.create(this.viewPolicy));
        }
    }

    private subscribeToSaveResponse(result: Observable<ViewPolicy>) {
        result.subscribe((res: ViewPolicy) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ViewPolicy) {
        this.eventManager.broadcast({ name: 'viewPolicyListModification', content: 'OK'});
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
    selector: 'jhi-view-policy-popup',
    template: ''
})
export class ViewPolicyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private viewPolicyPopupService: ViewPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.viewPolicyPopupService
                    .open(ViewPolicyDialogComponent as Component, params['id']);
            } else {
                this.viewPolicyPopupService
                    .open(ViewPolicyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
