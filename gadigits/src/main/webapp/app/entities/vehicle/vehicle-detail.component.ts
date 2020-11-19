import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Vehicle } from './vehicle.model';
import { VehicleService } from './vehicle.service';

@Component({
    selector: 'jhi-vehicle-detail',
    templateUrl: './vehicle-detail.component.html'
})
export class VehicleDetailComponent implements OnInit, OnDestroy {

    vehicle: Vehicle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vehicleService: VehicleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVehicles();
    }

    load(id) {
        this.vehicleService.find(id).subscribe((vehicle) => {
            this.vehicle = vehicle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVehicles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vehicleListModification',
            (response) => this.load(this.vehicle.id)
        );
    }
}
