import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserAccess } from './user-access.model';
import { UserAccessPopupService } from './user-access-popup.service';
import { UserAccessService } from './user-access.service';

@Component({
    selector: 'jhi-user-access-delete-dialog',
    templateUrl: './user-access-delete-dialog.component.html'
})
export class UserAccessDeleteDialogComponent {

    userAccess: UserAccess;

    constructor(
        private userAccessService: UserAccessService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAccessService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userAccessListModification',
                content: 'Deleted an userAccess'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-access-delete-popup',
    template: ''
})
export class UserAccessDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAccessPopupService: UserAccessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userAccessPopupService
                .open(UserAccessDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
