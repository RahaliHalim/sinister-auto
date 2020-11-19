import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PolicyHolder } from './policy-holder.model';
import { PolicyHolderService } from './policy-holder.service';

@Injectable()
export class PolicyHolderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private policyHolderService: PolicyHolderService

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
                this.policyHolderService.find(id).subscribe((policyHolder) => {
                    if (policyHolder.creationDate) {
                        policyHolder.creationDate = {
                            year: policyHolder.creationDate.getFullYear(),
                            month: policyHolder.creationDate.getMonth() + 1,
                            day: policyHolder.creationDate.getDate()
                        };
                    }
                    if (policyHolder.updateDate) {
                        policyHolder.updateDate = {
                            year: policyHolder.updateDate.getFullYear(),
                            month: policyHolder.updateDate.getMonth() + 1,
                            day: policyHolder.updateDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.policyHolderModalRef(component, policyHolder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.policyHolderModalRef(component, new PolicyHolder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    policyHolderModalRef(component: Component, policyHolder: PolicyHolder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.policyHolder = policyHolder;
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
