import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Reglement } from './reglement.model';
import { ReglementPopupService } from './reglement-popup.service';
import { ReglementService } from './reglement.service';

@Component({
    selector: 'jhi-reglement-delete-dialog',
    templateUrl: './reglement-delete-dialog.component.html'
})
export class ReglementDeleteDialogComponent {

    reglement: Reglement;

    constructor(
        private reglementService: ReglementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reglementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reglementListModification',
                content: 'Deleted an reglement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reglement-delete-popup',
    template: ''
})
export class ReglementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reglementPopupService: ReglementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reglementPopupService
                .open(ReglementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
