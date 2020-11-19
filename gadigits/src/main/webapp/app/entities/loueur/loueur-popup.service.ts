import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Loueur } from './loueur.model';
import { LoueurService } from './loueur.service';

@Injectable()
export class LoueurPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private loueurService: LoueurService

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
                this.loueurService.find(id).subscribe((loueur) => {
                    if (loueur.dateEffetConvention) {
                        loueur.dateEffetConvention = {
                            year: loueur.dateEffetConvention.getFullYear(),
                            month: loueur.dateEffetConvention.getMonth() + 1,
                            day: loueur.dateEffetConvention.getDate()
                        };
                    }
                    if (loueur.dateFinConvention) {
                        loueur.dateFinConvention = {
                            year: loueur.dateFinConvention.getFullYear(),
                            month: loueur.dateFinConvention.getMonth() + 1,
                            day: loueur.dateFinConvention.getDate()
                        };
                    }
                    this.ngbModalRef = this.loueurModalRef(component, loueur);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.loueurModalRef(component, new Loueur());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    
    openLoueurModal(component: Component, loueurId?: number): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.loueurId = loueurId;
        return this.ngbModalRef;
    }


    loueurModalRef(component: Component, loueur: Loueur): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.loueur = loueur;
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
