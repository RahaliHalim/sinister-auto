import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefMotif } from './ref-motif.model';
import { RefMotifPopupService } from './ref-motif-popup.service';
import { RefMotifService } from './ref-motif.service';

@Component({
    selector: 'jhi-ref-motif-delete-dialog',
    templateUrl: './ref-motif-delete-dialog.component.html'
})
export class RefMotifDeleteDialogComponent {

    refMotif: RefMotif;

    constructor(
        private refMotifService: RefMotifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refMotifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refMotifListModification',
                content: 'Deleted an refMotif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-motif-delete-popup',
    template: ''
})
export class RefMotifDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refMotifPopupService: RefMotifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refMotifPopupService
                .open(RefMotifDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
