import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PersonnePhysique } from './personne-physique.model';
import { PersonnePhysiqueService } from './personne-physique.service';

@Component({
    selector: 'jhi-personne-physique-detail',
    templateUrl: './personne-physique-detail.component.html'
})
export class PersonnePhysiqueDetailComponent implements OnInit, OnDestroy {

    personnePhysique: PersonnePhysique;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private personnePhysiqueService: PersonnePhysiqueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonnePhysiques();
    }

    load(id) {
        this.personnePhysiqueService.find(id).subscribe((personnePhysique) => {
            this.personnePhysique = personnePhysique;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonnePhysiques() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personnePhysiqueListModification',
            (response) => this.load(this.personnePhysique.id)
        );
    }
}
