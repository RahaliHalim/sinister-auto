package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Vehicle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    
}
