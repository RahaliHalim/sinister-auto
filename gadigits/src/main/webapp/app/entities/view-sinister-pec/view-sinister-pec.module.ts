import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ViewSinisterPecService,
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
        ViewSinisterPecService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumViewSinisterPecModule {}
