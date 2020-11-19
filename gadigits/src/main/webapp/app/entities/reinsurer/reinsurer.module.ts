import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';
import {
    ReinsurerService,
    ReinsurerComponent,
    reinsurerRoute
} from './';

const ENTITY_STATES = [
    ...reinsurerRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        ReinsurerComponent
    ],
    entryComponents: [
        ReinsurerComponent
    ],
    providers: [
        ReinsurerService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumReinsurerModule {}
