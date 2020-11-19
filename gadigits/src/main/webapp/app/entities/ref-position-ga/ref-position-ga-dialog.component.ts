import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefPositionGa } from './ref-position-ga.model';
import { RefPositionGaPopupService } from './ref-position-ga-popup.service';
import { RefPositionGaService } from './ref-position-ga.service';

@Component({
    selector: 'jhi-ref-position-ga-dialog',
    templateUrl: './ref-position-ga-dialog.component.html'
})
export class RefPositionGaDialogComponent implements OnInit {

    refPositionGa: RefPositionGa;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refPositionGaService: RefPositionGaService,
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
        if (this.refPositionGa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refPositionGaService.update(this.refPositionGa));
        } else {
            this.subscribeToSaveResponse(
                this.refPositionGaService.create(this.refPositionGa));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefPositionGa>) {
        result.subscribe((res: RefPositionGa) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefPositionGa) {
        this.eventManager.broadcast({ name: 'refPositionGaListModification', content: 'OK'});
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
    selector: 'jhi-ref-position-ga-popup',
    template: ''
})
export class RefPositionGaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refPositionGaPopupService: RefPositionGaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refPositionGaPopupService
                    .open(RefPositionGaDialogComponent as Component, params['id']);
            } else {
                this.refPositionGaPopupService
                    .open(RefPositionGaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
