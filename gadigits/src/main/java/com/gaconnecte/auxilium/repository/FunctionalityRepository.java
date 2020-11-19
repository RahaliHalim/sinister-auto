package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Functionality;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Functionality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionalityRepository extends JpaRepository<Functionality,Long> {
    
}
