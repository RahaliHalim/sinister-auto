import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefModeReglement } from './ref-mode-reglement.model';
import { RefModeReglementPopupService } from './ref-mode-reglement-popup.service';
import { RefModeReglementService } from './ref-mode-reglement.service';

@Component({
    selector: 'jhi-ref-mode-reglement-dialog',
    templateUrl: './ref-mode-reglement-dialog.component.html'
})
export class RefModeReglementDialogComponent implements OnInit {

    refModeReglement: RefModeReglement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refModeReglementService: RefModeReglementService,
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
        if (this.refModeReglement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refModeReglementService.update(this.refModeReglement));
        } else {
            this.subscribeToSaveResponse(
                this.refModeReglementService.create(this.refModeReglement));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefModeReglement>) {
        result.subscribe((res: RefModeReglement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefModeReglement) {
        this.eventManager.broadcast({ name: 'refModeReglementListModification', content: 'OK'});
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
    selector: 'jhi-ref-mode-reglement-popup',
    template: ''
})
export class RefModeReglementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refModeReglementPopupService: RefModeReglementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refModeReglementPopupService
                    .open(RefModeReglementDialogComponent as Component, params['id']);
            } else {
                this.refModeReglementPopupService
                    .open(RefModeReglementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
