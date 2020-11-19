package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehicleUsage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VehicleUsage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleUsageRepository extends JpaRepository<VehicleUsage,Long> {
    
}
