import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Apec } from '../apec';
@Injectable()
export class PieceJointeSignatureAccordPopupService {
    private ngbModalRef: NgbModalRef;
    constructor(
        private modalService: NgbModal,
        private router: Router,
    ) {
        this.ngbModalRef = null;
    }


    pieceJointeModalRef(component: Component, apec?: Apec): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.apec = apec;
        return this.ngbModalRef;
    }
}
