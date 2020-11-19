import { AuxiliumVehicleUsageModule } from './vehicle-usage/vehicle-usage.module';
import { AuxiliumVehicleEnergyModule } from './vehicle-energy/vehicle-energy.module';
import { AuxiliumVehicleBrandModelModule } from './vehicle-brand-model/vehicle-brand-model.module';
import { AuxiliumVehicleBrandModule } from './vehicle-brand/vehicle-brand.module';
import { AuxiliumVehicleModule } from './vehicle/vehicle.module';
import { AuxiliumPolicyHolderModule } from './policy-holder/policy-holder.module';
import { AuxiliumRegionModule } from './region/region.module';
import { AuxiliumPolicyNatureModule } from './policy-nature/policy-nature.module';
import { AuxiliumPolicyTypeModule } from './policy-type/policy-type.module';
import { AuxiliumPeriodicityModule } from './periodicity/periodicity.module';
import { AuxiliumAgencyModule } from './agency/agency.module';
import { Partner } from './partner/partner.model';
import { AuxiliumDelegationModule } from './delegation/delegation.module';
import { AuxiliumGovernorateModule } from './governorate/governorate.module';
import { Sinister } from './sinister/sinister.model';

import { AuxiliumSinisterPecModule } from './sinister-pec/sinister-pec.module';
import { AuxiliumSinisterModule } from './sinister/sinister.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AuxiliumRefPackModule } from './ref-pack/ref-pack.module';
import { AuxiliumRefTypeServiceModule } from './ref-type-service/ref-type-service.module';
import { AuxiliumRefRemorqueurModule } from './ref-remorqueur/ref-remorqueur.module';
import { AuxiliumReparateurModule } from './reparateur/reparateur.module';
import { AuxiliumContactModule } from './contact/contact.module';
import { AuxiliumPersonnePhysiqueModule } from './personne-physique/personne-physique.module';
import { AuxiliumPersonneMoraleModule } from './personne-morale/personne-morale.module';
import { AuxiliumSysVilleModule } from './sys-ville/sys-ville.module';
import { AuxiliumSysGouvernoratModule } from './sys-gouvernorat/sys-gouvernorat.module';
import { AuxiliumReglementModule } from './reglement/reglement.module';
import { AuxiliumBordereauModule } from './bordereau/bordereau.module';
import { AuxiliumRefModeReglementModule } from './ref-mode-reglement/ref-mode-reglement.module';
import { AuxiliumRefZoneGeoModule } from './ref-zone-geo/ref-zone-geo.module';
import { AuxiliumRefTypeInterventionModule } from './ref-type-intervention/ref-type-intervention.module';
import { AuxiliumRefMaterielModule } from './ref-materiel/ref-materiel.module';
import { AuxiliumFournisseurModule } from './fournisseur/fournisseur.module';
import { AuxiliumExpertModule } from './expert/expert.module';
import { AuxiliumAgentGeneralModule } from './agent-general/agent-general.module';
import { AuxiliumRefCompagnieModule } from './ref-compagnie/ref-compagnie.module';
import { AuxiliumRefAgenceModule } from './ref-agence/ref-agence.module';
import { AuxiliumRefMotifModule } from './ref-motif/ref-motif.module';
import { AuxiliumSysActionUtilisateurModule } from './sys-action-utilisateur/sys-action-utilisateur.module';
import { AuxiliumJournalModule } from './journal/journal.module';
import { AuxiliumAssureModule } from './assure/assure.module';
import { AuxiliumTiersModule } from './tiers/tiers.module';
import { AuxiliumGrilleModule } from './grille/grille.module';
import { AuxiliumDetailsPiecesModule } from './details-pieces/details-pieces.module';
import { AuxiliumPieceModule } from './piece/piece.module';
import { AuxiliumRefTypePiecesModule } from './ref-type-pieces/ref-type-pieces.module';
import { AuxiliumPieceJointeModule } from './piece-jointe/piece-jointe.module';
import { AuxiliumRefModeGestionModule } from './ref-mode-gestion/ref-mode-gestion.module';
import { AuxiliumContratAssuranceModule } from './contrat-assurance/contrat-assurance.module';
import { AuxiliumRefNatureContratModule } from './ref-nature-contrat/ref-nature-contrat.module';
import { AuxiliumRefTypeContratModule } from './ref-type-contrat/ref-type-contrat.module';
import { AuxiliumVehiculeAssureModule } from './vehicule-assure/vehicule-assure.module';
import { AuxiliumRefPositionGaModule } from './ref-position-ga/ref-position-ga.module';
import { AuxiliumRefBaremeModule } from './ref-bareme/ref-bareme.module';
import { AuxiliumDevisModule } from './devis/devis.module';
import { AuxiliumRefTarifModule } from './ref-tarif/ref-tarif.module';
import { AuxiliumDetailsMoModule } from './details-mo/details-mo.module';

