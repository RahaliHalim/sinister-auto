import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PolicyStatus } from './policy-status.model';
import { PolicyStatusService } from './policy-status.service';

@Component({
    selector: 'jhi-policy-status-detail',
    templateUrl: './policy-status-detail.component.html'
})
export class PolicyStatusDetailComponent implements OnInit, OnDestroy {

    policyStatus: PolicyStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private policyStatusService: PolicyStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolicyStatuses();
    }

    load(id) {
        this.policyStatusService.find(id).subscribe((policyStatus) => {
            this.policyStatus = policyStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolicyStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyStatusListModification',
            (response) => this.load(this.policyStatus.id)
        );
    }
}
