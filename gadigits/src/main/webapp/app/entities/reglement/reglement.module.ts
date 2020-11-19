import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ReglementService,
    ReglementPopupService,
    ReglementComponent,
    ReglementDetailComponent,
    ReglementDialogComponent,
    ReglementPopupComponent,
    ReglementDeletePopupComponent,
    ReglementDeleteDialogComponent,
    reglementRoute,
    reglementPopupRoute,
    ReglementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...reglementRoute,
    ...reglementPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReglementComponent,
        ReglementDetailComponent,
        ReglementDialogComponent,
        ReglementDeleteDialogComponent,
        ReglementPopupComponent,
        ReglementDeletePopupComponent,
    ],
    entryComponents: [
        ReglementComponent,
        ReglementDialogComponent,
        ReglementPopupComponent,
        ReglementDeleteDialogComponent,
        ReglementDeletePopupComponent,
    ],
    providers: [
        ReglementService,
        ReglementPopupService,
        ReglementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumReglementModule {}
