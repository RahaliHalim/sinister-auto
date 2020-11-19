import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VehiculeLoueur } from './vehicule-loueur.model';
import { VehiculeLoueurService } from './vehicule-loueur.service';

@Injectable()
export class VehiculeLoueurPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private vehiculeLoueurService: VehiculeLoueurService

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
                this.vehiculeLoueurService.find(id).subscribe((vehiculeLoueur) => {
                    this.ngbModalRef = this.vehiculeLoueurModalRef(component, vehiculeLoueur);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vehiculeLoueurModalRef(component, new VehiculeLoueur());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vehiculeLoueurModalRef(component: Component, vehiculeLoueur: VehiculeLoueur): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vehiculeLoueur = vehiculeLoueur;
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
