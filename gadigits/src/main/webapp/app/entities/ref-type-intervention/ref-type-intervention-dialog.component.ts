import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefTypeIntervention } from './ref-type-intervention.model';
import { RefTypeInterventionPopupService } from './ref-type-intervention-popup.service';
import { RefTypeInterventionService } from './ref-type-intervention.service';
import { Grille, GrilleService } from '../grille';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ref-type-intervention-dialog',
    templateUrl: './ref-type-intervention-dialog.component.html'
})
export class RefTypeInterventionDialogComponent implements OnInit {

    refTypeIntervention: RefTypeIntervention;
    isSaving: boolean;

    grilles: Grille[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refTypeInterventionService: RefTypeInterventionService,
        private grilleService: GrilleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.grilleService.query()
            .subscribe((res: ResponseWrapper) => { this.grilles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.refTypeIntervention.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refTypeInterventionService.update(this.refTypeIntervention));
        } else {
            this.subscribeToSaveResponse(
                this.refTypeInterventionService.create(this.refTypeIntervention));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefTypeIntervention>) {
        result.subscribe((res: RefTypeIntervention) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefTypeIntervention) {
        this.eventManager.broadcast({ name: 'refTypeInterventionListModification', content: 'OK'});
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

    trackGrilleById(index: number, item: Grille) {
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
    selector: 'jhi-ref-type-intervention-popup',
    template: ''
})
export class RefTypeInterventionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypeInterventionPopupService: RefTypeInterventionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refTypeInterventionPopupService
                    .open(RefTypeInterventionDialogComponent as Component, params['id']);
            } else {
                this.refTypeInterventionPopupService
                    .open(RefTypeInterventionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
