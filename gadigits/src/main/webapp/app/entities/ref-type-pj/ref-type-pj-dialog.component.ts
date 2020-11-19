import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefTypePj } from './ref-type-pj.model';
import { RefTypePjPopupService } from './ref-type-pj-popup.service';
import { RefTypePjService } from './ref-type-pj.service';

@Component({
    selector: 'jhi-ref-type-pj-dialog',
    templateUrl: './ref-type-pj-dialog.component.html'
})
export class RefTypePjDialogComponent implements OnInit {

    refTypePj: RefTypePj;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refTypePjService: RefTypePjService,
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
        if (this.refTypePj.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refTypePjService.update(this.refTypePj));
        } else {
            this.subscribeToSaveResponse(
                this.refTypePjService.create(this.refTypePj));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefTypePj>) {
        result.subscribe((res: RefTypePj) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefTypePj) {
        this.eventManager.broadcast({ name: 'refTypePjListModification', content: 'OK'});
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
    selector: 'jhi-ref-type-pj-popup',
    template: ''
})
export class RefTypePjPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypePjPopupService: RefTypePjPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refTypePjPopupService
                    .open(RefTypePjDialogComponent as Component, params['id']);
            } else {
                this.refTypePjPopupService
                    .open(RefTypePjDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
