import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PolicyNature } from './policy-nature.model';
import { PolicyNaturePopupService } from './policy-nature-popup.service';
import { PolicyNatureService } from './policy-nature.service';

@Component({
    selector: 'jhi-policy-nature-delete-dialog',
    templateUrl: './policy-nature-delete-dialog.component.html'
})
export class PolicyNatureDeleteDialogComponent {

    policyNature: PolicyNature;

    constructor(
        private policyNatureService: PolicyNatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.policyNatureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'policyNatureListModification',
                content: 'Deleted an policyNature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-policy-nature-delete-popup',
    template: ''
})
export class PolicyNatureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyNaturePopupService: PolicyNaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.policyNaturePopupService
                .open(PolicyNatureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
