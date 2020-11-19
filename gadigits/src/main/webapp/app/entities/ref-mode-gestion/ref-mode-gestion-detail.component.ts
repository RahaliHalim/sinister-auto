import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefModeGestion } from './ref-mode-gestion.model';
import { RefModeGestionService } from './ref-mode-gestion.service';

@Component({
    selector: 'jhi-ref-mode-gestion-detail',
    templateUrl: './ref-mode-gestion-detail.component.html'
})
export class RefModeGestionDetailComponent implements OnInit, OnDestroy {

    refModeGestion: RefModeGestion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refModeGestionService: RefModeGestionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefModeGestions();
    }

    load(id) {
        this.refModeGestionService.find(id).subscribe((refModeGestion) => {
            this.refModeGestion = refModeGestion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefModeGestions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refModeGestionListModification',
            (response) => this.load(this.refModeGestion.id)
        );
    }
}
