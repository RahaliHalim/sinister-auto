import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefFractionnement } from './ref-fractionnement.model';
import { RefFractionnementPopupService } from './ref-fractionnement-popup.service';
import { RefFractionnementService } from './ref-fractionnement.service';

@Component({
    selector: 'jhi-ref-fractionnement-delete-dialog',
    templateUrl: './ref-fractionnement-delete-dialog.component.html'
})
export class RefFractionnementDeleteDialogComponent {

    refFractionnement: RefFractionnement;

    constructor(
        private refFractionnementService: RefFractionnementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refFractionnementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refFractionnementListModification',
                content: 'Deleted an refFractionnement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-fractionnement-delete-popup',
    template: ''
})
export class RefFractionnementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refFractionnementPopupService: RefFractionnementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refFractionnementPopupService
                .open(RefFractionnementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
