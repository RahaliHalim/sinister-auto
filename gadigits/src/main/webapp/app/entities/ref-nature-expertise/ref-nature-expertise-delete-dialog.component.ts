import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefNatureExpertise } from './ref-nature-expertise.model';
import { RefNatureExpertisePopupService } from './ref-nature-expertise-popup.service';
import { RefNatureExpertiseService } from './ref-nature-expertise.service';

@Component({
    selector: 'jhi-ref-nature-expertise-delete-dialog',
    templateUrl: './ref-nature-expertise-delete-dialog.component.html'
})
export class RefNatureExpertiseDeleteDialogComponent {

    refNatureExpertise: RefNatureExpertise;

    constructor(
        private refNatureExpertiseService: RefNatureExpertiseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refNatureExpertiseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refNatureExpertiseListModification',
                content: 'Deleted an refNatureExpertise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-nature-expertise-delete-popup',
    template: ''
})
export class RefNatureExpertiseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refNatureExpertisePopupService: RefNatureExpertisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refNatureExpertisePopupService
                .open(RefNatureExpertiseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
