import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    ReasonService,
    ReasonPopupService,
    ReasonComponent,
    ReasonDetailComponent,
    ReasonDialogComponent,
    ReasonPopupComponent,
    ReasonDeletePopupComponent,
    ReasonDeleteDialogComponent,
    reasonRoute,
    reasonPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reasonRoute,
    ...reasonPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        ReasonComponent,
        ReasonDetailComponent,
        ReasonDialogComponent,
        ReasonDeleteDialogComponent,
        ReasonPopupComponent,
        ReasonDeletePopupComponent,
    ],
    entryComponents: [
        ReasonComponent,
        ReasonDialogComponent,
        ReasonPopupComponent,
        ReasonDeleteDialogComponent,
        ReasonDeletePopupComponent,
    ],
    providers: [
        ReasonService,
        ReasonPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumReasonModule {}
