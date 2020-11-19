import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Loueur } from './loueur.model';
import { LoueurPopupService } from './loueur-popup.service';
import { LoueurService } from './loueur.service';

@Component({
    selector: 'jhi-loueur-delete-dialog',
    templateUrl: './loueur-delete-dialog.component.html'
})
export class LoueurDeleteDialogComponent {

    loueur: Loueur;

    constructor(
        private loueurService: LoueurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loueurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'loueurListModification',
                content: 'Deleted an loueur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-loueur-delete-popup',
    template: ''
})
export class LoueurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private loueurPopupService: LoueurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.loueurPopupService
                .open(LoueurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
