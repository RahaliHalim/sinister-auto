package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.Dossier;
import com.gaconnecte.auxilium.domain.PrestationAvt;
import com.gaconnecte.auxilium.domain.PrestationPEC;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the Sinister entity.
 */
@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {

    /**
     * Get no deleted dossiers
     *
     * @return
     */
    @Query("SELECT dossier from Dossier dossier where dossier.deleted = false")
    Set<Dossier> findAllDossiers();

    // TODO: organize theses queries
    @Query("SELECT dossier FROM Dossier dossier where dossier.vehicule.id = :vehiculeId")
    Dossier findByVehicule(@Param("vehiculeId") Long vehiculeId);

    /*
      * Bug721 Sofien : Les doss acceptés ne s'affiche pas pour le gest.Accep
     */
    @Query("SELECT distinct dossier from Dossier dossier where dossier.deleted= false and dossier.etat!="+"Accepte")
    Page<Dossier> findDossierNotAccepted(Pageable pageable);

    @Query("SELECT avt from PrestationAvt avt where avt.dossier.id =:dossierId")
    Page<PrestationAvt> findPrestationAssistanceByDossier(Pageable pageable, @Param("dossierId") Long dossierId);

    @Query("SELECT pec from PrestationPEC pec where pec.dossier.id =:dossierId")
    Page<PrestationPEC> findPrestationPECByDossier(Pageable pageable, @Param("dossierId") Long dossierId);

    @Query("SELECT count(*) from Dossier dossier where dossier.dateCreation between :convertedDateD and :convertedDateF")
    Long countDossier(@Param("convertedDateD") ZonedDateTime convertedDateD, @Param("convertedDateF") ZonedDateTime convertedDateF);

    @Query("SELECT pec from PrestationPEC pec where pec.decision = 'Accepted_With_Reserv'")
    List<PrestationPEC> getOutstandingDemands();

    @Query("SELECT pec from PrestationPEC pec where pec.status = 'ST_SUBMITTED'")
    List<PrestationPEC> getSentDemands();

    @Query("SELECT devis from Devis devis where devis.etatDevis = 'AccordCmp'")
    List<Devis> getAccordDemands();

    @Query("SELECT pec from PrestationPEC pec where pec.status = 'ST_EXT_NEW'")
    List<PrestationPEC> getAllExternalNewDemands();

}
