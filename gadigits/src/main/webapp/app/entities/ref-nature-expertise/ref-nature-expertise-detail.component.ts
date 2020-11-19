import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefNatureExpertise } from './ref-nature-expertise.model';
import { RefNatureExpertiseService } from './ref-nature-expertise.service';

@Component({
    selector: 'jhi-ref-nature-expertise-detail',
    templateUrl: './ref-nature-expertise-detail.component.html'
})
export class RefNatureExpertiseDetailComponent implements OnInit, OnDestroy {

    refNatureExpertise: RefNatureExpertise;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refNatureExpertiseService: RefNatureExpertiseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefNatureExpertises();
    }

    load(id) {
        this.refNatureExpertiseService.find(id).subscribe((refNatureExpertise) => {
            this.refNatureExpertise = refNatureExpertise;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefNatureExpertises() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refNatureExpertiseListModification',
            (response) => this.load(this.refNatureExpertise.id)
        );
    }
}
