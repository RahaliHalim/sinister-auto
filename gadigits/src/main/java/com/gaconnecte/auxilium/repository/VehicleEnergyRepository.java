package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehicleEnergy;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VehicleEnergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleEnergyRepository extends JpaRepository<VehicleEnergy,Long> {
    
    @Query("SELECT ve FROM VehicleEnergy ve WHERE LOWER(REPLACE(ve.label,' ','')) =:label")
    VehicleEnergy findByLabel(@Param("label") String label);
}
