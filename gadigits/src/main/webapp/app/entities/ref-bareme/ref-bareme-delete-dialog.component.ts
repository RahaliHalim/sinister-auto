import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefBareme } from './ref-bareme.model';
import { RefBaremePopupService } from './ref-bareme-popup.service';
import { RefBaremeService } from './ref-bareme.service';

@Component({
    selector: 'jhi-ref-bareme-delete-dialog',
    templateUrl: './ref-bareme-delete-dialog.component.html'
})
export class RefBaremeDeleteDialogComponent {

    refBareme: RefBareme;

    constructor(
        private refBaremeService: RefBaremeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refBaremeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refBaremeListModification',
                content: 'Deleted an refBareme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-bareme-delete-popup',
    template: ''
})
export class RefBaremeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refBaremePopupService: RefBaremePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refBaremePopupService
                .open(RefBaremeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
