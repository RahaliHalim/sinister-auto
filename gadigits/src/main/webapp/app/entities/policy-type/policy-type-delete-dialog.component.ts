import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PolicyType } from './policy-type.model';
import { PolicyTypePopupService } from './policy-type-popup.service';
import { PolicyTypeService } from './policy-type.service';

@Component({
    selector: 'jhi-policy-type-delete-dialog',
    templateUrl: './policy-type-delete-dialog.component.html'
})
export class PolicyTypeDeleteDialogComponent {

    policyType: PolicyType;

    constructor(
        private policyTypeService: PolicyTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.policyTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'policyTypeListModification',
                content: 'Deleted an policyType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-policy-type-delete-popup',
    template: ''
})
export class PolicyTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyTypePopupService: PolicyTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.policyTypePopupService
                .open(PolicyTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
