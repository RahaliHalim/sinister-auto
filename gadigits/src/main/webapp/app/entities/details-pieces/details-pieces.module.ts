import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule} from '@angular/forms';

import { AuxiliumSharedModule } from '../../shared';
import {
    DetailsPiecesService,
    DetailsPiecesPopupService,
    DetailsPiecesComponent,
    DetailsPiecesDetailComponent,
    DetailsPiecesDialogComponent,
    DetailsPiecesPopupComponent,
    DetailsPiecesIngredientDialogComponent,
    DetailsPiecesIngredientPopupComponent,
    DetailsPiecesFournitureDialogComponent,
    DetailsPiecesFourniturePopupComponent,
    DetailsPiecesDeletePopupComponent,
    DetailsPiecesDeleteDialogComponent,
    detailsPiecesRoute,
    detailsPiecesPopupRoute,
    DetailsPiecesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...detailsPiecesRoute,
    ...detailsPiecesPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DetailsPiecesComponent,
        DetailsPiecesDetailComponent,
        DetailsPiecesDialogComponent,
        DetailsPiecesIngredientDialogComponent,
        DetailsPiecesFournitureDialogComponent,
        DetailsPiecesDeleteDialogComponent,
        DetailsPiecesPopupComponent,
        DetailsPiecesIngredientPopupComponent,
        DetailsPiecesFourniturePopupComponent,
        DetailsPiecesDeletePopupComponent,
    ],
    entryComponents: [
        DetailsPiecesComponent,
        DetailsPiecesDialogComponent,
        DetailsPiecesPopupComponent,
        DetailsPiecesIngredientDialogComponent,
        DetailsPiecesIngredientPopupComponent,
        DetailsPiecesFournitureDialogComponent,
        DetailsPiecesFourniturePopupComponent,
        DetailsPiecesDeleteDialogComponent,
        DetailsPiecesDeletePopupComponent,
    ],
    providers: [
        DetailsPiecesService,
        DetailsPiecesPopupService,
        DetailsPiecesResolvePagingParams,
    ],
    exports: [
        DetailsPiecesDialogComponent,
        DetailsPiecesIngredientDialogComponent,
        DetailsPiecesFournitureDialogComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumDetailsPiecesModule {}
