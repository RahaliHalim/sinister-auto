import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Reglement } from './reglement.model';
import { ReglementPopupService } from './reglement-popup.service';
import { ReglementService } from './reglement.service';
import { RefModeReglement, RefModeReglementService } from '../ref-mode-reglement';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-reglement-dialog',
    templateUrl: './reglement-dialog.component.html'
})
export class ReglementDialogComponent implements OnInit {

    reglement: Reglement;
    isSaving: boolean;

    refmodereglements: RefModeReglement[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private reglementService: ReglementService,
        private refModeReglementService: RefModeReglementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refModeReglementService.query()
            .subscribe((res: ResponseWrapper) => { this.refmodereglements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reglement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reglementService.update(this.reglement));
        } else {
            this.subscribeToSaveResponse(
                this.reglementService.create(this.reglement));
        }
    }

    private subscribeToSaveResponse(result: Observable<Reglement>) {
        result.subscribe((res: Reglement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Reglement) {
        this.eventManager.broadcast({ name: 'reglementListModification', content: 'OK'});
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

    trackRefModeReglementById(index: number, item: RefModeReglement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-reglement-popup',
    template: ''
})
export class ReglementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reglementPopupService: ReglementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reglementPopupService
                    .open(ReglementDialogComponent as Component, params['id']);
            } else {
                this.reglementPopupService
                    .open(ReglementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
