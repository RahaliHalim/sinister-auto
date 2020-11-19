import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Reglement } from './reglement.model';
import { ReglementService } from './reglement.service';

@Component({
    selector: 'jhi-reglement-detail',
    templateUrl: './reglement-detail.component.html'
})
export class ReglementDetailComponent implements OnInit, OnDestroy {

    reglement: Reglement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reglementService: ReglementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReglements();
    }

    load(id) {
        this.reglementService.find(id).subscribe((reglement) => {
            this.reglement = reglement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReglements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reglementListModification',
            (response) => this.load(this.reglement.id)
        );
    }
}
