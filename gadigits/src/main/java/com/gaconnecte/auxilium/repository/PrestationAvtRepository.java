package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PrestationAvt;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import com.gaconnecte.auxilium.domain.enumeration.TypePrestation;
import java.time.ZonedDateTime;


/**
 * Spring Data JPA repository for the PrestationAvt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestationAvtRepository extends JpaRepository<PrestationAvt,Long> {
    
    @Query("SELECT distinct prestationavt from PrestationAvt prestationavt where prestationavt.isDelete= false")
	List<PrestationAvt> findPrestationAvtByType(@Param("typePrestation") TypePrestation typePrestation);

    @Query("SELECT distinct prestationavt from PrestationAvt prestationavt where prestationavt.isDelete= false")
    PrestationAvt findByPrestation(@Param("prestationId") Long prestationId);
    
    @Query("SELECT count(*) from PrestationAvt prestationAvt where prestationAvt.dateCreation between :convertedDateD and :convertedDateF")
	Long countPrestationAvt(@Param("convertedDateD") ZonedDateTime convertedDateD,  @Param("convertedDateF") ZonedDateTime convertedDateF);
    @Query("select distinct prestationAvt from PrestationAvt prestationAvt where prestationAvt.dossier.id =:dossierId and prestationAvt.isDelete= false")
   List<PrestationAvt> findPrestationAvtsByDossier(@Param("dossierId") Long dossierId);
}
