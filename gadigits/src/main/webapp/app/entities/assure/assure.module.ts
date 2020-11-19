import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AuxiliumSharedModule } from '../../shared';

import {
    AssureService
} from './';

@NgModule({
    imports: [
        AuxiliumSharedModule
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    exports: [
    ],
    providers: [
        AssureService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumAssureModule {}
