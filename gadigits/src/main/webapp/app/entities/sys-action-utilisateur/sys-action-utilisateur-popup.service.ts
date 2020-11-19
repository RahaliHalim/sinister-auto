import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SysActionUtilisateur } from './sys-action-utilisateur.model';
import { SysActionUtilisateurService } from './sys-action-utilisateur.service';

@Injectable()
export class SysActionUtilisateurPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sysActionUtilisateurService: SysActionUtilisateurService

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
                this.sysActionUtilisateurService.find(id).subscribe((sysActionUtilisateur) => {
                    this.ngbModalRef = this.sysActionUtilisateurModalRef(component, sysActionUtilisateur);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sysActionUtilisateurModalRef(component, new SysActionUtilisateur());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sysActionUtilisateurModalRef(component: Component, sysActionUtilisateur: SysActionUtilisateur): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sysActionUtilisateur = sysActionUtilisateur;
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
