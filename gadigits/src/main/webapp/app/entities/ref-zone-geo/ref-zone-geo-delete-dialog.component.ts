import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RefZoneGeo } from './ref-zone-geo.model';
import { RefZoneGeoPopupService } from './ref-zone-geo-popup.service';
import { RefZoneGeoService } from './ref-zone-geo.service';

@Component({
    selector: 'jhi-ref-zone-geo-delete-dialog',
    templateUrl: './ref-zone-geo-delete-dialog.component.html'
})
export class RefZoneGeoDeleteDialogComponent {

    refZoneGeo: RefZoneGeo;

    constructor(
        private refZoneGeoService: RefZoneGeoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refZoneGeoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refZoneGeoListModification',
                content: 'Deleted an refZoneGeo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ref-zone-geo-delete-popup',
    template: ''
})
export class RefZoneGeoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refZoneGeoPopupService: RefZoneGeoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.refZoneGeoPopupService
                .open(RefZoneGeoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
