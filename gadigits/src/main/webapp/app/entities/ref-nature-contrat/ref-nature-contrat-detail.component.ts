import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefNatureContrat } from './ref-nature-contrat.model';
import { RefNatureContratService } from './ref-nature-contrat.service';

@Component({
    selector: 'jhi-ref-nature-contrat-detail',
    templateUrl: './ref-nature-contrat-detail.component.html'
})
export class RefNatureContratDetailComponent implements OnInit, OnDestroy {

    refNatureContrat: RefNatureContrat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refNatureContratService: RefNatureContratService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefNatureContrats();
    }

    load(id) {
        this.refNatureContratService.find(id).subscribe((refNatureContrat) => {
            this.refNatureContrat = refNatureContrat;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefNatureContrats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refNatureContratListModification',
            (response) => this.load(this.refNatureContrat.id)
        );
    }
}
