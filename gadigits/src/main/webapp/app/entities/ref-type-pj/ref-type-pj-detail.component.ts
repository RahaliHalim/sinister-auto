import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefTypePj } from './ref-type-pj.model';
import { RefTypePjService } from './ref-type-pj.service';

@Component({
    selector: 'jhi-ref-type-pj-detail',
    templateUrl: './ref-type-pj-detail.component.html'
})
export class RefTypePjDetailComponent implements OnInit, OnDestroy {

    refTypePj: RefTypePj;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refTypePjService: RefTypePjService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefTypePjs();
    }

    load(id) {
        this.refTypePjService.find(id).subscribe((refTypePj) => {
            this.refTypePj = refTypePj;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefTypePjs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refTypePjListModification',
            (response) => this.load(this.refTypePj.id)
        );
    }
}