import { AuxiliumBonSortieModule } from './bon-sortie/bon-sortie.module';
import { AuxiliumRefTypePjModule } from './ref-type-pj/ref-type-pj.module';
import { AuxiliumRefEtatDossierModule } from './ref-etat-dossier/ref-etat-dossier.module';
import { AuxiliumRefFractionnementModule } from './ref-fractionnement/ref-fractionnement.module';
import { AuxiliumRefNatureExpertiseModule } from './ref-nature-expertise/ref-nature-expertise.module';
import { AuxiliumApecModule } from './apec/apec.module';
import { AuxiliumPointChocModule } from './point-choc/point-choc.module';
import { AuxiliumRefEtatBsModule } from './ref-etat-bs/ref-etat-bs.module';
import { AuxiliumServiceAssuranceModule } from './service-assurance/service-assurance.module';
import { AuxiliumClientModule } from './client/client.module';
import { AuxiliumTarifModule } from './tarif/tarif.module';
import { AuxiliumRefHolidaysModule } from './ref-holidays/ref-holidays.module';
import { AuxiliumConventionModule } from './convention/convention.module';
import { AuxiliumProduitModule } from './produit/produit.module';
import { AuxiliumObservationModule } from './observation/observation.module';
import { AuxiliumCelluleModule } from './cellule/cellule.module';
import { AuxiliumLienModule } from './lien/lien.module';
import { AuxiliumUserCelluleModule } from './user-cellule/user-cellule.module';
import {AuxiliumReparationModule} from './reparation/reparation.module';

import { AuxiliumDroolsModule } from './drools/drools.module';

import { AuxiliumExpertiseModule } from './expertise/expertise.module';
import { AuxiliumGroupModule } from './groupe/group.module';
import { AuxiliumFeatureModule } from './feature/feature.module';
import { AuxiliumRefPricingModule } from './ref-pricing/ref-pricing.module';
import { AuxiliumCamionModule } from './camion/camion.module';
import { AuxiliumPrimaryQuotationModule } from './PrimaryQuotation/primary-quotation.module';
import { AuxiliumQuotationModule } from './quotation/quotation.module';
import { AuxiliumPolicyModule } from './policy/policy.module';
import { AuxiliumPartnerModule } from './partner/partner.module';
import { AuxiliumGaGeoModule } from './ga-geo/ga-geo.module';
import { WebSocketModule } from './web-socket/web-socket.module';
import { AuxiliumReinsurerModule } from './reinsurer/reinsurer.module';
import { AuxiliumCalculationRulesModule } from './calculation-rules/calculation-rules.module';
import { AuxiliumRulesModule } from './rules/rules.module';
import { AuxiliumVatRateModule } from './vat-rate/vat-rate.module';

import { AuxiliumGenerationRapportModule } from './report/report.module';

import { AuxiliumStampDutyModule } from './stamp-duty/stamp-duty.module'
import { AuxiliumPecMotifDecisionModule } from './pec-motifs-decision/pec-motif-decision.module';
import { AuxiliumUserExtraModule } from './user-extra/user-extra.module';
import { AuxiliumUserProfileModule } from './user-profile/user-profile.module';
import { AuxiliumUserAccessModule } from './user-access/user-access.module';
import { AuxiliumFunctionalityModule } from './functionality/functionality.module';
import { AuxiliumBusinessEntityModule } from './business-entity/business-entity.module';
import { AuxiliumElementMenuModule } from './element-menu/element-menu.module';
import { AuxiliumHistoryModule } from './history/history.module';
import { AuxiliumReasonModule } from './reason/reason.module';


