package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefTypeContrat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RefTypeContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefTypeContratRepository extends JpaRepository<RefTypeContrat,Long> {
    
    @Query("select distinct ref_type_contrat from RefTypeContrat ref_type_contrat")
    List<RefTypeContrat> findAllWithEagerRelationships();

    @Query("select ref_type_contrat from RefTypeContrat ref_type_contrat where ref_type_contrat.id =:id")
    RefTypeContrat findOneWithEagerRelationships(@Param("id") Long id);
    
}
