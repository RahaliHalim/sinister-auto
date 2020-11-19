import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AmendmentType } from './amendment-type.model';
import { AmendmentTypePopupService } from './amendment-type-popup.service';
import { AmendmentTypeService } from './amendment-type.service';

@Component({
    selector: 'jhi-amendment-type-delete-dialog',
    templateUrl: './amendment-type-delete-dialog.component.html'
})
export class AmendmentTypeDeleteDialogComponent {

    amendmentType: AmendmentType;

    constructor(
        private amendmentTypeService: AmendmentTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.amendmentTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'amendmentTypeListModification',
                content: 'Deleted an amendmentType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-amendment-type-delete-popup',
    template: ''
})
export class AmendmentTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amendmentTypePopupService: AmendmentTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.amendmentTypePopupService
                .open(AmendmentTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
