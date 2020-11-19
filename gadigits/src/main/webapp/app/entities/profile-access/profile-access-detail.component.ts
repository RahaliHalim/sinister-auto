import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ProfileAccess } from './profile-access.model';
import { ProfileAccessService } from './profile-access.service';

@Component({
    selector: 'jhi-profile-access-detail',
    templateUrl: './profile-access-detail.component.html'
})
export class ProfileAccessDetailComponent implements OnInit, OnDestroy {

    profileAccess: ProfileAccess;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private profileAccessService: ProfileAccessService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfileAccesses();
    }

    load(id) {
        this.profileAccessService.find(id).subscribe((profileAccess) => {
            this.profileAccess = profileAccess;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfileAccesses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'profileAccessListModification',
            (response) => this.load(this.profileAccess.id)
        );
    }
}
