import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefModeGestion } from './ref-mode-gestion.model';
import { RefModeGestionPopupService } from './ref-mode-gestion-popup.service';
import { RefModeGestionService } from './ref-mode-gestion.service';

@Component({
    selector: 'jhi-ref-mode-gestion-delete-dialog',
    templateUrl: './ref-mode-gestion-delete-dialog.component.html'
})
export class RefModeGestionDeleteDialogComponent {

    refModeGestion: RefModeGestion;

    constructor(
        private refModeGestionService: RefModeGestionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refModeGestionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refModeGestionListModification',
                content: 'Deleted an refModeGestion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-mode-gestion-delete-popup',
    template: ''
})
export class RefModeGestionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refModeGestionPopupService: RefModeGestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refModeGestionPopupService
                .open(RefModeGestionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
