import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Reparateur } from './reparateur.model';
import { ReparateurService } from './reparateur.service';

@Injectable()
export class ReparateurPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private reparateurService: ReparateurService

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
                this.reparateurService.find(id).subscribe((reparateur) => {
                    /*if (reparateur.debutConge) {
                        reparateur.debutConge = {
                            year: reparateur.debutConge.getFullYear(),
                            month: reparateur.debutConge.getMonth() + 1,
                            day: reparateur.debutConge.getDate()
                        };
                    }
                    if (reparateur.datFinConge) {
                        reparateur.datFinConge = {
                            year: reparateur.datFinConge.getFullYear(),
                            month: reparateur.datFinConge.getMonth() + 1,
                            day: reparateur.datFinConge.getDate()
                        };
                    }
                    if (reparateur.dateCreation) {
                        reparateur.dateCreation = {
                            year: reparateur.dateCreation.getFullYear(),
                            month: reparateur.dateCreation.getMonth() + 1,
                            day: reparateur.dateCreation.getDate()
                        };
                    }
                    this.ngbModalRef = this.reparateurModalRef(component, reparateur);
                    resolve(this.ngbModalRef);*/
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reparateurModalRef(component, new Reparateur());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reparateurModalRef(component: Component, reparateur: Reparateur): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reparateur = reparateur;
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
