import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PolicyStatus } from './policy-status.model';
import { PolicyStatusPopupService } from './policy-status-popup.service';
import { PolicyStatusService } from './policy-status.service';

@Component({
    selector: 'jhi-policy-status-delete-dialog',
    templateUrl: './policy-status-delete-dialog.component.html'
})
export class PolicyStatusDeleteDialogComponent {

    policyStatus: PolicyStatus;

    constructor(
        private policyStatusService: PolicyStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.policyStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'policyStatusListModification',
                content: 'Deleted an policyStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-policy-status-delete-popup',
    template: ''
})
export class PolicyStatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyStatusPopupService: PolicyStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.policyStatusPopupService
                .open(PolicyStatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
