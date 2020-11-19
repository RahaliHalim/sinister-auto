import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';
import {
    RulesService,
    rulesRoute,
} from './';
const ENTITY_STATES = [
    ...rulesRoute
];
@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule,
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        RulesService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRulesModule {}
