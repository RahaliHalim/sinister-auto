import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PolicyHolder } from './policy-holder.model';
import { PolicyHolderPopupService } from './policy-holder-popup.service';
import { PolicyHolderService } from './policy-holder.service';

@Component({
    selector: 'jhi-policy-holder-delete-dialog',
    templateUrl: './policy-holder-delete-dialog.component.html'
})
export class PolicyHolderDeleteDialogComponent {

    policyHolder: PolicyHolder;

    constructor(
        private policyHolderService: PolicyHolderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.policyHolderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'policyHolderListModification',
                content: 'Deleted an policyHolder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-policy-holder-delete-popup',
    template: ''
})
export class PolicyHolderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyHolderPopupService: PolicyHolderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.policyHolderPopupService
                .open(PolicyHolderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
