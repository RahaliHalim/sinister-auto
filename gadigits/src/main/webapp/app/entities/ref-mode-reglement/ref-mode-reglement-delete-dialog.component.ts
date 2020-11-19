import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefModeReglement } from './ref-mode-reglement.model';
import { RefModeReglementPopupService } from './ref-mode-reglement-popup.service';
import { RefModeReglementService } from './ref-mode-reglement.service';

@Component({
    selector: 'jhi-ref-mode-reglement-delete-dialog',
    templateUrl: './ref-mode-reglement-delete-dialog.component.html'
})
export class RefModeReglementDeleteDialogComponent {

    refModeReglement: RefModeReglement;

    constructor(
        private refModeReglementService: RefModeReglementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refModeReglementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refModeReglementListModification',
                content: 'Deleted an refModeReglement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-mode-reglement-delete-popup',
    template: ''
})
export class RefModeReglementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refModeReglementPopupService: RefModeReglementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refModeReglementPopupService
                .open(RefModeReglementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
