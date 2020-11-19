package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Prestation;
import com.gaconnecte.auxilium.domain.PrestationPEC;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the PrestationPEC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestationPECRepository extends JpaRepository<PrestationPEC,Long> {

    @Query("select distinct prestation_pec from PrestationPEC prestation_pec left join fetch prestation_pec.agentGenerales")
    List<PrestationPEC> findAllWithEagerRelationships();

    @Query("select prestation_pec from PrestationPEC prestation_pec left join fetch prestation_pec.agentGenerales where prestation_pec.id =:id")
    PrestationPEC findOneWithEagerRelationships(@Param("id") Long id);

     @Query("SELECT prestation_pec FROM PrestationPEC prestation_pec where prestation_pec.dossier.id=:dossierId")
	PrestationPEC findPecByDossier(@Param("dossierId") Long dossierId);

    @Query("select ppec from PrestationPEC ppec ,Dossier dossier  where ppec.dossier.id =dossier.id and dossier.reference= :reference")
    PrestationPEC findByPrestationReference(@Param("reference") String reference);


    @Query("select distinct prestation_pec from PrestationPEC prestation_pec   where prestation_pec.reparateur is null ")
    List<PrestationPEC> findPrestationPECsWhereAffectReparateurIsNull();
    
    @Query("select prestationPec from PrestationPEC prestationPec where prestationPec.id= :prestationPecId")
    PrestationPEC findOneById(@Param("prestationPecId") Long prestationPecId);

    @Query("SELECT count(*) from PrestationPEC prestationPec where prestationPec.dateCreation between :convertedDateD and :convertedDateF")
	Long countPrestationPEC(@Param("convertedDateD") ZonedDateTime convertedDateD,  @Param("convertedDateF") ZonedDateTime convertedDateF);

    @Query("select distinct prestationPec from PrestationPEC prestationPec where prestationPec.reparateur.id =:id ")
    List<PrestationPEC> findPrestationPECsByReparateur(@Param("id") Long id);
    
    @Query("select distinct prestationPec from PrestationPEC prestationPec where prestationPec.expert.id =:id ")
    List<PrestationPEC> findPrestationPECsByExpert(@Param("id") Long id);
    
    @Query("select distinct prestationPec from PrestationPEC prestationPec where prestationPec.user.id =:id ")
    List<PrestationPEC> findPrestationPECsByUserId(@Param("id") Long id);
    
    @Query("select distinct prestationPec from PrestationPEC prestationPec where prestationPec.dossier.compagnie.id =:idCompagnie ")
    List<PrestationPEC> findPrestationPECsByCompagnieId(@Param("idCompagnie") Long idCompagnie);
   
    /*
    @Query("SELECT 'Demandes de PEC' as indicateur, count(prestationPec.id) as ytd1, 10 as objectif, count(prestationPec1.id) as ytd2, count(prestationPec.id)-count(prestationPec1.id) as tendenace from PrestationPEC prestationPec, PrestationPEC prestationPec1, Prestation prestation, Prestation prestation1 where prestationPec.prestation.id = prestation.id and prestation.dateCreation between :convertedDateD and :convertedDateF and prestationPec1.prestation.id = prestation1.id and prestation1.dateCreation between :convertedYearNow and :convertedYearLater")
	List<Object[]> countAllPrestationPEC(@Param("convertedDateD") ZonedDateTime convertedDateD,  @Param("convertedDateF") ZonedDateTime convertedDateF, @Param("convertedYearNow") ZonedDateTime convertedYearNow, @Param("convertedYearLater") ZonedDateTime convertedYearLater);
    */
    
    @Query("SELECT distinct prestationPec from PrestationPEC prestationPec where prestationPec.decision= 'Accepted' and prestationPec.reparateur is null or prestationPec.expert is null")
    Page<PrestationPEC> findAllPrestationPECAcceptedRepNullExpertNull(Pageable pageable);
    
    @Query("SELECT distinct prestationPec from PrestationPEC prestationPec where prestationPec.decision= 'Accepted' and prestationPec.reparateur is not null and prestationPec.expert is not null and prestationPec.confirmationRDV = false")
	List<PrestationPEC> findAllPrestationPECAcceptedExpertAndReparateurNotNull();
    
    @Query("SELECT distinct prestationPec from PrestationPEC prestationPec where prestationPec.decision= 'Accepted_With_Reserv'")
   	Page<PrestationPEC> findAllPrestationPECWithReserve(Pageable pageable);
    
    @Query("SELECT distinct prestationPec from PrestationPEC prestationPec where prestationPec.decision= 'Canceled'")
    Page<PrestationPEC> findAllPrestationPECCanceled(Pageable pageable);

    @Query("SELECT distinct prestationPec from PrestationPEC prestationPec where prestationPec.decision= 'Accepted'")
    Page<PrestationPEC> findAllPrestationPECAccepted(Pageable pageable);
    
    @Query("SELECT count(*) from PrestationPEC prestationPec where prestationPec.reparateur.id =:id ")
	Long countNumberAffeReparator(@Param("id") Long id);

        
    @Query("SELECT count(*) from PrestationPEC prestationPec where prestationPec.expert.id =:id ")
       Long countNumberAffeExpert(@Param("id") Long id);
       
       
    @Query("SELECT distinct prestationPec from PrestationPEC prestationPec where prestationPec.status= 'ST_INT_NEW'")
    Page<PrestationPEC> findAllPrestationPECInProgress(Pageable pageable);

}
