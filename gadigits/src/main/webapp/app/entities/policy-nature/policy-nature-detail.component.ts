import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PolicyNature } from './policy-nature.model';
import { PolicyNatureService } from './policy-nature.service';

@Component({
    selector: 'jhi-policy-nature-detail',
    templateUrl: './policy-nature-detail.component.html'
})
export class PolicyNatureDetailComponent implements OnInit, OnDestroy {

    policyNature: PolicyNature;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private policyNatureService: PolicyNatureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolicyNatures();
    }

    load(id) {
        this.policyNatureService.find(id).subscribe((policyNature) => {
            this.policyNature = policyNature;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolicyNatures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyNatureListModification',
            (response) => this.load(this.policyNature.id)
        );
    }
}
