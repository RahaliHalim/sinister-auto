import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefTypeContrat } from './ref-type-contrat.model';
import { RefTypeContratPopupService } from './ref-type-contrat-popup.service';
import { RefTypeContratService } from './ref-type-contrat.service';
import { RefCompagnie, RefCompagnieService } from '../ref-compagnie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ref-type-contrat-dialog',
    templateUrl: './ref-type-contrat-dialog.component.html'
})
export class RefTypeContratDialogComponent implements OnInit {

    refTypeContrat: RefTypeContrat;
    isSaving: boolean;

    refcompagnies: RefCompagnie[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refTypeContratService: RefTypeContratService,
        private refCompagnieService: RefCompagnieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refCompagnieService.query()
            .subscribe((res: ResponseWrapper) => { this.refcompagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.refTypeContrat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refTypeContratService.update(this.refTypeContrat));
        } else {
            this.subscribeToSaveResponse(
                this.refTypeContratService.create(this.refTypeContrat));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefTypeContrat>) {
        result.subscribe((res: RefTypeContrat) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefTypeContrat) {
        this.eventManager.broadcast({ name: 'refTypeContratListModification', content: 'OK'});
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

    trackRefCompagnieById(index: number, item: RefCompagnie) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-ref-type-contrat-popup',
    template: ''
})
export class RefTypeContratPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypeContratPopupService: RefTypeContratPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refTypeContratPopupService
                    .open(RefTypeContratDialogComponent as Component, params['id']);
            } else {
                this.refTypeContratPopupService
                    .open(RefTypeContratDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
