package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VehicleBrand;
import com.gaconnecte.auxilium.domain.VehicleBrandModel;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the VehicleBrandModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleBrandModelRepository extends JpaRepository<VehicleBrandModel,Long> {
    
    @Query("SELECT vb FROM VehicleBrandModel vb WHERE LOWER(REPLACE(vb.label,' ','')) =:label")
    VehicleBrandModel findByLabel(@Param("label") String label);
    
    List<VehicleBrandModel> findAllByBrandOrderByLabelAsc(@Param("brand") VehicleBrand vehicleBrand);
}
