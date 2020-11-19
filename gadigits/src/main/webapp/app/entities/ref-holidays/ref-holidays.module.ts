import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';
import {
    RefHolidaysService,
    RefHolidaysComponent,
    RefHolidaysRoute
} from '.';

const ENTITY_STATES = [
    ...RefHolidaysRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        RefHolidaysComponent
    ],
    entryComponents: [
        RefHolidaysComponent
    ],
    providers: [
        RefHolidaysService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefHolidaysModule {}
