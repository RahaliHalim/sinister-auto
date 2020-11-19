import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefEtatBs } from './ref-etat-bs.model';
import { RefEtatBsPopupService } from './ref-etat-bs-popup.service';
import { RefEtatBsService } from './ref-etat-bs.service';

@Component({
    selector: 'jhi-ref-etat-bs-delete-dialog',
    templateUrl: './ref-etat-bs-delete-dialog.component.html'
})
export class RefEtatBsDeleteDialogComponent {

    refEtatBs: RefEtatBs;

    constructor(
        private refEtatBsService: RefEtatBsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refEtatBsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refEtatBsListModification',
                content: 'Deleted an refEtatBs'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-etat-bs-delete-popup',
    template: ''
})
export class RefEtatBsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refEtatBsPopupService: RefEtatBsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refEtatBsPopupService
                .open(RefEtatBsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
