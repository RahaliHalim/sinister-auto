import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AmendmentType } from './amendment-type.model';
import { AmendmentTypePopupService } from './amendment-type-popup.service';
import { AmendmentTypeService } from './amendment-type.service';

@Component({
    selector: 'jhi-amendment-type-dialog',
    templateUrl: './amendment-type-dialog.component.html'
})
export class AmendmentTypeDialogComponent implements OnInit {

    amendmentType: AmendmentType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private amendmentTypeService: AmendmentTypeService,
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
        if (this.amendmentType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.amendmentTypeService.update(this.amendmentType));
        } else {
            this.subscribeToSaveResponse(
                this.amendmentTypeService.create(this.amendmentType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AmendmentType>) {
        result.subscribe((res: AmendmentType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AmendmentType) {
        this.eventManager.broadcast({ name: 'amendmentTypeListModification', content: 'OK'});
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
    selector: 'jhi-amendment-type-popup',
    template: ''
})
export class AmendmentTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amendmentTypePopupService: AmendmentTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.amendmentTypePopupService
                    .open(AmendmentTypeDialogComponent as Component, params['id']);
            } else {
                this.amendmentTypePopupService
                    .open(AmendmentTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
