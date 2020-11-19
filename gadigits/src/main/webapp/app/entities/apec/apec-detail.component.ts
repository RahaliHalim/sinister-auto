import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Apec } from './apec.model';
import { ApecService } from './apec.service';

@Component({
    selector: 'jhi-apec-detail',
    templateUrl: './apec-detail.component.html'
})
export class ApecDetailComponent implements OnInit, OnDestroy {

    apec: Apec;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private apecService: ApecService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApecs();
    }

    load(id) {
        this.apecService.find(id).subscribe((apec) => {
            this.apec = apec;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApecs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'apecListModification',
            (response) => this.load(this.apec.id)
        );
    }
}
