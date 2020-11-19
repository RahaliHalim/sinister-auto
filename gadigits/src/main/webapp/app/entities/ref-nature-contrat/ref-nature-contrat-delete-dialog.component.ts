import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefNatureContrat } from './ref-nature-contrat.model';
import { RefNatureContratPopupService } from './ref-nature-contrat-popup.service';
import { RefNatureContratService } from './ref-nature-contrat.service';

@Component({
    selector: 'jhi-ref-nature-contrat-delete-dialog',
    templateUrl: './ref-nature-contrat-delete-dialog.component.html'
})
export class RefNatureContratDeleteDialogComponent {

    refNatureContrat: RefNatureContrat;

    constructor(
        private refNatureContratService: RefNatureContratService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refNatureContratService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refNatureContratListModification',
                content: 'Deleted an refNatureContrat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-nature-contrat-delete-popup',
    template: ''
})
export class RefNatureContratDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refNatureContratPopupService: RefNatureContratPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refNatureContratPopupService
                .open(RefNatureContratDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
