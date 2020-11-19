import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Reason } from './reason.model';
import { ReasonPopupService } from './reason-popup.service';
import { ReasonService } from './reason.service';

@Component({
    selector: 'jhi-reason-delete-dialog',
    templateUrl: './reason-delete-dialog.component.html'
})
export class ReasonDeleteDialogComponent {

    reason: Reason;

    constructor(
        private reasonService: ReasonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reasonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reasonListModification',
                content: 'Deleted an reason'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reason-delete-popup',
    template: ''
})
export class ReasonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reasonPopupService: ReasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reasonPopupService
                .open(ReasonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
