import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefPack } from './ref-pack.model';
import { RefPackService } from './ref-pack.service';

@Injectable()
export class RefPackPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refPackService: RefPackService

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
                this.refPackService.find(id).subscribe((refPack) => {
                    this.ngbModalRef = this.refPackModalRef(component, refPack);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.refPackModalRef(component, new RefPack());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    refPackModalRef(component: Component, refPack: RefPack): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.refPack = refPack;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: {  }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}