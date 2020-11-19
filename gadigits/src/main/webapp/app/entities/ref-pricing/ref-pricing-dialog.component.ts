import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefPricing } from './ref-pricing.model';
import { RefPricingService } from './ref-pricing.service';

@Component({
    selector: 'jhi-ref-pricing-dialog',
    templateUrl: './ref-pricing-dialog.component.html'
})
export class RefPricingDialogComponent implements OnInit {

    refPricing: RefPricing = new RefPricing();
    refPricingId: number;
    isSaving: boolean;

    constructor(
        private alertService: JhiAlertService,
        private refPricingService: RefPricingService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.route.params.subscribe(params => {
            if(params && params['id'] !== undefined) {
                this.refPricingId = +params['id']; // (+) converts string 'id' to a number    
                this.refPricingService.find(this.refPricingId).subscribe((response: RefPricing) => {
                    this.refPricing = response;
                });
            } 
         });
    }

    save() {
        this.isSaving = true;
        if (this.refPricing.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refPricingService.update(this.refPricing));
        } else {
            this.subscribeToSaveResponse(
                this.refPricingService.create(this.refPricing));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefPricing>) {
        result.subscribe((res: RefPricing) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefPricing) {
        this.eventManager.broadcast({ name: 'refPricingListModification', content: 'OK'});
        this.isSaving = false;
        this.router.navigate(['/ref-pricing']);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
