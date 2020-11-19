import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileAccess } from './profile-access.model';
import { ProfileAccessPopupService } from './profile-access-popup.service';
import { ProfileAccessService } from './profile-access.service';

@Component({
    selector: 'jhi-profile-access-delete-dialog',
    templateUrl: './profile-access-delete-dialog.component.html'
})
export class ProfileAccessDeleteDialogComponent {

    profileAccess: ProfileAccess;

    constructor(
        private profileAccessService: ProfileAccessService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profileAccessService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'profileAccessListModification',
                content: 'Deleted an profileAccess'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profile-access-delete-popup',
    template: ''
})
export class ProfileAccessDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileAccessPopupService: ProfileAccessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.profileAccessPopupService
                .open(ProfileAccessDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
