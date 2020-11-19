import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    SysVilleService
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
        SysVilleService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumSysVilleModule {}
