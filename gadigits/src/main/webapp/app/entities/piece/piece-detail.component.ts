import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Piece } from './piece.model';
import { PieceService } from './piece.service';

@Component({
    selector: 'jhi-piece-detail',
    templateUrl: './piece-detail.component.html'
})
export class PieceDetailComponent implements OnInit, OnDestroy {

    piece: Piece;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pieceService: PieceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPieces();
    }

    load(id) {
        this.pieceService.find(id).subscribe((piece) => {
            this.piece = piece;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPieces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pieceListModification',
            (response) => this.load(this.piece.id)
        );
    }
}
