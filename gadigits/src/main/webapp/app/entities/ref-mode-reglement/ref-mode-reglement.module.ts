import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefModeReglementService,
    RefModeReglementPopupService,
    RefModeReglementComponent,
    RefModeReglementDetailComponent,
    RefModeReglementDialogComponent,
    RefModeReglementPopupComponent,
    RefModeReglementDeletePopupComponent,
    RefModeReglementDeleteDialogComponent,
    refModeReglementRoute,
    refModeReglementPopupRoute,
    RefModeReglementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refModeReglementRoute,
    ...refModeReglementPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefModeReglementComponent,
        RefModeReglementDetailComponent,
        RefModeReglementDialogComponent,
        RefModeReglementDeleteDialogComponent,
        RefModeReglementPopupComponent,
        RefModeReglementDeletePopupComponent,
    ],
    entryComponents: [
        RefModeReglementComponent,
        RefModeReglementDialogComponent,
        RefModeReglementPopupComponent,
        RefModeReglementDeleteDialogComponent,
        RefModeReglementDeletePopupComponent,
    ],
    providers: [
        RefModeReglementService,
        RefModeReglementPopupService,
        RefModeReglementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefModeReglementModule {}
