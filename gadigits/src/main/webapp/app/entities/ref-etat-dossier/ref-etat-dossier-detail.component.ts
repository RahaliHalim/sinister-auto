import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefEtatDossier } from './ref-etat-dossier.model';
import { RefEtatDossierService } from './ref-etat-dossier.service';

@Component({
    selector: 'jhi-ref-etat-dossier-detail',
    templateUrl: './ref-etat-dossier-detail.component.html'
})
export class RefEtatDossierDetailComponent implements OnInit, OnDestroy {

    refEtatDossier: RefEtatDossier;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refEtatDossierService: RefEtatDossierService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefEtatDossiers();
    }

    load(id) {
        this.refEtatDossierService.find(id).subscribe((refEtatDossier) => {
            this.refEtatDossier = refEtatDossier;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefEtatDossiers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refEtatDossierListModification',
            (response) => this.load(this.refEtatDossier.id)
        );
    }
}
