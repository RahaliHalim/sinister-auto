import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    StampDutyService,
    StampDutyComponent,
    stampDutyRoute,
} from './';

const ENTITY_STATES = [
    ...stampDutyRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        StampDutyComponent,
    ],
    entryComponents: [
        StampDutyComponent,
    ],
    providers: [
        StampDutyService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumStampDutyModule {}
