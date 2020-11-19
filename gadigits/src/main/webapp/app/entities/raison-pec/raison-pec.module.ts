import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    RaisonPecService,
    RaisonPecComponent,
    raisonPecRoute,
} from './';

const ENTITY_STATES = [
    ...raisonPecRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        RaisonPecComponent,
    ],
    entryComponents: [
        RaisonPecComponent,
    ],
    providers: [
        RaisonPecService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRaisonPecModule {}
