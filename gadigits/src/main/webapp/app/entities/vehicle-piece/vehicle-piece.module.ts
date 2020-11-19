import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    VehiclePieceService,
    VehiclePieceComponent,
    vehiclePieceRoute,
} from './';

const ENTITY_STATES = [
    ...vehiclePieceRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        VehiclePieceComponent,
    ],
    entryComponents: [
        VehiclePieceComponent,
    ],
    providers: [
        VehiclePieceService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehiclePieceModule {}
