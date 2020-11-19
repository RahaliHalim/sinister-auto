import { SinisterPrestation } from './sinister-prestation.model';
import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Camion } from '../camion';

@Injectable()
export class SiniterPrestationTugPopupService {
    private ngbModalRef: NgbModalRef;
    constructor(
        private modalService: NgbModal,
        private router: Router

    ) {
        this.ngbModalRef = null; 
    }
    openTugModal(component: Component, prestation?: SinisterPrestation): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sinisterPrestation = prestation;
        return this.ngbModalRef;
    }

    openLoueurModal(component: Component, prestation?: SinisterPrestation, shortDuration?: boolean, longDuration?: boolean): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sinisterPrestation = prestation;
        this.ngbModalRef.componentInstance.shortDuration = shortDuration;
        this.ngbModalRef.componentInstance.longDuration = longDuration;
        return this.ngbModalRef;
    }

    openViewPrestationModal(component: Component, prestationId?: number): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', windowClass: 'modal-xxl', backdrop: 'static' });
        this.ngbModalRef.componentInstance.prestationId = prestationId;
        return this.ngbModalRef;
    }

}
