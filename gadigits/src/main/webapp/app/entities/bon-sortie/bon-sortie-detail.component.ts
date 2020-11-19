import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BonSortie } from './bon-sortie.model';
import { BonSortieService } from './bon-sortie.service';

@Component({
    selector: 'jhi-bon-sortie-detail',
    templateUrl: './bon-sortie-detail.component.html'
})
export class BonSortieDetailComponent implements OnInit, OnDestroy {

    bonSortie: BonSortie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bonSortieService: BonSortieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBonSorties();
    }

    load(id) {
        this.bonSortieService.find(id).subscribe((bonSortie) => {
            this.bonSortie = bonSortie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBonSorties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bonSortieListModification',
            (response) => this.load(this.bonSortie.id)
        );
    }
}