import { AuxiliumStepModule } from './step/step.module';
import { AuxiliumProfileAccessModule } from './profile-access/profile-access.module';
import { AuxiliumAttachmentModule } from './attachments/attachment.module';
import { AuxiliumUserPartnerModeModule } from './user-partner-mode/user-partner-mode.module';
import { AuxiliumUploadModule} from './upload/upload.module';
import { AuxiliumStatementModule } from './statement/statement.module';
import { AuxiliumVehiclePieceModule } from './vehicle-piece/vehicle-piece.module';
import { AuxiliumVehiclePieceTypeModule } from './vehicle-piece-type/vehicle-piece-type.module';
import { AuxiliumOperationModule } from './operation/operation.module';
import { AuxiliumRaisonAssistanceModule } from './raison-assistance/raison-assistance.module';
import { AuxiliumRaisonPecModule } from './raison-pec/raison-pec.module';
import { AuxiliumPecStatusChangeMatrixModule } from './pec-status-change-matrix/pec-status-change-matrix.module';
import { AuxiliumStatusPecModule } from './status-pec/status-pec.module';
import { AuxiliumReportsModule } from './reports/reports.module';
import { AuxiliumQuotationMPModule } from './quotation-m-p/quotation-mp.module';
import { AuxiliumViewSinisterPecModule } from './view-sinister-pec/view-sinister-pec.module';

import { AuxiliumPolicyReceiptStatusModule } from './policy-receipt-status/policy-receipt-status.module';
import { AuxiliumPolicyStatusModule } from './policy-status/policy-status.module';
import { AuxiliumAmendmentTypeModule } from './amendment-type/amendment-type.module';
import { AuxiliumNotificationModule } from './notification/notification.module';
import { AuxiliumNotificationAlertUserModule } from './notif-alert-user/notif-alert-user.module';
import { AuxiliumRefNotificationAlertModule } from './ref-notif-alert/ref-notif-alert.module';
import { AuxiliumViewPolicyModule } from './view-policy/view-policy.module';
import { AuxiliumObservationApecModule } from './observationApec/observation-apec.module';
import { AuxiliumRefStepPecModule } from './ref-step-pec/ref-step-pec.module';
import { AuxiliumDetailsQuotationModule } from './details-quotation/details-quotation.module';
import { AuxiliumVehiculeLoueurModule } from './vehicule-loueur/vehicule-loueur.module';
import { AuxiliumLoueurModule } from './loueur/loueur.module';
import { AuxiliumViewApecModule } from './view-apec/view-apec.module';
import { AuxiliumNaturePanneModule } from './ref-nature-panne/nature-panne.module';



