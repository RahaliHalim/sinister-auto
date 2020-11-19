import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ServiceAssurance } from './service-assurance.model';
import { ServiceAssurancePopupService } from './service-assurance-popup.service';
import { ServiceAssuranceService } from './service-assurance.service';

@Component({
    selector: 'jhi-service-assurance-delete-dialog',
    templateUrl: './service-assurance-delete-dialog.component.html'
})
export class ServiceAssuranceDeleteDialogComponent {

    serviceAssurance: ServiceAssurance;

    constructor(
        private serviceAssuranceService: ServiceAssuranceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.serviceAssuranceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'serviceAssuranceListModification',
                content: 'Deleted an serviceAssurance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-service-assurance-delete-popup',
    template: ''
})
export class ServiceAssuranceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceAssurancePopupService: ServiceAssurancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.serviceAssurancePopupService
                .open(ServiceAssuranceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
