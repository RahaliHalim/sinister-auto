import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefEtatDossier } from './ref-etat-dossier.model';
import { RefEtatDossierService } from './ref-etat-dossier.service';

@Injectable()
export class RefEtatDossierPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refEtatDossierService: RefEtatDossierService

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
                this.refEtatDossierService.find(id).subscribe((refEtatDossier) => {
                    this.ngbModalRef = this.refEtatDossierModalRef(component, refEtatDossier);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.refEtatDossierModalRef(component, new RefEtatDossier());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    refEtatDossierModalRef(component: Component, refEtatDossier: RefEtatDossier): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.refEtatDossier = refEtatDossier;
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
