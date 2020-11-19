import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ViewPolicy } from './view-policy.model';
import { ViewPolicyPopupService } from './view-policy-popup.service';
import { ViewPolicyService } from './view-policy.service';

@Component({
    selector: 'jhi-view-policy-delete-dialog',
    templateUrl: './view-policy-delete-dialog.component.html'
})
export class ViewPolicyDeleteDialogComponent {

    viewPolicy: ViewPolicy;

    constructor(
        private viewPolicyService: ViewPolicyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.viewPolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'viewPolicyListModification',
                content: 'Deleted an viewPolicy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-view-policy-delete-popup',
    template: ''
})
export class ViewPolicyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private viewPolicyPopupService: ViewPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.viewPolicyPopupService
                .open(ViewPolicyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
