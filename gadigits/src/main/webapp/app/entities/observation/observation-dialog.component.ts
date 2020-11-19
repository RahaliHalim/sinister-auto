import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Observation } from './observation.model';
import { ObservationPopupService } from './observation-popup.service';
import { ObservationService } from './observation.service';

@Component({
    selector: 'jhi-observation-dialog',
    templateUrl: './observation-dialog.component.html'
})
export class ObservationDialogComponent implements OnInit {

    observation: Observation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private observationService: ObservationService,
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
        if (this.observation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.observationService.update(this.observation));
        } else {
            this.subscribeToSaveResponse(
                this.observationService.create(this.observation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Observation>) {
        result.subscribe((res: Observation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Observation) {
        this.eventManager.broadcast({ name: 'observationListModification', content: 'OK'});
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
    selector: 'jhi-observation-popup',
    template: ''
})
export class ObservationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private observationPopupService: ObservationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.observationPopupService
                    .open(ObservationDialogComponent as Component, params['id']);
            } else {
                this.observationPopupService
                    .open(ObservationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
