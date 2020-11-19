import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { ScrollToModule } from '@nicky-lenaers/ngx-scroll-to';
import { AuxiliumSharedModule } from '../../shared';

import {
    RefTarifService,
    RefTarifPopupService,
    RefTarifComponent,
    RefTarifDetailComponent,
    RefTarifDialogComponent,
    RefTarifPopupComponent,
    RefTarifDeletePopupComponent,
    RefTarifDeleteDialogComponent,
    refTarifRoute,
    refTarifPopupRoute,
    RefTarifResolvePagingParams,
} from '.';
const ENTITY_STATES = [
    ...refTarifRoute,
    ...refTarifPopupRoute,
];

@NgModule({
    imports: [
       AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        ScrollToModule.forRoot(),
        DataTablesModule
    ],
    declarations: [
        RefTarifComponent,
        RefTarifDetailComponent,
        RefTarifDialogComponent,
        RefTarifDeleteDialogComponent,
        RefTarifPopupComponent,
        RefTarifDeletePopupComponent,
    ],
    entryComponents: [
        RefTarifComponent,
        RefTarifDialogComponent,
        RefTarifPopupComponent,
        RefTarifDeleteDialogComponent,
        RefTarifDeletePopupComponent,
    ],
    providers: [
        RefTarifService,
        RefTarifPopupService,
        RefTarifResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefTarifModule {}
