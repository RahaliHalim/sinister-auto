import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefModeGestion } from './ref-mode-gestion.model';
import { RefModeGestionPopupService } from './ref-mode-gestion-popup.service';
import { RefModeGestionService } from './ref-mode-gestion.service';

@Component({
    selector: 'jhi-ref-mode-gestion-dialog',
    templateUrl: './ref-mode-gestion-dialog.component.html'
})
export class RefModeGestionDialogComponent implements OnInit {

    refModeGestion: RefModeGestion;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refModeGestionService: RefModeGestionService,
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
        if (this.refModeGestion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refModeGestionService.update(this.refModeGestion));
        } else {
            this.subscribeToSaveResponse(
                this.refModeGestionService.create(this.refModeGestion));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefModeGestion>) {
        result.subscribe((res: RefModeGestion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefModeGestion) {
        this.eventManager.broadcast({ name: 'refModeGestionListModification', content: 'OK'});
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
    selector: 'jhi-ref-mode-gestion-popup',
    template: ''
})
export class RefModeGestionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refModeGestionPopupService: RefModeGestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refModeGestionPopupService
                    .open(RefModeGestionDialogComponent as Component, params['id']);
            } else {
                this.refModeGestionPopupService
                    .open(RefModeGestionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
