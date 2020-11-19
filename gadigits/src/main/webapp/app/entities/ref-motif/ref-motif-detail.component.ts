import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefMotif } from './ref-motif.model';
import { RefMotifService } from './ref-motif.service';

@Component({
    selector: 'jhi-ref-motif-detail',
    templateUrl: './ref-motif-detail.component.html'
})
export class RefMotifDetailComponent implements OnInit, OnDestroy {

    refMotif: RefMotif;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refMotifService: RefMotifService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefMotifs();
    }

    load(id) {
        this.refMotifService.find(id).subscribe((refMotif) => {
            this.refMotif = refMotif;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefMotifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refMotifListModification',
            (response) => this.load(this.refMotif.id)
        );
    }
}
