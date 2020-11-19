import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Lien } from './lien.model';
import { LienPopupService } from './lien-popup.service';
import { LienService } from './lien.service';
import { Cellule, CelluleService } from '../cellule';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lien-dialog',
    templateUrl: './lien-dialog.component.html'
})
export class LienDialogComponent implements OnInit {

    lien: Lien;
    isSaving: boolean;

    cellules: Cellule[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private lienService: LienService,
        private celluleService: CelluleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.celluleService.query()
            .subscribe((res: ResponseWrapper) => { this.cellules = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lien.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lienService.update(this.lien));
        } else {
            this.subscribeToSaveResponse(
                this.lienService.create(this.lien));
        }
    }

    private subscribeToSaveResponse(result: Observable<Lien>) {
        result.subscribe((res: Lien) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Lien) {
        this.eventManager.broadcast({ name: 'lienListModification', content: 'OK'});
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

    trackCelluleById(index: number, item: Cellule) {
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
    selector: 'jhi-lien-popup',
    template: ''
})
export class LienPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lienPopupService: LienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lienPopupService
                    .open(LienDialogComponent as Component, params['id']);
            } else {
                this.lienPopupService
                    .open(LienDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
