import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefAgence } from './ref-agence.model';
import { RefAgencePopupService } from './ref-agence-popup.service';
import { RefAgenceService } from './ref-agence.service';

@Component({
    selector: 'jhi-ref-agence-delete-dialog',
    templateUrl: './ref-agence-delete-dialog.component.html'
})
export class RefAgenceDeleteDialogComponent {

    refAgence: RefAgence;

    constructor(
        private refAgenceService: RefAgenceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refAgenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refAgenceListModification',
                content: 'Deleted an refAgence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-agence-delete-popup',
    template: ''
})
export class RefAgenceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refAgencePopupService: RefAgencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refAgencePopupService
                .open(RefAgenceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
