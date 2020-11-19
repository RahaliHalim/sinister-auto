import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefNatureExpertise } from './ref-nature-expertise.model';
import { RefNatureExpertiseService } from './ref-nature-expertise.service';

@Injectable()
export class RefNatureExpertisePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refNatureExpertiseService: RefNatureExpertiseService

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
                this.refNatureExpertiseService.find(id).subscribe((refNatureExpertise) => {
                    this.ngbModalRef = this.refNatureExpertiseModalRef(component, refNatureExpertise);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.refNatureExpertiseModalRef(component, new RefNatureExpertise());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    refNatureExpertiseModalRef(component: Component, refNatureExpertise: RefNatureExpertise): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.refNatureExpertise = refNatureExpertise;
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
