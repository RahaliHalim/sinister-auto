import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ServiceAssurance } from './service-assurance.model';
import { ServiceAssuranceService } from './service-assurance.service';

@Injectable()
export class ServiceAssurancePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private serviceAssuranceService: ServiceAssuranceService

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
                this.serviceAssuranceService.find(id).subscribe((serviceAssurance) => {
                    this.ngbModalRef = this.serviceAssuranceModalRef(component, serviceAssurance);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.serviceAssuranceModalRef(component, new ServiceAssurance());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    serviceAssuranceModalRef(component: Component, serviceAssurance: ServiceAssurance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.serviceAssurance = serviceAssurance;
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
