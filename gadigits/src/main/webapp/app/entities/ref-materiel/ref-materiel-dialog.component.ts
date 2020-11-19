import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefMateriel } from './ref-materiel.model';
import { RefMaterielPopupService } from './ref-materiel-popup.service';
import { RefMaterielService } from './ref-materiel.service';
import { Reparateur, ReparateurService } from '../reparateur';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ref-materiel-dialog',
    templateUrl: './ref-materiel-dialog.component.html'
})
export class RefMaterielDialogComponent implements OnInit {

    refMateriel: RefMateriel;
    isSaving: boolean;

    reparateurs: Reparateur[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refMaterielService: RefMaterielService,
        private reparateurService: ReparateurService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.reparateurService.query()
            .subscribe((res: ResponseWrapper) => { this.reparateurs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.refMateriel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refMaterielService.update(this.refMateriel));
        } else {
            this.subscribeToSaveResponse(
                this.refMaterielService.create(this.refMateriel));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefMateriel>) {
        result.subscribe((res: RefMateriel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefMateriel) {
        this.eventManager.broadcast({ name: 'refMaterielListModification', content: 'OK'});
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
    selector: 'jhi-ref-materiel-popup',
    template: ''
})
export class RefMaterielPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refMaterielPopupService: RefMaterielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refMaterielPopupService
                    .open(RefMaterielDialogComponent as Component, params['id']);
            } else {
                this.refMaterielPopupService
                    .open(RefMaterielDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
