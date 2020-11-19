import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SinisterPec } from './sinister-pec.model';
import { SinisterPecPopupService } from './sinister-pec-popup.service';
import { SinisterPecService } from './sinister-pec.service';

@Component({
    selector: 'jhi-sinister-pec-delete-dialog',
    templateUrl: './sinister-pec-delete-dialog.component.html'
})
export class SinisterPecDeleteDialogComponent {

    sinisterPec: SinisterPec;

    constructor(
        private sinisterPecService: SinisterPecService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sinisterPecService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sinisterPecListModification',
                content: 'Deleted an sinisterPec'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sinister-pec-delete-popup',
    template: ''
})
export class SinisterPecDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sinisterPecPopupService: SinisterPecPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sinisterPecPopupService
                .open(SinisterPecDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
