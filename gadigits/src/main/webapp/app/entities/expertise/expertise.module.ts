import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumPointChocModule } from '../point-choc/point-choc.module';
import { AuxiliumTiersModule } from '../tiers/tiers.module';
import { AuxiliumSharedModule } from '../../shared';

import {
    PrestationPECService
} from './';
@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumPointChocModule,
        AuxiliumTiersModule
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        PrestationPECService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumExpertiseModule {}
