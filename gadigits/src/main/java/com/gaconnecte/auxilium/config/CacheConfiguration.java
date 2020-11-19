package com.gaconnecte.auxilium.config;

import java.util.concurrent.TimeUnit;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(
                        Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.gaconnecte.auxilium.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.User.class.getName() + ".persistentTokens",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefPack.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeService.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ContratAssurance.class.getName() + ".garantiesContrat",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeService.class.getName() + ".packs",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeService.class.getName() + ".dossiers",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefRemorqueur.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefRemorqueur.class.getName() + ".contacts",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reparateur.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reparateur.class.getName() + ".marquePrincipales",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reparateur.class.getName() + ".autreMarques",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reparateur.class.getName() + ".materiels",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reparateur.class.getName() + ".grilles", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PersonnePhysique.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PersonneMorale.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefModeReglement.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefModeReglement.class.getName() + ".reparateurs",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefZoneGeo.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefZoneGeo.class.getName() + ".gouvernorats",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeIntervention.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeIntervention.class.getName() + ".detailMos",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefMateriel.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefMateriel.class.getName() + ".reparateurs",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Concessionnaire.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Concessionnaire.class.getName() + ".marques",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Fournisseur.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Fournisseur.class.getName() + ".contacts",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Expert.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Expert.class.getName() + ".contacts", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Expert.class.getName() + ".garanties", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PersonneMorale.class.getName() + ".contacts",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Expert.class.getName() + ".dossiers", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.AgentGeneral.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.AgentGeneral.class.getName() + ".dossiers",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefMotif.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefMotif.class.getName() + ".actionUtilisateurs",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefMotif.class.getName() + ".journals", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.SysActionUtilisateur.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefMotif.class.getName() + ".dossiers", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.SysActionUtilisateur.class.getName() + ".journals",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.SysActionUtilisateur.class.getName() + ".motifs",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Journal.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Journal.class.getName() + ".motifs", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Assure.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Assure.class.getName() + ".dossiers", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Tiers.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Grille.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Grille.class.getName() + ".reparateurs", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Grille.class.getName() + ".typeInters", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DetailsPieces.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DetailsPieces.class.getName() + ".facturePieces",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Piece.class.getName() + ".detailsPieces",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Piece.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Piece.class.getName() + ".modelVoitures",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypePieces.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PieceJointe.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefModeGestion.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefModeGestion.class.getName() + ".garanties",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ContratAssurance.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefNatureContrat.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefNatureContrat.class.getName() + ".compagnies",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeContrat.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypeContrat.class.getName() + ".compagnies",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehiculeAssure.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefPositionGa.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefBareme.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Devis.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DetailsMo.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DetailsMo.class.getName() + ".factureMos",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Piece.class.getName() + ".detailMos", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Facture.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Facture.class.getName() + ".factureMos", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Facture.class.getName() + ".factPieces", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.FactureDetailsMo.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.FacturePieces.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.BonSortie.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.AvisExpertMo.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.AvisExpertPiece.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ValidationDevis.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ValidationDevis.class.getName() + ".avisExpPieces",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ValidationDevis.class.getName() + ".avisExpMos",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTypePj.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefEtatDossier.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefFractionnement.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefNatureExpertise.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Apec.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PointChoc.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefEtatBs.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefEtatBs.class.getName() + ".bonSorties",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ServiceAssurance.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DesignationTypeContrat.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DesignationNatureContrat.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DesignationUsageContrat.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DesignationFractionnement.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Tarif.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Produit.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Produit.class.getName() + ".clients", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Dossier.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Prestation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Prestation.class.getName() + ".actions", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PrestationAvt.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PrestationPEC.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PrestationPEC.class.getName() + ".agentGenerales",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PrestationPEC.class.getName() + ".tiers",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Dossier.class.getName() + ".journals", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Prestation.class.getName() + ".journals",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.TracedException.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Observation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Lien.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Lien.class.getName() + ".cellules", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Cellule.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Lien.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Cellule.class.getName() + ".liens", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserCellule.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Authority.class.getName() + ".liens", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Authority.class.getName() + ".usersCellules",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserCellule.class.getName() + ".authorities",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ReparateurGrille.class.getName(), jcacheConfiguration);

            // referential entities
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefPricing.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefEnergy.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefStatusSinister.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefPartner.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefAgency.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefVehicleBrand.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefVehicleBrandModel.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefVehicleUsage.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefTugTruck.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Quotation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefHolidays.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefGrounds.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.prm.Convention.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.prm.ConventionAmendment.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.prm.SinisterTypeSetting.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.prm.ApecSettings.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.app.Sinister.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.app.SinisterPrestation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.app.Attachment.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Governorate.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Delegation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PolicyHolder.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehicleEnergy.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehicleBrand.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehicleBrandModel.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehicleUsage.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Periodicity.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Vehicle.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Partner.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Agency.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PolicyType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PolicyNature.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Policy.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Policy.class.getName() + ".vehicles", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reinsurer.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.SinisterPec.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.referential.RefStatusSinisterPec.class.getName(),
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VatRate.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.StampDuty.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Step.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Reason.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserProfile.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Functionality.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.BusinessEntity.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.BusinessEntity.class.getName() + ".functionalities",
                    jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ElementMenu.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserAccess.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserExtra.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserExtra.class.getName() + ".accesses", jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ProfileAccess.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.UserPartnerMode.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Referentiel.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Dossiers.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PriseEnCharge.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Statement.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Operation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RaisonAssistance.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PecStatusChangeMatrix.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RaisonPec.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehiclePiece.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehiclePieceType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.StatusPec.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.QuotationMP.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PolicyStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.PolicyReceiptStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.AmendmentType.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.ObservationApec.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.DetailsQuotation.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.VehiculeLoueur.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.Loueur.class.getName(), jcacheConfiguration);
            cm.createCache(com.gaconnecte.auxilium.domain.RefNaturePanne.class.getName(), jcacheConfiguration);

            // jhipster-needle-ehcache-add-entry
        };
    }
}
