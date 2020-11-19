import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    RaisonAssistanceService,
    RaisonAssistanceComponent,
    raisonAssistanceRoute,
} from './';

const ENTITY_STATES = [
    ...raisonAssistanceRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        RaisonAssistanceComponent,
    ],
    entryComponents: [
        RaisonAssistanceComponent,
    ],
    providers: [
        RaisonAssistanceService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRaisonAssistanceModule {}
