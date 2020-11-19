import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefModeReglement } from './ref-mode-reglement.model';
import { RefModeReglementService } from './ref-mode-reglement.service';

@Component({
    selector: 'jhi-ref-mode-reglement-detail',
    templateUrl: './ref-mode-reglement-detail.component.html'
})
export class RefModeReglementDetailComponent implements OnInit, OnDestroy {

    refModeReglement: RefModeReglement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refModeReglementService: RefModeReglementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefModeReglements();
    }

    load(id) {
        this.refModeReglementService.find(id).subscribe((refModeReglement) => {
            this.refModeReglement = refModeReglement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefModeReglements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refModeReglementListModification',
            (response) => this.load(this.refModeReglement.id)
        );
    }
}
