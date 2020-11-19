import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { FormsModule } from '@angular/forms';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefPricingService,
    RefPricingComponent,
    RefPricingDetailComponent,
    RefPricingDialogComponent,
    RefPricingDeleteDialogComponent,
    RefPricingRoute
} from './';

const ENTITY_STATES = [
    ...RefPricingRoute
];

@NgModule({
    imports: [
       AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule,
        FormsModule
    ],
    declarations: [
        RefPricingComponent,
        RefPricingDetailComponent,
        RefPricingDialogComponent,
        RefPricingDeleteDialogComponent
    ],
    entryComponents: [
        RefPricingComponent,
        RefPricingDialogComponent,
        RefPricingDeleteDialogComponent
    ],
    providers: [
        RefPricingService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefPricingModule {}
