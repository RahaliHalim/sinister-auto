import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefTypeIntervention } from './ref-type-intervention.model';
import { RefTypeInterventionService } from './ref-type-intervention.service';

@Component({
    selector: 'jhi-ref-type-intervention-detail',
    templateUrl: './ref-type-intervention-detail.component.html'
})
export class RefTypeInterventionDetailComponent implements OnInit, OnDestroy {

    refTypeIntervention: RefTypeIntervention;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refTypeInterventionService: RefTypeInterventionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefTypeInterventions();
    }

    load(id) {
        this.refTypeInterventionService.find(id).subscribe((refTypeIntervention) => {
            this.refTypeIntervention = refTypeIntervention;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefTypeInterventions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refTypeInterventionListModification',
            (response) => this.load(this.refTypeIntervention.id)
        );
    }
}
