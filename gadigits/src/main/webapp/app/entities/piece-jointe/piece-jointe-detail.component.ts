import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PieceJointe } from './piece-jointe.model';
import { PieceJointeService } from './piece-jointe.service';

@Component({
    selector: 'jhi-piece-jointe-detail',
    templateUrl: './piece-jointe-detail.component.html'
})
export class PieceJointeDetailComponent implements OnInit, OnDestroy {

    pieceJointe: PieceJointe;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pieceJointeService: PieceJointeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPieceJointes();
    }

    load(id) {
        this.pieceJointeService.find(id).subscribe((pieceJointe) => {
            this.pieceJointe = pieceJointe;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPieceJointes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pieceJointeListModification',
            (response) => this.load(this.pieceJointe.id)
        );
    }
}
