import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Fournisseur } from './fournisseur.model';
import { FournisseurPopupService } from './fournisseur-popup.service';
import { FournisseurService } from './fournisseur.service';
import { PersonneMorale, PersonneMoraleService } from '../personne-morale';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fournisseur-dialog',
    templateUrl: './fournisseur-dialog.component.html'
})
export class FournisseurDialogComponent implements OnInit {

    fournisseur: Fournisseur;
    isSaving: boolean;

    personnemorales: PersonneMorale[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private fournisseurService: FournisseurService,
        private personneMoraleService: PersonneMoraleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personneMoraleService
            .query({filter: 'fournisseur-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.fournisseur.personneMoraleId) {
                    this.personnemorales = res.json;
                } else {
                    this.personneMoraleService
                        .find(this.fournisseur.personneMoraleId)
                        .subscribe((subRes: PersonneMorale) => {
                            this.personnemorales = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fournisseur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fournisseurService.update(this.fournisseur));
        } else {
            this.subscribeToSaveResponse(
                this.fournisseurService.create(this.fournisseur));
        }
    }

    private subscribeToSaveResponse(result: Observable<Fournisseur>) {
        result.subscribe((res: Fournisseur) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Fournisseur) {
        this.eventManager.broadcast({ name: 'fournisseurListModification', content: 'OK'});
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

    trackPersonneMoraleById(index: number, item: PersonneMorale) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-fournisseur-popup',
    template: ''
})
export class FournisseurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fournisseurPopupService: FournisseurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fournisseurPopupService
                    .open(FournisseurDialogComponent as Component, params['id']);
            } else {
                this.fournisseurPopupService
                    .open(FournisseurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
