import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UserCellule } from './user-cellule.model';
import { UserCelluleService } from './user-cellule.service';

@Component({
    selector: 'jhi-user-cellule-detail',
    templateUrl: './user-cellule-detail.component.html'
})
export class UserCelluleDetailComponent implements OnInit, OnDestroy {

    userCellule: UserCellule;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userCelluleService: UserCelluleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserCellules();
    }

    load(id) {
        this.userCelluleService.find(id).subscribe((userCellule) => {
            this.userCellule = userCellule;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserCellules() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userCelluleListModification',
            (response) => this.load(this.userCellule.id)
        );
    }
}
