import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Grille } from './grille.model';
import { GrillePopupService } from './grille-popup.service';
import { GrilleService } from './grille.service';
import { Reparateur, ReparateurService } from '../reparateur';
import { RefTypeIntervention, RefTypeInterventionService } from '../ref-type-intervention';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-grille-dialog',
    templateUrl: './grille-dialog.component.html'
})
export class GrilleDialogComponent implements OnInit {

    grille: Grille;
    isSaving: boolean;

    reparateurs: Reparateur[];

    reftypeinterventions: RefTypeIntervention[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private grilleService: GrilleService,
        private reparateurService: ReparateurService,
        private refTypeInterventionService: RefTypeInterventionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.reparateurService.query()
            .subscribe((res: ResponseWrapper) => { this.reparateurs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTypeInterventionService.query()
            .subscribe((res: ResponseWrapper) => { this.reftypeinterventions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.grille.id !== undefined) {
            this.subscribeToSaveResponse(
                this.grilleService.update(this.grille));
        } else {
            this.subscribeToSaveResponse(
                this.grilleService.create(this.grille));
        }
    }

    private subscribeToSaveResponse(result: Observable<Grille>) {
        result.subscribe((res: Grille) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Grille) {
        this.eventManager.broadcast({ name: 'grilleListModification', content: 'OK'});
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

    trackReparateurById(index: number, item: Reparateur) {
        return item.id;
    }

    trackRefTypeInterventionById(index: number, item: RefTypeIntervention) {
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
    selector: 'jhi-grille-popup',
    template: ''
})
export class GrillePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grillePopupService: GrillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.grillePopupService
                    .open(GrilleDialogComponent as Component, params['id']);
            } else {
                this.grillePopupService
                    .open(GrilleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
