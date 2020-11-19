package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefNatureContrat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RefNatureContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefNatureContratRepository extends JpaRepository<RefNatureContrat,Long> {
    
    @Query("select distinct ref_nature_contrat from RefNatureContrat ref_nature_contrat")
    List<RefNatureContrat> findAllWithEagerRelationships();

    @Query("select ref_nature_contrat from RefNatureContrat ref_nature_contrat where ref_nature_contrat.id =:id")
    RefNatureContrat findOneWithEagerRelationships(@Param("id") Long id);
    
}
