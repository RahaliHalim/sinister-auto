import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';

@Component({
    selector: 'jhi-sinister-pec-detail',
    templateUrl: './sinister-pec-detail.component.html'
})
export class SinisterPecDetailComponent implements OnInit, OnDestroy {

    sinisterPec: SinisterPec;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sinisterPecService: SinisterPecService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSinisterPecs();
    }

    load(id) {
        this.sinisterPecService.find(id).subscribe((sinisterPec) => {
            this.sinisterPec = sinisterPec;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sinisterPecListModification',
            (response) => this.load(this.sinisterPec.id)
        );
    }
}
