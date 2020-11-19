import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Apec } from './apec.model';
import { ApecService } from './apec.service';

@Injectable()
export class ApecPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private apecService: ApecService

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
                this.apecService.find(id).subscribe((apec) => {
                    if (apec.dateGeneration) {
                        apec.dateGeneration = {
                            year: apec.dateGeneration.getFullYear(),
                            month: apec.dateGeneration.getMonth() + 1,
                            day: apec.dateGeneration.getDate()
                        };
                    }
                    this.ngbModalRef = this.apecModalRef(component, apec);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.apecModalRef(component, new Apec());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    apecModalRef(component: Component, apec: Apec): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.apec = apec;
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
