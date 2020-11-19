package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Periodicity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Periodicity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodicityRepository extends JpaRepository<Periodicity,Long> {
    
}
