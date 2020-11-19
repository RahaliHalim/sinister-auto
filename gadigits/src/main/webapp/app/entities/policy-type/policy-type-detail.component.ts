import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PolicyType } from './policy-type.model';
import { PolicyTypeService } from './policy-type.service';

@Component({
    selector: 'jhi-policy-type-detail',
    templateUrl: './policy-type-detail.component.html'
})
export class PolicyTypeDetailComponent implements OnInit, OnDestroy {

    policyType: PolicyType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private policyTypeService: PolicyTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolicyTypes();
    }

    load(id) {
        this.policyTypeService.find(id).subscribe((policyType) => {
            this.policyType = policyType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolicyTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'policyTypeListModification',
            (response) => this.load(this.policyType.id)
        );
    }
}
