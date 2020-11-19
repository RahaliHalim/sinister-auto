import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PieceJointe } from './piece-jointe.model';
import { PieceJointeService } from './piece-jointe.service';

@Injectable()
export class PieceJointePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pieceJointeService: PieceJointeService

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
                this.pieceJointeService.find(id).subscribe((pieceJointe) => {
                    if (pieceJointe.dateImport) {
                        pieceJointe.dateImport = {
                            year: pieceJointe.dateImport.getFullYear(),
                            month: pieceJointe.dateImport.getMonth() + 1,
                            day: pieceJointe.dateImport.getDate()
                        };
                    }
                    this.ngbModalRef = this.pieceJointeModalRef(component, pieceJointe);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pieceJointeModalRef(component, new PieceJointe());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pieceJointeModalRef(component: Component, pieceJointe: PieceJointe): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pieceJointe = pieceJointe;
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
