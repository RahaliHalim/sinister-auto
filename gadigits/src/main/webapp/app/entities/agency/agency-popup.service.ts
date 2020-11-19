import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Agency } from './agency.model';
import { AgencyService } from './agency.service';

@Injectable()
export class AgencyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private agencyService: AgencyService

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
                this.agencyService.find(id).subscribe((agency) => {
                    this.ngbModalRef = this.agencyModalRef(component, agency);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.agencyModalRef(component, new Agency());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    agencyModalRef(component: Component, agency: Agency): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.agency = agency;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }

    openHist(component: Component, id?: number, entityName?: string): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.id = id;
        this.ngbModalRef.componentInstance.entityName = entityName;
        return this.ngbModalRef;
    }
    openpopup(component: Component): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        return this.ngbModalRef;
    }
}
