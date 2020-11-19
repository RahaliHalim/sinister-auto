import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Observation } from './observation.model';
import { ObservationService } from './observation.service';

@Component({
    selector: 'jhi-observation-detail',
    templateUrl: './observation-detail.component.html'
})
export class ObservationDetailComponent implements OnInit, OnDestroy {

    observation: Observation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private observationService: ObservationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInObservations();
    }

    load(id) {
        this.observationService.find(id).subscribe((observation) => {
            this.observation = observation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInObservations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'observationListModification',
            (response) => this.load(this.observation.id)
        );
    }
}
