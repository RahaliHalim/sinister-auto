import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Policy } from './policy.model';
import { PolicyService } from './policy.service';

@Component({
    selector: 'jhi-policy-detail',
    templateUrl: './policy-detail.component.html'
})
export class PolicyDetailComponent implements OnInit, OnDestroy {

    policy: Policy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private policyService: PolicyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolicies();
    }

    load(id) {
        this.policyService.find(id).subscribe((policy) => {
            this.policy = policy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyListModification',
            (response) => this.load(this.policy.id)
        );
    }
}
