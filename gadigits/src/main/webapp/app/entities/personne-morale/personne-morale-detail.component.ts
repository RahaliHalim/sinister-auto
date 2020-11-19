import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PersonneMorale } from './personne-morale.model';
import { PersonneMoraleService } from './personne-morale.service';

@Component({
    selector: 'jhi-personne-morale-detail',
    templateUrl: './personne-morale-detail.component.html'
})
export class PersonneMoraleDetailComponent implements OnInit, OnDestroy {

    personneMorale: PersonneMorale;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private personneMoraleService: PersonneMoraleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonneMorales();
    }

    load(id) {
        this.personneMoraleService.find(id).subscribe((personneMorale) => {
            this.personneMorale = personneMorale;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonneMorales() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personneMoraleListModification',
            (response) => this.load(this.personneMorale.id)
        );
    }
}
