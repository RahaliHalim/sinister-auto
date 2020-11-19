import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PieceJointeService,
    PieceJointePopupService,
    PieceJointeComponent,
    PieceJointeDetailComponent,
    PieceJointeDialogComponent,
    PieceJointePopupComponent,
    PieceJointeDeletePopupComponent,
    PieceJointeDeleteDialogComponent,
    pieceJointeRoute,
    pieceJointePopupRoute,
    PieceJointeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pieceJointeRoute,
    ...pieceJointePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PieceJointeComponent,
        PieceJointeDetailComponent,
        PieceJointeDialogComponent,
        PieceJointeDeleteDialogComponent,
        PieceJointePopupComponent,
        PieceJointeDeletePopupComponent,
    ],
    entryComponents: [
        PieceJointeComponent,
        PieceJointeDialogComponent,
        PieceJointePopupComponent,
        PieceJointeDeleteDialogComponent,
        PieceJointeDeletePopupComponent,
    ],
    providers: [
        PieceJointeService,
        PieceJointePopupService,
        PieceJointeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPieceJointeModule {}
