import { RefPack } from '../ref-pack/ref-pack.model';
import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Camion } from '../camion';
@Injectable()
export class ConventionPackPopupService {
    private ngbModalRef: NgbModalRef;
    constructor(
        private modalService: NgbModal,
        private router: Router

    ) {
        this.ngbModalRef = null;
    }
    openTugModal(component: Component, refPack?: RefPack): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', windowClass: 'modal-xxl', backdrop: 'static' });
        this.ngbModalRef.componentInstance.refPack = refPack;
        return this.ngbModalRef;
    }
}
