import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Lien } from './lien.model';
import { LienService } from './lien.service';

@Component({
    selector: 'jhi-lien-detail',
    templateUrl: './lien-detail.component.html'
})
export class LienDetailComponent implements OnInit, OnDestroy {

    lien: Lien;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lienService: LienService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLiens();
    }

    load(id) {
        this.lienService.find(id).subscribe((lien) => {
            this.lien = lien;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLiens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lienListModification',
            (response) => this.load(this.lien.id)
        );
    }
}
