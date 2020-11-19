import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DetailsPieces } from './details-pieces.model';
import { DetailsPiecesService } from './details-pieces.service';

@Component({
    selector: 'jhi-details-pieces-detail',
    templateUrl: './details-pieces-detail.component.html'
})
export class DetailsPiecesDetailComponent implements OnInit, OnDestroy {

    detailsPieces: DetailsPieces;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detailsPiecesService: DetailsPiecesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetailsPieces();
    }

    load(id) {
        this.detailsPiecesService.find(id).subscribe((detailsPieces) => {
            this.detailsPieces = detailsPieces;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetailsPieces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detailsPiecesListModification',
            (response) => this.load(this.detailsPieces.id)
        );
    }
}
