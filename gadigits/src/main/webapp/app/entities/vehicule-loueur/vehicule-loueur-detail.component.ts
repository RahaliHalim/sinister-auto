import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { VehiculeLoueur } from './vehicule-loueur.model';
import { VehiculeLoueurService } from './vehicule-loueur.service';

@Component({
    selector: 'jhi-vehicule-loueur-detail',
    templateUrl: './vehicule-loueur-detail.component.html'
})
export class VehiculeLoueurDetailComponent implements OnInit, OnDestroy {

    vehiculeLoueur: VehiculeLoueur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vehiculeLoueurService: VehiculeLoueurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVehiculeLoueurs();
    }

    load(id) {
        this.vehiculeLoueurService.find(id).subscribe((vehiculeLoueur) => {
            this.vehiculeLoueur = vehiculeLoueur;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVehiculeLoueurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vehiculeLoueurListModification',
            (response) => this.load(this.vehiculeLoueur.id)
        );
    }
}
