import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefNatureContrat } from './ref-nature-contrat.model';
import { RefNatureContratPopupService } from './ref-nature-contrat-popup.service';
import { RefNatureContratService } from './ref-nature-contrat.service';
import { RefCompagnie, RefCompagnieService } from '../ref-compagnie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ref-nature-contrat-dialog',
    templateUrl: './ref-nature-contrat-dialog.component.html'
})
export class RefNatureContratDialogComponent implements OnInit {

    refNatureContrat: RefNatureContrat;
    isSaving: boolean;

    refcompagnies: RefCompagnie[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refNatureContratService: RefNatureContratService,
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
        if (this.refNatureContrat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refNatureContratService.update(this.refNatureContrat));
        } else {
            this.subscribeToSaveResponse(
                this.refNatureContratService.create(this.refNatureContrat));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefNatureContrat>) {
        result.subscribe((res: RefNatureContrat) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefNatureContrat) {
        this.eventManager.broadcast({ name: 'refNatureContratListModification', content: 'OK'});
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
    selector: 'jhi-ref-nature-contrat-popup',
    template: ''
})
export class RefNatureContratPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refNatureContratPopupService: RefNatureContratPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refNatureContratPopupService
                    .open(RefNatureContratDialogComponent as Component, params['id']);
            } else {
                this.refNatureContratPopupService
                    .open(RefNatureContratDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
