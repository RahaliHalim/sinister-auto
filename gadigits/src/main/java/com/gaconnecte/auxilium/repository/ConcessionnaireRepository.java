package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Concessionnaire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Concessionnaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcessionnaireRepository extends JpaRepository<Concessionnaire,Long> {
    
    @Query("select distinct concessionnaire from Concessionnaire concessionnaire left join fetch concessionnaire.marques")
    List<Concessionnaire> findAllWithEagerRelationships();

    @Query("select concessionnaire from Concessionnaire concessionnaire left join fetch concessionnaire.marques where concessionnaire.id =:id")
    Concessionnaire findOneWithEagerRelationships(@Param("id") Long id);
    
}
