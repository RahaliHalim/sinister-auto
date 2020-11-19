import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefTarif } from './ref-tarif.model';
import { RefTarifService } from './ref-tarif.service';

@Component({
    selector: 'jhi-ref-tarif-detail',
    templateUrl: './ref-tarif-detail.component.html'
})
export class RefTarifDetailComponent implements OnInit, OnDestroy {

    refTarif: RefTarif;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refTarifService: RefTarifService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefTarifs();
    }

    load(id) {
        this.refTarifService.find(id).subscribe((refTarif) => {
            this.refTarif = refTarif;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefTarifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tarifListModification',
            (response) => this.load(this.refTarif.id)
        );
    }
}
