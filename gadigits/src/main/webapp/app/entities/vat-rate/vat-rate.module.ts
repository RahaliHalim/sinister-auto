import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    VatRateService,
    VatRateComponent,
    vatRateRoute,
} from './';

const ENTITY_STATES = [
    ...vatRateRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        VatRateComponent,
    ],
    entryComponents: [
        VatRateComponent,
    ],
    providers: [
        VatRateService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVatRateModule {}
