import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefModeGestionService,
    RefModeGestionPopupService,
    RefModeGestionComponent,
    RefModeGestionDetailComponent,
    RefModeGestionDialogComponent,
    RefModeGestionPopupComponent,
    RefModeGestionDeletePopupComponent,
    RefModeGestionDeleteDialogComponent,
    refModeGestionRoute,
    refModeGestionPopupRoute,
    RefModeGestionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refModeGestionRoute,
    ...refModeGestionPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefModeGestionComponent,
        RefModeGestionDetailComponent,
        RefModeGestionDialogComponent,
        RefModeGestionDeleteDialogComponent,
        RefModeGestionPopupComponent,
        RefModeGestionDeletePopupComponent,
    ],
    entryComponents: [
        RefModeGestionComponent,
        RefModeGestionDialogComponent,
        RefModeGestionPopupComponent,
        RefModeGestionDeleteDialogComponent,
        RefModeGestionDeletePopupComponent,
    ],
    providers: [
        RefModeGestionService,
        RefModeGestionPopupService,
        RefModeGestionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefModeGestionModule {}
