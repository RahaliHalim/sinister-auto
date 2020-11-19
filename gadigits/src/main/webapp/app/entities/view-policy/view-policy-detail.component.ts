import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ViewPolicy } from './view-policy.model';
import { ViewPolicyService } from './view-policy.service';

@Component({
    selector: 'jhi-view-policy-detail',
    templateUrl: './view-policy-detail.component.html'
})
export class ViewPolicyDetailComponent implements OnInit, OnDestroy {

    viewPolicy: ViewPolicy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private viewPolicyService: ViewPolicyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInViewPolicies();
    }

    load(id) {
        this.viewPolicyService.find(id).subscribe((viewPolicy) => {
            this.viewPolicy = viewPolicy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInViewPolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'viewPolicyListModification',
            (response) => this.load(this.viewPolicy.id)
        );
    }
}
