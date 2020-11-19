import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BonSortie } from './bon-sortie.model';
import { BonSortiePopupService } from './bon-sortie-popup.service';
import { BonSortieService } from './bon-sortie.service';

@Component({
    selector: 'jhi-bon-sortie-delete-dialog',
    templateUrl: './bon-sortie-delete-dialog.component.html'
})
export class BonSortieDeleteDialogComponent {

    bonSortie: BonSortie;

    constructor(
        private bonSortieService: BonSortieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bonSortieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bonSortieListModification',
                content: 'Deleted an bonSortie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bon-sortie-delete-popup',
    template: ''
})
export class BonSortieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bonSortiePopupService: BonSortiePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bonSortiePopupService
                .open(BonSortieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
