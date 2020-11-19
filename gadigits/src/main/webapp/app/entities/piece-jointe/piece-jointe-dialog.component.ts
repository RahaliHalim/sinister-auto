import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PieceJointe } from './piece-jointe.model';
import { PieceJointePopupService } from './piece-jointe-popup.service';
import { PieceJointeService } from './piece-jointe.service';
import { RefTypePj, RefTypePjService } from '../ref-type-pj';
import { RefEtatDossier, RefEtatDossierService } from '../ref-etat-dossier';
import { ResponseWrapper } from '../../shared';
import { SinisterPec } from '../sinister-pec';

@Component({
    selector: 'jhi-piece-jointe-dialog',
    templateUrl: './piece-jointe-dialog.component.html'
})
export class PieceJointeDialogComponent implements OnInit {

    pieceJointe: PieceJointe;
    isSaving: boolean;

    reftypepjs: RefTypePj[];

    refetatdossiers: RefEtatDossier[];

    dateImportDp: any;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pieceJointeService: PieceJointeService,
        private refTypePjService: RefTypePjService,
        private refEtatDossierService: RefEtatDossierService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refTypePjService.query()
            .subscribe((res: ResponseWrapper) => { this.reftypepjs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refEtatDossierService.query()
            .subscribe((res: ResponseWrapper) => { this.refetatdossiers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    

    save() {
        this.isSaving = true;
        if (this.pieceJointe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pieceJointeService.update(this.pieceJointe));
        } else {
            this.subscribeToSaveResponse(
                this.pieceJointeService.create(this.pieceJointe));
        }
    }
    

    private subscribeToSaveResponse(result: Observable<PieceJointe>) {
        result.subscribe((res: PieceJointe) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PieceJointe) {
        this.eventManager.broadcast({ name: 'pieceJointeListModification', content: 'OK'});
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

    trackRefTypePjById(index: number, item: RefTypePj) {
        return item.id;
    }

    trackRefEtatDossierById(index: number, item: RefEtatDossier) {
        return item.id;
    }

    trackPrestationById(index: number, item: SinisterPec) {
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
    selector: 'jhi-piece-jointe-popup',
    template: ''
})
export class PieceJointePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pieceJointePopupService: PieceJointePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pieceJointePopupService
                    .open(PieceJointeDialogComponent as Component, params['id']);
            } else {
                this.pieceJointePopupService
                    .open(PieceJointeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
