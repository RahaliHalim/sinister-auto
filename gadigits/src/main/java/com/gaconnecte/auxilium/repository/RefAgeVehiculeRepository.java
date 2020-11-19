package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.RefAgeVehicule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RefTva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefAgeVehiculeRepository extends JpaRepository<RefAgeVehicule,Long> {
    
}
