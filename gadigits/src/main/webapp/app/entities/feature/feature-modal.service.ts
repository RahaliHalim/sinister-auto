import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Features } from './feature.model';
import { FeatureService } from './feature.service';


@Injectable()
export class FeatureModalService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private featureService: FeatureService
    ) {}

    open(component: Component, id?: number): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.featureService.find(id).subscribe((feature) => this.featureModalRef(component, feature));
        } else {
            return this.featureModalRef(component, new Features());
        }
    }

    featureModalRef(component: Component, feature: Features): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.feature = feature;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
