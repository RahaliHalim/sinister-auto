import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Lien } from './lien.model';
import { LienPopupService } from './lien-popup.service';
import { LienService } from './lien.service';

@Component({
    selector: 'jhi-lien-delete-dialog',
    templateUrl: './lien-delete-dialog.component.html'
})
export class LienDeleteDialogComponent {

    lien: Lien;

    constructor(
        private lienService: LienService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lienService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lienListModification',
                content: 'Deleted an lien'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lien-delete-popup',
    template: ''
})
export class LienDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lienPopupService: LienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lienPopupService
                .open(LienDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
