import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    DetailsQuotationService,
} from '.';

@NgModule({
    imports: [
        AuxiliumSharedModule,
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        DetailsQuotationService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumDetailsQuotationModule { }