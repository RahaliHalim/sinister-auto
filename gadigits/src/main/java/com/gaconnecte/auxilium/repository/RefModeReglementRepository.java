package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefModeReglement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefModeReglement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefModeReglementRepository extends JpaRepository<RefModeReglement,Long> {
    
}
