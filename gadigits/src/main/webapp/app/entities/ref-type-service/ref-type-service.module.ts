import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    RefTypeServiceService,
    RefTypeServiceComponent,
    refTypeServiceRoute
} from './';

const ENTITY_STATES = [
    ...refTypeServiceRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        RefTypeServiceComponent
    ],
    entryComponents: [
        RefTypeServiceComponent
    ],
    providers: [
        RefTypeServiceService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefTypeServiceModule {}
