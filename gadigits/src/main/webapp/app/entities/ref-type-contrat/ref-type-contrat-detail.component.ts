import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefTypeContrat } from './ref-type-contrat.model';
import { RefTypeContratService } from './ref-type-contrat.service';

@Component({
    selector: 'jhi-ref-type-contrat-detail',
    templateUrl: './ref-type-contrat-detail.component.html'
})
export class RefTypeContratDetailComponent implements OnInit, OnDestroy {

    refTypeContrat: RefTypeContrat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refTypeContratService: RefTypeContratService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefTypeContrats();
    }

    load(id) {
        this.refTypeContratService.find(id).subscribe((refTypeContrat) => {
            this.refTypeContrat = refTypeContrat;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefTypeContrats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refTypeContratListModification',
            (response) => this.load(this.refTypeContrat.id)
        );
    }
}
