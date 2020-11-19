import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BusinessEntity } from './business-entity.model';
import { BusinessEntityPopupService } from './business-entity-popup.service';
import { BusinessEntityService } from './business-entity.service';

@Component({
    selector: 'jhi-business-entity-dialog',
    templateUrl: './business-entity-dialog.component.html'
})
export class BusinessEntityDialogComponent implements OnInit {

    businessEntity: BusinessEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private businessEntityService: BusinessEntityService,
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
        if (this.businessEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.businessEntityService.update(this.businessEntity));
        } else {
            this.subscribeToSaveResponse(
                this.businessEntityService.create(this.businessEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<BusinessEntity>) {
        result.subscribe((res: BusinessEntity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BusinessEntity) {
        this.eventManager.broadcast({ name: 'businessEntityListModification', content: 'OK'});
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
    selector: 'jhi-business-entity-popup',
    template: ''
})
export class BusinessEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessEntityPopupService: BusinessEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.businessEntityPopupService
                    .open(BusinessEntityDialogComponent as Component, params['id']);
            } else {
                this.businessEntityPopupService
                    .open(BusinessEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
