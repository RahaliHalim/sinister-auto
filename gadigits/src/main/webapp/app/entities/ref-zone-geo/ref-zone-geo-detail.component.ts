import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefZoneGeo } from './ref-zone-geo.model';
import { RefZoneGeoService } from './ref-zone-geo.service';

@Component({
    selector: 'jhi-ref-zone-geo-detail',
    templateUrl: './ref-zone-geo-detail.component.html'
})
export class RefZoneGeoDetailComponent implements OnInit, OnDestroy {

    refZoneGeo: RefZoneGeo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refZoneGeoService: RefZoneGeoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefZoneGeos();
    }

    load(id) {
        this.refZoneGeoService.find(id).subscribe((refZoneGeo) => {
            this.refZoneGeo = refZoneGeo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefZoneGeos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refZoneGeoListModification',
            (response) => this.load(this.refZoneGeo.id)
        );
    }
}
