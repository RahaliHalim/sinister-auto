import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefAgence } from './ref-agence.model';
import { RefAgenceService } from './ref-agence.service';

@Component({
    selector: 'jhi-ref-agence-detail',
    templateUrl: './ref-agence-detail.component.html'
})
export class RefAgenceDetailComponent implements OnInit, OnDestroy {

    refAgence: RefAgence;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refAgenceService: RefAgenceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefAgences();
    }

    load(id) {
        this.refAgenceService.find(id).subscribe((refAgence) => {
            this.refAgence = refAgence;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefAgences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refAgenceListModification',
            (response) => this.load(this.refAgence.id)
        );
    }
}
