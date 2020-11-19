import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';


@Injectable()
export class SinisterPecPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router

    ) {
        this.ngbModalRef = null;
    }

    openDecisionPecModal(component: Component, pec?: SinisterPec): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, { size: 'sm', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sinisterPec = pec;
        return this.ngbModalRef;
    }
}
