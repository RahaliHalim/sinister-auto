import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SysActionUtilisateur } from './sys-action-utilisateur.model';
import { SysActionUtilisateurService } from './sys-action-utilisateur.service';

@Component({
    selector: 'jhi-sys-action-utilisateur-detail',
    templateUrl: './sys-action-utilisateur-detail.component.html'
})
export class SysActionUtilisateurDetailComponent implements OnInit, OnDestroy {

    sysActionUtilisateur: SysActionUtilisateur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSysActionUtilisateurs();
    }

    load(id) {
        this.sysActionUtilisateurService.find(id).subscribe((sysActionUtilisateur) => {
            this.sysActionUtilisateur = sysActionUtilisateur;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSysActionUtilisateurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sysActionUtilisateurListModification',
            (response) => this.load(this.sysActionUtilisateur.id)
        );
    }
}
