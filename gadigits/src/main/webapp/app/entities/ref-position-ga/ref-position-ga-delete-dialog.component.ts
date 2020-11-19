import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefPositionGa } from './ref-position-ga.model';
import { RefPositionGaPopupService } from './ref-position-ga-popup.service';
import { RefPositionGaService } from './ref-position-ga.service';

@Component({
    selector: 'jhi-ref-position-ga-delete-dialog',
    templateUrl: './ref-position-ga-delete-dialog.component.html'
})
export class RefPositionGaDeleteDialogComponent {

    refPositionGa: RefPositionGa;

    constructor(
        private refPositionGaService: RefPositionGaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refPositionGaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refPositionGaListModification',
                content: 'Deleted an refPositionGa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-position-ga-delete-popup',
    template: ''
})
export class RefPositionGaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refPositionGaPopupService: RefPositionGaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refPositionGaPopupService
                .open(RefPositionGaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
