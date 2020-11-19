import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PointChoc } from './point-choc.model';
import { PointChocService } from './point-choc.service';

@Component({
    selector: 'jhi-point-choc-detail',
    templateUrl: './point-choc-detail.component.html'
})
export class PointChocDetailComponent implements OnInit, OnDestroy {

    pointChoc: PointChoc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pointChocService: PointChocService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPointChocs();
    }

    load(id) {
        this.pointChocService.find(id).subscribe((pointChoc) => {
            this.pointChoc = pointChoc;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPointChocs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pointChocListModification',
            (response) => this.load(this.pointChoc.id)
        );
    }
}
