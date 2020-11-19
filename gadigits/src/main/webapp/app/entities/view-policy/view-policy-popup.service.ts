import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ViewPolicy } from './view-policy.model';
import { ViewPolicyService } from './view-policy.service';

@Injectable()
export class ViewPolicyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private viewPolicyService: ViewPolicyService

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
                this.viewPolicyService.find(id).subscribe((viewPolicy) => {
                    if (viewPolicy.startDate) {
                        viewPolicy.startDate = {
                            year: viewPolicy.startDate.getFullYear(),
                            month: viewPolicy.startDate.getMonth() + 1,
                            day: viewPolicy.startDate.getDate()
                        };
                    }
                    if (viewPolicy.endDate) {
                        viewPolicy.endDate = {
                            year: viewPolicy.endDate.getFullYear(),
                            month: viewPolicy.endDate.getMonth() + 1,
                            day: viewPolicy.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.viewPolicyModalRef(component, viewPolicy);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.viewPolicyModalRef(component, new ViewPolicy());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    viewPolicyModalRef(component: Component, viewPolicy: ViewPolicy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.viewPolicy = viewPolicy;
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
