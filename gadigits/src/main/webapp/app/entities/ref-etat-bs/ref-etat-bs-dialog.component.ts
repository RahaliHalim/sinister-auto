import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefEtatBs } from './ref-etat-bs.model';
import { RefEtatBsPopupService } from './ref-etat-bs-popup.service';
import { RefEtatBsService } from './ref-etat-bs.service';

@Component({
    selector: 'jhi-ref-etat-bs-dialog',
    templateUrl: './ref-etat-bs-dialog.component.html'
})
export class RefEtatBsDialogComponent implements OnInit {

    refEtatBs: RefEtatBs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refEtatBsService: RefEtatBsService,
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
        if (this.refEtatBs.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refEtatBsService.update(this.refEtatBs));
        } else {
            this.subscribeToSaveResponse(
                this.refEtatBsService.create(this.refEtatBs));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefEtatBs>) {
        result.subscribe((res: RefEtatBs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefEtatBs) {
        this.eventManager.broadcast({ name: 'refEtatBsListModification', content: 'OK'});
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
    selector: 'jhi-ref-etat-bs-popup',
    template: ''
})
export class RefEtatBsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refEtatBsPopupService: RefEtatBsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refEtatBsPopupService
                    .open(RefEtatBsDialogComponent as Component, params['id']);
            } else {
                this.refEtatBsPopupService
                    .open(RefEtatBsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
