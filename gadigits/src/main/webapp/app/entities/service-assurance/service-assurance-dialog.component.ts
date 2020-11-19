import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ServiceAssurance } from './service-assurance.model';
import { ServiceAssurancePopupService } from './service-assurance-popup.service';
import { ServiceAssuranceService } from './service-assurance.service';

@Component({
    selector: 'jhi-service-assurance-dialog',
    templateUrl: './service-assurance-dialog.component.html'
})
export class ServiceAssuranceDialogComponent implements OnInit {

    serviceAssurance: ServiceAssurance;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private serviceAssuranceService: ServiceAssuranceService,
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
        if (this.serviceAssurance.id !== undefined) {
            this.subscribeToSaveResponse(
                this.serviceAssuranceService.update(this.serviceAssurance));
        } else {
            this.subscribeToSaveResponse(
                this.serviceAssuranceService.create(this.serviceAssurance));
        }
    }

    private subscribeToSaveResponse(result: Observable<ServiceAssurance>) {
        result.subscribe((res: ServiceAssurance) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ServiceAssurance) {
        this.eventManager.broadcast({ name: 'serviceAssuranceListModification', content: 'OK'});
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
    selector: 'jhi-service-assurance-popup',
    template: ''
})
export class ServiceAssurancePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceAssurancePopupService: ServiceAssurancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.serviceAssurancePopupService
                    .open(ServiceAssuranceDialogComponent as Component, params['id']);
            } else {
                this.serviceAssurancePopupService
                    .open(ServiceAssuranceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
