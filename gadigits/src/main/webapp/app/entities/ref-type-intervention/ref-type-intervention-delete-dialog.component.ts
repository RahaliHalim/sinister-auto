import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefTypeIntervention } from './ref-type-intervention.model';
import { RefTypeInterventionPopupService } from './ref-type-intervention-popup.service';
import { RefTypeInterventionService } from './ref-type-intervention.service';

@Component({
    selector: 'jhi-ref-type-intervention-delete-dialog',
    templateUrl: './ref-type-intervention-delete-dialog.component.html'
})
export class RefTypeInterventionDeleteDialogComponent {

    refTypeIntervention: RefTypeIntervention;

    constructor(
        private refTypeInterventionService: RefTypeInterventionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refTypeInterventionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refTypeInterventionListModification',
                content: 'Deleted an refTypeIntervention'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-type-intervention-delete-popup',
    template: ''
})
export class RefTypeInterventionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTypeInterventionPopupService: RefTypeInterventionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refTypeInterventionPopupService
                .open(RefTypeInterventionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
