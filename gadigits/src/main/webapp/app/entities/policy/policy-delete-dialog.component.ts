import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Policy } from './policy.model';
import { PolicyPopupService } from './policy-popup.service';
import { PolicyService } from './policy.service';

@Component({
    selector: 'jhi-policy-delete-dialog',
    templateUrl: './policy-delete-dialog.component.html'
})
export class PolicyDeleteDialogComponent {

    policy: Policy;

    constructor(
        private policyService: PolicyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.policyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'policyListModification',
                content: 'Deleted an policy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-policy-delete-popup',
    template: ''
})
export class PolicyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyPopupService: PolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.policyPopupService
                .open(PolicyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
