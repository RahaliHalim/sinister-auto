import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefNatureContratService,
    RefNatureContratPopupService,
    RefNatureContratComponent,
    RefNatureContratDetailComponent,
    RefNatureContratDialogComponent,
    RefNatureContratPopupComponent,
    RefNatureContratDeletePopupComponent,
    RefNatureContratDeleteDialogComponent,
    refNatureContratRoute,
    refNatureContratPopupRoute,
    RefNatureContratResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refNatureContratRoute,
    ...refNatureContratPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefNatureContratComponent,
        RefNatureContratDetailComponent,
        RefNatureContratDialogComponent,
        RefNatureContratDeleteDialogComponent,
        RefNatureContratPopupComponent,
        RefNatureContratDeletePopupComponent,
    ],
    entryComponents: [
        RefNatureContratComponent,
        RefNatureContratDialogComponent,
        RefNatureContratPopupComponent,
        RefNatureContratDeleteDialogComponent,
        RefNatureContratDeletePopupComponent,
    ],
    providers: [
        RefNatureContratService,
        RefNatureContratPopupService,
        RefNatureContratResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefNatureContratModule {}
