import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    OperationService,
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
        OperationService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumOperationModule {}
