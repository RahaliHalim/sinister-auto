import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PolicyReceiptStatus } from './policy-receipt-status.model';
import { PolicyReceiptStatusPopupService } from './policy-receipt-status-popup.service';
import { PolicyReceiptStatusService } from './policy-receipt-status.service';

@Component({
    selector: 'jhi-policy-receipt-status-delete-dialog',
    templateUrl: './policy-receipt-status-delete-dialog.component.html'
})
export class PolicyReceiptStatusDeleteDialogComponent {

    policyReceiptStatus: PolicyReceiptStatus;

    constructor(
        private policyReceiptStatusService: PolicyReceiptStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.policyReceiptStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'policyReceiptStatusListModification',
                content: 'Deleted an policyReceiptStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-policy-receipt-status-delete-popup',
    template: ''
})
export class PolicyReceiptStatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyReceiptStatusPopupService: PolicyReceiptStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.policyReceiptStatusPopupService
                .open(PolicyReceiptStatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
