import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Periodicity } from './periodicity.model';
import { PeriodicityPopupService } from './periodicity-popup.service';
import { PeriodicityService } from './periodicity.service';

@Component({
    selector: 'jhi-periodicity-dialog',
    templateUrl: './periodicity-dialog.component.html'
})
export class PeriodicityDialogComponent implements OnInit {

    periodicity: Periodicity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private periodicityService: PeriodicityService,
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
        if (this.periodicity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.periodicityService.update(this.periodicity));
        } else {
            this.subscribeToSaveResponse(
                this.periodicityService.create(this.periodicity));
        }
    }

    private subscribeToSaveResponse(result: Observable<Periodicity>) {
        result.subscribe((res: Periodicity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Periodicity) {
        this.eventManager.broadcast({ name: 'periodicityListModification', content: 'OK'});
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
    selector: 'jhi-periodicity-popup',
    template: ''
})
export class PeriodicityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodicityPopupService: PeriodicityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.periodicityPopupService
                    .open(PeriodicityDialogComponent as Component, params['id']);
            } else {
                this.periodicityPopupService
                    .open(PeriodicityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
