import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PointChoc } from './point-choc.model';
import { PointChocPopupService } from './point-choc-popup.service';
import { PointChocService } from './point-choc.service';

@Component({
    selector: 'jhi-point-choc-delete-dialog',
    templateUrl: './point-choc-delete-dialog.component.html'
})
export class PointChocDeleteDialogComponent {

    pointChoc: PointChoc;

    constructor(
        private pointChocService: PointChocService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pointChocService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pointChocListModification',
                content: 'Deleted an pointChoc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-point-choc-delete-popup',
    template: ''
})
export class PointChocDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pointChocPopupService: PointChocPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pointChocPopupService
                .open(PointChocDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
