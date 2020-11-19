import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    GrilleService,
    GrillePopupService,
    GrilleComponent,
    GrilleDetailComponent,
    GrilleDialogComponent,
    GrillePopupComponent,
    GrilleDeletePopupComponent,
    GrilleDeleteDialogComponent,
    grilleRoute,
    grillePopupRoute,
    GrilleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...grilleRoute,
    ...grillePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GrilleComponent,
        GrilleDetailComponent,
        GrilleDialogComponent,
        GrilleDeleteDialogComponent,
        GrillePopupComponent,
        GrilleDeletePopupComponent,
    ],
    entryComponents: [
        GrilleComponent,
        GrilleDialogComponent,
        GrillePopupComponent,
        GrilleDeleteDialogComponent,
        GrilleDeletePopupComponent,
    ],
    providers: [
        GrilleService,
        GrillePopupService,
        GrilleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumGrilleModule {}
