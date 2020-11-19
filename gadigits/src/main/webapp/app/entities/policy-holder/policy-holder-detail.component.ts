import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PolicyHolder } from './policy-holder.model';
import { PolicyHolderService } from './policy-holder.service';

@Component({
    selector: 'jhi-policy-holder-detail',
    templateUrl: './policy-holder-detail.component.html'
})
export class PolicyHolderDetailComponent implements OnInit, OnDestroy {

    policyHolder: PolicyHolder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private policyHolderService: PolicyHolderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolicyHolders();
    }

    load(id) {
        this.policyHolderService.find(id).subscribe((policyHolder) => {
            this.policyHolder = policyHolder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolicyHolders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyHolderListModification',
            (response) => this.load(this.policyHolder.id)
        );
    }
}
