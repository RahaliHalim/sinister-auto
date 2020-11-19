import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PieceService,
    PiecePopupService,
    PieceComponent,
    PieceDetailComponent,
    PieceDialogComponent,
    PiecePopupComponent,
    PieceDeletePopupComponent,
    PieceDeleteDialogComponent,
    pieceRoute,
    piecePopupRoute,
    PieceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pieceRoute,
    ...piecePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PieceComponent,
        PieceDetailComponent,
        PieceDialogComponent,
        PieceDeleteDialogComponent,
        PiecePopupComponent,
        PieceDeletePopupComponent,
    ],
    entryComponents: [
        PieceComponent,
        PieceDialogComponent,
        PiecePopupComponent,
        PieceDeleteDialogComponent,
        PieceDeletePopupComponent,
    ],
    providers: [
        PieceService,
        PiecePopupService,
        PieceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPieceModule {}
