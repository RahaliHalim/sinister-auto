import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ContratAssurance } from './contrat-assurance.model';
import { ContratAssuranceService } from './contrat-assurance.service';

@Injectable()
export class ContratAssurancePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private contratAssuranceService: ContratAssuranceService

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
                this.contratAssuranceService.find(id).subscribe((contratAssurance) => {
                    if (contratAssurance.debutValidite) {
                        contratAssurance.debutValidite = {
                            year: contratAssurance.debutValidite.getFullYear(),
                            month: contratAssurance.debutValidite.getMonth() + 1,
                            day: contratAssurance.debutValidite.getDate()
                        };
                    }
                    if (contratAssurance.finValidite) {
                        contratAssurance.finValidite = {
                            year: contratAssurance.finValidite.getFullYear(),
                            month: contratAssurance.finValidite.getMonth() + 1,
                            day: contratAssurance.finValidite.getDate()
                        };
                    }
                    this.ngbModalRef = this.contratAssuranceModalRef(component, contratAssurance);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.contratAssuranceModalRef(component, new ContratAssurance());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    contratAssuranceModalRef(component: Component, contratAssurance: ContratAssurance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contratAssurance = contratAssurance;
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
