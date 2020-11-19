package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.DesignationNatureContrat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignationNatureContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationNatureContratRepository extends JpaRepository<DesignationNatureContrat,Long> {
    
}
