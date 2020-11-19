import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    TiersService,
    TiersPopupService,
    TiersComponent,
    TiersDetailComponent,
    TiersDialogComponent,
    TiersDialogForDetailComponent,
    TiersPopupComponent,
    TiersDeletePopupComponent,
    TiersDeleteDialogComponent,
    tiersRoute,
    tiersPopupRoute,
    TiersResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tiersRoute,
    ...tiersPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TiersComponent,
        TiersDetailComponent,
        TiersDialogComponent,
        TiersDialogForDetailComponent,
        TiersDeleteDialogComponent,
        TiersPopupComponent,
        TiersDeletePopupComponent,
    ],
    entryComponents: [
        TiersComponent,
        TiersDialogComponent,
        TiersDialogForDetailComponent,
        TiersPopupComponent,
        TiersDeleteDialogComponent,
        TiersDeletePopupComponent,
    ],
    providers: [
        TiersService,
        TiersPopupService,
        TiersResolvePagingParams,
    ],
     exports: [
        TiersDialogComponent,
        TiersDialogForDetailComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumTiersModule {}
