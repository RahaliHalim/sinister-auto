package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.DesignationTypeContrat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignationTypeContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationTypeContratRepository extends JpaRepository<DesignationTypeContrat,Long> {
    
}
