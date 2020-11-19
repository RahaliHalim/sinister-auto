import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Reason } from './reason.model';
import { ReasonService } from './reason.service';

@Component({
    selector: 'jhi-reason-detail',
    templateUrl: './reason-detail.component.html'
})
export class ReasonDetailComponent implements OnInit, OnDestroy {

    reason: Reason;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reasonService: ReasonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReasons();
    }

    load(id) {
        this.reasonService.find(id).subscribe((reason) => {
            this.reason = reason;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReasons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reasonListModification',
            (response) => this.load(this.reason.id)
        );
    }
}
