import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Piece } from './piece.model';
import { PiecePopupService } from './piece-popup.service';
import { PieceService } from './piece.service';

@Component({
    selector: 'jhi-piece-delete-dialog',
    templateUrl: './piece-delete-dialog.component.html'
})
export class PieceDeleteDialogComponent {

    piece: Piece;

    constructor(
        private pieceService: PieceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pieceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pieceListModification',
                content: 'Deleted an piece'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-piece-delete-popup',
    template: ''
})
export class PieceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private piecePopupService: PiecePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.piecePopupService
                .open(PieceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
