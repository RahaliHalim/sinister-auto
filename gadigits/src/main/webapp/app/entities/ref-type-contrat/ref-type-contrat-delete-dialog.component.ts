import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefTypeContrat } from './ref-type-contrat.model';
import { RefTypeContratPopupService } from './ref-type-contrat-popup.service';
import { RefTypeContratService } from './ref-type-contrat.service';

@Component({
    selector: 'jhi-ref-type-contrat-delete-dialog',
    templateUrl: './ref-type-contrat-delete-dialog.component.html'
})
export class RefTypeContratDeleteDialogComponent {

    refTypeContrat: RefTypeContrat;

    constructor(
        private refTypeContratService: RefTypeContratService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refTypeContratService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refTypeContratListModification',
                content: 'Deleted an refTypeContrat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-type-contrat-delete-popup',
    template: ''
})
export class RefTypeContratDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypeContratPopupService: RefTypeContratPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refTypeContratPopupService
                .open(RefTypeContratDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
