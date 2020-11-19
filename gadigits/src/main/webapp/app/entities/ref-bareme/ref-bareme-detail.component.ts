import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefBareme } from './ref-bareme.model';
import { RefBaremeService } from './ref-bareme.service';

@Component({
    selector: 'jhi-ref-bareme-detail',
    templateUrl: './ref-bareme-detail.component.html'
})
export class RefBaremeDetailComponent implements OnInit, OnDestroy {

    refBareme: RefBareme;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refBaremeService: RefBaremeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefBaremes();
    }

    load(id) {
        this.refBaremeService.find(id).subscribe((refBareme) => {
            this.refBareme = refBareme;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefBaremes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refBaremeListModification',
            (response) => this.load(this.refBareme.id)
        );
    }
}
