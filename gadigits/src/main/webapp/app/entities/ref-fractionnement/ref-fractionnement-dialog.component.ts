import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefFractionnement } from './ref-fractionnement.model';
import { RefFractionnementPopupService } from './ref-fractionnement-popup.service';
import { RefFractionnementService } from './ref-fractionnement.service';

@Component({
    selector: 'jhi-ref-fractionnement-dialog',
    templateUrl: './ref-fractionnement-dialog.component.html'
})
export class RefFractionnementDialogComponent implements OnInit {

    refFractionnement: RefFractionnement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refFractionnementService: RefFractionnementService,
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
        if (this.refFractionnement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refFractionnementService.update(this.refFractionnement));
        } else {
            this.subscribeToSaveResponse(
                this.refFractionnementService.create(this.refFractionnement));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefFractionnement>) {
        result.subscribe((res: RefFractionnement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefFractionnement) {
        this.eventManager.broadcast({ name: 'refFractionnementListModification', content: 'OK'});
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
    selector: 'jhi-ref-fractionnement-popup',
    template: ''
})
export class RefFractionnementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refFractionnementPopupService: RefFractionnementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refFractionnementPopupService
                    .open(RefFractionnementDialogComponent as Component, params['id']);
            } else {
                this.refFractionnementPopupService
                    .open(RefFractionnementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
