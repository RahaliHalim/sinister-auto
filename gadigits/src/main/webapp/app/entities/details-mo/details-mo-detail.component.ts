import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DetailsMo } from './details-mo.model';
import { DetailsMoService } from './details-mo.service';

@Component({
    selector: 'jhi-details-mo-detail',
    templateUrl: './details-mo-detail.component.html'
})
export class DetailsMoDetailComponent implements OnInit, OnDestroy {

    detailsMo: DetailsMo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detailsMoService: DetailsMoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetailsMos();
    }

    load(id) {
        this.detailsMoService.find(id).subscribe((detailsMo) => {
            this.detailsMo = detailsMo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetailsMos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detailsMoListModification',
            (response) => this.load(this.detailsMo.id)
        );
    }
}
