import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefEtatDossier } from './ref-etat-dossier.model';
import { RefEtatDossierPopupService } from './ref-etat-dossier-popup.service';
import { RefEtatDossierService } from './ref-etat-dossier.service';

@Component({
    selector: 'jhi-ref-etat-dossier-dialog',
    templateUrl: './ref-etat-dossier-dialog.component.html'
})
export class RefEtatDossierDialogComponent implements OnInit {

    refEtatDossier: RefEtatDossier;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refEtatDossierService: RefEtatDossierService,
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
        if (this.refEtatDossier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refEtatDossierService.update(this.refEtatDossier));
        } else {
            this.subscribeToSaveResponse(
                this.refEtatDossierService.create(this.refEtatDossier));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefEtatDossier>) {
        result.subscribe((res: RefEtatDossier) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefEtatDossier) {
        this.eventManager.broadcast({ name: 'refEtatDossierListModification', content: 'OK'});
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
    selector: 'jhi-ref-etat-dossier-popup',
    template: ''
})
export class RefEtatDossierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refEtatDossierPopupService: RefEtatDossierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refEtatDossierPopupService
                    .open(RefEtatDossierDialogComponent as Component, params['id']);
            } else {
                this.refEtatDossierPopupService
                    .open(RefEtatDossierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
