import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    VehicleEnergyService,
    VehicleEnergyComponent,
    vehicleEnergyRoute
} from './';

const ENTITY_STATES = [
    ...vehicleEnergyRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        VehicleEnergyComponent
    ],
    entryComponents: [
        VehicleEnergyComponent
    ],
    providers: [
        VehicleEnergyService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehicleEnergyModule {}
