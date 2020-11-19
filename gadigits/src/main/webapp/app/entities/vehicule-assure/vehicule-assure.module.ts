import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    VehiculeAssureService
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
        VehiculeAssureService
    ],
    exports: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehiculeAssureModule {}
