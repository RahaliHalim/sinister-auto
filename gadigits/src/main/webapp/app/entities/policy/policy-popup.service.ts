import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Policy } from './policy.model';
import { PolicyService } from './policy.service';

@Injectable()
export class PolicyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private policyService: PolicyService

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
                this.policyService.find(id).subscribe((policy) => {
                    if (policy.startDate) {
                        policy.startDate = {
                            year: policy.startDate.getFullYear(),
                            month: policy.startDate.getMonth() + 1,
                            day: policy.startDate.getDate()
                        };
                    }
                    if (policy.endDate) {
                        policy.endDate = {
                            year: policy.endDate.getFullYear(),
                            month: policy.endDate.getMonth() + 1,
                            day: policy.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.policyModalRef(component, policy);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.policyModalRef(component, new Policy());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    policyModalRef(component: Component, policy: Policy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.policy = policy;
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
