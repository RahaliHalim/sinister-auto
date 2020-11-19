import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Apec } from './apec.model';
import { ApecPopupService } from './apec-popup.service';
import { ApecService } from './apec.service';

@Component({
    selector: 'jhi-apec-delete-dialog',
    templateUrl: './apec-delete-dialog.component.html'
})
export class ApecDeleteDialogComponent {

    apec: Apec;

    constructor(
        private apecService: ApecService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apecService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'apecListModification',
                content: 'Deleted an apec'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-apec-delete-popup',
    template: ''
})
export class ApecDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apecPopupService: ApecPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.apecPopupService
                .open(ApecDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
