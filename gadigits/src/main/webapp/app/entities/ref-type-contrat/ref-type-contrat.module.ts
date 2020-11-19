import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefTypeContratService,
    RefTypeContratPopupService,
    RefTypeContratComponent,
    RefTypeContratDetailComponent,
    RefTypeContratDialogComponent,
    RefTypeContratPopupComponent,
    RefTypeContratDeletePopupComponent,
    RefTypeContratDeleteDialogComponent,
    refTypeContratRoute,
    refTypeContratPopupRoute,
    RefTypeContratResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refTypeContratRoute,
    ...refTypeContratPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefTypeContratComponent,
        RefTypeContratDetailComponent,
        RefTypeContratDialogComponent,
        RefTypeContratDeleteDialogComponent,
        RefTypeContratPopupComponent,
        RefTypeContratDeletePopupComponent,
    ],
    entryComponents: [
        RefTypeContratComponent,
        RefTypeContratDialogComponent,
        RefTypeContratPopupComponent,
        RefTypeContratDeleteDialogComponent,
        RefTypeContratDeletePopupComponent,
    ],
    providers: [
        RefTypeContratService,
        RefTypeContratPopupService,
        RefTypeContratResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefTypeContratModule {}
