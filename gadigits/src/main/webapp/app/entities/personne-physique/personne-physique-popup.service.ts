import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonnePhysique } from './personne-physique.model';
import { PersonnePhysiqueService } from './personne-physique.service';

@Injectable()
export class PersonnePhysiquePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personnePhysiqueService: PersonnePhysiqueService

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
                this.personnePhysiqueService.find(id).subscribe((personnePhysique) => {
                    this.ngbModalRef = this.personnePhysiqueModalRef(component, personnePhysique);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.personnePhysiqueModalRef(component, new PersonnePhysique());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    personnePhysiqueModalRef(component: Component, personnePhysique: PersonnePhysique): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personnePhysique = personnePhysique;
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
