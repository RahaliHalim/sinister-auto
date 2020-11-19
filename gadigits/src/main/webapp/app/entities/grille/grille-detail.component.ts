import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Grille } from './grille.model';
import { GrilleService } from './grille.service';

@Component({
    selector: 'jhi-grille-detail',
    templateUrl: './grille-detail.component.html'
})
export class GrilleDetailComponent implements OnInit, OnDestroy {

    grille: Grille;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private grilleService: GrilleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGrilles();
    }

    load(id) {
        this.grilleService.find(id).subscribe((grille) => {
            this.grille = grille;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGrilles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'grilleListModification',
            (response) => this.load(this.grille.id)
        );
    }
}
