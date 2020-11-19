import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ServiceAssurance } from './service-assurance.model';
import { ServiceAssuranceService } from './service-assurance.service';

@Component({
    selector: 'jhi-service-assurance-detail',
    templateUrl: './service-assurance-detail.component.html'
})
export class ServiceAssuranceDetailComponent implements OnInit, OnDestroy {

    serviceAssurance: ServiceAssurance;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private serviceAssuranceService: ServiceAssuranceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServiceAssurances();
    }

    load(id) {
        this.serviceAssuranceService.find(id).subscribe((serviceAssurance) => {
            this.serviceAssurance = serviceAssurance;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServiceAssurances() {
        this.eventSubscriber = this.eventManager.subscribe(
            'serviceAssuranceListModification',
            (response) => this.load(this.serviceAssurance.id)
        );
    }
}
