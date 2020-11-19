import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Partner } from './partner.model';
import { PartnerPopupService } from './partner-popup.service';
import { PartnerService } from './partner.service';

@Component({
    selector: 'jhi-partner-delete-dialog',
    templateUrl: './partner-delete-dialog.component.html'
})
export class PartnerDeleteDialogComponent {

    partner: Partner;

    constructor(
        private partnerService: PartnerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partnerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'partnerListModification',
                content: 'Deleted an partner'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('auxiliumApp.partner.deleted',null,null);
    }
}

@Component({
    selector: 'jhi-partner-delete-popup',
    template: ''
})
export class PartnerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partnerPopupService: PartnerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.partnerPopupService
                .open(PartnerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
