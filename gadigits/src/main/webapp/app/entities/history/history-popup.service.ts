import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';


@Injectable()
export class HistoryPopupService {
    private ngbModalRef: NgbModalRef;


    constructor(
        private modalService: NgbModal,
        private router: Router,
       // private agencyService: AgencyService

    ) {
        this.ngbModalRef = null;
    }
   /* open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.agencyService.find(id).subscribe((agency) => {
                    this.ngbModalRef = this.agencyModalRef(component, agency);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.agencyModalRef(component, any);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }*/


    agencyModalRef(component: Component, entity: any): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entity = entity;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }

    openHist(component: Component, id?: number, entityname?: string): NgbModalRef {
        console.log("2emeeee entrer"+id);
        console.log("2emeeee entrer name entité"+entityname);
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.idEntity = id;
        this.ngbModalRef.componentInstance.nameEntity = entityname;
        return this.ngbModalRef;
    }
}
