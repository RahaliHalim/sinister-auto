import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Piece } from './piece.model';
import { PiecePopupService } from './piece-popup.service';
import { PieceService } from './piece.service';
import { RefTypePieces, RefTypePiecesService } from '../ref-type-pieces';
import { VehicleBrandModel, VehicleBrandModelService } from '../vehicle-brand-model';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-piece-dialog',
    templateUrl: './piece-dialog.component.html'
})
export class PieceDialogComponent implements OnInit {

    piece: Piece;
    isSaving: boolean;

    reftypepieces: RefTypePieces[];

    refmodelvoitures: VehicleBrandModel[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pieceService: PieceService,
        private refTypePiecesService: RefTypePiecesService,
        private refModelVoitureService: VehicleBrandModelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refTypePiecesService.query()
            .subscribe((res: ResponseWrapper) => { this.reftypepieces = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refModelVoitureService.query()
            .subscribe((res: ResponseWrapper) => { this.refmodelvoitures = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.piece.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pieceService.update(this.piece));
        } else {
            this.subscribeToSaveResponse(
                this.pieceService.create(this.piece));
        }
    }

    private subscribeToSaveResponse(result: Observable<Piece>) {
        result.subscribe((res: Piece) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Piece) {
        this.eventManager.broadcast({ name: 'pieceListModification', content: 'OK'});
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

    trackRefTypePiecesById(index: number, item: RefTypePieces) {
        return item.id;
    }

    trackRefModelVoitureById(index: number, item: VehicleBrandModel) {
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
    selector: 'jhi-piece-popup',
    template: ''
})
export class PiecePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private piecePopupService: PiecePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.piecePopupService
                    .open(PieceDialogComponent as Component, params['id']);
            } else {
                this.piecePopupService
                    .open(PieceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
