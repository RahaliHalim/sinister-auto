import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Reason } from './reason.model';
import { ReasonPopupService } from './reason-popup.service';
import { ReasonService } from './reason.service';
import { Step, StepService } from '../step';
import { ResponseWrapper } from '../../shared';
import { UserExtraService, PermissionAccess} from '../user-extra';

@Component({
    selector: 'jhi-reason-dialog',
    templateUrl: './reason-dialog.component.html'
})
export class ReasonDialogComponent implements OnInit {

    reason: Reason;
    isSaving: boolean;
    permissionToAccess : PermissionAccess = new PermissionAccess();
    steps: Step[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private reasonService: ReasonService,
        private stepService: StepService,
        private eventManager: JhiEventManager,
        private userExtraService : UserExtraService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.stepService.query()
            .subscribe((res: ResponseWrapper) => { this.steps = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reason.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reasonService.update(this.reason));
        } else {
            this.subscribeToSaveResponse(
                this.reasonService.create(this.reason));
        }
    }

    private subscribeToSaveResponse(result: Observable<Reason>) {
        result.subscribe((res: Reason) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Reason) {
        this.eventManager.broadcast({ name: 'reasonListModification', content: 'OK'});
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

    trackStepById(index: number, item: Step) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-reason-popup',
    template: ''
})
export class ReasonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reasonPopupService: ReasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reasonPopupService
                    .open(ReasonDialogComponent as Component, params['id']);
            } else {
                this.reasonPopupService
                    .open(ReasonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
