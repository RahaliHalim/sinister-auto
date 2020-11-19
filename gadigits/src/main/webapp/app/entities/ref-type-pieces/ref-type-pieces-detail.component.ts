import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefTypePieces } from './ref-type-pieces.model';
import { RefTypePiecesService } from './ref-type-pieces.service';

@Component({
    selector: 'jhi-ref-type-pieces-detail',
    templateUrl: './ref-type-pieces-detail.component.html'
})
export class RefTypePiecesDetailComponent implements OnInit, OnDestroy {

    refTypePieces: RefTypePieces;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refTypePiecesService: RefTypePiecesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefTypePieces();
    }

    load(id) {
        this.refTypePiecesService.find(id).subscribe((refTypePieces) => {
            this.refTypePieces = refTypePieces;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefTypePieces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refTypePiecesListModification',
            (response) => this.load(this.refTypePieces.id)
        );
    }
}
