import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PolicyReceiptStatus } from './policy-receipt-status.model';
import { PolicyReceiptStatusService } from './policy-receipt-status.service';

@Injectable()
export class PolicyReceiptStatusPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private policyReceiptStatusService: PolicyReceiptStatusService

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
                this.policyReceiptStatusService.find(id).subscribe((policyReceiptStatus) => {
                    this.ngbModalRef = this.policyReceiptStatusModalRef(component, policyReceiptStatus);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.policyReceiptStatusModalRef(component, new PolicyReceiptStatus());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    policyReceiptStatusModalRef(component: Component, policyReceiptStatus: PolicyReceiptStatus): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.policyReceiptStatus = policyReceiptStatus;
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
