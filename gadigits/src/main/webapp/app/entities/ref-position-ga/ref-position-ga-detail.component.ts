import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefPositionGa } from './ref-position-ga.model';
import { RefPositionGaService } from './ref-position-ga.service';

@Component({
    selector: 'jhi-ref-position-ga-detail',
    templateUrl: './ref-position-ga-detail.component.html'
})
export class RefPositionGaDetailComponent implements OnInit, OnDestroy {

    refPositionGa: RefPositionGa;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refPositionGaService: RefPositionGaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefPositionGas();
    }

    load(id) {
        this.refPositionGaService.find(id).subscribe((refPositionGa) => {
            this.refPositionGa = refPositionGa;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefPositionGas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refPositionGaListModification',
            (response) => this.load(this.refPositionGa.id)
        );
    }
}
