import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefTypePieces } from './ref-type-pieces.model';
import { RefTypePiecesPopupService } from './ref-type-pieces-popup.service';
import { RefTypePiecesService } from './ref-type-pieces.service';

@Component({
    selector: 'jhi-ref-type-pieces-delete-dialog',
    templateUrl: './ref-type-pieces-delete-dialog.component.html'
})
export class RefTypePiecesDeleteDialogComponent {

    refTypePieces: RefTypePieces;

    constructor(
        private refTypePiecesService: RefTypePiecesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refTypePiecesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refTypePiecesListModification',
                content: 'Deleted an refTypePieces'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-type-pieces-delete-popup',
    template: ''
})
export class RefTypePiecesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypePiecesPopupService: RefTypePiecesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refTypePiecesPopupService
                .open(RefTypePiecesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
