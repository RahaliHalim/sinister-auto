import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Agency } from './agency.model';
import { AgencyPopupService } from './agency-popup.service';
import { AgencyService } from './agency.service';

@Component({
    selector: 'jhi-agency-delete-dialog',
    templateUrl: './agency-delete-dialog.component.html'
})
export class AgencyDeleteDialogComponent {

    agency: Agency;

    constructor(
        private agencyService: AgencyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'agencyListModification',
                content: 'Deleted an agency'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('auxiliumApp.agency.deleted',null,null);
    }
}

@Component({
    selector: 'jhi-agency-delete-popup',
    template: ''
})
export class AgencyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencyPopupService: AgencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.agencyPopupService
                .open(AgencyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
