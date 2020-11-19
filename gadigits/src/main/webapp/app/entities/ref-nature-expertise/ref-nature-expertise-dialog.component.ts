import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefNatureExpertise } from './ref-nature-expertise.model';
import { RefNatureExpertisePopupService } from './ref-nature-expertise-popup.service';
import { RefNatureExpertiseService } from './ref-nature-expertise.service';

@Component({
    selector: 'jhi-ref-nature-expertise-dialog',
    templateUrl: './ref-nature-expertise-dialog.component.html'
})
export class RefNatureExpertiseDialogComponent implements OnInit {

    refNatureExpertise: RefNatureExpertise;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refNatureExpertiseService: RefNatureExpertiseService,
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
        if (this.refNatureExpertise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refNatureExpertiseService.update(this.refNatureExpertise));
        } else {
            this.subscribeToSaveResponse(
                this.refNatureExpertiseService.create(this.refNatureExpertise));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefNatureExpertise>) {
        result.subscribe((res: RefNatureExpertise) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefNatureExpertise) {
        this.eventManager.broadcast({ name: 'refNatureExpertiseListModification', content: 'OK'});
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
    selector: 'jhi-ref-nature-expertise-popup',
    template: ''
})
export class RefNatureExpertisePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refNatureExpertisePopupService: RefNatureExpertisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refNatureExpertisePopupService
                    .open(RefNatureExpertiseDialogComponent as Component, params['id']);
            } else {
                this.refNatureExpertisePopupService
                    .open(RefNatureExpertiseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
