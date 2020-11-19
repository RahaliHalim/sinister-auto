import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefEtatDossier } from './ref-etat-dossier.model';
import { RefEtatDossierPopupService } from './ref-etat-dossier-popup.service';
import { RefEtatDossierService } from './ref-etat-dossier.service';

@Component({
    selector: 'jhi-ref-etat-dossier-delete-dialog',
    templateUrl: './ref-etat-dossier-delete-dialog.component.html'
})
export class RefEtatDossierDeleteDialogComponent {

    refEtatDossier: RefEtatDossier;

    constructor(
        private refEtatDossierService: RefEtatDossierService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refEtatDossierService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refEtatDossierListModification',
                content: 'Deleted an refEtatDossier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-etat-dossier-delete-popup',
    template: ''
})
export class RefEtatDossierDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refEtatDossierPopupService: RefEtatDossierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refEtatDossierPopupService
                .open(RefEtatDossierDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
