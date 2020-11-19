import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tarif } from './tarif.model';
import { TarifPopupService } from './tarif-popup.service';
import { TarifService } from './tarif.service';

@Component({
    selector: 'jhi-tarif-dialog',
    templateUrl: './tarif-dialog.component.html'
})
export class TarifDialogComponent implements OnInit {

    tarif: Tarif;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tarifService: TarifService,
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
        if (this.tarif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tarifService.update(this.tarif));
        } else {
            this.subscribeToSaveResponse(
                this.tarifService.create(this.tarif));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tarif>) {
        result.subscribe((res: Tarif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Tarif) {
        this.eventManager.broadcast({ name: 'tarifListModification', content: 'OK'});
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
    selector: 'jhi-tarif-popup',
    template: ''
})
export class TarifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tarifPopupService: TarifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tarifPopupService
                    .open(TarifDialogComponent as Component, params['id']);
            } else {
                this.tarifPopupService
                    .open(TarifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
