import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PolicyReceiptStatus } from './policy-receipt-status.model';
import { PolicyReceiptStatusService } from './policy-receipt-status.service';

@Component({
    selector: 'jhi-policy-receipt-status-detail',
    templateUrl: './policy-receipt-status-detail.component.html'
})
export class PolicyReceiptStatusDetailComponent implements OnInit, OnDestroy {

    policyReceiptStatus: PolicyReceiptStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private policyReceiptStatusService: PolicyReceiptStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolicyReceiptStatuses();
    }

    load(id) {
        this.policyReceiptStatusService.find(id).subscribe((policyReceiptStatus) => {
            this.policyReceiptStatus = policyReceiptStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolicyReceiptStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyReceiptStatusListModification',
            (response) => this.load(this.policyReceiptStatus.id)
        );
    }
}
