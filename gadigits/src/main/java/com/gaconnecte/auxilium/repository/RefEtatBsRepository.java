package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefEtatBs;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefEtatBs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefEtatBsRepository extends JpaRepository<RefEtatBs,Long> {
    
}
