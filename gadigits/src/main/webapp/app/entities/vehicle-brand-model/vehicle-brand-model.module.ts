import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    VehicleBrandModelService,
    VehicleBrandModelComponent,
    vehicleBrandModelRoute
} from './';

const ENTITY_STATES = [
    ...vehicleBrandModelRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        VehicleBrandModelComponent
    ],
    entryComponents: [
        VehicleBrandModelComponent
    ],
    providers: [
        VehicleBrandModelService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehicleBrandModelModule {}
