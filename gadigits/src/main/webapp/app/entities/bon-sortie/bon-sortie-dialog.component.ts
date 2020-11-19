import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BonSortie } from './bon-sortie.model';
import { BonSortiePopupService } from './bon-sortie-popup.service';
import { BonSortieService } from './bon-sortie.service';
import { RefEtatBs, RefEtatBsService } from '../ref-etat-bs';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-bon-sortie-dialog',
    templateUrl: './bon-sortie-dialog.component.html'
})
export class BonSortieDialogComponent implements OnInit {

    bonSortie: BonSortie;
    isSaving: boolean;

    refetatbs: RefEtatBs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bonSortieService: BonSortieService,
        private refEtatBsService: RefEtatBsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refEtatBsService.query()
            .subscribe((res: ResponseWrapper) => { this.refetatbs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bonSortie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bonSortieService.update(this.bonSortie));
        } else {
            this.subscribeToSaveResponse(
                this.bonSortieService.create(this.bonSortie));
        }
    }

    private subscribeToSaveResponse(result: Observable<BonSortie>) {
        result.subscribe((res: BonSortie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BonSortie) {
        this.eventManager.broadcast({ name: 'bonSortieListModification', content: 'OK'});
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

    trackRefEtatBsById(index: number, item: RefEtatBs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-bon-sortie-popup',
    template: ''
})
export class BonSortiePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bonSortiePopupService: BonSortiePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bonSortiePopupService
                    .open(BonSortieDialogComponent as Component, params['id']);
            } else {
                this.bonSortiePopupService
                    .open(BonSortieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
