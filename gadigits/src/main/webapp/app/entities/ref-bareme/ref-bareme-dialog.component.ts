import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefBareme } from './ref-bareme.model';
import { RefBaremePopupService } from './ref-bareme-popup.service';
import { RefBaremeService } from './ref-bareme.service';

@Component({
    selector: 'jhi-ref-bareme-dialog',
    templateUrl: './ref-bareme-dialog.component.html'
})
export class RefBaremeDialogComponent implements OnInit {

    refBareme: RefBareme;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refBaremeService: RefBaremeService,
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
        if (this.refBareme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refBaremeService.update(this.refBareme));
        } else {
            this.subscribeToSaveResponse(
                this.refBaremeService.create(this.refBareme));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefBareme>) {
        result.subscribe((res: RefBareme) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefBareme) {
        this.eventManager.broadcast({ name: 'refBaremeListModification', content: 'OK'});
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
    selector: 'jhi-ref-bareme-popup',
    template: ''
})
export class RefBaremePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refBaremePopupService: RefBaremePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refBaremePopupService
                    .open(RefBaremeDialogComponent as Component, params['id']);
            } else {
                this.refBaremePopupService
                    .open(RefBaremeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
