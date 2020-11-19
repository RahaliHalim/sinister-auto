import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Camion } from './camion.model';
import { CamionPopupService } from './camion-popup.service';
import { CamionService } from './camion.service';

@Component({
    selector: 'jhi-camion-delete-dialog',
    templateUrl: './camion-delete-dialog.component.html'
})
export class CamionDeleteDialogComponent {

    camion: Camion;

    constructor(
        private camionService: CamionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.camionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'camionListModification',
                content: 'Deleted an camion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-camion-delete-popup',
    template: ''
})
export class CamionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private camionPopupService: CamionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.camionPopupService
                .open(CamionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
