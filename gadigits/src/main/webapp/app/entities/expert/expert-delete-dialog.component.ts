import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Expert } from './expert.model';
import { ExpertPopupService } from './expert-popup.service';
import { ExpertService } from './expert.service';

@Component({
    selector: 'jhi-expert-delete-dialog',
    templateUrl: './expert-delete-dialog.component.html'
})
export class ExpertDeleteDialogComponent {

    expert: Expert;

    constructor(
        private expertService: ExpertService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.expertService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'expertListModification',
                content: 'Deleted an expert'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-expert-delete-popup',
    template: ''
})
export class ExpertDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private expertPopupService: ExpertPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.expertPopupService
                .open(ExpertDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
