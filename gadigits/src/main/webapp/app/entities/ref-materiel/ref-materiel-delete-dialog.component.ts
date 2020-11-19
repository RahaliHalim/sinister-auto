import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefMateriel } from './ref-materiel.model';
import { RefMaterielPopupService } from './ref-materiel-popup.service';
import { RefMaterielService } from './ref-materiel.service';

@Component({
    selector: 'jhi-ref-materiel-delete-dialog',
    templateUrl: './ref-materiel-delete-dialog.component.html'
})
export class RefMaterielDeleteDialogComponent {

    refMateriel: RefMateriel;

    constructor(
        private refMaterielService: RefMaterielService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refMaterielService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refMaterielListModification',
                content: 'Deleted an refMateriel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-materiel-delete-popup',
    template: ''
})
export class RefMaterielDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refMaterielPopupService: RefMaterielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refMaterielPopupService
                .open(RefMaterielDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
