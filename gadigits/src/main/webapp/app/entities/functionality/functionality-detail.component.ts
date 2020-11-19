import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Functionality } from './functionality.model';
import { FunctionalityService } from './functionality.service';

@Component({
    selector: 'jhi-functionality-detail',
    templateUrl: './functionality-detail.component.html'
})
export class FunctionalityDetailComponent implements OnInit, OnDestroy {

    functionality: Functionality;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private functionalityService: FunctionalityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFunctionalities();
    }

    load(id) {
        this.functionalityService.find(id).subscribe((functionality) => {
            this.functionality = functionality;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFunctionalities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'functionalityListModification',
            (response) => this.load(this.functionality.id)
        );
    }
}
