import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefTarif } from './ref-tarif.model';
import { RefTarifPopupService } from './ref-tarif-popup.service';
import { RefTarifService } from './ref-tarif.service';

@Component({
    selector: 'jhi-ref-tarif-delete-dialog',
    templateUrl: './ref-tarif-delete-dialog.component.html'
})
export class RefTarifDeleteDialogComponent {

    refTarif: RefTarif;

    constructor(
        private refTarifService: RefTarifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refTarifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: ' ref tarifListModification',
                content: 'Deleted an  ref Tarif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-tarif-delete-popup',
    template: ''
})
export class RefTarifDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTarifPopupService: RefTarifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refTarifPopupService
                .open(RefTarifDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
