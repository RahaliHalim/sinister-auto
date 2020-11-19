import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefTypePiecesService,
    RefTypePiecesPopupService,
    RefTypePiecesComponent,
    RefTypePiecesDetailComponent,
    RefTypePiecesDialogComponent,
    RefTypePiecesPopupComponent,
    RefTypePiecesDeletePopupComponent,
    RefTypePiecesDeleteDialogComponent,
    refTypePiecesRoute,
    refTypePiecesPopupRoute,
    RefTypePiecesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refTypePiecesRoute,
    ...refTypePiecesPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefTypePiecesComponent,
        RefTypePiecesDetailComponent,
        RefTypePiecesDialogComponent,
        RefTypePiecesDeleteDialogComponent,
        RefTypePiecesPopupComponent,
        RefTypePiecesDeletePopupComponent,
    ],
    entryComponents: [
        RefTypePiecesComponent,
        RefTypePiecesDialogComponent,
        RefTypePiecesPopupComponent,
        RefTypePiecesDeleteDialogComponent,
        RefTypePiecesDeletePopupComponent,
    ],
    providers: [
        RefTypePiecesService,
        RefTypePiecesPopupService,
        RefTypePiecesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefTypePiecesModule {}
