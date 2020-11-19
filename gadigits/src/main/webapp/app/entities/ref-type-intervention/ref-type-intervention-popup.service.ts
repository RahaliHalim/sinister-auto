import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefTypeIntervention } from './ref-type-intervention.model';
import { RefTypeInterventionService } from './ref-type-intervention.service';

@Injectable()
export class RefTypeInterventionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refTypeInterventionService: RefTypeInterventionService

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
                this.refTypeInterventionService.find(id).subscribe((refTypeIntervention) => {
                    this.ngbModalRef = this.refTypeInterventionModalRef(component, refTypeIntervention);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.refTypeInterventionModalRef(component, new RefTypeIntervention());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    refTypeInterventionModalRef(component: Component, refTypeIntervention: RefTypeIntervention): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.refTypeIntervention = refTypeIntervention;
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
