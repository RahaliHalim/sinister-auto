import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    VehicleBrandService,
    VehicleBrandComponent,
    UserComponent,
    vehicleBrandRoute
} from './';

const ENTITY_STATES = [
    ...vehicleBrandRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        UserComponent,
        VehicleBrandComponent
    ],
    entryComponents: [
        UserComponent,
        VehicleBrandComponent
    ],
    providers: [
        VehicleBrandService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehicleBrandModule {}
