import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Vehicle } from './vehicle.model';
import { VehicleService } from './vehicle.service';

@Injectable()
export class VehiclePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private vehicleService: VehicleService

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
                this.vehicleService.find(id).subscribe((vehicle) => {
                    if (vehicle.firstEntryIntoService) {
                        vehicle.firstEntryIntoService = {
                            year: vehicle.firstEntryIntoService.getFullYear(),
                            month: vehicle.firstEntryIntoService.getMonth() + 1,
                            day: vehicle.firstEntryIntoService.getDate()
                        };
                    }
                    this.ngbModalRef = this.vehicleModalRef(component, vehicle);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vehicleModalRef(component, new Vehicle());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vehicleModalRef(component: Component, vehicle: Vehicle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vehicle = vehicle;
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
