package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehicleBrand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the VehicleBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand,Long> {
    
    @Query("SELECT vb FROM VehicleBrand vb WHERE LOWER(REPLACE(vb.label,' ','')) =:label")
    VehicleBrand findByLabel(@Param("label") String label);
}
