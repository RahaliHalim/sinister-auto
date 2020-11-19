package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Observation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Spring Data JPA repository for the Observation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long> {

    @Query("select distinct observation from Observation observation where observation.sinisterPec.id =:prestationId ORDER BY observation.date ASC")
    List<Observation> findObservationByPrestation(@Param("prestationId") Long prestationId);
    @Query("select distinct observation from Observation observation where observation.devis.id =:devisId")
    Page<Observation> findObservationByDevis(Pageable pageable,@Param("devisId") Long devisId);
    
}