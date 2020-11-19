import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    VehicleUsageService,
    VehicleUsageComponent,
    vehicleUsageRoute
} from './';

const ENTITY_STATES = [
    ...vehicleUsageRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        VehicleUsageComponent
    ],
    entryComponents: [
        VehicleUsageComponent
    ],
    providers: [
        VehicleUsageService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehicleUsageModule {}
