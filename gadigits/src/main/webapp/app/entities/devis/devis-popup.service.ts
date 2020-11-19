import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Devis } from './devis.model';
import { DevisService } from './devis.service';
import { DatePipe } from '@angular/common';

@Injectable()
export class DevisPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private datePipe: DatePipe,
        private router: Router,
        private devisService: DevisService

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
                this.devisService.find(id).subscribe((devis) => {
                    if (devis.dateGeneration) {
                        devis.dateGeneration = this.datePipe
                        .transform(devis.dateGeneration, 'yyyy-MM-ddThh:mm');
                    }
                    this.ngbModalRef = this.devisModalRef(component, devis);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.devisModalRef(component, new Devis());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    devisModalRef(component: Component, devis: Devis): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.devis = devis;
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
