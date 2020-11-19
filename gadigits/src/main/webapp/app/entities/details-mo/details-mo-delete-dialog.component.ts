import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetailsMo } from './details-mo.model';
import { DetailsMoPopupService } from './details-mo-popup.service';
import { DetailsMoService } from './details-mo.service';
import { DevisService } from '../devis/devis.service';
import { GrilleService } from '../grille/grille.service';

@Component({
    selector: 'jhi-details-mo-delete-dialog',
    templateUrl: './details-mo-delete-dialog.component.html'
})
export class DetailsMoDeleteDialogComponent {

    detailsMo: DetailsMo;
    detailMo: any;
    devis: any;
    pec: any;
    reparateurGrille: any;
    grille: any;
    constructor(
        private detailsMoService: DetailsMoService,
        private devisService: DevisService,
        private grilleService: GrilleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
    confirmDelete(id: number) {
    }
}

@Component({
    selector: 'jhi-details-mo-delete-popup',
    template: ''
})
export class DetailsMoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailsMoPopupService: DetailsMoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.detailsMoPopupService
                .open(DetailsMoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
