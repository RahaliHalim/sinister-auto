import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    StatementService,
    StatementComponent,
    InvoiceComponent,
    statementRoute
} from './';

const ENTITY_STATES = [
    ...statementRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        StatementComponent,
        InvoiceComponent
    ],
    entryComponents: [
        StatementComponent,
        InvoiceComponent
    ],
    providers: [
        StatementService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumStatementModule {}
