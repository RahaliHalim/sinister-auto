import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefMateriel } from './ref-materiel.model';
import { RefMaterielService } from './ref-materiel.service';

@Component({
    selector: 'jhi-ref-materiel-detail',
    templateUrl: './ref-materiel-detail.component.html'
})
export class RefMaterielDetailComponent implements OnInit, OnDestroy {

    refMateriel: RefMateriel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refMaterielService: RefMaterielService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefMateriels();
    }

    load(id) {
        this.refMaterielService.find(id).subscribe((refMateriel) => {
            this.refMateriel = refMateriel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefMateriels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refMaterielListModification',
            (response) => this.load(this.refMateriel.id)
        );
    }
}
