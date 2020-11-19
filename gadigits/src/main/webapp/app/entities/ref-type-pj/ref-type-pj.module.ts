import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefTypePjService,
    RefTypePjPopupService,
    RefTypePjComponent,
    RefTypePjDetailComponent,
    RefTypePjDialogComponent,
    RefTypePjPopupComponent,
    RefTypePjDeletePopupComponent,
    RefTypePjDeleteDialogComponent,
    refTypePjRoute,
    refTypePjPopupRoute,
    RefTypePjResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refTypePjRoute,
    ...refTypePjPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefTypePjComponent,
        RefTypePjDetailComponent,
        RefTypePjDialogComponent,
        RefTypePjDeleteDialogComponent,
        RefTypePjPopupComponent,
        RefTypePjDeletePopupComponent,
    ],
    entryComponents: [
        RefTypePjComponent,
        RefTypePjDialogComponent,
        RefTypePjPopupComponent,
        RefTypePjDeleteDialogComponent,
        RefTypePjDeletePopupComponent,
    ],
    providers: [
        RefTypePjService,
        RefTypePjPopupService,
        RefTypePjResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefTypePjModule {}
