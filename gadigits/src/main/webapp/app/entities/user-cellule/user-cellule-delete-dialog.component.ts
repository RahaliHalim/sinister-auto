import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserCellule } from './user-cellule.model';
import { UserCellulePopupService } from './user-cellule-popup.service';
import { UserCelluleService } from './user-cellule.service';

@Component({
    selector: 'jhi-user-cellule-delete-dialog',
    templateUrl: './user-cellule-delete-dialog.component.html'
})
export class UserCelluleDeleteDialogComponent {

    userCellule: UserCellule;

    constructor(
        private userCelluleService: UserCelluleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userCelluleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userCelluleListModification',
                content: 'Deleted an userCellule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-cellule-delete-popup',
    template: ''
})
export class UserCelluleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userCellulePopupService: UserCellulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userCellulePopupService
                .open(UserCelluleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
