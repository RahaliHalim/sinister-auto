import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefEtatBs } from './ref-etat-bs.model';
import { RefEtatBsService } from './ref-etat-bs.service';

@Component({
    selector: 'jhi-ref-etat-bs-detail',
    templateUrl: './ref-etat-bs-detail.component.html'
})
export class RefEtatBsDetailComponent implements OnInit, OnDestroy {

    refEtatBs: RefEtatBs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refEtatBsService: RefEtatBsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefEtatBs();
    }

    load(id) {
        this.refEtatBsService.find(id).subscribe((refEtatBs) => {
            this.refEtatBs = refEtatBs;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefEtatBs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refEtatBsListModification',
            (response) => this.load(this.refEtatBs.id)
        );
    }
}
