import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    VehiclePieceTypeService,
} from './';

@NgModule({
    imports: [
        AuxiliumSharedModule
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        VehiclePieceTypeService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehiclePieceTypeModule {}
