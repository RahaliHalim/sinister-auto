import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefFractionnement } from './ref-fractionnement.model';
import { RefFractionnementService } from './ref-fractionnement.service';

@Component({
    selector: 'jhi-ref-fractionnement-detail',
    templateUrl: './ref-fractionnement-detail.component.html'
})
export class RefFractionnementDetailComponent implements OnInit, OnDestroy {

    refFractionnement: RefFractionnement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refFractionnementService: RefFractionnementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefFractionnements();
    }

    load(id) {
        this.refFractionnementService.find(id).subscribe((refFractionnement) => {
            this.refFractionnement = refFractionnement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefFractionnements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refFractionnementListModification',
            (response) => this.load(this.refFractionnement.id)
        );
    }
}
