import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Vehicle } from './vehicle.model';
import { VehiclePopupService } from './vehicle-popup.service';
import { VehicleService } from './vehicle.service';
import { VehicleBrand, VehicleBrandService } from '../vehicle-brand';
import { VehicleBrandModel, VehicleBrandModelService } from '../vehicle-brand-model';
import { VehicleEnergy, VehicleEnergyService } from '../vehicle-energy';
import { VehicleUsage, VehicleUsageService } from '../vehicle-usage';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vehicle-dialog',
    templateUrl: './vehicle-dialog.component.html'
})
export class VehicleDialogComponent implements OnInit {

    vehicle: Vehicle;
    isSaving: boolean;

    vehiclebrands: VehicleBrand[];

    vehiclebrandmodels: VehicleBrandModel[];

    vehicleenergies: VehicleEnergy[];

    vehicleusages: VehicleUsage[];
    firstEntryIntoServiceDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private vehicleService: VehicleService,
        private vehicleBrandService: VehicleBrandService,
        private vehicleBrandModelService: VehicleBrandModelService,
        private vehicleEnergyService: VehicleEnergyService,
        private vehicleUsageService: VehicleUsageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.vehicleBrandService.query()
            .subscribe((res: ResponseWrapper) => { this.vehiclebrands = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleBrandModelService.query()
            .subscribe((res: ResponseWrapper) => { this.vehiclebrandmodels = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleEnergyService.query()
            .subscribe((res: ResponseWrapper) => { this.vehicleenergies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleUsageService.query()
            .subscribe((res: ResponseWrapper) => { this.vehicleusages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vehicle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vehicleService.update(this.vehicle));
        } else {
            this.subscribeToSaveResponse(
                this.vehicleService.create(this.vehicle));
        }
    }

    private subscribeToSaveResponse(result: Observable<Vehicle>) {
        result.subscribe((res: Vehicle) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Vehicle) {
        this.eventManager.broadcast({ name: 'vehicleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackVehicleBrandById(index: number, item: VehicleBrand) {
        return item.id;
    }

    trackVehicleBrandModelById(index: number, item: VehicleBrandModel) {
        return item.id;
    }

    trackVehicleEnergyById(index: number, item: VehicleEnergy) {
        return item.id;
    }

    trackVehicleUsageById(index: number, item: VehicleUsage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vehicle-popup',
    template: ''
})
export class VehiclePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehiclePopupService: VehiclePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vehiclePopupService
                    .open(VehicleDialogComponent as Component, params['id']);
            } else {
                this.vehiclePopupService
                    .open(VehicleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
