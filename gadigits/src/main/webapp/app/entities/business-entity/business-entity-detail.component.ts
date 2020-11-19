import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BusinessEntity } from './business-entity.model';
import { BusinessEntityService } from './business-entity.service';

@Component({
    selector: 'jhi-business-entity-detail',
    templateUrl: './business-entity-detail.component.html'
})
export class BusinessEntityDetailComponent implements OnInit, OnDestroy {

    businessEntity: BusinessEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private businessEntityService: BusinessEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBusinessEntities();
    }

    load(id) {
        this.businessEntityService.find(id).subscribe((businessEntity) => {
            this.businessEntity = businessEntity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBusinessEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'businessEntityListModification',
            (response) => this.load(this.businessEntity.id)
        );
    }
}
