import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefPricing } from './ref-pricing.model';
import { RefPricingService } from './ref-pricing.service';

@Component({
    selector: 'jhi-ref-pricing-detail',
    templateUrl: './ref-pricing-detail.component.html'
})
export class RefPricingDetailComponent implements OnInit, OnDestroy {

    refPricing: RefPricing = new RefPricing();
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refPricingService: RefPricingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefPricings();
    }

    load(id) {
        this.refPricingService.find(id).subscribe((refPricing) => {
            this.refPricing = refPricing;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefPricings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refPricingListModification',
            (response) => this.load(this.refPricing.id)
        );
    }
}
