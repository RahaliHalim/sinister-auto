import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Camion } from './camion.model';
import { CamionService } from './camion.service';

@Component({
    selector: 'jhi-camion-detail',
    templateUrl: './camion-detail.component.html'
})
export class CamionDetailComponent implements OnInit, OnDestroy {

    camion: Camion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private camionService: CamionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCamions();
    }

    load(id) {
        this.camionService.find(id).subscribe((camion) => {
            this.camion = camion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCamions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'camionListModification',
            (response) => this.load(this.camion.id)
        );
    }
}
