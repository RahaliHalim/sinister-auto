import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefTypePj } from './ref-type-pj.model';
import { RefTypePjPopupService } from './ref-type-pj-popup.service';
import { RefTypePjService } from './ref-type-pj.service';

@Component({
    selector: 'jhi-ref-type-pj-delete-dialog',
    templateUrl: './ref-type-pj-delete-dialog.component.html'
})
export class RefTypePjDeleteDialogComponent {

    refTypePj: RefTypePj;

    constructor(
        private refTypePjService: RefTypePjService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refTypePjService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refTypePjListModification',
                content: 'Deleted an refTypePj'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-type-pj-delete-popup',
    template: ''
})
export class RefTypePjDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypePjPopupService: RefTypePjPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refTypePjPopupService
                .open(RefTypePjDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
