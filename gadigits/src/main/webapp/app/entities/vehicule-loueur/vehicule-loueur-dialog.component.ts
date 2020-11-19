import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VehiculeLoueur } from './vehicule-loueur.model';
import { VehiculeLoueurPopupService } from './vehicule-loueur-popup.service';
import { VehiculeLoueurService } from './vehicule-loueur.service';

@Component({
    selector: 'jhi-vehicule-loueur-dialog',
    templateUrl: './vehicule-loueur-dialog.component.html'
})
export class VehiculeLoueurDialogComponent implements OnInit {

    vehiculeLoueur: VehiculeLoueur;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private vehiculeLoueurService: VehiculeLoueurService,
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
        if (this.vehiculeLoueur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vehiculeLoueurService.update(this.vehiculeLoueur));
        } else {
            this.subscribeToSaveResponse(
                this.vehiculeLoueurService.create(this.vehiculeLoueur));
        }
    }

    private subscribeToSaveResponse(result: Observable<VehiculeLoueur>) {
        result.subscribe((res: VehiculeLoueur) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: VehiculeLoueur) {
        this.eventManager.broadcast({ name: 'vehiculeLoueurListModification', content: 'OK'});
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
    selector: 'jhi-vehicule-loueur-popup',
    template: ''
})
export class VehiculeLoueurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehiculeLoueurPopupService: VehiculeLoueurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vehiculeLoueurPopupService
                    .open(VehiculeLoueurDialogComponent as Component, params['id']);
            } else {
                this.vehiculeLoueurPopupService
                    .open(VehiculeLoueurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
