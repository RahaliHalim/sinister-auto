import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import {
    PrestationPECService,
    ReparationAjoutSaisieDevisComponent,
    reparationRoute,
    prestationPECPopupRoute,
    PrestationPECResolvePagingParams,
    GenerationAPECComponent,
    GenerationBSComponent,
    InstanceReparationComponent,
    SignatureAccordComponent,
    SignatureBSComponent,
    PieceJointeSignatureAccordComponent,
    PieceJointeSignatureAccordPopupService,
    DemontageComponent,
    ConfirmationComponent,
    VerificationComponent,
    ExpertOpinionComponent,
    RectificationComponent,
    ConfirmationModificationPrixComponent,
    RevueValidationDevisComponent,
    ConfirmationDevisComplementaryComponent,
    QuotationGtEstimateComponent
} from './';
import { AuxiliumPointChocModule } from '../point-choc/point-choc.module';
import { AuxiliumTiersModule } from '../tiers/tiers.module';
import { DataTablesModule } from 'angular-datatables/src/angular-datatables.module';

const ENTITY_STATES = [
    ...reparationRoute,
    ...prestationPECPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumPointChocModule,
        AuxiliumTiersModule,
        DataTablesModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RectificationComponent,
        ReparationAjoutSaisieDevisComponent,
        GenerationAPECComponent,
        GenerationBSComponent,
        InstanceReparationComponent,
        ExpertOpinionComponent,
        SignatureAccordComponent,
        SignatureBSComponent,
        PieceJointeSignatureAccordComponent,
        DemontageComponent,
        ConfirmationComponent,
        VerificationComponent,
        ConfirmationModificationPrixComponent,
        RevueValidationDevisComponent,
        ConfirmationDevisComplementaryComponent,
        QuotationGtEstimateComponent

    ],
    entryComponents: [
        RectificationComponent,
        ReparationAjoutSaisieDevisComponent,
        GenerationAPECComponent,
        GenerationBSComponent,
        InstanceReparationComponent,
        ExpertOpinionComponent,
        SignatureAccordComponent,
        SignatureBSComponent,
        PieceJointeSignatureAccordComponent,
        DemontageComponent,
        ConfirmationComponent,
        VerificationComponent,
        ConfirmationModificationPrixComponent,
        RevueValidationDevisComponent,
        ConfirmationDevisComplementaryComponent,
        QuotationGtEstimateComponent


    ],
    providers: [
        PrestationPECService,
        PieceJointeSignatureAccordPopupService,
        PrestationPECResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumReparationModule { }
