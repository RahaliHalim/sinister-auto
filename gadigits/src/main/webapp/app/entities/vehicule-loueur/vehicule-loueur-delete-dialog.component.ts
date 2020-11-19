import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VehiculeLoueur } from './vehicule-loueur.model';
import { VehiculeLoueurPopupService } from './vehicule-loueur-popup.service';
import { VehiculeLoueurService } from './vehicule-loueur.service';

@Component({
    selector: 'jhi-vehicule-loueur-delete-dialog',
    templateUrl: './vehicule-loueur-delete-dialog.component.html'
})
export class VehiculeLoueurDeleteDialogComponent {

    vehiculeLoueur: VehiculeLoueur;

    constructor(
        private vehiculeLoueurService: VehiculeLoueurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vehiculeLoueurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vehiculeLoueurListModification',
                content: 'Deleted an vehiculeLoueur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vehicule-loueur-delete-popup',
    template: ''
})
export class VehiculeLoueurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehiculeLoueurPopupService: VehiculeLoueurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.vehiculeLoueurPopupService
                .open(VehiculeLoueurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
