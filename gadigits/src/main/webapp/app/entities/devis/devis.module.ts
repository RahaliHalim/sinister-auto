import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import {
   DevisService,
   DevisPopupService,
   ViewDetailQuotationService,
   DevisComponent,
   DevisDetailComponent,
   DevisDialogComponent,
   QuotationDialogComponent,
   QuotationPopupComponent,
   DevisAffecteExpertDialogComponent,
   DevisAffecteExpertPopupComponent,
   DevisPopupComponent,
   DevisDeletePopupComponent,
   DevisDeleteDialogComponent,
   devisRoute,
   devisPopupRoute,
   DevisResolvePagingParams,
   DevisRefusDialogComponent,
   DevisRefusPopupComponent,
   FacturationComponent,
   QuotationPieceAddComponent,
   GTEstimateComponent
} from './';
import { AuxiliumDetailsMoModule } from '../details-mo/details-mo.module';
import { AuxiliumDetailsPiecesModule } from '../details-pieces/details-pieces.module';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';

const ENTITY_STATES = [
   ...devisRoute,
   ...devisPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumDetailsMoModule,
        AuxiliumDetailsPiecesModule,
        TypeaheadModule.forRoot(),
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DevisComponent,
        DevisDetailComponent,
        DevisDialogComponent,
        QuotationDialogComponent,
        QuotationPopupComponent,
        DevisAffecteExpertDialogComponent,
        DevisAffecteExpertPopupComponent,
        DevisDeleteDialogComponent,
        DevisPopupComponent,
        DevisDeletePopupComponent,
        DevisRefusDialogComponent,
        DevisRefusPopupComponent,
        FacturationComponent,
        QuotationPieceAddComponent,
        GTEstimateComponent
        //PiecePipe
    ],
    entryComponents: [
        DevisComponent,
        DevisDialogComponent,
        QuotationDialogComponent,
        QuotationPopupComponent,
        DevisAffecteExpertDialogComponent,
        DevisAffecteExpertPopupComponent,
        DevisPopupComponent,
        DevisDeleteDialogComponent,
        DevisDeletePopupComponent,
        DevisRefusDialogComponent,
        DevisRefusPopupComponent,
        FacturationComponent,
        QuotationPieceAddComponent,
        GTEstimateComponent
    ],
    providers: [DevisService, DevisPopupService, ViewDetailQuotationService,DevisResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumDevisModule {}