import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Observation } from './observation.model';
import { ObservationPopupService } from './observation-popup.service';
import { ObservationService } from './observation.service';

@Component({
    selector: 'jhi-observation-delete-dialog',
    templateUrl: './observation-delete-dialog.component.html'
})
export class ObservationDeleteDialogComponent {

    observation: Observation;

    constructor(
        private observationService: ObservationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.observationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'observationListModification',
                content: 'Deleted an observation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-observation-delete-popup',
    template: ''
})
export class ObservationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private observationPopupService: ObservationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.observationPopupService
                .open(ObservationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