@NgModule({
    imports: [
        AuxiliumGaGeoModule,
        AuxiliumRefHolidaysModule,
        AuxiliumLienModule,
        AuxiliumCelluleModule,
        AuxiliumUserCelluleModule,
        AuxiliumTarifModule,
        AuxiliumRefPackModule,
        AuxiliumRefTypeServiceModule,
        AuxiliumRefRemorqueurModule,
        AuxiliumReparateurModule,
        AuxiliumContactModule,
        AuxiliumPersonnePhysiqueModule,
        AuxiliumPersonneMoraleModule,
        AuxiliumSysVilleModule,
        AuxiliumSysGouvernoratModule,
        AuxiliumReglementModule,
        AuxiliumRefModeReglementModule,
        AuxiliumRefZoneGeoModule,
        AuxiliumRefTypeInterventionModule,
        AuxiliumRefMaterielModule,
        AuxiliumFournisseurModule,
        AuxiliumExpertModule,
        AuxiliumAgentGeneralModule,
        AuxiliumRefCompagnieModule,
        AuxiliumRefAgenceModule,
        AuxiliumRefMotifModule,
        AuxiliumSysActionUtilisateurModule,
        AuxiliumJournalModule,
        AuxiliumAssureModule,
        AuxiliumTiersModule,
        AuxiliumGrilleModule,
        AuxiliumDetailsPiecesModule,
        AuxiliumPieceModule,
        AuxiliumRefTypePiecesModule,
        AuxiliumPieceJointeModule,
        AuxiliumRefModeGestionModule,
        AuxiliumContratAssuranceModule,
        AuxiliumRefNatureContratModule,
        AuxiliumRefTypeContratModule,
        AuxiliumVehiculeAssureModule,
        AuxiliumRefPositionGaModule,
        AuxiliumRefBaremeModule,
        AuxiliumDevisModule,
        AuxiliumDetailsMoModule,
        AuxiliumBonSortieModule,
        AuxiliumGaGeoModule,
        AuxiliumRefTypePjModule,
        AuxiliumRefEtatDossierModule,
        AuxiliumRefFractionnementModule,
        AuxiliumRefNatureExpertiseModule,
        AuxiliumApecModule,
        AuxiliumPointChocModule,
        AuxiliumRefEtatBsModule,
        AuxiliumRefTarifModule,
        AuxiliumServiceAssuranceModule,
        AuxiliumClientModule,
        AuxiliumProduitModule,
        AuxiliumObservationModule,
        AuxiliumDroolsModule,
        AuxiliumReparationModule,
        AuxiliumExpertiseModule,
        AuxiliumReparationModule,
        AuxiliumBordereauModule,
        AuxiliumGroupModule,
        AuxiliumFeatureModule,
        AuxiliumRefPricingModule,
        AuxiliumCamionModule,
        AuxiliumPrimaryQuotationModule,
        AuxiliumQuotationModule,
        AuxiliumConventionModule,
        AuxiliumSinisterModule,
        AuxiliumGovernorateModule,
        AuxiliumDelegationModule,
        AuxiliumPolicyModule,
        AuxiliumPartnerModule,
        AuxiliumAgencyModule,
        AuxiliumPeriodicityModule,
        AuxiliumPolicyTypeModule,
        AuxiliumPolicyNatureModule,
        AuxiliumRegionModule,
        AuxiliumPolicyHolderModule,
        AuxiliumVehicleModule,
        AuxiliumVehicleBrandModule,
        AuxiliumVehicleBrandModelModule,
        AuxiliumVehicleEnergyModule,
        AuxiliumVehicleUsageModule,
        AuxiliumGaGeoModule,
        WebSocketModule,
        AuxiliumReinsurerModule,
        AuxiliumSinisterPecModule,
        AuxiliumCalculationRulesModule,
        AuxiliumRulesModule,
        AuxiliumVatRateModule,
        AuxiliumStampDutyModule,
        AuxiliumGenerationRapportModule,
        AuxiliumPecMotifDecisionModule,
        AuxiliumUserExtraModule,
        AuxiliumUserProfileModule,
        AuxiliumUserAccessModule,
        AuxiliumFunctionalityModule,
        AuxiliumBusinessEntityModule,
        AuxiliumElementMenuModule,
        AuxiliumHistoryModule,
        AuxiliumReasonModule,
        AuxiliumStepModule,
        AuxiliumUserProfileModule,
        AuxiliumProfileAccessModule,
        //AuxiliumModificationPrestationModule,
        AuxiliumUploadModule,
        AuxiliumUserPartnerModeModule,
        AuxiliumStatementModule,
        AuxiliumVehiclePieceModule,
        AuxiliumVehiclePieceTypeModule,
        AuxiliumOperationModule,
        AuxiliumRaisonAssistanceModule,
        AuxiliumRaisonPecModule,
        AuxiliumPecStatusChangeMatrixModule,
        AuxiliumStatusPecModule,
        AuxiliumReportsModule,
        AuxiliumQuotationMPModule,
        AuxiliumViewSinisterPecModule,
        AuxiliumAttachmentModule,
        AuxiliumPolicyReceiptStatusModule,
        AuxiliumPolicyStatusModule,
        AuxiliumAmendmentTypeModule,
        AuxiliumNotificationModule,
        AuxiliumNotificationAlertUserModule,
        AuxiliumRefNotificationAlertModule,
        AuxiliumViewPolicyModule,
        AuxiliumObservationApecModule,
        AuxiliumRefStepPecModule,
        AuxiliumDetailsQuotationModule,
        AuxiliumVehiculeLoueurModule,
        AuxiliumLoueurModule,
        AuxiliumViewApecModule,
        AuxiliumNaturePanneModule

/* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumEntityModule {}
