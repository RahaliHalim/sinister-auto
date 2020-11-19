import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BusinessEntity } from './business-entity.model';
import { BusinessEntityPopupService } from './business-entity-popup.service';
import { BusinessEntityService } from './business-entity.service';

@Component({
    selector: 'jhi-business-entity-delete-dialog',
    templateUrl: './business-entity-delete-dialog.component.html'
})
export class BusinessEntityDeleteDialogComponent {

    businessEntity: BusinessEntity;

    constructor(
        private businessEntityService: BusinessEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.businessEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'businessEntityListModification',
                content: 'Deleted an businessEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-business-entity-delete-popup',
    template: ''
})
export class BusinessEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessEntityPopupService: BusinessEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.businessEntityPopupService
                .open(BusinessEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
