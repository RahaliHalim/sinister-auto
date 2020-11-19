package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefNatureExpertise;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefNatureExpertise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefNatureExpertiseRepository extends JpaRepository<RefNatureExpertise,Long> {
    
}
