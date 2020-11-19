package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTypeIntervention;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the RefTypeIntervention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTypeInterventionRepository extends JpaRepository<RefTypeIntervention,Long> {
    
    @Query("select refTypeIntervention from RefTypeIntervention refTypeIntervention where refTypeIntervention.type =:type")
    Set<RefTypeIntervention> findByType(@Param("type") Integer type);
}
