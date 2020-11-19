import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { JhiEventManager } from 'ng-jhipster';

import { RefPricing } from './ref-pricing.model';
import { RefPricingService } from './ref-pricing.service';

@Component({
    selector: 'jhi-ref-pricing-delete-dialog',
    templateUrl: './ref-pricing-delete-dialog.component.html'
})
export class RefPricingDeleteDialogComponent {

    refPricing: RefPricing;

    constructor(
        private refPricingService: RefPricingService,
        private eventManager: JhiEventManager
    ) {
    }
}