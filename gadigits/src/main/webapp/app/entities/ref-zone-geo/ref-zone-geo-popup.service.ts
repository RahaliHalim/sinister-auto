import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefZoneGeo } from './ref-zone-geo.model';
import { RefZoneGeoService } from './ref-zone-geo.service';

@Injectable()
export class RefZoneGeoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refZoneGeoService: RefZoneGeoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.refZoneGeoService.find(id).subscribe((refZoneGeo) => {
                    this.ngbModalRef = this.refZoneGeoModalRef(component, refZoneGeo);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.refZoneGeoModalRef(component, new RefZoneGeo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    refZoneGeoModalRef(component: Component, refZoneGeo: RefZoneGeo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.refZoneGeo = refZoneGeo;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
