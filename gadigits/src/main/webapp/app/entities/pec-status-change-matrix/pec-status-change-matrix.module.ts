import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AuxiliumSharedModule } from '../../shared';
import {
    PecStatusChangeMatrixService,
} from './';

@NgModule({
    imports: [
        AuxiliumSharedModule,
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        PecStatusChangeMatrixService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPecStatusChangeMatrixModule {}
