import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { AmendmentType } from './amendment-type.model';
import { AmendmentTypeService } from './amendment-type.service';

@Component({
    selector: 'jhi-amendment-type-detail',
    templateUrl: './amendment-type-detail.component.html'
})
export class AmendmentTypeDetailComponent implements OnInit, OnDestroy {

    amendmentType: AmendmentType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private amendmentTypeService: AmendmentTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAmendmentTypes();
    }

    load(id) {
        this.amendmentTypeService.find(id).subscribe((amendmentType) => {
            this.amendmentType = amendmentType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAmendmentTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'amendmentTypeListModification',
            (response) => this.load(this.amendmentType.id)
        );
    }
}
