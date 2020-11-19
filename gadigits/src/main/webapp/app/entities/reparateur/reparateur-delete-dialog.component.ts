import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Reparateur } from './reparateur.model';
import { ReparateurPopupService } from './reparateur-popup.service';
import { ReparateurService } from './reparateur.service';

@Component({
    selector: 'jhi-reparateur-delete-dialog',
    templateUrl: './reparateur-delete-dialog.component.html'
})
export class ReparateurDeleteDialogComponent {

    reparateur: Reparateur;

    constructor(
        private reparateurService: ReparateurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reparateurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reparateurListModification',
                content: 'Deleted an reparateur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reparateur-delete-popup',
    template: ''
})
export class ReparateurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reparateurPopupService: ReparateurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reparateurPopupService
                .open(ReparateurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
