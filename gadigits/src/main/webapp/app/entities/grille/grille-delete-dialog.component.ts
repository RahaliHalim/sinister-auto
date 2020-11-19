import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Grille } from './grille.model';
import { GrillePopupService } from './grille-popup.service';
import { GrilleService } from './grille.service';

@Component({
    selector: 'jhi-grille-delete-dialog',
    templateUrl: './grille-delete-dialog.component.html'
})
export class GrilleDeleteDialogComponent {

    grille: Grille;

    constructor(
        private grilleService: GrilleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grilleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'grilleListModification',
                content: 'Deleted an grille'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grille-delete-popup',
    template: ''
})
export class GrilleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grillePopupService: GrillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.grillePopupService
                .open(GrilleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
