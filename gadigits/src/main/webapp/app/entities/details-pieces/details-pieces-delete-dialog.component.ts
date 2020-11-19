import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetailsPieces } from './details-pieces.model';
import { DetailsPiecesPopupService } from './details-pieces-popup.service';
import { DetailsPiecesService } from './details-pieces.service';
import { DevisService } from '../devis/devis.service';

@Component({
    selector: 'jhi-details-pieces-delete-dialog',
    templateUrl: './details-pieces-delete-dialog.component.html'
})
export class DetailsPiecesDeleteDialogComponent {

    detailsPieces: DetailsPieces;
    detailPiece: any;
    devis: any;

    constructor(
        private detailsPiecesService: DetailsPiecesService,
        private devisService: DevisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detailsPiecesService.find(id).subscribe((res) => {this.detailPiece = res;
            this.devisService.find(this.detailPiece.devisId).subscribe((resDevis) => {this.devis = resDevis;
            this.devis.totalTtc =  this.devis.totalTtc -
            ((this.detailPiece.prixUnit * this.detailPiece.quantite) + ((this.detailPiece.prixUnit * this.detailPiece.quantite) * (this.detailPiece.tva / 100)));
            this.devisService.update(this.devis).subscribe((resDevisUpdated) => this.devis = resDevisUpdated)
            this.detailsPiecesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'detailsPiecesListModification',
                content: 'Deleted an detailsPieces'
            });
            this.activeModal.dismiss(true);
        }); })})
    }
}

@Component({
    selector: 'jhi-details-pieces-delete-popup',
    template: ''
})
export class DetailsPiecesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailsPiecesPopupService: DetailsPiecesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.detailsPiecesPopupService
                .open(DetailsPiecesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
