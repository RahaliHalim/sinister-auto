import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefTypePieces } from './ref-type-pieces.model';
import { RefTypePiecesPopupService } from './ref-type-pieces-popup.service';
import { RefTypePiecesService } from './ref-type-pieces.service';

@Component({
    selector: 'jhi-ref-type-pieces-dialog',
    templateUrl: './ref-type-pieces-dialog.component.html'
})
export class RefTypePiecesDialogComponent implements OnInit {

    refTypePieces: RefTypePieces;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refTypePiecesService: RefTypePiecesService,
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
        if (this.refTypePieces.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refTypePiecesService.update(this.refTypePieces));
        } else {
            this.subscribeToSaveResponse(
                this.refTypePiecesService.create(this.refTypePieces));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefTypePieces>) {
        result.subscribe((res: RefTypePieces) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefTypePieces) {
        this.eventManager.broadcast({ name: 'refTypePiecesListModification', content: 'OK'});
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
    selector: 'jhi-ref-type-pieces-popup',
    template: ''
})
export class RefTypePiecesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypePiecesPopupService: RefTypePiecesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refTypePiecesPopupService
                    .open(RefTypePiecesDialogComponent as Component, params['id']);
            } else {
                this.refTypePiecesPopupService
                    .open(RefTypePiecesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
