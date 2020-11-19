import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { UserAccess } from './user-access.model';
import { UserAccessService } from './user-access.service';

@Component({
    selector: 'jhi-user-access-detail',
    templateUrl: './user-access-detail.component.html'
})
export class UserAccessDetailComponent implements OnInit, OnDestroy {

    userAccess: UserAccess;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userAccessService: UserAccessService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserAccesses();
    }

    load(id) {
        this.userAccessService.find(id).subscribe((userAccess) => {
            this.userAccess = userAccess;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserAccesses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userAccessListModification',
            (response) => this.load(this.userAccess.id)
        );
    }
}
